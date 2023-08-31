package com.fudian.project.ams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.mapper.E01z8Mapper;
import com.fudian.project.ams.mapper.E01z8RelaMapper;
import com.fudian.project.ams.pojo.E01z8;
import com.fudian.project.ams.pojo.E01z8Rela;
import com.fudian.project.ams.service.E01z8RelaService;
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
public class E01z8RelaServiceImpl extends ServiceImpl<E01z8RelaMapper, E01z8Rela> implements E01z8RelaService {
    @Autowired
    private E01z8RelaMapper e01z8RelaMapper;
    @Autowired
    private E01z8Mapper e01z8Mapper;

    @Override
    public int updateBatch(List<E01z8Rela> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<E01z8Rela> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(E01z8Rela record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(E01z8Rela record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public List<E01z8Rela> queryE01z8RelaList(String e01z8Id) {
        LambdaQueryWrapper<E01z8Rela> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(E01z8Rela::getE01z8Id,e01z8Id);
//        queryWrapper.eq(E01z8Rela::getFlag,"1");
        List<E01z8Rela> e01z8Relas=e01z8RelaMapper.selectList(queryWrapper);
        return e01z8Relas;
    }

    @Override
    public int updateE01z8RelaById(E01z8Rela e01z8Rela) {
        E01z8Rela rela=e01z8RelaMapper.selectById(e01z8Rela.getId());
        rela.setFeedback(e01z8Rela.getFeedback());
        int res=0;
        //通过wrapper进行修改(update)
//        UpdateWrapper<E01z8Rela> updateWrapper=new UpdateWrapper<>();
//        updateWrapper.eq("id",e01z8Rela.getId());
//        res=e01z8RelaMapper.update(e01z8Rela,updateWrapper);

        try {
            res=e01z8RelaMapper.updateById(rela);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int insertE01zcRelaList(String uid,List<E01z8Rela> e01z8RelaList) {
        for (int i = 0; i < e01z8RelaList.size(); i++) {
            String id= UUID.randomUUID().toString().replace("-","");
            E01z8Rela e01z8Rela=e01z8RelaList.get(i);
            e01z8Rela.setId(id);
            e01z8Rela.setFlag("1");
            e01z8Rela.setE01z8Id(uid);
        }
        int res=e01z8RelaMapper.batchInsert(e01z8RelaList);
        return res;
    }

    @Override
    public int deleteE01z8Rela(String id) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("e01z8_id",id);
        int i=e01z8RelaMapper.delete(wrapper);
        return i;
    }

    @Override
    public List<Map<String, Object>> queryListByA01AndText(String userId, String a0101,String deptName,Integer pageNum,Integer pageSize) {
        LambdaQueryWrapper<E01z8Rela> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(E01z8Rela::getA0101,a0101);
        wrapper.like(E01z8Rela::getDeptName,deptName);
        List<E01z8Rela> e01z8RelaList=e01z8RelaMapper.selectList(wrapper);
        List<String> e01z8Ids=e01z8RelaList.stream().map(e->e.getE01z8Id()).distinct().collect(Collectors.toList());
        if (e01z8Ids.size()==0){
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        LambdaQueryWrapper<E01z8> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(E01z8::getId, e01z8Ids);
        queryWrapper.eq(E01z8::getUserId, userId);
        PageHelper.startPage(pageNum,pageSize);
        List<E01z8> e01z8List=e01z8Mapper.selectList(queryWrapper);
        if (e01z8List.size()==0){
            return null;
        }
        for (int i=0;i<e01z8List.size();i++){
            Map<String, Object> map = null;
            try {
                map = Map2Bean.bean2map(e01z8List.get(i));
                LambdaQueryWrapper<E01z8Rela> wrapper1=new LambdaQueryWrapper<>();
                wrapper1.eq(E01z8Rela::getE01z8Id,e01z8List.get(i).getId());
                List<E01z8Rela> e01z8Relas=e01z8RelaMapper.selectList(wrapper1);
                map.put("children",e01z8Relas);
                list.add(map);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<E01z8Rela> quertNoApproval() {
        LambdaQueryWrapper<E01z8Rela> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(E01z8Rela::getFlag,"1");
        wrapper.groupBy(E01z8Rela::getE01z8Id);
        List<E01z8Rela> e01z8Relas=e01z8RelaMapper.selectList(wrapper);
        return e01z8Relas;
    }

    @Override
    public int ApprovalEntity(String id, List<String> idList) {
        LambdaQueryWrapper<E01z8Rela> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(E01z8Rela::getE01z8Id,id);
        wrapper.ne(E01z8Rela::getId,idList);
        List<E01z8Rela> e01z8RelaList=e01z8RelaMapper.selectList(wrapper);
        if (e01z8RelaList==null||e01z8RelaList.size()==0){
            return 0;
        }
        for (E01z8Rela e01z8Rela:e01z8RelaList){
            e01z8Rela.setFlag("2");
        }
        int res=e01z8RelaMapper.updateBatch(e01z8RelaList);
        return res;
    }

    @Override
    public int updateE01z8Rela(E01z8Rela e01z8Rela) {
        LambdaQueryWrapper<E01z8Rela> wrapper=new LambdaQueryWrapper();
        wrapper.eq(E01z8Rela::getId,e01z8Rela.getId());
        int i=e01z8RelaMapper.update(e01z8Rela,wrapper);
        return i;
    }

    @Override
    public List<E01z8Rela> queryE01z8RelaListNotApprov(String e01z8Id) {
        LambdaQueryWrapper<E01z8Rela> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(E01z8Rela::getE01z8Id,e01z8Id);
        queryWrapper.eq(E01z8Rela::getFlag,"1");
        List<E01z8Rela> e01z8Relas=e01z8RelaMapper.selectList(queryWrapper);
        return e01z8Relas;
    }

    @Override
    public CommonResult queryE01z8RelasByE01z8Id(String id) {
        E01z8 e01z8 = new E01z8();
        e01z8.setId(id);
        List<Map<String,Object>> returnList=baseMapper.selectViewDetails(e01z8);
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
