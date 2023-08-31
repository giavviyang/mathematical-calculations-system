package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.Schools;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;

/**
 * 学校
 * @author xuchenghong
 */
public interface SchoolsService extends IService<Schools> {

    /**
     * 分页查询学校
     *
     * @param schools
     * @return
     */
    CommonGridResult pagingSelectSchools(Schools schools);

    /**
     * 查询学校
     *
     * @param schools
     * @return
     */
    List<Schools> selectSchools(Schools schools);

    /**
     * 添加学校
     *
     * @param schools
     * @return
     */
    int insertSchools(Schools schools);

    /**
     * 修改学校
     *
     * @param schools
     * @return
     */
    int updateSchoolsById(Schools schools);

    /**
     * 批量删除学校
     *
     * @param id
     * @return
     */
    int deleteSchoolsById(String id);
    /**
     * 批量删除学校
     *
     * @param ids
     * @return
     */
    int deleteSchoolsById(List<String> ids);
}
