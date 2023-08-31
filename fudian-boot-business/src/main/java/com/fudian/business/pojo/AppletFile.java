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
@TableName(value = "applet_file")
public class AppletFile extends BaseEntity implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private String id;
    /**
     * 文件二进制数据
     */
    @TableField(value = "file_data")
    private byte[] fileData;
    /**
     * 文件格式
     */
    @TableField(value = "file_format")
    private String fileFormat;
}
