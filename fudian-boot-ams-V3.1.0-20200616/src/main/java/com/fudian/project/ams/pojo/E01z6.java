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
@TableName(value = "e01z6")
public class E01z6 implements Serializable {
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
     * 整理日期
     */
    @TableField(value = "e01z601")
    private String e01z601;

    /**
     * 正本数
     */
    @TableField(value = "e01z604")
    private Integer e01z604;

    /**
     * 副本数
     */
    @TableField(value = "e01z607")
    private Integer e01z607;

    /**
     * 整理人
     */
    @TableField(value = "e01z611")
    private String e01z611;

    /**
     * 审核人
     */
    @TableField(value = "e01z614")
    private String e01z614;

    /**
     * 验收人
     */
    @TableField(value = "checker")
    private String checker;

    /**
     * 备注
     */
    @TableField(value = "e01z617")
    private String e01z617;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ORG_ID = "org_id";

    public static final String COL_A01_ID = "a01_id";

    public static final String COL_E01Z601 = "e01z601";

    public static final String COL_E01Z604 = "e01z604";

    public static final String COL_E01Z607 = "e01z607";

    public static final String COL_E01Z611 = "e01z611";

    public static final String COL_E01Z614 = "e01z614";

    public static final String COL_CHECKER = "checker";

    public static final String COL_E01Z617 = "e01z617";
}
