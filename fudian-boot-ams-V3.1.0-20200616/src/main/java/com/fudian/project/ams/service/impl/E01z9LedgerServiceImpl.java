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
import com.fudian.project.ams.mapper.E01z9LedgerMapper;
import com.fudian.project.ams.pojo.E01z9;
import com.fudian.project.ams.pojo.E01z9Ledger;
import com.fudian.project.ams.pojo.E01z9Rela;
import com.fudian.project.ams.pojo.E01z9RelaLedger;
import com.fudian.project.ams.service.E01z9LedgerService;
import com.fudian.project.ams.service.E01z9RelaLedgerService;
import com.fudian.project.ams.service.E01z9RelaService;
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
 * @Date: 2020/3/13 10:32
 * @Desc:
 */
@Service
public class E01z9LedgerServiceImpl extends ServiceImpl<E01z9LedgerMapper, E01z9Ledger> implements E01z9LedgerService {


    @Autowired
    E01z9RelaLedgerService e01z9RelaLedgerService;

    @Autowired
    E01z9RelaService e01z9RelaService;

    @Value("${fileStorage.type}")
    private String fileStorageType;

    @Autowired
    SysFileApi sysFileService;

    /**
     * 查询电子档案查阅台账
     *
     * @param e01z9Ledger
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public CommonGridResult queryE01z9LedgerService(E01z9Ledger e01z9Ledger, Integer pageNum, Integer pageSize) {
        CommonGridResult commonGridResult = new CommonGridResult();
        LambdaQueryWrapper<E01z9Ledger> wrapper = new LambdaQueryWrapper<>();

        if (e01z9Ledger.getUserName() != null && !"".equals(e01z9Ledger.getUserName())) {
            wrapper.like(E01z9Ledger::getUserName, e01z9Ledger.getUserName());
        }

        if (e01z9Ledger.getApprovTime() != null) {
            wrapper.like(E01z9Ledger::getApprovTime, new SimpleDateFormat("YYYY").format(e01z9Ledger.getApprovTime()));
        }

        if (e01z9Ledger.getBeginTime() != null) {
            wrapper.ge(E01z9Ledger::getEndTime, e01z9Ledger.getBeginTime());
        }

        if (e01z9Ledger.getEndTime() != null) {
            wrapper.le(E01z9Ledger::getEndTime, e01z9Ledger.getEndTime());
        }

        wrapper.orderByDesc(E01z9Ledger::getApprovTime);
        PageHelper.startPage(pageNum, pageSize);
        List<E01z9Ledger> list = this.list(wrapper);
        Page page = (Page) list;
        commonGridResult.setRows(page.getResult());
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setPageNum(pageNum);
        commonGridResult.setPageSize(pageSize);
        return commonGridResult;
    }

    /**
     * @Author: wangyuzhuo
     * @Date: 2020/3/13 17:33
     * @Return: 同步借阅申请
     * @Param:
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateE01z9AndRela(E01z9 e01z9) {
        JSONObject jsone01z9 = (JSONObject) JSONObject.toJSON(e01z9);

        List<E01z9Rela> list = this.e01z9RelaService.list(new LambdaQueryWrapper<E01z9Rela>().eq(E01z9Rela::getE01z9Id, e01z9.getId()));

        JSONArray jsone01z9Relas = (JSONArray) JSONArray.toJSON(list);

        E01z9Ledger e01z9Ledger = JSON.toJavaObject(jsone01z9, E01z9Ledger.class);
        e01z9Ledger.setApplyStatus("查阅申请");

        boolean b = this.saveOrUpdate(e01z9Ledger);

        if (list.size() > 0) {
            List<E01z9RelaLedger> e01z9RelaLedgers = JSONArray.parseArray(jsone01z9Relas.toJSONString(), E01z9RelaLedger.class);
            b = e01z9RelaLedgerService.saveOrUpdateBatch(e01z9RelaLedgers);
        }

        return b;
    }

    /**
     * @Author: wangyuzhuo
     * @Date: 2020/3/13 17:33
     * @Return: 同步借阅申请
     * @Param:
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean InsertE01z9AndRela(E01z9 e01z9) {
        JSONObject jsone01z9 = (JSONObject) JSONObject.toJSON(e01z9);

        List<E01z9Rela> list = this.e01z9RelaService.list(new LambdaQueryWrapper<E01z9Rela>().eq(E01z9Rela::getE01z9Id, e01z9.getId()));

        JSONArray jsone01z9Relas = (JSONArray) JSONArray.toJSON(list);

        E01z9Ledger e01z9Ledger = JSON.toJavaObject(jsone01z9, E01z9Ledger.class);

        List<E01z9RelaLedger> e01z9RelaLedgers = JSONArray.parseArray(jsone01z9Relas.toJSONString(), E01z9RelaLedger.class);

        boolean b;

        b = this.save(e01z9Ledger);

        b = this.e01z9RelaLedgerService.saveBatch(e01z9RelaLedgers);

        return b;
    }

    @Override
    public CommonResult updateByEntityAndList(MultipartFile files, Map<String, Object> data) {
        JSONObject e01z9Str = JSON.parseObject(data.get("formValues").toString());
        String e01z9Id=e01z9Str.get("e01z9Id").toString();
        E01z9Ledger e01z9Ledger = JSONObject.toJavaObject(e01z9Str, E01z9Ledger.class);
        e01z9Ledger.setId(e01z9Id);
        if (e01z9Ledger.getStatus() == null) {
            e01z9Ledger.setStatus(0);//待审批
        }
        e01z9Ledger.setApplyStatus("实体档案借阅");
        e01z9Ledger.setCreatersTime(new Date());

        List<Map<String, Object>> e01z9Relas = (List<Map<String, Object>>) data.get("gridValues");
        e01z9Ledger.setApplyNumber(e01z9Relas.size());
        List<E01z9RelaLedger> list = new ArrayList<>();
        for (Map<String, Object> map : e01z9Relas) {
            E01z9RelaLedger e01z9Rela = new E01z9RelaLedger();
            e01z9Rela.setId(UUID.randomUUID().toString().replace("-", ""));
            e01z9Rela.setE01z9Id(e01z9Ledger.getId());
            e01z9Rela.setA01Id(map.get("a01Id").toString());
            list.add(e01z9Rela);
        }
        if (files != null) {
            FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
            SysFile sysFile = new SysFile();
            String fileSourceId = fileStorageService.uploadFile(files);
            sysFile.setFileName(files.getOriginalFilename());
            sysFile.setBindingId(e01z9Ledger.getId());
            sysFile.setFileType(files.getOriginalFilename().split("\\.")[files.getOriginalFilename().split("\\.").length - 1]);
            sysFile.setId(StringUtils.uuid());
            sysFile.setFileSize((int) files.getSize());
            sysFile.setFileStorageMode("2");
            sysFile.setFileSource(fileSourceId);
            LambdaQueryWrapper<SysFile> sysFileLam = new LambdaQueryWrapper<>();
            sysFileLam.eq(SysFile::getBindingId, e01z9Ledger.getId());
            SysFile sysFile1 = sysFileService.getOne(sysFileLam);
            if (sysFile1 != null) {//删除电子文件
                fileStorageService.deleteFile(sysFile1.getFileSource());
            }
            sysFileService.remove(sysFileLam);
            sysFileService.save(sysFile);
        }
        baseMapper.updateById(e01z9Ledger);
        //删除之前的
        e01z9RelaLedgerService.remove(new LambdaQueryWrapper<E01z9RelaLedger>().eq(E01z9RelaLedger::getE01z9Id, e01z9Ledger.getId()));
        //添加现在的
        e01z9RelaLedgerService.saveBatch(list);
        return CommonResult.success("申请成功");
    }

    @Override
    public int updateBatch(List<E01z9Ledger> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<E01z9Ledger> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(E01z9Ledger record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(E01z9Ledger record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

