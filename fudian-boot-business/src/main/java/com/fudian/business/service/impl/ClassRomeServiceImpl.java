package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.ClassRomeMapper;
import com.fudian.business.pojo.ClassRome;
import com.fudian.business.service.ClassRomeService;
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
public class ClassRomeServiceImpl extends ServiceImpl<ClassRomeMapper, ClassRome> implements ClassRomeService {

    @Resource
    private ClassRomeMapper classRomeMapper;

    /**
     * 查询所有班级
     * @return
     */
    @Override
    public List<ClassRome> selectAll(ClassRome classRome){
        LambdaQueryWrapper<ClassRome> queryWrapper = new LambdaQueryWrapper<>();
        List<ClassRome> classRomes = classRomeMapper.selectList(queryWrapper);
        return classRomes;
    }

    /**
     * 分页查询所有班级
     * @param classRome
     * @return
     */
    @Override
    public CommonGridResult pagingSelectAll(ClassRome classRome){
        LambdaQueryWrapper<ClassRome> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(classRome.getPageNum(), classRome.getPageSize());
        List<ClassRome> classRomes = classRomeMapper.selectList(queryWrapper);
        Page page = (Page) classRomes;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(classRome.getPageNum(), classRome.getPageSize(), total, classRomes);
        return commonGridResult;
    }

    /**
     * 添加班级
     * @param classRome
     * @return
     */
    @Override
    public int insertClassRome(ClassRome classRome){

        String uuid = UUID.randomUUID().toString();
        classRome.setClassId(uuid);
        int insert = classRomeMapper.insert(classRome);
        return insert;
    }
    /**
     * 修改班级
     * @param classRome
     * @return
     */
    @Override
    public int updateClassRomeById(ClassRome classRome){

        int i = classRomeMapper.updateById(classRome);
        return i;
    }
    /**
     * 添加班级
     * @param ids
     * @return
     */
    @Override
    public int deleteClassRomeByIds(List<String> ids){

        int i = classRomeMapper.deleteBatchIds(ids);
        return i;
    }

}
