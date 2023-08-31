package com.fudian.project.system.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.common.constant.HttpStatus;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.system.mapper.SysDictDataMapper;
import com.fudian.project.system.mapper.SysDictTypeMapper;
import com.fudian.project.system.pojo.SysDictData;
import com.fudian.project.system.pojo.SysDictType;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.SysDictDataService;
import com.fudian.project.system.service.SysDictTypeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public int updateBatch(List<SysDictType> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SysDictType> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SysDictType record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SysDictType record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonGridResult selectDictTypeList(SysDictType dictType) {

        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        //字典类型名称
        if (StringUtils.isNotEmpty(dictType.getDictName())) {
            wrapper.like(SysDictType::getDictName, dictType.getDictName());
        }
        //字典状态
        if (StringUtils.isNotEmpty(dictType.getStatus())) {
            wrapper.eq(SysDictType::getStatus, dictType.getStatus());
        }
        //字典类型
        if (StringUtils.isNotEmpty(dictType.getDictType())) {
            wrapper.eq(SysDictType::getDictType, dictType.getDictType());
        }
        //大于开始时间
        if (dictType.getBeginTime() != null) {
            wrapper.ge(SysDictType::getCreateTime, dictType.getBeginTime());
        }
        //小于结束时间
        if (dictType.getEndTime() != null) {
            wrapper.le(SysDictType::getCreateTime, dictType.getEndTime());
        }
        wrapper.orderByAsc(SysDictType::getDictTypeid);
        PageHelper.startPage(dictType.getPageNum(), dictType.getPageSize(), dictType.getOrderCondition());
        List<SysDictType> list = baseMapper.selectList(wrapper);

        return new CommonGridResult(((Page) list).getTotal(), list);


    }

    @Override
    public CommonResult selectDictType(String dictType) {
        QueryWrapper<SysDictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_typeid", dictType);
        SysDictType sysDictType = baseMapper.selectOne(queryWrapper);
        CommonResult commonResult = new CommonResult();
        commonResult.setMsg("查询成功");
        commonResult.setCode(HttpStatus.SUCCESS);
        commonResult.setData(sysDictType);
        return commonResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult updateType(SysDictType sysDictType) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        sysDictType.setUpdateBy(user.getUserName());
        sysDictType.setUpdateTime(new Date());
        QueryWrapper<SysDictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_typeid", sysDictType.getDictTypeid());
        SysDictType sysDictType1 = baseMapper.selectOne(queryWrapper);
        if (!sysDictType.getDictType().equals(sysDictType1.getDictType())) {
            LambdaQueryWrapper<SysDictData> sysDictDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sysDictDataLambdaQueryWrapper.eq(SysDictData::getDictType, sysDictType1.getDictType());
            List<SysDictData> sysDictData = sysDictDataService.getBaseMapper().selectList(sysDictDataLambdaQueryWrapper);
            if (null != sysDictData && 0 != sysDictData.size()) {
                // 禁止修改子节点 个人认为不太合理 改为修改父节点之后 同时修改子节点的DictTyoe
                /**
                 * CommonResult commonResult = new CommonResult();
                 *                 commonResult.setCode(HttpStatus.NOT_MODIFIED);
                 *                 commonResult.setMsg("该节点存在子节点,禁止修改字典类型!");
                 *                 return commonResult;
                 */
                for (SysDictData sysDictDatum : sysDictData) {
                    sysDictDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    sysDictDataLambdaQueryWrapper.eq(SysDictData::getDictValueid, sysDictDatum.getDictValueid());
                    sysDictDatum.setDictType(sysDictType.getDictType());
                    int update = sysDictDataService.getBaseMapper().update(sysDictDatum, sysDictDataLambdaQueryWrapper);
                    if (update != 1) {
                        throw new RuntimeException("子节点修改失败!");
                    }
                }
            }
        }
        int update = baseMapper.update(sysDictType, queryWrapper);
        CommonResult commonResult = new CommonResult();
        if (update != 1) {
            throw new RuntimeException("父节点修改失败!");
        }
        commonResult.setMsg("修改成功");
        commonResult.setCode(HttpStatus.SUCCESS);
        return commonResult;
    }

    @Override
    public CommonResult addType(SysDictType sysDictType, SysDictData sysDictData) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        Date date = new Date();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //在sys_dict_type中添加数据
        if (sysDictData.getDictValueid() != null && !"".equals(sysDictData.getDictValueid())) {
            sysDictType.setCreateBy(user.getUserName());
            sysDictType.setCreateTime(date);
            sysDictType.setUpdateBy(user.getUserName());
            sysDictType.setUpdateTime(date);
            sysDictType.setIsSystem("N");
            sysDictType.setDictTypeid(uuid);
            int i = sysDictTypeMapper.insert(sysDictType);
        } else if ("".equals(sysDictData.getDictValueid())) {
            sysDictData.setDictValueid(uuid);
            sysDictData.setParentId(sysDictType.getDictTypeid());
            sysDictData.setCreateBy(user.getUserName());
            sysDictData.setCreateTime(date);
            sysDictData.setUpdateBy(user.getUserName());
            sysDictData.setUpdateTime(date);
            sysDictData.setIsSystem("N");
            sysDictData.setParentId(sysDictType.getDictTypeid());
            int i = sysDictDataMapper.insert(sysDictData);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult delType(String dictTypes) {
        StringBuilder msg = new StringBuilder();
        String[] ids = dictTypes.split(",");

        int i = -1;
        if (ids.length == 1) {
            LambdaQueryWrapper<SysDictType> sysDictTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sysDictTypeLambdaQueryWrapper.eq(SysDictType::getDictTypeid, ids[0]);
            SysDictType sysDictType = baseMapper.selectOne(sysDictTypeLambdaQueryWrapper);
            LambdaQueryWrapper<SysDictData> sysDictDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sysDictDataLambdaQueryWrapper.eq(SysDictData::getDictType, sysDictType.getDictType());
            List<SysDictData> sysDictData = sysDictDataService.getBaseMapper().selectList(sysDictDataLambdaQueryWrapper);
            if (null == sysDictData || 0 == sysDictData.size()) {
                i = baseMapper.delete(sysDictTypeLambdaQueryWrapper);
            } else {
                CommonResult commonResult = new CommonResult();
                commonResult.setCode(HttpStatus.NOT_MODIFIED);
                commonResult.setMsg(sysDictType.getDictName() + "节点存在子节点,删除失败!");
                return commonResult;
            }
        } else {
            for (int i1 = 0; i1 < ids.length; i1++) {
                LambdaQueryWrapper<SysDictType> sysDictTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
                sysDictTypeLambdaQueryWrapper.eq(SysDictType::getDictTypeid, ids[i1]);
                SysDictType sysDictType = baseMapper.selectOne(sysDictTypeLambdaQueryWrapper);
                LambdaQueryWrapper<SysDictData> sysDictDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
                sysDictDataLambdaQueryWrapper.eq(SysDictData::getDictType, sysDictType.getDictType());
                List<SysDictData> sysDictData = sysDictDataService.getBaseMapper().selectList(sysDictDataLambdaQueryWrapper);
                if (null != sysDictData && 0 != sysDictData.size()) {
                    msg.append(sysDictType.getDictName() + "节点存在子节点,删除失败!\n\r");
                }
            }
            if (msg.toString().trim().length() != 0) {
                CommonResult commonResult = new CommonResult();
                commonResult.setMsg(msg.toString());
                commonResult.setCode(HttpStatus.NOT_MODIFIED);
                return commonResult;
            }
            for (int i1 = 0; i1 < ids.length; i1++) {
                LambdaQueryWrapper<SysDictType> sysDictTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
                sysDictTypeLambdaQueryWrapper.eq(SysDictType::getDictTypeid, ids[i1]);
                i = baseMapper.delete(sysDictTypeLambdaQueryWrapper);
                if (i != 1) {
                    throw new RuntimeException("ID为" + ids[i1] + "删除失败");
                }
            }
        }
        CommonResult commonResult = new CommonResult();
        if (i == 1) {
            commonResult.setMsg("删除成功");
            commonResult.setCode(HttpStatus.SUCCESS);
        } else {
            commonResult.setMsg("删除失败");
            commonResult.setCode(HttpStatus.ERROR);
        }
        return commonResult;
    }

    @Autowired
    private SysDictDataService sysDictDataService;

    @Override
    public CommonResult getLeftTreeNode() {
        CommonResult commonResult = new CommonResult();
        JSONArray jsonArray = new JSONArray();
        List<SysDictType> sysDictTypes = baseMapper.selectList(new QueryWrapper<>());
        LambdaQueryWrapper<SysDictData> sysDictDataLambdaQueryWrapper;
        for (SysDictType sysDictType : sysDictTypes) {
            sysDictDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sysDictDataLambdaQueryWrapper.eq(SysDictData::getDictType, sysDictType.getDictType());
            List<SysDictData> sysDictData = sysDictDataService.getBaseMapper().selectList(sysDictDataLambdaQueryWrapper);
            List<JSONObject> list = new ArrayList<>();
            for (SysDictData sysDictDatum : sysDictData) {
                String string = JSONObject.toJSONString(sysDictDatum);
                JSONObject jsonObject = JSONObject.parseObject(string);
                jsonObject.put("dictName", sysDictDatum.getDictLabel());
                jsonObject.put("dictTypeid", sysDictDatum.getDictValueid());
                list.add(jsonObject);
            }
            String string = JSONObject.toJSONString(sysDictType);
            JSONObject jsonObject = JSONObject.parseObject(string);
            jsonObject.put("dictLabel", jsonObject.get("dictName"));
            jsonObject.put("dictValueid", jsonObject.get("dictTypeid"));
            jsonObject.put("children", list);
            jsonArray.add(jsonObject);
        }
        commonResult.setMsg("查询树节点成功");
        commonResult.setCode(HttpStatus.SUCCESS);
        commonResult.setData(jsonArray);
        return commonResult;
    }


    @Override
    public CommonResult addData(SysDictData sysDictData) {

        if (sysDictData.getIsDefault().equalsIgnoreCase("Y")) {
            //判断是否存在默认
            LambdaQueryWrapper<SysDictData> isDefaultQuery = new LambdaQueryWrapper<>();
            isDefaultQuery.eq(SysDictData::getDictType, sysDictData.getDictType());
            isDefaultQuery.eq(SysDictData::getIsDefault, "Y");
            SysDictData sysDictData1 = sysDictDataService.getBaseMapper().selectOne(isDefaultQuery);
            if (null != sysDictData1 && !sysDictData1.getDictValueid().equals(sysDictData.getDictValueid())) {
                CommonResult commonResult = new CommonResult();
                commonResult.setMsg("该字段默认已经存在,字段名为:" + sysDictData1.getDictLabel());
                commonResult.setCode(HttpStatus.NOT_MODIFIED);
                return commonResult;
            }
        }

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        Date date = new Date();
        sysDictData.setCreateBy(user.getUserName());
        sysDictData.setCreateTime(date);
        sysDictData.setUpdateBy(user.getUserName());
        sysDictData.setUpdateTime(date);

        List<SysDictData> sysDictDatas = sysDictDataService.getBaseMapper().selectList(new QueryWrapper<>());
        int max = -1;
        for (SysDictData x : sysDictDatas) {
            if (Integer.parseInt(x.getDictValueid()) > max) {
                max = Integer.parseInt(x.getDictValueid());
            }
        }
        sysDictData.setDictValueid(String.valueOf((max + 1)));
        LambdaQueryWrapper<SysDictData> sysDictDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDictDataLambdaQueryWrapper.eq(SysDictData::getDictType, sysDictData.getDictType());
        List<SysDictData> sorts = sysDictDataService.getBaseMapper().selectList(sysDictDataLambdaQueryWrapper);
        if (null == sorts || 0 == sorts.size()) {
            sysDictData.setDictSort(1);
        } else {
            max = -1;
            for (SysDictData x : sorts) {
                if (x.getDictSort() > max) {
                    max = x.getDictSort();
                }
            }
            sysDictData.setDictSort((max + 1));
        }
        int insert = sysDictDataService.getBaseMapper().insert(sysDictData);
        CommonResult commonResult = new CommonResult();
        if (insert == 1) {
            commonResult.setMsg("新增成功");
            commonResult.setCode(HttpStatus.SUCCESS);
        } else {
            commonResult.setMsg("新增失败");
            commonResult.setCode(HttpStatus.ERROR);
        }
        return commonResult;
    }

    @Override
    public CommonResult getData(String dictValueid) {
        LambdaQueryWrapper<SysDictData> sysDictDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDictDataLambdaQueryWrapper.eq(SysDictData::getDictValueid, dictValueid);
        SysDictData sysDictData = sysDictDataService.getBaseMapper().selectOne(sysDictDataLambdaQueryWrapper);
        CommonResult commonResult = new CommonResult();
        commonResult.setMsg("查询成功");
        commonResult.setCode(HttpStatus.SUCCESS);
        commonResult.setData(sysDictData);
        return commonResult;
    }

    @Override
    public CommonResult updateData(SysDictData sysDictData) {
        if (sysDictData.getIsDefault().equalsIgnoreCase("Y")) {
            //判断是否存在默认
            LambdaQueryWrapper<SysDictData> isDefaultQuery = new LambdaQueryWrapper<>();
            isDefaultQuery.eq(SysDictData::getDictType, sysDictData.getDictType());
            isDefaultQuery.eq(SysDictData::getIsDefault, "Y");
            SysDictData sysDictData1 = sysDictDataService.getBaseMapper().selectOne(isDefaultQuery);
            if (null != sysDictData1 && !sysDictData1.getDictValueid().equals(sysDictData.getDictValueid())) {
                CommonResult commonResult = new CommonResult();
                commonResult.setMsg("该字段默认已经存在,字段名为:" + sysDictData1.getDictLabel());
                commonResult.setCode(HttpStatus.NOT_MODIFIED);
                return commonResult;
            }
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        sysDictData.setUpdateBy(user.getUserName());
        sysDictData.setUpdateTime(new Date());
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictData::getDictValueid, sysDictData.getDictValueid());
        int update = sysDictDataService.getBaseMapper().update(sysDictData, queryWrapper);
        CommonResult commonResult = new CommonResult();
        if (update == 1) {
            commonResult.setMsg("修改成功");
            commonResult.setCode(HttpStatus.SUCCESS);
        } else {
            commonResult.setMsg("修改失败");
            commonResult.setCode(HttpStatus.ERROR);
        }
        return commonResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult delData(String dictDatas) {
        String[] ids = dictDatas.split(",");
        int i = -1;
        if (ids.length == 1) {
            LambdaQueryWrapper<SysDictData> sysDictTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sysDictTypeLambdaQueryWrapper.eq(SysDictData::getDictValueid, ids[0]);
            i = sysDictDataService.getBaseMapper().delete(sysDictTypeLambdaQueryWrapper);
        } else {
            for (int i1 = 0; i1 < ids.length; i1++) {
                LambdaQueryWrapper<SysDictData> sysDictTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
                sysDictTypeLambdaQueryWrapper.eq(SysDictData::getDictValueid, ids[i1]);
                i = sysDictDataService.getBaseMapper().delete(sysDictTypeLambdaQueryWrapper);
                if (i != 1) {
                    throw new RuntimeException("ID为" + ids[i1] + "删除失败");
                }
            }
        }
        CommonResult commonResult = new CommonResult();
        if (i == 1) {
            commonResult.setMsg("删除成功");
            commonResult.setCode(HttpStatus.SUCCESS);
        } else {
            commonResult.setMsg("删除失败");
            commonResult.setCode(HttpStatus.ERROR);
        }
        return commonResult;
    }

    @Override
    public CommonGridResult clickNode(SysDictData sysDictData, String dictTypeid) {
        CommonGridResult result = null;
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysDictData::getCreateTime);
        //dictTypeid!=null说明点击的是第一级
        //如果dictTypeid=="000"说明点击的是顶级节点
        if ("000".equals(dictTypeid)) {
            LambdaQueryWrapper<SysDictType> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.orderByDesc(SysDictType::getUpdateTime);
            if(!StringUtils.isEmpty(sysDictData.getDictLabel())){
                wrapper1.like(SysDictType::getDictName,sysDictData.getDictLabel() );
            }
            PageHelper.startPage(sysDictData.getPageNum(), sysDictData.getPageSize());
            List<SysDictType> list = sysDictTypeMapper.selectList(wrapper1);
            Page page = (Page) list;
            List<JSONObject> jsonObjects = new ArrayList<>();
            for (SysDictType sd : list) {
                JSONObject object = (JSONObject) JSONObject.toJSON(sd);
                object.put("dictLabel", object.get("dictName"));
                jsonObjects.add(object);
            }
            return result = new CommonGridResult(sysDictData.getPageNum(), sysDictData.getPageSize(), page.getTotal(), jsonObjects);
        }

        if (dictTypeid != null) {
            LambdaQueryWrapper<SysDictData> wrapper111 = new LambdaQueryWrapper<>();
            wrapper111.eq(SysDictData::getParentId, dictTypeid);
            if(!StringUtils.isEmpty(sysDictData.getDictLabel())){
                wrapper111.like(SysDictData::getDictLabel,sysDictData.getDictLabel() );
            }
            wrapper111.orderByDesc(SysDictData::getUpdateTime);
            PageHelper.startPage(sysDictData.getPageNum(), sysDictData.getPageSize());
            List<SysDictData> sysDictDataList = sysDictDataMapper.selectList(wrapper111);
            Page page = (Page) sysDictDataList;
            return result = new CommonGridResult(sysDictData.getPageNum(), sysDictData.getPageSize(), page.getTotal(), sysDictDataList);
        }
        if (sysDictData.getDictValueid() != null && !"".equals(sysDictData.getDictValueid())) {
            LambdaQueryWrapper<SysDictData> wrapper11 = new LambdaQueryWrapper<>();
            wrapper11.orderByDesc(SysDictData::getUpdateTime);
            wrapper11.eq(SysDictData::getParentId, sysDictData.getDictValueid());
            PageHelper.startPage(sysDictData.getPageNum(), sysDictData.getPageSize());
            List<SysDictData> dataList = sysDictDataMapper.selectList(wrapper11);
            Page page = (Page) dataList;
            //如果有子节点数据则返沪子节点数据，没有则返回该节点数据
            if (dataList != null && dataList.size() > 0) {
                return result = new CommonGridResult(sysDictData.getPageNum(), sysDictData.getPageSize(), page.getTotal(), dataList);
            } else {
                List<SysDictData> sysDictDataList = new ArrayList<>();
                SysDictData dictData = sysDictDataMapper.selectById(sysDictData.getDictValueid());
                sysDictDataList.add(dictData);
                return result = new CommonGridResult(sysDictData.getPageNum(), sysDictData.getPageSize(), 1, sysDictDataList);
            }
        }
        return new CommonGridResult(sysDictData.getPageNum(), sysDictData.getPageSize(), 0, null);
    }

    @Override
    public CommonResult treeList(String id) {
        CommonResult result = null;
        //先查询sysDictType,获取从二级节点
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        List<SysDictType> sysDictTypes = sysDictTypeMapper.selectList(wrapper);
        List<JSONObject> list = new ArrayList<>();
        if (id == null) {
            for (SysDictType st : sysDictTypes) {
                JSONObject object = (JSONObject) JSONObject.toJSON(st);
                object.put("dictLabel", object.get("dictName"));
                list.add(object);
            }
            result = new CommonResult(200, "获取成功", list);
            return result;
        } else {
            LambdaQueryWrapper<SysDictData> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(SysDictData::getParentId, id);
            List<SysDictData> dataList = sysDictDataMapper.selectList(wrapper1);
            result = new CommonResult(200, "获取成功", dataList);
            return result;
        }

//        查询sysDictDate,获取子节点
//        for (SysDictType st:sysDictTypes){
//            LambdaQueryWrapper<SysDictData> dataWrapper=new LambdaQueryWrapper<>();
//            dataWrapper.eq(SysDictData::getParentId,st.getDictTypeid());
////            dataWrapper.eq(SysDictData::getDictType,st.getDictType());
//            List<SysDictData> dataList=sysDictDataMapper.selectList(dataWrapper);
//            JSONObject object= (JSONObject) JSONObject.toJSON(st);
//            List<JSONObject> objectList=findSonNode(dataList,st.getDictTypeid());
//            object.put("children",objectList);
//            object.put("dictLabel",object.get("dictName"));
//            list.add(object);
//        }
    }

//    /**
//     * 递归生成字典树
//     * @param sysDictData
//     * @param parentId
//     * @return
//     */
//    public List<JSONObject> findSonNode(List<SysDictData> sysDictData,String parentId){
//        //找下级节点
//        List<JSONObject> returnList=new ArrayList();
//        for(SysDictData sd:sysDictData){
//            String pid=sd.getDictValueid();
//            LambdaQueryWrapper<SysDictData> wrapper=new LambdaQueryWrapper<>();
//            wrapper.eq(SysDictData::getParentId,pid);
//            List<SysDictData> list=sysDictDataMapper.selectList(wrapper);
//            JSONObject jsonObject= (JSONObject) JSONObject.toJSON(sd);
//            if (list!=null&&list.size()>0){
//                jsonObject.put("children",list);
//                returnList.add(jsonObject);
//                this.findSonNode(list,pid);
//            }else {
//                returnList.add(jsonObject);
//            }
//        }
//        return returnList;
//    }

    @Override
    public CommonResult addNodeById(String dictType, String status, String remark, String dictLabel, String dictTypeid, String dictValueid) {
        CommonResult result = null;
        String uuid = UUID.randomUUID().toString();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        Date date = new Date();
        if ("000".equals(dictTypeid)) {
            SysDictType type = new SysDictType();
            type.setDictTypeid(uuid);
            type.setStatus(status);
            type.setRemark(remark);
            type.setDictType(dictType);
            type.setDictName(dictLabel);
            type.setUpdateBy(user.getUserName());
            type.setCreateBy(user.getUserName());
            type.setUpdateTime(date);
            type.setCreateTime(date);
            int i = sysDictTypeMapper.insert(type);
            if (i > 0) {
                result = new CommonResult(200, "新增成功", i);
            } else {
                result = new CommonResult(500, "新增失败", i);
            }
            return result;
        }
        if (!"000".equals(dictTypeid) && "".equals(dictValueid)) {
            SysDictData data = new SysDictData();
            data.setDictValueid(uuid);
            data.setParentId(dictTypeid);
            data.setStatus(status);
            data.setRemark(remark);
            data.setDictType(dictType);
            data.setDictLabel(dictLabel);
            data.setUpdateTime(date);
            data.setUpdateBy(user.getUserName());
            data.setCreateTime(date);
            data.setCreateBy(user.getUserName());
            int i = sysDictDataMapper.insert(data);
            if (i > 0) {
                result = new CommonResult(200, "新增成功", i);
            } else {
                result = new CommonResult(500, "新增失败", i);
            }
            return result;
        }
        if (!"".equals(dictTypeid) && !"".equals(dictValueid)) {
            //生成字典表三级及一下结构
            SysDictData data = new SysDictData();
            data.setDictValueid(uuid);
            data.setParentId(dictValueid);
            data.setStatus(status);
            data.setRemark(remark);
            data.setDictType(dictType);
            data.setDictLabel(dictLabel);
            data.setUpdateTime(date);
            data.setUpdateBy(user.getUserName());
            data.setCreateTime(date);
            data.setCreateBy(user.getUserName());
            int i = sysDictDataMapper.insert(data);
            if (i > 0) {
                result = new CommonResult(200, "新增成功", i);
            } else {
                result = new CommonResult(500, "新增失败", i);
            }
            return result;
        }
        return new CommonResult(500, "系统异常，请联系管理员", 0);
    }

    @Override
    public CommonResult deleteNodeById(String id) {
        CommonResult result = null;
        int i = sysDictDataMapper.deleteById(id);
        int j = sysDictTypeMapper.deleteById(id);
        if (j > 0 || i > 0) {
            result = new CommonResult(200, "删除成功", 1);
        } else {
            result = new CommonResult(500, "系统异常，请联系管理员", 0);
        }
        return result;
    }

    @Override
    public CommonResult addorUpdateTypeNode(SysDictType sysDictType) {
        Boolean aBoolean = Boolean.valueOf(sysDictType.getJsonStr());
        if(!aBoolean){
            LambdaQueryWrapper<SysDictType> sysDictTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sysDictTypeLambdaQueryWrapper.eq(SysDictType::getDictType,sysDictType.getDictType());
            SysDictType sysDictType1 = baseMapper.selectOne(sysDictTypeLambdaQueryWrapper);
            if(null!=sysDictType1){
                return CommonResult.error(500, "字段类型已存在");
            }
        }
        CommonResult result = null;
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        Date date = new Date();
        int i = 0;
        if (sysDictType.getDictTypeid() == null || "".equals(sysDictType.getDictTypeid())) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            sysDictType.setDictTypeid(uuid);
            sysDictType.setCreateBy(user.getUserName());
            sysDictType.setCreateTime(date);
            sysDictType.setUpdateTime(date);
            sysDictType.setUpdateBy(user.getUserName());
            i = sysDictTypeMapper.insert(sysDictType);
        } else {
            sysDictType.setUpdateBy(user.getUserName());
            sysDictType.setUpdateTime(date);
            i = sysDictTypeMapper.updateById(sysDictType);
        }
        if (i > 0) {
            result = new CommonResult(200, "成功", i);
        } else {
            result = new CommonResult(500, "失败", i);
        }
        return result;
    }

    @Override
    public CommonGridResult queryListByName(String dictLabel, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        if (dictLabel != null && !"".equals(dictLabel)) {
            wrapper.like(SysDictType::getDictName, dictLabel);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<SysDictType> list = sysDictTypeMapper.selectList(wrapper);
        Page page = (Page) list;
        List<JSONObject> objectList = new ArrayList<>();
        for (SysDictType dictType : list) {
            JSONObject object = (JSONObject) JSONObject.toJSON(dictType);
            object.put("dictLabel", object.get("dictName"));
            objectList.add(object);
        }
        return new CommonGridResult(pageNum, pageSize, page.getTotal(), objectList);
    }
}
