package com.fudian.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.pojo.SysBrowseLog;

import java.util.List;

/**
*@author lutao
*@create 2020/3/9
*/

public interface SysBrowseLogService extends IService<SysBrowseLog> {


int updateByPrimaryKeySelective(SysBrowseLog record);

int updateByPrimaryKey(SysBrowseLog record);

int updateBatch(List<SysBrowseLog> list);

int batchInsert(List<SysBrowseLog> list);

/**
 * 功能描述：根据条件获取所有的浏览日志
 * @author lutao
 * @param
 * @return :
 * @date    2020/3/9
*/
List<SysBrowseLog> queryAllLog(SysBrowseLog sysBrowseLog);
}
