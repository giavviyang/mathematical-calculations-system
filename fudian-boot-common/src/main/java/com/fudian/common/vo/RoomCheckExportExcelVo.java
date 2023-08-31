package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomCheckExportExcelVo implements Serializable {
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    @Excel(name = "检查人", orderNum = "0", width = 15)
    private String checkname;
    @Excel(name = "所属库房", orderNum = "1", width = 15)
    private String roomName;
    @Excel(name = "检查时间", orderNum = "2", width = 15)
    private String checkdate;
    @Excel(name = "温度（°c）", orderNum = "3", width = 15)
    private String temperature;
    @Excel(name = "湿度（%）", orderNum = "4", width = 15)
    private String humidity;
    @Excel(name = "库房基本配置", orderNum = "5", width = 15)
    private String roomconfig;
    @Excel(name = "灭火设施检查", orderNum = "6", width = 15)
    private String devicedesc;
    @Excel(name = "防尘检查", orderNum = "7", width = 15)
    private String dustproof;
    @Excel(name = "通风情况", orderNum = "8", width = 15)
    private String airthough;
    @Excel(name = "线路检查", orderNum = "9", width = 15)
    private String electricline;
    @Excel(name = "易燃易爆品防护", orderNum = "10", width = 15)
    private String explosive;
    @Excel(name = "防蛀检查", orderNum = "11", width = 15)
    private String mothproof;
}