package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.AppletFile;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;

/**
 * 小程序文件
 * @author xuchenghong
 */
public interface AppletFileService extends IService<AppletFile> {

    /**
     * 分页查询小程序文件
     *
     * @param appletFile
     * @return
     */
    CommonGridResult pagingSelectAppletFile(AppletFile appletFile);

    /**
     * 查询小程序文件根据id
     *
     * @param fileId
     * @return
     */
    AppletFile selectAppletFileById(String fileId);

    /**
     * 添加小程序文件
     *
     * @param appletFile
     * @return
     */
    int insertAppletFile(AppletFile appletFile);

    /**
     * 修改小程序文件
     *
     * @param appletFile
     * @return
     */
    int updateAppletFileById(AppletFile appletFile);

    /**
     * 批量删除小程序文件
     *
     * @param id
     * @return
     */
    int deleteAppletFileById(String id);
    /**
     * 批量删除小程序文件
     *
     * @param ids
     * @return
     */
    int deleteAppletFileById(List<String> ids);
}
