package com.fudian.project.ams.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
  *@author lutao
  *@create 2020/3/16
*/

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "issue_certificate")
public class IssueCertificate extends BaseEntity implements Serializable {
    /**
    * uuid
    */
    @TableField(value = "id")
    private String id;

    /**
    * 干部id
    */
    @TableField(value = "a01_id")
    private String a01Id;

    /**
    * 干部名称
    */
    @Excel(name = "")
    @TableField(value = "a0101")
    private String a0101;

    /**
    * 部门id
    */
    @TableField(value = "dept_id")
    private String deptId;

    /**
    * 部门名称
    */
    @TableField(value = "dept_name")
    private String deptName;

    /**
    * 性别(GB/T2261.1 GB22611)
    */
    @TableField(value = "a0104")
    private String a0104;

    /**
    * 干部状态（FDA01State）
    */
    @TableField(value = "state")
    private String state;

    /**
    * 证件号码
    */
    @TableField(value = "a0184")
    private String a0184;

    /**
    * 参加工作时间a0134
    */
    @TableField(value = "a0134")
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private Date a0134;

    /**
    * 父亲姓名
    */
    @TableField(value = "father")
    private String father;

    /**
    * 母亲姓名
    */
    @TableField(value = "mother")
    private String mother;

    /**
    * 配偶姓名
    */
    @TableField(value = "spouse")
    private String spouse;

    /**
    * 女儿
    */
    @TableField(value = "daughter")
    private String daughter;

    /**
    * 证明日期
    */
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @TableField(value = "prove_date")
    private Date proveDate;

    /**
    * 证明类型
    */
    @TableField(value = "prove_type")
    private String proveType;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_A01_ID = "a01_id";

    public static final String COL_A0101 = "a0101";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_DEPT_NAME = "dept_name";

    public static final String COL_A0104 = "a0104";

    public static final String COL_STATE = "state";

    public static final String COL_A0184 = "a0184";

    public static final String COL_A0134 = "a0134";

    public static final String COL_FATHER = "father";

    public static final String COL_MOTHER = "mother";

    public static final String COL_SPOUSE = "spouse";

    public static final String COL_DAUGHTER = "daughter";

    public static final String COL_PROVE_DATE = "prove_date";

    public static final String COL_PROVE_TYPE = "prove_type";
}
