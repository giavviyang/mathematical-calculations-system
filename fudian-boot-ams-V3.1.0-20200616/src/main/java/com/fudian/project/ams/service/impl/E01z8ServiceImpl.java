package com.fudian.project.ams.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.SysFile;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.SysDeptApi;
import com.fudian.api.system.service.SysFileApi;
import com.fudian.api.system.service.TokenApi;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.factory.FileStorageFactory;
import com.fudian.filestorage.service.FileStorageService;
import com.fudian.project.ams.mapper.E01z8Mapper;
import com.fudian.project.ams.mapper.E01z8RelaMapper;
import com.fudian.project.ams.pojo.E01z8;
import com.fudian.project.ams.pojo.E01z8Rela;
import com.fudian.project.ams.service.E01z8LedgerService;
import com.fudian.project.ams.service.E01z8RelaService;
import com.fudian.project.ams.service.E01z8Service;
import com.fudian.project.ams.utils.Map2Bean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class E01z8ServiceImpl extends ServiceImpl<E01z8Mapper, E01z8> implements E01z8Service {
    @Autowired
    E01z8Mapper e01z8Mapper;

    @Value("${fileStorage.type}")
    private String fileStorageType;

    @Autowired
    E01z8RelaMapper e01z8RelaMapper;

    @Autowired
    E01z8RelaService e01z8RelaService;


    @Autowired
    TokenApi tokenService;
    @Autowired
    SysDeptApi sysDeptMapper;
    @Autowired
    E01z8LedgerService e01z8LedgerService;

    @Autowired
    private SysFileApi sysFileService;

    @Override
    public int updateBatch(List<E01z8> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<E01z8> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(E01z8 record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(E01z8 record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public List<E01z8> queryE01z8List(String id) {
        LambdaQueryWrapper<E01z8> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(E01z8::getUserId, id);
        wrapper.orderByDesc(E01z8::getApprovTime);
        List<E01z8> e01z8s = e01z8Mapper.selectList(wrapper);
        return e01z8s;
    }

    @Override
    public String insertE01z8(String uuid, String userId) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        E01z8 e01z8 = new E01z8();
        e01z8.setUserId(userId);
        e01z8.setId(uuid);
        e01z8.setUserName(user.getUserName());
        SysDept sysDept = sysDeptMapper.getById(user.getDeptId());
        if (sysDept != null && !"".equals(sysDept)) {
            e01z8.setE01z824a(sysDept.getDeptName());
            e01z8.setE01z824b(sysDept.getId());
        }
        Date date = new Date();
        e01z8.setApprovTime(date);
        int a = e01z8Mapper.insert(e01z8);
        return uuid;
    }

    @Override
    public List<E01z8> queryE01z8List() {
        QueryWrapper wrapper = new QueryWrapper();
        List<E01z8> e01z8s = e01z8Mapper.selectList(wrapper);
        return e01z8s;
    }

    @Override
    public int deleteE01z8(String id) {
        int res = e01z8Mapper.deleteById(id);
        return res;
    }

    @Override
    public int updateE01z8(E01z8 e01z8) {
        int res = e01z8Mapper.updateById(e01z8);
        return res;
    }

    @Override
    public List<Map<String, Object>> queryE01z8AndE01z8Rela(String deptName, String a0101, Integer pageNum, Integer pageSize) {
        List<Map<String, Object>> list = new ArrayList<>();
        QueryWrapper<E01z8> e8wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<E01z8Rela> eawrapper = new LambdaQueryWrapper<>();
        eawrapper.like(E01z8Rela::getA0101, a0101);
        eawrapper.like(E01z8Rela::getDeptName, deptName);
        List<E01z8Rela> e01z8Relas = e01z8RelaMapper.selectList(eawrapper);
        List<String> ids = e01z8Relas.stream().map(e -> e.getE01z8Id()).distinct().collect(Collectors.toList());
        if (ids.size() == 0) {
            return null;
        }
        e8wrapper.in("id", ids);
        PageHelper.startPage(pageNum, pageSize);
        List<E01z8> e01z8s = e01z8Mapper.selectList(e8wrapper);
        for (int i = 0; i < e01z8s.size(); i++) {
            Map<String, Object> map = null;
            try {
                map = Map2Bean.bean2map(e01z8s.get(i));
                LambdaQueryWrapper<E01z8Rela> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(E01z8Rela::getE01z8Id, e01z8s.get(i).getId());
                List<E01z8Rela> e01z8RelaList = e01z8RelaMapper.selectList(wrapper);
                map.put("children", e01z8RelaList);
                list.add(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<E01z8> queryE01z8List(List<String> ids) {
        List<E01z8> e01z8List = baseMapper.selectBatchIds(ids);
        return e01z8List;
    }

    @Override
    public int updateBatchSelective(List<E01z8> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insertByEntityAndList(Map<String, Object> data, MultipartFile files) {
        JSONObject e01z8Str = JSON.parseObject(data.get("formValues").toString());
        E01z8 e01z8 = JSONObject.toJavaObject(e01z8Str, E01z8.class);
        String e01z8Id = UUID.randomUUID().toString().replace("-", "");
        if(e01z8.getStatus()==null) {
            e01z8.setStatus(0);//待审批
        }else{
            e01z8.setApprovTime(new Date());
        }
        e01z8.setApplyStatus("实体档案查阅");
        e01z8.setCreatersTime(new Date());
        e01z8.setId(e01z8Id);
        List<Map<String, Object>> e01z8Relas = (List<Map<String, Object>>) data.get("gridValues");
        e01z8.setApplyNumber(e01z8Relas.size());
        List<E01z8Rela> list = new ArrayList<>();
        for (Map<String, Object> map : e01z8Relas) {
            E01z8Rela e01z8Rela = new E01z8Rela();
            e01z8Rela.setId(UUID.randomUUID().toString().replace("-", ""));
            e01z8Rela.setE01z8Id(e01z8Id);
            e01z8Rela.setA01Id(map.get("a01Id").toString());
            list.add(e01z8Rela);
        }
        if(files!=null){
            FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
            SysFile sysFile = new SysFile();
            String fileSourceId = fileStorageService.uploadFile(files);
            sysFile.setFileName(files.getOriginalFilename());
            sysFile.setBindingId(e01z8Id);
            sysFile.setFileType(files.getOriginalFilename().split("\\.")[files.getOriginalFilename().split("\\.").length-1]);
            sysFile.setId(StringUtils.uuid());
            sysFile.setFileSize((int)files.getSize());
            sysFile.setFileStorageMode("2");
            sysFile.setFileSource(fileSourceId);
            sysFileService.save(sysFile);
        }
        baseMapper.insert(e01z8);
//        e01z8RelaMapper.batchInsert(list);
        e01z8RelaService.saveBatch(list);
        e01z8LedgerService.InsertE01z8AndRela(e01z8);
        return CommonResult.success("申请成功");
    }

    @Override
    public CommonGridResult getE01z8sByStates(E01z8 e01z8, String isAll) {
        PageHelper.startPage(e01z8.getPageNum(), e01z8.getPageSize());
        LambdaQueryWrapper<E01z8> queryWrapper = new LambdaQueryWrapper<E01z8>();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        queryWrapper.eq(E01z8::getStatus, e01z8.getStatus());
        if(isAll == null){
            queryWrapper.eq(E01z8::getUserId, loginUser.getUser().getUserId());
        }


        if (e01z8.getSearchValue() != null && !"".equals(e01z8.getSearchValue())) {
            JSONObject jsonObject = JSONObject.parseObject(e01z8.getSearchValue());
            E01z8 e01z8c = JSONObject.toJavaObject(jsonObject, E01z8.class);
            if (e01z8c.getStartTime() != null) {
                queryWrapper.ge(E01z8::getCreatersTime, e01z8c.getStartTime());
            }
            if (e01z8c.getEndTime() != null) {
                queryWrapper.le(E01z8::getCreatersTime, e01z8c.getEndTime());
            }
            if (e01z8c.getUserName() != null && !"".equals(e01z8c.getUserName())) {
                queryWrapper.like(E01z8::getUserName, e01z8c.getUserName());
            }
        }

        List<E01z8> e01z8List = this.list(queryWrapper.orderByDesc(E01z8::getCreatersTime));
        Page<E01z8> page = (Page) e01z8List;
        CommonGridResult commonGridResult = new CommonGridResult();
        if (e01z8.getStatus() == 1) {
            List<Map<String, Object>> list = new ArrayList<>();
            for (E01z8 e01z8t : page.getResult()) {
                JSON jsons = (JSON) JSON.toJSON(e01z8t);
                Map paramMap = (Map) JSONObject.parseObject(jsons.toString());
                if (e01z8t.getEndTime() != null) {
                    paramMap.put("remark", e01z8t.getEndTime().before(new Date()) ? "已到期" : "未到期");
                } else {
                    paramMap.put("remark", "未到期");
                }
                list.add(paramMap);
            }
            commonGridResult.setRows(list);
        } else {
            commonGridResult.setRows(page.getResult());
        }
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setPageSize(page.getPageSize());
        return commonGridResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult archiveRenewalOfMaturity(String condition) {
        JSONObject jsonObject = JSONObject.parseObject(condition);
        E01z8 e01z8c = JSONObject.toJavaObject(jsonObject, E01z8.class);
        E01z8 e01z8 = this.getById(e01z8c.getId());
        e01z8.setStatus(0);
        e01z8.setApplyStatus("到期续查");
        e01z8.setEndTime(e01z8c.getEndTime());
        boolean b = this.updateById(e01z8);
        this.e01z8LedgerService.updateE01z8AndRela(e01z8);
        if (b) {
            return CommonResult.success("续查申请成功");
        }else{
            return CommonResult.error(500,"续查申请失败");
        }

    }

    @Override
    public CommonResult archiveApply(String ids, String isAgree, String e01z817, String flag) {
        String[] idstrs = ids.split(",");
        LambdaQueryWrapper<E01z8> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        lambdaQueryWrapper.in(E01z8::getId,idstrs);
        List<E01z8> list = this.list(lambdaQueryWrapper);
        for(E01z8 e01z8:list){
            if("1".equals(isAgree)) {
                e01z8.setStatus(1);
            }else{
                e01z8.setStatus(-1);
            }
            e01z8.setApprovTime(new Date());
            e01z8.setE01z817(e01z817);
            e01z8.setFlag(flag);
            e01z8.setE01z801(loginUser.getUser().getUserName());
            e01z8LedgerService.updateE01z8AndRela(e01z8);
        }
        int i =this.updateBatch(list);
        if(i==list.size()){
            return CommonResult.success("审批成功");
        }else if(i>0&&i<list.size()){
            return CommonResult.success("部分审批成功");
        }else{
            return CommonResult.error(500,"审批失败");
        }
    }

    @Override
    public List<E01z8> queryNoAppov() {
        LambdaQueryWrapper<E01z8> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(E01z8::getStatus,0);
        //wrapper.eq(E01z8::getTarget,1);
        List<E01z8> list=e01z8Mapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<Map<String, Object>> summaryE01z8() {
        return baseMapper.summaryE01z8();
    }
}









