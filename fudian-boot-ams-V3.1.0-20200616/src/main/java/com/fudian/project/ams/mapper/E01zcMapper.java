package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01zc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/11 19:08
 * @Desc:
 */
@Mapper
public interface E01zcMapper extends BaseMapper<E01zc> {
    int updateBatch(List<E01zc> list);

    int batchInsert(@Param("list") List<E01zc> list);

    int insertOrUpdate(E01zc record);

    int insertOrUpdateSelective(E01zc record);

    List<Map<String,Object>> summaryE01zc();
}
