package com.fudian.project.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_permission_menu")
public class SysPermissionMenu implements Serializable {
    /**
     * 权限id
     */
    @TableId(value = "permission_id", type = IdType.INPUT)
    private String permissionId;

    /**
     * 按钮权限id
     */
    @TableId(value = "menu_id", type = IdType.INPUT)
    private String menuId;

    public static final String COL_PERMISSION_ID = "permission_id";

    public static final String COL_MENU_ID = "menu_id";
}
