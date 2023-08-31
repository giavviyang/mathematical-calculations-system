package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.AppletFileMapper;
import com.fudian.business.pojo.AppletFile;
import com.fudian.business.service.AppletFileService;
import com.fudian.common.pojo.CommonGridResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


/**
 * 程序文件
 * @author xuchenghong
 */

@Service
public class AppletFileServiceImpl extends ServiceImpl<AppletFileMapper, AppletFile> implements AppletFileService {


    @Resource
    private AppletFileMapper appletFileMapper;

    /**
     * 分页查询小程序文件
     *
     * @param appletFile
     * @return
     */
    @Override
    public CommonGridResult pagingSelectAppletFile(AppletFile appletFile) {
        LambdaQueryWrapper<AppletFile> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(appletFile.getPageNum(), appletFile.getPageSize());
        List<AppletFile> appletFiles = appletFileMapper.selectList(queryWrapper);

        Page page = (Page) appletFiles;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(appletFile.getPageNum(), appletFile.getPageSize(), total, appletFiles);
        return commonGridResult;
    }

    /**
     * 查询小程序文件根据id
     *
     * @param fileId
     * @return
     */
    @Override
    public AppletFile selectAppletFileById(String fileId) {

        AppletFile appletFile = appletFileMapper.selectById(fileId);
        return appletFile;
    }

    /**
     * 添加小程序文件
     *
     * @param appletFile
     * @return
     */
    @Override
    public int insertAppletFile(AppletFile appletFile) {

        int insert = appletFileMapper.insert(appletFile);
        return insert;
    }

    /**
     * 修改小程序文件
     *
     * @param appletFile
     * @return
     */
    @Override
    public int updateAppletFileById(AppletFile appletFile) {

        int i = appletFileMapper.updateById(appletFile);
        return i;
    }

    /**
     * 批量删除小程序文件
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteAppletFileById(List<String> ids) {
        int insert = appletFileMapper.deleteBatchIds(ids);
        return insert;
    }
    /**
     * 删除小程序文件
     *
     * @param id
     * @return
     */
    @Override
    public int deleteAppletFileById(String id) {
        int insert = appletFileMapper.deleteById(id);
        return insert;
    }
}
