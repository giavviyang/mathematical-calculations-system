package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.ams.pojo.E01z2;
import com.fudian.project.ams.pojo.E01z2Example;

import java.util.List;
import java.util.Map;

public interface E01z2Service extends IService<E01z2>{


    long countByExample(E01z2Example example);

    int deleteByExample(E01z2Example example);

    List<E01z2> selectByExample(E01z2Example example);

    int updateByExampleSelective(E01z2 record, E01z2Example example);

    int updateByExample(E01z2 record, E01z2Example example);

    int updateBatch(List<E01z2> list);

    int batchInsert(List<E01z2> list);

    int insertOrUpdate(E01z2 record);

    int insertOrUpdateSelective(E01z2 record);

    /**
     * 批量删除材料接收记录
     * @param e01z2Ids
     * @return
     */
    int deleteE01z2s(String e01z2Ids);

    /**
     * 查询所有材料接收记录
     * @param e01z2
     * @return
     */
    CommonGridResult queryE01z2s(E01z2 e01z2);

    /**
     * 统计材料接收
     * @return
     */
    List<Map<String,Object>> summaryE01z2();

    /**
     * 编辑材料接收台账信息
     * @param e01z2
     * @return
     */
//    boolean updateE01z2AndDuty(E01z2 e01z2);
}
