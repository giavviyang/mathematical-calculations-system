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
public class E01zcExportExcelVo implements Serializable {
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;

    /**
     * 人员姓名
     */
    @Excel(name = "申请人姓名", width = 15)
    private String userName;

    /**
     * 申请人证件号码
     */
    @Excel(name = "申请人证件号码", width = 15)
    private String userCard;

    /**
     * 申请理由
     */
    @Excel(name = "申请查阅理由",  width = 15)
    private String applicationReasons;


    /**
     * 查档单位名称
     */
    @Excel(name = "申请人所在单位",  width = 15)
    private String e01z824a;

    /**
     * 职务
     */
    @Excel(name = "申请人职务",  width = 15)
    private String duty;

    /**
     * 申请份数
     */
    @Excel(name = "申请查阅档案卷数",  width = 15)
    private String relaNum;

    /**
     * 开始时间
     */
    @Excel(name = "申请时间", width = 15)
    private String approvTime;

    /**
     * 申请结束时间
     */
    @Excel(name = "申请查阅截止时间至", width = 15)
    private String endDate;

    /**
     * 申请人电话
     */
    @Excel(name = "申请人电话号码",  width = 15)
    private String userPhone;

    /**
     * 申请类型
     */
    @Excel(name = "申请类型", width = 15)
    private String type;

    /**
     * 查阅状态
     */
    @Excel(name = "查阅状态", width = 15)
    private String lookType;

    /**
     * 审批人
     */
    @Excel(name = "审批人",  width = 15)
    private String approver;

    /**
     * 经办人
     */
    @Excel(name = "经办人",  width = 15)
    private String personCharge;

    /**
     * 审批时间
     */
    @Excel(name = "审批时间",  width = 15)
    private String approverDate;

    /**
     * 审批意见
     */
    @Excel(name = "审批意见",  width = 15)
    private String approverOpinion;

    /**
     * 打印内容
     */
    @Excel(name = "申请打印内容", width = 15)
    private String printRemark;

    /**
     * 备注
     */
    @Excel(name = "备注",  width = 15)
    private String e01z831;

    private static final long serialVersionUID = 1L;
}
