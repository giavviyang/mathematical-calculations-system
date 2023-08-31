package com.fudian.business.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.SysDataPermissionApi;
import com.fudian.api.system.service.SysDeptApi;
import com.fudian.api.system.service.TokenApi;
import com.fudian.business.mapper.StudentsMapper;
import com.fudian.business.service.StudentsService;
import com.fudian.business.vo.StudentInfo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.PinYin;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.ams.utils.excel.EasyExcelUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;


/**
 * 学生业务
 *
 * @author xuchenghong
 */

@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Students> implements StudentsService {

    @Resource
    private StudentsMapper studentsMapper;

    @Autowired
    private TokenApi tokenService;

    @Autowired
    private SysDataPermissionApi sysDataPermissionApi;

    @Autowired
    private SysDeptApi sysDeptApi;


    /**
     * 根据学号查询学生
     *
     * @param studentNumber
     * @return
     */
    @Override
    public Students selectStudentsByStudentNumber(String studentNumber) {
        LambdaQueryWrapper<Students> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Students::getStudentNumber, studentNumber);
        Students student = studentsMapper.selectOne(queryWrapper);
        return student;
    }

    /**
     * 根据id查询学生
     *
     * @param id
     * @return
     */
    @Override
    public Students selectStudentsByStudentId(String id) {
        LambdaQueryWrapper<Students> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Students::getStudentId, id);
        Students student = studentsMapper.selectOne(queryWrapper);
        return student;
    }

    /**
     * 根据学号修改
     *
     * @param student
     * @return
     */
    @Override
    public int updateStudentByStudentNumber(Students student) {
        LambdaQueryWrapper<Students> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Students::getStudentNumber, student.getStudentNumber());
        int update = studentsMapper.update(student, queryWrapper);
        return update;
    }


    /**
     * 分页查询学生
     *
     * @return
     */
    @Override
    public CommonGridResult pagingSelectStudents(Students students) {
        LambdaQueryWrapper<Students> queryWrapper = new LambdaQueryWrapper<>();
        if (students.getStudentGrade() != null) {
            queryWrapper.eq(Students::getStudentGrade, students.getStudentGrade());
        }
        if (students.getStudentNumber() != null) {
            queryWrapper.eq(Students::getStudentNumber, students.getStudentNumber());
        }
        PageHelper.startPage(students.getPageNum(), students.getPageSize());
        List<Students> list = studentsMapper.selectList(queryWrapper);
        Page page = (Page) list;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(students.getPageNum(), students.getPageSize(), total, list);
        return commonGridResult;
    }

    /**
     * 分页查询学生
     *
     * @return
     */
    @Override
    public List<Students> selectStudents(Students students) {
        LambdaQueryWrapper<Students> queryWrapper = new LambdaQueryWrapper<>();
        if (students.getStudentGrade() != null) {
            queryWrapper.eq(Students::getStudentGrade, students.getStudentGrade());
        }
        if (students.getStudentNumber() != null) {
            queryWrapper.eq(Students::getStudentNumber, students.getStudentNumber());
        }
        List<Students> list = studentsMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 新增学生
     *
     * @return
     */
    @Override
    public CommonResult insertStudent(Students student) {

        String uuid = UUID.randomUUID().toString();
        student.setStudentId(uuid);
        String studentNumber = student.getStudentNumber();
        String substring = studentNumber.substring(studentNumber.length() - 6, studentNumber.length());
        String encode = new BCryptPasswordEncoder().encode(substring);
        student.setStudentCode(encode);
        student.setStudentStatus(0);

        boolean save = this.save(student);
        if (save) {
            return CommonResult.success();
        }
        return CommonResult.error();
    }

    /**
     * 查询当前学生同年级学生
     *
     * @param classRoomId
     * @return
     */
    @Override
    public List<Students> queryGradeStudent(String classRoomId) {
        List<Students> students = studentsMapper.selectGradeStudent(classRoomId);
        return students;
    }


    /**
     * 根据id修改
     *
     * @param student
     * @return
     */
    @Override
    public int updateStudentByStudentId(Students student) {
        int update = studentsMapper.updateById(student);
        return update;
    }

    /**
     * 根据id删除学生
     *
     * @param studentId
     * @return
     */
    @Override
    public int deleteStudentByStudentId(String studentId) {
        int i = studentsMapper.deleteById(studentId);
        return i;
    }

    /**
     * 根据ids删除学生
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteStudentByStudentId(List<String> ids) {
        int i = studentsMapper.deleteBatchIds(ids);
        return i;
    }

    @Override
    public CommonGridResult queryStudentAndClass(Map<String, Object> map) {
        CommonGridResult result = new CommonGridResult();
        Integer pageNum = Integer.valueOf(map.get("pageNum").toString());
        Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
        List<Map<String, Object>> studentList = new ArrayList<>();

        //根据当前登录用户添加权限过滤参数，过滤数据
//        map = sysDataPermissionApi.setSerachMap(map);
        Page page = PageHelper.startPage(pageNum, pageSize);
        studentList = baseMapper.queryStudentAndClass(map);
        result.setTotal(page.getTotal());
        result.setRows(studentList);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        return result;
    }

    @Override
    public CommonGridResult queryStudentInfoByStudent(Students student) {
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        //当前老师
        student.setClassHeadmasterId(user.getUserId());
        Page page = PageHelper.startPage(student.getPageNum(), student.getPageSize());
        List<Map<String, Object>> list = studentsMapper.queryStudentInfoByStudent(student);
        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setPageSize(page.getPageSize());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setRows(list);
        return commonGridResult;
    }


    @Override
    public CommonResult importStudentsByExcel(MultipartFile part) {
        String type = part.getOriginalFilename().split("\\.")[part.getOriginalFilename().split("\\.").length - 1];
        try {
            List<Students> studentsList = new ArrayList<>();
            List<List<String>> lists = EasyExcelUtil.readExcelWithStringList(part.getInputStream(), ExcelTypeEnum.valueOf(type.toUpperCase()));
            for (List<String> list : lists) {
                Students student = new Students();
                String schoolName = list.get(0);
                String gradeName = list.get(1);
                String className = list.get(2);
                String studentName = list.get(3);
                String number = list.get(4);
                Students one = this.selectStudentsByStudentNumber(number);
                if (one != null && one.getStudentNumber().equals(number)) {
                    continue;
                }
                if (list.get(1) != null && !gradeName.equals("年级")) {
                    if (className.equals("1班")) {
                        className = "一班";
                    } else if (className.equals("2班")) {
                        className = "二班";
                    } else if (className.equals("3班")) {
                        className = "三班";
                    } else if (className.equals("4班")) {
                        className = "四班";
                    } else if (className.equals("5班")) {
                        className = "五班";
                    } else if (className.equals("6班")) {
                        className = "六班";
                    }
                    String substring = number.substring(number.length() - 6);
                    String encode = new BCryptPasswordEncoder().encode(substring);
                    Map<String, Object> stringObjectMap = new HashMap<>();
                    stringObjectMap.put("schoolName", schoolName);
                    stringObjectMap.put("gradeName", gradeName);
                    stringObjectMap.put("className", className);
                    SysDept sysDept = sysDeptApi.getIdByDeptName(stringObjectMap);
                    if (sysDept == null || StringUtils.isEmpty(sysDept.getId())) {
                        continue;
                    }
                    student.setStudentId(StringUtils.uuid());
                    student.setStudentStatus(0);
                    student.setStudentNumber(number);

                    student.setClassRoomId(sysDept.getId());
                    student.setStudentCode(encode);
                    student.setStudentName(studentName);
                    student.setStudentNickname(studentName);
                    student.setRemark(list.get(0) + list.get(1));
                    student.setStudentPortrait("default");
                    studentsList.add(student);
                }
            }
            boolean b = this.saveBatch(studentsList);
            if (b) {
                return CommonResult.success("上传成功，本次上传" + studentsList.size() + "条数据");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.error();
        }
        return CommonResult.success("上传成功，本次上传0条数据");
    }

    @Override
    public CommonResult changeClass(String ids, String classId) {
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        UpdateWrapper updateWrapper = new UpdateWrapper<Students>();
        LambdaQueryWrapper<Students> studentsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        updateWrapper.in("student_id", strings);
        updateWrapper.set("class_room_id", classId);
        boolean update = this.update(updateWrapper);
        if (update) {
            return CommonResult.success();
        }
        return CommonResult.error();
    }


    /**
     * 查询学生个人信息
     *
     * @param studentId
     * @return
     */
    @Override
    public StudentInfo queryStudentInfoByStudentId(String studentId) {
        StudentInfo studentInfo = studentsMapper.queryStudentInfoByStudentId(studentId);
        return studentInfo;
    }

    /**
     * @param studentIds
     * @param classId
     * @return
     */
    @Override
    public CommonResult<?> studentGraduation(List<String> studentIds, String classId) {
        try {
            SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
            //查询当前年的毕业年级班级有么有创建  没有则创建毕业班  毕业班不会被查询到
            SysDept sysDept = sysDeptApi.getById(classId);
            //当前年
            String year = Calendar.getInstance().get(Calendar.YEAR) + "";
            //年级
            LambdaQueryWrapper<SysDept> gradeQueryWrapper = new LambdaQueryWrapper<>();
            gradeQueryWrapper.eq(SysDept::getParentId, sysDept.getId());
            gradeQueryWrapper.eq(SysDept::getDeptType, 1);
            gradeQueryWrapper.eq(SysDept::getDeptName, year);
            SysDept grade = sysDeptApi.getOne(gradeQueryWrapper);
            //没有年级则创建
            if (grade == null) {
                String uuid = StringUtils.uuid();
                grade = new SysDept();
                grade.setId(uuid);
                grade.setDeptName(year);
                grade.setStatus("1");
                grade.setNodeType("1");
                grade.setDeptType(1);
                grade.setParentId(sysDept.getId());
                grade.setCreateBy(user.getUserName());
                grade.setAncestors(sysDept.getAncestors() + uuid + ",");
                grade.setCreateTime(new Date());
                grade.setDeptPym(PinYin.getFirstSpell(grade.getDeptName()));
                sysDeptApi.save(grade);
            }
            //班级
            LambdaQueryWrapper<SysDept> classRoomQueryWrapper = new LambdaQueryWrapper<>();
            classRoomQueryWrapper.eq(SysDept::getParentId, grade.getId());
            classRoomQueryWrapper.eq(SysDept::getDeptType, 2);
            classRoomQueryWrapper.eq(SysDept::getDeptName, "毕业班");
            SysDept classRoom = sysDeptApi.getOne(classRoomQueryWrapper);
            if (classRoom == null) {
                String uuid = StringUtils.uuid();
                classRoom = new SysDept();
                classRoom.setId(uuid);
                classRoom.setDeptName("毕业班");
                classRoom.setStatus("1");
                classRoom.setNodeType("1");
                classRoom.setDeptType(2);
                classRoom.setParentId(grade.getId());
                classRoom.setCreateBy(user.getUserName());
                classRoom.setAncestors(grade.getAncestors() + uuid + ",");
                classRoom.setCreateTime(new Date());
                classRoom.setDeptPym(PinYin.getFirstSpell(classRoom.getDeptName()));
                sysDeptApi.save(classRoom);
            }
            //把传入的学生班级年级改成当前年毕业班
            UpdateWrapper<Students> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("class_room_id", classRoom.getId());
            updateWrapper.in("student_id", studentIds);
            this.update(updateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
        return CommonResult.success();
    }

    /**
     * @param students
     * @return
     */
    @Override
    public CommonGridResult pagingQueryStudentGraduation(Students students) {
        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setPageNum(students.getPageNum());
        commonGridResult.setPageSize(students.getPageSize());
        commonGridResult.setTotal(0);
        commonGridResult.setRows(null);
        try {
            //当前年
            String year = Calendar.getInstance().get(Calendar.YEAR) + "";
            //年级
            LambdaQueryWrapper<SysDept> gradeQueryWrapper = new LambdaQueryWrapper<>();
            gradeQueryWrapper.eq(SysDept::getParentId, students.getClassRoomId());
            gradeQueryWrapper.eq(SysDept::getDeptType, 1);
            gradeQueryWrapper.eq(SysDept::getDeptName, year);
            SysDept grade = sysDeptApi.getOne(gradeQueryWrapper);

            //班级
            LambdaQueryWrapper<SysDept> classRoomQueryWrapper = new LambdaQueryWrapper<>();
            classRoomQueryWrapper.eq(SysDept::getParentId, grade.getId());
            classRoomQueryWrapper.eq(SysDept::getDeptType, 2);
            classRoomQueryWrapper.eq(SysDept::getDeptName, "毕业班");
            SysDept classRoom = sysDeptApi.getOne(classRoomQueryWrapper);
            //毕业班学生
            LambdaQueryWrapper<Students> studentQueryWrapper = new LambdaQueryWrapper<>();
            studentQueryWrapper.eq(Students::getClassRoomId, classRoom.getId());
            if (StringUtils.isNotEmpty(students.getStudentName())) {
                studentQueryWrapper.like(Students::getStudentName, students.getStudentName());
            }
            PageHelper.startPage(students.getPageNum(), students.getPageSize());
            List<Students> studentList = this.list(studentQueryWrapper);
            Page page = (Page) studentList;
            commonGridResult.setTotal(page.getTotal());
            commonGridResult.setRows(studentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commonGridResult;
    }


    /**
     * 查询学生（学生头像和头像框为替换后的）根据学生id集合
     *
     * @param studentIds 学生id集合
     * @return 学生信息
     */
    @Override
    public List<Students> batchSelectStudentsById(List<String> studentIds) {
        if (studentIds != null && studentIds.size() > 0) {
            return baseMapper.batchSelectStudentsById(studentIds);
        }
        return new ArrayList<>();

    }
}
