package com.fudian.project.ams.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.system.pojo.SysFile;
import com.fudian.api.system.service.SysFileApi;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.factory.FileStorageFactory;
import com.fudian.filestorage.factory.ImgDESFactory;
import com.fudian.filestorage.service.FileStorageService;
import com.fudian.project.ams.mapper.E01z1Mapper;
import com.fudian.project.ams.mapper.TransferBatchMapper;
import com.fudian.project.ams.mapper.TransferBillMapper;
import com.fudian.project.ams.pojo.*;
import com.fudian.project.ams.service.E01z1Service;
import com.fudian.project.ams.service.TransferBatchService;
import com.fudian.project.ams.service.TransferBillService;
import com.fudian.project.ams.utils.CreatePersonXml;
import com.fudian.project.ams.utils.ZipUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransferBillServiceImpl extends ServiceImpl<TransferBillMapper, TransferBill> implements TransferBillService {

    @Autowired
    private TransferBatchMapper transferBatchMapper;
    @Autowired
    private TransferBillMapper transferBillMapper;

    @Autowired
    private TransferBatchService transferBatchService;

    @Autowired
    private E01z1Mapper e01z1Mapper;

    @Autowired
    private SysFileApi sysFileService;


    @Value("${fileStorage.type}")
    private String fileStorageType;



    @Autowired
    private E01z1Service e01z1Service;


    @Override
    public long countByExample(TransferBillExample example) {
        return baseMapper.countByExample(example);
    }
    @Override
    public int deleteByExample(TransferBillExample example) {
        return baseMapper.deleteByExample(example);
    }
    @Override
    public List<TransferBill> selectByExample(TransferBillExample example) {
        return baseMapper.selectByExample(example);
    }
    @Override
    public int updateByExampleSelective(TransferBill record, TransferBillExample example) {
        return baseMapper.updateByExampleSelective(record,example);
    }
    @Override
    public int updateByExample(TransferBill record, TransferBillExample example) {
        return baseMapper.updateByExample(record,example);
    }
    @Override
    public int updateBatch(List<TransferBill> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<TransferBill> list) {
        this.saveBatch(list);
//        for (TransferBill transferBill : list) {
//            baseMapper.insert(transferBill);
//        }
        return list.size();
    }
    @Override
    public int insertOrUpdate(TransferBill record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(TransferBill record) {
        return baseMapper.insertOrUpdateSelective(record);
    }


    @Override
    public CommonGridResult queryTransferBillList(TransferBill transferBill) {
        Map<String,Object> map = new HashMap<>();
        /*// 年度检索
        if(transferBill.getOtherParam()!=null&&!"".equals(transferBill.getOtherParam())){
            map.put("otherParam",transferBill.getOtherParam());
        }*/
        // 普通条件区检索
        if (transferBill.getSearchValue()!=null && !"".equals(transferBill.getSearchValue())) {
            Map<String, String> transferMaps = JSONArray.parseObject(transferBill.getSearchValue(), Map.class);
            if (transferMaps.get("personName") != null && !"".equals(transferMaps.get("personName"))) {
                String name = transferMaps.get("personName").toLowerCase();
                map.put("personName", name);
            }
            if (transferMaps.get("deptName") != null && !"".equals(transferMaps.get("deptName"))) {
                map.put("deptName", transferMaps.get("deptName"));
            }
            /*if (transferBill.getBeginTime() != null && transferBill.getEndTime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                map.put("beginTime", sdf.format(transferBill.getBeginTime()));
                map.put("endTime", sdf.format(transferBill.getEndTime()));
            }*/
            if (transferMaps.get("id") != null && !"".equals(transferMaps.get("id"))) {
                map.put("transId", transferMaps.get("id"));
            }
            if (transferMaps.get("checkYear") != null && !"".equals(transferMaps.get("checkYear"))) {
                map.put("checkYear", transferMaps.get("checkYear"));
            }
            /*if (transferMaps.get("deptId") != null && !"".equals(transferMaps.get("deptId"))) {
                map.put("deptId", transferMaps.get("deptId"));
            }*/
        }
        PageHelper.startPage(transferBill.getPageNum(), transferBill.getPageSize());
        List<Map<String,Object>> returnList=baseMapper.queryBillsByMap(map);
        TreeSet<String> years = new TreeSet<>();
        for (Map<String,Object> map1 : returnList) {
            try {
                if(map1.get("e01z701")!=null&&!"".equals(map1.get("e01z701"))){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date date =sdf.parse(map1.get("e01z701").toString());
                    map1.put("e01z701",date);
                    String year = map1.get("e01z701").toString().substring(0,4);
                    years.add(year);
                    map1.put("children",years);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Page<TransferBatch> page = (Page) returnList;
        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setRows(returnList);
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setPageSize(page.getPageSize());
        return commonGridResult;
    }

    @Override
    @Transactional
    public CommonResult batchDeleteTransferBill(TransferBill transferBill) {
        String jsonStr  = transferBill.getJsonStr();
        List<Map<String, Object>> billMaps = JSONArray.parseObject(jsonStr, List.class);
        LambdaQueryWrapper<TransferBill> lambdaQueryWrapper =  new LambdaQueryWrapper<>();
        String transId="";
        for(Map<String,Object> map:billMaps){
            lambdaQueryWrapper.or(wrapper -> wrapper.eq(TransferBill::getTransId,map.get("transId")).eq(TransferBill::getTransBillIndex,map.get("transBillIndex")));
            transId=map.get("transId").toString();
        }

        LambdaQueryWrapper<TransferBill> transferBillLambdaQueryWrapper =new LambdaQueryWrapper<>();
        transferBillLambdaQueryWrapper.eq(TransferBill::getTransId,transId);
        List<TransferBill> list = this.list(transferBillLambdaQueryWrapper);
        boolean b = this.remove(lambdaQueryWrapper);
        if(list.size()==billMaps.size()){
            LambdaQueryWrapper<TransferBatch> transferBatchLambdaQueryWrapper =new LambdaQueryWrapper<>();
            transferBatchLambdaQueryWrapper.eq(TransferBatch::getId,transId);
            if(!transferBatchService.remove(transferBatchLambdaQueryWrapper)){
                return CommonResult.error(500,"删除失败");
            }
        }else{
            int newTransPersonNum = list.size()-billMaps.size();
            LambdaQueryWrapper<TransferBatch> transferBatchLambdaQueryWrapper =new LambdaQueryWrapper<>();
            transferBatchLambdaQueryWrapper.eq(TransferBatch::getId,transId);
            TransferBatch transferBatch = transferBatchService.getOne(transferBatchLambdaQueryWrapper);
            transferBatch.setPersonNum(newTransPersonNum);
            transferBatchService.updateById(transferBatch);
        }
        if(b){
            return CommonResult.success("删除成功");
        }else{
            return CommonResult.error(500,"删除失败");
        }
    }

    @Override
    public List<Map<String, Object>> queryhighDetail(String data) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("data",data);
        List<Map<String,Object>> list=transferBillMapper.highDetail(stringStringHashMap);
        return list;
    }

    private  byte[] isBytes(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
