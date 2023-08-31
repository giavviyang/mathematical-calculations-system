package com.fudian.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 〈表格通用返回对象〉
 *
 * @author Mr.Hou
 * @create 2019/6/28
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonGridResult {

    /**
     * 默认的code值
     */
    private  final  int  code=200;

    /**
     * 当前是第几页
     */
    private int pageNum;


    /**
     * 每一页的个数
     */
    private int pageSize;

    /**
     * 是否还有前一页
     */
    private boolean hasPrev;

    /**
     * 是否还有后一页
     */
    private boolean hasNext;

    /**
     * 总的页数
     */
    private int totalPage;


    /**
     * 总条数
     */
    private long total;



    /**
     * 列表数据
     */
    private List<?> rows;



    public CommonGridResult(int pageNum, int pageSize, long total, List<?> rows){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.rows = rows;

    }
    public CommonGridResult(long total, List<?> rows){
        this.total = total;
        this.rows = rows;
    }
}
