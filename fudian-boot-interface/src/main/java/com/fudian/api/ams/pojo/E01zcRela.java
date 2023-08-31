package com.fudian.api.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fudian.common.pojo.BaseEntity;
import com.fudian.common.utils.DateTimeFormatSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/2 15:36
 * @Desc:
 */
@ApiModel(value = "com-fudian-project-ams-pojo-E01zcRela")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "e01zc_rela")
public class E01zcRela extends BaseEntity {
    /**
     * uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "uuid")
    private String id;

    /**
     * 查档对象姓名
     */
    @TableField(value = "a0101")
    @ApiModelProperty(value = "查档对象姓名")
    private String a0101;

    /**
     * 借档对象id
     */
    @TableField(value = "a01_id")
    @ApiModelProperty(value = "借档对象id")
    private String a01Id;

    /**
     * 单位id
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value = "单位id")
    private String deptId;

    /**
     * 单位名称
     */
    @TableField(value = "dept_name")
    @ApiModelProperty(value = "单位名称")
    private String deptName;

    /**
     * 职务
     */
    @TableField(value = "duty")
    @ApiModelProperty(value = "职务")
    private String duty;

    /**
     * 政治面貌
     */
    @TableField(value = "politcy")
    @ApiModelProperty(value = "政治面貌")
    private String politcy;

    /**
     * 批准人
     */
    @TableField(value = "e01z801")
    @ApiModelProperty(value = "批准人")
    private String e01z801;

    /**
     * 查阅日期
     */
    @TableField(value = "e01z804")
    @ApiModelProperty(value = "查阅日期")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    private Date e01z804;

    /**
     * 查阅结束日期
     */
    @TableField(value = "e01z805")
    @ApiModelProperty(value = "查阅结束日期")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    private Date e01z805;



    /**
     * e01zc.id
     */
    @TableField(value = "e01zc_id")
    @ApiModelProperty(value = "e01zc.id")
    private String e01zcId;

    /**
     * 摘抄范围
     */
    @TableField(value = "e01z811")
    @ApiModelProperty(value = "摘抄范围")
    private String e01z811;

    /**
     * 摘抄人
     */
    @TableField(value = "e01z814")
    @ApiModelProperty(value = "摘抄人")
    private String e01z814;

    /**
     * 经办人
     */
    @TableField(value = "e01z817")
    @ApiModelProperty(value = "经办人")
    private String e01z817;

    /**
     * 查阅理由
     */
    @TableField(value = "e01z821")
    @ApiModelProperty(value = "查阅理由")
    private String e01z821;

    /**
     * 申请权限(AMS10)
     */
    @TableField(value = "pri_type")
    @ApiModelProperty(value = "申请权限(AMS10)")
    private String priType;

    /**
     * 查档材料id
     */
    @TableField(value = "e01z1_id")
    @ApiModelProperty(value = "查档材料id")
    private String e01z1Id;

    /**
     * 查档材料名称
     */
    @TableField(value = "e01z111a")
    @ApiModelProperty(value = "查档材料名称")
    private String e01z111a;

    /**
     * 通过后的材料id，如若为空则按照e01z1_id材料查阅
     */
    @TableField(value = "adopt_e01z1_id")
    @ApiModelProperty(value = "通过后的材料id，如若为空则按照e01z1_id材料查阅")
    private String adoptE01z1Id;

    /**
     * 审批意见
     */
    @TableField(value = "approval_opinion")
    @ApiModelProperty(value = "审批意见")
    private String approvalOpinion;

    /**
     * 审批状态
     */
    @TableField(value = "flag")
    @ApiModelProperty(value = "审批状态")
    private String flag;

    /**
     * 备注
     */
    @TableField(value = "e01z831")
    @ApiModelProperty(value = "备注")
    private String e01z831;

    /**
     * 用户查阅反馈
     */
    @TableField(value = "feedback")
    @ApiModelProperty(value = "用户查阅反馈")
    private String feedback;

    /**
     * 通过后的材料名称
     */
    @TableField(value = "adopt_e01z1_msg")
    @ApiModelProperty(value = "通过后的材料名称")
    private String adoptE01z1Msg;

    public static final String COL_ID = "id";

    public static final String COL_A0101 = "a0101";

    public static final String COL_A01_ID = "a01_id";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_DEPT_NAME = "dept_name";

    public static final String COL_DUTY = "duty";

    public static final String COL_POLITCY = "politcy";

    public static final String COL_E01Z801 = "e01z801";

    public static final String COL_E01Z804 = "e01z804";

    public static final String COL_E01Z805 = "e01z805";

    public static final String COL_E01ZC_ID = "e01zc_id";

    public static final String COL_E01Z811 = "e01z811";

    public static final String COL_E01Z814 = "e01z814";

    public static final String COL_E01Z817 = "e01z817";

    public static final String COL_E01Z821 = "e01z821";

    public static final String COL_PRI_TYPE = "pri_type";

    public static final String COL_E01Z1_ID = "e01z1_id";

    public static final String COL_E01Z111A = "e01z111a";

    public static final String COL_ADOPT_E01Z1_ID = "adopt_e01z1_id";

    public static final String COL_APPROVAL_OPINION = "approval_opinion";

    public static final String COL_FLAG = "flag";

    public static final String COL_E01Z831 = "e01z831";

    public static final String COL_FEEDBACK = "feedback";

    public static final String COL_ADOPT_E01Z1_MSG = "adopt_e01z1_msg";
}
