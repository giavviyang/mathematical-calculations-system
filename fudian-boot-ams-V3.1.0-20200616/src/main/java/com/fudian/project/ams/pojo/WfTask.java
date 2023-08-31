package com.fudian.project.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fudian.common.pojo.BaseEntity;
import com.fudian.common.utils.DateFormatSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/25 18:02
 * @Desc:
 */
@Data
@TableName(value = "wf_task")
public class WfTask extends BaseEntity implements Serializable {
    public static final String COL_ID = "id";
    public static final String COL_CREATE_NAME = "create_name";
    public static final String COL_ORG_ID = "org_id";
    public static final String COL_STATUS = "status";
    public static final String COL_DIGITIZATION_TYPE = "digitization_type";
    public static final String COL_TASK_TYPE = "task_type";
    public static final String COL_CREATE_TIME = "create_time";
    public static final String COL_A01_ID = "a01_id";
    public static final String COL_DISPOSE_NAME = "dispose_name";
    public static final String COL_DISPOSE_TIME = "dispose_time";
    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 创建人
     */
    @TableField(value = "create_name")
    private String createName;

    /**
     * 全宗id
     */
    @TableField(value = "org_id")
    private String orgId;

    /**
     * 任务状态（1未完成 2已完成）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 数字化任务类型（1单人单份  2多人多份 3单人整本）
     */
    @TableField(value = "digitization_type")
    private Integer digitizationType;

    /**
     * 任务类型（0档案权限审批任务 1档案转出审批任务 2数字化任务）
     */
    @TableField(value = "task_type")
    private Integer taskType;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonSerialize(using = DateFormatSerializer.class)
    private Date createTime;

    /**
     * 人员信息ID
     */
    @TableField(value = "a01_id")
    private String a01Id;

    /**
     * 修改人
     */
    @TableField(value = "dispose_name")
    private String disposeName;

    /**
     * 修改时间
     */
    @TableField(value = "dispose_time")
    private Date disposeTime;

    /**
     * 总份数
     */
    @TableField(value = "share_num")
    private Integer shareNum;

    /**
     * 总页数
     */
    @TableField(value = "page_num")
    private Integer pageNum;

    private static final long serialVersionUID = 1L;
}
