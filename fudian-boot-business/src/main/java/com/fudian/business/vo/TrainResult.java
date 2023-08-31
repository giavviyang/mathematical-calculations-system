package com.fudian.business.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TrainResult implements Serializable {

    /**
     * 知识点id
     */
    private String id;
    /**下一关的id*/
    private String nextId;
    /**
     * 知识点标题
     */
    private String title;
    /**
     * 得分
     */
    private int fraction;
    /**
     * 金币
     */
    private int coins;
    /**
     * 时长
     */
    private double duration;
    /**
     * 正确率（0-1之间保留2位小数）
     */
    private double correctRate;
    /**
     * 均速（1位小数）
     */
    private double average;
    /**
     * 正确率排行（0-1之间保留2位小数）
     */
    private double correctRateRanking;
    /**
     * 用时排行（0-1之间保留2位小数）
     */
    private double timeRanking;
    /**
     * 是否进行锁定（不锁定，错5次锁，对5次锁）
     */
    private int isLock;
    /**
     * 错题数量
     */
    private int errorNum;
    /**
     * 错题相关知识点列表
     */
    private List<JSONObject> list;
    /**
     * 知识点名称列表
     */
    private Set<String> classification;
    /**
     * 返回的错题对象集合
     */
    private JSONObject errorResultObject;



}
