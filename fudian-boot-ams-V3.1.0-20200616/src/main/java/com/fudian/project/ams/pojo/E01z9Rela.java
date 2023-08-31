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
@TableName(value = "e01z9_rela")
public class E01z9Rela extends BaseEntity implements Serializable {
    /**
     * uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 借档对象姓名
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
     * 借阅日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "e01z804")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    private Date e01z804;

    /**
     * 借阅结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "e01z805")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    private Date e01z805;

    /**
     * 归还日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "e01z806")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    private Date e01z806;

    /**
     * e01z9.id
     */
    @TableField(value = "e01z9_id")
    private String e01z9Id;

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
     * 借阅理由
     */
    @TableField(value = "e01z821")
    private String e01z821;

    /**
     * 申请权限(AMS10)
     */
    @TableField(value = "pri_type")
    private String priType;

    /**
     * 借档材料id
     */
    @TableField(value = "e01z1_id")
    private String e01z1Id;

    /**
     * 材料份数
     */
    @TableField(value = "materials")
    private Integer materials;

    /**
     * 总页数
     */
    @TableField(value = "total_pages")
    private Integer totalPages;

    /**
     * 借档材料名称
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
     * 借阅反馈
     */
    @TableField(value = "feedback")
    private String feedback;

    /**
     * 涂改内容
     */
    @TableField(value = "modified")
    private String modified;

    /**
     * 是否破损
     */
    @TableField(value = "is_break")
    private String isBreak;

    /**
     * 破损内容
     */
    @TableField(value = "broken")
    private String broken;

    /**
     * 是否遗失
     */
    @TableField(value = "is_lose")
    private String isLose;

    /**
     * 具体情况说明
     */
    @TableField(value = "instructions")
    private String instructions;

    /**
     * 是否已归还
1未归还，2已归还
     */
    @TableField(value = "remind")
    private String remind;

    /**
     * 归还经办人
     */
    @TableField(value = "e01z934")
    private String e01z934;

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

    public static final String COL_E01Z806 = "e01z806";

    public static final String COL_E01Z9_ID = "e01z9_id";

    public static final String COL_E01Z811 = "e01z811";

    public static final String COL_E01Z814 = "e01z814";

    public static final String COL_E01Z817 = "e01z817";

    public static final String COL_E01Z821 = "e01z821";

    public static final String COL_PRI_TYPE = "pri_type";

    public static final String COL_E01Z1_ID = "e01z1_id";

    public static final String COL_MATERIALS = "materials";

    public static final String COL_TOTAL_PAGES = "total_pages";

    public static final String COL_E01Z111A = "e01z111a";

    public static final String COL_FLAG = "flag";

    public static final String COL_E01Z831 = "e01z831";

    public static final String COL_FEEDBACK = "feedback";

    public static final String COL_MODIFIED = "modified";

    public static final String COL_IS_BREAK = "is_break";

    public static final String COL_BROKEN = "broken";

    public static final String COL_IS_LOSE = "is_lose";

    public static final String COL_INSTRUCTIONS = "instructions";

    public static final String COL_REMIND = "remind";

    public static final String COL_E01Z934 = "e01z934";
}
