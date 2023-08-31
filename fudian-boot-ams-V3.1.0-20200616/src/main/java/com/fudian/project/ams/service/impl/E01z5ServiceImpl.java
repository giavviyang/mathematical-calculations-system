package com.fudian.project.ams.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.ams.mapper.E01z5Mapper;
import com.fudian.project.ams.pojo.E01z5;
import com.fudian.project.ams.pojo.E01z5Example;
import com.fudian.project.ams.service.E01z5Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class E01z5ServiceImpl extends ServiceImpl<E01z5Mapper, E01z5> implements E01z5Service {

    @Override
    public long countByExample(E01z5Example example) {
        return baseMapper.countByExample(example);
    }
    @Override
    public int deleteByExample(E01z5Example example) {
        return baseMapper.deleteByExample(example);
    }
    @Override
    public List<E01z5> selectByExample(E01z5Example example) {
        return baseMapper.selectByExample(example);
    }
    @Override
    public int updateByExampleSelective(E01z5 record, E01z5Example example) {
        return baseMapper.updateByExampleSelective(record,example);
    }
    @Override
    public int updateByExample(E01z5 record, E01z5Example example) {
        return baseMapper.updateByExample(record,example);
    }
    @Override
    public int updateBatch(List<E01z5> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<E01z5> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(E01z5 record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(E01z5 record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int deleteE01z5s(String e01z5Ids) {
        LambdaQueryWrapper<E01z5> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(E01z5::getId,e01z5Ids.split("-"));
        return  baseMapper.delete(lambdaQueryWrapper);
    }

    @Override
    public CommonGridResult queryE01z5List(E01z5 e01z5) {
        Map<String,String> conmap = new HashMap<>();
        /*// 年度检索
        if (e01z5.getOtherParam()!=null&&!"".equals(e01z5.getOtherParam())){
            conmap.put("checkYear",e01z5.getOtherParam());
        }*/
        // 普通条件区检索
        if (e01z5.getSearchValue()!=null && !"".equals(e01z5.getSearchValue())){
            Map<String,String> e01z5Maps = JSONArray.parseObject(e01z5.getSearchValue(), Map.class);
            if(e01z5Maps.get("personName")!=null&&!"".equals(e01z5Maps.get("personName"))){
                String name = e01z5Maps.get("personName").toLowerCase();
                conmap.put("personName",name);
            }
            if(e01z5Maps.get("deptName")!=null&&!"".equals(e01z5Maps.get("deptName"))){
                conmap.put("deptName",e01z5Maps.get("deptName"));
            }
            if(e01z5Maps.get("e01z501")!=null&&!"".equals(e01z5Maps.get("e01z501"))) {
                conmap.put("e01z501", e01z5Maps.get("e01z501"));
            }
            if(e01z5Maps.get("e01z521a")!=null&&!"".equals(e01z5Maps.get("e01z521a"))) {
                conmap.put("e01z521a", e01z5Maps.get("e01z521a"));
            }
            if(e01z5Maps.get("checkYear")!=null&&!"".equals(e01z5Maps.get("checkYear"))) {
                conmap.put("checkYear", e01z5Maps.get("checkYear"));
            }
        }
        PageHelper.startPage(e01z5.getPageNum(), e01z5.getPageSize());
        List<Map<String,Object>> returnList = baseMapper.queryByMap(conmap);
        // 高级检索
        if(e01z5.getJsonStr()!=null&&!"".equals(e01z5.getJsonStr())){

        }
        for(Map<String,Object> e01z5Map:returnList){
            try {
                if(e01z5Map.get("e01z501")!=null&&!"".equals(e01z5Map.get("e01z501"))){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                    Date dd=sdf.parse(e01z5Map.get("e01z501").toString());
                    e01z5Map.put("e01z501",dd);
                }
                if(e01z5Map.get("e01z524")!=null&&!"".equals(e01z5Map.get("e01z524"))){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                    Date dd=sdf.parse(e01z5Map.get("e01z524").toString());
                    e01z5Map.put("e01z524",dd);
                }
                if(e01z5Map.get("e01z531")!=null&&!"".equals(e01z5Map.get("e01z531"))){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                    Date dd=sdf.parse(e01z5Map.get("e01z531").toString());
                    e01z5Map.put("e01z531",dd);
                }
                if(e01z5Map.get("e01z534")!=null&&!"".equals(e01z5Map.get("e01z534"))){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                    Date dd=sdf.parse(e01z5Map.get("e01z534").toString());
                    e01z5Map.put("e01z534",dd);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        Page<Map<String,Object>> page = (Page)returnList;
        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setPageSize(page.getPageSize());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setRows(returnList);
        return commonGridResult;
    }

    @Override
    public List<Map<String, Object>> summaryE01z5() {
        return baseMapper.summaryE01z5();
    }
}
