package com.fudian.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysRoleMenu;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/13 11:52
 * @Desc:
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {


    int updateBatch(List<SysRoleMenu> list);

    int updateBatchSelective(List<SysRoleMenu> list);

    int batchInsert(List<SysRoleMenu> list);

    int insertOrUpdate(SysRoleMenu record);

    int insertOrUpdateSelective(SysRoleMenu record);
    /**
     * 向角色与按钮表中添加数据
     * @param sysRoleMenu
     * @return
     */
    CommonResult insertRoleMenu(SysRoleMenu sysRoleMenu);

    /**
     * 从项目中移除当前角色
     * 与功能权限得关联任务
     * @param sysRoleMenu
     * @return
     */
    CommonResult removeRoleMenu(SysRoleMenu sysRoleMenu);

    /**
     * 批量保存功能权限
     * @param roleId
     * @param menuIds
     * @return
     */
    CommonResult batchInsertRoleMenu(String roleId, String menuIds);

    CommonResult getAllMenuIds(String roleId);
}


