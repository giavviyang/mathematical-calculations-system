package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.GlobalOrg;
import com.fudian.api.system.service.GlobalOrgApi;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.mapper.GlobalOrgMapper;
import com.fudian.project.system.mapper.GlobalUserMapper;
import com.fudian.project.system.pojo.GlobalUser;
import com.fudian.project.system.service.GlobalOrgService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GlobalOrgServiceImpl extends ServiceImpl<GlobalOrgMapper, GlobalOrg> implements GlobalOrgService, GlobalOrgApi {
    @Autowired
    private GlobalOrgMapper globalOrgMapper;
    @Autowired
    private GlobalUserMapper globalUserMapper;

    @Override
    public int updateBatch(List<GlobalOrg> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<GlobalOrg> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(GlobalOrg record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(GlobalOrg record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonGridResult queryGlobalOrgList(GlobalOrg globalOrg) {
        QueryWrapper<GlobalOrg> queryWrapper = new QueryWrapper<>();
        try {
            Class clazz = globalOrg.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                Object b = f.get(globalOrg);
                if (b != null && b != "") {
                    queryWrapper.eq(fields[i].getName(), b);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        PageHelper.startPage(globalOrg.getPageNum(), globalOrg.getPageSize());
        List<GlobalOrg> list = globalOrgMapper.selectList(queryWrapper);
        Page page = (Page) list;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(globalOrg.getPageNum(), globalOrg.getPageSize(), total, list);
        return commonGridResult;
    }

    @Override
    public boolean deleteGlobalOrgById(String globalOrgId) {
        GlobalOrg globalOrg=globalOrgMapper.selectById(globalOrgId);
        String code=globalOrg.getGlobalOrgAuthorizationcode();
        //第一步判断当前授权码是否在有效期
        Map<String, Object> map=analysisCode(code);
        //获取授权码中的时间
        Long expirationTime= (Long) map.get("Expiration");
        //当前时区毫秒值
        Long currentTime= LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        //第二步判断当前用户表中是否存在用户
        GlobalUser globalUser=globalUserMapper.selectById(globalOrg.getGlobalOrgCode());
        //当前有一个存在则不会进行删除。更改状态为假删除 否则直接删除
        int i;
        boolean b=false;
        if(globalUser!=null&&expirationTime>currentTime){
            //当前为有效全宗无法删除 直接返回false
            b=false;
        }else{
            if(globalUser!=null){
                //当前条件为有用户，判断当前的
                if(expirationTime>currentTime){
                    return  false;
                }else{
                    //当前为假删除更改状态为不可用
//                    globalOrg.setStatus(1);
                    i=globalOrgMapper.updateById(globalOrg);
                }
            }else{
                //当前无用户意味着当前未进行用户绑定，直接删除即可
                i =globalOrgMapper.deleteById(globalOrgId);
                if(i>0){
                    b=true;
                }
            }
        }
        return b;
    }

    @Override
    public Map<String, Object> analysisCode(String value) {
        Map<String, Object>  map=new HashMap<>(4);
        map.put("Expiration",System.currentTimeMillis());
        return map;
    }

    @Override
    public CommonResult<GlobalOrg> confirmPermissionsCode(GlobalOrg globalOrg) {
        CommonResult<GlobalOrg> commonResult=new CommonResult();
        GlobalUser globalUser=globalUserMapper.selectById(globalOrg);
        Map<String,Object>  map= analysisCode(globalOrg.getGlobalOrgAuthorizationcode());
        commonResult.put("UserName",globalUser.getUserName());
        return commonResult;
    }

    @Override
    public CommonResult<GlobalOrg> updateGlobalOrgById(GlobalOrg globalOrg) {
        CommonResult<GlobalOrg> commonResult=new CommonResult();
        int  i= globalOrgMapper.updateById(globalOrg);
        if (i>0) {
            commonResult.setMsg("更新全宗信息成功");
            commonResult.setCode(200);
        }else{
            commonResult.setMsg("更新全宗信息失败，请联系管理员");
            commonResult.setCode(500);
        }
        return commonResult;
    }

    @Override
    public boolean dataBaseConfirm(GlobalOrg globalOrg) {

        return false;
    }

    @Override
    public boolean fileBaseConfirm(GlobalOrg globalOrg) {


        return false;
    }

    @Override
    public CommonResult queryAllGlobalOrgList() {
        List<GlobalOrg> list = this.list();
        CommonResult<Object> commonResult = new CommonResult<>();
        commonResult.setCode(200);
        commonResult.setData(list);
        return commonResult;
    }

    @Override
    public GlobalOrg getOne(Wrapper<GlobalOrg> queryWrapper) {
        return this.getBaseMapper().selectOne(queryWrapper);
    }
}




