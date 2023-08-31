package com.fudian.project.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.system.pojo.SysDataPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysDataPermissionMapper extends BaseMapper<SysDataPermission> {
    int updateBatch(List<SysDataPermission> list);

    int batchInsert(@Param("list") List<SysDataPermission> list);

    int insertOrUpdate(SysDataPermission record);

    int insertOrUpdateSelective(SysDataPermission record);

}
