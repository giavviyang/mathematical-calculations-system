package com.fudian.project.ams.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.ams.pojo.E01zcRela;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.ams.mapper.E01zcLedgerMapper;
import com.fudian.project.ams.pojo.E01zc;
import com.fudian.project.ams.pojo.E01zcLedger;
import com.fudian.project.ams.pojo.E01zcRelaLedger;
import com.fudian.project.ams.service.E01zcLedgerService;
import com.fudian.project.ams.service.E01zcRelaLedgerService;
import com.fudian.project.ams.service.E01zcRelaService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
* @Author: WangYuZhuo
* @Date: 2020/3/13 10:32
* @Desc:
*/
@Service
public class E01zcLedgerServiceImpl extends ServiceImpl<E01zcLedgerMapper, E01zcLedger> implements E01zcLedgerService {


    @Autowired
    E01zcRelaLedgerService e01zcRelaLedgerService;

    @Autowired
    E01zcRelaService e01zcRelaService;

    /**
     * 查询电子档案查阅台账
     * @param e01zcLedger
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public CommonGridResult queryE01zcLedgerService(E01zcLedger e01zcLedger, Integer pageNum, Integer pageSize) {
        CommonGridResult commonGridResult = new CommonGridResult();
        LambdaQueryWrapper<E01zcLedger> wrapper = new LambdaQueryWrapper<>();

        if(e01zcLedger.getUserName()!=null&&!"".equals(e01zcLedger.getUserName())){
            wrapper.like(E01zcLedger::getUserName,e01zcLedger.getUserName());
        }

        if(e01zcLedger.getApprovTime()!=null){
            wrapper.like(E01zcLedger::getApprovTime,new SimpleDateFormat("YYYY").format(e01zcLedger.getApprovTime()));
        }

        if(e01zcLedger.getBeginTime()!=null){
            wrapper.ge(E01zcLedger::getEndDate,e01zcLedger.getBeginTime());
        }

        if(e01zcLedger.getEndTime()!=null){
            wrapper.le(E01zcLedger::getEndDate,e01zcLedger.getEndTime());
        }

        wrapper.orderByDesc(E01zcLedger::getApprovTime);
        PageHelper.startPage(pageNum,pageSize);
        List<E01zcLedger> list = this.list(wrapper);
        Page page = (Page) list;
        commonGridResult.setRows(page.getResult());
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setPageNum(pageNum);
        commonGridResult.setPageSize(pageSize);
        return commonGridResult;
    }

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/13 17:31
    * @Return: 同步查阅申请
    * @Param:
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateE01zcAndRela(E01zc e01zc) {
        JSONObject jsonE01zc = (JSONObject) JSONObject.toJSON(e01zc);

        List<E01zcRela> list = this.e01zcRelaService.list(new LambdaQueryWrapper<E01zcRela>().eq(E01zcRela::getE01zcId,e01zc.getId()));

        JSONArray jsonE01zcRelas = (JSONArray) JSONArray.toJSON(list);

        E01zcLedger e01zcLedger = JSON.toJavaObject(jsonE01zc, E01zcLedger.class);
        e01zcLedger.setType("查阅申请");

        boolean b = this.saveOrUpdate(e01zcLedger);

        if(list.size()>0){
            List<E01zcRelaLedger> e01zcRelaLedgers = JSONArray.parseArray(jsonE01zcRelas.toJSONString(), E01zcRelaLedger.class);
            b = e01zcRelaLedgerService.saveOrUpdateBatch(e01zcRelaLedgers);
        }

        return b;
    }

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/13 17:31
    * @Return: 同步查阅申请
    * @Param:
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean InsertE01zcAndRela(E01zc e01zc) {
        JSONObject jsonE01zc = (JSONObject) JSONObject.toJSON(e01zc);

        List<E01zcRela> list = this.e01zcRelaService.list(new LambdaQueryWrapper<E01zcRela>().eq(E01zcRela::getE01zcId,e01zc.getId()));

        JSONArray jsonE01zcRelas = (JSONArray) JSONArray.toJSON(list);

        E01zcLedger e01zcLedger = JSON.toJavaObject(jsonE01zc, E01zcLedger.class);

        List<E01zcRelaLedger> e01zcRelaLedgers = JSONArray.parseArray(jsonE01zcRelas.toJSONString(), E01zcRelaLedger.class);

        boolean b ;

        b =  this.save(e01zcLedger);

        b = this.e01zcRelaLedgerService.saveBatch(e01zcRelaLedgers);

        return b;
    }
}
