package com.fudian.api.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_dept")
public class SysDept extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 11222111L;
    /**
     * 部门id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 父部门id
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 祖级列表
     */
    @TableField(value = "ancestors")
    private String ancestors;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    private String deptName;

    /**
     * 显示顺序
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 负责人
     */
    @TableField(value = "leader")
    private String leader;

    /**
     * 联系电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    @TableField(value = "status")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableField(value = "del_flag")
    private String delFlag;

    /**
     * 部门名称拼音码
     */
    @TableField(value = "dept_pym")
    private String deptPym;

    /**
     * 部门树类型（0机构管理    1档案机构管理）
     */
    @TableField(value = "dept_type")
    private Integer deptType;

    /**
     * 部门编号
     */
    @TableField(value = "dept_number")
    private String deptNumber;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
    /**
     * 学校地址
     */
    @TableField(value = "address")
    private String address;
    /**
     * 座机
     */
    @TableField(value = "landline")
    private String landline;

    /**
     * 节点类型
     */
    @TableField(value = "node_type")
    private String nodeType;

    public static final String COL_ID = "id";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_ANCESTORS = "ancestors";

    public static final String COL_DEPT_NAME = "dept_name";

    public static final String COL_ORDER_NUM = "order_num";

    public static final String COL_LEADER = "leader";

    public static final String COL_PHONE = "phone";

    public static final String COL_EMAIL = "email";

    public static final String COL_STATUS = "status";

    public static final String COL_DEL_FLAG = "del_flag";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_DEPT_PYM = "dept_pym";

    public static final String COL_DEPT_TYPE = "dept_type";

    public static final String COL_DEPT_NUMBER = "dept_number";

    public static final String COL_REMARK = "remark";
}
