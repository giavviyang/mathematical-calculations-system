package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScatteredMaterialExportExcelVo implements Serializable {
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    @Excel(name = "创建人", orderNum = "0", width = 15)
    private String createName;
    @Excel(name = "创建时间", orderNum = "1", width = 15)
    private String createTime;
    @Excel(name = "材料总份数", orderNum = "2", width = 15)
    private String shareNum;
    @Excel(name = "材料总页数", orderNum = "3", width = 15)
    private String pageNum;

}