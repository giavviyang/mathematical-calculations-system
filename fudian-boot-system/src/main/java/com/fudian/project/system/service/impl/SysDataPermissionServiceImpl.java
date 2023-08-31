package com.fudian.project.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.ams.service.E01z1Api;
import com.fudian.api.ams.service.E01zcRelaApi;
import com.fudian.api.system.pojo.*;
import com.fudian.api.system.service.SysDataPermissionApi;
import com.fudian.api.system.service.TokenApi;
import com.fudian.business.pojo.StudentAnswer;
import com.fudian.business.service.StudentAnswerService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.project.system.cache.DataPermissionCache;
import com.fudian.project.system.mapper.*;
import com.fudian.project.system.pojo.SysPermissionData;
import com.fudian.project.system.pojo.SysRoleData;
import com.fudian.project.system.pojo.SysUserRole;
import com.fudian.project.system.security.service.PermissionService;
import com.fudian.project.system.service.SysDataPermissionService;
import com.fudian.project.system.service.SysDeptService;
import com.fudian.project.system.service.SysRoleDataService;
import com.fudian.project.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysDataPermissionServiceImpl extends ServiceImpl<SysDataPermissionMapper, SysDataPermission> implements SysDataPermissionService, SysDataPermissionApi {


    @Autowired
    private SysDataPermissionMapper sysDataPermissionMapper;
    @Autowired
    private SysPermissionDataServiceImpl sysPermissionDataService;


    @Autowired
    private SysRoleDataService sysRoleDataService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SysPermissionDataMapper sysPermissionDataMapper;

    @Autowired
    private SysFileMapper sysFileMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    @Autowired
    private E01z1Api e01z1Mapper;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private E01zcRelaApi e01zcRelaService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private DataPermissionCache dataPermissionCacheService;

    @Autowired
    private StudentAnswerService studentAnswerService;


    @Override
    public int updateBatch(List<SysDataPermission> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SysDataPermission> list) {
        this.saveBatch(list);
        return list.size();
    }

    @Override
    public int insertOrUpdate(SysDataPermission record) {
        SysDataPermission sysDataPermission = this.getById(record.getDataPermissionsId());
        if (sysDataPermission == null) {
            this.save(record);
        } else {
            this.updateById(record);
        }
        return 1;
    }

    @Override
    public int insertOrUpdateSelective(SysDataPermission record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonResult<SysDataPermission> insertDataPermissions(List<SysDataPermission> dataPermissions) {

        for (SysDataPermission sysDataPermission : dataPermissions) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            sysDataPermission.setDataPermissionsId(uuid);
        }
        int i = sysDataPermissionMapper.batchInsert(dataPermissions);
        if (i > 0) {
            CommonResult<SysDataPermission> commonResult = new CommonResult<>(200, "新增数据权限成功", null);
            return commonResult;
        } else {
            CommonResult<SysDataPermission> commonResult = new CommonResult<>(200, "新增数据权限失败，请联系管理员", null);
            return commonResult;
        }
    }


    @Override
    public CommonResult deleteDataPermissionsById(List<String> dataPermissionsIds) {

        int i = sysDataPermissionMapper.deleteBatchIds(dataPermissionsIds);
        if (i > 0) {
            CommonResult commonResult = new CommonResult<>(200, "删除数据成功", i);
            return commonResult;
        } else {
            CommonResult commonResult = new CommonResult<>(500, "删除数据失败，请联系管理员", null);
            return commonResult;
        }
    }

    @Override
    public List<SysDataPermission> querySysDataPermissionList(List<String> sysDataPermissionIdList) {
        QueryWrapper<SysDataPermission> sysDataPermissionQueryWrapper = new QueryWrapper<>();
        sysDataPermissionQueryWrapper.lambda().in(SysDataPermission::getDataPermissionsId, sysDataPermissionIdList);
        List<SysDataPermission> sysDataPermissionList = sysDataPermissionMapper.selectList(sysDataPermissionQueryWrapper);
        return sysDataPermissionList;
    }

    @Override
    @Transactional
    public CommonResult insertDataPermissionsByPermissionId(String permissionId, String jsonData) {

        List<SysDataPermission> dataPermissions = new ArrayList<>();

        JSONObject jsonObject = JSON.parseObject(jsonData);
        JSONArray globalOrgIds = jsonObject.getJSONArray("globalOrgIds");
        if (globalOrgIds != null && globalOrgIds.size() > 0) {
            for (int i = 0; i < globalOrgIds.size(); i++) {
                SysDataPermission sysDataPermission = new SysDataPermission();
                sysDataPermission.setDataPermissionsName("部门权限");
                sysDataPermission.setDataId(globalOrgIds.getString(i));
                sysDataPermission.setDataPermissionsType("1");
                sysDataPermission.setDataPermissionsIdentity("1");
                dataPermissions.add(sysDataPermission);
            }
        }

        JSONArray userIds = jsonObject.getJSONArray("userIds");
        if (userIds != null && userIds.size() > 0) {
            for (int i = 0; i < userIds.size(); i++) {
                SysDataPermission sysDataPermission = new SysDataPermission();
                sysDataPermission.setDataPermissionsName("人员权限");
                sysDataPermission.setDataId(userIds.getString(i));
                sysDataPermission.setDataPermissionsType("2");
                sysDataPermission.setDataPermissionsIdentity("1");
                dataPermissions.add(sysDataPermission);

                JSONArray materialIds = jsonObject.getJSONArray("materialIds");
                for (int j = 0; j < materialIds.size(); j++) {
                    SysDataPermission sysDataPermission1 = new SysDataPermission();
                    sysDataPermission1.setDataPermissionsName("分类权限");
                    sysDataPermission1.setDataId(materialIds.getString(j));
                    sysDataPermission1.setDataPermissionsType("3");
                    sysDataPermission1.setDataPermissionsIdentity("1");
                    sysDataPermission1.setDataPermissionSummary(userIds.getString(i));
                    dataPermissions.add(sysDataPermission1);
                }
            }
        }

        JSONArray materialNameIds = jsonObject.getJSONArray("materialNameIds");
        if (materialNameIds != null && materialNameIds.size() > 0) {
            for (int i = 0; i < materialNameIds.size(); i++) {
                SysDataPermission sysDataPermission = new SysDataPermission();
                sysDataPermission.setDataPermissionsName("条目权限");
                sysDataPermission.setDataId(materialNameIds.getString(i));
                sysDataPermission.setDataPermissionsType("4");
                sysDataPermission.setDataPermissionsIdentity("1");
                dataPermissions.add(sysDataPermission);
            }
        }

        JSONArray sysFileData = jsonObject.getJSONArray("sysFile");
        if (sysFileData != null && sysFileData.size() > 0) {
            for (int i = 0; i < sysFileData.size(); i++) {
                SysDataPermission sysDataPermission = new SysDataPermission();
                sysDataPermission.setDataPermissionsName("电子文件权限");
                sysDataPermission.setDataId(sysFileData.getJSONObject(i).getString("dataId"));
                sysDataPermission.setDataPermissionsType("5");
                sysDataPermission.setDataPermissionsIdentity("1");
                sysDataPermission.setDataPermissionSummary(sysFileData.getJSONObject(i).getString("data_permission_summary"));
                dataPermissions.add(sysDataPermission);
            }
        }

        if (permissionId == null || "".equals(permissionId)) {
            CommonResult commonResult = new CommonResult();
            commonResult.setCode(500);

            return commonResult;
        }

        //删除原先的记录
        QueryWrapper<SysPermissionData> queryWrapper = new QueryWrapper();
        queryWrapper.eq("permission_id", permissionId);
        List<SysPermissionData> sysPermissionDataList = sysPermissionDataService.list(queryWrapper);

        if (sysPermissionDataList != null && sysPermissionDataList.size() > 0) {
            List<String> dataPermissionIdList = new ArrayList<>();
            for (SysPermissionData sysPermissionData : sysPermissionDataList) {
                dataPermissionIdList.add(sysPermissionData.getDataPermissionId());
            }

            QueryWrapper deleteWrapper1 = new QueryWrapper();
            deleteWrapper1.in("data_permissions_id", dataPermissionIdList);
            sysDataPermissionMapper.delete(deleteWrapper1);

            QueryWrapper deleteWrapper2 = new QueryWrapper();
            deleteWrapper2.eq("permission_id", permissionId);
            sysPermissionDataService.remove(deleteWrapper2);
        }

        for (SysDataPermission dataPermission : dataPermissions) {
            dataPermission.setDataPermissionsId(UUID.randomUUID().toString().replaceAll("-", ""));
            dataPermission.setCreateTime(new Date());
            dataPermission.setCreateBy("admin");
        }
        this.batchInsert(dataPermissions);
        List<SysPermissionData> list = new ArrayList<>();
        for (SysDataPermission dataPermission : dataPermissions) {
            SysPermissionData data = new SysPermissionData();
            data.setPermissionId(permissionId);
            data.setDataPermissionId(dataPermission.getDataPermissionsId());
            list.add(data);
        }
        sysPermissionDataService.batchInsert(list);
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(200);

        return commonResult;
    }

    @Override
    public CommonResult selectDataPermissionsByPermissionId(String permissionId) {

        //查询权限id对应的数据权限
        QueryWrapper<SysPermissionData> queryWrapper = new QueryWrapper();
        queryWrapper.eq("permission_id", permissionId);
        List<SysPermissionData> list = sysPermissionDataService.list(queryWrapper);
        List<String> ids = new ArrayList<>();
        for (SysPermissionData sysPermissionData : list) {
            ids.add(sysPermissionData.getDataPermissionId());
        }
        //根据数据权限id查询对应的数据
        QueryWrapper<SysDataPermission> wrapper = new QueryWrapper();
        wrapper.in("data_permissions_id", ids.toArray());
        List<SysDataPermission> result = sysDataPermissionMapper.selectList(wrapper);

        CommonResult commonResult = new CommonResult();
        commonResult.setCode(200);
        commonResult.setData(result);

        return commonResult;
    }

    @Autowired
    TokenApi tokenService;


    /**
     * 查询具有材料下电子文件权限的数量
     *
     * @param e01z1id
     * @return
     */
    @Override
    public List<SysFile> getSysFile(String e01z1id) {

        //获取当前登录的用户名
        /*LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String loginCode = loginUser.getUser().getUserId();*/
        String loginCode = "8e7f3d6f442b4bf08deea981fc22505b";

        //查询当前用户的所有权限
        List<String> permissionList = permissionService.getAllPermission(loginCode);

        //查询当前材料下的所有电子文件
        QueryWrapper<SysFile> sysFileWrapper = new QueryWrapper<>();
        sysFileWrapper.eq("binding_id", e01z1id);
        List<SysFile> sysFileList = sysFileMapper.selectList(sysFileWrapper);

        //判断是否有查借阅权限
        if (!e01zcRelaService.judgeAthor(e01z1id)) {
            //查询当前用户的所有电子文件权限
            QueryWrapper<SysPermissionData> permissionDataWrapper = new QueryWrapper();
            permissionDataWrapper.in("permission_id", permissionList.toArray());
            List<SysPermissionData> sysPermissionDataList = sysPermissionDataMapper.selectList(permissionDataWrapper);
            QueryWrapper<SysDataPermission> dataPermissionWrapper = new QueryWrapper();
            List<String> dataPermissionIdList = sysPermissionDataList.stream().map(e -> e.getDataPermissionId()).collect(Collectors.toList());
            dataPermissionWrapper.in("data_permissions_id", dataPermissionIdList.toArray());
            dataPermissionWrapper.eq("data_permissions_type", "5");
            List<SysDataPermission> sysDataPermissionList = sysDataPermissionMapper.selectList(dataPermissionWrapper);
            List<String> sysFilePermissionList = sysDataPermissionList.stream().map(e -> e.getDataId()).collect(Collectors.toList());

            if (sysFilePermissionList == null || sysFileList == null) {
                return new ArrayList<>();
            }

            List<SysFile> result = new ArrayList<>();

            //取出所有具有权限的电子文件
            for (SysFile sysFile : sysFileList) {
                if (sysFilePermissionList.contains(sysFile.getId())) {
                    result.add(sysFile);
                }
            }

            return result;
        }
        return sysFileList;
    }

    /**
     * 获取具有权限的部门
     *
     * @return
     */
    @Override
    public JSONArray getDept() {
        //获取当前登录的用户名
        /*LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String loginCode = loginUser.getUser().getUserId();*/

        //查询当前用户的所有权限
        /*List<String> permissionList = permissionService.getAllPermission(loginCode);

        //查询当前用户的所有部门权限
        QueryWrapper<SysPermissionData> permissionDataWrapper = new QueryWrapper();
        permissionDataWrapper.in("permission_id", permissionList.toArray());
        List<SysPermissionData> sysPermissionDataList = sysPermissionDataMapper.selectList(permissionDataWrapper);
        QueryWrapper<SysDataPermission> dataPermissionWrapper = new QueryWrapper();
        List<String> dataPermissionIdList = sysPermissionDataList.stream().map(e -> e.getDataPermissionId()).collect(Collectors.toList());
        dataPermissionWrapper.in("data_permissions_id", dataPermissionIdList.toArray());
        dataPermissionWrapper.eq("data_permissions_type", "1");
        List<SysDataPermission> sysDataPermissionList = sysDataPermissionMapper.selectList(dataPermissionWrapper);
        List<String> deptList = sysDataPermissionList.stream().map(e -> e.getDataId()).collect(Collectors.toList());

        if (deptList == null) {
            return new JSONArray();
        }*/

        //根据权限id查询所有的部门
        QueryWrapper<SysDept> deptWrapper = new QueryWrapper<>();
        //deptWrapper.in("id", deptList.toArray());
        List<SysDept> sysDepts = sysDeptMapper.selectList(deptWrapper);

        JSONArray jsonArray = sysDeptService.getDeptTreeGrid(sysDepts, "000");

        return jsonArray;
    }


    /**
     * 获取具有权限的材料分类
     *
     * @param a01Id
     * @return
     */
    @Override
    public JSONArray getType(String a01Id) {


        //获取当前登录的用户名
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String loginCode = loginUser.getUser().getUserId();

        //查询当前用户的所有权限
        List<String> permissionList = permissionService.getAllPermission(loginCode);

        //查询所有分类
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("dict_type", "ZB97A");
        List<SysDictionary> list = sysDictionaryMapper.selectList(wrapper);


        //查询当前用户的所有分类权限
        QueryWrapper<SysPermissionData> permissionDataWrapper = new QueryWrapper();
        permissionDataWrapper.in("permission_id", permissionList.toArray());
        List<SysPermissionData> sysPermissionDataList = sysPermissionDataMapper.selectList(permissionDataWrapper);
        QueryWrapper<SysDataPermission> dataPermissionWrapper = new QueryWrapper();
        List<String> dataPermissionIdList = sysPermissionDataList.stream().map(e -> e.getDataPermissionId()).collect(Collectors.toList());
        dataPermissionWrapper.in("data_permissions_id", dataPermissionIdList.toArray());
        dataPermissionWrapper.eq("data_permissions_type", "3");
        dataPermissionWrapper.eq("data_permission_summary", a01Id);
        List<SysDataPermission> sysDataPermissionList = sysDataPermissionMapper.selectList(dataPermissionWrapper);
        List<String> typePermissionList = sysDataPermissionList.stream().map(e -> e.getDataId()).collect(Collectors.toList());

        //查询所有查借阅的材料
        List<E01z1> e01z1_2 = e01zcRelaService.getE01zcRelaByE01z1Id(a01Id);
        if (e01z1_2 != null && e01z1_2.size() > 0) {
            Set<String> dictCodeSet = new HashSet<>();
            for (E01z1 z1 : e01z1_2) {
                dictCodeSet.add(z1.getE01z101());
            }
            for (String s : dictCodeSet) {
                if (!typePermissionList.contains(s)) {
                    typePermissionList.add(s);
                }
            }
        }

        if (typePermissionList == null || list == null) {
            return new JSONArray();
        }

        List<SysDictionary> deptList = new ArrayList<>();
        for (SysDictionary sysDictionary : list) {
            if (typePermissionList.contains(sysDictionary.getDictCode())) {
                deptList.add(sysDictionary);
                continue;
            }
            for (String s : typePermissionList) {
                if (s.startsWith(sysDictionary.getDictCode())) {
                    deptList.add(sysDictionary);
                    break;
                }
            }
        }

        List<SysDictionary> childrenList = new ArrayList<>();
        for (int i = 0; i < deptList.size(); i++) {
            if (deptList.get(i).getDictCode().equals("-")) {
                deptList.remove(i);
            }
            if (!deptList.get(i).getEparentCode().equals("0")) {
                childrenList.add(deptList.get(i));
            }
        }
        JSONArray jsonArray = new JSONArray();
        for (SysDictionary dictionary : deptList) {
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

        //查询所有拥有材料的id
        List<E01z1> e01z1 = getE01z1(a01Id);
        if (e01z1_2 != null) {
            e01z1.addAll(e01z1_2);
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String dictCode = (String) jsonObject.get("dictCode");
            JSONArray jsonArray1 = jsonObject.getJSONArray("children");

            if (jsonArray1 == null | jsonArray1.size() <= 0) {
                jsonArray1 = new JSONArray();
                for (E01z1 z1 : e01z1) {
                    if (dictCode != null && dictCode.equals(z1.getE01z101())) {
                        JSONObject jsonObject1 = JSON.parseObject(JSON.toJSONString(z1));
                        jsonObject1.put("fullName", z1.getE01z111a());
                        jsonArray1.add(jsonObject1);
                    }
                }
                jsonObject.put("children", jsonArray1);
            } else {
                for (int j = 0; j < jsonArray1.size(); j++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    String dictCode2 = (String) jsonObject2.get("dictCode");
                    JSONArray jsonArray2 = jsonObject2.getJSONArray("children");
                    if (jsonArray1 == null | jsonArray1.size() <= 0) {
                        jsonArray2 = new JSONArray();
                        for (E01z1 z2 : e01z1) {
                            if (dictCode2 != null && dictCode2.equals(z2.getE01z101())) {
                                JSONObject jsonObject3 = JSON.parseObject(JSON.toJSONString(z2));
                                jsonObject3.put("fullName", z2.getE01z111a());
                                jsonArray2.add(jsonObject3);
                                jsonObject2.put("children", jsonArray2);
                            }
                        }
                    }
                }
            }
        }

        return jsonArray;
    }

    @Override
    public List<E01z1> getE01z1(String a01Id) {
        //获取当前登录的用户名
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String loginCode = loginUser.getUser().getUserId();

        //查询当前用户的所有权限
        List<String> permissionList = permissionService.getAllPermission(loginCode);

        //查询当前用户下的所有材料
        QueryWrapper<E01z1> sysFileWrapper = new QueryWrapper<>();
        sysFileWrapper.eq("a01_id", a01Id);
        List<E01z1> e01z1List = e01z1Mapper.list(sysFileWrapper);

        //查询当前用户的所有材料权限
        QueryWrapper<SysPermissionData> permissionDataWrapper = new QueryWrapper();
        permissionDataWrapper.in("permission_id", permissionList.toArray());
        List<SysPermissionData> sysPermissionDataList = sysPermissionDataMapper.selectList(permissionDataWrapper);
        QueryWrapper<SysDataPermission> dataPermissionWrapper = new QueryWrapper();
        List<String> dataPermissionIdList = sysPermissionDataList.stream().map(e -> e.getDataPermissionId()).collect(Collectors.toList());
        dataPermissionWrapper.in("data_permissions_id", dataPermissionIdList.toArray());
        dataPermissionWrapper.eq("data_permissions_type", "4");
        List<SysDataPermission> sysDataPermissionList = sysDataPermissionMapper.selectList(dataPermissionWrapper);
        List<String> sysFilePermissionList = sysDataPermissionList.stream().map(e -> e.getDataId()).collect(Collectors.toList());

        List<E01z1> result = new ArrayList<>();

        for (E01z1 e01z1 : e01z1List) {
            if (sysFilePermissionList.contains(e01z1.getId())) {
                result.add(e01z1);
            }
        }

        return result;
    }


    @Override
    /*public Map<String,List<String>> getDataPermission(String userId){
        Map<String,List<SysDataPermission>> dataPermissionMap=dataPermissionCacheService.getPermissions();
        LambdaQueryWrapper<SysUserRole> userRoleLam = new LambdaQueryWrapper<>();
        userRoleLam.eq(SysUserRole::getUserId,userId);
        List<SysUserRole> sysUserRoles=sysUserRoleService.list(userRoleLam);
        Map<String,List<String>> returnMap = new HashMap<>();
        if(sysUserRoles!=null){
            List<String> a01Ids = new ArrayList<>();//无法查看的干部id集合
            List<String> deptIds = new ArrayList<>();//可查看部门id集合
            List<String> classifyIds = new ArrayList<>();//可查看的材料分类类号
            List<String> otherPermission = new ArrayList<>();//其他数据权限
            for(SysUserRole sysUserRole:sysUserRoles){
                List<SysDataPermission> sysDataPermissions = dataPermissionMap.get(sysUserRole.getRoleId());
                if(sysDataPermissions!=null) {
                    for (SysDataPermission sysDataPermission : sysDataPermissions) {
                        if ("1".equals(sysDataPermission.getDataPermissionsType())) {
                            deptIds.add(sysDataPermission.getDataId());
                        } else if ("2".equals(sysDataPermission.getDataPermissionsType())) {
                            a01Ids.add(sysDataPermission.getDataId());
                        } else if ("3".equals(sysDataPermission.getDataPermissionsType())) {
                            classifyIds.add(sysDataPermission.getDataId());
                        } else if ("6".equals(sysDataPermission.getDataPermissionsType())) {
                            otherPermission.add(sysDataPermission.getDataId());
                        }
                    }
                }
            }
            returnMap.put("a01Ids",a01Ids);
            returnMap.put("deptIds",deptIds);
            returnMap.put("classifyIds",classifyIds);
            returnMap.put("otherPermission",otherPermission);
        }else{
            return null;
        }
        return returnMap;
    }*/
    public Map<String, List<String>> getDataPermission(String userId) {

        LambdaQueryWrapper<SysRoleData> roleDataLam = new LambdaQueryWrapper<>();
        roleDataLam.eq(SysRoleData::getRoleId, userId);
        List<SysRoleData> sysUserRoles = sysRoleDataService.list(roleDataLam);
        Map<String, List<String>> returnMap = new HashMap<>();
        if (sysUserRoles != null) {
            List<String> deptIds = sysUserRoles.stream().map(SysRoleData::getDataPermissionId).collect(Collectors.toList());
            returnMap.put("deptIds", deptIds);
        } else {
            return null;
        }
        return returnMap;
    }


    @Override
    public List<Map<String, List<String>>> getA01DataPermission(String userId) {
        Map<String, List<SysDataPermission>> dataPermissionMap = dataPermissionCacheService.getPermissions();
        LambdaQueryWrapper<SysUserRole> userRoleLam = new LambdaQueryWrapper<>();
        userRoleLam.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(userRoleLam);
        List<Map<String, List<String>>> returnList = new ArrayList<>();
        if (sysUserRoles != null) {
            for (SysUserRole sysUserRole : sysUserRoles) {
                Map<String, List<String>> oneMap = new HashMap<>();
                List<SysDataPermission> sysDataPermissions = dataPermissionMap.get(sysUserRole.getRoleId());
                if (sysDataPermissions != null) {
                    List<String> deptList = new ArrayList<>();
                    List<String> a01List = new ArrayList<>();
                    sysDataPermissions.stream().forEach(sysDataPermission -> {
                        if ("1".equals(sysDataPermission.getDataPermissionsType())) {
                            deptList.add(sysDataPermission.getDataId());
                        }
                        if ("2".equals(sysDataPermission.getDataPermissionsType())) {
                            a01List.add(sysDataPermission.getDataId());
                        }
                    });
                    if (deptList.size() > 0) {
                        oneMap.put("deptId", deptList);
                    }
                    if (a01List.size() > 0) {
                        oneMap.put("a01Id", a01List);
                    }
                    if (oneMap.size() > 0) {
                        returnList.add(oneMap);
                    }
                }
            }
        }
        return returnList;
    }

    /*public CommonResult insertDeptDatas(String roleId, String deptIds) {
        LambdaQueryWrapper<SysRoleData> sysRoleDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRoleDataLambdaQueryWrapper.eq(SysRoleData::getRoleId, roleId);
        List<SysRoleData> sysRoleDatas = sysRoleDataService.list(sysRoleDataLambdaQueryWrapper);
        List<String> dataIds = sysRoleDatas.stream().map(e -> e.getDataPermissionId()).collect(Collectors.toList());
        LambdaQueryWrapper<SysDataPermission> sysDataPermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDataPermissionLambdaQueryWrapper.eq(SysDataPermission::getDataPermissionsType, "1");
        if (dataIds != null && dataIds.size() > 0) {
            sysDataPermissionLambdaQueryWrapper.in(SysDataPermission::getDataPermissionsId, dataIds);
            List<SysDataPermission> sysDataPermissionList = this.list(sysDataPermissionLambdaQueryWrapper);
            if (sysDataPermissionList != null && sysDataPermissionList.size() > 0) {
                List<String> delDataIds = sysDataPermissionList.stream().map(e -> e.getDataPermissionsId()).collect(Collectors.toList());
                LambdaQueryWrapper<SysDataPermission> lam = new LambdaQueryWrapper<>();
                lam.in(SysDataPermission::getDataPermissionsId, delDataIds);
                LambdaQueryWrapper<SysRoleData> lax = new LambdaQueryWrapper<>();
                lax.in(SysRoleData::getDataPermissionId, delDataIds);
                this.remove(lam);
                sysRoleDataService.remove(lax);
            }

            if (!"root".equals(deptIds)) {
                //删除原有无权限干部
                LambdaQueryWrapper<SysDataPermission> delLambdaQueryWrapper = new LambdaQueryWrapper<>();
                delLambdaQueryWrapper.eq(SysDataPermission::getDataPermissionsType, "2");
                delLambdaQueryWrapper.in(SysDataPermission::getDataPermissionsId, dataIds);
                List<SysDataPermission> waitDelList = this.list(delLambdaQueryWrapper);
                if (waitDelList != null && waitDelList.size() > 0) {
                    List<String> stuIds = waitDelList.stream().map(e -> e.getDataId()).collect(Collectors.toList());
                    String[] deptIdList = deptIds.split(",");
                    LambdaQueryWrapper<StudentAnswer> answerLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    answerLambdaQueryWrapper.in(StudentAnswer::getId, stuIds);
                    answerLambdaQueryWrapper.notIn(StudentAnswer::getClassId, deptIdList);
                    List<StudentAnswer> studentAnswers = studentAnswerService.list(answerLambdaQueryWrapper);
                    if (studentAnswers != null && studentAnswers.size() > 0) {
                        List<String> delA01Id = studentAnswers.stream().map(e -> e.getId()).collect(Collectors.toList());
                        LambdaQueryWrapper<SysDataPermission> delA01Lam = new LambdaQueryWrapper<>();
                        delA01Lam.in(SysDataPermission::getDataId, delA01Id).in(SysDataPermission::getDataPermissionsId, dataIds);
                        List<SysDataPermission> delPer = this.list(delA01Lam);
                        List<String> delA01DataIds = delPer.stream().map(e -> e.getDataPermissionsId()).collect(Collectors.toList());
                        LambdaQueryWrapper<SysRoleData> delSysRole = new LambdaQueryWrapper<>();
                        delSysRole.in(SysRoleData::getDataPermissionId, delA01DataIds);
                        this.remove(delA01Lam);
                        sysRoleDataService.remove(delSysRole);
                    }
                }
            }
        }
        if (deptIds.equals("root")) {
            SysDataPermission sysDataPermission = new SysDataPermission();
            String sysDataPId = UUID.randomUUID().toString().replace("-", "");
            sysDataPermission.setDataPermissionsId(sysDataPId);
            sysDataPermission.setDataId("root");
            sysDataPermission.setDataPermissionsType("1");
            sysDataPermission.setDataPermissionsIdentity("1");
            SysRoleData sysRoleData = new SysRoleData();
            sysRoleData.setDataPermissionId(sysDataPId);
            sysRoleData.setRoleId(roleId);
            this.insertOrUpdate(sysDataPermission);
            sysRoleDataService.insertOrUpdate(sysRoleData);

        } else {
            String[] strs = deptIds.split(",");
            List<SysRoleData> sysRoleDataList = new ArrayList<>();
            List<SysDataPermission> sysDataPermissions = new ArrayList<>();
            for (String ids : strs) {
                SysDataPermission sysDataPermission = new SysDataPermission();
                String sysDataPId = UUID.randomUUID().toString().replace("-", "");
                sysDataPermission.setDataPermissionsId(sysDataPId);
                sysDataPermission.setDataId(ids);
                sysDataPermission.setDataPermissionsType("1");
                sysDataPermission.setDataPermissionsIdentity("1");
                SysRoleData sysRoleData = new SysRoleData();
                sysRoleData.setDataPermissionId(sysDataPId);
                sysRoleData.setRoleId(roleId);
                sysRoleDataList.add(sysRoleData);
                sysDataPermissions.add(sysDataPermission);
            }

            this.batchInsert(sysDataPermissions);
            sysRoleDataService.batchInsert(sysRoleDataList);
        }
        dataPermissionCacheService.updatePermissions();
        return CommonResult.success("添加成功");

    }*/

    /*
     * 获取当前登录用户的角色id
     * 这些内容用来给系统中各个方法做数据权限筛选使用
     * */
    public Map<String, Object> setSerachMap(Map<String, Object> map) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (loginUser != null && loginUser.getUser() != null && loginUser.getUser().getUserId() != null && !loginUser.getUser().getUserId().equals("admin")) {
            if (!loginUser.getUser().getUserId().equals("admin") && !loginUser.getUser().getUserId().equals("apply")) {
                LambdaQueryWrapper<SysUserRole> sysUserRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
                sysUserRoleLambdaQueryWrapper.eq(SysUserRole::getUserId, loginUser.getUser().getUserId());
                List<SysUserRole> list = sysUserRoleService.list(sysUserRoleLambdaQueryWrapper);
                List<String> roleIds = list.stream().map(e -> e.getRoleId()).collect(Collectors.toList());

                map.put("roleIds", roleIds);
            }

        }
        return map;
    }
}
