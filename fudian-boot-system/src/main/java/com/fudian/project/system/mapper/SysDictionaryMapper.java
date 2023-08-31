package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.system.pojo.SysDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {
    int updateBatch(List<SysDictionary> list);

    int batchInsert(@Param("list") List<SysDictionary> list);

    int insertOrUpdate(SysDictionary record);

    int insertOrUpdateSelective(SysDictionary record);

    /**
     * 根据学历代号查询学历
     * @param education
     * @return
     */
    String selectSysDictionaryByDictCode(String education);

    List<Map<String,Object>> getDataByEparentCode(SysDictionary sysDictionary);
}
