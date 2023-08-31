package com.fudian.project.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "transfer_bill")
public class TransferBill extends BaseEntity implements Serializable {
    /**
     * 转递单号
     */
    @TableId(value = "trans_id", type = IdType.INPUT)
    private String transId;

    /**
     * 序号
     */
    @TableId(value = "trans_bill_index", type = IdType.INPUT)
    private Integer transBillIndex;

    /**
     * 人员信息id
     */
    @TableField(value = "a01_id")
    private String a01Id;

    /**
     * 人员姓名
     */
    @TableField(value = "a0101")
    private String a0101;

    /**
     * 全宗名称（所属单位）
     */
    @TableField(value = "org_name")
    private String orgName;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    private String deptId;

    /**
     * 职务
     */
    @TableField(value = "duty")
    private String duty;

    /**
     * 转递原因
     */
    @TableField(value = "e01z721")
    private String e01z721;

    /**
     * 转出正本数
     */
    @TableField(value = "e01z711")
    private Integer e01z711;

    /**
     * 转出副本数
     */
    @TableField(value = "e01z714")
    private Integer e01z714;

    /**
     * 档案材料（份）
     */
    @TableField(value = "mat_num")
    private Integer matNum;

    /**
     * 备注
     */
    @TableField(value = "e01z731")
    private String e01z731;

    private static final long serialVersionUID = 1L;

    public static final String COL_TRANS_ID = "trans_id";

    public static final String COL_TRANS_BILL_INDEX = "trans_bill_index";

    public static final String COL_A01_ID = "a01_id";

    public static final String COL_A0101 = "a0101";

    public static final String COL_ORG_NAME = "org_name";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_DUTY = "duty";

    public static final String COL_E01Z721 = "e01z721";

    public static final String COL_E01Z711 = "e01z711";

    public static final String COL_E01Z714 = "e01z714";

    public static final String COL_MAT_NUM = "mat_num";

    public static final String COL_E01Z731 = "e01z731";
}
