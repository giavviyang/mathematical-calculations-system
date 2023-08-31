package com.fudian.api.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_data_permission")
public class SysDataPermission extends BaseEntity implements Serializable {
    /**
     * 数据权限标识  主键ID
     */
    @TableId(value = "data_permissions_id", type = IdType.INPUT)
    private String dataPermissionsId;

    /**
     * 数据权限名称
     */
    @TableField(value = "data_permissions_name")
    private String dataPermissionsName;

    /**
     * 表id
     */
    @TableField(value = "table_id")
    private String tableId;

    /**
     * 数据id
     */
    @TableField(value = "data_id")
    private String dataId;

    /**
     * 数据权限种类  0为机构 1为部门 2为人员 3为分类  4为条目 5为资料
     */
    @TableField(value = "data_permissions_type")
    private String dataPermissionsType;

    /**
     * 当前业务标识设置在字典表中，字典表中区分档案/业务 1 为干部 2为综合
     */
    @TableField(value = "data_permissions_identity")
    private String dataPermissionsIdentity;

    /**
     * 数据权限描述
     */
    @TableField(value = "data_permission_summary")
    private String dataPermissionSummary;

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
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    public static final String COL_DATA_PERMISSIONS_ID = "data_permissions_id";

    public static final String COL_DATA_PERMISSIONS_NAME = "data_permissions_name";

    public static final String COL_TABLE_ID = "table_id";

    public static final String COL_DATA_PERMISSIONS_TYPE = "data_permissions_type";

    public static final String COL_DATA_PERMISSIONS_IDENTITY = "data_permissions_identity";

    public static final String COL_DATA_PERMISSION_SUMMARY = "data_permission_summary";

    public static final String COL_REMARK = "remark";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_UPDATE_TIME = "update_time";
}
