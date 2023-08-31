package com.fudian.project.ams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.ams.pojo.E01zcRela;
import com.fudian.api.ams.service.E01zcRelaApi;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.SysDictionary;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.SysDeptApi;
import com.fudian.api.system.service.SysDictionaryApi;
import com.fudian.api.system.service.TokenApi;
import com.fudian.common.utils.ServletUtils;
import com.fudian.project.ams.mapper.E01zcMapper;
import com.fudian.project.ams.mapper.E01zcRelaMapper;
import com.fudian.project.ams.pojo.E01zc;
import com.fudian.project.ams.service.E01z1Service;
import com.fudian.project.ams.service.E01zcRelaService;
import com.fudian.project.ams.utils.Map2Bean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class E01zcRelaServiceImpl extends ServiceImpl<E01zcRelaMapper, E01zcRela> implements E01zcRelaService, E01zcRelaApi {
    @Autowired
    E01zcRelaMapper e01zcRelaMapper;
    @Autowired
    E01zcMapper e01zcMapper;
    @Autowired
    TokenApi tokenService;
    @Autowired
    E01z1Service e01z1Service;
    @Autowired
    SysDictionaryApi sysDictionaryService;
    @Autowired
    SysDeptApi sysDeptService;

    @Override
    public int updateBatch(List<E01zcRela> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<E01zcRela> list) {
        this.saveBatch(list);
        return list.size();
//         baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(E01zcRela record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(E01zcRela record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public List<E01zcRela> queryE01zcRelaList(String id) {
        LambdaQueryWrapper<E01zcRela> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(E01zcRela::getE01zcId, id);
//        wrapper.eq(E01zcRela::getFlag,"1");
        List<E01zcRela> e01zcRelaList = baseMapper.selectList(wrapper);
        return e01zcRelaList;
    }

    @Override
    public int updateE01zcRelaById(E01zcRela e01zcRela) throws IllegalAccessException {
        UpdateWrapper<E01zcRela> wrapper = new UpdateWrapper<>();
        //如果前端传的是一个bean类
        E01zcRela rela = e01zcRelaMapper.selectById(e01zcRela.getId());
        rela.setFeedback(e01zcRela.getFeedback());
        int res = 0;
        try {
            res = e01zcRelaMapper.updateById(rela);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int insertE01zcRelaList(String uid, List<E01zcRela> e01zcRelaList) {
        for (int i = 0; i < e01zcRelaList.size(); i++) {
            E01zcRela e01zcRela = e01zcRelaList.get(i);
            String id = UUID.randomUUID().toString().replace("-", "");
            e01zcRela.setId(id);
            e01zcRela.setFlag("1");
            e01zcRela.setE01zcId(uid);
        }
        int res = e01zcRelaMapper.batchInsert(e01zcRelaList);
        return res;
    }

    @Override
    public List<E01zcRela> queryE01ZcRelaList(List<String> idList) {
        QueryWrapper<E01zcRela> wrapper = new QueryWrapper<>();
        wrapper.in("e01zc_id", idList);
        List<E01zcRela> e01zcRelaList = e01zcRelaMapper.selectList(wrapper);
        return e01zcRelaList;
    }

    @Override
    public List<Map<String, Object>> queryListByA01AndText(String userId, String a0101, String deptName, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<E01zcRela> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(E01zcRela::getA0101, a0101);
        wrapper.like(E01zcRela::getDeptName, deptName);
        List<E01zcRela> e01zcRelas = e01zcRelaMapper.selectList(wrapper);
        List<String> e01zcIds = e01zcRelas.stream().map(e -> e.getE01zcId()).distinct().collect(Collectors.toList());
        //取出父表数据
        if (e01zcIds.size() == 0) {
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        LambdaQueryWrapper<E01zc> e01zcLambdaQueryWrapper = new LambdaQueryWrapper<>();
        e01zcLambdaQueryWrapper.in(E01zc::getId, e01zcIds);
        e01zcLambdaQueryWrapper.eq(E01zc::getUserId, userId);
        PageHelper.startPage(pageNum, pageSize);
        List<E01zc> e01zcList = e01zcMapper.selectList(e01zcLambdaQueryWrapper);
        if (e01zcList.size() == 0) {
            return null;
        }
        for (int i = 0; i < e01zcList.size(); i++) {
            Map<String, Object> map = null;
            try {
                map = Map2Bean.bean2map(e01zcList.get(i));
                LambdaQueryWrapper<E01zcRela> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(E01zcRela::getE01zcId, e01zcList.get(i).getId());
                List<E01zcRela> e01zcRelaList = e01zcRelaMapper.selectList(wrapper1);
                map.put("children", e01zcRelaList);
                list.add(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public int deleteE01zcRela(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("e01zc_id", id);
        int i = e01zcRelaMapper.delete(wrapper);
        return i;
    }

    @Override
    public List<E01zcRela> quertNoApproval() {
        LambdaQueryWrapper<E01zcRela> wrapper = new LambdaQueryWrapper();
        wrapper.eq(E01zcRela::getFlag, "1");
        wrapper.groupBy(E01zcRela::getE01zcId);
        List<E01zcRela> relaList = e01zcRelaMapper.selectList(wrapper);
        return relaList;
    }

    @Override
    public int ApprovalElec(String id, List<String> idList) {
        LambdaQueryWrapper<E01zcRela> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(E01zcRela::getE01zcId, id);
        wrapper.ne(E01zcRela::getId, idList);
        List<E01zcRela> e01zcRelaList = e01zcRelaMapper.selectList(wrapper);
        if (e01zcRelaList == null || e01zcRelaList.size() == 0) {
            return 0;
        }
        for (E01zcRela e01zcRela : e01zcRelaList) {
            e01zcRela.setFlag("2");
        }
        int res = e01zcRelaMapper.updateBatch(e01zcRelaList);
        return res;
    }

    @Override
    public List<E01zcRela> queryArchiveInTime(String id, String a01Id) {
        LambdaQueryWrapper<E01zcRela> wrapper = new LambdaQueryWrapper<>();
        Date today = new Date();
        LocalDateTime time = today.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        wrapper.eq(E01zcRela::getE01z111a, id);
        wrapper.gt(E01zcRela::getE01z805, time);
        wrapper.eq(E01zcRela::getA01Id, a01Id);
        List<E01zcRela> e01zcRelas = e01zcRelaMapper.selectList(wrapper);
        return e01zcRelas;
    }

    @Override
    public int updateE01zcRela(E01zcRela e01zcRela) {
        LambdaQueryWrapper<E01zcRela> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(E01zcRela::getId, e01zcRela.getId());
        int i = e01zcRelaMapper.update(e01zcRela, wrapper);
        return i;
    }


    @Override
    public List<E01z1> getE01zcRelaByE01z1Id(String a01Id) {
        LambdaQueryWrapper<E01zcRela> wrapper = new LambdaQueryWrapper<>();
        Date today = new Date();
        wrapper.eq(E01zcRela::getA01Id, a01Id);
        wrapper.ge(E01zcRela::getE01z805, today);
        List<E01zcRela> e01zcRelas = e01zcRelaMapper.selectList(wrapper);
        List<String> ids = e01zcRelas.stream().map(e -> e.getE01z1Id()).collect(Collectors.toList());

        if (ids == null || ids.size() <= 0) {
            return null;
        }

        QueryWrapper<E01z1> e01z1QueryWrapper = new QueryWrapper<>();
        e01z1QueryWrapper.lambda().in(E01z1::getId, ids);
        List<E01z1> e01z1s = e01z1Service.getBaseMapper()
                .selectList(e01z1QueryWrapper);

        return e01z1s;
    }

    @Override
    public boolean judgeAthor(String e01z1Id) {
        LambdaQueryWrapper<E01zcRela> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(E01zcRela::getE01z1Id, e01z1Id);
        Date today = new Date();
        wrapper.ge(E01zcRela::getE01z805, today);
        List<E01zcRela> e01zcRelaList = e01zcRelaMapper.selectList(wrapper);
        if (e01zcRelaList == null || e01zcRelaList.size() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<String> getA01Author() {
        //获取登录用户的信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        LambdaQueryWrapper<E01zc> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(E01zc::getUserId, user.getUserId());
        List<E01zc> e01zcs = e01zcMapper.selectList(wrapper);
        List<String> ids = e01zcs.stream().map(e -> e.getId()).collect(Collectors.toList());
        if (ids == null || ids.size() <= 0) {
            return null;
        }
        LambdaQueryWrapper<E01zcRela> queryWrapper = new LambdaQueryWrapper<>();
        Date today = new Date();
        queryWrapper.in(E01zcRela::getE01zcId, ids);
        queryWrapper.ge(E01zcRela::getE01z805, today);
        List<E01zcRela> e01zcRelaList = e01zcRelaMapper.selectList(queryWrapper);
        if (e01zcRelaList == null || e01zcRelaList.size() == 0) {
            return null;
        }
        List<String> a01List = e01zcRelaList.stream().map(e -> e.getA01Id()).distinct().collect(Collectors.toList());
        return a01List;
    }

    @Override
    public List<E01zcRela> queryE01zcRelaListNotApprov(String id) {
        LambdaQueryWrapper<E01zcRela> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(E01zcRela::getE01zcId, id);
        wrapper.eq(E01zcRela::getFlag, "1");
        List<E01zcRela> e01zcRelaList = baseMapper.selectList(wrapper);
        return e01zcRelaList;
    }

    /**
     * 查询部门数据 并封装成map
     *
     * @return
     */
    public Map<String, String> getDeptMap() {
        List<SysDept> sysDepts = sysDeptService.list(null);
        Map<String, String> map = new HashMap<>();
        sysDepts.stream().forEach(x -> {
            map.put(x.getId(), x.getDeptName());
        });
        return map;
    }

    /**
     * 查询申请信息子集   并转换为tree
     *
     * @param e01zcId
     * @return
     */
    @Override
    public List<Map<String, Object>> queryE01zcRelaTreeByE01zcId(String e01zcId) {

        List<Map<String, Object>> mapList = new ArrayList<>();

        //部门map
        Map<String, String> deptMap = getDeptMap();

        //查询信息子集元数据
        LambdaQueryWrapper<E01zcRela> e01zcRelaListLambdaQueryWrapper = new LambdaQueryWrapper();
        e01zcRelaListLambdaQueryWrapper.eq(E01zcRela::getE01zcId, e01zcId);
        List<E01zcRela> e01zcRelaList = baseMapper.selectList(e01zcRelaListLambdaQueryWrapper);

        //根据被申请人查询出全部的材料数据
        List<String> a01Ids = e01zcRelaList.stream().map(E01zcRela::getA01Id).collect(Collectors.toList());
        LambdaQueryWrapper<E01z1> e01z1LambdaQueryWrapper = new LambdaQueryWrapper<>();
        e01z1LambdaQueryWrapper.in(E01z1::getA01Id, a01Ids);
        List<E01z1> e01z1List ;

        if(a01Ids.size() > 0){
            e01z1List = e01z1Service.list(e01z1LambdaQueryWrapper);
        }else{
            e01z1List = new ArrayList<>();
        }

        //查询十大类
//         = null;
        QueryWrapper<SysDictionary> sysDictionaryQueryWrapper = new QueryWrapper<>();
        sysDictionaryQueryWrapper.eq("dict_type", "ZB97B");
//        sysDictionaryQueryWrapper.eq("Eparent_code","0");
        sysDictionaryQueryWrapper.ne("dict_code", "-");
        final List<SysDictionary> dictlist = sysDictionaryService.list(sysDictionaryQueryWrapper);

        //生成树
        e01zcRelaList.stream().forEach(x -> {
            //查询该信息子级的e01z1s
            List<E01z1> e01z1s = e01z1List.stream().filter(y -> {
                return y.getA01Id().equals(x.getA01Id());
            }).collect(Collectors.toList());

            //生成树的一级目录  干部姓名
            Map<String, Object> node = new HashMap<>();
            node.put("id", x.getA01Id());
            String deptName = "";
            if(deptMap.get(x.getDeptId())!=null){
                deptName = "-" + deptMap.get(x.getDeptId());
            }
            node.put("label", x.getA0101() + deptName);
            node.put("data", x);

            //获取树的二级目录或三级目录
            node.put("children", getA01E01z1TreeByE01zcRela(dictlist, x, x.getA01Id(), e01z1s));
            mapList.add(node);
        });
        return mapList;
    }

    @Override
    public List<Map<String, Object>> queryE01zcRelaTreeByE01zcRelaId(String e01zcRelaId) {
        E01zcRela e01zcRela = this.baseMapper.selectById(e01zcRelaId);
        String e01z1Ids = e01zcRela.getAdoptE01z1Id();
        if(e01zcRela.getAdoptE01z1Id()==null||"".equals(e01zcRela.getAdoptE01z1Id())){
            e01z1Ids = e01zcRela.getE01z1Id();
        }
        LambdaQueryWrapper<E01z1> e01z1LambdaQueryWrapper = new LambdaQueryWrapper<>();
        e01z1LambdaQueryWrapper.eq(E01z1::getA01Id, e01zcRela.getA01Id());
        e01z1LambdaQueryWrapper.orderByAsc(E01z1::getE01z104);
        List<E01z1> e01z1List = e01z1Service.list(e01z1LambdaQueryWrapper);

        String []ids = e01z1Ids.split(",");
        if(!e01z1Ids.equals("zb")){
            e01z1List = e01z1List.stream().filter(x->{
                for(String Id:ids){
                    if(x.getId().equals(Id)||x.getE01z101().equals(Id)){
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
        }

        QueryWrapper<SysDictionary> sysDictionaryQueryWrapper = new QueryWrapper<>();
        sysDictionaryQueryWrapper.eq("dict_type", "ZB97A");
//        sysDictionaryQueryWrapper.eq("Eparent_code","0");
        sysDictionaryQueryWrapper.ne("dict_code", "-");
        List<Map<String,Object>> mapList = new ArrayList<>();
        final List<SysDictionary> dictlist = sysDictionaryService.list(sysDictionaryQueryWrapper);
        for (SysDictionary sd : dictlist) {
            if(sd.getDictCode().equals("0")){
                continue;
            }
            if(!sd.getEparentCode().equals("0")){
                continue;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", sd.getDictCode());
            map.put("e01z104", sd.getFullName().indexOf(".") > -1 ? sd.getFullName().substring(0, sd.getFullName().indexOf(".")) : "散");
            map.put("pinyin", sd.getPinyin());
            map.put("e01z111a", sd.getFullName().indexOf(".") > -1 ? sd.getFullName().substring(sd.getFullName().indexOf(".")+1, sd.getFullName().length()):sd.getFullName());
            map.put("remark", "catalog");
            //查找二级字典数据
            List<Map<String,Object>> children = new ArrayList<>();
            for (SysDictionary sd2 : dictlist) {
                if(sd2.getEparentCode().equals(sd.getDictCode())){
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("id", sd2.getDictCode());
                    map2.put("e01z104", sd2.getFullName().indexOf(".") > -1 ? sd2.getFullName().substring(0, sd2.getFullName().indexOf(".")) : "散");
                    map2.put("pinyin", sd2.getPinyin());
                    map2.put("e01z111a", sd2.getFullName().indexOf(".") > -1 ? sd2.getFullName().substring(sd2.getFullName().indexOf(".")+1, sd2.getFullName().length()):sd2.getFullName());
                    map2.put("remark", "catalog");
                    //查找二级字典的材料
                    List<Map<String,Object>> children2 = new ArrayList<>();
                    e01z1List.stream().forEach(e01z1->{
                        if(e01z1.getE01z101().equals(sd2.getDictCode())){
                            Map<String, Object> map3 = new HashMap<>();
                            map3.put("id",e01z1.getId());
                            map3.put("orgId",e01z1.getOrgId());
                            map3.put("a01Id",e01z1.getA01Id());
                            map3.put("e01z101",e01z1.getE01z101());
                            map3.put("e01z104",e01z1.getE01z104());
                            map3.put("e01z107",e01z1.getE01z107());
                            map3.put("e01z111a",e01z1.getE01z111a());
                            map3.put("e01z111b",e01z1.getE01z111b());
                            map3.put("e01z114",e01z1.getE01z114());
                            map3.put("e01z117",e01z1.getE01z117());
                            map3.put("e01z121",e01z1.getE01z121());
                            map3.put("e01z124",e01z1.getE01z124());
                            map3.put("e01z127",e01z1.getE01z127());
                            map3.put("e01z131",e01z1.getE01z131());
                            map3.put("type",3);
                            map3.put("year",e01z1.getE01z117Year());
                            map3.put("month",e01z1.getE01z117Month());
                            map3.put("day",e01z1.getE01z117Day());
                            map3.put("disabled",false);
                            children2.add(map3);
                        }
                    });
                    map2.put("children",children2);
                    children.add(map2);
                }
            }
            if(children.size()==0){
                //查找材料数据
                e01z1List.stream().forEach(e01z1->{
                    if(e01z1.getE01z101().equals(sd.getDictCode())){
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("id",e01z1.getId());
                        map2.put("orgId",e01z1.getOrgId());
                        map2.put("a01Id",e01z1.getA01Id());
                        map2.put("e01z101",e01z1.getE01z101());
                        map2.put("e01z104",e01z1.getE01z104());
                        map2.put("e01z107",e01z1.getE01z107());
                        map2.put("e01z111a",e01z1.getE01z111a());
                        map2.put("e01z111b",e01z1.getE01z111b());
                        map2.put("e01z114",e01z1.getE01z114());
                        map2.put("e01z117",e01z1.getE01z117());
                        map2.put("e01z121",e01z1.getE01z121());
                        map2.put("e01z124",e01z1.getE01z124());
                        map2.put("e01z127",e01z1.getE01z127());
                        map2.put("e01z131",e01z1.getE01z131());
                        map2.put("type",3);
                        map2.put("year",e01z1.getE01z117Year());
                        map2.put("month",e01z1.getE01z117Month());
                        map2.put("day",e01z1.getE01z117Day());
                        map2.put("disabled",false);
                        children.add(map2);
                    }
                });
                map.put("children",children);
            }else{
                map.put("children",children);
            }
            mapList.add(map);

        }

        return mapList;
    }

    /**
     * 查找查阅信息子级下某一分类下申请的材料
     *
     * @param e01zcRela 查阅信息子级
     * @param e01z1s    材料集合
     * @param a01Id     干部id
     * @param sd        材料分类
     * @return
     */
    public List<Map<String, Object>> getE01zcRelaE01z1s(E01zcRela e01zcRela, List<E01z1> e01z1s, String a01Id, SysDictionary sd) {
        List<Map<String, Object>> mapChildrens = new ArrayList<>();
        if (e01zcRela.getE01z1Id().equals("zb")) {
            e01z1s.stream().forEach(x -> {
                if (x.getE01z101().equals(sd.getDictCode())) {
                    Map<String, Object> mapChildren = new HashMap<>();
                    mapChildren.put("id", a01Id + "-" + x.getId());
                    mapChildren.put("label", x.getE01z111a());
                    mapChildren.put("check", "check");
                    mapChildren.put("data", x);
                    mapChildrens.add(mapChildren);
                }
            });
        } else if (e01zcRela.getE01z1Id().split(",")[0].length() > 30) {
            //单份材料
            String[] e01Ids = e01zcRela.getE01z1Id().split(",");

            e01z1s.stream().forEach(x -> {
                for (String e01id : e01Ids) {
                    if (x.getE01z101().equals(sd.getDictCode()) && x.getId().equals(e01id)) {
                        Map<String, Object> mapChildren = new HashMap<>();
                        mapChildren.put("id", a01Id + "-" + x.getId());
                        mapChildren.put("label", x.getE01z111a());
                        mapChildren.put("check", "check");
                        mapChildren.put("data", x);
                        mapChildrens.add(mapChildren);
                    }
                }

            });

        } else {
            String[] dicts = e01zcRela.getE01z1Id().split(",");
            //单类材料
            e01z1s.stream().forEach(x -> {
                for (String dict : dicts) {
                    if (x.getE01z101().equals(dict) && dict.equals(sd.getDictCode())) {
                        Map<String, Object> mapChildren = new HashMap<>();
                        mapChildren.put("id", a01Id + "-" + x.getId());
                        mapChildren.put("label", x.getE01z111a());
                        mapChildren.put("check", "check");
                        mapChildren.put("data", x);
                        mapChildrens.add(mapChildren);
                    }
                }

            });
        }
        return mapChildrens;
    }

    /**
     * 根据查阅信息子级 生成该子级的档案材料tree
     *
     * @param dictlist  档案分类
     * @param e01zcRela 信息子级
     * @param a01Id     干部id
     * @param e01z1s    材料列表
     * @return
     */
    public List<Map<String, Object>> getA01E01z1TreeByE01zcRela(List<SysDictionary> dictlist, E01zcRela e01zcRela, String a01Id, List<E01z1> e01z1s) {
        List<Map<String, Object>> maps = new ArrayList<>();
        for (SysDictionary sd : dictlist) {
            if (!sd.getEparentCode().equals("0")) {
                continue;
            }
            Map<String, Object> map = new HashMap<>();
            //第一级数据
            map.put("id", a01Id + "-" + sd.getDictCode());
            map.put("e01z104", sd.getFullName().indexOf(".") > -1 ? sd.getFullName().substring(0, sd.getFullName().indexOf(".")) : "散");
            map.put("remark", "catalog");
            map.put("data", sd);
            map.put("label", sd.getFullName());
            //查询第二级数据
            List<Map<String, Object>> mapChildrens = new ArrayList<>();
            for (SysDictionary sd2 : dictlist) {

                if (sd2.getEparentCode().equals(sd.getDictCode())) {
                    Map<String, Object> mapChildren = new HashMap<>();
                    //第二级数据
                    mapChildren.put("id", a01Id + "-" + sd2.getDictCode());
                    mapChildren.put("e01z104", sd2.getFullName().indexOf(".") > -1 ? sd.getFullName().substring(0, sd.getFullName().indexOf(".")) : "散");
                    mapChildren.put("remark", "catalog");
                    mapChildren.put("data", sd2);
                    mapChildren.put("label", sd2.getFullName());
                    List<Map<String, Object>> mapChildrens2 = new ArrayList<>();
                    //查询详细材料
                    mapChildrens2 = getE01zcRelaE01z1s(e01zcRela, e01z1s, a01Id, sd2);
                    if (mapChildrens2.size() > 0) {
                        mapChildren.put("children", mapChildrens2);
                    }
                    mapChildrens.add(mapChildren);


                    //如果子节点不存在并且该子节点不在申请中就把该节点设为不可点击
                    String e01z1Ids[] = e01zcRela.getE01z1Id().split(",");
                    boolean b = true;
                    //申请的为材料
                    if (e01z1Ids[0].length() > 30) {
                        b = true;
                    } else {
                        //申请的为十大类
                        for (String e01z1Id : e01z1Ids) {
                            //申请的为该类则设为可选择
                            if (e01z1Id.equals(sd2.getDictCode())) {
                                b = false;
                            }
                        }
                    }
                    if (mapChildrens2.size() == 0 && !e01zcRela.getE01z1Id().equals("zb") && b) {
                        mapChildren.put("disabled", "true");
                    }

                }
            }
            //查询材料
            if (mapChildrens.size() == 0) {
                //查找子级下分类下申请的材料
                mapChildrens = getE01zcRelaE01z1s(e01zcRela, e01z1s, a01Id, sd);
            }
            if (mapChildrens.size() > 0) {
                map.put("children", mapChildrens);
            }
            //如果子节点不存在并且该子节点不在申请中就把该节点设为不可点击
            String e01z1Ids[] = e01zcRela.getE01z1Id().split(",");
            boolean b = true;
            //申请的为材料
            if (e01z1Ids[0].length() > 30) {
                b = true;
            } else {
                //申请的为十大类
                for (String e01z1Id : e01z1Ids) {
                    if (e01z1Id.equals(sd.getDictCode())) {
                        b = false;
                    }
                }
            }


            if (mapChildrens.size() == 0 && !e01zcRela.getE01z1Id().equals("zb") && b) {
                map.put("disabled", "true");
            }
            maps.add(map);
        }
        return maps;
    }
}


