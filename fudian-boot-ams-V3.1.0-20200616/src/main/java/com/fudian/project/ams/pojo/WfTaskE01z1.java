package com.fudian.project.ams.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "wf_task_e01z1")
public class WfTaskE01z1 implements Serializable {
    /**
     * 任务id
     */
    @TableId(value = "task_id", type = IdType.INPUT)
    private String taskId;

    /**
     * 材料id
     */
    @TableId(value = "e01z1_id", type = IdType.INPUT)
    private String e01z1Id;

    /**
     * 序号
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 状态 0：失败  1：成功
     */
    @TableField(value = "status")
    private String status;

    private static final long serialVersionUID = 1L;

    public static final String COL_TASK_ID = "task_id";

    public static final String COL_E01Z1_ID = "e01z1_id";

    public static final String COL_ORDER_NUM = "order_num";

    public static final String COL_STATUS = "status";
}
