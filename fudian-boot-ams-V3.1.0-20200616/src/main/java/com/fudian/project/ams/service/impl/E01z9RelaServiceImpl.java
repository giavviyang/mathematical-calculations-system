package com.fudian.project.ams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.mapper.E01z9Mapper;
import com.fudian.project.ams.mapper.E01z9RelaMapper;
import com.fudian.project.ams.pojo.E01z9;
import com.fudian.project.ams.pojo.E01z9Rela;
import com.fudian.project.ams.service.E01z9RelaService;
import com.fudian.project.ams.utils.Map2Bean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class E01z9RelaServiceImpl extends ServiceImpl<E01z9RelaMapper, E01z9Rela> implements E01z9RelaService {
    @Autowired
    E01z9RelaMapper e01z9RelaMapper;
    @Autowired
    E01z9Mapper e01z9Mapper;

    @Override
    public int updateBatch(List<E01z9Rela> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<E01z9Rela> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(E01z9Rela record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(E01z9Rela record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public List<E01z9Rela> queryE01z9List(String e01z9Id) {
        LambdaQueryWrapper<E01z9Rela> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(E01z9Rela::getE01z9Id, e01z9Id);
//        queryWrapper.eq(E01z9Rela::getFlag,"1");
        List<E01z9Rela> e01z9RelaList = e01z9RelaMapper.selectList(queryWrapper);
        return e01z9RelaList;
    }

    @Override
    public int updateE01z9RelaById(E01z9Rela e01z9Rela) {
        E01z9Rela rela = e01z9RelaMapper.selectById(e01z9Rela.getId());
        rela.setFeedback(e01z9Rela.getFeedback());
        int res = 0;
        try {
            res = e01z9RelaMapper.updateById(rela);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int updateE01z9Rela(E01z9Rela e01z9Rela) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", e01z9Rela.getId());
        e01z9Rela.setRemind("2");
        int res = e01z9RelaMapper.update(e01z9Rela, updateWrapper);
        return res;
    }

    @Override
    public int insertE01z9RelaList(String id, List<E01z9Rela> e01z9Relas) {
        for (int i = 0; i < e01z9Relas.size(); i++) {
            String idRela = UUID.randomUUID().toString().replace("-", "");
            E01z9Rela e01z9Rela = e01z9Relas.get(i);
            e01z9Rela.setId(idRela);
            e01z9Rela.setFlag("1");
            e01z9Rela.setE01z9Id(id);
        }
        int res = e01z9RelaMapper.batchInsert(e01z9Relas);
        return res;
    }

    @Override
    public int deleteE01z9Rela(String id) {
        QueryWrapper<E01z9Rela> wrapper = new QueryWrapper<>();
        wrapper.eq("e01z9_id",id);
        int res = e01z9RelaMapper.delete(wrapper);
        return res;
    }

    @Override
    public List<Map<String, Object>> queryListByA01AndText(String userId, String a0101,String deptName,Integer pageNum,Integer pageSize) {
        LambdaQueryWrapper<E01z9Rela> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(E01z9Rela::getA0101,a0101);
        wrapper.like(E01z9Rela::getDeptName,deptName);
        List<E01z9Rela> e01z9Relas=e01z9RelaMapper.selectList(wrapper);
        List<String> e01zcIds=e01z9Relas.stream().map(e->e.getE01z9Id()).distinct().collect(Collectors.toList());
        if (e01zcIds.size()==0){
            return null;
        }

        List<Map<String,Object>> list=new ArrayList<>();
        LambdaQueryWrapper<E01z9> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(E01z9::getId,e01zcIds);
        queryWrapper.eq(E01z9::getUserId,userId);
        PageHelper.startPage(pageNum,pageSize);
        List<E01z9> e01z9List=e01z9Mapper.selectList(queryWrapper);
        if (e01z9List.size()==0){
            return null;
        }
        for (int i=0;i<e01z9List.size();i++){
            Map<String, Object> map = null;
            try {
                map= Map2Bean.bean2map(e01z9List.get(i));
                LambdaQueryWrapper<E01z9Rela> wrapper1=new LambdaQueryWrapper<>();
                wrapper1.eq(E01z9Rela::getE01z9Id,e01z9List.get(i).getId());
                List<E01z9Rela> e01z9RelaList=e01z9RelaMapper.selectList(wrapper1);
                map.put("children",e01z9RelaList);
                list.add(map);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<E01z9Rela> quertNoApproval() {
        LambdaQueryWrapper<E01z9Rela> wrapper=new LambdaQueryWrapper();
        wrapper.eq(E01z9Rela::getFlag,"1");
        wrapper.groupBy(E01z9Rela::getE01z9Id);
        List<E01z9Rela> e01z9Relas=e01z9RelaMapper.selectList(wrapper);
        return e01z9Relas;
    }

    @Override
    public int ApprovalBorrow(String id, List<String> idList) {
        LambdaQueryWrapper<E01z9Rela> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(E01z9Rela::getE01z9Id,id);
        wrapper.ne(E01z9Rela::getId,idList);
        List<E01z9Rela> e01z9Relas=e01z9RelaMapper.selectList(wrapper);
        if (e01z9Relas==null||e01z9Relas.size()==0){
            return 0;
        }
        for (E01z9Rela e01z9Rela:e01z9Relas){
            e01z9Rela.setFlag("2");
        }
        int res=e01z9RelaMapper.updateBatch(e01z9Relas);
        return res;
    }

    @Override
    public int updateE01z9RelaByBean(E01z9Rela e01z9Rela) {
        LambdaQueryWrapper<E01z9Rela> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(E01z9Rela::getId,e01z9Rela.getId());
        int i=e01z9RelaMapper.update(e01z9Rela,wrapper);
        return i;
    }

    @Override
    public List<E01z9Rela> queryE01z9ListNotApprov(String e01z9Id) {
        LambdaQueryWrapper<E01z9Rela> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(E01z9Rela::getE01z9Id, e01z9Id);
        queryWrapper.eq(E01z9Rela::getFlag,"1");
        List<E01z9Rela> e01z9RelaList = e01z9RelaMapper.selectList(queryWrapper);
        return e01z9RelaList;
    }

    @Override
    public CommonResult queryE01z9RelasByE01z9Id(String id) {
        E01z9 e01z9 = new E01z9();
        e01z9.setId(id);
        List<Map<String,Object>> returnList=baseMapper.selectViewDetails(e01z9);
        if(returnList!=null&&returnList.size()>0){
            for(Map<String,Object> map :returnList){
                map.put("e01z111a","整本");
            }
            return CommonResult.success("获取成功",returnList);
        }else{
            return CommonResult.error("没有数据");
        }
    }
}
