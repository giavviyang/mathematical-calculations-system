package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.system.pojo.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    int updateBatch(List<SysPermission> list);

    int batchInsert(@Param("list") List<SysPermission> list);

    int insertOrUpdate(SysPermission record);

    int insertOrUpdateSelective(SysPermission record);

    List<SysPermission> queryUserListByParamters(Map<String, String> map);
}
