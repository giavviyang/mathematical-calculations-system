package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.pojo.GlobalOrg;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;

import java.util.List;
import java.util.Map;

public interface GlobalOrgService extends IService<GlobalOrg> {


    int updateBatch(List<GlobalOrg> list);

    int batchInsert(List<GlobalOrg> list);

    int insertOrUpdate(GlobalOrg record);

    int insertOrUpdateSelective(GlobalOrg record);

    /**
     * 依据对象进行分页查询
     * @param globalOrg
     * @return
     */
    CommonGridResult queryGlobalOrgList(GlobalOrg globalOrg);

    /**
     *删除当前全宗数据
     * 1、判断用户表中是否存在数据
     * 2、判断数据
     * @param globalOrgId  全宗对象id
     * @return boolean
     */
    boolean deleteGlobalOrgById(String globalOrgId);

    /**
     * 解析授权码
     * @param value  授权码
     * @return   Map<String,Object>
     */
    Map<String,Object>  analysisCode(String value);

    /**
     * 解析校验数据
     * @param globalOrg 全宗对象参数
     * @return  CommonResult<GlobalOrg>  data中为Map对象
     */
    CommonResult<GlobalOrg> confirmPermissionsCode(GlobalOrg globalOrg);

    /**
     * 依据id更新数据
     * @param globalOrg  全宗对象参数
     * @return   CommonResult<GlobalOrg>
     */
    CommonResult<GlobalOrg> updateGlobalOrgById(GlobalOrg globalOrg);

    /**
     * 校验当前数据库是否能够联通
     * @param globalOrg  全宗对象参数
     * @return boolean
     */
    boolean  dataBaseConfirm(GlobalOrg globalOrg);


    /**
     * 校验当前文件存储库是否能够联通
     * @param globalOrg  对象
     * @return boolean
     */
    boolean  fileBaseConfirm(GlobalOrg globalOrg);


    CommonResult queryAllGlobalOrgList();
}
