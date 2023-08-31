package com.fudian.project.system.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.TokenApi;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.project.system.mapper.SysDictDataMapper;
import com.fudian.project.system.mapper.SysDictTypeMapper;
import com.fudian.project.system.pojo.SysDictData;
import com.fudian.project.system.pojo.SysDictType;
import com.fudian.project.system.service.SysDictDataService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {
    @Autowired
    private SysDictDataMapper sysDictDataMapper;
    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;
    @Autowired
    private TokenApi tokenService;

    @Override
    public int updateBatch(List<SysDictData> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SysDictData> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SysDictData record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SysDictData record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonResult addOrupdateDataNode(SysDictData sysDictData) {
        Boolean aBoolean = Boolean.valueOf(sysDictData.getJsonStr());
        // 如果是修改
//        if(aBoolean){
//            LambdaQueryWrapper<SysDictData> sysDictTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            sysDictTypeLambdaQueryWrapper.eq(SysDictData::getDictLabel, sysDictData.getDictLabel());
//            sysDictTypeLambdaQueryWrapper.eq(SysDictData::getDictType, sysDictData.getDictType());
//            sysDictTypeLambdaQueryWrapper.ne(SysDictData::getDictLabel, sysDictData.getDictLabel());
//            SysDictData sysDictData1 = baseMapper.selectOne(sysDictTypeLambdaQueryWrapper);
//            if (null != sysDictData1) {
//                return CommonResult.error(500, "字段名称已存在");
//            }
//            sysDictTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            sysDictTypeLambdaQueryWrapper.eq(SysDictData::getDictType, sysDictData.getDictType());
//            sysDictTypeLambdaQueryWrapper.eq(SysDictData::getDictValue, sysDictData.getDictValue());
//            sysDictTypeLambdaQueryWrapper.ne(SysDictData::getDictValue, sysDictData.getDictValue());
//            SysDictData sysDictData2 = baseMapper.selectOne(sysDictTypeLambdaQueryWrapper);
//            if (null != sysDictData2) {
//                return CommonResult.error(500, "字段值已存在");
//            }
//        }else{
        // 新增
//        QueryWrapper<SysDictData> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(SysDictData::getDictLabel, sysDictData.getDictLabel());
//        queryWrapper.lambda().eq(SysDictData::getDictType, sysDictData.getDictType());
//        SysDictData sysDictData1 = baseMapper.selectOne(queryWrapper);
//        if (null != sysDictData1) {
//            return CommonResult.error(500, "字段名称已存在");
//        }
//        queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(SysDictData::getDictType, sysDictData.getDictType());
//        queryWrapper.lambda().eq(SysDictData::getDictValue, sysDictData.getDictValue());
//        SysDictData sysDictData2 = baseMapper.selectOne(queryWrapper);
//        if (null != sysDictData2) {
//            return CommonResult.error(500, "字段值已存在");
//        }
//        }

        LambdaQueryWrapper<SysDictData> sysDictDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDictDataLambdaQueryWrapper.eq(SysDictData::getDictType,sysDictData.getDictType() );
        sysDictDataLambdaQueryWrapper.eq(SysDictData::getDictLabel,sysDictData.getDictLabel());
        SysDictData sysDictData1 = sysDictDataMapper.selectOne(sysDictDataLambdaQueryWrapper);
        if(sysDictData1!=null){
            return CommonResult.error(500, "字段名称已存在");
        }
        LambdaQueryWrapper<SysDictData> sysDictDataLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        sysDictDataLambdaQueryWrapper1.eq(SysDictData::getDictType,sysDictData.getDictType() );
        sysDictDataLambdaQueryWrapper1.eq(SysDictData::getDictValue,sysDictData.getDictValue());
        SysDictData sysDictData2 = sysDictDataMapper.selectOne(sysDictDataLambdaQueryWrapper1);
        if(sysDictData2!=null){
            return CommonResult.error(500, "字段值已存在");
        }

        CommonResult result = null;
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        Date date = new Date();
        int i = 0;
        if (sysDictData.getDictValueid() == null || "".equals(sysDictData.getDictValueid())) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            sysDictData.setDictValueid(uuid);
            sysDictData.setCreateBy(user.getUserName());
            sysDictData.setCreateTime(date);
            sysDictData.setUpdateTime(date);
            sysDictData.setUpdateBy(user.getUserName());

            i = sysDictDataMapper.insert(sysDictData);
            if (i > 0) {
                result = new CommonResult(200, "新增成功", i);
            } else {
                result = new CommonResult(500, "新增失败", i);
            }
        } else {
            sysDictData.setUpdateBy(user.getUserName());
            sysDictData.setUpdateTime(date);
            i = sysDictDataMapper.updateById(sysDictData);
            if (i > 0) {
                result = new CommonResult(200, "修改成功", i);
            } else {
                result = new CommonResult(500, "修改失败", i);
            }
        }
        return result;
    }

    @Override
    public List<SysDictData> queryListByIds(List<String> ids) {

        List<SysDictData> list = sysDictDataMapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public CommonGridResult queryListByName(String dictLabel, Integer pageSize, Integer pageNum, String dictValueid, String dictTypeid) {
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        if (!"".equals(dictValueid) && dictValueid != null) {
            wrapper.eq(SysDictData::getParentId, dictValueid);
        } else {
            wrapper.eq(SysDictData::getParentId, dictTypeid);
        }
        if (dictLabel != null && !"".equals(dictLabel)) {
            wrapper.like(SysDictData::getDictLabel, dictLabel);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<SysDictData> list = sysDictDataMapper.selectList(wrapper);
        Page page = (Page) list;
        return new CommonGridResult(pageNum, pageSize, page.getTotal(), list);
    }

    @Override
    public CommonResult queryByDictType(String dictType) {
        CommonResult result = null;
        try {
            if (dictType == null || "".equals(dictType)) {
                return result = new CommonResult(200, "没有符合当前类型的字典数据", 1);
            }
            LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysDictType::getDictType, dictType);
            SysDictType sysDictType = sysDictTypeMapper.selectOne(wrapper);
            if (sysDictType != null) {
                JSONObject object = (JSONObject) JSONObject.toJSON(sysDictType);
                LambdaQueryWrapper<SysDictData> dataWrapper = new LambdaQueryWrapper<>();
                dataWrapper.eq(SysDictData::getParentId, sysDictType.getDictTypeid());
                List<SysDictData> list = sysDictDataMapper.selectList(dataWrapper);

                List<JSONObject> objectList = findSonNode(list, sysDictType.getDictTypeid());
//                object.put("dictLabel",object.get("dictName"));
//                object.put("children",objectList);
                result = new CommonResult(200, "查询成功", objectList);
            } else {
                result = new CommonResult(200, "没有符合当前类型的字典数据", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result = new CommonResult(500, "系统异常，请联系管理员", 1);
        }
        return result;
    }

    /**
     * 递归生成字典树
     *
     * @param sysDictData
     * @param parentId
     * @return
     */
    public List<JSONObject> findSonNode(List<SysDictData> sysDictData, String parentId) {
        //找下级节点
        List<JSONObject> returnList = new ArrayList();
        for (SysDictData sd : sysDictData) {
            String pid = sd.getDictValueid();
            LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysDictData::getParentId, pid);
            wrapper.orderByDesc(SysDictData::getUpdateTime);
            List<SysDictData> list = sysDictDataMapper.selectList(wrapper);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(sd);
            if (list != null && list.size() > 0) {
                jsonObject.put("children", list);
                returnList.add(jsonObject);
                this.findSonNode(list, pid);
            } else {
                returnList.add(jsonObject);
            }
        }
        return returnList;
    }
}
