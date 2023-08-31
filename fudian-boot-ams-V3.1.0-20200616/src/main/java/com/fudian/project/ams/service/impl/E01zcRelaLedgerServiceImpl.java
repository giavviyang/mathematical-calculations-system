package com.fudian.project.ams.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.ams.mapper.E01zcRelaLedgerMapper;
import com.fudian.project.ams.pojo.E01zcLedger;
import com.fudian.project.ams.pojo.E01zcRelaLedger;
import com.fudian.project.ams.pojo.TransferBatch;
import com.fudian.project.ams.service.E01zcRelaLedgerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.sql.NClob;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Author: WangYuZhuo
* @Date: 2020/3/13 10:32
* @Desc:
*/
@Service
public class E01zcRelaLedgerServiceImpl extends ServiceImpl<E01zcRelaLedgerMapper, E01zcRelaLedger> implements E01zcRelaLedgerService {

    @Override
    public CommonGridResult queryE01zcList(E01zcLedger e01zcLedger)  {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(e01zcLedger.getPageNum(), e01zcLedger.getPageSize());
        // 年度检索
        if(e01zcLedger.getOtherParam()!=null&&!"".equals(e01zcLedger.getOtherParam())){
            map.put("otherParam",e01zcLedger.getOtherParam());
        }
        if(e01zcLedger.getApprovTime()!=null){
            map.put("appTime",new SimpleDateFormat("YYYY").format(e01zcLedger.getApprovTime()));
        }
        // 普通条件区检索
        if(e01zcLedger.getUserName()!=null&&!"".equals(e01zcLedger.getUserName())){
            map.put("personName",e01zcLedger.getUserName());
        }
        if(e01zcLedger.getBeginTime()!=null&&!"".equals(e01zcLedger.getBeginTime())){
            map.put("beginTime",e01zcLedger.getBeginTime());
        }
        if(e01zcLedger.getEndTime()!=null&&!"".equals(e01zcLedger.getEndTime())){
            map.put("endTime",e01zcLedger.getEndTime());
        }
        List<Map<String,Object>> returnList=baseMapper.queryBillsByMap(map);
//        for (Map<String, Object> objectMap : returnList) {
//            objectMap.put("e01z1Id", StringUtils.ClobToString((NClob) objectMap.get("e01z1Id")));
//            objectMap.put("adoptE01z1Id", StringUtils.ClobToString((NClob) objectMap.get("adoptE01z1Id")));
//            objectMap.put("adoptE01z1Msg", StringUtils.ClobToString((NClob) objectMap.get("adoptE01z1Msg")));
//        }
        Page<TransferBatch> page = (Page) returnList;
        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setRows(returnList);
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setPageSize(page.getPageSize());
        return commonGridResult;
    }

}
