package com.fudian.common.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.sql.SqlUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Entity基类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 搜索值
     */
    @TableField(exist = false)
    private String searchValue;

    /**
     * 起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date beginTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date endTime;

    /**
     * 创建者
     */
    @TableField(exist = false)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(exist = false)
    @JsonIgnore
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(exist = false)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(exist = false)
    @JsonIgnore
    private Date updateTime;

    /**
     * 备注
     */
    @TableField(exist = false)
    private String remark;

    /**
     * 请求参数
     */
    @TableField(exist = false)
    private Map<String, Object> params;


    @TableField(exist = false)
    private String otherParam;

    /**
     * 页容量
     */
    @TableField(exist = false)
    private Integer pageSize;

    /**
     * 当前页
     */
    @TableField(exist = false)
    private Integer pageNum;

    /**
     * 排序行
     */
    @TableField(exist = false)
    private String orderByColumn;

    /**
     * 正序 倒序
     */
    @TableField(exist = false)
    private String isAsc;

    /**
     * 接收jsonMap专用的字符串
     */
    @TableField(exist = false)
    private String jsonStr;


    /**
     * 接收数组所用的参数
     */
    @TableField(exist = false)
    private String[] arry;

    /**
     * 获取排序条件
     *
     * @return
     */
    public String getOrderCondition() {
        if (StringUtils.isEmpty(orderByColumn)) {
            return "";
        }
        return SqlUtil.escapeOrderBySql(StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc);
    }

}
