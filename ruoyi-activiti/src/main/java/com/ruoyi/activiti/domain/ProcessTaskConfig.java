package com.ruoyi.activiti.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 流程节点配置对象 process_task_config
 * 
 * @author ruoyi
 * @date 2019-12-15
 */
@Data
@ToString
public class ProcessTaskConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 节点配置id */
    private String processTaskId;

    /** 流程id */
    @Excel(name = "流程id")
    private String processId;

    /** 节点顺序 */
    @Excel(name = "节点顺序")
    private Long taskNum;

    /** 任务节点key */
    @Excel(name = "任务节点key")
    private String taskKey;

    /** 节点name */
    @Excel(name = "节点name")
    private String taskName;

    /** 节点类型 userTask/serviceTask/signTask */
    @Excel(name = "节点类型 userTask/serviceTask/signTask")
    private String taskType;

    /** 审批状态设置 0 多用户随机指派、1多用户竞争审批 */
    @Excel(name = "审批状态设置 0 多用户随机指派、1多用户竞争审批")
    private String taskUserType;

    /** 会签状态 0一票否决 1全票通过 2半数通过 */
    @Excel(name = "会签状态 0一票否决 1全票通过 2半数通过")
    private String taskSignType;

    /** 驳回节点key */
    @Excel(name = "驳回节点key")
    private String taskRejectType;

    /** 节点用户 */
    @Excel(name = "节点用户")
    private String taskUsers;

    /** 节点岗位 */
    @Excel(name = "节点岗位")
    private String taskPosts;

    /** 节点角色 */
    @Excel(name = "节点角色")
    private String taskRoles;

    /** 审批节点按钮json Array 可调整顺序 */
    @Excel(name = "审批节点按钮json Array 可调整顺序")
    private String taskButtons;

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
}
