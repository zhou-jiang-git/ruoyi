package com.ruoyi.activiti.service.impl;

import com.ruoyi.activiti.domain.ProcessGatewayConfig;
import com.ruoyi.activiti.mapper.ProcessGatewayConfigMapper;
import com.ruoyi.activiti.service.IProcessGatewayConfigService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程网关配置Service业务层处理
 *
 * @author ruoyi
 * @date 2019-12-15
 */
@Service
public class ProcessGatewayConfigServiceImpl implements IProcessGatewayConfigService {
    @Autowired
    private ProcessGatewayConfigMapper processGatewayConfigMapper;

    /**
     * 查询流程网关配置
     *
     * @param processGatewayId 流程网关配置ID
     * @return 流程网关配置
     */
    @Override
    public ProcessGatewayConfig selectProcessGatewayConfigById(String processGatewayId) {
        return processGatewayConfigMapper.selectProcessGatewayConfigById(processGatewayId);
    }

    /**
     * 查询流程网关配置列表
     *
     * @param processGatewayConfig 流程网关配置
     * @return 流程网关配置
     */
    @Override
    public List<ProcessGatewayConfig> selectProcessGatewayConfigList(ProcessGatewayConfig processGatewayConfig) {
        return processGatewayConfigMapper.selectProcessGatewayConfigList(processGatewayConfig);
    }

    /**
     * 新增流程网关配置
     *
     * @param processGatewayConfig 流程网关配置
     * @return 结果
     */
    @Override
    public int insertProcessGatewayConfig(ProcessGatewayConfig processGatewayConfig) {
        return processGatewayConfigMapper.insertProcessGatewayConfig(processGatewayConfig);
    }

    /**
     * 修改流程网关配置
     *
     * @param processGatewayConfig 流程网关配置
     * @return 结果
     */
    @Override
    public int updateProcessGatewayConfig(ProcessGatewayConfig processGatewayConfig) {
        return processGatewayConfigMapper.updateProcessGatewayConfig(processGatewayConfig);
    }

    /**
     * 删除流程网关配置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProcessGatewayConfigByIds(String ids) {
        return processGatewayConfigMapper.deleteProcessGatewayConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除流程网关配置信息
     *
     * @param processGatewayId 流程网关配置ID
     * @return 结果
     */
    @Override
    public int deleteProcessGatewayConfigById(String processGatewayId) {
        return processGatewayConfigMapper.deleteProcessGatewayConfigById(processGatewayId);
    }

    @Override
    public void bathAddGatewayConfig(List<ProcessGatewayConfig> gatewayConfigs) {
        processGatewayConfigMapper.insertProcessGatewayConfigBatch(gatewayConfigs);
    }
}
