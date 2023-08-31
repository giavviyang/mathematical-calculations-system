package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.ams.pojo.E01zcRela;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/2 15:36
 * @Desc:
 */
@Mapper
public interface E01zcRelaMapper extends BaseMapper<E01zcRela> {
    int updateBatch(List<E01zcRela> list);

    int batchInsert(@Param("list") List<E01zcRela> list);

    int insertOrUpdate(E01zcRela record);

    int insertOrUpdateSelective(E01zcRela record);

}
