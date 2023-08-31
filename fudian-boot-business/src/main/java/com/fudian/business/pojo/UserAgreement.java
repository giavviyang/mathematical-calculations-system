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
@TableName(value = "user_agreement")
public class UserAgreement extends BaseEntity implements Serializable {
    /** id */
    @TableId(value = "id")
    private String id;
    /** 标题 */
    @TableField(value = "title")
    private String title;
    /** 内容 */
    @TableField(value = "content")
    private String content;
    /** 是否使用 1启动 ，2不启动 */
    @TableField(value = "is_start")
    private int isStart;
    /** 创建人 */
    @TableField(value = "create_name")
    private String createName;
    /** 创建时间 */
    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
