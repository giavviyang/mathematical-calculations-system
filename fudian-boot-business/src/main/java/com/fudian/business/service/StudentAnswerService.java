package com.fudian.business.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.StudentAnswer;
import com.fudian.business.vo.LearnPracticeVo;
import com.fudian.business.vo.SysRankingList;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;
import java.util.Map;

/**
 * 答题记录
 *
 * @author xuchenghong
 */
public interface StudentAnswerService extends IService<StudentAnswer> {

    /**
     * 分页查询学生知识点关联表
     *
     * @param studentAnswer
     * @return
     */
    CommonGridResult pagingSelectStudentAnswer(StudentAnswer studentAnswer);

    /**
     * 查询学生知识点关联表
     *
     * @param studentAnswer
     * @return
     */
    List<StudentAnswer> selectStudentAnswer(StudentAnswer studentAnswer);

    /**
     * 查询学期正确率和总题数
     *
     * @param
     * @return
     */
    List<JSONObject> selectStudentCorrectRate(String studentId);

    /**
     * 添加学生知识点关联表
     *
     * @param studentAnswer
     * @return
     */
    int insertStudentAnswer(StudentAnswer studentAnswer);

    /**
     * 修改学生知识点关联表
     *
     * @param studentAnswer
     * @return
     */
    int updateStudentAnswerById(StudentAnswer studentAnswer);

    /**
     * 批量删除学生知识点关联表
     *
     * @param id
     * @return
     */
    int deleteStudentAnswerById(String id);

    /**
     * 批量删除学生知识点关联表
     *
     * @param ids
     * @return
     */
    int deleteStudentAnswerById(List<String> ids);

    /**
     * 查询勤奋榜单
     *
     * @param type 0周 1学期
     * @param list 学生id
     * @return
     */
    List<Map<String, Object>> diligenceRanking(int type, List<String> list);

    /**
     * 查询进步榜单
     *
     * @return
     */
    List<Map<String, Object>> progressRanking(List<String> list);

    /**
     * 查询闯关榜单
     *
     * @return
     */
    List<Map<String, Object>> levelRanking(List<String> list);

    /**
     * 系统查询勤奋榜单
     *
     * @param sysRankingList
     * @return
     */
    CommonGridResult sysDiligenceRanking(SysRankingList sysRankingList);
    /**
     * 系统查询进步榜单
     *
     * @param sysRankingList
     * @return
     */
    CommonGridResult sysProgressRanking(SysRankingList sysRankingList);

    /**
     * 查询正确率/用时排名
     *
     * @return
     */
    Double correctRateOnTimeRanking(StudentAnswer studentAnswer);

    /**
     * 查询正确率/用时排名
     *
     * @return
     */
    Double correctRateRanking(StudentAnswer studentAnswer);

    /**
     * 查询正确率/用时排名
     *
     * @return
     */
    Double correctTimeRanking(StudentAnswer studentAnswer);

    /**
     * 随学随练
     *
     * @param learnPracticeVo
     * @return
     */
    CommonGridResult getLearnPractice(LearnPracticeVo learnPracticeVo);
    /**
     * 随学随练
     *
     * @param learnPracticeVo
     * @return
     */
    CommonGridResult getLearnPracticeByClassify(LearnPracticeVo learnPracticeVo);

}
