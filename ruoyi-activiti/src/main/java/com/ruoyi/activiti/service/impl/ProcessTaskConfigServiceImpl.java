package com.ruoyi.activiti.service.impl;

import com.ruoyi.activiti.domain.ProcessTaskConfig;
import com.ruoyi.activiti.mapper.ProcessTaskConfigMapper;
import com.ruoyi.activiti.service.IProcessTaskConfigService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程节点配置Service业务层处理
 *
 * @author ruoyi
 * @date 2019-12-15
 */
@Service
public class ProcessTaskConfigServiceImpl implements IProcessTaskConfigService {

    @Autowired
    private ProcessTaskConfigMapper processTaskConfigMapper;

    /**
     * 查询流程节点配置
     *
     * @param processTaskId 流程节点配置ID
     * @return 流程节点配置
     */
    @Override
    public ProcessTaskConfig selectProcessTaskConfigById(String processTaskId) {
        return processTaskConfigMapper.selectProcessTaskConfigById(processTaskId);
    }

    /**
     * 查询流程节点配置列表
     *
     * @param processTaskConfig 流程节点配置
     * @return 流程节点配置
     */
    @Override
    public List<ProcessTaskConfig> selectProcessTaskConfigList(ProcessTaskConfig processTaskConfig) {
        return processTaskConfigMapper.selectProcessTaskConfigList(processTaskConfig);
    }

    /**
     * 新增流程节点配置
     *
     * @param processTaskConfig 流程节点配置
     * @return 结果
     */
    @Override
    public int insertProcessTaskConfig(ProcessTaskConfig processTaskConfig) {
        return processTaskConfigMapper.insertProcessTaskConfig(processTaskConfig);
    }

    /**
     * 修改流程节点配置
     *
     * @param processTaskConfig 流程节点配置
     * @return 结果
     */
    @Override
    public int updateProcessTaskConfig(ProcessTaskConfig processTaskConfig) {
        return processTaskConfigMapper.updateProcessTaskConfig(processTaskConfig);
    }

    /**
     * 删除流程节点配置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProcessTaskConfigByIds(String ids) {
        return processTaskConfigMapper.deleteProcessTaskConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除流程节点配置信息
     *
     * @param processTaskId 流程节点配置ID
     * @return 结果
     */
    @Override
    public int deleteProcessTaskConfigById(String processTaskId) {
        return processTaskConfigMapper.deleteProcessTaskConfigById(processTaskId);
    }

    @Override
    public void bathAddTaskConfig(List<ProcessTaskConfig> configs) {
        processTaskConfigMapper.insertProcessTaskConfigBatch(configs);
    }
}
