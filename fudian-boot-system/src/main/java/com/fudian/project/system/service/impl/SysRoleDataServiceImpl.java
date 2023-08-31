package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.mapper.SysRoleDataMapper;
import com.fudian.project.system.pojo.SysRoleData;
import com.fudian.project.system.service.SysRoleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/13 11:53
 * @Desc:
 */
@Service
public class SysRoleDataServiceImpl extends ServiceImpl<SysRoleDataMapper, SysRoleData> implements SysRoleDataService {

    /**
     * 获取用户管理的班级年级学校关联数据
     * @param userId
     * @return
     */
    @Override
    public List<SysRoleData> getUserRoleData(String userId) {
        //查询用户关联班级

        LambdaQueryWrapper<SysRoleData> roleDataLam = new LambdaQueryWrapper<>();
        roleDataLam.eq(SysRoleData::getRoleId, userId);
        List<SysRoleData> sysUserRoles = this.list(roleDataLam);

        return sysUserRoles;
    }
    @Override
    public int batchInsert(List<SysRoleData> list) {
        this.saveBatch(list);
        return list.size();
    }
    @Override
    public int insertOrUpdate(SysRoleData record) {
        LambdaQueryWrapper<SysRoleData> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(SysRoleData::getDataPermissionId,record.getDataPermissionId());
        lambdaQueryWrapper.eq(SysRoleData::getRoleId,record.getRoleId());
        SysRoleData SysRoleData = this.getOne(lambdaQueryWrapper);
        if(SysRoleData == null){
            this.save(record);
        }else{
            this.updateById(record);
        }
        return 1;
    }


    @Override
    public CommonResult removeRoleData(SysRoleData sysRoleData) {
        QueryWrapper<SysRoleData> wrapper=new QueryWrapper<>();
        int i=0;
        if (sysRoleData!=null&&
                sysRoleData.getRoleId()!=null&&
                sysRoleData.getDataPermissionId()!=null&&
                !sysRoleData.getRoleId().equals("")&&
                !sysRoleData.getDataPermissionId().equals("")
        ){
            wrapper.lambda().eq(SysRoleData::getRoleId,sysRoleData.getRoleId())
                    .eq(SysRoleData::getDataPermissionId,sysRoleData.getDataPermissionId());
            i = baseMapper.delete(wrapper);
        }
        return new CommonResult(i>0?200:500,i>0?"删除成功":"删除失败",null);
    }
}

