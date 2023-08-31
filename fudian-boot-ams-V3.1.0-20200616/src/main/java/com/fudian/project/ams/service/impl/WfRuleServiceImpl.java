package com.fudian.project.ams.service.impl;

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
import com.fudian.project.ams.mapper.WfRuleMapper;
import com.fudian.project.ams.pojo.WfRule;
import com.fudian.project.ams.service.WfRuleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WfRuleServiceImpl extends ServiceImpl<WfRuleMapper, WfRule> implements WfRuleService {

    @Value("${fileStorage.type}")
    private String fileStorageType;

    @Autowired
    private SysFileApi sysFileService;

    @Override
    public int updateBatch(List<WfRule> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<WfRule> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<WfRule> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(WfRule record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(WfRule record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Transactional
    @Override
    public CommonResult insertData(WfRule wfRule, MultipartFile[] files) {
        String ruleId= StringUtils.uuid();
        wfRule.setRuleId(ruleId);
        wfRule.setCreateTime(new Date());
        this.insertOrUpdate(wfRule);
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
        List<SysFile> list = new ArrayList<>();
        for(MultipartFile multipartFile :files){
            SysFile sysFile = new SysFile();
            String fileSourceId = fileStorageService.uploadFile(multipartFile);
            sysFile.setFileName(multipartFile.getOriginalFilename());
            sysFile.setBindingId(ruleId);
            sysFile.setFileType(multipartFile.getOriginalFilename().split("\\.")[multipartFile.getOriginalFilename().split("\\.").length-1]);
            sysFile.setId(StringUtils.uuid());
            sysFile.setFileSize((int)multipartFile.getSize());
            sysFile.setFileStorageMode("2");
            sysFile.setFileSource(fileSourceId);
            list.add(sysFile);
        }
        if(list.size()>0){
            sysFileService.batchInsert(list);
        }
        return CommonResult.success("保存成功");
    }

    @Transactional
    @Override
    public CommonResult updateData(WfRule wfRule, MultipartFile[] files) {
        WfRule wfRule1 = this.getById(wfRule.getRuleId());
        wfRule1.setRuleName(wfRule.getRuleName());
        wfRule1.setRuleNumber(wfRule.getRuleNumber());
        wfRule1.setRuleRemark(wfRule.getRuleRemark());
        wfRule1.setRuleType(wfRule.getRuleType());
        wfRule1.setRuleStatus(wfRule.getRuleStatus());
        wfRule1.setUpdateTime(new Date());
        this.insertOrUpdate(wfRule1);
        if(files!=null&&files.length>0){
            FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
            List<SysFile> list = new ArrayList<>();
            List<String> typeList= new ArrayList<>();
            for(MultipartFile multipartFile:files){
                SysFile sysFile = new SysFile();
                String fileSourceId = fileStorageService.uploadFile(multipartFile);
                sysFile.setFileName(multipartFile.getOriginalFilename());
                sysFile.setBindingId(wfRule.getRuleId());
                String fileType=multipartFile.getOriginalFilename().split("\\.")[multipartFile.getOriginalFilename().split("\\.").length-1];
                typeList.add(fileType);
                sysFile.setFileType(fileType);
                sysFile.setId(StringUtils.uuid());
                sysFile.setFileSize((int)multipartFile.getSize());
                sysFile.setFileStorageMode("2");
                sysFile.setFileSource(fileSourceId);
                list.add(sysFile);
            }
            if(typeList.size()>0){
                LambdaQueryWrapper<SysFile> sysFileLam = new LambdaQueryWrapper<>();
                sysFileLam.eq(SysFile::getBindingId,wfRule.getRuleId());
                sysFileLam.in(SysFile::getFileType,typeList);
                List<SysFile> removeList = sysFileService.list(sysFileLam);
                if(removeList!=null&&removeList.size()>0){//删除电子文件
                    for(SysFile sysFile:removeList){
                        fileStorageService.deleteFile(sysFile.getFileSource());
                    }
                }
                sysFileService.remove(sysFileLam);
            }
            if(list.size()>0){
                sysFileService.batchInsert(list);
            }
        }
        return CommonResult.success("保存成功");
    }

    @Transactional
    @Override
    public CommonResult delWfRulesByIds(String ids) {
        String[] idL = ids.split(",");
        LambdaQueryWrapper<WfRule> delWfRuleLam = new LambdaQueryWrapper<>();
        delWfRuleLam.in(WfRule::getRuleId,idL);
        LambdaQueryWrapper<SysFile> sysFileLam = new LambdaQueryWrapper<>();
        sysFileLam.in(SysFile::getBindingId,idL);
        List<SysFile> sysFiles = sysFileService.list(sysFileLam);
        if(sysFiles!=null&&sysFiles.size()>0){
            FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
            for(SysFile sysFile:sysFiles){
                fileStorageService.deleteFile(sysFile.getFileSource());
            }
            sysFileService.remove(sysFileLam);
        }
        this.remove(delWfRuleLam);
        return CommonResult.success("删除成功");
    }

    @Override
    public CommonGridResult getWfRulesList(WfRule wfRule) {
        PageHelper.startPage(wfRule.getPageNum(),wfRule.getPageSize());
        LambdaQueryWrapper<WfRule> wfRuleLam = new LambdaQueryWrapper<>();
        if(wfRule.getRuleName()!=null&&!"".equals(wfRule.getRuleName().replaceAll(" ",""))){
            wfRuleLam.like(WfRule::getRuleName,wfRule.getRuleName());
        }
        if(wfRule.getRuleType()!=null&&!"".equals(wfRule.getRuleType().replaceAll(" ",""))){
            wfRuleLam.like(WfRule::getRuleType,wfRule.getRuleType());
        }
        if(wfRule.getRuleStatus()!=null&&!"".equals(wfRule.getRuleStatus())){
            wfRuleLam.eq(WfRule::getRuleStatus,wfRule.getRuleStatus());
        }
        wfRuleLam.orderByDesc(WfRule::getCreateTime);
        List<WfRule> returnList = this.list(wfRuleLam);
        Page page = (Page) returnList;
        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setRows(page.getResult());
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setPageSize(page.getPageSize());
        return commonGridResult;
    }

    @Override
    public CommonResult getPdfFileByRuleId(String ruleId) {
        LambdaQueryWrapper<SysFile> sysFileLam = new LambdaQueryWrapper<>();
        sysFileLam.eq(SysFile::getBindingId,ruleId).eq(SysFile::getFileType,"pdf");
        SysFile sysFile = sysFileService.getOne(sysFileLam);
        if(sysFile!=null) {
            return CommonResult.success("获取成功", sysFile.getFileSource());
        }else{
            return CommonResult.error(500,"获取失败");
        }
    }
}
