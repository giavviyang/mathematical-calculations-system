package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class WfTemplateExportExcelVo implements Serializable {
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    /**
     * 模板名称
     */
    @Excel(name = "模板名称",  width = 15)
    private String templateName;

    /**
     * 使用情况 0正在使用   1已停用
     */
    @Excel(name = "使用情况",  width = 15)
    private String templateStatus;

    public void setTemplateStatus(String templateStatus) {
        if ("0".equals(templateStatus)) {
            this.templateStatus = "正在使用";
        } else if ("1".equals(templateStatus)) {
            this.templateStatus = "已停用";
        } else {
            this.templateStatus = templateStatus;
        }
    }
}
