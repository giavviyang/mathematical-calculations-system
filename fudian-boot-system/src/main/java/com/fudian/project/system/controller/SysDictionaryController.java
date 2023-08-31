package com.fudian.project.system.controller;

import com.fudian.api.system.pojo.SysDictionary;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.service.SysDictionaryService;
import com.fudian.project.system.service.SysDictionaryServiceTwo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 系统字典表操作
 * @ClassName SysDictionaryController
 * @Author liang
 * @Date 2020/1/7 17:10
 * @Version 1.0.0
 **/
@Slf4j
@RestController
@RequestMapping("sysDictionary")
@OperTitle("系统字典")
public class SysDictionaryController {

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private SysDictionaryServiceTwo sysDictionaryServiceTwo;

    /**
     * 通过类型查询字典表数据，并去除-
     * @return
     */
    @GetMapping("queryDictionaryByType")
    @OperInfo(info = "查询字典数据")
    public CommonResult queryDictionaryByType(SysDictionary sysDictionary){
        return sysDictionaryService.querySysDictionaryByType(sysDictionary);
    }

    /**
     * 根据多个type获取相应字典表数据
     * @param types
     * @return
     */
    @GetMapping("queryDictionaryByTypes")
    @OperInfo(info = "查询字典数据")
    public CommonResult queryDictionaryByTypes(String types){

        return sysDictionaryService.querySysDictionaryByTypes(types);
    }

    /**
     * 返回知识点左侧树
     * @return
     */
    @GetMapping("queryMaterialsTree")
    @OperInfo(info = "查询材料树")
    public CommonResult queryMaterialsTree(){

        return sysDictionaryService.queryMaterialsTree();
    }



    /**
     * 获取材料分类列表 以供下拉框使用  去除4 9 大类
     * @return
     */
    @GetMapping("queryArchiveTree")
    @OperInfo(info = "查询材料分类列表")
    public CommonResult queryArchiveTree(){

        return sysDictionaryService.queryArchiveTreeDatas();
    }

    @GetMapping("queryTreeById")
    @OperInfo(info = "查询字典树")
    public CommonGridResult queryTreeById(SysDictionary sysDictionary){
        log.info("--------------" + sysDictionaryServiceTwo + "-------------------");
        return sysDictionaryService.queryTreeByIdNew(sysDictionary);
    }

    @PostMapping("insertSysDictionary")
    @OperInfo(info = "新增字典")
    public CommonResult insertSysDictionary(@RequestBody SysDictionary sysDictionary){
        return sysDictionaryService.insertSysDictionary(sysDictionary);
    }

    @RequestMapping("updateSysDictionary")
    @OperInfo(info = "修改字典")
    public CommonResult modifySysDictionary(SysDictionary sysDictionary){
        return sysDictionaryService.modifySysDictionary(sysDictionary);
    }

    @DeleteMapping("deleteSysDictionary")
    @OperInfo(info = "删除字典")
    public CommonResult delete(String json){
        log.error("--------------" + sysDictionaryServiceTwo + "-------------------");
        return sysDictionaryService.deleteByIds(json);
    }

    @GetMapping("getDataByEparentCode")
    public CommonResult getDataByEparentCode(SysDictionary sysDictionary){
        return sysDictionaryService.getDataByEparentCode(sysDictionary);
    }
}
