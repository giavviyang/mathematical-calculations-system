package com.fudian.project.ams.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.ams.pojo.E01zcRela;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.SysFile;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.SysDeptApi;
import com.fudian.api.system.service.SysFileApi;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.DateUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.factory.FileStorageFactory;
import com.fudian.filestorage.service.FileStorageService;
import com.fudian.project.ams.mapper.E01z1Mapper;
import com.fudian.project.ams.mapper.E01zcMapper;
import com.fudian.project.ams.mapper.E01zcRelaMapper;
import com.fudian.project.ams.pojo.E01zc;
import com.fudian.project.ams.service.E01zcLedgerService;
import com.fudian.project.ams.service.E01zcRelaService;
import com.fudian.project.ams.service.E01zcService;
import com.fudian.project.ams.utils.Map2Bean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class E01zcServiceImpl extends ServiceImpl<E01zcMapper, E01zc> implements E01zcService {
    @Autowired
    E01zcMapper e01zcMapper;
    @Autowired
    E01zcRelaMapper e01zcRelaMapper;
    @Autowired
    E01z1Mapper e01z1Mapper;
    @Autowired
    SysDeptApi sysDeptMapper;
    @Autowired
    E01zcRelaService e01zcRelaService;

    @Value("${fileStorage.type}")
    private String fileStorageType;

    @Autowired
    E01zcLedgerService e01zcLedgerService;

    @Autowired
    SysFileApi sysFileService;


    @Override
    public int updateBatch(List<E01zc> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<E01zc> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(E01zc record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(E01zc record) {
        return baseMapper.insertOrUpdateSelective(record);
    }


    @Override
    public String insertE01zc(String uuid, SysUser user) {
        E01zc e01zc = new E01zc();
        e01zc.setId(uuid);
        e01zc.setUserId(user.getUserId());
        e01zc.setUserName(user.getUserName());
        SysDept sysDept = sysDeptMapper.getById(user.getDeptId());
        e01zc.setOrgId(user.getOrgId());
        if (sysDept != null) {
            e01zc.setE01z824a(sysDept.getDeptName());
            e01zc.setE01z824b(sysDept.getId());
        }
        Date date = new Date();
        e01zc.setApprovTime(date);
        e01zc.setE01z824b(user.getDeptId());
        int i = e01zcMapper.insert(e01zc);
        return uuid;
    }

    @Override
    public List<E01zc> queryE01zcList(E01zc e01zc, String id, Integer status, Integer target) {
        LambdaQueryWrapper<E01zc> wrapper = new LambdaQueryWrapper<>();
        if(id!=null){
            wrapper.eq(E01zc::getUserId, id);
        }

        if(e01zc.getUserName()!=null&&!"".equals(e01zc.getUserName())){
            wrapper.like(E01zc::getUserName,e01zc.getUserName());
        }

        if(e01zc.getUserId()!=null&&!"".equals(e01zc.getUserId())){
            wrapper.eq(E01zc::getUserId,e01zc.getUserId());
        }

        if(e01zc.getBeginTime()!=null){
            wrapper.ge(E01zc::getApprovTime,e01zc.getBeginTime());
        }

        if(e01zc.getEndTime()!=null){
            //将日期加一  不然搜索不到
            wrapper.le(E01zc::getApprovTime, DateUtils.nextDate(e01zc.getEndTime(),1));
        }



        wrapper.eq(E01zc::getTarget,target);

        wrapper.eq(E01zc::getStatus, status);
        wrapper.orderByDesc(E01zc::getApprovTime);
        List<E01zc> e01zcList = e01zcMapper.selectList(wrapper);
//        List<String> e01zcIds = e01zcList.stream().map(E01zc::getId).collect(Collectors.toList());
//        LambdaQueryWrapper<E01zcRela> e01zcRelaLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        e01zcRelaLambdaQueryWrapper.in(E01zcRela::getE01zcId,e01zcIds);
//        List<E01zcRela> e01zcRelaList = e01zcRelaService.list(e01zcRelaLambdaQueryWrapper);
//        e01zcList.stream().forEach(x->{
//            List<E01zcRela> e01zcRelaList1 = e01zcRelaList.stream().filter(y->{
//                if(y.getE01zcId().equals(x.getId())){
//                    return true;
//                }
//                return false;
//
//            }).collect(Collectors.toList());
//            x.setRelaNum(e01zcRelaList1.size());
//        });
        return e01zcList;
    }

    @Override
    public List<E01zc> queryE01zcList(String id) {
        LambdaQueryWrapper<E01zc> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(E01zc::getUserId, id);
//        wrapper.eq(E01zc::getStatus, );
        wrapper.orderByDesc(E01zc::getApprovTime);
        List<E01zc> e01zcList = e01zcMapper.selectList(wrapper);
        return e01zcList;
    }

    @Override
    public List<E01zc> queryE01z8ListBytext(String text) {
        QueryWrapper<E01zc> wrapper = new QueryWrapper<>();
        Field[] fields = E01zc.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            wrapper.like(fields[i].getName(), text);
        }
        List<E01zc> e01zcList = e01zcMapper.selectList(wrapper);
//        List<E01zcRela> e01zcRelaList=e01zcRelaMapper.selectList(wrapper);


        return e01zcList;
    }

    @Override
    public List<E01zc> queryE01zcListInTime(String id) {
        QueryWrapper<E01zc> queryWrapper = new QueryWrapper<>();
        Date today = new Date();
        LocalDateTime time = today.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        queryWrapper.eq("a01_id", id);
        queryWrapper.gt("e01z805", time);
        List<E01zc> e01zcList = e01zcMapper.selectList(queryWrapper);
        return e01zcList;
    }

    @Override
    public List<E01z1> queryE01z1ListByCondition(String a01Id, String fileType, String text) {
        //fileType材料类号 对应ZB97b 如9-1，4-1
        QueryWrapper<E01z1> queryWrapper = new QueryWrapper<>();
        if (!"".equals(a01Id)) {
            queryWrapper.eq("a01_id", a01Id);

            e01z1Mapper.selectList(queryWrapper);
        }
        return null;
    }

    @Override
    public List<E01zc> queryE01zcList() {
        QueryWrapper wrapper = new QueryWrapper();
        List<E01zc> e01zcList = e01zcMapper.selectList(wrapper);
        return e01zcList;
    }

    @Override
    public int deleteE01zc(String id) {
        int res = e01zcMapper.deleteById(id);
        return res;
    }

    @Override
    public int updateE01zc(E01zc e01zc) {
        int res = e01zcMapper.updateById(e01zc);
        return res;
    }

    @Override
    public List<Map<String, Object>> queryE01zcAndE01zcRela(String deptName, String a0101, Integer pageNum, Integer pageSize) {
        //模糊查询
        QueryWrapper<E01zc> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<E01zcRela> erwrapper = new LambdaQueryWrapper<>();
        erwrapper.like(E01zcRela::getA0101, a0101);
        erwrapper.like(E01zcRela::getDeptName, deptName);
        List<E01zcRela> e01zcRelas = e01zcRelaMapper.selectList(erwrapper);
        List<String> ids = e01zcRelas.stream().map(e -> e.getE01zcId()).distinct().collect(Collectors.toList());
        if (ids.size() == 0) {
            return null;
        }
        wrapper.in("id", ids);
        PageHelper.startPage(pageNum, pageSize);
        List<E01zc> e01zcs = e01zcMapper.selectList(wrapper);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < e01zcs.size(); i++) {
            Map<String, Object> map = null;
            try {
                map = Map2Bean.bean2map(e01zcs.get(i));
                QueryWrapper<E01zcRela> wrapper1 = new QueryWrapper<>();
                wrapper1.eq("e01zc_id", e01zcs.get(i).getId());
                List<E01zcRela> e01zcRelaList = e01zcRelaMapper.selectList(wrapper1);
                map.put("children", e01zcRelaList);
                mapList.add(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mapList;
    }

    @Override
    public CommonGridResult queryElecByPage(E01zc e01zc) {
        QueryWrapper<E01zcRela> queryWrapper = new QueryWrapper<>();
        //查出所有未审批的数据
        queryWrapper.eq("flag", "1");
        queryWrapper.groupBy("e01zc_id");
        List<E01zcRela> e01zcRelaList = e01zcRelaMapper.selectList(queryWrapper);
        List<String> ids = e01zcRelaList.stream().map(e -> e.getE01zcId()).collect(Collectors.toList());

        PageHelper.startPage(e01zc.getPageNum(), e01zc.getPageSize());
        List<E01zc> e01zcList = e01zcMapper.selectBatchIds(ids);
        Page page = (Page) e01zcList;
        CommonGridResult commonGridResult = new CommonGridResult(e01zc.getPageNum(), e01zc.getPageSize(), page.getTotal(), e01zcList);
        return commonGridResult;
    }

    @Override
    public List<E01zc> queryE01zcList(List<String> ids) {
        List<E01zc> e01zcList = baseMapper.selectBatchIds(ids);
        return e01zcList;
    }

    /**
     * 审批一个申请
     * @param jsonData
     * @return
     * @throws ParseException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean approVal(String jsonData) throws ParseException {
        JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonData);
        String e01zcIds[] = jsonObject.getString("e01zcId").split(",");
        int approValType = jsonObject.getInteger("type");

        for(String e01zcId:e01zcIds){
//            List<E01zcRela> e01zcRelas = this.e01zcRelaMapper.selectList(new LambdaQueryWrapper<E01zcRela>().eq(E01zcRela::getE01zcId,e01zcId));
//            Map<String,E01zcRela> map = new HashMap<>();
//            for(E01zcRela e01zcRela:e01zcRelas){
//                map.put(e01zcRela.getId(),e01zcRela);
//            }
            if (approValType == 1) {
                //已通过时将通过内容写进数据库中
                JSONArray jsonArray = jsonObject.getJSONArray("agreeNodes");
                List<E01zcRela> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {

                    JSONObject agreeNode = jsonArray.getJSONObject(i);
//                    E01zcRela e01zcRela = map.get(agreeNode.getString("id"));
                    E01zcRela e01zcRela = new E01zcRela();
                    e01zcRela.setId(agreeNode.getString("id"));
                    if (agreeNode.getString("e01z1Id").equals("")) {
                        e01zcRela.setAdoptE01z1Id("无");
                    } else {
                        e01zcRela.setAdoptE01z1Id(agreeNode.getString("e01z1Id"));
                        e01zcRela.setAdoptE01z1Msg(agreeNode.getString("e01z1Name"));
                    }


                    list.add(e01zcRela);

                }
                if (list.size() > 0) {
                    this.e01zcRelaService.updateBatchById(list);
                }

            }
            E01zc e01zc = this.e01zcMapper.selectById(e01zcId);
            e01zc.setApprover(jsonObject.getString("approver"));
            e01zc.setApproverDate(new Date());
            e01zc.setApproverOpinion(jsonObject.getString("approverOpinion"));
            e01zc.setPersonCharge(jsonObject.getString("personCharge"));
            e01zc.setStatus(approValType);
            e01zcLedgerService.updateE01zcAndRela(e01zc);
            this.updateById(e01zc);
        }

        return true;
    }

    /**
     * 添加一个申请
     * @param e01zc
     * @param jsonData
     * @return
     */
    @Override
    public String addE01zc(E01zc e01zc, String jsonData) {

        e01zc.setId(StringUtils.uuid());
        e01zc.setApprovTime(new Date());
        e01zc.setStatus(0);
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        List<E01zcRela> e01zcRelas = new ArrayList<>();
        for(int i = 0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            E01zcRela e01zcRela = new E01zcRela();

            e01zcRela.setId(StringUtils.uuid());
            e01zcRela.setA0101(jsonObject.getString("a0101"));
            e01zcRela.setA01Id(jsonObject.getString("id"));
            e01zcRela.setDeptId(jsonObject.getString("deptId"));
            e01zcRela.setDuty(jsonObject.getString("duty"));
            e01zcRela.setE01z1Id(jsonObject.getString("e01z1_id"));
            e01zcRela.setE01z111a(jsonObject.getString("e01z1_name"));
            e01zcRela.setDeptName(jsonObject.getString("deptName"));
            e01zcRela.setE01zcId(e01zc.getId());
            e01zcRelas.add(e01zcRela);
        }
        e01zc.setRelaNum(e01zcRelas.size());
        this.e01zcRelaService.batchInsert(e01zcRelas);
        this.baseMapper.insert(e01zc);
        this.e01zcLedgerService.InsertE01zcAndRela(e01zc);
        return e01zc.getId();
    }

    @Override
    public List<E01zc> queryNoApproList() {
        LambdaQueryWrapper<E01zc> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(E01zc::getStatus,0);
        wrapper.eq(E01zc::getTarget,1);
        List<E01zc> list=e01zcMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<Map<String, Object>> summaryE01zc() {
        return baseMapper.summaryE01zc();
    }
}







