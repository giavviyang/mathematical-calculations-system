package com.fudian.project.system.controller;

import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.pojo.SysDictData;
import com.fudian.project.system.pojo.SysDictType;
import com.fudian.project.system.service.SysDictDataService;
import com.fudian.project.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 数据字典信息
 *
 */
@RestController
@RequestMapping("/system/dict/type")
@OperTitle("数据字典信息")
public class SysDictTypeController {
    @Autowired
    private SysDictTypeService dictTypeService;
    @Autowired
    private SysDictDataService dictDataService;

    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    @OperInfo(info = "查询字典类型")
    public CommonGridResult list(SysDictType dictType) {
        CommonGridResult result = dictTypeService.selectDictTypeList(dictType);
        return result;
    }

    @GetMapping("/getType/{dictType}")
    @OperInfo(info = "查询字典类型")
    public CommonResult getType(@PathVariable String dictType){
        return dictTypeService.selectDictType(dictType);
    }

    @PutMapping("/updateType")
    @OperInfo(info = "修改字典类型")
    public CommonResult updateType(@RequestBody SysDictType sysDictType){
        return dictTypeService.updateType(sysDictType);
    }

    @PostMapping("/addType")
    @OperInfo(info = "新增字典类型")
    public CommonResult addType(@RequestBody SysDictType sysDictType, SysDictData sysDictData){
        return dictTypeService.addType(sysDictType,sysDictData);
    }

    @DeleteMapping("/delType/{dictTypes}")
    @OperInfo(info = "删除字典类型")
    public CommonResult delType(@PathVariable String dictTypes){
        return dictTypeService.delType(dictTypes);
    }

    @GetMapping("/getLeftTreeNode")
    @OperInfo(info = "新增字典树")
    public CommonResult getLeftTreeNode(){
        return dictTypeService.getLeftTreeNode();
    }

    @PostMapping("/addDictData")
    @OperInfo(info = "新增字典数据")
    public CommonResult addData(@RequestBody SysDictData sysDictData){
        return dictTypeService.addData(sysDictData);
    }

    @GetMapping("/getData/{dictValueid}")
    @OperInfo(info = "查询字典数据")
    public CommonResult getData(@PathVariable String dictValueid){
        return dictTypeService.getData(dictValueid);
    }

    @PutMapping("/updateData")
    @OperInfo(info = "修改字典数据")
    public CommonResult updateData(@RequestBody SysDictData sysDictData){
        return dictTypeService.updateData(sysDictData);
    }

    @DeleteMapping("/delData/{dictDatas}")
    @OperInfo(info = "删除字典数据")
    public CommonResult delData(@PathVariable String dictDatas){
        return dictTypeService.delData(dictDatas);
    }

    @GetMapping("/clickTreeNode")
    @OperInfo(info = "点击字典数据")
    public CommonGridResult clickTreeNode(SysDictData sysDictData, String dictTypeid){
        CommonGridResult result=dictTypeService.clickNode(sysDictData,dictTypeid);
        return result;
    }

    @GetMapping("queryDictTree")
    @OperInfo(info = "查询字典树")
    public CommonResult queryDictTree(String id){
        CommonResult result=null;
        result=dictTypeService.treeList(id);
        return result;
    }

    @PostMapping("addNode")
    @OperInfo(info = "新增字典节点")
    public CommonResult addNode(String dictType, String status, String remark, String dictLabel, String dictTypeid, String dictValueid){
        CommonResult result=dictTypeService.addNodeById(dictType,status,remark,dictLabel,dictTypeid,dictValueid);
        System.out.println(dictValueid);
        System.out.println(dictTypeid);
        return result;
    }

    /**
     * 新增或修改-字典数据提交按钮 sys_dict_data表
     * @param sysDictData
     * @return
     */
    @PostMapping("addOrUpdateNodeData")
    @OperInfo(info = "修改字典数据")
    public CommonResult addOrUpdateNodeData(@RequestBody SysDictData sysDictData){
        CommonResult result=null;
         result=dictDataService.addOrupdateDataNode(sysDictData);
        return result;
    }

    /**
     *  新增/修改-字典类型提交按钮 sys_dict_type
     * @param sysDictType
     * @return
     */
    @PostMapping("addOrUpdateNodeType")
    @OperInfo(info = "修改字典节点")
    public CommonResult updateNode(@RequestBody SysDictType sysDictType, String dictLabel){
        CommonResult result=null;
//        sysDictType.setDictName(dictLabel);
        result=dictTypeService.addorUpdateTypeNode(sysDictType);
        return result;
    }

    /**
     * 通过id批量删除，如何有子节点，则子节点一起删除
     * @param ids
     * @param type type=true删除sys_dict_data,false删除sys_dict_type
     * @return
     */
    @DeleteMapping("removeNodeById")
    @OperInfo(info = "修改字典节点")
    public CommonResult removeNodeById(String[] ids, boolean type){
        CommonResult result=null;
        List<String> idList= Arrays.asList(ids);
        Boolean i;
        if (type){
            i=dictDataService.removeByIds(idList);
        }else {
            i=dictTypeService.removeByIds(idList);
            //同时删除子节点sys_dict_data中的数据
            List<SysDictData> dataList=dictDataService.queryListByIds(idList);
        }
        if (i){
            result=new CommonResult(200,"删除成功",1);
        }else {
            result=new CommonResult(500,"删除失败",0);
        }
        return result;
    }

    @GetMapping("queryByLabel")
    @OperInfo(info = "修改字典数据")
    public CommonGridResult queryByLabel(String dictLabel, Integer pageSize, Integer pageNum, boolean queryType, String dictValueid, String dictTypeid){
        CommonGridResult result=null;
        if (queryType){
            result=dictTypeService.queryListByName(dictLabel,pageSize,pageNum);
        }else {
            result=dictDataService.queryListByName(dictLabel,pageSize,pageNum,dictValueid,dictTypeid);
        }
        return result;
    }

    @GetMapping("getTreeByDictType")
    @OperInfo(info = "修改字典树")
    public CommonResult getTreeByDictType(String transValue){
        CommonResult result=null;
        result=dictDataService.queryByDictType(transValue);
        return result;
    }
}
