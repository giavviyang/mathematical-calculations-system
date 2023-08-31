package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.TeachersMapper;
import com.fudian.business.pojo.Teacher;
import com.fudian.business.service.TeachersService;
import com.fudian.common.pojo.CommonGridResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


/**
 * @author xuchenghong
 */

@Service
public class TeachersServiceImpl extends ServiceImpl<TeachersMapper, Teacher> implements TeachersService {

    @Resource
    private TeachersMapper teachersMapper;

    /**
     * 分页查询教师信息
     * @param teacher
     * @return
     */
    @Override
    public CommonGridResult pagingSelectTeacher(Teacher teacher){
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        if (teacher.getTeacherName() != null) {
            queryWrapper.eq(Teacher::getTeacherName,teacher.getTeacherName());
        }
        if (teacher.getTeacherTelphone() != null) {
            queryWrapper.eq(Teacher::getTeacherTelphone,teacher.getTeacherTelphone());
        }

        PageHelper.startPage(teacher.getPageNum(), teacher.getPageSize());
        List<Teacher> list = teachersMapper.selectList(queryWrapper);
        Page page = (Page) list;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(teacher.getPageNum(), teacher.getPageSize(), total, list);
        return commonGridResult;
    }

    /**
     * 查询教师信息
     * @param teacher
     * @return
     */
    @Override
    public List<Teacher> selectTeacher(Teacher teacher){
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        if (teacher.getTeacherName() != null) {
            queryWrapper.eq(Teacher::getTeacherName,teacher.getTeacherName());
        }
        if (teacher.getTeacherTelphone() != null) {
            queryWrapper.eq(Teacher::getTeacherTelphone,teacher.getTeacherTelphone());
        }
        List<Teacher> list = teachersMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 添加教师信息
     * @param teacher
     * @return
     */
    @Override
    public int insertTeacher(Teacher teacher){
        String uuid = UUID.randomUUID().toString();
        teacher.setTeacherId(uuid);
        int insert = teachersMapper.insert(teacher);
        return insert;
    }

    /**
     * 修改教师信息
     * @param teacher
     * @return
     */
    @Override
    public int updateTeacherById(Teacher teacher){
        int i = teachersMapper.updateById(teacher);
        return i;
    }

    /**
     * 批量删除教师信息
     * @param ids
     * @return
     */
    @Override
    public int deleteTeacherById(List<String> ids){
        int i = teachersMapper.deleteBatchIds(ids);
        return i;
    }
    /**
     * 批量删除教师信息
     * @param id
     * @return
     */
    @Override
    public int deleteTeacherById(String id){
        int i = teachersMapper.deleteById(id);
        return i;
    }
}
