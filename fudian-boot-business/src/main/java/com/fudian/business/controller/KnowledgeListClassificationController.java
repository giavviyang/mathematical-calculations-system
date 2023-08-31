package com.fudian.business.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fudian.business.pojo.KnowledgeListClassification;
import com.fudian.business.pojo.Popup;
import com.fudian.business.service.KnowledgeListClassificationService;
import com.fudian.business.vo.QuestionSettingVo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.vo.CommonResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * xyh
 * 知识管理列表
 *
 */
@RestController
@RequestMapping(value = "klc")
public class KnowledgeListClassificationController {


    @Resource
    private KnowledgeListClassificationService knowledgeListClassificationService;



    /**
     * 新增知识点分类
     */

    @PostMapping("insertKLC")
    public CommonResult  instertKlC(String knowledgeListClassification,
                                    String popups,
                                    String eparentCode,
                                    String diagnosisMsg){
        KnowledgeListClassification  knowledgeListClassification1 = JSONObject.parseObject(knowledgeListClassification,KnowledgeListClassification.class);
        CommonResult commonResult = knowledgeListClassificationService.saveData1(eparentCode,popups, knowledgeListClassification1, diagnosisMsg);
//        return   commonResult;
        return  commonResult;
    }


    /**
     * 更新知识点分类
     */
    @PutMapping("updateKLC")
    public CommonResult  updateKLC(String knowledgeListClassification,String  diagnosisMsg){
            KnowledgeListClassification knowledgeListClassification1  = JSONObject.parseObject(knowledgeListClassification,KnowledgeListClassification.class);
            List<Popup>  popups = knowledgeListClassification1.getPopupList();
            CommonResult commonResult  =  knowledgeListClassificationService.updateKLCByObj(knowledgeListClassification1,diagnosisMsg,popups);
        return   commonResult;
    }


    /**
     * 删除知识点分类
     */
    @DeleteMapping("deleteKLC")
    public CommonResult  deleteKLC(String  ids){
        CommonResult commonResult  =  knowledgeListClassificationService.deleteKLCByIds(ids);
        return  commonResult;
    }

    /**
     * 查询知识点分类
     */
    @GetMapping("selectKLC")
    public CommonGridResult selectKLC(KnowledgeListClassification knowledgeListClassification){
        LambdaQueryWrapper<KnowledgeListClassification> knowledgeListClassificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //查询当前的信息
        knowledgeListClassificationLambdaQueryWrapper.like(KnowledgeListClassification::getClassificationMsg,"%"+knowledgeListClassification.getClassificationMsg()+"%");
        PageHelper.startPage(knowledgeListClassification.getPageNum(), knowledgeListClassification.getPageSize());
        List<KnowledgeListClassification> list = knowledgeListClassificationService.list(knowledgeListClassificationLambdaQueryWrapper);
        Page page = (Page) list;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(knowledgeListClassification.getPageNum(),
                knowledgeListClassification.getPageSize(), total, list);
        return   commonGridResult;
    }

    /**
     * 依据数域、运算、类型查询确定当前数据的详细分类信息
     * @param classfiyCode 分类code值
     * @return
     */
    @GetMapping("selectKLCByParams")
    public   CommonGridResult  selectKLCByParams(String  classfiyCode,String classificationId,Integer pageNum,Integer pageSize){
        CommonGridResult commonGridResult =  knowledgeListClassificationService.selectListByParmas(classfiyCode,classificationId,pageNum, pageSize);
        return commonGridResult;
    }

    @GetMapping("getListByPid")
    public CommonResult getListByPid(String pid){
        return knowledgeListClassificationService.getListByPid(pid);
    }

    /**
     * 删除知识点以及相关数据
     */
    @DeleteMapping("removeklc")
    public CommonResult  removeklc(String  ids){
        return knowledgeListClassificationService.removeInfoByklcIds(ids);
    }

    /**
     * 知识点排序
     */
    @PutMapping("setOrderNum")
    public CommonResult  setOrderNum(String  ids){
        return knowledgeListClassificationService.setOrderNum(ids);
    }

    /**
     * 知识点出题设置分页查询功能
     */
    @PostMapping("pageingQueryQuestionSetting")
    public CommonGridResult  pageingQueryQuestionSetting(QuestionSettingVo questionSettingVo){
        return knowledgeListClassificationService.pageingQueryQuestionSetting(questionSettingVo);
    }
    /**
     * 知识点出题设置
     */
    @PostMapping("modifyIsRandom")
    public CommonResult  modifyIsRandom(String idList,String classId ,int flag){
        List<String> ids = JSON.parseArray(idList, String.class);
        return knowledgeListClassificationService.modifyIsRandom(ids,classId,flag);
    }

}
