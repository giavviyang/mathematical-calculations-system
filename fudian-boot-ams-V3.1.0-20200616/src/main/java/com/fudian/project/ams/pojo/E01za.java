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
@TableName(value = "e01za")
public class E01za implements Serializable {
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
     * 档案卷本号(新增)
     */
    @TableField(value = "e01za01")
    private String e01za01;

    /**
     * 档案条形码号(新增)
     */
    @TableField(value = "e01za04")
    private String e01za04;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ORG_ID = "org_id";

    public static final String COL_A01_ID = "a01_id";

    public static final String COL_E01ZA01 = "e01za01";

    public static final String COL_E01ZA04 = "e01za04";
}
