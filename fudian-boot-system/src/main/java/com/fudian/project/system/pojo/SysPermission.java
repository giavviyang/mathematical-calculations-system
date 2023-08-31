package com.fudian.project.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_permission")
public class SysPermission extends BaseEntity {
    /**
     * 权限主键
     */
    @TableId(value = "permission_id", type = IdType.INPUT)
    private String permissionId;

    /**
     * 权限名称
     */
    @TableField(value = "permission_name")
    private String permissionName;

    /**
     * 所属全宗
     */
    @TableField(value = "org_id")
    private String orgId;

    /**
     * 权限状态 权限状态用于控制当前权限启用情况 1为启用 2为关闭
     */
    @TableField(value = "permission_status")
    private Integer permissionStatus;

    /**
     * 数据权限摘要
     */
    @TableField(value = "permission_data_summary")
    private String permissionDataSummary;

    /**
     * 功能权限摘要
     */
    @TableField(value = "permission_menu_summary")
    private String permissionMenuSummary;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;

    /**
     * 修改人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateTime;


}
