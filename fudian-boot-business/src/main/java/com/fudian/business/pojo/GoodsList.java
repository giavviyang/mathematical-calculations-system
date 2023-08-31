package com.fudian.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuchenghong
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "goods_list")
public class GoodsList extends BaseEntity implements Serializable {
    /**
     * 商品id
     */
    @TableId(value = "goods_id")
    private String goodsId;
    /**
     * 商品名称
     */
    @TableField(value = "goods_name")
    private String goodsName;
    /**
     * 商品图片id
     */
    @TableField(value = "goods_imge")
    private String goodsImge;
    /**
     * 金币数量 (价格)
     */
    @TableField(value = "goods_coins")
    private int goodsCoins;
    /**
     * 商品数量
     */
    @TableField(value = "goods_num")
    private Integer goodsNum;

    /**
     * 商品类型 0头像框
     */
    @TableField(value = "goods_type")
    private int goodsType;
    /**
     * 商品描述
     */
    @TableField(value = "goods_describe")
    private String goodsDescribe;

}
