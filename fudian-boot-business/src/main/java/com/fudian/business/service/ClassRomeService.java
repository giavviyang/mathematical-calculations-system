package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.ClassRome;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;


/**
 * @author xuchenghong
 */

public interface ClassRomeService extends IService<ClassRome> {

    /**
     * 查询所有班级
     * @return
     */
    List<ClassRome> selectAll(ClassRome classRome);

    /**
     * 分页查询所有班级
     * @param classRome
     * @return
     */
    CommonGridResult pagingSelectAll(ClassRome classRome);
    /**
     * 添加班级
     * @param classRome
     * @return
     */
    int insertClassRome(ClassRome classRome);
    /**
     * 修改班级
     * @param classRome
     * @return
     */
    int updateClassRomeById(ClassRome classRome);
    /**
     * 添加班级
     * @param ids
     * @return
     */
    int deleteClassRomeByIds(List<String> ids);
}
