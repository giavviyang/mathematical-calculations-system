package com.fudian.project.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fudian.api.system.enums.DeptEnum;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.TokenApi;
import com.fudian.common.constant.HttpStatus;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.data.DataStoreWrapper;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.service.SysDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2019/12/31 13:43
 * @Desc:
 */
@RestController
@RequestMapping("system/dept")
@OperTitle("机构部门")
public class DeptController  {
    private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    SysDeptService sysDeptService;


    @Autowired
    DataStoreWrapper redisCache;

    @Autowired
    private TokenApi tokenService;




    /**
     *
     * @return 返回部门树
     */
    @GetMapping
    @OperInfo(info = "查询部门树")
    public ResponseEntity<CommonResult> queryDeptTree(SysDept sysDept){

        CommonResult commonResult = new CommonResult();
        commonResult.setCode(HttpStatus.SUCCESS);
        commonResult.setData(sysDeptService.getDeptTreeList(sysDept));
        return ResponseEntity.ok(commonResult);
    }

    /**
     *
     * @return 返回用户的部门树
     */
    @GetMapping("queryUserDeptTree")
    public ResponseEntity<CommonResult> queryUserDeptTree(String userId){

        CommonResult commonResult = new CommonResult();
        commonResult.setCode(HttpStatus.SUCCESS);
        commonResult.setData(sysDeptService.getUserDeptTreeList(userId));
        return ResponseEntity.ok(commonResult);
    }

    /**
     * 进行like查询
     * @param sysDept
     * @return
     */
    @GetMapping("querySysDeptByLike")
    @OperInfo(info = "模糊查询部门")
    public CommonResult<SysDept> querySysDeptByLike(SysDept sysDept){
        CommonResult commonResult=   sysDeptService.querySysDeptByLike(sysDept);
        return commonResult;
    }

    /**
     *
     * @return 返回部门树
     */
    @GetMapping("queryDeptListByParentId")
    @OperInfo(info = "查询部门树")
    public ResponseEntity<CommonResult> queryDeptListByParentId(){

        CommonResult commonResult = new CommonResult();
        commonResult.setCode(HttpStatus.SUCCESS);
        commonResult.setData(sysDeptService.getDeptList());
        return ResponseEntity.ok(commonResult);
    }

    /**
     * 添加一个部门
     * @param sysDept
     * @return
     */
    @PostMapping("insertDept")
    @OperInfo(info = "新增部门")
    public ResponseEntity<CommonResult> insertDept(SysDept sysDept){
        CommonResult commonResult =sysDeptService.insertSysDept(sysDept);
        return ResponseEntity.ok(commonResult);
    }

    /**
     * 修改一个部门
     * @param sysDept
     * @return
     */
    @PutMapping("updateDeptById")
    @OperInfo(info = "修改部门")
    public ResponseEntity<CommonResult> updateDeptById(SysDept sysDept){

        CommonResult commonResult =sysDeptService.updateDeptById(sysDept);

        return ResponseEntity.ok(commonResult);
    }

    /**
     * 检查该部门是否可删除
     * @param id 部门id
     * @return
     */
    @GetMapping("check/{id}")
    public ResponseEntity<CommonResult> checkDept(@PathVariable("id") String id){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(HttpStatus.SUCCESS);
        commonResult.setMsg("操作成功");
        if(StringUtils.isEmpty(id)){
            commonResult.setCode(HttpStatus.ERROR);
            commonResult.setMsg("参数错误");
        }else{
            DeptEnum.CheckDept check = sysDeptService.check(id);
            commonResult  = check.getResult();
        }

        return ResponseEntity.ok(commonResult);
    }


    /**
     * 删除该部门
     * @param sysDept 部门id
     * @return
     */
    @DeleteMapping("deleteDeptById")
    @OperInfo(info = "删除部门")
    public ResponseEntity<CommonResult> deleteDeptById(SysDept sysDept){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(HttpStatus.SUCCESS);
        commonResult.setMsg("操作成功");
        if(StringUtils.isEmpty(sysDept.getId())){
            commonResult.setCode(HttpStatus.ERROR);
            commonResult.setMsg("参数错误");
        }else{
            DeptEnum.CheckDept deleteDept = sysDeptService.deleteDept(sysDept.getId());
            commonResult  = deleteDept.getResult();
        }
        return ResponseEntity.ok(commonResult);
    }


    /**
     *
     * @param id 当前部门id
     * @param parentId 调整到部门id
     * @return
     */
    @PutMapping("setparent")
    @OperInfo(info = "调整部门")
    public ResponseEntity<CommonResult> updateParent(String id, String parentId){
        CommonResult commonResult = new CommonResult();

        commonResult.setCode(HttpStatus.SUCCESS);
        commonResult.setMsg("操作成功");
        if(!sysDeptService.updateParent(id, parentId)){
            commonResult.setCode(HttpStatus.ERROR);
            commonResult.setMsg("操作失败");
        }
        return ResponseEntity.ok(commonResult);
    }


    @PutMapping("order")
    @OperInfo(info = "部门排序")
    public ResponseEntity<CommonResult> saveOrder(String data){
        CommonResult commonResult = new CommonResult();

        commonResult.setCode(HttpStatus.SUCCESS);
        commonResult.setMsg("操作成功");
        if(!sysDeptService.saveOrder(data)){
            commonResult.setCode(HttpStatus.ERROR);
            commonResult.setMsg("操作失败");
        }
        return ResponseEntity.ok(commonResult);
    }

    /**
     * 根据父级Id获取当前子集部门列表List
     * @param sysDept 父级部门Id及status
     * @return
     */
    @GetMapping("getListDept")
    @OperInfo(info = "获取部门列表")
    public CommonGridResult getListDept(SysDept sysDept){
        return sysDeptService.getListDeptByPId(sysDept);
    }

    /**
     * 排序保存
    * @param
     * @return
     */
    @PutMapping("orderSave")
    @OperInfo(info = "排序保存")
    public CommonResult orderSave(SysDept sysDept){
        return sysDeptService.orderSave(sysDept);
    }

    /**
     * 删除该部门
     * @param sysDept 部门id
     * @return
     */
    @DeleteMapping("deleteDeptByIds")
    @OperInfo(info = "删除部门")
    public CommonResult deleteDeptByIds(SysDept sysDept){
        return sysDeptService.delSysDeptByIds(sysDept);
    }


    /**
     * 排序保存
     * @param
     * @return
     */
    @GetMapping("getDeptListByLoginUser")
    @OperInfo(info = "获取学校信息")
    public CommonResult getDeptListByUser(){
        return sysDeptService.getDeptListByUser();
    }

    /**
    * 根据当前登录用户绑定的学校获取该学校的节点树
    */
    @GetMapping("getDeptTreeByUserSchool")
    @OperInfo(info = "根据当前登录用户绑定的学校获取该学校的节点树")
    public CommonResult getDeptTreeByUserSchool(String showClass){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        LambdaQueryWrapper<SysDept> sysDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(showClass)&&showClass.equals("false")){
            sysDeptLambdaQueryWrapper.ne(SysDept::getNodeType,"2");
        }
        sysDeptLambdaQueryWrapper.eq(SysDept::getStatus,0);
        sysDeptLambdaQueryWrapper.like(SysDept::getAncestors,user.getDeptId()).or().eq(SysDept::getId,user.getDeptId());
        sysDeptLambdaQueryWrapper.orderByAsc(SysDept::getOrderNum);
        List<SysDept> list = sysDeptService.list(sysDeptLambdaQueryWrapper);
        JSONArray deptTreeGrid = sysDeptService.getDeptTreeGrid(list, "000");
        return CommonResult.success(deptTreeGrid);
    }
}
