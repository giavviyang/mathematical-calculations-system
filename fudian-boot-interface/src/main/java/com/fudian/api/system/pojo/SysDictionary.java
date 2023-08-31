package com.fudian.api.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_dictionary")
public class SysDictionary extends BaseEntity {
    /**
     * 代码类型
     */
    @TableId(value = "dict_type", type = IdType.INPUT)
    private String dictType;

    /**
     * 代码
     */
    @TableId(value = "dict_code", type = IdType.INPUT)
    private String dictCode;

    /**
     * 父级代码
     */
    @TableField(value = "Eparent_code")
    private String eparentCode;

    /**
     * 代码全称
     */
    @TableField(value = "full_name")
    private String fullName;

    /**
     * 代码简称
     */
    @TableField(value = "nicker_name")
    private String nickerName;

    /**
     * 英文简称
     */
    @TableField(value = "pinyin")
    private String pinyin;

    /**
     * 代码属性 3位一组，前两位为当前版本，第三位为是否使用，0未使用；1使用
     */
    @TableField(value = "code_attr")
    private String codeAttr;

    public static final String COL_DICT_TYPE = "dict_type";

    public static final String COL_DICT_CODE = "dict_code";

    public static final String COL_EPARENT_CODE = "Eparent_code";

    public static final String COL_FULL_NAME = "full_name";

    public static final String COL_NICKER_NAME = "nicker_name";

    public static final String COL_PINYIN = "pinyin";

    public static final String COL_CODE_ATTR = "code_attr";
}
