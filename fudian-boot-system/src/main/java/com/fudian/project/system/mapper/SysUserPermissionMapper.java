package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.system.pojo.SysUserPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserPermissionMapper extends BaseMapper<SysUserPermission> {
    int updateBatch(List<SysUserPermission> list);

    int batchInsert(@Param("list") List<SysUserPermission> list);

    int insertOrUpdate(SysUserPermission record);

    int insertOrUpdateSelective(SysUserPermission record);
}
