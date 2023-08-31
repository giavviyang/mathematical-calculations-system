package com.fudian.project.ams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.ams.mapper.E01z8RelaLedgerMapper;
import com.fudian.project.ams.mapper.E01z9RelaLedgerMapper;
import com.fudian.project.ams.pojo.E01z9Ledger;
import com.fudian.project.ams.pojo.E01z9RelaLedger;
import com.fudian.project.ams.pojo.TransferBatch;
import com.fudian.project.ams.service.E01z9RelaLedgerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @Author: WangYuZhuo
* @Date: 2020/3/13 10:32
* @Desc:
*/
@Service
public class E01z9RelaLedgerServiceImpl extends ServiceImpl<E01z9RelaLedgerMapper, E01z9RelaLedger> implements E01z9RelaLedgerService {

    @Autowired
    E01z8RelaLedgerMapper e01z8RelaLedgerMapper;

    @Override
    public List<Map<String, Object>> selectViewDetails(String e01z9Id) {
        return baseMapper.selectViewDetails(e01z9Id);
    }

    @Override
    public List<Map<String, Object>> selectA01sByE01z9Id(String e01z9Id) {
        LambdaQueryWrapper<E01z9RelaLedger> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(E01z9RelaLedger::getE01z9Id,e01z9Id);
        List<E01z9RelaLedger> list = this.list(lambdaQueryWrapper);
        List<String> stringList = list.stream().map(E01z9RelaLedger::getA01Id).collect(Collectors.toList());
        return this.e01z8RelaLedgerMapper.selectA01ByIds(stringList);
    }

    @Override
    public CommonGridResult queryE01z9List(E01z9Ledger e01z9Ledger) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(e01z9Ledger.getPageNum(), e01z9Ledger.getPageSize());
        if(e01z9Ledger.getCreatersTime()!=null){
            map.put("creaters",new SimpleDateFormat("YYYY").format(e01z9Ledger.getCreatersTime()));
        }
        // 普通条件区检索
        if(e01z9Ledger.getUserName()!=null&&!"".equals(e01z9Ledger.getUserName())){
            map.put("personName",e01z9Ledger.getUserName());
        }
        if(e01z9Ledger.getBeginTime()!=null&&!"".equals(e01z9Ledger.getBeginTime())){
            map.put("beginTime",e01z9Ledger.getBeginTime());
        }
        if(e01z9Ledger.getEndTime()!=null&&!"".equals(e01z9Ledger.getEndTime())){
            map.put("endTime",e01z9Ledger.getEndTime());
        }
        List<Map<String,Object>> returnList=baseMapper.queryBillsByMap(map);
        Page<TransferBatch> page = (Page) returnList;
        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setRows(returnList);
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setPageSize(page.getPageSize());
        return commonGridResult;
    }
}
