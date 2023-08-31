package com.fudian.project.system.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.pojo.SysDictionary;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;

import java.util.List;

public interface SysDictionaryService extends IService<SysDictionary> {


    int updateBatch(List<SysDictionary> list);

    int batchInsert(List<SysDictionary> list);

    int insertOrUpdate(SysDictionary record);

    int insertOrUpdateSelective(SysDictionary record);

    /**
     * 通过类型获取字典表list集合
     *
     * @return
     */
    CommonResult querySysDictionaryByType(SysDictionary sysDictionary);

    /**
     * 用过多个type获取相应字典表数据
     *
     * @param types
     * @return
     */
    CommonResult querySysDictionaryByTypes(String types);

    CommonResult queryMaterialsTree();

    /**
     * 获取档案材料类型下拉数据
     *
     * @return
     */
    CommonResult queryArchiveTreeDatas();

    CommonResult queryTreeById(String id);

    CommonGridResult queryTreeByIdNew(SysDictionary sysDictionary);

    CommonResult insertSysDictionary(SysDictionary sysDictionary);

    CommonResult modifySysDictionary(SysDictionary sysDictionary);

    CommonResult deleteByIds(String json);

    /**
     * @auther xuchenghong
     * 根据类型和Eparent查询
     * @return
     */
    List<SysDictionary> queryByTypeAndEparent(String type,String eparent);

    CommonResult getDataByEparentCode(SysDictionary sysDictionary);

}
