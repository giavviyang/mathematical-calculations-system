package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.WfTaskE01z1;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WfTaskE01z1Mapper extends BaseMapper<WfTaskE01z1> {
    int updateBatch(List<WfTaskE01z1> list);

    int updateBatchSelective(List<WfTaskE01z1> list);

    int batchInsert(@Param("list") List<WfTaskE01z1> list);

    int insertOrUpdate(WfTaskE01z1 record);

    int insertOrUpdateSelective(WfTaskE01z1 record);

    /**
     * 根据ID 查询材料
     *
     * @param e01z1Id
     * @return
     */
    Map<String, String> selectE01z1ViewById(String e01z1Id);

    int updateStatus(List<WfTaskE01z1> list);

    int updateStatus(@Param("list") List<String> list, @Param("taskId")String taskId,@Param("status") String status);
}
