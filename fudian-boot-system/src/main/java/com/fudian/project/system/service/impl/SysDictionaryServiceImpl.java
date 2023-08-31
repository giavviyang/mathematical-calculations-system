package com.fudian.project.system.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.SysDictionary;
import com.fudian.api.system.pojo.SysFile;
import com.fudian.api.system.service.SysDictionaryApi;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.system.mapper.SysDictionaryMapper;
import com.fudian.project.system.service.SysDictionaryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements SysDictionaryService, SysDictionaryApi {

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    private static final ConcurrentHashMap<String, Lock> LOCK_POOL = new ConcurrentHashMap<>();

    static {
        LOCK_POOL.putIfAbsent("insert", new ReentrantLock(true));
        LOCK_POOL.putIfAbsent("modify", new ReentrantLock(true));
    }


    @Override
    public int updateBatch(List<SysDictionary> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SysDictionary> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SysDictionary record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SysDictionary record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonResult querySysDictionaryByType(SysDictionary sysDictionary) {
        LambdaQueryWrapper<SysDictionary> lambdaQueryWrappe = new LambdaQueryWrapper<>();
        lambdaQueryWrappe.eq(SysDictionary::getDictType, sysDictionary.getDictType()).ne(SysDictionary::getDictCode, "-");
        if (sysDictionary.getEparentCode() != null && !sysDictionary.getEparentCode().equals("")) {
            lambdaQueryWrappe.eq(SysDictionary::getEparentCode, sysDictionary.getEparentCode());
        }
        List<SysDictionary> list = baseMapper.selectList(lambdaQueryWrappe);
        if (list != null && list.size() > 0) {

            //将从字典表查询到的dict_type = 'FDA01State',dict_code='7'已转出从数据中去除，不显示在前台页面
            if (list.get(0).getDictType().equals("FDA01State")){
                Iterator<SysDictionary> it_b = list.iterator();
                while(it_b.hasNext()){
                    SysDictionary dictionary=it_b.next();
                    if (dictionary.getDictCode().equals("7")) {
                        it_b.remove();
                    }
                }
            }
            return CommonResult.success("查询成功", list);
        } else {
            return CommonResult.error(400, "未查询到数据");
        }
    }

    @Override
    public CommonResult querySysDictionaryByTypes(String types) {
        LambdaQueryWrapper<SysDictionary> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        String tmptypes[] = types.split("-");
        String tmps[] = new String[tmptypes.length];
        for (int i = 0; i < tmptypes.length; i++) {
            if (tmptypes[i].split(":").length > 1) {
                tmps[i] = tmptypes[i].split(":")[0];
            } else {
                tmps[i] = tmptypes[i];
            }
        }
        lambdaQueryWrapper.in(SysDictionary::getDictType, tmps).ne(SysDictionary::getDictCode, "-");
        List<SysDictionary> sysDictionaries = baseMapper.selectList(lambdaQueryWrapper);
        Map<String, List<SysDictionary>> sysDictionarieMap = sysDictionaries.stream().collect(Collectors.groupingBy(SysDictionary::getDictType));

        Map<String, List<SysDictionary>> returnMap = new HashMap<>();
        for (String type : types.split("-")) {
            String tmp[] = type.split(":");
            type = tmp[0];
            List<SysDictionary> sysDictionaryList = sysDictionarieMap.get(type);

            //将从字典表查询到的dict_type = 'FDA01State',dict_code='7'已转出从数据中去除，不显示在前台页面
            if (sysDictionaryList.get(0).getDictType().equals("FDA01State")){
                Iterator<SysDictionary> it_b = sysDictionaryList.iterator();
                while(it_b.hasNext()){
                    SysDictionary dictionary=it_b.next();
                    if (dictionary.getDictCode().equals("7")) {
                        it_b.remove();
                    }
                }
            }

            List<SysDictionary> sysDictionaryList1 = sysDictionaryList.stream().filter(x -> {
            if (tmp.length > 1) {
                if (x.getEparentCode().equals(tmp[1])) {
                    return true;
                } else {
                    return false;
                }
            }
            return true;
            }).collect(Collectors.toList());
            //Map<String, SysDictionary> sysDictionaryMap = sysDictionaryList.stream().collect(Collectors.toMap(SysDictionary::getDictCode, a -> a,(k1,k2)->k1));
            returnMap.put(type, sysDictionaryList1);
        }
        return CommonResult.success("", returnMap);
    }

    @Override
    public CommonResult queryMaterialsTree() {

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("dict_type", "ZB97A");
        List<SysDictionary> list = sysDictionaryMapper.selectList(wrapper);
        List<SysDictionary> childrenList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDictCode().equals("-")) {
                list.remove(i);
            }
            if (!list.get(i).getEparentCode().equals("0")) {
                childrenList.add(list.get(i));
            }
        }
        JSONArray jsonArray = new JSONArray();
        for (SysDictionary dictionary : list) {
            if (dictionary.getEparentCode().equals("0")) {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(dictionary));
                JSONArray array = new JSONArray();
                for (SysDictionary sysDictionary : childrenList) {
                    if (sysDictionary.getEparentCode().equals(dictionary.getDictCode())) {
                        array.add(sysDictionary);
                    }
                }
                jsonObject.put("children", array);
                jsonArray.add(jsonObject);
            }
        }
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(200);
        commonResult.setData(jsonArray);
        return commonResult;
    }

    @Override
    public CommonResult queryArchiveTreeDatas() {
        LambdaQueryWrapper<SysDictionary> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SysDictionary::getDictType, "ZB97B");
        lambdaQueryWrapper.ne(SysDictionary::getDictCode, "-").ne(SysDictionary::getDictCode, "4").ne(SysDictionary::getDictCode, "9");
        List<SysDictionary> sysDictionaries = baseMapper.selectList(lambdaQueryWrapper);

        return CommonResult.success("", sysDictionaries);
    }

    /**
     * 根据ID获取树
     *
     * @param id
     * @return
     */
    @Override
    public CommonResult queryTreeById(String id) {
        // 如果不存在获取全部
        if (StringUtils.isEmpty(id)) {
            id = "-";
        }
        // 条件
        LambdaQueryWrapper<SysDictionary> sysDictionaryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getDictCode, id);
        List<SysDictionary> sysDictionaries = baseMapper.selectList(sysDictionaryLambdaQueryWrapper);
        List<JSONObject> result = new LinkedList<>();
        // 查询子集
        if (null != sysDictionaries && 0 < sysDictionaries.size()) {
            for (SysDictionary x : sysDictionaries) {
                LambdaQueryWrapper<SysDictionary> privateQueryWapper = new LambdaQueryWrapper<>();
                privateQueryWapper.eq(SysDictionary::getDictType, x.getDictType());
                privateQueryWapper.eq(SysDictionary::getEparentCode, "");
                privateQueryWapper.ne(SysDictionary::getDictCode, "-");
                List<SysDictionary> children = baseMapper.selectList(privateQueryWapper);
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(x));
                jsonObject.put("children", children);
                // 迭代子集
                getSubNode(jsonObject);
                result.add(jsonObject);
            }
        }
        return new CommonResult(200, "查询成功", result);
    }

    /**
     * 获取子节点
     *
     * @param list
     */
    private void getSubNode(JSONObject list) {
        // 获取集合
        JSONArray children = list.getJSONArray("children");
        if (null != children && 0 < children.size()) {
            // 递归遍历
            for (int i = 0; i < children.size(); i++) {
                JSONObject jsonObject = children.getJSONObject(i);
                LambdaQueryWrapper<SysDictionary> getQueryWapper = new LambdaQueryWrapper<>();
                getQueryWapper.eq(SysDictionary::getEparentCode, jsonObject.getString("dictCode"));
                getQueryWapper.eq(SysDictionary::getDictType, jsonObject.getString("dictType"));
                List<SysDictionary> subChildren = baseMapper.selectList(getQueryWapper);
                jsonObject.put("children", subChildren);
                getSubNode(jsonObject);
            }
        }
    }

    /**
     * 全部加载不可能的,不是因为数据量庞大,是因为处理嵌套关系时间太长
     *
     * @return
     */
    @Override
    public CommonGridResult queryTreeByIdNew(SysDictionary sysDictionary) {
        if (StringUtils.isEmpty(sysDictionary.getEparentCode())) {
            sysDictionary.setEparentCode("000");
        }
        LambdaQueryWrapper<SysDictionary> sysDictionaryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if ("000".equals(sysDictionary.getEparentCode())) {
            sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getEparentCode, sysDictionary.getEparentCode());
        } else {
            sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getDictType, sysDictionary.getDictType());
            sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getEparentCode, sysDictionary.getEparentCode());
        }

        PageHelper.startPage(sysDictionary.getPageNum(), sysDictionary.getPageSize());
        List<SysDictionary> sysDictionaries = baseMapper.selectList(sysDictionaryLambdaQueryWrapper);
        Page<SysDictionary> pages = (Page<SysDictionary>) sysDictionaries;
        List<JSONObject> result = new LinkedList<>();
        sysDictionaries.forEach(x -> {
            String s = JSONObject.toJSONString(x);
            JSONObject jsonObject = JSONObject.parseObject(s);
            jsonObject.put("children", new ArrayList());
            result.add(jsonObject);
        });
        return new CommonGridResult(pages.getPageNum(), pages.getPageSize(), pages.getTotal(), result);
    }

    @Transactional
    @Override
    public CommonResult deleteByIds(String json) {
        List<SysDictionary> jsonArray = JSONArray.parseArray(json, SysDictionary.class);
        boolean flag = true;
        String message = "";
        for (SysDictionary sysDictionary : jsonArray) {
            LambdaQueryWrapper<SysDictionary> sysDictionaryLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getDictType,sysDictionary.getDictType());
            sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getEparentCode,sysDictionary.getDictCode());
            List<SysDictionary> sysDictionaries = baseMapper.selectList(sysDictionaryLambdaQueryWrapper);
            if(null!=sysDictionaries&&0<sysDictionaries.size()){
                flag = false;
                message += sysDictionary.getFullName()+"、";
            }
        }

        if(flag){
            for (SysDictionary sysDictionary : jsonArray) {
                LambdaQueryWrapper<SysDictionary> sysDictionaryLambdaQueryWrapper = new LambdaQueryWrapper<>();
                sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getDictType,sysDictionary.getDictType());
                sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getDictCode,sysDictionary.getDictCode());
                sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getEparentCode,sysDictionary.getEparentCode());
                int delete = baseMapper.delete(sysDictionaryLambdaQueryWrapper);
                if(delete<=0){
                    flag = false;
                    message+= sysDictionary.getFullName()+"、";
                }
            }
        }else{
            return CommonResult.error(500,message+"节点下存在子节点,无法删除");
        }

        if(flag){
            return new CommonResult(200,"删除成功" );
        }else{
            return new CommonResult(500,"删除失败" );

        }
    }

    @Override
    public CommonResult insertSysDictionary(SysDictionary sysDictionary) {
        LOCK_POOL.get("insert").lock();
        try {
            if ("-".equals(sysDictionary.getDictCode())) {
                LambdaQueryWrapper<SysDictionary> queryOne = new LambdaQueryWrapper<>();
                queryOne.eq(SysDictionary::getDictType, sysDictionary.getDictType());
                queryOne.eq(SysDictionary::getDictCode, "-");
                SysDictionary isParent = baseMapper.selectOne(queryOne);
                if (null == isParent) {
                    sysDictionary.setCodeAttr("011");
                    int insert = baseMapper.insert(sysDictionary);
                    if (0 < insert) {
                        return new CommonResult(200, "新增成功");
                    } else {
                        return new CommonResult(500, "新增失败");
                    }
                } else {
                    return new CommonResult(500, "代码类型已存在");
                }
            } else {
                LambdaQueryWrapper<SysDictionary> queryFour = new LambdaQueryWrapper<>();
                queryFour.eq(SysDictionary::getDictType, sysDictionary.getDictType());
                queryFour.eq(SysDictionary::getDictCode, sysDictionary.getDictCode());
                SysDictionary isParent = baseMapper.selectOne(queryFour);
                if (null != isParent) {
                    return new CommonResult(500, "代码已存在");
                }
                LambdaQueryWrapper<SysDictionary> querySix = new LambdaQueryWrapper<>();
                querySix.eq(SysDictionary::getDictType, sysDictionary.getDictType());
                querySix.eq(SysDictionary::getFullName, sysDictionary.getFullName());
                SysDictionary isNameCz = baseMapper.selectOne(querySix);
                if (null != isNameCz) {
                    return new CommonResult(500, "代码全称已存在");
                }
                sysDictionary.setCodeAttr("011");
                int insert = baseMapper.insert(sysDictionary);
                if (0 < insert) {
                    return new CommonResult(200, "新增成功");
                } else {
                    return new CommonResult(500, "新增失败");
                }
            }
        } finally {
            LOCK_POOL.get("insert").unlock();
        }
    }

    @Override
    public CommonResult modifySysDictionary(SysDictionary sysDictionary) {
        LOCK_POOL.get("modify").lock();
        try {
            LambdaQueryWrapper<SysDictionary> queryOne = new LambdaQueryWrapper<>();
            queryOne.eq(SysDictionary::getDictType, sysDictionary.getDictType());
//            queryOne.eq(SysDictionary::getDictCode, sysDictionary.getDictCode());
            queryOne.eq(SysDictionary::getEparentCode, sysDictionary.getEparentCode());
            queryOne.eq(SysDictionary::getFullName, sysDictionary.getFullName());
            SysDictionary isParent = baseMapper.selectOne(queryOne);
            if (null == isParent) {
                LambdaQueryWrapper<SysDictionary> modifyOne = new LambdaQueryWrapper<>();
                modifyOne.eq(SysDictionary::getDictType, sysDictionary.getDictType());
                modifyOne.eq(SysDictionary::getDictCode, sysDictionary.getDictCode());
                modifyOne.eq(SysDictionary::getEparentCode, sysDictionary.getEparentCode());
                int insert = baseMapper.update(sysDictionary, modifyOne);
                if (0 < insert) {
                    return new CommonResult(200, "修改成功");
                } else {
                    return new CommonResult(500, "修改失败");
                }
            } else {
                return new CommonResult(500, "代码全称已存在");
            }

        } finally {
            LOCK_POOL.get("modify").unlock();
        }
    }

    @Override
    public SysDictionary getOne(Wrapper<SysDictionary> queryWrapper) {
            return baseMapper.selectOne(queryWrapper);
    }


    /**
     * @auther xuchenghong
     * 根据类型和Eparent查询
     * @return
     */
    @Override
    public List<SysDictionary> queryByTypeAndEparent(String type,String eparent){
        LambdaQueryWrapper<SysDictionary> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDictionary::getDictType, type);
        lambdaQueryWrapper.eq(SysDictionary::getEparentCode, eparent);
        List<SysDictionary> sysDictionaries = baseMapper.selectList(lambdaQueryWrapper);
        return sysDictionaries;
    }

    @Override
    public CommonResult getDataByEparentCode(SysDictionary sysDictionary) {
        List<Map<String, Object>> dataByEparentCode = sysDictionaryMapper.getDataByEparentCode(sysDictionary);
        for (Map<String, Object> stringObjectMap : dataByEparentCode) {
            stringObjectMap.put("leaf",false);
        }
        return CommonResult.success(dataByEparentCode);
    }
}
