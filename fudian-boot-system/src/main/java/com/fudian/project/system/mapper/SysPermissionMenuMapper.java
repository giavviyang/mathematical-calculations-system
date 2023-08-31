package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.system.pojo.SysPermissionMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionMenuMapper extends BaseMapper<SysPermissionMenu> {
    int updateBatch(List<SysPermissionMenu> list);

    int batchInsert(@Param("list") List<SysPermissionMenu> list);

    int insertOrUpdate(SysPermissionMenu record);

    int insertOrUpdateSelective(SysPermissionMenu record);

    List<String> selectSysPermissionMenuByPermissionId(String permissionId);
}
