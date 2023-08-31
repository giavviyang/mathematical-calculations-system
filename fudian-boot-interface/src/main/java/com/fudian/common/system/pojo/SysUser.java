package com.fudian.common.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@ApiModel(value = "LoginUser")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "SYS_USER")
public class SysUser  implements Serializable {
    /**
     * 主键id
     */
    @TableField(value = "ID")
    @ApiModelProperty(value = "主键id")
    private String id;

    /**
     * 组织机构ID
     */
    @TableField(value = "ORG_ID")
    @ApiModelProperty(value = "组织机构ID")
    private String orgId;

    /**
     * 登录账号
     */
    @TableField(value = "USERNAME")
    @ApiModelProperty(value = "登录账号")
    private String username;

    /**
     * 昵称
     */
    @TableField(value = "NICKNAME")
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 密码
     */
    @TableField(value = "PASSWORD")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 头像
     */
    @TableField(value = "AVATAR")
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 生日
     */
    @TableField(value = "BIRTHDAY")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    @TableField(value = "SEX")
    @ApiModelProperty(value = "性别(0-默认未知,1-男,2-女)")
    private Integer sex;

    /**
     * 电子邮件
     */
    @TableField(value = "EMAIL")
    @ApiModelProperty(value = "电子邮件")
    private String email;

    /**
     * 电话
     */
    @TableField(value = "PHONE")
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 机构编码
     */
    @TableField(value = "DEPT_ID")
    @ApiModelProperty(value = "机构编码")
    private String deptId;

    /**
     * 状态(-1：已删除，0：禁用，1：正常)
     */
    @TableField(value = "STATUS")
    @ApiModelProperty(value = "状态(-1：已删除，0：禁用，1：正常)")
    private Integer status;

    /**
     * 工号，唯一键
     */
    @TableField(value = "WORK_NO")
    @ApiModelProperty(value = "工号，唯一键")
    private String workNo;

    /**
     * 职务，关联职务表
     */
    @TableField(value = "POST")
    @ApiModelProperty(value = "职务，关联职务表")
    private String post;

    /**
     * 创建人
     */
    @TableField(value = "CREATE_BY")
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "UPDATE_BY")
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    public static boolean isAdmin(String userId) {
        return "admin".equals(userId) ;
    }

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "ID";

    public static final String COL_ORG_ID = "ORG_ID";

    public static final String COL_USERNAME = "USERNAME";

    public static final String COL_NICKNAME = "NICKNAME";

    public static final String COL_PASSWORD = "PASSWORD";

    public static final String COL_AVATAR = "AVATAR";

    public static final String COL_BIRTHDAY = "BIRTHDAY";

    public static final String COL_SEX = "SEX";

    public static final String COL_EMAIL = "EMAIL";

    public static final String COL_PHONE = "PHONE";

    public static final String COL_DEPT_ID = "DEPT_ID";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_WORK_NO = "WORK_NO";

    public static final String COL_POST = "POST";

    public static final String COL_CREATE_BY = "CREATE_BY";

    public static final String COL_CREATE_TIME = "CREATE_TIME";

    public static final String COL_UPDATE_BY = "UPDATE_BY";

    public static final String COL_UPDATE_TIME = "UPDATE_TIME";

}
