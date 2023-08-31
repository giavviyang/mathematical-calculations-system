package com.fudian.business.controller;

import com.alibaba.fastjson.JSON;
import com.fudian.api.system.service.TokenApi;
import com.fudian.business.pojo.StudentKnowledgeRelation;
import com.fudian.business.pojo.UserAgreement;
import com.fudian.business.service.StudentKnowledgeRelationService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 解锁处理
 *
 * @author xuchenghong
 */
@RestController
@RequestMapping(value = "unlock")
public class UnlockController {

    @Autowired
    private StudentKnowledgeRelationService unlockService;
    @Autowired
    private TokenApi tokenService;

    /**
     * 分页查询锁定数据
     *
     * @return
     */
    @PostMapping(value = "/pagingQueryUnlock")
    public CommonGridResult pagingQueryNewsNotice(StudentKnowledgeRelation unlockVo) {
        CommonGridResult commonGridResult = null;
//        if (unlockVo.getRelationType() == 1){
//            commonGridResult = unlockService.pagingSelectStudentLevelRelation(unlockVo);
//        } else {
            commonGridResult = unlockService.pagingSelectStudentKnowledgeRelation(unlockVo);
//        }
        return commonGridResult;
    }
    /**
     * 修改锁定数据
     *
     * @return
     */
    @PostMapping(value = "/modifyUnlockById")
    public CommonResult<?> modifyUnlockById(@RequestBody String data) {
        try {
            List<StudentKnowledgeRelation> studentKnowledgeRelations = JSON.parseArray(data, StudentKnowledgeRelation.class);
            List<String> ids = studentKnowledgeRelations.stream().map(StudentKnowledgeRelation::getId).collect(Collectors.toList());
            int i = unlockService.updateUnlocks(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
        return CommonResult.success();
    }

}
