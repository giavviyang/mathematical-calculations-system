package com.fudian.project.ams.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.SysFile;
import com.fudian.api.system.service.SysFileApi;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.factory.FileStorageFactory;
import com.fudian.filestorage.service.FileStorageService;
import com.fudian.project.ams.mapper.E01z8LedgerMapper;
import com.fudian.project.ams.pojo.E01z8;
import com.fudian.project.ams.pojo.E01z8Ledger;
import com.fudian.project.ams.pojo.E01z8Rela;
import com.fudian.project.ams.pojo.E01z8RelaLedger;
import com.fudian.project.ams.service.E01z8LedgerService;
import com.fudian.project.ams.service.E01z8RelaLedgerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/13 10:28
 * @Desc:
 */
@Service
public class E01z8LedgerServiceImpl extends ServiceImpl<E01z8LedgerMapper, E01z8Ledger> implements E01z8LedgerService {

    @Autowired
    E01z8RelaLedgerService e01z8RelaLedgerService;

    @Autowired
    com.fudian.project.ams.service.E01z8RelaService E01z8RelaService;

    @Autowired
    E01z8LedgerMapper e01z8RelaMapper;

    @Value("${fileStorage.type}")
    private String fileStorageType;

    @Autowired
    SysFileApi sysFileService;

    /**
     * 查询电子档案查阅台账
     *
     * @param e01z8Ledger
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public CommonGridResult queryE01z8LedgerService(E01z8Ledger e01z8Ledger, Integer pageNum, Integer pageSize) {
        CommonGridResult commonGridResult = new CommonGridResult();
        LambdaQueryWrapper<E01z8Ledger> wrapper = new LambdaQueryWrapper<>();

        if (e01z8Ledger.getUserName() != null && !"".equals(e01z8Ledger.getUserName())) {
            wrapper.like(E01z8Ledger::getUserName, e01z8Ledger.getUserName());
        }

        if (e01z8Ledger.getApprovTime() != null) {
            wrapper.like(E01z8Ledger::getApprovTime, new SimpleDateFormat("YYYY").format(e01z8Ledger.getApprovTime()));
        }

        if (e01z8Ledger.getBeginTime() != null) {
            wrapper.ge(E01z8Ledger::getEndTime, e01z8Ledger.getBeginTime());
        }

        if (e01z8Ledger.getEndTime() != null) {
            wrapper.le(E01z8Ledger::getEndTime, e01z8Ledger.getEndTime());
        }

        wrapper.orderByDesc(E01z8Ledger::getApprovTime);
        PageHelper.startPage(pageNum, pageSize);
        List<E01z8Ledger> list = this.list(wrapper);
        Page page = (Page) list;
        commonGridResult.setRows(page.getResult());
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setPageNum(pageNum);
        commonGridResult.setPageSize(pageSize);
        return commonGridResult;
    }

    /**
     * @Author: wangyuzhuo
     * @Date: 2020/3/13 17:32
     * @Return: boolean 同步实体查阅申请
     * @Param: [e01z8]
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateE01z8AndRela(E01z8 E01z8) {
        JSONObject jsonE01z8 = (JSONObject) JSONObject.toJSON(E01z8);

        List<E01z8Rela> list = this.E01z8RelaService.list(new LambdaQueryWrapper<E01z8Rela>().eq(E01z8Rela::getE01z8Id, E01z8.getId()));

        JSONArray jsonE01z8Relas = (JSONArray) JSONArray.toJSON(list);

        E01z8Ledger E01z8Ledger = JSON.toJavaObject(jsonE01z8, com.fudian.project.ams.pojo.E01z8Ledger.class);
        E01z8Ledger.setApplyStatus("查阅申请");

        boolean b = this.saveOrUpdate(E01z8Ledger);

        if (list.size() > 0) {
            List<E01z8RelaLedger> E01z8RelaLedgers = JSONArray.parseArray(jsonE01z8Relas.toJSONString(), E01z8RelaLedger.class);
            b = e01z8RelaLedgerService.saveOrUpdateBatch(E01z8RelaLedgers);
        }

        return b;
    }

    /**
     * @Author: wangyuzhuo
     * @Date: 2020/3/13 17:32
     * @Return: boolean 同步实体查阅申请
     * @Param: [e01z8]
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean InsertE01z8AndRela(E01z8 e01z8) {
        JSONObject jsonE01z8 = (JSONObject) JSONObject.toJSON(e01z8);

        List<E01z8Rela> list = this.E01z8RelaService.list(new LambdaQueryWrapper<E01z8Rela>().eq(E01z8Rela::getE01z8Id, e01z8.getId()));

        JSONArray jsonE01z8Relas = (JSONArray) JSONArray.toJSON(list);

        E01z8Ledger e01z8Ledger = JSON.toJavaObject(jsonE01z8, E01z8Ledger.class);

        List<E01z8RelaLedger> E01z8RelaLedgers = JSONArray.parseArray(jsonE01z8Relas.toJSONString(), E01z8RelaLedger.class);

        boolean b;

        b = this.save(e01z8Ledger);

        b = this.e01z8RelaLedgerService.saveBatch(E01z8RelaLedgers);

        return b;
    }

    /**
     * @Author: wangyuzhuo
     * @Date: 2020/3/14 11:50
     * @Return: 修改实体查阅台账
     * @Param:
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult updateByEntityAndList(MultipartFile files, Map<String, Object> data) {
        JSONObject e01z8Str = JSON.parseObject(data.get("formValues").toString());
        String e01z8Id=e01z8Str.get("e01z8Id").toString();
        E01z8Ledger e01z8Ledger = JSONObject.toJavaObject(e01z8Str, E01z8Ledger.class);
        e01z8Ledger.setId(e01z8Id);
//        String e01z8Id = UUID.randomUUID().toString().replace("-", "");
        if (e01z8Ledger.getStatus() == null) {
            e01z8Ledger.setStatus(0);//待审批
        }
        e01z8Ledger.setApplyStatus("实体档案查阅");
        e01z8Ledger.setCreatersTime(new Date());
        List<Map<String, Object>> e01z8Relas = (List<Map<String, Object>>) data.get("gridValues");
        e01z8Ledger.setApplyNumber(e01z8Relas.size());
        List<E01z8RelaLedger> list = new ArrayList<>();
        for (Map<String, Object> map : e01z8Relas) {
            E01z8RelaLedger e01z8Rela = new E01z8RelaLedger();
            e01z8Rela.setId(UUID.randomUUID().toString().replace("-", ""));
            e01z8Rela.setE01z8Id(e01z8Ledger.getId());
            e01z8Rela.setA01Id(map.get("a01Id").toString());
            list.add(e01z8Rela);
        }
        if (files != null) {
            FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
            SysFile sysFile = new SysFile();
            String fileSourceId = fileStorageService.uploadFile(files);
            sysFile.setFileName(files.getOriginalFilename());
            sysFile.setBindingId(e01z8Ledger.getId());
            sysFile.setFileType(files.getOriginalFilename().split("\\.")[files.getOriginalFilename().split("\\.").length - 1]);
            sysFile.setId(StringUtils.uuid());
            sysFile.setFileSize((int) files.getSize());
            sysFile.setFileStorageMode("2");
            sysFile.setFileSource(fileSourceId);
            LambdaQueryWrapper<SysFile> sysFileLam = new LambdaQueryWrapper<>();
            sysFileLam.eq(SysFile::getBindingId, e01z8Ledger.getId());
            SysFile sysFile1 = sysFileService.getOne(sysFileLam);
            if (sysFile1 != null) {//删除电子文件
                fileStorageService.deleteFile(sysFile1.getFileSource());
            }
            sysFileService.remove(sysFileLam);
            sysFileService.save(sysFile);
        }
        baseMapper.updateById(e01z8Ledger);
        //删除之前的
        e01z8RelaLedgerService.remove(new LambdaQueryWrapper<E01z8RelaLedger>().eq(E01z8RelaLedger::getE01z8Id, e01z8Ledger.getId()));
        //添加现在的
        e01z8RelaLedgerService.saveBatch(list);
        return CommonResult.success("申请成功");
    }

    @Override
    public int updateBatch(List<E01z8Ledger> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<E01z8Ledger> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(E01z8Ledger record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(E01z8Ledger record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


