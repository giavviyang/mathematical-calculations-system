package com.fudian.project.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_permission_data")
public class SysPermissionData extends BaseEntity {
    /**
     * 权限主键
     */
    @TableId(value = "permission_id", type = IdType.INPUT)
    private String permissionId;

    /**
     * 数据权限主键
     */
    @TableId(value = "data_permission_id", type = IdType.INPUT)
    private String dataPermissionId;

    public static final String COL_PERMISSION_ID = "permission_id";

    public static final String COL_DATA_PERMISSION_ID = "data_permission_id";
}
