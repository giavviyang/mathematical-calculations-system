package com.fudian.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysDictData;

import java.util.List;

public interface SysDictDataService extends IService<SysDictData>{


    int updateBatch(List<SysDictData> list);

    int batchInsert(List<SysDictData> list);

    int insertOrUpdate(SysDictData record);

    int insertOrUpdateSelective(SysDictData record);

    /**
     *  在sys_dict_data表中新增或修改数据
     * @param sysDictData
     * @return
     */
    CommonResult addOrupdateDataNode(SysDictData sysDictData);

    /**
     * 通过ids批量查找
     * @param ids
     * @return
     */
    List<SysDictData> queryListByIds(List<String> ids);

    /**
     * 通过 dictLabel模糊查询
     * @param dictLabel
     * @param pageSize
     * @param pageNum
     * @return
     */
    CommonGridResult queryListByName(String dictLabel, Integer pageSize, Integer pageNum, String dictValueid, String dictTypeid);

    /**
     * 通过字典类型查询当前类型下所有的字典树结构
     * @param dictType
     * @return
     */
    CommonResult queryByDictType(String dictType);

}
