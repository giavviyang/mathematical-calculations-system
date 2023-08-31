package com.fudian.business.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.dto.MasterSituationStatisticsDTO;
import com.fudian.business.pojo.BreakthroughStatistics;
import com.fudian.business.pojo.RelatedKnowledgePoints;
import com.fudian.business.pojo.StudentAnswer;
import com.fudian.business.vo.LearnPracticeVo;
import com.fudian.business.vo.SysRankingList;
import com.fudian.common.vo.MasterSituationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author xuchenghong
 */

@Mapper
public interface StudentAnswerMapper extends BaseMapper<StudentAnswer> {

    /**
     * 按照总题数和正确率做排行（以周，以学期）
     *
     * @param currentTime 查询范围
     * @return
     */
    List<Map<String, Object>> diligenceRanking(@Param("currentTime") Date currentTime, @Param("ids") List<String> ids);

    /**
     * 按照本周正确率和上周正确率作对比做排行
     *
     * @param thisWeek 查询范围
     * @param lastWeek 查询范围
     * @return
     */
    List<Map<String, Object>> progressRanking(@Param("thisWeek") Date thisWeek, @Param("lastWeek") Date lastWeek, @Param("ids") List<String> ids);

    /**
     *
     *
     * @param thisWeek 查询范围
     * @return
     */
    List<Map<String, Object>> levelRanking(@Param("thisWeek") Date thisWeek, @Param("ids") List<String> ids);

    /**
     * 按照总题数和正确率做排行（以周，以学期）
     * @param sysRankingList
     * @return
     */
    List<SysRankingList> sysDiligenceRanking(@Param("sysRankingList") SysRankingList sysRankingList);
    /**
     * 按照总题数和正确率做排行（以周，以学期） 的总数量
     * @param sysRankingList
     * @return
     */
    int sysDiligenceRankingCount(@Param("sysRankingList") SysRankingList sysRankingList);
    /**
     * 系统进步榜
     * @param sysRankingList
     * @return
     */
    List<SysRankingList> sysProgressRanking(@Param("sysRankingList") SysRankingList sysRankingList);
    /**
     * 系统进步榜总数量
     * @param sysRankingList
     * @return
     */
    int sysProgressRankingCount(@Param("sysRankingList") SysRankingList sysRankingList);

    /**
     * 根据学生id获取知识点掌握情况
     *
     * @param studentId
     * @return java.util.List<com.fudian.common.vo.MasterSituationVO>
     * @author hong 2022/1/2
     */
    List<MasterSituationVO> getMasterSituation(String studentId);

    /**
     * 根据班级id获取知识点掌握情况
     *
     * @param map
     * @return java.util.List<com.fudian.common.vo.MasterSituationVO>
     * @author hong 2022/1/2
     */
    List<MasterSituationVO> getMasterSituationByClassId(Map<String, Object> map);

    /**
     * 根据学生id获取闯关答题情况
     * 返回数据：1-年级、2-批次id、3-关卡名称、4-总正确率、5-平均答题时间并保留一位小数、6-总题数、7-关卡id
     * @param
     * @return java.util.List<com.fudian.common.vo.MasterSituationVO>
     * @author hong 2022/1/4
     */
    List<BreakthroughStatistics> getBreakthroughStatisticsByStudentId(String studentId);

    /**
     * 获取当前关卡，当前学生，最近11次闯关信息
     * 返回数据：批次id
     * @author hong 2022/2/11
        * @param studentId
        * @param levelId
     * @return java.util.List<java.lang.String>
     */
//    @Select("SELECT s1.batch_id FROM student_answer s1 WHERE s1.type = 1 AND s1.student_id = #{studentId} AND s1.classification_id = #{levelId} ORDER BY s1.create_time DESC LIMIT 11")
    List<String> getBatchIdByLevelId(@Param("studentId") String studentId,@Param("levelId") String levelId);

    /**
     * 根据知识点id和学生id获取关联知识正确率
     *
     * @param
     * @return java.util.List<RelatedKnowledgePoints>
     * @author hong 2022/1/4
     */
    @Deprecated
    List<RelatedKnowledgePoints> getRelatedKnowledgePoints(@Param("studentId") String studentId,
                                                           @Param("classificationIds") Set<String> classificationIds);


    /**
     * 查询正确率/用时排名
     *
     * @return
     */
    Double correctRateOnTimeRanking(@Param("studentAnswer") StudentAnswer studentAnswer);

    /**
     * 查询正确率/用时排名
     *
     * @return
     */
    Double correctRateRanking(@Param("studentAnswer") StudentAnswer studentAnswer);

    /**
     * 查询正确率/用时排名
     *
     * @return
     */
    Double correctTimeRanking(@Param("studentAnswer") StudentAnswer studentAnswer);
    /**
     * 查询随学随练 总页数
     *
     * @return
     */
    int getLearnPracticeTotal(@Param("learnPractice") LearnPracticeVo learnPracticeVo);
    /**
     * 查询随学随练
     *
     * @return
     */
    List<LearnPracticeVo> getLearnPractice(@Param("learnPractice") LearnPracticeVo learnPracticeVo);
    /**
     * 查询随学随练
     *
     * @return
     */
    List<Map<String,Object>> getLearnPracticeByClassify(@Param("learnPractice") LearnPracticeVo learnPracticeVo);
    /**
     * 查询随学随练
     *
     * @return
     */
    List<Map<String,Object>> getLearnPracticeByClassifyUnified(@Param("learnPractice") LearnPracticeVo learnPracticeVo);
    /**
     * 查询随学随练
     *
     * @return
     */
    int getLearnPracticeByClassifyTotal(@Param("learnPractice") LearnPracticeVo learnPracticeVo);

    /**
     * 获取知识点练习批次IDs
     * 返回数据：classificationId：知识点ID、classificationMsg：知识点名称、batchIds：批次IDs
     * @author hong 2022/2/16
     * @param studentId 学生ID
     * @param isMaster 是否掌握（0-未掌握、1-已掌握）
     */
    List<MasterSituationVO> getKnowledgePointExerciseBatchIDs(String studentId, int isMaster);

    /**
     * 知识点练习正确率统计
     * 返回数据：count：题数、correctRate：正确率、averageTime：答题平均用时
     * @author hong 2022/2/16
     * @param batchIds 批次IDs
     */
    MasterSituationStatisticsDTO accuracyStatistics(List<String> batchIds);

    /**
     * 按照本周正确率和上周正确率作对比做排行
     *
     * @param thisWeek 查询范围
     * @param lastWeek 查询范围
     * @return
     */
    Map<String, Object>  diagram(@Param("thisWeek") Date thisWeek, @Param("lastWeek") Date lastWeek, @Param("studentId") String studentId);
    /**
     * 查询学生答题曲线图
     *
     * @param leftDate 起始时间
     * @param studentId 学生id
     * @return
     */
    List<JSONObject>  diagrams(@Param("leftDate") Date leftDate, @Param("studentId") String studentId);
    /**
     * 查询所有正确率不一致的数据
     *
     * @return
     */
    List<JSONObject>  selectAllDiscord();
}
