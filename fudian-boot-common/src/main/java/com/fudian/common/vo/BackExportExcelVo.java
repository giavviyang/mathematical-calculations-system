package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class BackExportExcelVo implements Serializable {

    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    @Excel(name = "部门名称", width = 15)
    private String deptName;
    @Excel(name = "干部姓名", width = 15)
    private String a0101;
    @Excel(name = "材料类号", width = 15)
    private String e01z101;
    @Excel(name = "材料序号", width = 15)
    private String e01z104;
//    @Excel(name = "材料编号", width = 15)
//    private String e01z107;
    @Excel(name = "材料名称", width = 15)
    private String e01z111a;
    @Excel(name = "页数", width = 15)
    private String e01z114;
    @Excel(name = "材料制成日期", width = 15)
    private String e01z117;
    @Excel(name = "备注", width = 15)
    private String e01z121;
    @Excel(name = "份数", width = 15)
    private String e01z124;
    @Excel(name = "档案卷本号", width = 15)
    private String e01z127;
    @Excel(name = "删除时间", width = 15)
    private String updateTime;
    @Excel(name = "操作人员", width = 15)
    private String updateBy;
}