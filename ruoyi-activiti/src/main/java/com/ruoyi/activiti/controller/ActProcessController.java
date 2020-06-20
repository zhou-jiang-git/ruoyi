package com.ruoyi.activiti.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ruoyi.activiti.domain.ProcessDefinitionDto;
import com.ruoyi.activiti.domain.ProcessTaskConfig;
import com.ruoyi.activiti.service.ActProcessService;
import com.ruoyi.activiti.service.impl.ProcessDefinitionDiagramLayoutResource;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 流程管理 操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/activiti/process")
public class ActProcessController extends BaseController {
    private String prefix = "activiti/process";

    @Autowired
    private ActProcessService actProcessService;

    @Autowired
    private RepositoryService repositoryService;

    @Resource
    private ProcessDefinitionDiagramLayoutResource processDefinitionDiagramLayoutResource;

    @RequiresPermissions("activiti:process:view")
    @GetMapping
    public String process() {
        return prefix + "/process";
    }

    @RequiresPermissions("activiti:process:list")
    @PostMapping("list")
    @ResponseBody
    public TableDataInfo list(ProcessDefinitionDto processDefinitionDto) {
        return actProcessService.selectProcessDefinitionList(processDefinitionDto);
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * TODO(流程部署)
     * @param category
     * @param file
     * @date 2019/12/15 16:35
     * @return
     * @author tht
     */
    @RequiresPermissions("activiti:process:add")
    @Log(title = "流程部署", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam String category, @RequestParam("file") MultipartFile file)
            throws IOException {
        InputStream fileInputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        return actProcessService.saveNameDeplove(fileInputStream, fileName, category);
    }

    @RequiresPermissions("activiti:process:model")
    @GetMapping(value = "/convertToModel/{processId}")
    @ResponseBody
    public AjaxResult convertToModel(@PathVariable("processId") String processId) {
        try {
            Model model = actProcessService.convertToModel(processId);
            return success(StringUtils.format("转换模型成功，模型编号[{}]", model.getId()));
        } catch (Exception e) {
            return error("转换模型失败" + e.getMessage());
        }
    }

    @GetMapping(value = "/resource/{imageName}/{deploymentId}")
    public void viewImage(@PathVariable("imageName") String imageName,
                          @PathVariable("deploymentId") String deploymentId, HttpServletResponse response) {
        try {
            InputStream in = actProcessService.findImageStream(deploymentId, imageName);
            for (int bit = -1; (bit = in.read()) != -1; ) {
                response.getOutputStream().write(bit);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresPermissions("activiti:process:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(actProcessService.deleteProcessDefinitionByDeploymentIds(ids));
    }

    /**
     * TODO(流程配置页)
     *
     * @param processId 流程id
     * @return
     * @date 2019/12/9 12:42
     * @author tht
     */
    @GetMapping(value = "/processConfig/{processId}")
    public String processConfig(@PathVariable("processId") String processId, ModelMap mmap) {

        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
                .getProcessDefinition(processId);

        mmap.put("processId", processId);
        mmap.put("deploymentId", processDefinition.getDeploymentId());
        mmap.put("processKey",processDefinition.getKey());
        mmap.put("processVersion",processDefinition.getVersion());
        String processName = processDefinition.getName();
        mmap.put("processName",StringUtils.isBlank(processName)?"流程图":processName);

        return prefix + "/process_config";
    }


    /**
     * TODO(获取流程定义数据)
     *
     * @param processId 流程id
     * @return
     * @date 2019/12/9 14:29
     * @author tht
     */
    @GetMapping(value = "/process-definition/{processId}")
    @ResponseBody
    public ObjectNode processDefinition(@PathVariable("processId") String processId) {
        ObjectNode diagramNode = processDefinitionDiagramLayoutResource.getDiagramNode(null, processId);
        return diagramNode;
    }

    /**
     * 获取流程配置信息
     * @param processId
     * @date 2019/12/16 16:41
     * @return
     * @author tht
     */
    @GetMapping(value = "/getProcessConfigData")
    @ResponseBody
    public AjaxResult getProcessConfigData(@RequestParam String processId) {
        if(StringUtils.isBlank(processId)) {
            return AjaxResult.error("流程id不不能为空!");
        }
        return AjaxResult.success(actProcessService.getProcessConfigData(processId));
    }

    /**
     * 保存人工节点配置信息
     * @date 2019/12/16 16:41
     * @return
     * @author tht
     */
    @RequiresPermissions("activiti:process:set")
    @Log(title = "流程配置保存", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/saveUserTaskConfig")
    @ResponseBody
    public AjaxResult saveUserTaskConfig(ProcessTaskConfig processTaskConfig) {
        actProcessService.saveUserTaskConfig(processTaskConfig);
        return AjaxResult.success();
    }
}
