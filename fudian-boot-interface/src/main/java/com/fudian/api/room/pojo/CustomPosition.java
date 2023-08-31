package com.fudian.api.room.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/1/17 11:24
 * @Desc:
 */
@ApiModel(value = "com-fudian-project-room-pojo-CustomPosition")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "custom_position")
public class CustomPosition {
    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "唯一标识")
    private String id;

    /**
     * 列号
     */
    @TableField(value = "column_number")
    @ApiModelProperty(value = "列号")
    private Integer columnNumber;

    /**
     * 节号
     */
    @TableField(value = "section_number")
    @ApiModelProperty(value = "节号")
    private Integer sectionNumber;

    /**
     * 层号
     */
    @TableField(value = "layer_number")
    @ApiModelProperty(value = "层号")
    private Integer layerNumber;

    /**
     * 列别名
     */
    @TableField(value = "column_nickname")
    @ApiModelProperty(value = "列别名")
    private String columnNickname;

    /**
     * 节别名
     */
    @TableField(value = "section_nickname")
    @ApiModelProperty(value = "节别名")
    private String sectionNickname;

    /**
     * 曾别名
     */
    @TableField(value = "layer_nickname")
    @ApiModelProperty(value = "曾别名")
    private String layerNickname;

    /**
     * 层存放数
     */
    @TableField(value = "layer_num")
    @ApiModelProperty(value = "层存放数")
    private Integer layerNum;

    /**
     * 库房标识
     */
    @TableField(value = "room_id")
    @ApiModelProperty(value = "库房标识")
    private String roomId;

    /**
     * 列级编号
     */
    @TableField(value = "column_code")
    @ApiModelProperty(value = "列级编号")
    private Integer columnCode;

    /**
     * 节级编号
     */
    @TableField(value = "section_code")
    @ApiModelProperty(value = "节级编号")
    private Integer sectionCode;

    /**
     * 层级编号
     */
    @TableField(value = "layer_code")
    @ApiModelProperty(value = "层级编号")
    private Integer layerCode;

    public static final String COL_ID = "id";

    public static final String COL_COLUMN_NUMBER = "column_number";

    public static final String COL_SECTION_NUMBER = "section_number";

    public static final String COL_LAYER_NUMBER = "layer_number";

    public static final String COL_COLUMN_NICKNAME = "column_nickname";

    public static final String COL_SECTION_NICKNAME = "section_nickname";

    public static final String COL_LAYER_NICKNAME = "layer_nickname";

    public static final String COL_LAYER_NUM = "layer_num";

    public static final String COL_ROOM_ID = "room_id";

    public static final String COL_COLUMN_CODE = "column_code";

    public static final String COL_SECTION_CODE = "section_code";

    public static final String COL_LAYER_CODE = "layer_code";
}
