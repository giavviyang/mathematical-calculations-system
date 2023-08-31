package com.fudian.business.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 关卡知识点关联对象 level_knowledge
 *
 * @author WangHao
 * @date 2022-01-01
 */
@TableName(value = "Level_knowledge")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LevelKnowledge extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 关卡id */
    @TableId(value = "level_id", type = IdType.INPUT)
    private String levelId;

    /** 知识点id */
    @TableField("classification_id")
    private String classificationId;

    /** 知识点百分比 */
    @TableField("percentage")
    private String percentage;

    public void setLevelId(String levelId)
    {
        this.levelId = levelId;
    }

    public String getLevelId()
    {
        return levelId;
    }
    public void setClassificationId(String classificationId)
    {
        this.classificationId = classificationId;
    }

    public String getClassificationId()
    {
        return classificationId;
    }
    public void setPercentage(String percentage)
    {
        this.percentage = percentage;
    }

    public String getPercentage()
    {
        return percentage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("levelId", getLevelId())
            .append("classificationId", getClassificationId())
            .append("percentage", getPercentage())
            .toString();
    }
}
