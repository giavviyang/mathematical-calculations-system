package com.fudian.project.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value="com-fudian-project-system-pojo-SysRolePermission")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role_permission")
public class SysRolePermission extends BaseEntity implements Serializable {
    /**
     * 角色id
     */
    @TableId(value = "role_id", type = IdType.INPUT)
    @ApiModelProperty(value="角色id")
    private String roleId;

    /**
     * 权限Id
     */
    @TableId(value = "permission_id", type = IdType.INPUT)
    @ApiModelProperty(value="权限Id")
    private String permissionId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_PERMISSION_ID = "permission_id";
}
