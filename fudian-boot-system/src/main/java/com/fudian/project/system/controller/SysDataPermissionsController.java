package com.fudian.project.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.system.pojo.SysDataPermission;
import com.fudian.api.system.pojo.SysFile;
import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.pojo.SysRoleData;
import com.fudian.project.system.service.SysDataPermissionService;
import com.fudian.project.system.service.SysRoleDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xyh
 * @title: SysDataPermissions
 * @projectName platform
 * @description: TODO 数据权限的控制类
 * @date 2019/12/28 15:12
 */
@RestController
@RequestMapping("dataPermissions")
@OperTitle("数据权限")
public class SysDataPermissionsController {

    private static final Logger log = LoggerFactory.getLogger(SysDataPermissionsController.class);

    @Autowired
    private SysDataPermissionService sysDataPermissionService;


    /**
     * 向数据权限表中插入数据权限
     *
     * @param dataPermissions 多个数据权限对象
     * @return CommonResult<SysDataPermission>
     */
    @PostMapping("insertDataPermissions")
    @OperInfo(info = "新增数据权限")
    public CommonResult<SysDataPermission> insertDataPermissions(List<SysDataPermission> dataPermissions) {
        CommonResult<SysDataPermission> commonResult = sysDataPermissionService.insertDataPermissions(dataPermissions);
        return commonResult;
    }

    /**
     * 向数据权限表中插入数据权限
     *
     * @param permissionId
     * @param dataPermissions
     * @return CommonResult<SysDataPermission>
     */
    @PostMapping("insertDataPermissionsByPermissionId")
    @OperInfo(info = "新增数据权限")
    public CommonResult<SysDataPermission> insertDataPermissionsByPermissionId(String permissionId, String dataPermissions) {
        CommonResult commonResult = sysDataPermissionService.insertDataPermissionsByPermissionId(permissionId, dataPermissions);
        return commonResult;
    }

    /**
     * 获取具有权限的部门
     *
     * @return CommonResult<SysDataPermission>
     */
    @GetMapping("getSysFile")
    @OperInfo(info = "获取文件数量")
    public CommonResult getSysFile(String e01z1Id) {
        CommonResult commonResult = new CommonResult();
        List<SysFile> sysFiles = null;
        try {
            sysFiles = sysDataPermissionService.getSysFile(e01z1Id);
            commonResult.setCode(200);
            commonResult.setData(sysFiles);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(500);
            commonResult.setMsg("服务器繁忙");
        }
        return commonResult;
    }

    /**
     * 获取具有权限的部门
     *
     * @return CommonResult<SysDataPermission>
     */
    @GetMapping("getDept")
    @OperInfo(info = "获取具有权限的部门")
    public CommonResult getDept() {
        CommonResult commonResult = new CommonResult();
        JSONArray dept = null;
        try {
            dept = sysDataPermissionService.getDept();
            commonResult.setCode(200);
            commonResult.setData(dept);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(500);
            commonResult.setMsg("服务器繁忙");
        }
        return commonResult;
    }


    /**
     * @return
     */
    @GetMapping("getType")
    @OperInfo(info = "获取材料分类")
    public CommonResult getType(String a01Id) {
        CommonResult commonResult = new CommonResult();
        JSONArray jsonArray = null;
        try {
            jsonArray = sysDataPermissionService.getType(a01Id);
            commonResult.setCode(200);
            commonResult.setData(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(500);
            commonResult.setMsg("服务器繁忙");
        }
        return commonResult;
    }

    /**
     * @return
     */
    @GetMapping("getE01z1")
    @OperInfo(info = "获取材料")
    public CommonResult getE01z1(String a01Id) {
        CommonResult commonResult = new CommonResult();
        List<E01z1> list = null;
        try {
            list = sysDataPermissionService.getE01z1(a01Id);
            commonResult.setCode(200);
            commonResult.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(500);
            commonResult.setMsg("服务器繁忙");
        }
        return commonResult;
    }


    /**
     * 查询数据
     *
     * @return CommonResult<SysDataPermission>
     */
    @GetMapping("selectDataPermissionsByPermissionId")
    @OperInfo(info = "查询数据权限")
    public CommonResult<SysDataPermission> selectDataPermissionsByPermissionId(String permissionId) {
        CommonResult commonResult = sysDataPermissionService.selectDataPermissionsByPermissionId(permissionId);
        return commonResult;
    }


    @DeleteMapping("deleteDataPermissionsById")
    public CommonResult deleteDataPermissionsById(List<String> dataPermissionsIds) {
        CommonResult commonResult = sysDataPermissionService.deleteDataPermissionsById(dataPermissionsIds);
        return commonResult;
    }


    /*
     * 保存部门数据权限信息
     *
     * @param
     * @return
     */
    /*@PostMapping("saveDataPermissions")
    @OperInfo(info = "修改数据权限")
    public CommonResult saveDataPermissions(String roleId, String deptIds) {
        try {
            sysDataPermissionService.insertDeptDatas(roleId, deptIds);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(500, "保存失败");
        }
        //CommonResult commonResult= sysDataPermissionService.deleteDataPermissionsById(dataPermissionsIds);
        return CommonResult.success("保存成功");
    }*/

    @Autowired
    SysRoleDataService sysRoleDataService;

    /**
     * 保存教师数据权限信息
     *
     * @param
     * @return
     */
    @PostMapping("saveUserDataPermissions")
    @OperInfo(info = "修改数据权限")
    public CommonResult saveUserDataPermissions(String sysRoleDataString,String userId) {
        try {
            List<SysRoleData> sysRoleData = JSON.parseArray(sysRoleDataString, SysRoleData.class);
            QueryWrapper<SysRoleData> removeWrapper = new QueryWrapper<>();
            removeWrapper.eq("role_id", userId);
            sysRoleDataService.remove(removeWrapper);
            sysRoleDataService.saveBatch(sysRoleData);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(500, "保存失败");
        }
        //CommonResult commonResult= sysDataPermissionService.deleteDataPermissionsById(dataPermissionsIds);
        return CommonResult.success("保存成功");
    }
}
