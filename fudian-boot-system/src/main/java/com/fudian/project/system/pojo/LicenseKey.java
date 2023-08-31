package com.fudian.project.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
* @Author: WangYuZhuo
* @Date: 2020/7/20 18:21
* @Desc:
*/
@Data
@TableName(value = "license_key")
public class LicenseKey implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * license字符串
     */
    @TableField(value = "license")
    private String license;
}
