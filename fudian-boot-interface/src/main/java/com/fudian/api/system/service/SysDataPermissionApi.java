package com.fudian.api.system.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 16:18
 * @Desc:
 */
public interface SysDataPermissionApi {

    public Map<String, List<String>> getDataPermission(String userId);

    public List<Map<String, List<String>>> getA01DataPermission(String userId);

    Map<String,Object> setSerachMap(Map<String,Object> map);
}
