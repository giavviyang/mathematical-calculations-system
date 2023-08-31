package com.fudian.filestorage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fudian.common.pojo.BaseEntity;
import com.fudian.common.utils.DateTimeFormatSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
* @Author: WangYuZhuo
* @Date: 2020/6/13 14:44
* @Desc:
*/

/**
 * 'fd-standard.e01z1_recycle_back_view' is not BASE TABLE
 */
@Data
@TableName(value = "storage_config")
public class StorageConfig extends BaseEntity {
    public static String fileStorageType;
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 配置名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 地址
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 端口
     */
    @TableField(value = "port")
    private Long port;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 容器类型 0:ftp 1:mogodb 2:fastdfs 3:本地
     */
    @TableField(value = "type")
    private String type;

    /**
     * 0:停用 1:启用
     */
    @TableField(value = "active")
    private String active;

    /**
     * 文件保存的路径
     */
    @TableField(value = "base_path")
    private String basePath;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_name")
    private String createName;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = DateTimeFormatSerializer.class)
    private Date updateTime;

    /**
     * 修改人
     */
    @TableField(value = "update_name")
    private String updateName;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_IP = "ip";

    public static final String COL_PORT = "port";

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_TYPE = "type";

    public static final String COL_ACTIVE = "active";

    public static final String COL_BASE_PATH = "base_path";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_CREATE_NAME = "create_name";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_UPDATE_NAME = "update_name";
}
