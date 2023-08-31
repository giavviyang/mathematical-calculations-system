package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class WholeFileExportExcelVo implements Serializable {
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    @Excel(name = "创建人", orderNum = "0", width = 15)
    private String createName;
    @Excel(name = "创建时间", orderNum = "1", width = 15)
    private String createTime;
    @Excel(name = "干部名称", orderNum = "2", width = 15)
    private String a0101;
    @Excel(name = "干部编号", orderNum = "3", width = 15)
    private String number;
    @Excel(name = "性别", orderNum = "4", width = 15)
    private String a0104;
    @Excel(name = "出生日期", orderNum = "5", width = 15)
    private String a0107;


}