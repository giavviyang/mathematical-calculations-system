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
@TableName(value = "e01z5")
public class E01z5 extends BaseEntity implements Serializable {
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
     * 收件日期
     */
    @TableField(value = "e01z501")
    private String e01z501;

    /**
     * 收件文号
     */
    @TableField(value = "e01z504")
    private String e01z504;

    /**
     * 来件单位名称
     */
    @TableField(value = "e01z507a")
    private String e01z507a;

    /**
     * 来件单位代码zb02
     */
    @TableField(value = "e01z507b")
    private String e01z507b;

    /**
     * 正本卷数
     */
    @TableField(value = "e01z511")
    private Integer e01z511;

    /**
     * 副本卷书
     */
    @TableField(value = "e01z514")
    private Integer e01z514;

    /**
     * 经办人
     */
    @TableField(value = "e01z517")
    private String e01z517;

    /**
     * 审核人
     */
    @TableField(value = "e01z521")
    private String e01z521;

    /**
     * 审核日期
     */
    @TableField(value = "e01z524")
    private String e01z524;

    /**
     * 案卷质量0不合格；1合格；2优秀
     */
    @TableField(value = "e01z527")
    private String e01z527;

    /**
     * 回执日期
     */
    @TableField(value = "e01z531")
    private String e01z531;

    /**
     * 入库日期
     */
    @TableField(value = "e01z534")
    private String e01z534;

    /**
     * 入库审批人
     */
    @TableField(value = "e01z537")
    private String e01z537;

    /**
     * 档案位置xx柜xx层xx号
     */
    @TableField(value = "e01z541")
    private String e01z541;

    /**
     * 备注
     */
    @TableField(value = "e01z544")
    private String e01z544;

    /**
     * 原职务
     */
    @TableField(value = "duty")
    private String duty;

    /**
     * 转入原因
     */
    @TableField(value = "reason")
    private String reason;

    /**
     * 内部转递单(BIZ_INNER_TRANSFER.ID或上传扫描件存入BIZ_OPER_EFILE表)
     */
    @TableField(value = "trans_id")
    private String transId;

    /**
     * 审核评分
     */
    @TableField(value = "score")
    private Integer score;

    /**
     * 档案整理遗留问题
     */
    @TableField(value = "problem")
    private String problem;

    /**
     * 提任副本制作与否
     */
    @TableField(value = "is_copy")
    private Integer isCopy;

    /**
     * 副本存放处
     */
    @TableField(value = "copy_save")
    private String copySave;

    /**
     * 材料缺失情况
     */
    @TableField(value = "lack")
    private String lack;

    /**
     * 档案目录电子版(0无1有)
     */
    @TableField(value = "catalog")
    private Integer catalog;

    /**
     * 转入部门
     */
    @TableField(value = "dept_id")
    private String deptId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ORG_ID = "org_id";

    public static final String COL_A01_ID = "a01_id";

    public static final String COL_E01Z501 = "e01z501";

    public static final String COL_E01Z504 = "e01z504";

    public static final String COL_E01Z507A = "e01z507a";

    public static final String COL_E01Z507B = "e01z507b";

    public static final String COL_E01Z511 = "e01z511";

    public static final String COL_E01Z514 = "e01z514";

    public static final String COL_E01Z517 = "e01z517";

    public static final String COL_E01Z521 = "e01z521";

    public static final String COL_E01Z524 = "e01z524";

    public static final String COL_E01Z527 = "e01z527";

    public static final String COL_E01Z531 = "e01z531";

    public static final String COL_E01Z534 = "e01z534";

    public static final String COL_E01Z537 = "e01z537";

    public static final String COL_E01Z541 = "e01z541";

    public static final String COL_E01Z544 = "e01z544";

    public static final String COL_DUTY = "duty";

    public static final String COL_REASON = "reason";

    public static final String COL_TRANS_ID = "trans_id";

    public static final String COL_SCORE = "score";

    public static final String COL_PROBLEM = "problem";

    public static final String COL_IS_COPY = "is_copy";

    public static final String COL_COPY_SAVE = "copy_save";

    public static final String COL_LACK = "lack";

    public static final String COL_CATALOG = "catalog";

    public static final String COL_DEPT_ID = "dept_id";
}
