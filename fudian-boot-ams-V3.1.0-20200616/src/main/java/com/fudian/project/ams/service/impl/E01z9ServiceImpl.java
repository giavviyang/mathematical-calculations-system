package com.fudian.project.ams.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.room.pojo.CustomPositionA01;
import com.fudian.api.room.service.CustomPositionA01Api;
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
import com.fudian.project.ams.mapper.E01z9Mapper;
import com.fudian.project.ams.mapper.E01z9RelaMapper;
import com.fudian.project.ams.pojo.E01z9;
import com.fudian.project.ams.pojo.E01z9Rela;
import com.fudian.project.ams.service.E01z9LedgerService;
import com.fudian.project.ams.service.E01z9RelaService;
import com.fudian.project.ams.service.E01z9Service;
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
public class E01z9ServiceImpl extends ServiceImpl<E01z9Mapper, E01z9> implements E01z9Service {
    @Autowired
    E01z9Mapper e01z9Mapper;
    @Autowired
    E01z9RelaMapper e01z9RelaMapper;

    @Autowired
    E01z9RelaService e01z9RelaService;
    @Autowired
    TokenApi tokenService;
    @Autowired
    SysDeptApi sysDeptService;
    @Autowired
    E01z9LedgerService e01z9LedgerService;

    @Value("${fileStorage.type}")
    private String fileStorageType;

    @Autowired
    private SysFileApi sysFileService;




    @Override
    public int updateBatch(List<E01z9> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<E01z9> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(E01z9 record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(E01z9 record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public List<E01z9> queryE01z9List(String id) {
        LambdaQueryWrapper<E01z9> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(E01z9::getUserId, id);
        wrapper.orderByDesc(E01z9::getApprovTime);
//        try {
//            Class clazz=a01.getClass();
//            Field[] fields = clazz.getDeclaredFields();
//            for (int i = 0; i < fields.length; i++) {
//                Field field = fields[i];
//                Object b=field.get(a01);
//                if(b!=null&&b!=""){
//                    wrapper.eq(fields[i].getName(),b);
//                }
//            }
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        PageHelper.startPage(a01.getPageNum(), a01.getPageSize());
        List<E01z9> list = e01z9Mapper.selectList(wrapper);
        return list;
    }

    @Override
    public String insertE01z9(String uuid, String userId) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        E01z9 e01z9 = new E01z9();
        e01z9.setId(uuid);
        e01z9.setUserId(userId);
        e01z9.setUserName(user.getUserName());
        SysDept sysDept = sysDeptService.getById(user.getDeptId());
        if (sysDept != null && !"".equals(sysDept)) {
            e01z9.setE01z824a(sysDept.getDeptName());
            e01z9.setE01z824a(sysDept.getId());
        }
        e01z9.setE01z824b(user.getDeptId());
        Date date = new Date();
        e01z9.setApprovTime(date);
        int res = e01z9Mapper.insert(e01z9);
        return uuid;
    }

    @Override
    public List<E01z9> queryE01z9List() {
        QueryWrapper<E01z9> wrapper = new QueryWrapper<>();
        List<E01z9> e01z9List = e01z9Mapper.selectList(wrapper);
        return e01z9List;
    }

    @Override
    public int deleteE01z9ById(String id) {
        int res = e01z9Mapper.deleteById(id);
        return res;
    }

    @Override
    public int updateE01z9(E01z9 e01z9) {
        int res = e01z9Mapper.updateById(e01z9);
        return res;
    }

    @Override
    public List<Map<String, Object>> queryE01z9AndE01z9Rela(String deptName, String a0101, Integer pageNum, Integer pageSize) {
        List<Map<String, Object>> list = new ArrayList<>();
        QueryWrapper<E01z9> e9wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<E01z9Rela> eawrapper = new LambdaQueryWrapper<>();
        if (!"".equals(a0101)) {
            eawrapper.like(E01z9Rela::getA0101, a0101);
        }
        if (!"".equals(deptName)) {
            eawrapper.like(E01z9Rela::getDeptName, deptName);
        }
        List<E01z9Rela> e01z9Relas = e01z9RelaMapper.selectList(eawrapper);
        List<String> ids = e01z9Relas.stream().map(e -> e.getE01z9Id()).distinct().collect(Collectors.toList());
        if (ids.size() == 0) {
            return null;
        }
        e9wrapper.in("id", ids);
        PageHelper.startPage(pageNum, pageSize);
        List<E01z9> e01z9List = e01z9Mapper.selectList(e9wrapper);
        for (int i = 0; i < e01z9List.size(); i++) {
            Map<String, Object> map = null;
            try {
                map = Map2Bean.bean2map(e01z9List.get(i));
                LambdaQueryWrapper<E01z9Rela> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(E01z9Rela::getE01z9Id, e01z9List.get(i).getId());
                if (!"".equals(a0101)) {
                    wrapper.like(E01z9Rela::getA0101, a0101);
                }
                if (!"".equals(deptName)) {
                    wrapper.like(E01z9Rela::getDeptName, deptName);
                }
                List<E01z9Rela> e01z9RelaList = e01z9RelaMapper.selectList(wrapper);
                if (e01z9RelaList.size() == 0) {
                    continue;
                }
                map.put("children", e01z9RelaList);
                list.add(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<E01z9> queryE01z9List(List<String> ids) {
        List<E01z9> e01z9s = baseMapper.selectBatchIds(ids);
        return e01z9s;
    }

    @Override
    public int updateBatchSelective(List<E01z9> list) {
        return baseMapper.updateBatchSelective(list);
    }



    @Override
    public CommonGridResult getE01z9sByStates(E01z9 e01z9, String isAll) {
        PageHelper.startPage(e01z9.getPageNum(), e01z9.getPageSize());
        LambdaQueryWrapper<E01z9> queryWrapper = new LambdaQueryWrapper<E01z9>();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        queryWrapper.eq(E01z9::getStatus, e01z9.getStatus());
        if(isAll == null){
            queryWrapper.eq(E01z9::getUserId, loginUser.getUser().getUserId());
        }


        if (e01z9.getSearchValue() != null && !"".equals(e01z9.getSearchValue())) {
            JSONObject jsonObject = JSONObject.parseObject(e01z9.getSearchValue());
            E01z9 e01z9c = JSONObject.toJavaObject(jsonObject, E01z9.class);
            if (e01z9c.getStartTime() != null) {
                queryWrapper.ge(E01z9::getCreatersTime, e01z9c.getStartTime());
            }
            if (e01z9c.getEndTime() != null) {
                queryWrapper.le(E01z9::getCreatersTime, e01z9c.getEndTime());
            }
            if (e01z9c.getUserName() != null && !"".equals(e01z9c.getUserName())) {
                queryWrapper.like(E01z9::getUserName, e01z9c.getUserName());
            }
        }
        List<E01z9> e01z9List = this.list(queryWrapper.orderByDesc(E01z9::getCreatersTime));
        Page<E01z9> page = (Page) e01z9List;
        CommonGridResult commonGridResult = new CommonGridResult();
        if (e01z9.getStatus() == 1) {
            List<Map<String, Object>> list = new ArrayList<>();
            for (E01z9 e01z9t : page.getResult()) {
                JSON jsons = (JSON) JSON.toJSON(e01z9t);
                Map paramMap = (Map) JSONObject.parseObject(jsons.toString());
                if (e01z9t.getEndTime() != null) {
                    paramMap.put("remark", e01z9t.getEndTime().before(new Date()) ? "已到期" : "未到期");
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
    public CommonResult archiveRenewalOfMaturity(String condition) {
        JSONObject jsonObject = JSONObject.parseObject(condition);
        E01z9 e01z9c = JSONObject.toJavaObject(jsonObject, E01z9.class);
        E01z9 e01z9 = this.getById(e01z9c.getId());
        e01z9.setStatus(0);
        e01z9.setApplyStatus("到期续查");
        e01z9.setEndTime(e01z9c.getEndTime());
        boolean b = this.updateById(e01z9);
        this.e01z9LedgerService.updateE01z9AndRela(e01z9);
        if (b) {
            return CommonResult.success("续查申请成功");
        } else {
            return CommonResult.error(500, "续查申请失败");
        }

    }


    @Override
    public List<E01z9> queryNoAppro() {
        LambdaQueryWrapper<E01z9> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(E01z9::getStatus,0);
        //wrapper.eq(E01z9::getTarget,1);
        List<E01z9> list=e01z9Mapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<E01z9> queryNoReturn() {
        LambdaQueryWrapper<E01z9> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(E01z9::getStatus,1);
        wrapper.eq(E01z9::getTarget,1);
        Date today=new Date();
        wrapper.lt(E01z9::getEndTime,today);
        List<E01z9> list=e01z9Mapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<Map<String, Object>> summaryE01z9() {
        return baseMapper.summaryE01z9();
    }
}












