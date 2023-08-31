package com.fudian.project.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fudian.common.pojo.BaseEntity;
import com.fudian.common.utils.DateTimeFormatSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
  *@author lutao
  *@create 2020/1/8
*/

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "e01z8_rela")
public class E01z8Rela extends BaseEntity implements Serializable {
    /**
     * uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 查档对象姓名
     */
    @TableField(value = "a0101")
    private String a0101;

    /**
     * 借档对象id
     */
    @TableField(value = "a01_id")
    private String a01Id;

    /**
     * 单位id
     */
    @TableField(value = "dept_id")
    private String deptId;

    /**
     * 单位名称
     */
    @TableField(value = "dept_name")
    private String deptName;

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
     * 批准人
     */
    @TableField(value = "e01z801")
    private String e01z801;

    /**
     * 查阅日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "e01z804")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    private Date e01z804;

    /**
     * 查阅结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "e01z805")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    private Date e01z805;

    /**
     * e01z8.id
     */
    @TableField(value = "e01z8_id")
    private String e01z8Id;

    /**
     * 摘抄范围
     */
    @TableField(value = "e01z811")
    private String e01z811;

    /**
     * 摘抄人
     */
    @TableField(value = "e01z814")
    private String e01z814;

    /**
     * 经办人
     */
    @TableField(value = "e01z817")
    private String e01z817;

    /**
     * 查阅理由
     */
    @TableField(value = "e01z821")
    private String e01z821;

    /**
     * 申请权限(AMS10)
     */
    @TableField(value = "pri_type")
    private String priType;

    /**
     * 查档材料id
     */
    @TableField(value = "e01z1_id")
    private String e01z1Id;

    /**
     * 查档材料名称
     */
    @TableField(value = "e01z111a")
    private String e01z111a;

    /**
     * 审批意见
     */
    @TableField(value = "flag")
    private String flag;

    /**
     * 备注
     */
    @TableField(value = "e01z831")
    private String e01z831;

    /**
     * 用户查阅反馈
     */
    @TableField(value = "feedback")
    private String feedback;

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

    public static final String COL_E01Z8_ID = "e01z8_id";

    public static final String COL_E01Z811 = "e01z811";

    public static final String COL_E01Z814 = "e01z814";

    public static final String COL_E01Z817 = "e01z817";

    public static final String COL_E01Z821 = "e01z821";

    public static final String COL_PRI_TYPE = "pri_type";

    public static final String COL_E01Z1_ID = "e01z1_id";

    public static final String COL_E01Z111A = "e01z111a";

    public static final String COL_FLAG = "flag";

    public static final String COL_E01Z831 = "e01z831";

    public static final String COL_FEEDBACK = "feedback";
}
