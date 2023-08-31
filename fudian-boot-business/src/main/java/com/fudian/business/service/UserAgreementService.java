package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.UserAgreement;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;

/**
 * 协议
 *
 * @author xuchenghong
 */
public interface UserAgreementService extends IService<UserAgreement> {

    /**
     * 分页查询协议
     *
     * @param userAgreement
     * @return
     */
    CommonGridResult pagingSelectUserAgreement(UserAgreement userAgreement);

    /**
     * 查询正在使用中的协议
     *
     * @return
     */
    UserAgreement selectUserAgreement();

    /**
     * 查询协议根据id
     *
     * @param id
     * @return
     */
    UserAgreement selectUserAgreementById(String id);

    /**
     * 添加协议
     *
     * @param userAgreement
     * @return
     */
    int insertUserAgreement(UserAgreement userAgreement);

    /**
     * 修改协议
     *
     * @param userAgreement
     * @return
     */
    int updateUserAgreementById(UserAgreement userAgreement);

    /**
     * 批量删除协议
     *
     * @param id
     * @return
     */
    int deleteUserAgreementById(String id);

    /**
     * 批量删除协议
     *
     * @param ids
     * @return
     */
    int deleteUserAgreementById(List<String> ids);
}
