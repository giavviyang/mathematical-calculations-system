package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class StorageConfigExportExcelVo implements Serializable {
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    @Excel(name = "存储名称",  width = 15)
    private String fileName;
    @Excel(name = "存储方式",  width = 15)
    private String fileType;
    @Excel(name = "ip地址",  width = 15)
    private String ip;
    @Excel(name = "端口号",  width = 15)
    private String port;
    @Excel(name = "存放路径",  width = 15)
    private String file;
    @Excel(name = "绑定文件",  width = 15)
    private String bangFc;
    @Excel(name = "是否启用",  width = 15)
    private String isEnabled;
    @Excel(name = "创建时间",  width = 15)
    private String createTime;
    @Excel(name = "创建人姓名",  width = 15)
    private String createName;
    @Excel(name = "更新时间",  width = 15)
    private String updateTime;
    @Excel(name = "更新人姓名",  width = 15)
    private String updateName;


}