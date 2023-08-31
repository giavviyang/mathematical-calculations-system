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
@TableName(value = "student_goods_relation")
public class StudentGoodsRelation implements Serializable {
    /**ID*/
    @TableId(value = "id")
    private String id;
    /**学生ID*/
    @TableId(value = "student_id")
    private String studentId;
    /**商品ID*/
    @TableId(value = "goods_id")
    private String goodsId;


}
