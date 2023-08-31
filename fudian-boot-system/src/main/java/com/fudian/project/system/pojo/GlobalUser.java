package com.fudian.project.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "global_user")
public class GlobalUser {
    /**
     * 唯一标识
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户类型      1系统内置   2用户定义
     */
    @TableField(value = "user_type")
    private Integer userType;

    /**
     * 登录名
     */
    @TableField(value = "login_code")
    private String loginCode;

    /**
     * 登录密码
     */
    @TableField(value = "login_password")
    private String loginPassword;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 修改人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 用户状态  0启用 1关闭   默认启用
     */
    @TableField(value = "user_status")
    private Integer userStatus;

    /**
     * 所属全宗
     */
    @TableField(value = "org_id")
    private String orgId;

    /**
     * 所属部门
     */
    @TableField(value = "dept_id")
    private String deptId;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_USER_TYPE = "user_type";

    public static final String COL_LOGIN_CODE = "login_code";

    public static final String COL_LOGIN_PASSWORD = "login_password";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_USER_STATUS = "user_status";

    public static final String COL_ORG_ID = "org_id";

    public static final String COL_DEPT_ID = "dept_id";
}
