package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.project.ams.pojo.IssueCertificate;

import java.util.List;

/**
 * @author lutao
 * @create 2020/3/16
 */

public interface IssueCertificateService extends IService<IssueCertificate> {


    int updateByPrimaryKeySelective(IssueCertificate record);

    int updateByPrimaryKey(IssueCertificate record);

    int updateBatch(List<IssueCertificate> list);

    int batchInsert(List<IssueCertificate> list);

    /**
     * 获取出具证明数据
     * @param issueCertificate
     * @return List
     */
    List<IssueCertificate> queryList(IssueCertificate issueCertificate);

    /**
     * 通过id删除数据
     * @param id
     * @return
     */
    int removeById(String id);

    /**
     * 保存数据
     * @param issueCertificate
     * @return
     */
    int saveIssueCertificate(IssueCertificate issueCertificate);

}
