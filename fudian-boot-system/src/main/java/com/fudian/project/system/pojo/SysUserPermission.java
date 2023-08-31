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
@TableName(value = "sys_user_permission")
public class SysUserPermission  extends BaseEntity {
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;

    /**
     * 权限id
     */
    @TableId(value = "permission_id", type = IdType.INPUT)
    private String permissionId;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_PERMISSION_ID = "permission_id";
}
