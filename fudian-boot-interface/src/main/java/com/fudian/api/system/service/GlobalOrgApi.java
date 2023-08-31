package com.fudian.api.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fudian.api.system.pojo.GlobalOrg;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.SysFile;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 16:20
 * @Desc:
 */
public interface GlobalOrgApi {

    public List<GlobalOrg> list(Wrapper<GlobalOrg> queryWrapper);

    public GlobalOrg getOne(Wrapper<GlobalOrg> queryWrapper);
}
