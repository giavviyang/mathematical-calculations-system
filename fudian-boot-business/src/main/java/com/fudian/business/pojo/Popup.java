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
@TableName(value = "popup")
public class Popup extends BaseEntity implements Serializable {
    /** id */
    @TableId(value = "id")
    private String id;
    /** 知识点id */
    @TableField(value = "classification_id")
    private String classificationId;
    /** 描述内容 */
    @TableField(value = "content")
    private String content;
    /** 例题类型(0题，1双题对立) */
    @TableField(value = "type")
    private Integer type;
    /** 公式1 */
    @TableField(value = "formula_one")
    private String formulaOne;
    /** 公式2 */
    @TableField(value = "formula_two")
    private String formulaTwo;
}
