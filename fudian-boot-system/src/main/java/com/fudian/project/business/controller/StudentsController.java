package com.fudian.project.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fudian.api.system.pojo.Students;
import com.fudian.business.service.StudentsService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 学生管理
 *
 * @author xuchenghong
 */
@RestController
@RequestMapping(value = "students")
@OperTitle("学生信息")
public class StudentsController {
    @Autowired
    private StudentsService studentsService;

    /**
     * 分页查询学生信息
     *
     * @param students
     * @return
     */
    @PostMapping("/pagingQueryStudents")
    public CommonGridResult pagingQueryStudents(Students students) {
        CommonGridResult commonGridResult = studentsService.pagingSelectStudents(students);
        return commonGridResult;
    }

    /**
     * 查询学生信息
     *
     * @param student
     * @return
     */
    @PostMapping("/queryStudents")
    public CommonResult<Students> queryStudents(Students student) {
        List<Students> students = studentsService.selectStudents(student);
        return CommonResult.success(students);
    }

    /**
     * 添加学生信息
     *
     * @param students
     * @return
     */
    @PostMapping("/addStudents")
    @OperInfo(info = "新增学生信息")
    public CommonResult addStudents(Students students) {
        return studentsService.insertStudent(students);
    }

    /**
     * 查询学生学号是否重复
     *
     * @param studentNumber
     * @return
     */
    @GetMapping("/queryStudentsNumber")
    public CommonResult queryStudentsNumber(String studentNumber) {
        Students one = studentsService.selectStudentsByStudentNumber(studentNumber);
        if (one != null && one.getStudentNumber().equals(studentNumber)) {
            return CommonResult.error();
        }
        return CommonResult.success();
    }


    /**
     * 根据id 修改学生信息
     *
     * @param students
     * @return
     */
    @PostMapping("/modifyStudentsById")
    public CommonResult modifyStudentsById(Students students) {

        int i = studentsService.updateStudentByStudentId(students);
        if (i > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.error();
        }
    }

    /**
     * 根据ids 删除学生信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeStudentsById")
    public CommonResult removeStudentsById(String ids) {
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        int i = studentsService.deleteStudentByStudentId(strings);
        if (i > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.error();
        }
    }

    @GetMapping("queryStudentAndClass")
    @OperInfo(info = "查询学生信息")
    public CommonGridResult queryStudentAndClass(@RequestParam Map<String, Object> map) {
        return studentsService.queryStudentAndClass(map);
    }


    @GetMapping("/queryStudentInfoByStudent")
    @OperInfo(info = "查询学生信息")
    public CommonGridResult queryStudentInfoByStudent(Students student) {
        CommonGridResult commonGridResult = studentsService.queryStudentInfoByStudent(student);
        return commonGridResult;
    }

    @PostMapping("importExcelData")
    @OperInfo(info = "根据excel导入学生信息")
    public CommonResult<Students> importExcelData(MultipartFile part) {
        return studentsService.importStudentsByExcel(part);
    }

    @PostMapping("changeClass")
    @OperInfo(info = "批量修改班级")
    public CommonResult<Students> changeClass(String ids, String classId) {
        return studentsService.changeClass(ids, classId);
    }

    /**
     * 学生毕业功能
     *
     * @param studentIds
     * @param classId
     * @return
     */
    @PostMapping("studentGraduation")
    public CommonResult<?> studentGraduation(String studentIds, String classId) {
        List<String> list = JSON.parseArray(studentIds, String.class);
        return studentsService.studentGraduation(list, classId);
    }

    /**
     * 分页查询当前年度毕业班级
     *
     * @param students
     * @return
     */
    @PostMapping("pagingQueryStudentGraduation")
    public CommonGridResult pagingQueryStudentGraduation(Students students) {
        return studentsService.pagingQueryStudentGraduation(students);
    }
}
