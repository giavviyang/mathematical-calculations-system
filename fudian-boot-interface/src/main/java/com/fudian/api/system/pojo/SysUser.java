package com.fudian.api.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fudian.common.pojo.BaseEntity;
import com.fudian.common.utils.DateTimeFormatSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class SysUser extends BaseEntity implements Serializable {
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户类型 0：系统内置 1：用户定义
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 修改人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 用户状态  0.启用 1.关闭 默认启用
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

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 人员排序
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 人员编号
     */
    @TableField(value = "user_number")
    private String userNumber;

    /**
     * 性别 0女 1男
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 手机号
     */
    @TableField(value = "iphone")
    private String iphone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;
    /**
     * 教师头像
     */
    @TableField(value = "user_portrait")
    private byte[] userPortrait;

    /**
     * 临时用户专属，密码到期时间
     */
    @TableField(value = "login_password_date")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date loginPasswordDate;

    private static final long serialVersionUID = 1L;

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

    public static final String COL_REMARK = "remark";

    public static final String COL_ORDER_NUM = "order_num";

    public static final String COL_USER_NUMBER = "user_number";

    public static final String COL_SEX = "sex";

    public static final String COL_IPHONE = "iphone";

    public static final String COL_EMAIL = "email";

    public static final String COL_LOGIN_PASSWORD_DATE = "login_password_date";
}
