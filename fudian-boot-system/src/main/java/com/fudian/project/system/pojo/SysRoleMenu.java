package com.fudian.project.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/13 12:37
 * @Desc:
 */
@Data
@TableName(value = "sys_role_menu")
public class SysRoleMenu implements Serializable {
    /**
     * 角色id
     */
    @TableId(value = "role_id", type = IdType.INPUT)
    private String roleId;

    /**
     * 数据权限主键
     */
    @TableId(value = "menu_id", type = IdType.INPUT)
    private String menuId;

    private static final long serialVersionUID = 1L;
}
