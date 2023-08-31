package com.fudian.api.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "e01z1")
public class E01z1 extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识(uuid)
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
     * 材料类号(ZB97)
     */
    @TableField(value = "e01z101")
    private String e01z101;

    /**
     * 材料序号
     */
    @TableField(value = "e01z104")
    private Integer e01z104;

    /**
     * 材料编号
     */
    @TableField(value = "e01z107")
    private String e01z107;

    /**
     * 材料名称
     */
    @TableField(value = "e01z111a")
    private String e01z111a;

    /**
     * 材料名称代码(ZB98)
     */
    @TableField(value = "e01z111b")
    private String e01z111b;

    /**
     * 页数
     */
    @TableField(value = "e01z114")
    private Integer e01z114;

    /**
     * 材料制成日期
     */
    @TableField(value = "e01z117")
    private String e01z117;

    /**
     * 备注
     */
    @TableField(value = "e01z121")
    private String e01z121;

    /**
     * 份数
     */
    @TableField(value = "e01z124")
    private Integer e01z124;

    /**
     * 档案卷本号
     */
    @TableField(value = "e01z127")
    private String e01z127;

    /**
     * 档案条形码号
     */
    @TableField(value = "e01z131")
    private String e01z131;

    /**
     * 材料状态0:正常状态1:假删除
     */
    @TableField(value = "efile_flag")
    private String efileFlag;

    @TableField(value = "update_by")
    private String updateBy;

    @TableField(value = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 材料制成日期（年）
     */
    @TableField(value = "e01z117_year")
    private String e01z117Year;

    /**
     * 材料制成日期（月）
     */
    @TableField(value = "e01z117_month")
    private String e01z117Month;

    /**
     * 材料制成日期（日）
     */
    @TableField(value = "e01z117_day")
    private String e01z117Day;

    public static final String COL_ID = "id";

    public static final String COL_ORG_ID = "org_id";

    public static final String COL_A01_ID = "a01_id";

    public static final String COL_E01Z101 = "e01z101";

    public static final String COL_E01Z104 = "e01z104";

    public static final String COL_E01Z107 = "e01z107";

    public static final String COL_E01Z111A = "e01z111a";

    public static final String COL_E01Z111B = "e01z111b";

    public static final String COL_E01Z114 = "e01z114";

    public static final String COL_E01Z117 = "e01z117";

    public static final String COL_E01Z121 = "e01z121";

    public static final String COL_E01Z124 = "e01z124";

    public static final String COL_E01Z127 = "e01z127";

    public static final String COL_E01Z131 = "e01z131";

    public static final String COL_EFILE_FLAG = "efile_flag";

//    public static final String COL_UPDATE_TIME = "update_time";

//    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_E01Z117_YEAR = "e01z117_year";

    public static final String COL_E01Z117_MONTH = "e01z117_month";

    public static final String COL_E01Z117_DAY = "e01z117_day";
}
