package com.fudian.api.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_file")
public class SysFile {
    /**
     * 唯一标识uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 电子文件名称
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 电子文件序号
     */
    @TableField(value = "file_index")
    private Integer fileIndex;

    /**
     * 电子文件类型
     */
    @TableField(value = "file_type")
    private String fileType;

    /**
     * 电子文件大小（字节）
     */
    @TableField(value = "file_size")
    private Integer fileSize;

    /**
     * 文件说明
     */
    @TableField(value = "file_desc")
    private String fileDesc;

    /**
     * 电子文件来源（mongdb为ID  ftp为路径）
     */
    @TableField(value = "file_source")
    private String fileSource;

    /**
     * 绑定id(例：a01Id、e01z1id、转出)
     */
    @TableField(value = "binding_id")
    private String bindingId;

    /**
     * 文件存储方式（1 mongdb  2 ftp  3fastDFS)
     */
    @TableField(value = "file_storage_mode")
    private String fileStorageMode;

    public static final String COL_ID = "id";

    public static final String COL_FILE_NAME = "file_name";

    public static final String COL_FILE_INDEX = "file_index";

    public static final String COL_FILE_TYPE = "file_type";

    public static final String COL_FILE_SIZE = "file_size";

    public static final String COL_FILE_DESC = "file_desc";

    public static final String COL_FILE_SOURCE = "file_source";

    public static final String COL_BINDING_ID = "binding_id";

    public static final String COL_FILE_STORAGE_MODE = "file_storage_mode";
}
