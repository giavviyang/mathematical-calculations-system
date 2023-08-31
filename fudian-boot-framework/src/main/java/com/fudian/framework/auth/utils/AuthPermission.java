package com.fudian.framework.auth.utils;

import com.fudian.common.utils.data.DataStoreWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xyh
 * @title: AuthPermissionController
 * @projectName standard
 * @description:  当前为认证权限的控制类
 * @date 2020/1/7 21:06
 */
@Component
public class AuthPermission {

    @Autowired
    DataStoreWrapper redisCache;


    public Map<String,Object> getPermissionMapList(){
        //获取当前的redis状态
        boolean b=true;
        Map<String,Object> map=new HashMap<>();
        if(b){
            //当为畅通时则直接从redis中直接获取数据 否则从数据库中获取权限
            List<String> deptList=redisCache.getCacheList("0");
            List<String>  userList=redisCache.getCacheList("1");
            List<String>  tenTypeList= redisCache.getCacheList("2");
            List<String>  dataList= redisCache.getCacheList("3");
            List<String>  fileList= redisCache.getCacheList("4");
            map.put("0",deptList);
            map.put("1",userList);
            map.put("2",tenTypeList);
            map.put("3",dataList);
            map.put("4",fileList);
        }else{




        }


        return  null;
    }






}
