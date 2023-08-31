package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.system.pojo.WrongQuestionListDTO;
import com.fudian.business.dto.MasterSituationStatisticsDTO;
import com.fudian.business.pojo.WrongQuestionList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 错题
 * @author xuchenghong
 */

@Mapper
public interface WrongQuestionListMapper extends BaseMapper<WrongQuestionList> {

    List<WrongQuestionListDTO> queryListByDict(String dictId, String studentId);

    /**
     * 获取学生知识点错题数量
     * @author hong 2022/1/3
        * @param dictId
        * @param studentId
     * @return int
     */
    int isError(String dictId, String studentId);

    /**
     * 获取学生知识点错题
     * @author hong 2022/1/2
        * @param studentId
     * @return java.util.List<com.fudian.api.system.pojo.WrongQuestionListDTO>
     */
    List<WrongQuestionListDTO> getErrorContent(String studentId);

    /**
     * 获取班级知识点错题
     * @author hong 2022/1/2
        * @param map
     * @return java.util.List<com.fudian.api.system.pojo.WrongQuestionListDTO>
     */
    List<WrongQuestionListDTO> getErrorContentByClassId(Map<String,Object> map);

    /**
     * @author xch
     * @param ids
     * @return
     */
    List<WrongQuestionList> getWrongQuestionByBatchId(@Param("ids") List<String> ids);

    /**
     * 获取知识点错题
     * 返回数据：classificationId：知识点ID、error：错题、reason：错误原因
     * @author hong 2022/2/16
     * @param batchIds 批次IDs
     */
    List<MasterSituationStatisticsDTO> wrongQuestionsInAcquiringKnowledgePoints(List<String> batchIds);

}
