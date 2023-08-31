package com.fudian.business.vo;

import com.alibaba.fastjson.JSONObject;
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

import java.util.List;

/**
 *
 *
 * @author 徐成宏
 * @date 2022-04-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LevelKnowledgeVo {
    /** 知识点名称 */
    private String knowledgeName;
    /** 知识点百分比 */
    private String proportion;
    /** 知识点诊断 */
    private String diagnosisMsg;
    /** 知识点正确率 */
    private int correcRate;
    /** 错题 */
    private List<JSONObject> errorList;

}
