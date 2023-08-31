package com.fudian.business.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fudian.api.system.service.TokenApi;
import com.fudian.business.pojo.UserAgreement;
import com.fudian.business.service.UserAgreementService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户协议管理
 *
 * @author xuchenghong
 */
@RestController
@RequestMapping(value = "userAgreement")
public class UserAgreementController {

    @Autowired
    private UserAgreementService userAgreementService;
    @Autowired
    private TokenApi tokenService;

    /**
     * 分页查询用户协议
     *
     * @return
     */
    @GetMapping(value = "/pagingQueryAgreement")
    public CommonGridResult pagingQueryAgreement(UserAgreement userAgreement) {
        CommonGridResult commonGridResult = userAgreementService.pagingSelectUserAgreement(userAgreement);
        return commonGridResult;
    }

    /**
     * 新增用户协议
     *
     * @return
     */
    @PostMapping(value = "/addAgreement")
    public CommonResult<?> addAgreement(UserAgreement userAgreement) {
        try {
            HttpServletRequest request = ServletUtils.getRequest();
            String username = tokenService.getLoginUser(request).getUsername();
            userAgreement.setCreateName(username);
            userAgreement.setIsStart(0);
            userAgreementService.insertUserAgreement(userAgreement);
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }

    /**
     * 根据id修改用户协议
     *
     * @return
     */
    @PostMapping(value = "/modifyAgreementById")
    public CommonResult<?> modifyAgreementById(UserAgreement userAgreement) {
        try {
            userAgreementService.updateUserAgreementById(userAgreement);
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }

    /**
     * 根据id查询用户协议
     *
     * @return
     */
    @PostMapping(value = "/queryAgreementById")
    public CommonResult<?> queryAgreementById(String id) {
        try {
            UserAgreement userAgreement = userAgreementService.selectUserAgreementById(id);
            return CommonResult.success(userAgreement);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }

    /**
     * 根据ids删除用户协议
     *
     * @return
     */
    @PostMapping(value = "/removeAgreementById")
    public CommonResult<?> removeAgreementById(@RequestBody String agreementList) {
        try {
            List<UserAgreement> list = JSON.parseArray(agreementList, UserAgreement.class);
            List<String> ids = list.stream().map(e -> e.getId()).collect(Collectors.toList());
            userAgreementService.deleteUserAgreementById(ids);
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }

    /**
     * 修改启用状态
     *
     * @return
     */
    @PostMapping(value = "/modifyAgreementStartById")
    public CommonResult<?> modifyAgreementStartById(String id) {

        UpdateWrapper<UserAgreement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_start", 0);
        userAgreementService.update(updateWrapper);
        try {
            UserAgreement userAgreement = new UserAgreement();
            userAgreement.setId(id);
            userAgreement.setIsStart(1);
            userAgreementService.updateUserAgreementById(userAgreement);
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }
}
