package com.fudian.project.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* @Author: WangYuZhuo
* @Date: 2020/6/9 17:15
* @Desc:
*/
@Data
@TableName(value = "custom_message")
public class CustomMessage {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 绑定用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 消息文字
     */
    @TableField(value = "message")
    private String message;

    /**
     * 消息类型
     */
    @TableField(value = "msg_type")
    private Integer msgType;

    /**
     * 消息状态,0为未读，1为已读
     */
    @TableField(value = "msg_status")
    private Integer msgStatus;

    /**
     * 绑定id
     */
    @TableField(value = "bind_id")
    private String bindId;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_MESSAGE = "message";

    public static final String COL_MSG_TYPE = "msg_type";

    public static final String COL_MSG_STATUS = "msg_status";

    public static final String COL_BIND_ID = "bind_id";
}
