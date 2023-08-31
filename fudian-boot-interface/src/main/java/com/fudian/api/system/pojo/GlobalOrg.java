package com.fudian.api.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "global_org")
public class GlobalOrg extends BaseEntity {
    /**
     * 唯一标识uuid
     */
    @TableId(value = "global_org_id", type = IdType.INPUT)
    private String globalOrgId;

    /**
     * 全宗名称
     */
    @TableField(value = "global_org_name")
    private String globalOrgName;

    /**
     * 全宗号
     */
    @TableField(value = "global_org_code")
    private String globalOrgCode;

    /**
     * 创建时间
     */
    @TableField(value = "global_org_createtime")
    private Date globalOrgCreatetime;

    /**
     * 授权码
     */
    @TableField(value = "global_org_authorizationcode")
    private String globalOrgAuthorizationcode;

    /**
     * 数据库标识  k-v查找数据源
     */
    @TableField(value = "global_org_databasekey")
    private String globalOrgDatabasekey;

    /**
     * 数据库名称
     */
    @TableField(value = "global_org_databasename")
    private String globalOrgDatabasename;

    /**
     * 数据库URL
     */
    @TableField(value = "global_org_databaseURL")
    private String globalOrgDatabaseurl;

    /**
     * 用户名
     */
    @TableField(value = "global_org_databaseusername")
    private String globalOrgDatabaseusername;

    /**
     * 密码
     */
    @TableField(value = "global_org_databasepwd")
    private String globalOrgDatabasepwd;

    /**
     * 数据库端口号
     */
    @TableField(value = "global_org_databaseport")
    private Integer globalOrgDatabaseport;

    /**
     * 数据库类型 Mysql、Oracle、达蒙
     */
    @TableField(value = "global_org_databasetype")
    private Integer globalOrgDatabasetype;

    /**
     * 文件存储名称
     */
    @TableField(value = "global_org_filebasename")
    private String globalOrgFilebasename;

    /**
     * 文件存储URL
     */
    @TableField(value = "global_org_filebaseURL")
    private String globalOrgFilebaseurl;

    /**
     * 用户名
     */
    @TableField(value = "global_org_filebaseusername")
    private String globalOrgFilebaseusername;

    /**
     * 密码
     */
    @TableField(value = "global_org_filebasepwd")
    private String globalOrgFilebasepwd;

    /**
     * 文件存储端口号
     */
    @TableField(value = "global_org_filebaseport")
    private Integer globalOrgFilebaseport;

    /**
     * 文件存储类型  MongoDB、FTP、FastDFS
     */
    @TableField(value = "global_org_filebasetype")
    private Integer globalOrgFilebasetype;

    /**
     * 全宗状态0为启用  1为禁用
     */
    @TableField(value = "global_org_status")
    private Integer globalOrgStatus;

    public static final String COL_GLOBAL_ORG_ID = "global_org_id";

    public static final String COL_GLOBAL_ORG_NAME = "global_org_name";

    public static final String COL_GLOBAL_ORG_CODE = "global_org_code";

    public static final String COL_GLOBAL_ORG_CREATETIME = "global_org_createtime";

    public static final String COL_GLOBAL_ORG_AUTHORIZATIONCODE = "global_org_authorizationcode";

    public static final String COL_GLOBAL_ORG_DATABASEKEY = "global_org_databasekey";

    public static final String COL_GLOBAL_ORG_DATABASENAME = "global_org_databasename";

    public static final String COL_GLOBAL_ORG_DATABASEURL = "global_org_databaseURL";

    public static final String COL_GLOBAL_ORG_DATABASEUSERNAME = "global_org_databaseusername";

    public static final String COL_GLOBAL_ORG_DATABASEPWD = "global_org_databasepwd";

    public static final String COL_GLOBAL_ORG_DATABASEPORT = "global_org_databaseport";

    public static final String COL_GLOBAL_ORG_DATABASETYPE = "global_org_databasetype";

    public static final String COL_GLOBAL_ORG_FILEBASENAME = "global_org_filebasename";

    public static final String COL_GLOBAL_ORG_FILEBASEURL = "global_org_filebaseURL";

    public static final String COL_GLOBAL_ORG_FILEBASEUSERNAME = "global_org_filebaseusername";

    public static final String COL_GLOBAL_ORG_FILEBASEPWD = "global_org_filebasepwd";

    public static final String COL_GLOBAL_ORG_FILEBASEPORT = "global_org_filebaseport";

    public static final String COL_GLOBAL_ORG_FILEBASETYPE = "global_org_filebasetype";

    public static final String COL_GLOBAL_ORG_STATUS = "global_org_status";
}
