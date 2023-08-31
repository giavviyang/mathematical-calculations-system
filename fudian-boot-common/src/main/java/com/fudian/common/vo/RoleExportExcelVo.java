package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleExportExcelVo implements Serializable {
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    @Excel(name = "角色名称", orderNum = "0", width = 15)
    private String roleName;
    @Excel(name = "角色描述", orderNum = "1", width = 15)
    private String roleSummary;
    @Excel(name = "创建时间", orderNum = "2", width = 15)
    private String createTime;
    @Excel(name = "创建人", orderNum = "4", width = 15)
    private String createBy;
    @Excel(name = "备注", orderNum = "5", width = 15)
    private String remark;


}