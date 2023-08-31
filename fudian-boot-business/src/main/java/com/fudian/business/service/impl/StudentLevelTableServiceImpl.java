package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.StudentLevelTableMapper;
import com.fudian.business.pojo.StudentLevelTable;
import com.fudian.business.service.StudentLevelTableService;
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
public class StudentLevelTableServiceImpl extends ServiceImpl<StudentLevelTableMapper, StudentLevelTable> implements StudentLevelTableService {


    @Resource
    private StudentLevelTableMapper studentLevelTableMapper;

    /**
     * 分页查询学生关卡习题
     *
     * @param studentLevelTable
     * @return
     */
    @Override
    public CommonGridResult pagingSelectStudentLevelTable(StudentLevelTable studentLevelTable) {
        LambdaQueryWrapper<StudentLevelTable> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(studentLevelTable.getPageNum(), studentLevelTable.getPageSize());
        List<StudentLevelTable> studentLevelTables = studentLevelTableMapper.selectList(queryWrapper);

        Page page = (Page) studentLevelTables;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(studentLevelTable.getPageNum(), studentLevelTable.getPageSize(), total, studentLevelTables);
        return commonGridResult;
    }

    /**
     * 查询学生关卡习题
     *
     * @param studentLevelTable
     * @return
     */
    @Override
    public List<StudentLevelTable> selectStudentLevelTable(StudentLevelTable studentLevelTable) {
        LambdaQueryWrapper<StudentLevelTable> queryWrapper = new LambdaQueryWrapper<>();

        List<StudentLevelTable> studentLevelTables = studentLevelTableMapper.selectList(queryWrapper);
        return studentLevelTables;
    }

    /**
     * 添加学生关卡习题
     *
     * @param studentLevelTable
     * @return
     */
    @Override
    public int insertStudentLevelTable(StudentLevelTable studentLevelTable) {
        String uuid = UUID.randomUUID().toString();
        /*studentLevelTable.setExercisesId(uuid);*/
        int insert = studentLevelTableMapper.insert(studentLevelTable);
        return insert;
    }

    /**
     * 修改学生关卡习题
     *
     * @param studentLevelTable
     * @return
     */
    @Override
    public int updateStudentLevelTableById(StudentLevelTable studentLevelTable) {

        int i = studentLevelTableMapper.updateById(studentLevelTable);
        return i;
    }

    /**
     * 批量删除学生关卡习题
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteStudentLevelTable(List<String> ids) {
        int insert = studentLevelTableMapper.deleteBatchIds(ids);
        return insert;
    }

}
