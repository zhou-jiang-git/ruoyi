package com.ruoyi.activiti.mapper;

import com.ruoyi.activiti.domain.ProcessGatewayConfig;

import java.util.List;

/**
 * 流程网关配置Mapper接口
 *
 * @author ruoyi
 * @date 2019-12-15
 */
public interface ProcessGatewayConfigMapper {
    /**
     * 查询流程网关配置
     *
     * @param processGatewayId 流程网关配置ID
     * @return 流程网关配置
     */
    public ProcessGatewayConfig selectProcessGatewayConfigById(String processGatewayId);

    /**
     * 查询流程网关配置列表
     *
     * @param processGatewayConfig 流程网关配置
     * @return 流程网关配置集合
     */
    public List<ProcessGatewayConfig> selectProcessGatewayConfigList(ProcessGatewayConfig processGatewayConfig);

    /**
     * 新增流程网关配置
     *
     * @param processGatewayConfig 流程网关配置
     * @return 结果
     */
    public int insertProcessGatewayConfig(ProcessGatewayConfig processGatewayConfig);

    /**
     * 修改流程网关配置
     *
     * @param processGatewayConfig 流程网关配置
     * @return 结果
     */
    public int updateProcessGatewayConfig(ProcessGatewayConfig processGatewayConfig);

    /**
     * 删除流程网关配置
     *
     * @param processGatewayId 流程网关配置ID
     * @return 结果
     */
    public int deleteProcessGatewayConfigById(String processGatewayId);

    /**
     * 批量删除流程网关配置
     *
     * @param processGatewayIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteProcessGatewayConfigByIds(String[] processGatewayIds);

    /**
     * 批量插入网关配置
     * @param gatewayConfigs
     * @date 2019/12/15 16:29
     * @return
     * @author tht
     */
    void insertProcessGatewayConfigBatch(List<ProcessGatewayConfig> gatewayConfigs);
}
