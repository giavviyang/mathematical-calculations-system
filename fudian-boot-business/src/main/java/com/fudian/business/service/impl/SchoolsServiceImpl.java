package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.SchoolsMapper;
import com.fudian.business.pojo.Schools;
import com.fudian.business.service.SchoolsService;
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
public class SchoolsServiceImpl extends ServiceImpl<SchoolsMapper, Schools> implements SchoolsService {


    @Resource
    private SchoolsMapper schoolsMapper;

    /**
     * 分页查询学校
     *
     * @param schools
     * @return
     */
    @Override
    public CommonGridResult pagingSelectSchools(Schools schools) {
        LambdaQueryWrapper<Schools> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(schools.getPageNum(), schools.getPageSize());
        List<Schools> schoolss = schoolsMapper.selectList(queryWrapper);

        Page page = (Page) schoolss;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(schools.getPageNum(), schools.getPageSize(), total, schoolss);
        return commonGridResult;
    }

    /**
     * 查询学校
     *
     * @param schools
     * @return
     */
    @Override
    public List<Schools> selectSchools(Schools schools) {
        LambdaQueryWrapper<Schools> queryWrapper = new LambdaQueryWrapper<>();

        List<Schools> schoolss = schoolsMapper.selectList(queryWrapper);
        return schoolss;
    }

    /**
     * 添加学校
     *
     * @param schools
     * @return
     */
    @Override
    public int insertSchools(Schools schools) {
        String uuid = UUID.randomUUID().toString();
        schools.setSchoolId(uuid);
        int insert = schoolsMapper.insert(schools);
        return insert;
    }

    /**
     * 修改学校
     *
     * @param schools
     * @return
     */
    @Override
    public int updateSchoolsById(Schools schools) {

        int i = schoolsMapper.updateById(schools);
        return i;
    }

    /**
     * 批量删除学校
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteSchoolsById(List<String> ids) {
        int insert = schoolsMapper.deleteBatchIds(ids);
        return insert;
    }
    /**
     * 删除学校
     *
     * @param id
     * @return
     */
    @Override
    public int deleteSchoolsById(String id) {
        int insert = schoolsMapper.deleteById(id);
        return insert;
    }
}
