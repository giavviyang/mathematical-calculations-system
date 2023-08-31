package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class DictExportExcelVo implements Serializable {

    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    @Excel(name = "字典名称", orderNum = "0", width = 15)
    private String dictLabel;
    @Excel(name = "字典状态", orderNum = "1", width = 15)
    private String status;
    @Excel(name = "字典类型", orderNum = "2", width = 15)
    private String dictType;

    public String getStatus() {
        if(status.equals("0")){
            return "启用";
        }else if(status.equals("1")){
            return "禁止";
        }
        return status;
    }
}