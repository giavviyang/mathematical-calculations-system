package com.fudian.project.system.service.impl;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.ams.service.E01z1Api;
import com.fudian.api.system.pojo.SysFile;
import com.fudian.api.system.service.SysFileApi;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.mapper.SysFileMapper;
import com.fudian.project.system.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService, SysFileApi {

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private E01z1Api e01z1Mapper;

    @Override
    public int updateBatch(List<SysFile> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<SysFile> list) {
        this.saveBatch(list);
        return list.size();
    }
    @Override
    public int insertOrUpdate(SysFile record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SysFile record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public SysFile queryBySysFile(SysFile sysFile) {
        QueryWrapper<SysFile> queryWrapper = new QueryWrapper<>();
        try {
            Class clazz = sysFile.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                TableField tableField = f.getDeclaredAnnotation(TableField.class);
                if(tableField==null){
                    continue;
                }
                f.setAccessible(true);
                Object b = f.get(sysFile);
                if (b != null && b != "") {
                    queryWrapper.eq(tableField.value(), b);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SysFile> queryBySysFileList(String binding_id) {
        LambdaQueryWrapper<SysFile> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SysFile::getBindingId,binding_id);
        List<SysFile> sysFileList=baseMapper.selectList(wrapper);
        return sysFileList;
    }

    @Override
    public int selectCount(Wrapper<SysFile> queryWrapper) {
        return this.getBaseMapper().selectCount(queryWrapper);
    }

    @Override
    public CommonResult queryByE01z1Ids(String ids) {

        if (ids == null || "".equals(ids)) {
            CommonResult result = new CommonResult();
            result.setCode(500);
            result.setMsg("传入材料为空");
        }

        String[] idArr = ids.split(",");
        //根据材料id查询所有的用户名拼接到电子文件名称之前
        List<A01> a01List = e01z1Mapper.selectUserByE01ziIds(idArr);
        QueryWrapper<E01z1> wrapper = new QueryWrapper();
        wrapper.in("id",idArr);
        Map<String,String> e01z1Map = new HashMap<>();
        List<E01z1> e01z1s = e01z1Mapper.list(wrapper);
        if (e01z1s != null && e01z1s.size() > 0) {
            for (E01z1 e01z1 : e01z1s) {
                e01z1Map.put(e01z1.getId(),e01z1.getA01Id());
            }
        }

        Map<String,String> a01Map = new HashMap<>();
        if (a01List != null && a01List.size() > 0) {
            for (A01 a01 : a01List) {
                a01Map.put(a01.getId(),a01.getA0101());
            }
        }

        //根据材料id查询文件
        QueryWrapper<SysFile> w = new QueryWrapper<>();
        w.in("binding_id",idArr);
        List<SysFile> list = sysFileService.list(w);

        for (SysFile sysFile : list) {
            String name = a01Map.get(e01z1Map.get(sysFile.getBindingId()));
            if (name != null && !"".equals(name)) {
                sysFile.setFileName( name + "/" + sysFile.getFileName());
            }
        }

        CommonResult result = new CommonResult();
        result.setCode(200);
        result.setData(list);
        return result;
    }


    @Override
    public boolean update(Wrapper<SysFile> updateWrapper) {
        return this.update((SysFile) null,updateWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<SysFile> entityList) {
        return this.updateBatchById(entityList,1000);
    }

    @Override
    public SysFile getOne(Wrapper<SysFile> queryWrapper) {
       return baseMapper.selectOne(queryWrapper);
    }
}
