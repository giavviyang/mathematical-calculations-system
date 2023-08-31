package com.fudian.project.ams.pojo;

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
@TableName(value = "transfer_batch")
public class TransferBatch extends BaseEntity implements Serializable {
    /**
     * 转递单号（档案转递文号E01Z707）
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 全宗id
     */
    @TableField(value = "org_id")
    private String orgId;

    /**
     * 转往单位名称
     */
    @TableField(value = "e01z704a")
    private String e01z704a;

    /**
     * 转往单位代码zb02
     */
    @TableField(value = "e01z704b")
    private String e01z704b;

    /**
     * 转递日期
     */
    @TableField(value = "e01z701")
    private String e01z701;

    /**
     * 转出人数
     */
    @TableField(value = "person_num")
    private Integer personNum;

    /**
     * 经办人
     */
    @TableField(value = "e01z717")
    private String e01z717;

    /**
     * 回执人
     */
    @TableField(value = "e01z724")
    private String e01z724;

    /**
     * 回执日期
     */
    @TableField(value = "e01z727")
    private String e01z727;

    /**
     * 备注
     */
    @TableField(value = "ramark")
    private String ramark;

    /**
     * 审批工作流实例
     */
    @TableField(value = "wfi_id")
    private String wfiId;

    /**
     * 审批进度（AMS18)
     */
    @TableField(value = "flag")
    private Integer flag;

    public static final String COL_ID = "id";

    public static final String COL_ORG_ID = "org_id";

    public static final String COL_E01Z704A = "e01z704a";

    public static final String COL_E01Z704B = "e01z704b";

    public static final String COL_E01Z701 = "e01z701";

    public static final String COL_PERSON_NUM = "person_num";

    public static final String COL_E01Z717 = "e01z717";

    public static final String COL_E01Z724 = "e01z724";

    public static final String COL_E01Z727 = "e01z727";

    public static final String COL_RAMARK = "ramark";

    public static final String COL_WFI_ID = "wfi_id";

    public static final String COL_FLAG = "flag";
}
