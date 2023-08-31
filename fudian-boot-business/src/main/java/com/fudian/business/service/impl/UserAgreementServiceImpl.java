package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.UserAgreementMapper;
import com.fudian.business.pojo.UserAgreement;
import com.fudian.business.service.UserAgreementService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @author xuchenghong
 */

@Service
public class UserAgreementServiceImpl extends ServiceImpl<UserAgreementMapper, UserAgreement> implements UserAgreementService {


    @Resource
    private UserAgreementMapper userAgreementMapper;

    /**
     * 分页查询协议
     *
     * @param userAgreement
     * @return
     */
    @Override
    public CommonGridResult pagingSelectUserAgreement(UserAgreement userAgreement) {
        LambdaQueryWrapper<UserAgreement> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotEmpty(userAgreement.getTitle())){
            queryWrapper.like(UserAgreement::getTitle,userAgreement.getTitle());
        }
        PageHelper.startPage(userAgreement.getPageNum(), userAgreement.getPageSize());
        List<UserAgreement> userAgreements = userAgreementMapper.selectList(queryWrapper);

        Page page = (Page) userAgreements;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(userAgreement.getPageNum(), userAgreement.getPageSize(), total, userAgreements);
        return commonGridResult;
    }

    /**
     * 查询正在使用中的协议
     *
     * @return
     */
    @Override
    public UserAgreement selectUserAgreement() {
        LambdaQueryWrapper<UserAgreement> queryWrapper = new LambdaQueryWrapper<>();


        queryWrapper.eq(UserAgreement::getIsStart, 1);
        UserAgreement userAgreements = userAgreementMapper.selectOne(queryWrapper);
        return userAgreements;
    }

    /**
     * 查询协议根据id
     *
     * @param id
     * @return
     */
    @Override
    public UserAgreement selectUserAgreementById(String id) {

        UserAgreement userAgreements = userAgreementMapper.selectById(id);
        return userAgreements;
    }

    /**
     * 添加协议
     *
     * @param userAgreement
     * @return
     */
    @Override
    public int insertUserAgreement(UserAgreement userAgreement) {
        String uuid = UUID.randomUUID().toString();
        userAgreement.setId(uuid);
        userAgreement.setCreateTime(new Date());
        int insert = userAgreementMapper.insert(userAgreement);
        return insert;
    }

    /**
     * 修改协议
     *
     * @param userAgreement
     * @return
     */
    @Override
    public int updateUserAgreementById(UserAgreement userAgreement) {

        int i = userAgreementMapper.updateById(userAgreement);
        return i;
    }

    /**
     * 批量删除协议
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteUserAgreementById(List<String> ids) {
        int insert = userAgreementMapper.deleteBatchIds(ids);
        return insert;
    }

    /**
     * 删除协议
     *
     * @param id
     * @return
     */
    @Override
    public int deleteUserAgreementById(String id) {
        int insert = userAgreementMapper.deleteById(id);
        return insert;
    }
}
