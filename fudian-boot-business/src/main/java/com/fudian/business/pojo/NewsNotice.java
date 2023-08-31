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

/**
 * @author xuchenghong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "news_notice")
public class NewsNotice extends BaseEntity implements Serializable {
    /** id */
    @TableId(value = "id")
    private String id;
    /** 发件人id */
    @TableField(value = "sender_id")
    private String senderId;
    /** 收件人 */
    @TableField(value = "addressee_id")
    private String addresseeId;
    /** 发件人姓名 */
    @TableField(value = "sender_name")
    private String senderName;
    /** 收件人姓名 */
    @TableField(value = "addressee_name")
    private String addresseeName;
    /** 消息类型 */
    @TableField(value = "news_type")
    private int newsType;
    /** 消息内容 */
    @TableField(value = "news_content")
    private String newsContent;
    /** 通知人（发件人）头像 */
    @TableField(value = "notice_portrait")
    private String noticePortrait;
    /** 创建时间 */
    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 是否已读  0未读 ，1已读*/
    @TableField(value = "is_read")
    private int isRead;
    /**
     * 节点id
     */
    @TableField(exist = false)
    private String deptId;
}
