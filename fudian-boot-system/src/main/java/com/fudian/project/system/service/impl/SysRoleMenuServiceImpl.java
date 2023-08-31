package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.mapper.SysRoleMenuMapper;
import com.fudian.project.system.pojo.SysRoleMenu;
import com.fudian.project.system.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/13 11:52
 * @Desc:
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {


    @Autowired
    private  SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public CommonResult insertRoleMenu(SysRoleMenu sysRoleMenu) {
      int i =  sysRoleMenuMapper.insert(sysRoleMenu);
      return    new CommonResult(i>0?200:500,i>0?"插入成功":"插入失败",null);
    }

    @Override
    public int updateBatch(List<SysRoleMenu> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SysRoleMenu> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SysRoleMenu> list) {
        this.saveBatch(list);
        return list.size();
    }
    @Override
    public int insertOrUpdate(SysRoleMenu record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SysRoleMenu record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonResult removeRoleMenu(SysRoleMenu sysRoleMenu) {
        QueryWrapper<SysRoleMenu> wrapper=new QueryWrapper<>();
        int i=0;
        if (sysRoleMenu!=null&&
            sysRoleMenu.getRoleId()!=null&&
                sysRoleMenu.getMenuId()!=null&&
                !sysRoleMenu.getRoleId().equals("")&&
        !sysRoleMenu.getMenuId().equals("")
        ){
            wrapper.lambda().eq(SysRoleMenu::getRoleId,sysRoleMenu.getRoleId())
                    .eq(SysRoleMenu::getMenuId,sysRoleMenu.getMenuId());
             i = sysRoleMenuMapper.delete(wrapper);
        }
      return new CommonResult(i>0?200:500,i>0?"删除成功":"删除失败",null);
    }

    @Transactional
    @Override
    public CommonResult batchInsertRoleMenu(String roleId, String menuIds) {
        LambdaQueryWrapper<SysRoleMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRoleMenu::getRoleId,roleId);
        this.remove(lambdaQueryWrapper);
        if(menuIds!=null&&!"".equals(menuIds)){
            String[] menuId = menuIds.split(",");
            List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
            for(String id : menuId){
                if ("".equals(id) || id == null){
                    continue;
                }
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(id);
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenus.add(sysRoleMenu);
            }
            if(sysRoleMenus.size()>0){
                this.batchInsert(sysRoleMenus);
            }

        }
        return CommonResult.success("保存成功");
    }

    @Override
    public CommonResult getAllMenuIds(String roleId) {
        LambdaQueryWrapper<SysRoleMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> list = this.list(lambdaQueryWrapper);
        return CommonResult.success(list);
    }
}


