package com.fudian.business.mapper;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.TestTable;
import com.fudian.business.vo.TestClassificationAnalysisVo;
import com.fudian.business.vo.TestStudentAnalysisVo;
import com.fudian.common.json.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author xuchenghong
 */

@Mapper
public interface TestTableMapper extends BaseMapper<TestTable> {
    /**
     * 分页查询
     *
     * @param testTable
     * @return
     */
    List<TestTable> pagingQueryTestTable(@Param("testTable") TestTable testTable);

    /**
     * 分页查询的总数量
     *
     * @param testTable
     * @return
     */
    int pagingQueryTestTableCount(@Param("testTable") TestTable testTable);

    /**
     * 批量删除根据testId
     *
     * @param testIds
     * @return
     */
    int deleteByTestIds(@Param("testIds") List<String> testIds);

    /**
     * 删除根据testId
     *
     * @param testId
     * @return
     */
    int deleteByTestId(@Param("testId") String testId);

    /**
     * 查询学生可练习的课堂测试题
     *
     * @param studentId
     * @return
     */
    List<Map<String, Object>> selectTestList(@Param("studentId") String studentId);

    /**
     * 分页查询课堂统一错题 按知识点分类
     *
     * @param testClassifVo
     * @return
     */
    List<TestClassificationAnalysisVo> selectErrorIntegratedTopic(@Param("testClassifVo") TestClassificationAnalysisVo testClassifVo);
    /**
     * 分页查询课堂随机错题  按知识点分类
     *
     * @param testClassifVo
     * @return
     */
    List<TestClassificationAnalysisVo> selectErrorRandomTopic(@Param("testClassifVo") TestClassificationAnalysisVo testClassifVo);
    /**
     * 分页查询课堂错题对应知识点总数量 按知识点分类
     *
     * @param testClassifVo
     * @return
     */
    int selectErrorIntegratedTopicCount(@Param("testClassifVo") TestClassificationAnalysisVo testClassifVo);

     /**
     * 分页查询课堂错题 按学生分类
     *
     * @param testStudentfVo
     * @return
     */
    List<TestStudentAnalysisVo> selectStudentErrorTopic(@Param("testStudentfVo") TestStudentAnalysisVo testStudentfVo);
     /**
     * 分页查询课堂错题 按学生分类 总数量
     *
     * @param testStudentfVo
     * @return
     */
    int selectStudentErrorTopicCount(@Param("testStudentfVo") TestStudentAnalysisVo testStudentfVo);

}
