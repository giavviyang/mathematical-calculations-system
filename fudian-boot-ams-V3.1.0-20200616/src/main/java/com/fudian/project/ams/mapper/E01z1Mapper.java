package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.project.ams.pojo.E01z1Example;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface E01z1Mapper extends BaseMapper<E01z1> {
    long countByExample(E01z1Example example);

    int deleteByExample(E01z1Example example);

    List<E01z1> selectByExample(E01z1Example example);

    int updateByExampleSelective(@Param("record") E01z1 record, @Param("example") E01z1Example example);

    int updateByExample(@Param("record") E01z1 record, @Param("example") E01z1Example example);

    int updateBatch(List<E01z1> list);

    int batchInsert(@Param("list") List<E01z1> list);

    int insertOrUpdate(E01z1 record);

    int insertOrUpdateSelective(E01z1 record);

    List<A01> selectUserByE01ziIds(String[] ids);

    List<Map<String,Object>> queryE01z1ByIds(String[] ids);

    int updateSortByClassIfyAndOrderNum(E01z1 e01z1);

    String findMaxNum(E01z1 e01z1);

    List<Map<String,String>> selectE01z1ListByName(Map map);

    List<E01z1> selectListByFileCount(@Param("a01Id") String a01Id, @Param("status")String status);

    /**
     * 根据a01Id获取材料
     * @param id
     */
    List<String> selectE01z111aByA01Id(String id);
}
