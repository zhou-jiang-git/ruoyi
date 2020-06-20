package com.ruoyi.activiti.mapper;

import com.ruoyi.activiti.domain.ProcessTaskConfig;

import java.util.List;

/**
 * 流程节点配置Mapper接口
 *
 * @author ruoyi
 * @date 2019-12-15
 */
public interface ProcessTaskConfigMapper {
    /**
     * 查询流程节点配置
     *
     * @param processTaskId 流程节点配置ID
     * @return 流程节点配置
     */
    public ProcessTaskConfig selectProcessTaskConfigById(String processTaskId);

    /**
     * 查询流程节点配置列表
     *
     * @param processTaskConfig 流程节点配置
     * @return 流程节点配置集合
     */
    public List<ProcessTaskConfig> selectProcessTaskConfigList(ProcessTaskConfig processTaskConfig);

    /**
     * 新增流程节点配置
     *
     * @param processTaskConfig 流程节点配置
     * @return 结果
     */
    public int insertProcessTaskConfig(ProcessTaskConfig processTaskConfig);

    /**
     * 修改流程节点配置
     *
     * @param processTaskConfig 流程节点配置
     * @return 结果
     */
    public int updateProcessTaskConfig(ProcessTaskConfig processTaskConfig);

    /**
     * 删除流程节点配置
     *
     * @param processTaskId 流程节点配置ID
     * @return 结果
     */
    public int deleteProcessTaskConfigById(String processTaskId);

    /**
     * 批量删除流程节点配置
     *
     * @param processTaskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteProcessTaskConfigByIds(String[] processTaskIds);

    /**
     * 批量添加节点配置信息
     * @param configs
     * @date 2019/12/15 16:07
     * @return
     * @author tht
     */
    void insertProcessTaskConfigBatch(List<ProcessTaskConfig> configs);
}
