package com.fudian.project.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "e01z7")
public class E01z7 implements Serializable {
    /**
     * uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 主表(A38.ID、BIZ_SEND_BILL.ID)
     */
    @TableField(value = "send_id")
    private String sendId;

    /**
     * 人员信息ID
     */
    @TableField(value = "A01_ID")
    private String a01Id;

    /**
     * 转递日期(冗余)
     */
    @TableField(value = "E01Z701")
    private String e01z701;

    /**
     * 转往单位名称(冗余)
     */
    @TableField(value = "E01Z704A")
    private String e01z704a;

    /**
     * 转往单位代码ZB02(冗余)
     */
    @TableField(value = "E01Z704B")
    private String e01z704b;

    /**
     * 档案转递文号(冗余)
     */
    @TableField(value = "E01Z707")
    private String e01z707;

    /**
     * 转出正本数
     */
    @TableField(value = "E01Z711")
    private Integer e01z711;

    /**
     * 转出副本数
     */
    @TableField(value = "E01Z714")
    private Integer e01z714;

    /**
     * 经办人(冗余)
     */
    @TableField(value = "E01Z717")
    private String e01z717;

    /**
     * 转递原因
     */
    @TableField(value = "E01Z721")
    private String e01z721;

    /**
     * 回执人
     */
    @TableField(value = "E01Z724")
    private String e01z724;

    /**
     * 回执日期
     */
    @TableField(value = "E01Z727")
    private String e01z727;

    /**
     * 备注
     */
    @TableField(value = "E01Z731")
    private String e01z731;

    /**
     * 材料总份数
     */
    @TableField(value = "MAT_SUM")
    private Integer matSum;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_SEND_ID = "send_id";

    public static final String COL_A01_ID = "A01_ID";

    public static final String COL_E01Z701 = "E01Z701";

    public static final String COL_E01Z704A = "E01Z704A";

    public static final String COL_E01Z704B = "E01Z704B";

    public static final String COL_E01Z707 = "E01Z707";

    public static final String COL_E01Z711 = "E01Z711";

    public static final String COL_E01Z714 = "E01Z714";

    public static final String COL_E01Z717 = "E01Z717";

    public static final String COL_E01Z721 = "E01Z721";

    public static final String COL_E01Z724 = "E01Z724";

    public static final String COL_E01Z727 = "E01Z727";

    public static final String COL_E01Z731 = "E01Z731";

    public static final String COL_MAT_SUM = "MAT_SUM";
}
