package com.fudian.project.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.system.pojo.SysPermissionData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionDataMapper extends BaseMapper<SysPermissionData> {
    int updateBatch(List<SysPermissionData> list);

    int batchInsert(@Param("list") List<SysPermissionData> list);

    int insertOrUpdate(SysPermissionData record);

    int insertOrUpdateSelective(SysPermissionData record);
}
