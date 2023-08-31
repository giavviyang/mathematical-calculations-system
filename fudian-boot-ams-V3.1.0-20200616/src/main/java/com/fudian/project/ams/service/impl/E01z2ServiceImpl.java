package com.fudian.project.ams.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.ams.pojo.A01;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.ams.mapper.E01z2Mapper;
import com.fudian.project.ams.pojo.E01z2;
import com.fudian.project.ams.pojo.E01z2Example;
import com.fudian.project.ams.service.E01z2Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class E01z2ServiceImpl extends ServiceImpl<E01z2Mapper, E01z2> implements E01z2Service {



    @Override
    public long countByExample(E01z2Example example) {
        return baseMapper.countByExample(example);
    }
    @Override
    public int deleteByExample(E01z2Example example) {
        return baseMapper.deleteByExample(example);
    }
    @Override
    public List<E01z2> selectByExample(E01z2Example example) {
        return baseMapper.selectByExample(example);
    }
    @Override
    public int updateByExampleSelective(E01z2 record, E01z2Example example) {
        return baseMapper.updateByExampleSelective(record,example);
    }
    @Override
    public int updateByExample(E01z2 record, E01z2Example example) {
        return baseMapper.updateByExample(record,example);
    }
    @Override
    public int updateBatch(List<E01z2> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<E01z2> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(E01z2 record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(E01z2 record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int deleteE01z2s(String e01z2Ids) {
        LambdaQueryWrapper<E01z2> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(E01z2::getId,e01z2Ids.split("-"));
        return baseMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public CommonGridResult queryE01z2s(E01z2 e01z2) {
        Map<String,String> conmap = new HashMap<>();
        /*// 年度检索
        if (e01z2.getOtherParam()!=null&&!"".equals(e01z2.getOtherParam())){
            conmap.put("checkYear",e01z2.getOtherParam());
        }*/
        // 普通条件区检索
        if (e01z2.getSearchValue()!=null && !"".equals(e01z2.getSearchValue())){
            Map<String,String> e01z2Maps = JSONArray.parseObject(e01z2.getSearchValue(), Map.class);
            if(e01z2Maps.get("personName")!=null&&!"".equals(e01z2Maps.get("personName"))){
                String name = e01z2Maps.get("personName").toLowerCase();
                conmap.put("personName",name);
            }
            /*if(e01z2Maps.get("deptName")!=null&&!"".equals(e01z2Maps.get("deptName"))){
                conmap.put("deptName",e01z2Maps.get("deptName"));
            }*/
            if(e01z2Maps.get("e01z201")!=null&&!"".equals(e01z2Maps.get("e01z201"))) {
                conmap.put("e01z201", e01z2Maps.get("e01z201"));
            }
            if(e01z2Maps.get("e01z221a")!=null&&!"".equals(e01z2Maps.get("e01z221a"))) {
                conmap.put("e01z221a", e01z2Maps.get("e01z221a"));
            }
            if(e01z2Maps.get("checkYear")!=null&&!"".equals(e01z2Maps.get("checkYear"))) {
                conmap.put("checkYear", e01z2Maps.get("checkYear"));
            }
        }
        PageHelper.startPage(e01z2.getPageNum(), e01z2.getPageSize());
        List<Map<String,Object>> returnList = baseMapper.queryE01z2s(conmap);
        for(Map<String,Object> map :returnList) {
            try {
                if (map.get("e01z201") != null && !"".equals(map.get("e01z201"))) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date date = date = sdf.parse(map.get("e01z201").toString());
                    map.put("e01z201", date);
                }
                if (map.get("e01z227") != null && !"".equals(map.get("e01z227"))) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date date = date = sdf.parse(map.get("e01z227").toString());
                    map.put("e01z227", date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        Page<Map<String,Object>> page = (Page)returnList;
        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setPageSize(page.getPageSize());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setRows(page.getResult());
        return commonGridResult;
    }

    @Override
    public List<Map<String, Object>> summaryE01z2() {
        return baseMapper.summaryE01z2();
    }


}
