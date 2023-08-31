package com.fudian.project.business.controller;

import com.fudian.business.pojo.ClassRome;
import com.fudian.business.service.ClassRomeService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 班级管理
 *
 * @author xuchenghong
 */
@RestController
@RequestMapping(value = "classRome")
public class ClassRomeController {
    @Autowired
    private ClassRomeService classRomeService;

    /**
     * 分页查询班级信息
     *
     * @param classRome
     * @return
     */
    @PostMapping("/pagingQueryClassRome")
    public CommonGridResult pagingQueryClassRome(ClassRome classRome) {
        CommonGridResult commonGridResult = classRomeService.pagingSelectAll(classRome);
        return commonGridResult;
    }
    /**
     * 分页查询班级信息
     *
     * @param classRome
     * @return
     */
    @PostMapping("/queryClassRome")
    public CommonResult queryClassRome(ClassRome classRome) {
        List<ClassRome> classRomes = classRomeService.selectAll(classRome);
        return CommonResult.success(classRomes);
    }

    /**
     * 添加班级信息
     *
     * @param classRome
     * @return
     */
    @PostMapping("/addClassRome")
    public CommonResult addClassRome(ClassRome classRome) {

        int i = classRomeService.insertClassRome(classRome);
        if (i > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.error();
        }
    }

    /**
     * 根据id 修改班级信息
     *
     * @param classRome
     * @return
     */
    @PostMapping("/modifyClassRomeById")
    public CommonResult modifyClassRomeById(ClassRome classRome) {

        int i = classRomeService.updateClassRomeById(classRome);
        if (i > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.error();
        }
    }

    /**
     * 根据id 删除班级信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeClassRomeByIds")
    public CommonResult removeClassRomeByIds(List<String> ids) {

        int i = classRomeService.deleteClassRomeByIds(ids);
        if (i > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.error();
        }
    }


}
