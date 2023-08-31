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
import com.fudian.project.ams.mapper.WfTemplateMapper;
import com.fudian.project.ams.pojo.WfTemplate;
import com.fudian.project.ams.service.WfTemplateService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WfTemplateServiceImpl extends ServiceImpl<WfTemplateMapper, WfTemplate> implements WfTemplateService {


    @Value("${fileStorage.type}")
    private String fileStorageType;

    @Autowired
    private SysFileApi sysFileService;

    @Override
    public int updateBatch(List<WfTemplate> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<WfTemplate> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<WfTemplate> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(WfTemplate record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(WfTemplate record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonResult insertTemplate(MultipartFile[] files, WfTemplate wfTemplate) {
        String temId= StringUtils.uuid();
        wfTemplate.setTemplateId(temId);
        wfTemplate.setCreateTime(new Date());
        this.insertOrUpdate(wfTemplate);
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
        List<SysFile> list = new ArrayList<>();
        for(MultipartFile multipartFile :files){
            SysFile sysFile = new SysFile();
            String fileSourceId = fileStorageService.uploadFile(multipartFile);
            sysFile.setFileName(multipartFile.getOriginalFilename());
            sysFile.setBindingId(temId);
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

    @Override
    public CommonResult updateTemplate(MultipartFile[] files, WfTemplate wfTemplate) {
        WfTemplate wfTemplate1 = this.getById(wfTemplate.getTemplateId());
        wfTemplate1.setTemplateName(wfTemplate.getTemplateName());
        wfTemplate1.setTemplateStatus(wfTemplate.getTemplateStatus());
        wfTemplate1.setUpdateTime(new Date());
        this.insertOrUpdate(wfTemplate1);
        if(files!=null&&files.length>0){
            FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
            List<SysFile> list = new ArrayList<>();
            List<String> typeList= new ArrayList<>();
            for(MultipartFile multipartFile:files){
                SysFile sysFile = new SysFile();
                String fileSourceId = fileStorageService.uploadFile(multipartFile);
                sysFile.setFileName(multipartFile.getOriginalFilename());
                sysFile.setBindingId(wfTemplate.getTemplateId());
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
                sysFileLam.eq(SysFile::getBindingId,wfTemplate.getTemplateId());
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

    @Override
    public CommonResult delTemplatesByIds(String ids) {

        String[] idL = ids.split(",");
        LambdaQueryWrapper<WfTemplate> delWfTemLam = new LambdaQueryWrapper<>();
        delWfTemLam.in(WfTemplate::getTemplateId,idL);
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
        this.remove(delWfTemLam);
        return CommonResult.success("删除成功");
    }

    @Override
    public CommonGridResult getTemplateList(WfTemplate wfTemplate) {
        PageHelper.startPage(wfTemplate.getPageNum(),wfTemplate.getPageSize());
        LambdaQueryWrapper<WfTemplate> wfTemLam = new LambdaQueryWrapper<>();
        if(wfTemplate.getTemplateName()!=null&&!"".equals(wfTemplate.getTemplateName().replaceAll(" ",""))){
            wfTemLam.like(WfTemplate::getTemplateName,wfTemplate.getTemplateName());
        }
        if(wfTemplate.getTemplateStatus()!=null&&!"".equals(wfTemplate.getTemplateStatus().replaceAll(" ",""))){
            wfTemLam.eq(WfTemplate::getTemplateStatus,wfTemplate.getTemplateStatus());
        }
        wfTemLam.orderByDesc(WfTemplate::getCreateTime);
        List<WfTemplate> returnList = this.list(wfTemLam);
        Page page = (Page) returnList;
        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setRows(page.getResult());
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setPageSize(page.getPageSize());
        return commonGridResult;
    }

    @Override
    public CommonResult getPdfFileByTemplateId(String templateId) {
        LambdaQueryWrapper<SysFile> sysFileLam = new LambdaQueryWrapper<>();
        sysFileLam.eq(SysFile::getBindingId,templateId).eq(SysFile::getFileType,"pdf");
        SysFile sysFile = sysFileService.getOne(sysFileLam);
        if(sysFile!=null) {
            return CommonResult.success("获取成功", sysFile.getFileSource());
        }else{
            return CommonResult.error(500,"获取失败");
        }
    }

    @Override
    public CommonResult getWordFileByTemplateId(String templateIds) {
        String[] temIds = templateIds.split(",");
        LambdaQueryWrapper<SysFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<String> list = new ArrayList<>();
        list.add("doc");
        list.add("docx");
        lambdaQueryWrapper.in(SysFile::getBindingId,temIds).in(SysFile::getFileType,list);
        List<SysFile> sysFiles = sysFileService.list(lambdaQueryWrapper);
        if(sysFiles!=null&&sysFiles.size()>0){
            List<String> returnList = sysFiles.stream().map(e->e.getFileSource()).collect(Collectors.toList());
            return CommonResult.success("查询成功",returnList);
        }else{
            return CommonResult.error(500,"没有文档");
        }


    }


}
