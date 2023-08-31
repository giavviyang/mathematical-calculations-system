package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.system.pojo.SysOperLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
    int updateBatch(List<SysOperLog> list);

    int batchInsert(@Param("list") List<SysOperLog> list);

    int insertOrUpdate(SysOperLog record);

    int insertOrUpdateSelective(SysOperLog record);

    /**
     * 分页查询操作日志
     * @param sysOperLog
     * @return
     */
    List<SysOperLog> pagingQueryList(@Param("sysOperLog") SysOperLog sysOperLog);
    /**
     * 查询分页查询操作日志的总条数
     * @param sysOperLog
     * @return
     */
    int pagingQueryListCount(@Param("sysOperLog") SysOperLog sysOperLog);
}
