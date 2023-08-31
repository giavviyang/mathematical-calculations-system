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
@TableName(value = "e01z9")
public class E01z9 extends BaseEntity implements Serializable {
    public static final String COL_STARTTIME = "startTime";
    public static final String COL_ENDTIME = "endTime";
    /**
     * uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 隶属全宗
     */
    @TableField(value = "org_id")
    private String orgId;

    /**
     * 人员信息ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 人员姓名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 职务
     */
    @TableField(value = "duty")
    private String duty;

    /**
     * 政治面貌
     */
    @TableField(value = "politcy")
    private String politcy;

    /**
     * 借档单位名称
     */
    @TableField(value = "e01z824a")
    private String e01z824a;

    /**
     * 借档单位代码
     */
    @TableField(value = "e01z824b")
    private String e01z824b;

    /**
     * 备注
     */
    @TableField(value = "e01z831")
    private String e01z831;

    /**
     * 判断申请角色1是系统内，2是系统外
     */
    @TableField(value = "target")
    private String target;

    /**
     * 申请时间
     */
    @TableField(value = "approv_time")
    private Date approvTime;

    /**
     * 查阅开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 查阅结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 打印内容
     */
    @TableField(value = "print_contents")
    private String printContents;

    /**
     * 申请理由
     */
    @TableField(value = "application_reasons")
    private String applicationReasons;

    /**
     * 证件号码
     */
    @TableField(value = "user_card")
    private String userCard;

    /**
     * 审批状态 0待审批  1为以通过 -1为未通过
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 申请人电话
     */
    @TableField(value = "user_phone")
    private String userPhone;

    /**
     * 审批意见
     */
    @TableField(value = "flag")
    private String flag;

    /**
     * 经办人
     */
    @TableField(value = "e01z817")
    private String e01z817;

    /**
     * 审批人
     */
    @TableField(value = "e01z801")
    private String e01z801;

    /**
     * 创建时间
     */
    @TableField(value = "creaters_time")
    private Date creatersTime;

    /**
     * 申请人编号
     */
    @TableField(value = "user_number")
    private String userNumber;

    /**
     * 申请份数
     */
    @TableField(value = "apply_number")
    private Integer applyNumber;

    /**
     * 申请状态
     */
    @TableField(value = "apply_status")
    private String applyStatus;

    /**
     * 档案状态
     */
    @TableField(value = "archive_status")
    private String archiveStatus;

    public static final String COL_ID = "id";

    public static final String COL_ORG_ID = "org_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_DUTY = "duty";

    public static final String COL_POLITCY = "politcy";

    public static final String COL_E01Z824A = "e01z824a";

    public static final String COL_E01Z824B = "e01z824b";

    public static final String COL_E01Z831 = "e01z831";

    public static final String COL_TARGET = "target";

    public static final String COL_APPROV_TIME = "approv_time";

    public static final String COL_START_TIME = "start_time";

    public static final String COL_END_TIME = "end_time";

    public static final String COL_PRINT_CONTENTS = "print_contents";

    public static final String COL_APPLICATION_REASONS = "application_reasons";

    public static final String COL_USER_CARD = "user_card";

    public static final String COL_STATUS = "status";

    public static final String COL_USER_PHONE = "user_phone";

    public static final String COL_FLAG = "flag";

    public static final String COL_E01Z817 = "e01z817";

    public static final String COL_E01Z801 = "e01z801";

    public static final String COL_CREATERS_TIME = "creaters_time";

    public static final String COL_USER_NUMBER = "user_number";

    public static final String COL_APPLY_NUMBER = "apply_number";

    public static final String COL_APPLY_STATUS = "apply_status";

    public static final String COL_ARCHIVE_STATUS = "archive_status";
}
