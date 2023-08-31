package com.fudian.business.vo;

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
public class Goods implements Serializable {
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品图片地址
     */
    private String goodsImge;
    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品描述
     */
    private String goodsDescribe;
    /**
     * 购买状态 0未购买 1已购买
     */
    private int purchaseStatus;
    /**
     * 金币数
     */
    private Integer goodsCoins;
    /**
     * 商品类型
     */
    private Integer goodsType;

}
