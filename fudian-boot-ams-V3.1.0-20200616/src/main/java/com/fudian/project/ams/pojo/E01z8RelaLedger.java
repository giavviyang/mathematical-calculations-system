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
@TableName(value = "e01z8_rela_ledger")
public class E01z8RelaLedger implements Serializable {
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
    @TableField(value = "e01z804")
    private Date e01z804;

    /**
     * 查阅结束日期
     */
    @TableField(value = "e01z805")
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

    private static final long serialVersionUID = 1L;
}
