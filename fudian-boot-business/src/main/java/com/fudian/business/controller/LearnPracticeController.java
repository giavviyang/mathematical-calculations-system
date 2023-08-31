package com.fudian.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.service.SysDeptApi;
import com.fudian.api.system.service.TokenApi;
import com.fudian.business.pojo.KnowledgeListClassification;
import com.fudian.business.pojo.StudentAnswer;
import com.fudian.business.service.KnowledgeListClassificationService;
import com.fudian.business.service.StudentAnswerService;
import com.fudian.business.service.StudentsService;
import com.fudian.business.vo.LearnPracticeVo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 随学随练
 *
 * @author xuchenghong
 */
@RestController
@RequestMapping(value = "learnPractice")
public class LearnPracticeController {

    @Autowired
    private StudentAnswerService studentAnswerService;
    @Autowired
    private TokenApi tokenService;
    @Autowired
    private KnowledgeListClassificationService classificationService;
    @Autowired
    private StudentsService studentsService;


    /**
     * 查询 学生分类
     * @return
     */
    @PostMapping(value = "/pagingQueryAnswer")
    public CommonGridResult pagingQueryAnswer(@RequestBody String data) {
        LearnPracticeVo learnPracticeVo = JSONObject.parseObject(data,LearnPracticeVo.class);
        CommonGridResult learnPractice = studentAnswerService.getLearnPractice(learnPracticeVo);
        return learnPractice;
    }
    /**
     * 查询 按知识点分类
     * @return
     */
    @PostMapping(value = "/pagingQueryClassify")
    public CommonGridResult pagingQueryClassify(@RequestBody String data) {
        LearnPracticeVo learnPracticeVo = JSONObject.parseObject(data,LearnPracticeVo.class);
        CommonGridResult learnPractice = studentAnswerService.getLearnPracticeByClassify(learnPracticeVo);
        return learnPractice;
    }

}
