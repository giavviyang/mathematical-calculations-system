package com.fudian.business.pojo;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fudian.business.vo.LevelKnowledgeVo;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xuchenghong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "student_knowledge_relation")
public class StudentKnowledgeRelation extends BaseEntity implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private String id;
    /**
     * 学生id
     */
    @TableField(value = "student_id")
    private String studentId;
    /**
     * 学生id
     */
    @TableField(value = "student_name")
    private String studentName;
    /**
     * 关联id
     */
    @TableField(value = "relation_id")
    private String relationId;
    /**
     * 关联名称
     */
    @TableField(value = "relation_name")
    private String relationName;
    /**
     * 关联类型 0知识点 1闯关
     */
    @TableField(value = "relation_type")
    private int relationType;
    /**
     * 是否掌握 0未掌握 1已掌握
     */
    @TableField(value = "is_master")
    private int isMaster;
    /**
     * 锁定类型 0未锁定，1错5次锁，2对5次锁
     */
    @TableField(value = "is_lock")
    private int isLock;
    /**
     * 知识点,闯关：历史最高分
     */
    @TableField(value = "highest_score")
    private int highestScore;
    /**
     * 历史发放最高金币数量
     */
    @TableField(value = "highest_coins_num")
    private int highestCoinsNum;
    /**
     * 错误次数
     */
    @TableField(value = "error_num")
    private int errorNum;
    /**
     * 正确次数
     */
    @TableField(value = "correct_num")
    private int correctNum;
    /**
     * 班级id 节点id
     */
    @TableField(value = "dept_id")
    private String deptId;

    /**
     * 锁定时间
     */
    @TableField(value = "lock_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lockTime;

    /**
     * 知识点错题本错题
     */
    @TableField(exist = false)
    private List<JSONObject> errorList;

    /**
     * 正确率
     */
    @TableField(exist = false)
    private int correcRate;
    /**
     * 诊断信息
     */
    @TableField(exist = false)
    private String diagnosisMsg;
    /**
     * 关卡关联知识点相关信息
     */
    @TableField(exist = false)
    private List<LevelKnowledgeVo> list;
}

