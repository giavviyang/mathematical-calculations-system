package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description TODO
 * @ClassName RoomExportExcelVo
 * @Author mr.zhang
 * @Date 2020/4/3 16:41
 * @Version 1.0.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class RoomExportExcelVo implements Serializable {
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    @Excel(name = "库房名称", orderNum = "0", width = 15)
    private String roomName;
    @Excel(name = "库房编号", orderNum = "1", width = 15)
    private String code;
    @Excel(name = "库房面积", orderNum = "2", width = 15)
    private String roomArea;
    @Excel(name = "库房地址", orderNum = "3", width = 15)
    private String roomLocation;
    @Excel(name = "库房管理员", orderNum = "4", width = 15)
    private String roomManager;
    @Excel(name = "总列数", orderNum = "5", width = 15)
    private String columnNum;
    @Excel(name = "总节数", orderNum = "6", width = 15)
    private String sectionNum;
    @Excel(name = "总层数", orderNum = "7", width = 15)
    private String layerNum;
    @Excel(name = "已占用", orderNum = "8", width = 15)
    private String curNum;
    @Excel(name = "剩余容量", orderNum = "9", width = 15)
    private String allowance;
    @Excel(name = "总容量", orderNum = "10", width = 15)
    private String znum;

    public String getColumnNum() {
        return columnNum+"列";
    }

    public String getSectionNum() {
        return sectionNum+"节";
    }

    public String getLayerNum() {
        return layerNum+"层";
    }

    public String getCurNum() {
        return curNum+"卷";
    }

    public String getAllowance() {
        return allowance+"卷";
    }

    public String getZnum() {
        return String.valueOf((Integer.valueOf(this.curNum) + Integer.valueOf(this.allowance)))+"卷";
    }
}
