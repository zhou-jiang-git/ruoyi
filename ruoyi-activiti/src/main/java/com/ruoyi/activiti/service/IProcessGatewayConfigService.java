package com.ruoyi.activiti.service;

import com.ruoyi.activiti.domain.ProcessGatewayConfig;

import java.util.List;

/**
 * 流程网关配置Service接口
 *
 * @author ruoyi
 * @date 2019-12-15
 */
public interface IProcessGatewayConfigService {
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
     * 批量删除流程网关配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProcessGatewayConfigByIds(String ids);

    /**
     * 删除流程网关配置信息
     *
     * @param processGatewayId 流程网关配置ID
     * @return 结果
     */
    public int deleteProcessGatewayConfigById(String processGatewayId);

    /**
     * 批量插入网关配置
     * @param gatewayConfigs
     * @date 2019/12/15 16:28
     * @return
     * @author tht
     */
    void bathAddGatewayConfig(List<ProcessGatewayConfig> gatewayConfigs);
}
