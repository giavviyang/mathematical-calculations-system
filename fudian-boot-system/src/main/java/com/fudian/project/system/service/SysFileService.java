package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.pojo.SysFile;
import com.fudian.common.pojo.CommonResult;

import java.util.List;

public interface SysFileService extends IService<SysFile> {


    int updateBatch(List<SysFile> list);

    int batchInsert(List<SysFile> list);

    int insertOrUpdate(SysFile record);

    int insertOrUpdateSelective(SysFile record);

    /**
     * 根据条件查询文件对象
     */
    SysFile queryBySysFile(SysFile sysFile);

    /**
     * 根据binding_id查出所有的材料对象
     */
    List<SysFile> queryBySysFileList(String binding_id);

    CommonResult queryByE01z1Ids(String ids);
}
