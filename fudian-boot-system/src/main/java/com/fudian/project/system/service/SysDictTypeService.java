package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysDictData;
import com.fudian.project.system.pojo.SysDictType;

import java.util.List;

public interface SysDictTypeService extends IService<SysDictType> {


    int updateBatch(List<SysDictType> list);

    int batchInsert(List<SysDictType> list);

    int insertOrUpdate(SysDictType record);

    int insertOrUpdateSelective(SysDictType record);

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    CommonGridResult selectDictTypeList(SysDictType dictType) ;

    /**
     * 修改查询
     *
     * @param dictType
     * @return
     */
    CommonResult selectDictType(String dictType);

    /**
     * 修改
     *
     * @param sysDictType
     * @return
     */
    CommonResult updateType(SysDictType sysDictType);

    /**
     * 新增
     *
     * @param sysDictType
     * @return
     */
    CommonResult addType(SysDictType sysDictType, SysDictData sysDictData);

    /**
     * 删除
     *
     * @param dictType
     * @return
     */
    CommonResult delType(String dictTypes);

    /**
     * 左侧节点树
     * @return
     */
    CommonResult getLeftTreeNode();

    /**
     * 添加字典数据
     * @param sysDictData
     * @return
     */
    CommonResult addData(SysDictData sysDictData);

    /**
     * 获取字典数据
     * @param dictValueid
     * @return
     */
    CommonResult getData(String dictValueid);

    /**
     * 修改字典数据
     * @param sysDictData
     * @return
     */
    CommonResult updateData(SysDictData sysDictData);

    /**
     * 删除字典数据
     * @param dictDatas
     * @return
     */
    CommonResult delData(String dictDatas);

    /**
     * 点击节点向后台传id
     * @param dictTypeid
     * @param sysDictData
     * @return
     */
    CommonGridResult clickNode(SysDictData sysDictData, String dictTypeid);


    /**
     * 数据库加入parentId重新写方法
     * 获取字典表树结构
     * @return
     */
    CommonResult treeList(String id);

    CommonResult addNodeById(String dictType, String status, String remark, String dictLabel, String dictTypeid, String dictValueid);

    /**
     * 通过id删除数据
     * @param id
     * @return
     */
    CommonResult deleteNodeById(String id);

    CommonResult addorUpdateTypeNode(SysDictType sysDictType);

    /**
     * 通过 dictLabel模糊查询
     * @param dictLabel
     * @param pageSize
     * @param pageNum
     * @return
     */
    CommonGridResult queryListByName(String dictLabel, Integer pageSize, Integer pageNum);
}
