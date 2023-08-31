package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.system.pojo.SysFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysFileMapper extends BaseMapper<SysFile> {
    int updateBatch(List<SysFile> list);

    int batchInsert(@Param("list") List<SysFile> list);

    int insertOrUpdate(SysFile record);

    int insertOrUpdateSelective(SysFile record);
}
