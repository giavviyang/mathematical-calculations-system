package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.Audience;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author xuchenghong
 */

@Mapper
public interface AudienceMapper extends BaseMapper<Audience> {

    /**
     * 批量添加
     * @author xuchenghong
     */
    int batchAdd(@Param("testId") String testId, @Param("classRoomId") List<String> classRoomId);

    /**
     * 批量删除根据testId
     * @param testIds
     * @return
     */
    int deleteByTestIds(@Param("testIds") List<String> testIds);

    /**
     * 删除根据testId
     * @param testId
     * @return
     */
    int deleteByTestId(@Param("testId") String testId);

}
