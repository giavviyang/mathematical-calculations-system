package com.fudian.api.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fudian.api.system.pojo.SysFile;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 16:17
 * @Desc:
 */
public interface SysFileApi {

    public int batchInsert(List<SysFile> list);

    public SysFile queryBySysFile(SysFile sysFile);

    public boolean removeByIds(Collection<? extends Serializable> idList);

    public boolean save(SysFile entity);

    public List<SysFile> list(Wrapper<SysFile> queryWrapper);

    public SysFile getOne(Wrapper<SysFile> queryWrapper);

    public boolean removeById(Serializable id);

    public boolean remove(Wrapper<SysFile> wrapper);

    public boolean updateById(SysFile entity);

    public List<SysFile> queryBySysFileList(String binding_id);

    public int selectCount(Wrapper<SysFile> queryWrapper);

    public boolean update(Wrapper<SysFile> queryWrapper);

    public boolean updateBatchById(Collection<SysFile> entityList);

    public SysFile  getById(Serializable id);
}
