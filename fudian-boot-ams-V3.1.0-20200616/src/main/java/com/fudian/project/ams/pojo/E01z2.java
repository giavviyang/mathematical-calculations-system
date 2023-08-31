package com.fudian.project.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "e01z2")
public class E01z2 extends BaseEntity {
    /**
     * uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 隶属全宗
     */
    @TableField(value = "org_id")
    private String orgId;

    /**
     * 人员信息ID
     */
    @TableField(value = "a01_id")
    private String a01Id;

    /**
     * 收件日期(新增)
     */
    @TableField(value = "e01z201")
    private String e01z201;

    /**
     * 来件单位名称(材料来源)
     */
    @TableField(value = "e01z204a")
    private String e01z204a;

    /**
     * 来件单位代码(ZB02)
     */
    @TableField(value = "e01z204b")
    private String e01z204b;

    /**
     * 接收人(经手人)
     */
    @TableField(value = "e01z207")
    private String e01z207;

    /**
     * 材料类号(新增)(zb97)
     */
    @TableField(value = "e01z211")
    private String e01z211;

    /**
     * 材料序号(序号)
     */
    @TableField(value = "e01z214")
    private String e01z214;

    /**
     * 材料编号(流转单号)
     */
    @TableField(value = "e01z217")
    private String e01z217;

    /**
     * 材料名称
     */
    @TableField(value = "e01z221a")
    private String e01z221a;

    /**
     * 材料名称代码zb98
     */
    @TableField(value = "e01z221b")
    private String e01z221b;

    /**
     * 页数(新增)
     */
    @TableField(value = "e01z224")
    private Integer e01z224;

    /**
     * 材料制成日期(新增)
     */
    @TableField(value = "e01z227")
    private String e01z227;

    /**
     * 备注(新增)
     */
    @TableField(value = "e01z231")
    private String e01z231;

    /**
     * 份数(新增)
     */
    @TableField(value = "e01z234")
    private Integer e01z234;

    /**
     * 材料处理标识(新增)1待整理；2待退回
     */
    @TableField(value = "e01z237")
    private String e01z237;

    /**
     * 零散材料序号(新增)
     */
    @TableField(value = "e01z241")
    private String e01z241;

    /**
     * 是否已处理标识(新增)1是；0否
     */
    @TableField(value = "e01z244")
    private String e01z244;

    /**
     * 归档人
     */
    @TableField(value = "archive")
    private String archive;

    /**
     * 批次编号
     */
    @TableField(value = "batch_number")
    private String batchNumber;

    public static final String COL_ID = "id";

    public static final String COL_ORG_ID = "org_id";

    public static final String COL_A01_ID = "a01_id";

    public static final String COL_E01Z201 = "e01z201";

    public static final String COL_E01Z204A = "e01z204a";

    public static final String COL_E01Z204B = "e01z204b";

    public static final String COL_E01Z207 = "e01z207";

    public static final String COL_E01Z211 = "e01z211";

    public static final String COL_E01Z214 = "e01z214";

    public static final String COL_E01Z217 = "e01z217";

    public static final String COL_E01Z221A = "e01z221a";

    public static final String COL_E01Z221B = "e01z221b";

    public static final String COL_E01Z224 = "e01z224";

    public static final String COL_E01Z227 = "e01z227";

    public static final String COL_E01Z231 = "e01z231";

    public static final String COL_E01Z234 = "e01z234";

    public static final String COL_E01Z237 = "e01z237";

    public static final String COL_E01Z241 = "e01z241";

    public static final String COL_E01Z244 = "e01z244";

    public static final String COL_ARCHIVE = "archive";

    public static final String COL_BATCH_NUMBER = "batch_number";
}
