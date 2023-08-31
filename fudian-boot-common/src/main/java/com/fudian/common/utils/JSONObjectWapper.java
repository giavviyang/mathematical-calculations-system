package com.fudian.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @Description JSONObject 装饰器
 * @ClassName JSONObjectProxy
 * @Author mr.zhang
 * @Date 2020/4/3 17:10
 * @Version 1.0.0
 **/
public class JSONObjectWapper implements Serializable {

    private JSONObject objectWapper;

    public JSONObjectWapper(JSONObject objectWapper){
        this.objectWapper = objectWapper;
    }

    public String getString(String key){
        return objectWapper.getString(key)==null?"":objectWapper.getString(key);
    }

}
