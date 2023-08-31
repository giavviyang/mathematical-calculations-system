package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01za;
import com.fudian.project.ams.pojo.E01zaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface E01zaMapper extends BaseMapper<E01za> {
    long countByExample(E01zaExample example);

    int deleteByExample(E01zaExample example);

    List<E01za> selectByExample(E01zaExample example);

    int updateByExampleSelective(@Param("record") E01za record, @Param("example") E01zaExample example);

    int updateByExample(@Param("record") E01za record, @Param("example") E01zaExample example);

    int updateBatch(List<E01za> list);

    int batchInsert(@Param("list") List<E01za> list);

    int insertOrUpdate(E01za record);

    int insertOrUpdateSelective(E01za record);
}
