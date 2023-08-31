package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.KnowledgeExercisesTrain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 知识点练习统一出题习题存储表
 * @author xuchenghong
 */

@Mapper
public interface KnowledgeExercisesTrainMapper extends BaseMapper<KnowledgeExercisesTrain> {
    /**
     * 获取知识点统一习题根据知识点id
     * @param classifyId
     * @return
     */
    List<Map<String, String>> getUnifiedExercises(@Param("classifyId") String classifyId);
}
