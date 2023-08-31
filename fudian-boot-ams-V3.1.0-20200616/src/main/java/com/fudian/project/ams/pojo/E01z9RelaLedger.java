package com.fudian.project.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* @Author: WangYuZhuo
* @Date: 2020/3/13 10:32
* @Desc:
*/
@Getter
@Setter
@ToString
@TableName(value = "e01z9_rela_ledger")
public class E01z9RelaLedger implements Serializable {
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
    @TableField(value = "e01z804")
    private Date e01z804;

    /**
     * 借阅结束日期
     */
    @TableField(value = "e01z805")
    private Date e01z805;

    /**
     * 归还日期
     */
    @TableField(value = "e01z806")
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

    private static final long serialVersionUID = 1L;
}
