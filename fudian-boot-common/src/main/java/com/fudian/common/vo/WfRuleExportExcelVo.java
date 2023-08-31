package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class WfRuleExportExcelVo implements Serializable {
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    /**
     * 制度名称
     */
    @Excel(name = "制度名称",  width = 15)
    private String ruleName;

    /**
     * 文号
     */
    @Excel(name = "文号", width = 15)
    private String ruleNumber;

    /**
     * 制度类型
     */
    @Excel(name = "制度类型", width = 15)
    private String ruleType;

    /**
     * 执行情况 0现行有效   1已废止
     */
    @Excel(name = "执行情况",  width = 15)
    private String ruleStatus;

    public String getRuleStatus() {
        if (ruleStatus.equals("0")) {
            return ruleStatus = "现行有效";
        } else if (ruleStatus.equals("1")) {
            return ruleStatus = "已废止";
        } else {
            return ruleStatus;
        }
    }

    /**
     * 文件注释
     */
    @Excel(name = "文件注释", width = 15)
    private String ruleRemark;

}
