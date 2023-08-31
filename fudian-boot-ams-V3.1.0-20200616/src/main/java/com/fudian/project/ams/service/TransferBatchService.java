package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.TransferBatch;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/1/2 15:46
 * @Desc:
 */
public interface TransferBatchService extends IService<TransferBatch> {

    int deleteByPrimaryKey(String id);

    int insert(TransferBatch record);

    int insertSelective(TransferBatch record);

    TransferBatch selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TransferBatch record);

    int updateByPrimaryKey(TransferBatch record);

    TransferBatch queryByCondition(Map<String, Object> map);

    /**
     * 新增转出批次与详细信息
     * @param jsonStr 包含批次信息与详细信息的JSON字符串
     * @return
     */
//    boolean insertTransfer(String jsonStr);

    CommonGridResult queryListByTransferBatch(TransferBatch transferBatch, String personName);

    /**
     * 删除批次与详细信息
     *
     * @param jsonStr
     * @return
     */
    boolean deleteBatchAndBill(String jsonStr);

    /**
     * 根据批次单号查询回执单电子文件
     *
     * @param batchId
     * @return
     */
    byte[] getFileByBatchId(String batchId);

    /**
     * 上传电子文件并保存至数据库
     *
     * @param part
     * @param batchId
     * @return
     */
    boolean uploadFileAndInsertSysFile(MultipartFile part, String batchId);

    /**
     * 页面展示数据：获取转递单号及当前日期
     *
     * @return
     */
    Map<String, String> queryIdAndDate();

    int updateBatch(List<TransferBatch> list);

    int batchInsert(List<TransferBatch> list);

    int insertOrUpdate(TransferBatch record);

    int insertOrUpdateSelective(TransferBatch record);

    /**
     * 根据批次id删除回执单电子文件
     *
     * @param batchId
     * @return
     */
    CommonResult deleteFileByBatchId(String batchId);

    int updateBatchSelective(List<TransferBatch> list);

    /**
     * 转出统计
     * @return
     */
    List<Map<String,Object>> summaryTransferBathc();
}


