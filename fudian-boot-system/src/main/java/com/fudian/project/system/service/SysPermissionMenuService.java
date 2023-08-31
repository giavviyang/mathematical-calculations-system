package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysPermissionMenu;

import java.util.List;

public interface SysPermissionMenuService extends IService<SysPermissionMenu> {


    int updateBatch(List<SysPermissionMenu> list);

    int batchInsert(List<SysPermissionMenu> list);

    int insertOrUpdate(SysPermissionMenu record);

    int insertOrUpdateSelective(SysPermissionMenu record);

    /**
     * 依据权限id查询当前的menu数据
     * @param permissionIdList
     * @return
     */
    List<SysPermissionMenu> querySysPermissionMenu(List<String> permissionIdList);


    CommonResult selectSysPermissionMenuByPermissionId(String permissionId);
}
