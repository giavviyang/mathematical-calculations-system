package com.fudian.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 练习类型
 * @author xuchenghong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classification implements Serializable {
    /** id */
    private String id;
    /** 名称 */
    private String name;

    /** 图标 */
    private String icon;
    /** 是否有弹窗 */
    private int dialog;
    /** 锁定类型（未锁定，错5次锁，对5次锁） */
    private int lockType;
    /** 分数 */
    private Double grade;
    /** 集合 */
    private List<Classification> list;
}
