package com.ruoyi.activiti.service;

import com.ruoyi.activiti.domain.ProcessTaskConfig;

import java.util.List;

/**
 * 流程节点配置Service接口
 *
 * @author ruoyi
 * @date 2019-12-15
 */
public interface IProcessTaskConfigService {
    /**
     * 查询流程节点配置
     *
     * @param processTaskId 流程节点配置ID
     * @return 流程节点配置
     */
    ProcessTaskConfig selectProcessTaskConfigById(String processTaskId);

    /**
     * 查询流程节点配置列表
     *
     * @param processTaskConfig 流程节点配置
     * @return 流程节点配置集合
     */
    List<ProcessTaskConfig> selectProcessTaskConfigList(ProcessTaskConfig processTaskConfig);

    /**
     * 新增流程节点配置
     *
     * @param processTaskConfig 流程节点配置
     * @return 结果
     */
    int insertProcessTaskConfig(ProcessTaskConfig processTaskConfig);

    /**
     * 修改流程节点配置
     *
     * @param processTaskConfig 流程节点配置
     * @return 结果
     */
    int updateProcessTaskConfig(ProcessTaskConfig processTaskConfig);

    /**
     * 批量删除流程节点配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteProcessTaskConfigByIds(String ids);

    /**
     * 删除流程节点配置信息
     *
     * @param processTaskId 流程节点配置ID
     * @return 结果
     */
    int deleteProcessTaskConfigById(String processTaskId);
    
    /**
     * 批量添加节点配置信息
     * @param configs
     * @date 2019/12/15 16:05  
     * @return   
     * @author tht  
     */ 
    void bathAddTaskConfig(List<ProcessTaskConfig> configs);
}
