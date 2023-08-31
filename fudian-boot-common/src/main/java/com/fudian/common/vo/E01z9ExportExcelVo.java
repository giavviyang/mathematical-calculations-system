package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description 电子档案查阅导出Excel类
 * @ClassName A01ExportExcelVo
 * @Author mr.zhang
 * @Date 2020/4/2 15:54
 * @Version 1.0.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class E01z9ExportExcelVo implements Serializable {

    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    /**
     * 人员姓名
     */
    @Excel(name = "申请人姓名", orderNum = "1", width = 15)
    private String userName;

    /**
     * 证件号码
     */
    @Excel(name = "申请人证件号码", orderNum = "2", width = 15)
    private String userCard;

    /**
     * 申请理由
     */
    @Excel(name = "申请查阅理由", orderNum = "3", width = 15)
    private String applicationReasons;

    /**
     * 借档单位名称
     */
    @Excel(name = "申请人所在单位", orderNum = "4", width = 15)
    private String e01z824a;

    /**
     * 档案状态
     */
    @Excel(name = "档案归还状态", orderNum = "5", width = 15)
    private String archiveStatus;

    /**
     * 申请人电话
     */
    @Excel(name = "申请人电话号码", orderNum = "6", width = 15)
    private String userPhone;

    /**
     * 职务
     */
    @Excel(name = "申请人职务", orderNum = "7", width = 15)
    private String duty;

    /**
     * 申请份数
     */
    @Excel(name = "申请借阅案卷卷数", orderNum = "8", width = 15)
    private String applyNumber;

    /**
     * 创建时间
     */
    @Excel(name = "申请时间", orderNum = "9", width = 15)
    private String creatersTime;

    /**
     * 查阅开始时间
     */
    @Excel(name = "申请借阅起始日期", orderNum = "10", width = 15)
    private String startTime;

    /**
     * 查阅结束时间
     */
    @Excel(name = "申请借阅截止日期至", orderNum = "11", width = 15)
    private String endTime;

    /**
     * 申请状态
     */
    @Excel(name = "申请类型", orderNum = "12", width = 15)
    private String applyStatus;

    /**
     * 审批人
     */
    @Excel(name = "审批人", orderNum = "13", width = 15)
    private String e01z801;

    /**
     * 经办人
     */
    @Excel(name = "经办人", orderNum = "14", width = 15)
    private String e01z817;

    /**
     * 申请时间
     */
    @Excel(name = "申请时间", orderNum = "15", width = 15)
    private String approvTime;

    /**
     * 审批意见
     */
    @Excel(name = "审批意见", orderNum = "16", width = 15)
    private String flag;

    /**
     * 打印内容
     */
    @Excel(name = "申请打印内容", orderNum = "17", width = 15)
    private String printContents;

    /**
     * 备注
     */
    @Excel(name = "备注", orderNum = "18", width = 15)
    private String e01z831;

    private static final long serialVersionUID = 1L;
}
