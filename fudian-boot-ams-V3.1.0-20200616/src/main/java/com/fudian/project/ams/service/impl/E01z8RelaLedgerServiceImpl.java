package com.fudian.project.ams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.ams.mapper.E01z8RelaLedgerMapper;
import com.fudian.project.ams.pojo.E01z8Ledger;
import com.fudian.project.ams.pojo.E01z8RelaLedger;
import com.fudian.project.ams.pojo.TransferBatch;
import com.fudian.project.ams.service.E01z8RelaLedgerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
public class E01z8RelaLedgerServiceImpl extends ServiceImpl<E01z8RelaLedgerMapper, E01z8RelaLedger> implements E01z8RelaLedgerService {

    @Override
    public List<Map<String, Object>> selectViewDetails(String e01z8Id) {
        return this.baseMapper.selectViewDetails(e01z8Id);
    }

    @Override
    public List<Map<String, Object>> selectA01sByE01z8Id(String e01z8Id) {
        LambdaQueryWrapper<E01z8RelaLedger> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(E01z8RelaLedger::getE01z8Id,e01z8Id);
        List<E01z8RelaLedger> list = this.list(lambdaQueryWrapper);
        List<String> stringList = list.stream().map(E01z8RelaLedger::getA01Id).collect(Collectors.toList());
        return this.baseMapper.selectA01ByIds(stringList);
    }

    @Override
    public CommonGridResult queryE01z8List(E01z8Ledger e01z8Ledger) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(e01z8Ledger.getPageNum(), e01z8Ledger.getPageSize());

        if(e01z8Ledger.getCreatersTime()!=null){
            map.put("creaters",new SimpleDateFormat("YYYY").format(e01z8Ledger.getCreatersTime()));
        }
        // 普通条件区检索
        if(e01z8Ledger.getUserName()!=null&&!"".equals(e01z8Ledger.getUserName())){
            map.put("personName",e01z8Ledger.getUserName());
        }
        if(e01z8Ledger.getBeginTime()!=null&&!"".equals(e01z8Ledger.getBeginTime())){
            map.put("beginTime",e01z8Ledger.getBeginTime());
        }
        if(e01z8Ledger.getEndTime()!=null&&!"".equals(e01z8Ledger.getEndTime())){
            map.put("endTime",e01z8Ledger.getEndTime());
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
