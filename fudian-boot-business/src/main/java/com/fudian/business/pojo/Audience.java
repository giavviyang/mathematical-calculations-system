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
@TableName(value = "audience")
public class Audience extends BaseEntity implements Serializable {
    /**
     * 测试表主键
     */
    @TableField(value = "test_id")
    private String testId;

    /**
     * 根据受众人员类型关联学校，年级，班级，个人id
     */
    @TableField(value = "relation_id")
    private String relationId;
}
