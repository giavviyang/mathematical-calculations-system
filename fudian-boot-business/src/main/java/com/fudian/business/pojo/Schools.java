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
@TableName(value = "schools")
public class Schools extends BaseEntity implements Serializable {

    @TableId(value = "school_id")
    private String schoolId;


    @TableField(value = "school_name")
    private String schoolName;


    @TableField(value = "school_address")
    private String schoolAddress;


    @TableField(value = "school_contact")
    private String schoolContact;


    @TableField(value = "school_principal")
    private String schoolPrincipal;


    @TableField(value = "school_landline")
    private String schoolLandline;

}
