package com.fudian.common.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserExportExcelVo implements Serializable {
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15 , type = 10)
    private Integer xh;
    @Excel(name = "教师姓名", width = 15)
    private String userName;
    @Excel(name = "手机号",  width = 15)
    private String iphone;
    @Excel(name = "登录账号",  width = 15)
    private String loginCode;
    @Excel(name = "学校",  width = 15)
    private String deptName;
    @Excel(name = "角色身份",  width = 15)
    private String roleName;
    @Excel(name = "用户状态", width = 15)
    private String userStatus;

    public String getUserStatus() {
        if(userStatus.equals("0")){
            return "启用";
        }else if(userStatus.equals("1")){
            return "禁用";
        }
        return userStatus;
    }
}