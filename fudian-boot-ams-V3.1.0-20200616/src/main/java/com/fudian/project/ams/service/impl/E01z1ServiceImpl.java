package com.fudian.project.ams.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.ams.service.E01z1Api;
import com.fudian.api.system.pojo.*;
import com.fudian.api.system.service.*;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.threadLocal.DataPermissionConfig;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.filestorage.controller.FileStorageController;
import com.fudian.filestorage.factory.ImgDESFactory;
import com.fudian.filestorage.service.ImgDESService;
import com.fudian.project.ams.mapper.E01z1Mapper;
import com.fudian.project.ams.mapper.E01z2Mapper;
import com.fudian.project.ams.mapper.WfTaskE01z1Mapper;
import com.fudian.project.ams.mapper.WfTaskMapper;
import com.fudian.project.ams.pojo.*;
import com.fudian.project.ams.service.*;
import com.fudian.project.ams.utils.ChineseNumToArabicNumUtil;
import com.fudian.project.ams.utils.excel.EasyExcelUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

//import com.fudian.project.ams.utils.ExcelUtil;

@Service
public class E01z1ServiceImpl extends ServiceImpl<E01z1Mapper, E01z1> implements E01z1Service, E01z1Api {
    @Autowired
    private E01z2Mapper e01z2Mapper;

    @Autowired
    private E01z2Service e01z2Service;

    @Autowired
    private E01z1Mapper e01z1Mapper;

    @Autowired
    private WfTaskMapper wfTaskMapper;



    @Autowired
    private TokenApi tokenService;

    @Autowired
    private SysDeptApi sysDeptMapper;

    @Autowired
    private SysUserApi sysUserService;

    @Autowired
    private SysFileApi sysFileService;

    @Autowired
    private WfTaskE01z1Mapper wfTaskE01z1Mapper;

    @Autowired
    private WfTaskService wfTaskService;

    @Autowired
    private SysDataPermissionApi sysDataPermissionService;

    @Autowired
    private WfTaskE01z1Service wfTaskE01z1Service;

    @Autowired
    private FileStorageController fileStorageController;

    @Autowired
    private SysDictionaryApi sysDictionaryMapper;

    @Autowired
    private SysDictionaryApi sysDictionaryService;

    @Autowired
    private SysRoleApi sysRoleService;

    @Value("${fileStorage.type}")
    private String fileStorageType;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addE01z1AndImgByApi(E01z1 e01z1, MultipartFile[] files) throws IOException {

        e01z1Mapper.insert(e01z1);
        int i = 1;
        for (MultipartFile file : files) {
            String fileId = "";
            if (ImgDESFactory.isDES()) {
                ImgDESService imgDESService = ImgDESFactory.getInstance();
                byte[] encrypt = imgDESService.encrypt(file.getBytes());
                System.out.println("上传图片已被加密");
                fileId = fileStorageController.fileUpload(encrypt);
            } else {
                fileId = fileStorageController.fileUpload(file.getBytes());
            }
//             = fileStorageController.fileUpload(file);
            SysFile sysFile = new SysFile();
            sysFile.setId(StringUtils.uuid());
            sysFile.setFileName(file.getOriginalFilename());

            String[] fileType = file.getOriginalFilename().split("\\.");
            sysFile.setFileType(fileType[fileType.length-1]);

            sysFile.setFileSource(fileId);
            sysFile.setBindingId(e01z1.getId());
            sysFile.setFileSize(Integer.parseInt(String.valueOf(file.getSize())));
            sysFile.setFileIndex(i);
            sysFileService.save(sysFile);
            i++;
        }
        return true;
    }

    @Override
    public long countByExample(E01z1Example example) {
        return baseMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(E01z1Example example) {
        return baseMapper.deleteByExample(example);
    }

    @Override
    public List<E01z1> selectByExample(E01z1Example example) {
        return baseMapper.selectByExample(example);
    }

    @Override
    public int updateByExampleSelective(E01z1 record, E01z1Example example) {
        return baseMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(E01z1 record, E01z1Example example) {
        return baseMapper.updateByExample(record, example);
    }

    @Override
    public int updateBatch(List<E01z1> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<E01z1> list) {
        for (E01z1 e01z1 : list) {
            baseMapper.insert(e01z1);
        }
        return list.size();
    }

    @Override
    public int insertOrUpdate(E01z1 record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(E01z1 record) {
        return baseMapper.insertOrUpdateSelective(record);
    }


    @Override
    public List<Map<String, Object>> queryTree(String a01Id, boolean b) {

        List<SysDictionary> dictlist = null;
        //查询十大类
        QueryWrapper<SysDictionary> sysDictionaryQueryWrapper = new QueryWrapper<>();
        sysDictionaryQueryWrapper.eq("dict_type", "ZB97B");
        sysDictionaryQueryWrapper.eq("Eparent_code", "0");
        sysDictionaryQueryWrapper.ne("dict_code", "-");

        //数据权限
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (loginUser != null && loginUser.getUser() != null && loginUser.getUser().getUserId() != null
                && !loginUser.getUser().getUserId().equals("admin") && DataPermissionConfig.getIsClassIfy()) {
            Map<String, List<String>> dataMap = sysDataPermissionService.getDataPermission(loginUser.getUser().getUserId());
            List<String> classifyIds = dataMap.get("classifyIds");

            if (classifyIds != null && classifyIds.size() > 0) {
                sysDictionaryQueryWrapper.lambda().in(SysDictionary::getDictCode, classifyIds);
            } else {
                return new ArrayList<>();
            }
        }

        dictlist = sysDictionaryMapper.list(sysDictionaryQueryWrapper);
        return this.getE01z1Tree(dictlist, a01Id, b);
    }


    @Override
    public List<E01z1> parsingExcel(MultipartFile port) {
//        List<Object> list=ExcelUtil.readMoreThan1000Row(port);

        return null;
    }

    @Override
    public boolean parseAndInsert(MultipartFile file, String a01Id) {
        List<List<String>> list = null;
        try {
            list = EasyExcelUtil.readExcelWithStringList(file.getInputStream(), ExcelTypeEnum.valueOf(file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length - 1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<E01z1> e01z1List = new ArrayList<>();
        if (list != null && list.size() > 0) {
            String e01z101 = ""; //类号
            for (int i = 0; i < list.size(); i++) {
                List<String> strings = list.get(i);
                //跳过干部档案目录标题和表头（前三行）及空的行
                if (i < 2 || strings.get(0) == null) {
                    continue;
                }
                //读取Excel文件中的档案材料，类号为false，材料为true
                if (!ChineseNumToArabicNumUtil.isNum(strings.get(0))) { //当为类号时
                    if (strings.get(0).contains("-")) { //第4第9小类
                        e01z101 = strings.get(0);
                    } else { //其他大类数字转汉字
                        e01z101 = String.valueOf(ChineseNumToArabicNumUtil.chineseNumToArabicNum(strings.get(0)));
                    }
                } else { //当为材料时
                    Map<String, Object> map = new HashMap<>();
                    int n = 1;
                    map.put("e01z101", e01z101);
                    map.put("e01z104", Integer.parseInt(strings.get(n)));
                    map.put("e01z111a", strings.get(n + 1));
//                    map.put("e01z117",(strings.get(n+2)!=null?strings.get(n+2):"")+(strings.get(n+3)!=null?strings.get(n+3):"")+
//                            (strings.get(n+4)!=null?strings.get(n+4):""));
                    map.put("e01z117Year", strings.get(n + 2) != null ? strings.get(n + 2) : "");
                    map.put("e01z117Month", strings.get(n + 3) != null ? strings.get(n + 3) : "");
                    map.put("e01z117Day", strings.get(n + 4) != null ? strings.get(n + 4) : "");
                    map.put("e01z114", Integer.parseInt(strings.get(n + 5)));
                    map.put("e01z121", strings.get(n + 6) != null ? strings.get(n + 6) : "");
                    map.put("a01Id", a01Id);
                    String json = JSON.toJSONString(map);//map转String
                    JSONObject jsonObject = JSON.parseObject(json);//String转json
                    E01z1 e01z1 = jsonObject.toJavaObject(E01z1.class);
                    e01z1List.add(e01z1);
                }
            }
        }
        e01z1Mapper.batchInsert(e01z1List);
        return true;
    }

    @Override
    public CommonResult getE01z1Grid(MultipartFile file, String a01Id) {
        if (file == null) {
            List<Map<String, Object>> returnList = queryTree(a01Id, false);
            returnList.stream().forEach(maps -> {
                if (!maps.get("id").equals("4") && !maps.get("id").equals("9") && maps.get("remark").equals("catalog")) {
                    maps.put("isAdd", true);
                } else if (maps.get("id").equals("4") || maps.get("id").equals("9")) {
                    maps.put("nButton", false);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) maps.get("children");
                    list.stream().forEach(childrenMap -> {
                        childrenMap.put("isAdd", true);
                    });
                }
            });
            return CommonResult.success("获取成功", returnList);
        } else {
            List<List<String>> list = null;
            try {
                String type = file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length - 1];
                list = EasyExcelUtil.readExcelWithStringList(file.getInputStream(), ExcelTypeEnum.valueOf(type.toUpperCase()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Map<String, Object>> mapList = queryTree(null, false);
            mapList.stream().forEach(maps -> {
                if (!maps.get("id").equals("4") && !maps.get("id").equals("9") && maps.get("remark").equals("catalog")) {
                    maps.put("isAdd", true);
                }
            });
            if (list != null && list.size() > 0) {
                String e01z101 = "";
//                String classify="";
                List<Map<String, Object>> childrenList = new ArrayList<>();
                try {
                    for (int i = 0; i < list.size(); i++) {
                        //跳过干部档案目录条目（第一行）及空的行
                        List<String> strings = list.get(i);
                        int n = 0;
                        if (i < 3 || strings.get(n) == null) {
                            continue;
                        }

                        //读取excel文件中的档案材料并增加至目录树中  后期待优化。
                        if (!ChineseNumToArabicNumUtil.isNum(strings.get(n))) {
                            if (childrenList.size() > 0) {
                                if (e01z101.contains("-")) {
                                    for (Map<String, Object> map : mapList) {
                                        if (map.get("id").equals(e01z101.split("-")[0])) {
                                            List<Map<String, Object>> listMap = (List<Map<String, Object>>) map.get("children");
                                            for (Map<String, Object> stringObjectMap : listMap) {
                                                if (stringObjectMap.get("id").equals(e01z101)) {
                                                    stringObjectMap.put("children", childrenList);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for (Map<String, Object> map : mapList) {
                                        if (e01z101.equals("10")) {
                                            if (map.get("id").equals("A")) {
                                                map.put("children", childrenList);
                                                map.put("leaf", true);
                                            }
                                        } else {
                                            if (map.get("id").equals(e01z101)) {
                                                map.put("children", childrenList);
                                                map.put("leaf", true);
                                            }
                                        }
                                    }
                                }
                                childrenList = new ArrayList<>();
                            }
                            if (strings.get(n).contains("-")) {
//                                e01z101 = classify + String.valueOf(ChineseNumToArabicNumUtil.chineseNumToArabicNum(strings.get(n).replaceAll("\\(", "").replaceAll("\\)", "")));
                                e01z101 = strings.get(n);
                            } else {
                                e01z101 = String.valueOf(ChineseNumToArabicNumUtil.chineseNumToArabicNum(strings.get(n)));
//                                classify = e01z101 + "-";
                            }
                        } else {
                            Map<String, Object> map = new HashMap<>();

                            map.put("e01z101", e01z101);
                            map.put("e01z104", Integer.parseInt(strings.get(n)));
                            map.put("e01z111a", strings.get(n + 1));
//                            map.put("e01z117", (strings.get(n + 2) != null ? strings.get(n + 2) : "") + (strings.get(n + 3) != null ? strings.get(n + 3) : "") +
//                                    (strings.get(n + 4) != null ? strings.get(n + 4) : ""));
                            map.put("e01z117Year", strings.get(n + 2) != null ? strings.get(n + 2) : "");
                            map.put("e01z117Month", strings.get(n + 3) != null ? strings.get(n + 3) : "");
                            map.put("e01z117Day", strings.get(n + 4) != null ? strings.get(n + 4) : "");
                            map.put("e01z114", strings.get(n + 5) != null ? strings.get(n + 5) : "");
                            map.put("e01z121", strings.get(n + 6) != null ? strings.get(n + 6) : "");
                            map.put("leaf", false);
                            //兼容材料树
                            map.put("label", strings.get(n + 1));
                            map.put("disabled", false);
                            map.put("nButton", true);
                            childrenList.add(map);
                        }

                    }
                    if (childrenList.size() > 0) {
                        if (e01z101.contains("-")) {
                            for (Map<String, Object> map : mapList) {
                                if (map.get("id").equals(e01z101.split("\\.")[0])) {
                                    List<Map<String, Object>> listMap = (List<Map<String, Object>>) map.get("children");
                                    for (Map<String, Object> stringObjectMap : listMap) {
                                        if (stringObjectMap.get("id").equals(e01z101)) {
                                            stringObjectMap.put("children", childrenList);
                                        }
                                    }
                                }
                            }
                        } else {
                            for (Map<String, Object> map : mapList) {

                                if (e01z101.equals("10")) {
                                    if (map.get("id").equals("A")) {
                                        map.put("children", childrenList);
                                        map.put("leaf", true);
                                    }
                                } else {
                                    if (map.get("id").equals(e01z101)) {
                                        map.put("children", childrenList);
                                        map.put("leaf", true);
                                    }
                                }
                            }
                        }
                        childrenList = new ArrayList<>();
                    }
                    return CommonResult.success("读取成功", mapList);
                } catch (Exception e) {
                    return CommonResult.error(500, "不支持此格式的目录");
                }
            }
            return CommonResult.error("没有读取到数据", mapList);
        }
    }


    @Override
    public List<E01z1> queryE01z1ListByA01AndE01z101(String a01Id, String e01z101) {
        LambdaQueryWrapper<E01z1> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(E01z1::getA01Id, a01Id);
        lambdaQueryWrapper.eq(E01z1::getE01z101, e01z101);
        lambdaQueryWrapper.orderByAsc(E01z1::getE01z104);
        return this.list(lambdaQueryWrapper);
    }


    @Override
    public CommonGridResult queryListByFlag(E01z1 e01z1, String a0101) {
        return null;
    }

    @Override
    public int queryMaxIndex(String a01Id, String type) {
        LambdaQueryWrapper<E01z1> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(E01z1::getA01Id, a01Id).eq(E01z1::getE01z101, type).orderByDesc(E01z1::getE01z104);
        List<E01z1> list = this.list(lambdaQueryWrapper);
        if (list != null && list.size() > 0) {
            if (list.get(0).getE01z104() == null) {
                return 1;
            }
            return list.get(0).getE01z104() + 1;
        }
        return 1;
    }

    @Override
    public List<E01z1> queryE01z1List(String userIds, String materialIds) {
        return null;
    }

    /**
     * @param dictlist 档案目录集合
     * @param a01Id    查阅对象id
     * @param b        材料树是否携带序号
     * @return
     */
    private List<Map<String, Object>> getE01z1Tree(List<SysDictionary> dictlist, String a01Id, boolean b) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysDictionary sd : dictlist) {
            Map<String, Object> map = new HashMap<>();
            //第一级数据
            map.put("id", sd.getDictCode());
            map.put("e01z104", sd.getFullName().indexOf(".") > -1 ? sd.getFullName().substring(0, sd.getFullName().indexOf(".")) : "散");
            map.put("pinyin", sd.getPinyin());
            map.put("e01z111a", b ? sd.getFullName() : sd.getFullName().substring(sd.getFullName().indexOf(".") + 1));
            map.put("remark", "catalog");
            map.put("disabled", false);
            if (b) {
                map.put("children", new ArrayList<>());

            }
            map.put("label", sd.getFullName());
            //当前一级在字典表中是否有二级
            LambdaQueryWrapper<SysDictionary> sysQueryWrapper = new LambdaQueryWrapper<>();
            sysQueryWrapper.eq(SysDictionary::getEparentCode, sd.getDictCode());
            sysQueryWrapper.eq(SysDictionary::getDictType, "ZB97A");
            sysQueryWrapper.ne(SysDictionary::getDictCode, "-");
            sysQueryWrapper.orderByAsc(SysDictionary::getDictCode);
            List<SysDictionary> sonSys = sysDictionaryMapper.list(sysQueryWrapper);

            if (sonSys.size() > 0) {
                map.put("leaf", true);
                List<Map<String, Object>> children = this.getSecondTree(sonSys, a01Id, b);
                map.put("children", children);
                list.add(map);
                continue;
            }

            //如果没有a01Id,则不查询该大类下的所有材料
            if (a01Id == null || "".equals(a01Id)) {
                map.put("leaf", true);
                list.add(map);
                continue;
            }
            LambdaQueryWrapper<E01z1> FWrapper = new LambdaQueryWrapper<>();
            FWrapper.eq(E01z1::getA01Id, a01Id);
            FWrapper.eq(E01z1::getE01z101, sd.getDictCode());
            FWrapper.and(query->query.isNull(E01z1::getEfileFlag).or().ne(E01z1::getEfileFlag, "1"));
            FWrapper.orderByAsc(E01z1::getE01z104);
           // FWrapper.isNull(E01z1::getEfileFlag).or().ne(E01z1::getEfileFlag, "1");
            List<E01z1> e01z1List = e01z1Mapper.selectList(FWrapper);
            if (e01z1List.size() > 0) {
                List<Map<String, Object>> e01z1ListMap = new ArrayList<>();
                for (E01z1 e01z1 : e01z1List) {
                    Map<String, Object> EveryMap = new HashMap<>();
                    if (b) {
                        EveryMap.put("label", e01z1.getE01z111a());
                    }
                    EveryMap.put("id", e01z1.getId());
                    EveryMap.put("orgId", e01z1.getOrgId());
                    EveryMap.put("a01Id", e01z1.getA01Id());
                    EveryMap.put("e01z101", e01z1.getE01z101());
                    EveryMap.put("e01z104", e01z1.getE01z104());
                    EveryMap.put("e01z107", e01z1.getE01z107());
                    EveryMap.put("e01z111a", e01z1.getE01z111a());
                    EveryMap.put("e01z111b", e01z1.getE01z111b());
                    EveryMap.put("e01z114", e01z1.getE01z114());
                    EveryMap.put("e01z117", e01z1.getE01z117());
                    EveryMap.put("e01z121", e01z1.getE01z121());
                    EveryMap.put("e01z124", e01z1.getE01z124());
                    EveryMap.put("e01z127", e01z1.getE01z127());
                    EveryMap.put("e01z131", e01z1.getE01z131());
                    EveryMap.put("label", e01z1.getE01z111a());
                    EveryMap.put("e01z117Year", e01z1.getE01z117Year());
                    EveryMap.put("e01z117Month", e01z1.getE01z117Month());
                    EveryMap.put("e01z117Day", e01z1.getE01z117Day());
                    //将时间格式转成年/月/日  分开
                    String date = e01z1.getE01z117();
                    if (date != null && !"".equals(date)) {
                        if (date.length() == 8) {
                            EveryMap.put("year", date.substring(0, 4));
                            EveryMap.put("month", date.substring(4, 6));
                            EveryMap.put("day", date.substring(6, 8));
                            EveryMap.put("disabled", false);
                        } else if (date.length() == 6) {
                            EveryMap.put("year", date.substring(0, 4));
                            EveryMap.put("month", date.substring(4, 6));
                            EveryMap.put("day", "");
                            EveryMap.put("disabled", false);
                        } else {
                            EveryMap.put("year", "");
                            EveryMap.put("month", "");
                            EveryMap.put("day", "");
                            EveryMap.put("disabled", false);
                        }
                    } else {
                        EveryMap.put("year", "");
                        EveryMap.put("month", "");
                        EveryMap.put("day", "");
                        EveryMap.put("disabled", false);
                    }
                    e01z1ListMap.add(EveryMap);
                }
//                map.put("expanded", true);
                map.put("leaf", false);
                map.put("children", e01z1ListMap);
                list.add(map);
            } else {
                map.put("leaf", true);
                list.add(map);
            }
        }
        return list;
    }

    private List<Map<String, Object>> getSecondTree(List<SysDictionary> sonSys, String a01Id, boolean b) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysDictionary sd : sonSys) {
            Map<String, Object> map = new HashMap<>();
            //第一级数据
            map.put("id", sd.getDictCode());
            map.put("e01z104", sd.getFullName().indexOf(".") > -1 ? sd.getFullName().substring(0, sd.getFullName().indexOf(".")) : "散");
            map.put("pinyin", sd.getPinyin());
            map.put("e01z111a", b ? sd.getFullName() : sd.getFullName().substring(sd.getFullName().indexOf(".") + 1));
            map.put("remark", "catalog");
            map.put("disabled", false);
            map.put("label", sd.getFullName());
            LambdaQueryWrapper<E01z1> FWrapper = new LambdaQueryWrapper<>();
            if (a01Id == null || "".equals(a01Id)) {
                map.put("leaf", true);
                list.add(map);
                continue;
            } else {
                FWrapper.eq(E01z1::getA01Id, a01Id);
                FWrapper.eq(E01z1::getE01z101, sd.getDictCode());
                FWrapper.and(query->query.isNull(E01z1::getEfileFlag).or().ne(E01z1::getEfileFlag, "1"));
                FWrapper.orderByAsc(E01z1::getE01z104);
                List<E01z1> e01z1List = e01z1Mapper.selectList(FWrapper);
                List<Map<String, Object>> e01z1ListMap = new ArrayList<>();
                for (E01z1 e01z1 : e01z1List) {
                    Map<String, Object> EveryMap = new HashMap<>();
                    // if (b) {
                        EveryMap.put("label", e01z1.getE01z111a());
                    // }
                    EveryMap.put("id", e01z1.getId());
                    EveryMap.put("orgId", e01z1.getOrgId());
                    EveryMap.put("a01Id", e01z1.getA01Id());
                    EveryMap.put("e01z101", e01z1.getE01z101());
                    EveryMap.put("e01z104", e01z1.getE01z104());
                    EveryMap.put("e01z107", e01z1.getE01z107());
                    EveryMap.put("e01z111a", e01z1.getE01z111a());
                    EveryMap.put("e01z111b", e01z1.getE01z111b());
                    EveryMap.put("e01z114", e01z1.getE01z114());
                    EveryMap.put("e01z117", e01z1.getE01z117());
                    EveryMap.put("e01z121", e01z1.getE01z121());
                    EveryMap.put("e01z124", e01z1.getE01z124());
                    EveryMap.put("e01z127", e01z1.getE01z127());
                    EveryMap.put("e01z131", e01z1.getE01z131());
                    EveryMap.put("e01z117Year", e01z1.getE01z117Year());
                    EveryMap.put("e01z117Month", e01z1.getE01z117Month());
                    EveryMap.put("e01z117Day", e01z1.getE01z117Day());
                    //将时间格式转成年/月/日  分开
                    String date = e01z1.getE01z117();
                    if (date != null && !"".equals(date)) {
                        if (date.length() == 8) {
                            EveryMap.put("year", date.substring(0, 4));
                            EveryMap.put("month", date.substring(4, 6));
                            EveryMap.put("day", date.substring(6, 8));
                            EveryMap.put("disabled", false);
                        } else if (date.length() == 6) {
                            EveryMap.put("year", date.substring(0, 4));
                            EveryMap.put("month", date.substring(4, 6));
                            EveryMap.put("day", "");
                            EveryMap.put("disabled", false);
                        } else {
                            EveryMap.put("year", "");
                            EveryMap.put("month", "");
                            EveryMap.put("day", "");
                            EveryMap.put("disabled", false);
                        }
                    } else {
                        EveryMap.put("year", "");
                        EveryMap.put("month", "");
                        EveryMap.put("day", "");
                        EveryMap.put("disabled", false);
                    }
                    e01z1ListMap.add(EveryMap);
                }
                map.put("leaf", false);
//                map.put("expanded",true);
                map.put("children", e01z1ListMap);
                list.add(map);
            }
        }
        return list;
    }


    @Override
    public CommonGridResult queryE01z1List(String e01z111a, String a0101, Integer pageNum, Integer pageSize) {
//        long begin = System.currentTimeMillis();
        Map map = new HashMap();
        if(!StringUtils.isEmpty(e01z111a)){
            map.put("e01z111a", "%"+e01z111a+"%");
        }
        if(!StringUtils.isEmpty(a0101)){
            map.put("a0101", "%"+a0101+"%");
        }
        if (null != pageNum && 0 != pageNum && null != pageSize && 0 != pageSize) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<JSONObject> list = new Page<>();
        List<Map<String,String>> mapList = e01z1Mapper.selectE01z1ListByName(map);
        Page maps = (Page) mapList;
        maps.getResult().stream().forEach(x->{

            String jsonStr = JSON.toJSONStringWithDateFormat(x,"yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            list.add(jsonObject);


        });



        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setRows(list);
        commonGridResult.setTotal(maps.getTotal());
        commonGridResult.setPageSize(pageSize);
        commonGridResult.setPageNum(pageNum);
//        long end = System.currentTimeMillis();
//        System.out.println("查询用时:"+(end-begin)+"ms");
        return commonGridResult;
    }

    @Override
    public CommonResult removoByIds(List<String> ids) {
        CommonResult result = null;
        //清空
        if (ids.size() == 1 && ids.get(0).equals("all")) {
            LambdaQueryWrapper<E01z1> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(E01z1::getEfileFlag, "1");
            int j = e01z1Mapper.delete(wrapper);
            if (j > 0) {
                result = new CommonResult(200, "清空成功", j);
            } else {
                result = new CommonResult(500, "清空失败", j);
            }
            return result;
        }

        //批量删除
        int i = e01z1Mapper.deleteBatchIds(ids);
        if (i > 0) {
            result = new CommonResult(200, "删除成功", i);
        } else {
            result = new CommonResult(500, "删除失败", i);
        }
        return result;
    }

    @Override
    @Transactional
    public CommonResult updateFlagByIds(List<String> ids) {
        CommonResult result = null;
        //沒有必要将所有的数据都查询出来
        /*List<E01z1> e01z1s = e01z1Mapper.selectBatchIds(ids);
        for (E01z1 e01z1 : e01z1s) {
            e01z1.setEfileFlag("0");
        }*/
        //
        E01z1 e01z1 = new E01z1();
        e01z1.setEfileFlag("0");
        E01z1Example example = new E01z1Example();
        E01z1Example.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        int i = e01z1Mapper.updateByExampleSelective(e01z1, example);
        //int i = e01z1Mapper.updateBatch(e01z1s);
        if (i > 0) {
            result = new CommonResult(200, "修改成功", i);
        } else {
            result = new CommonResult(500, "修改失败", i);
        }
        return result;
    }

    @Override
    public HSSFWorkbook makeE01z1sExcel(String a01Id) {
        LambdaQueryWrapper<E01z1> e01z1Lam = new LambdaQueryWrapper<>();
        e01z1Lam.eq(E01z1::getA01Id, a01Id);
        e01z1Lam.and(e -> e.isNull(E01z1::getEfileFlag).or().eq(E01z1::getEfileFlag, "0"));
        e01z1Lam.orderByAsc(E01z1::getE01z101);
        List<E01z1> e01z1List = this.list(e01z1Lam);
        List<E01z1> filterList = e01z1List.stream().filter(item -> item.getEfileFlag() == null || !"1".equals(item.getEfileFlag())).collect(Collectors.toList());
        ClassPathResource resource = new ClassPathResource("/static/template.xls");
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(resource.getInputStream());
            HSSFSheet sheet = workbook.getSheetAt(0);
            //需要插入数据的第一行
            int RowIndex = 5;
            //需要插入数据的第一列
            int ColumIndex = 1;
            //一次插入几行
            int InsertRowCount = 1;

            //获取源格式行
            HSSFRow mySourceStyleRow = sheet.getRow(4);
            HSSFCellStyle cellStyle = sheet.getRow(4).getCell(1).getCellStyle();
            Map<String, List<E01z1>> map = filterList.stream().collect(Collectors.groupingBy(E01z1::getE01z101));
            LambdaQueryWrapper<SysDictionary> sdLam = new LambdaQueryWrapper<>();
            sdLam.eq(SysDictionary::getDictType, "ZB97B").orderByAsc(SysDictionary::getDictCode).ne(SysDictionary::getDictCode, "-").ne(SysDictionary::getDictCode, "ZZZZ");
            List<SysDictionary> sysDictionaries = sysDictionaryService.list(sdLam);
            for (SysDictionary sysDictionary : sysDictionaries) {
                if ("4".equals(sysDictionary.getDictCode()) || "9".equals(sysDictionary.getDictCode())) {
                    RowIndex++;
                } else {
                    List<E01z1> e01z1s = map.get(sysDictionary.getDictCode());
                    if (e01z1s != null && e01z1s.size() > 0) {
                        List<E01z1> sortedList = e01z1s.stream().sorted((o1, o2) -> o1.getE01z104() - o2.getE01z104()).collect(Collectors.toList());
                        for (E01z1 e01z1 : sortedList) {
                            myInsertRow(sheet, RowIndex, InsertRowCount, mySourceStyleRow);
                            HSSFRow row = sheet.getRow(RowIndex - 1);
                            row.getCell(ColumIndex).setCellValue(e01z1.getE01z104() == null ? "" : e01z1.getE01z104().toString());
                            row.getCell(ColumIndex + 1).setCellValue(e01z1.getE01z111a() == null ? "" : e01z1.getE01z111a());
                            row.getCell(ColumIndex + 2).setCellValue(e01z1.getE01z117Year() == null ? "" : e01z1.getE01z117Year());
                            row.getCell(ColumIndex + 3).setCellValue(e01z1.getE01z117Month() == null ? "" : e01z1.getE01z117Month());
                            row.getCell(ColumIndex + 4).setCellValue(e01z1.getE01z117Day() == null ? "" : e01z1.getE01z117Day());
                            row.getCell(ColumIndex + 5).setCellValue(e01z1.getE01z114() == null ? "" : e01z1.getE01z114().toString());
                            row.getCell(ColumIndex + 6).setCellValue(e01z1.getE01z121() == null ? "" : e01z1.getE01z121());
                            RowIndex += 1;
                        }
                        for (int m = 0; m < 1; m++) {
                            myInsertRow(sheet, RowIndex, InsertRowCount, mySourceStyleRow);
                            RowIndex += 1;
                        }
                        RowIndex += 2;
                    } else {
                        for (int m = 0; m < 1; m++) {
                            myInsertRow(sheet, RowIndex, InsertRowCount, mySourceStyleRow);
                            RowIndex += 1;
                        }
                        RowIndex += 2;
                    }

                }
            }
            sheet.setActive(true);
            return workbook;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    @Override
    public CommonResult insertOneArchiveData(E01z1 e01z1) {
        String e01z1Id = e01z1.getId();
        if (e01z1.getE01z117Day() != null) {
            if (e01z1.getE01z117Day().length() == 1) {
                e01z1.setE01z117Day("0" + e01z1.getE01z117Day());
            }
        }
        if (e01z1.getE01z117Month() != null) {
            if (e01z1.getE01z117Month().length() == 1) {
                e01z1.setE01z117Month("0" + e01z1.getE01z117Month());
            }
        }
        if (e01z1Id == null || "".equals(e01z1Id)) {
            //新增数据重新排序
            baseMapper.updateSortByClassIfyAndOrderNum(e01z1);
            e01z1Id = StringUtils.uuid();
            e01z1.setId(e01z1Id);
            this.save(e01z1);
        } else{
            this.updateById(e01z1);
        }

        return CommonResult.success("保存成功", e01z1Id);
    }

    @Override
    public CommonResult moveClassify(E01z1 e01z1) {
        LambdaQueryWrapper<E01z1> e01z1Lam = new LambdaQueryWrapper<>();
        e01z1Lam.eq(E01z1::getId, e01z1.getId());
        E01z1 oldE01z1 = this.getOne(e01z1Lam);
        oldE01z1.setE01z101(e01z1.getE01z101());
        String i = baseMapper.findMaxNum(oldE01z1) == null ? "1" : String.valueOf(Integer.parseInt(baseMapper.findMaxNum(oldE01z1)) + 1);
        oldE01z1.setE01z104(Integer.parseInt(i));
        int n = this.insertOrUpdate(oldE01z1);
        if (n > 0) {
            return CommonResult.success("保存成功");
        } else {
            return CommonResult.error("保存失败");
        }
    }

    @Override
    public E01z1 selectById(String id) {
        return e01z1Mapper.selectById(id);
    }

    //参数说明
    //第一个:指定操作的Sheet。
    //第二个:指定在第几行指入（插入行的位置）
    //第三个:指定要插入多少行
    //第四个:源单元格格式的行
    private void myInsertRow(HSSFSheet sheet, int InsertRowIndex, int InsertRowCount, HSSFRow mySourceStyleRow) {
        sheet.shiftRows(
                InsertRowIndex,                                //--开始行
                sheet.getLastRowNum(),                        //--结束行
                InsertRowCount,                                //--移动大小(行数)--往下移动
                true,                                        //是否复制行高
                false//,                               		//是否重置行高
                //true                                 		//是否移动批注
        );

        for (int i = InsertRowIndex; i < InsertRowIndex + InsertRowCount - 1; i++) {
            HSSFRow targetRow = null;
            HSSFCell sourceCell = null;
            HSSFCell targetCell = null;

            targetRow = sheet.createRow(i + 1);

            for (int m = mySourceStyleRow.getFirstCellNum(); m < mySourceStyleRow.getLastCellNum(); m++) {
                sourceCell = mySourceStyleRow.getCell(m);
                if (sourceCell == null)
                    continue;
                targetCell = targetRow.createCell(m);

                //targetCell..Encoding = sourceCell.Encoding;
                targetCell.setCellStyle(sourceCell.getCellStyle());
                targetCell.setCellType(sourceCell.getCellType());
            }
            //CopyRow(sourceRow, targetRow);
            //Util.CopyRow(sheet, sourceRow, targetRow);
        }

        HSSFRow firstTargetRow = sheet.getRow(InsertRowIndex);
        HSSFCell firstSourceCell = null;
        HSSFCell firstTargetCell = null;

        for (int m = mySourceStyleRow.getFirstCellNum(); m < mySourceStyleRow.getLastCellNum(); m++) {
            firstSourceCell = mySourceStyleRow.getCell(m);
            if (firstSourceCell == null)
                continue;
            firstTargetCell = firstTargetRow.createCell(m);

            //firstTargetCell.Encoding = firstSourceCell.Encoding;
            firstTargetCell.setCellStyle(firstSourceCell.getCellStyle());
            firstTargetCell.setCellType(firstSourceCell.getCellType());
        }
    }

    @Override
    public List<A01> selectUserByE01ziIds(String[] ids) {
        return baseMapper.selectUserByE01ziIds(ids);
    }
}
