package com.fudian.api.room.pojo;

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
@TableName(value = "custom_position_a01")
public class CustomPositionA01 implements Serializable {
    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 库房位置id
     */
    @TableField(value = "position_id")
    private String positionId;

    /**
     * 干部id
     */
    @TableField(value = "a01_id")
    private String a01Id;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_POSITION_ID = "position_id";

    public static final String COL_A01_ID = "a01_id";

    public static final String COL_STATUS = "status";
}
