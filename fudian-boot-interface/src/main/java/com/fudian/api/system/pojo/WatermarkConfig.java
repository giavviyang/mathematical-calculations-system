package com.fudian.api.system.pojo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fudian.common.pojo.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName(value = "watermark_config")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WatermarkConfig extends BaseEntity {
    @TableField(value = "id")
    private String id;

    @TableField(value = "name")
    private String name;

    /**
     * 文字水印
     */
    @TableField(value = "text", strategy = FieldStrategy.IGNORED)
    private String text;

    /**
     * 图片水印
     */
    @TableField(value = "image", strategy = FieldStrategy.IGNORED)
    private byte[] image;

    /**
     * 水印类型  0：文字水印  1：图片水印
     */
    @TableField(value = "type")
    private String type;

    /**
     * 是否启用  0：不启用  1：启用
     */
    @TableField(value = "active")
    private String active;

    /**
     * 文字颜色  (RGB格式 如255,255,255)
     */
    @TableField(value = "color", strategy = FieldStrategy.IGNORED)
    private String color;

    /**
     * 透明度（0-1之间）
     */
    @TableField(value = "diaphaneity")
    private Double diaphaneity;

    /**
     * 旋转角度 (0-1之间)
     */
    @TableField(value = "rotate")
    private Double rotate;

    /**
     * 文字大小
     */
    @TableField(value = "font_size", strategy = FieldStrategy.IGNORED)
    private Integer fontSize;

    /**
     * 图片大小  （长,宽，如100,,200）
     */
    @TableField(value = "img_size", strategy = FieldStrategy.IGNORED)
    private String imgSize;

    /**
     * 每个水印之间的行间距
     */
    @TableField(value = "row_length")
    private Integer rowLength;

    /**
     * 每个水印之间的纵间距
     */
    @TableField(value = "col_length")
    private Integer colLength;


    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd  HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_name")
    private String createName;

    /**
     * 创建时间
     */
    @TableField(value = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd  HH:mm:ss")
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField(value = "update_name")
    private String updateName;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取文字水印
     *
     * @return text - 文字水印
     */
    public String getText() {
        return text;
    }

    /**
     * 设置文字水印
     *
     * @param text 文字水印
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 获取图片水印
     *
     * @return image - 图片水印
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * 设置图片水印
     *
     * @param image 图片水印
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * 获取水印类型  0：文字水印  1：图片水印
     *
     * @return type - 水印类型  0：文字水印  1：图片水印
     */
    public String getType() {
        return type;
    }

    /**
     * 设置水印类型  0：文字水印  1：图片水印
     *
     * @param type 水印类型  0：文字水印  1：图片水印
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取是否启用  0：不启用  1：启用
     *
     * @return active - 是否启用  0：不启用  1：启用
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置是否启用  0：不启用  1：启用
     *
     * @param active 是否启用  0：不启用  1：启用
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 获取文字颜色  (RGB格式 如255,255,255)
     *
     * @return color - 文字颜色  (RGB格式 如255,255,255)
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置文字颜色  (RGB格式 如255,255,255)
     *
     * @param color 文字颜色  (RGB格式 如255,255,255)
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * 获取透明度（0-1之间）
     *
     * @return diaphaneity - 透明度（0-1之间）
     */
    public Double getDiaphaneity() {
        return diaphaneity;
    }

    /**
     * 设置透明度（0-1之间）
     *
     * @param diaphaneity 透明度（0-1之间）
     */
    public void setDiaphaneity(Double diaphaneity) {
        this.diaphaneity = diaphaneity;
    }

    /**
     * 获取旋转角度 (0-1之间)
     *
     * @return rotate - 旋转角度 (0-1之间)
     */
    public Double getRotate() {
        return rotate;
    }

    /**
     * 设置旋转角度 (0-1之间)
     *
     * @param rotate 旋转角度 (0-1之间)
     */
    public void setRotate(Double rotate) {
        this.rotate = rotate;
    }

    /**
     * 获取文字大小
     *
     * @return font_size - 文字大小
     */
    public Integer getFontSize() {
        return fontSize;
    }

    /**
     * 设置文字大小
     *
     * @param fontSize 文字大小
     */
    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * 获取图片大小  （长,宽，如100,,200）
     *
     * @return img_size - 图片大小  （长,宽，如100,,200）
     */
    public String getImgSize() {
        return imgSize;
    }

    /**
     * 设置图片大小  （长,宽，如100,,200）
     *
     * @param imgSize 图片大小  （长,宽，如100,,200）
     */
    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    /**
     * 获取每个水印之间的行间距
     *
     * @return row - 每个水印之间的行间距
     */
    public Integer getRowLength() {
        return rowLength;
    }

    /**
     * 设置每个水印之间的行间距
     *
     * @param rowLength 每个水印之间的行间距
     */
    public void setRowLength(Integer rowLength) {
        this.rowLength = rowLength;
    }

    /**
     * 获取每个水印之间的纵间距
     *
     * @return colLength - 每个水印之间的纵间距
     */
    public Integer getColLength() {
        return colLength;
    }

    /**
     * 设置每个水印之间的纵间距
     *
     * @param colLength 每个水印之间的纵间距
     */
    public void setColLength(Integer colLength) {
        this.colLength = colLength;
    }

    /**
     * 获取创建时间
     *
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人
     *
     * @return create_name 创建人
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * 设置创建人
     *
     * @param createName 创建人
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 获取修改时间
     *
     * @return update_time 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取修改人
     *
     * @return update_time 修改人
     */
    public String getUpdateName() {
        return updateName;
    }

    /**
     * 获取修改人
     *
     * @param updateName 修改人
     */
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", text=").append(text);
        sb.append(", type=").append(type);
        sb.append(", active=").append(active);
        sb.append(", color=").append(color);
        sb.append(", diaphaneity=").append(diaphaneity);
        sb.append(", rotate=").append(rotate);
        sb.append(", fontSize=").append(fontSize);
        sb.append(", imgSize=").append(imgSize);
        sb.append(", rowLength=").append(rowLength);
        sb.append(", colLength=").append(colLength);
        sb.append(", createTime=").append(createTime);
        sb.append(", createName=").append(createName);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateName=").append(updateName);
        sb.append("]");
        return sb.toString();
    }
}
