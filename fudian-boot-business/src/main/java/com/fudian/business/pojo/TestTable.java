package com.fudian.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName(value = "test_table")
public class TestTable extends BaseEntity implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private String id;
    /**
     * 课堂测试名称
     */
    @TableField(value = "test_name")
    private String testName;
    /**
     * 是否发布 1-未发布 ，2已发布
     */
    @TableField(value = "is_release")
    private Integer isRelease;
    /**
     * 出题方式 1-统一（都做相同的题），2-随机（每个人随机出题）
     */
    @TableField(value = "topic_mode")
    private Integer topicMode;
    /**
     * 出题数量
     */
    @TableField(value = "test_topic_num")
    private Integer testTopicNum;
    /**
     * 答题计时
     */
    @TableField(value = "count_time")
    private Integer countTime;
    /* *//**
     * 受众人员 1-个人，2-班级年级，3-学校
     *//*
    @TableField(value = "type")
    private Integer type;*/
    /**
     * 创建人
     */
    @TableField(value = "creator")
    private String creator;
    /**
     * 创建人id
     */
    @TableField(value = "creator_id")
    private String creatorId;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 修改人
     */
    @TableField(value = "modifier")
    private String modifier;
    /**
     * 修改时间
     */
    @TableField(value = "modify_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    /**
     * 关联知识点id
     */
    @TableField(exist = false)
    private List<TestKnowledgeRelation> classification;
    /**
     * 班级id
     */
    @TableField(exist = false)
    private String classId;
    /**
     * 受众D班级id集合
     */
    @TableField(exist = false)
    private List<String> classRoomId;
    /**
     * 受众班级名称集合
     */
    @TableField(exist = false)
    private List<String> classRoomName;
    /*public void setClassRoomId(List<String> classRoomId){
        this.classRoomId = classRoomId;
    }
    public void setClassRoomId(String classRoomId){
        String[] split = classRoomId.split(",");
        List<String> list = Arrays.asList(split);
        this.classRoomId = list;
    }
    public List<String> getClassRoomId(){
        return this.classRoomId;
    }*/
}
