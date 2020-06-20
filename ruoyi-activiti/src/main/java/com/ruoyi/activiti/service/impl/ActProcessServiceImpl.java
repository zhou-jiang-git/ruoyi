package com.ruoyi.activiti.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ruoyi.activiti.domain.ProcessDefinitionDto;
import com.ruoyi.activiti.domain.ProcessGatewayConfig;
import com.ruoyi.activiti.domain.ProcessTaskConfig;
import com.ruoyi.activiti.service.ActProcessService;
import com.ruoyi.activiti.service.IProcessGatewayConfigService;
import com.ruoyi.activiti.service.IProcessTaskConfigService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

/**
 * 流程管理 服务层实现层
 *
 * @author ruoyi
 */
@Service
public class ActProcessServiceImpl implements ActProcessService {
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private IProcessTaskConfigService processTaskConfigService;

    @Autowired
    private IProcessGatewayConfigService processGatewayConfigService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 将流程定义转换成模型
     *
     * @param processId 流程编号
     * @return 模型数据
     * @throws Exception
     */
    @Override
    public Model convertToModel(String processId) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        ObjectNode modelNode = converter.convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getResourceName());
        modelData.setCategory(processDefinition.getCategory());
        modelData.setDeploymentId(processDefinition.getDeploymentId());
        modelData.setVersion(Integer.parseInt(
                String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count() + 1)));

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
        return modelData;
    }

    /**
     * 使用部署对象ID查看流程图
     *
     * @param deploymentId 部署id
     * @param imageName    资源文件名
     * @return 文件流
     */
    @Override
    public InputStream findImageStream(String deploymentId, String imageName) throws Exception {
        return repositoryService.getResourceAsStream(deploymentId, imageName);
    }

    /**
     * 查询流程定义
     *
     * @param processDefinition 流程信息
     * @return 流程集合
     */
    @Override
    public TableDataInfo selectProcessDefinitionList(ProcessDefinitionDto processDefinition) {
        TableDataInfo data = new TableDataInfo();
        ProcessDefinitionQuery pdQuery = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotEmpty(processDefinition.getKey())) {
            pdQuery.processDefinitionKey(processDefinition.getKey());
        }
        if (StringUtils.isNotEmpty(processDefinition.getName())) {
            pdQuery.processDefinitionName(processDefinition.getName());
        }
        if (StringUtils.isNotEmpty(processDefinition.getDeploymentId())) {
            pdQuery.deploymentId(processDefinition.getDeploymentId());
        }
        data.setTotal(pdQuery.count());
        data.setRows(pdQuery.orderByDeploymentId().desc()
                .listPage(processDefinition.getPageNum(), processDefinition.getPageSize()).stream()
                .map(ProcessDefinitionDto::new).collect(Collectors.toList()));
        return data;
    }

    /**
     * 部署流程定义
     *
     * @param is       文件流
     * @param fileName 文件名称
     * @param category 类型
     * @return 结果
     */
    @Transactional
    @Override
    public AjaxResult saveNameDeplove(InputStream is, String fileName, String category) {
        try {
            String extension = FilenameUtils.getExtension(fileName);
            Deployment deployment;
            if (extension.equals("zip")) {
                ZipInputStream zipInputStream = new ZipInputStream(is);
                // 创建流程定义
                deployment = repositoryService.createDeployment().name(fileName)
                        .addZipInputStream(zipInputStream).deploy();

            } else if (extension.equals("bpmn")) {
                // 创建流程定义
                deployment = repositoryService.createDeployment()
                        .addInputStream(fileName, is).deploy();
            } else {
                return AjaxResult.error("不支持的文件类型：" + extension);
            }

            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployment.getId()).singleResult();
            repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);

            this.initTaskAndGatewayConfig(processDefinition.getId());

            return AjaxResult.success(StringUtils.format("部署成功，流程编号[{}]", processDefinition.getId()));

        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 根据流程部署id，删除流程定义
     *
     * @param ids 部署ids
     * @return 结果
     */
    @Override
    public boolean deleteProcessDefinitionByDeploymentIds(String ids) {
        boolean result = true;
        try {
            String[] deploymentIds = Convert.toStrArray(ids);
            for (String deploymentId : deploymentIds) {
                // 级联删除，不管流程是否启动，都能可以删除
                repositoryService.deleteDeployment(deploymentId, true);
            }
        } catch (Exception e) {
            result = false;
            throw e;
        }
        return result;
    }

    @Override
    public void initTaskAndGatewayConfig(String processDefinitionId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Process process = bpmnModel.getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        //节点配置集合
        List<ProcessTaskConfig> taskConfig = new ArrayList<>();
        //排他网关配置集合
        List<ProcessGatewayConfig> gateway = new ArrayList<>();
        long taskNum = 1;

        for (FlowElement flowElement:flowElements) {
            //用户任务或者服务任务
            if (flowElement instanceof UserTask ||flowElement instanceof ServiceTask) {
                ProcessTaskConfig cfgParams = new ProcessTaskConfig();
                cfgParams.setProcessTaskId(UUID.randomUUID().toString().replaceAll("-", ""));
                cfgParams.setProcessId(processDefinitionId);
                cfgParams.setCreateUser(ShiroUtils.getSysUser().getUserId());
                cfgParams.setCreateDate(new Date());
                cfgParams.setUpdateUser(ShiroUtils.getSysUser().getUserId());
                cfgParams.setUpdateDate(new Date());
                //设置默认属性
                //审批类型 随机指派
                cfgParams.setTaskUserType("0");;
                //驳回处理人模式 上一节点审批人
                cfgParams.setTaskRejectType("0");
                //会签模型默认 一票否决
                cfgParams.setTaskSignType("0");

                if(flowElement instanceof UserTask) {
                    UserTask u = (UserTask) flowElement;
                    cfgParams.setTaskType("userTask");
                    cfgParams.setTaskKey(u.getId());
                    cfgParams.setTaskName(u.getName());
                    cfgParams.setTaskNum(taskNum);
                    taskNum++;
                }
                taskConfig.add(cfgParams);
            }
            //排他网关
            if (flowElement instanceof ExclusiveGateway) {
                ExclusiveGateway exclusiveGateway = (ExclusiveGateway) flowElement;
                List<SequenceFlow> outLine = exclusiveGateway.getOutgoingFlows();
                for(SequenceFlow flow : outLine) {
                    //获取流出节点信息
                    ProcessGatewayConfig out = new ProcessGatewayConfig();
                    out.setProcessGatewayId(UUID.randomUUID().toString().replaceAll("-", ""));
                    out.setProcessId(processDefinitionId);
                    out.setCreateUser(ShiroUtils.getSysUser().getUserId());
                    out.setCreateDate(new Date());
                    out.setUpdateUser(ShiroUtils.getSysUser().getUserId());
                    out.setUpdateDate(new Date());
                    out.setGatewayKey(exclusiveGateway.getId());

                    //目标节点
                    FlowElement targetFlowElement = process.getFlowElement(flow.getTargetRef());
                    out.setNextNodeKey(targetFlowElement.getId());
                    out.setNextNodeName(targetFlowElement.getName());
                    gateway.add(out);
                }
            }
        }

        if(taskConfig.size()>0) {
            processTaskConfigService.bathAddTaskConfig(taskConfig);
        }

        if(gateway.size()>0) {
            processGatewayConfigService.bathAddGatewayConfig(gateway);
        }
    }

    @Override
    public Map<String, Object> getProcessConfigData(String processId) {

        Map<String, Object> config = new HashMap<>();
        List<Map<String, Object>> taskConfigList = new ArrayList<>();
        ProcessTaskConfig processTaskConfig = new ProcessTaskConfig();
        processTaskConfig.setProcessId(processId);

        List<ProcessTaskConfig> processTaskConfigs = processTaskConfigService.selectProcessTaskConfigList(processTaskConfig);

        if(!processTaskConfigs.isEmpty()) {
            processTaskConfigs.forEach(taskConfig -> {
                Map<String, Object> configMap = new HashMap<>();
                configMap.put("processTaskId", taskConfig.getProcessTaskId());
                configMap.put("taskNum", taskConfig.getTaskNum());
                configMap.put("taskKey", taskConfig.getTaskKey());
                configMap.put("taskName", taskConfig.getTaskName());
                configMap.put("taskType", taskConfig.getTaskType());
                configMap.put("taskUserType", taskConfig.getTaskUserType());
                configMap.put("taskSignType", taskConfig.getTaskSignType());
                configMap.put("taskRejectType", taskConfig.getTaskRejectType());

                if(StringUtils.isNotBlank(taskConfig.getTaskUsers())) {
                    List<SysUser> taskUsers = new ArrayList<>();
                    List<String> userIds = Arrays.asList(taskConfig.getTaskUsers().split(","));
                    userIds.forEach(userId -> taskUsers.add(sysUserService.selectUserById(Long.valueOf(userId))));
                    configMap.put("taskUsers",taskUsers);
                }
                if(StringUtils.isNotBlank(taskConfig.getTaskRoles())) {
                    List<SysRole> taskRoles = new ArrayList<>();
                    List<String> roleIds = Arrays.asList(taskConfig.getTaskRoles().split(","));
                    roleIds.forEach(roleId -> taskRoles.add(sysRoleService.selectRoleById(Long.valueOf(roleId))));
                    configMap.put("taskRoles",taskRoles);
                }
                taskConfigList.add(configMap);
            });

        }

        config.put("processTaskConfig", taskConfigList);


        return config;
    }

    @Override
    public void saveUserTaskConfig(ProcessTaskConfig processTaskConfig) {
        ProcessTaskConfig taskConfig = processTaskConfigService.selectProcessTaskConfigById(processTaskConfig.getProcessTaskId());
        taskConfig.setTaskUsers(processTaskConfig.getTaskUsers());
        taskConfig.setTaskRoles(processTaskConfig.getTaskRoles());
        taskConfig.setTaskPosts(processTaskConfig.getTaskPosts());
        processTaskConfigService.updateProcessTaskConfig(taskConfig);
    }
}
