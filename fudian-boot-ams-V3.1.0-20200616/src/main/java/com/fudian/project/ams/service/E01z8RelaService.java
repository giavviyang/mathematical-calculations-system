package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.E01z8Rela;

import java.util.List;
import java.util.Map;

public interface E01z8RelaService extends IService<E01z8Rela>{

    int updateBatch(List<E01z8Rela> list);

    int batchInsert(List<E01z8Rela> list);

    int insertOrUpdate(E01z8Rela record);

    int insertOrUpdateSelective(E01z8Rela record);

    /**
     * 功能描述：获取批次下所有的数据
     * @author lutao
     * @param  e01z8Id
     * @return :
     * @date    2019/12/27
     */
    List<E01z8Rela> queryE01z8RelaList(String e01z8Id);

    /**
     * 功能描述：实体档案查阅反馈填写
     * @author lutao
     * @param
     * @return :
     * @date    2019/12/27
     */
    int updateE01z8RelaById(E01z8Rela e01z8Rela);

    /**
     * 功能描述：批量新增
     * @author lutao
     * @param  uid 关联主表id
     * @return :
     * @date    2019/12/30
     */
    int insertE01zcRelaList(String uid,List<E01z8Rela> e01z8Relas);

    /**
     * 功能描述：根据关联id删除数据
     * @author lutao
     * @param  id
     * @return :
     * @date    2019/12/30
     */
    int deleteE01z8Rela(String id);

    /**
     * 功能描述：当前登录用户电子档案查阅搜索
     * @author lutao
     * @param  userId,a0101
     * @return :
     * @date    2019/12/31
     */
    List<Map<String,Object>> queryListByA01AndText(String userId, String a0101,String deptName,Integer pageNum,Integer pageSize);

    /**
     * 功能描述：获取所有未审批的数据
     * @author lutao
     * @param
     * @return :
     * @date    2020/1/6
     */
    List<E01z8Rela> quertNoApproval();

    /**
     * 功能描述：实体档案查阅审批
     * @author lutao
     * @param id:批次id,ids:子表的uuid
     * @return :
     * @date    2020/1/6
     */
    int ApprovalEntity(String id,List<String> idList);

    /**
     * 功能描述：修改单条数据
     * @author lutao
     * @param  e01z8Rela
     * @return :
     * @date    2020/1/10
     */
    int updateE01z8Rela(E01z8Rela e01z8Rela);

    /**
     * 功能描述：获取当前批次下所有未审批的数据
     * @author lutao
     * @param
     * @return :
     * @date    2020/1/17
     */
    List<E01z8Rela> queryE01z8RelaListNotApprov(String e01z8Id);

    /**
     * 根据批次id获取所有详细信息
     * @param id
     * @return
     */
    CommonResult queryE01z8RelasByE01z8Id(String id);
}
