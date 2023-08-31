package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileDeptExportExcelVo implements Serializable {
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    @Excel(name = "单位名称", orderNum = "0", width = 15)
    private String deptName;
    @Excel(name = "单位编号", orderNum = "1", width = 15)
    private String deptNumber;
//    @Excel(name = "单位状态", orderNum = "2", width = 15)
//    private String status;
//
//    public String getStatus() {
//        if(status.equals("0")){
//            return "启用";
//        }else if(status.equals("1")){
//            return "禁用";
//        }
//        return status;
//    }

    @Excel(name = "显示顺序", orderNum = "3", width = 15)
    private String orderNum;
    @Excel(name = "备注", orderNum = "4", width = 15)
    private String remark;


}