package com.ruoyi.activiti.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 流程网关配置对象 process_gateway_config
 *
 * @author ruoyi
 * @date 2019-12-15
 */
@Data
@ToString
public class ProcessGatewayConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 网关配置ID */
    private String processGatewayId;

    /** 流程ID */
    @Excel(name = "流程ID")
    private String processId;

    /** 网关key */
    @Excel(name = "网关key")
    private String gatewayKey;

    /** 流转条件,多条件以逗号分隔 */
    @Excel(name = "流转条件,多条件以逗号分隔")
    private String gatewayWhere;

    /** 下一节点key/下一网关等 */
    @Excel(name = "下一节点key/下一网关等")
    private String nextNodeKey;

    /** 下一节点名称/下一网关等 */
    @Excel(name = "下一节点名称/下一网关等")
    private String nextNodeName;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUser;

    /** 更新人 */
    @Excel(name = "更新人")
    private Long updateUser;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 更新时间 */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateDate;

    public void setProcessGatewayId(String processGatewayId) 
    {
        this.processGatewayId = processGatewayId;
    }

    public String getProcessGatewayId() 
    {
        return processGatewayId;
    }
    public void setProcessId(String processId) 
    {
        this.processId = processId;
    }

    public String getProcessId() 
    {
        return processId;
    }
    public void setGatewayKey(String gatewayKey) 
    {
        this.gatewayKey = gatewayKey;
    }

    public String getGatewayKey() 
    {
        return gatewayKey;
    }
    public void setGatewayWhere(String gatewayWhere) 
    {
        this.gatewayWhere = gatewayWhere;
    }

    public String getGatewayWhere() 
    {
        return gatewayWhere;
    }
    public void setNextNodeKey(String nextNodeKey) 
    {
        this.nextNodeKey = nextNodeKey;
    }

    public String getNextNodeKey() 
    {
        return nextNodeKey;
    }
    public void setNextNodeName(String nextNodeName) 
    {
        this.nextNodeName = nextNodeName;
    }

    public String getNextNodeName() 
    {
        return nextNodeName;
    }
    public void setCreateUser(Long createUser) 
    {
        this.createUser = createUser;
    }

    public Long getCreateUser() 
    {
        return createUser;
    }
    public void setUpdateUser(Long updateUser) 
    {
        this.updateUser = updateUser;
    }

    public Long getUpdateUser() 
    {
        return updateUser;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public Date getCreateDate() 
    {
        return createDate;
    }
    public void setUpdateDate(Date updateDate) 
    {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() 
    {
        return updateDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("processGatewayId", getProcessGatewayId())
            .append("processId", getProcessId())
            .append("gatewayKey", getGatewayKey())
            .append("gatewayWhere", getGatewayWhere())
            .append("nextNodeKey", getNextNodeKey())
            .append("nextNodeName", getNextNodeName())
            .append("createUser", getCreateUser())
            .append("updateUser", getUpdateUser())
            .append("createDate", getCreateDate())
            .append("updateDate", getUpdateDate())
            .toString();
    }
}
