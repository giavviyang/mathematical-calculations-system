package com.fudian.project.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fudian.common.pojo.BaseEntity;
import com.fudian.common.utils.DateTimeFormatSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/11 19:08
 * @Desc:
 */
@Getter
@Setter
@ToString
@TableName(value = "e01zc")
public class E01zc extends BaseEntity implements Serializable {
    public static final String COL_END_TIME = "end_time";
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
    public static final String COL_STATUS = "status";
    public static final String COL_USER_CARD = "user_card";
    public static final String COL_USER_PHONE = "user_phone";
    public static final String COL_APPLICATION_REASONS = "application_reasons";
    public static final String COL_APPROVER = "approver";
    public static final String COL_PERSON_CHARGE = "person_charge";
    public static final String COL_APPROVER_DATE = "approver_date";
    public static final String COL_APPROVER_OPINION = "approver_opinion";
    public static final String COL_USER_CODE = "user_code";
    public static final String COL_PRINT_REMARK = "print_remark";
    public static final String COL_END_DATE = "end_date";
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
     * 查档单位名称
     */
    @TableField(value = "e01z824a")
    private String e01z824a;

    /**
     * 查档单位代码
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
     * 开始时间
     */
    @TableField(value = "approv_time")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date approvTime;

    /**
     * 审批状态 0待审批  1为以通过 -1为未通过
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 申请人证件号码
     */
    @TableField(value = "user_card")
    private String userCard;

    /**
     * 申请人电话
     */
    @TableField(value = "user_phone")
    private String userPhone;

    /**
     * 申请理由
     */
    @TableField(value = "application_reasons")
    private String applicationReasons;

    /**
     * 申请份数
     */
    @TableField(value = "rela_num")
    private Integer relaNum;

    /**
     * 审批人
     */
    @TableField(value = "approver")
    private String approver;

    /**
     * 经办人
     */
    @TableField(value = "person_charge")
    private String personCharge;

    /**
     * 审批时间
     */
    @TableField(value = "approver_date")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date approverDate;

    /**
     * 审批意见
     */
    @TableField(value = "approver_opinion")
    private String approverOpinion;

    /**
     * 申请人编号
     */
    @TableField(value = "user_code")
    private String userCode;

    /**
     * 打印内容
     */
    @TableField(value = "print_remark")
    private String printRemark;

    /**
     * 申请结束时间
     */
    @TableField(value = "end_date")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 申请类型
     */
    @TableField(value = "type")
    private String type;

    private static final long serialVersionUID = 1L;
}
