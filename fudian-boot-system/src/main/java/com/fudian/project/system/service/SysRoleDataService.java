package com.fudian.project.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysRoleData;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/13 11:53
 * @Desc:
 */
public interface SysRoleDataService extends IService<SysRoleData> {
    /**
     * 获取用户管理的班级年级学校关联数据
     * @param userId
     * @return
     */
    List<SysRoleData> getUserRoleData(String userId);



    int batchInsert(List<SysRoleData> list);

    int insertOrUpdate(SysRoleData record);



    /**
     * 移除角色与 数据权限得关联关系
     * @param sysRoleData
     * @return
     */
    CommonResult removeRoleData(SysRoleData sysRoleData);
}

