package com.fudian.project.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fudian.common.pojo.BaseEntity;
import com.fudian.common.utils.DateTimeFormatSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role")
public class SysRole extends BaseEntity {
    /**
     * 角色主键
     */
    @TableId(value = "role_id", type = IdType.INPUT)
    private String roleId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 机构全宗号
     */
    @TableField(value = "org_id")
    private String orgId;

    /**
     * 角色状态用于控制当前角色启用情况 1为启用 2为关闭
     */
    @TableField(value = "role_status")
    private Integer roleStatus;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    private Date createTime;

    /**
     * 角色号                                                                                               建立的用户组的编号 用户组前600为系统角色，600-1000为内置角色，1000以上为自定义角色
     */
    @TableField(value = "role_num")
    private String roleNum;

    /**
     * 是否为系统内置  0为是1为否
     */
    @TableField(value = "is_system")
    private Integer isSystem;

    /**
     * 角色摘要
     */
    @TableField(value = "role_summary")
    private String roleSummary;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
    /**
     * 学校id
     */
    @TableField(value = "school_id")
    private String schoolId;
    /**
     * 学校名称
     */
    @TableField(value = "school_name")
    private String schoolName;



}
