package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.TransferBill;
import com.fudian.project.ams.pojo.TransferBillExample;

import java.util.List;
import java.util.Map;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/1/2 15:46
 * @Desc:
 */

public interface TransferBillService extends IService<TransferBill>{

    long countByExample(TransferBillExample example);

    int deleteByExample(TransferBillExample example);

    List<TransferBill> selectByExample(TransferBillExample example);

    int updateByExampleSelective(TransferBill record, TransferBillExample example);

    int updateByExample(TransferBill record, TransferBillExample example);

    int updateBatch(List<TransferBill> list);

    int batchInsert(List<TransferBill> list);

    int insertOrUpdate(TransferBill record);

    int insertOrUpdateSelective(TransferBill record);

    /**
     * 生成数据包
     * @param transId
     * @return
     */
//    byte[] createPacket(String transId,String fileType);

    /**
     * 转出批次详情台账列表（加搜索）
     * @param transferBill
     * @return
     */
    CommonGridResult queryTransferBillList(TransferBill transferBill);


    /**
     * 批量删除详细信息
     * @param transferBill
     * @return
     */
    CommonResult batchDeleteTransferBill(TransferBill transferBill);

    /**
     * 档案转出详细台账高级
     */
    List<Map<String,Object>> queryhighDetail(String data);
}
