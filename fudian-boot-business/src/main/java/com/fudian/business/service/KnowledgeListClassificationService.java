package com.fudian.business.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.KnowledgeListClassification;
import com.fudian.business.pojo.KnowledgeListSchedule;
import com.fudian.business.pojo.Popup;
import com.fudian.business.vo.KnowledgeVo;
import com.fudian.business.vo.QuestionSettingVo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.vo.CommonResult;

import java.util.List;
import java.util.Map;


/**
 * @author xuchenghong
 */

public interface KnowledgeListClassificationService extends IService<KnowledgeListClassification> {


    /**
     * 查询小程序一级知识点列表
     *
     * @param studentId 学生id
     * @return 一级知识点列表
     */
    List<KnowledgeVo> selectKnowledgeListClassification(String studentId);
    /**
     * 查询小程序二级知识点列表
     *
     * @param studentId 学生id
     * @return 二级知识点列表
     */
    List<KnowledgeVo> selectKnowledgeListClassificationTow(String studentId);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    KnowledgeListClassification selectKnowledgeListClassificationById(String id);

    List<Map<String, Object>> queryListByLevelId(String levelId);

    /**
     * 依据分类信息查询知识点数据
     *
     * @param classfiyCode
     * @return
     */
    CommonGridResult selectListByParmas(String classfiyCode, String queryId, Integer pageNum, Integer pageSize);

    CommonResult updateKlcById(String popups,
                               KnowledgeListClassification knowledgeListClassification,
                               KnowledgeListSchedule knowledgeListSchedule);

    /**
     * 依据ids进行删除
     *
     * @param ids
     * @return
     */
    CommonResult deleteKLCByIds(String ids);

    CommonResult updateKLCByObj(KnowledgeListClassification knowledgeListClassification, String diagnosisMsg, List<Popup> popups);

    CommonResult saveData(String eparentCode, String classificationId, String classificationDatarange, String classificationTime, String classificationMsg, Integer questionsNumber, String formula_one, String formula_two, String diagnosisMsg);

    CommonResult saveData1(String eparentCode, String popups, KnowledgeListClassification knowledgeListClassification, String diagnosisMsg);

    CommonResult getListByPid(String pid);

    CommonResult removeInfoByklcIds(String ids);

    CommonResult setOrderNum(String ids);

    String getAncestorMsgByClassificationId(String classificationId);

    /**
     * 知识点出题设置分页查询功能
     *
     * @param questionSettingVo
     * @return
     */
    CommonGridResult pageingQueryQuestionSetting(QuestionSettingVo questionSettingVo);

    /**
     * 知识点出题设置统一还是随机出题
     *
     * @param ids
     * @param classId
     * @param flag
     * @return
     */
    CommonResult modifyIsRandom(List<String> ids, String classId, int flag);
}
