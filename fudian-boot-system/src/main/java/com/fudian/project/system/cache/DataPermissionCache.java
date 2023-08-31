package com.fudian.project.system.cache;

import com.fudian.api.system.pojo.SysDataPermission;
import com.fudian.project.system.pojo.SysRoleData;
import com.fudian.project.system.service.SysDataPermissionService;
import com.fudian.project.system.service.SysRoleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DataPermissionCache {
    @Autowired
    private SysDataPermissionService sysDataPermissionService;

    @Autowired
    private SysRoleDataService sysRoleDataService;

    /**
     * 取值
     * @return
     */
    @Cacheable(value="roleDataPermissions")
    public  Map<String, List<SysDataPermission>> getPermissions() {
        List<SysDataPermission> sysDataPermissions = sysDataPermissionService.list();
        List<SysRoleData> sysRoleDatas= sysRoleDataService.list();
        Map<String,List<SysRoleData>> map=sysRoleDatas.stream().collect(Collectors.groupingBy(SysRoleData::getRoleId));
        Map<String,List<SysDataPermission>> saveMap= new HashMap<>();
        map.forEach((k,v)->{
            List<SysDataPermission> sysDataPermissionList = new ArrayList<>();
            v.stream().forEach(e->{
                List<SysDataPermission> permissionList= sysDataPermissions.stream().filter(e1->e1.getDataPermissionsId().equals(e.getDataPermissionId())).collect(Collectors.toList());
                permissionList.forEach(e2->{
                    sysDataPermissionList.add(e2);
                });
            });
            saveMap.put(k,sysDataPermissionList);
        });
        return saveMap;
    }

    /**
     * 更新缓存
     * @return
     */
    @CachePut(value="roleDataPermissions")
    public Map<String,List<SysDataPermission>> updatePermissions() {
        List<SysDataPermission> sysDataPermissions = sysDataPermissionService.list();
        List<SysRoleData> sysRoleDatas= sysRoleDataService.list();
        Map<String,List<SysRoleData>> map=sysRoleDatas.stream().collect(Collectors.groupingBy(SysRoleData::getRoleId));
        Map<String,List<SysDataPermission>> saveMap= new HashMap<>();
        map.forEach((k,v)->{
            List<SysDataPermission> sysDataPermissionList = new ArrayList<>();
            v.stream().forEach(e->{
                List<SysDataPermission> permissionList= sysDataPermissions.stream().filter(e1->e1.getDataPermissionsId().equals(e.getDataPermissionId())).collect(Collectors.toList());
                permissionList.forEach(e2->{
                    sysDataPermissionList.add(e2);
                });
            });
            saveMap.put(k,sysDataPermissionList);
        });
        return saveMap;
    }
}
