package com.fudian.api.room.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "custom_room")
public class CustomRoom extends BaseEntity implements Serializable {
    /**
     * 唯一标识uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 库房名称
     */
    @TableField(value = "room_name")
    private String roomName;

    /**
     * 库房管理员
     */
    @TableField(value = "room_manager")
    private String roomManager;

    /**
     * 库房面积
     */
    @TableField(value = "room_area")
    private Integer roomArea;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    private Date createDate;

    /**
     * 创建人
     */
    @TableField(value = "create_user")
    private String createUser;

    /**
     * 修改时间
     */
    @TableField(value = "update_date")
    private Date updateDate;

    /**
     * 修改人
     */
    @TableField(value = "update_user")
    private String updateUser;

    /**
     * 库房地址
     */
    @TableField(value = "room_location")
    private String roomLocation;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ROOM_NAME = "room_name";

    public static final String COL_ROOM_MANAGER = "room_manager";

    public static final String COL_ROOM_AREA = "room_area";

    public static final String COL_REMARK = "remark";

    public static final String COL_CREATE_DATE = "create_date";

    public static final String COL_CREATE_USER = "create_user";

    public static final String COL_UPDATE_DATE = "update_date";

    public static final String COL_UPDATE_USER = "update_user";

    public static final String COL_ROOM_LOCATION = "room_location";
}
