package com.fudian.project.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "wf_rule")
public class WfRule extends BaseEntity implements Serializable {
    public static final String COL_序号 = "序号";
    public static final String COL_CREATES_TIME = "creates_time";
    public static final String COL_UPDATES_TIME = "updates_time";
    /**
     * 主键
     */
    @TableId(value = "rule_id", type = IdType.INPUT)
    private String ruleId;

    /**
     * 制度名称
     */
    @TableField(value = "rule_name")
    private String ruleName;

    /**
     * 文号
     */
    @TableField(value = "rule_number")
    private String ruleNumber;

    /**
     * 制度类型
     */
    @TableField(value = "rule_type")
    private String ruleType;

    /**
     * 执行情况 0现行有效   1已废止
     */
    @TableField(value = "rule_status")
    private String ruleStatus;

    /**
     * 文件注释
     */
    @TableField(value = "rule_remark")
    private String ruleRemark;

    /**
     * 序号
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;



    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    public static final String COL_RULE_ID = "rule_id";

    public static final String COL_RULE_NAME = "rule_name";

    public static final String COL_RULE_NUMBER = "rule_number";

    public static final String COL_RULE_TYPE = "rule_type";

    public static final String COL_RULE_STATUS = "rule_status";

    public static final String COL_RULE_REMARK = "rule_remark";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_ORDER_NUM = "order_num";
}
