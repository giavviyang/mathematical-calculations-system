package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.E01z8;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface E01z8Service extends IService<E01z8> {

    int updateBatch(List<E01z8> list);

    int batchInsert(List<E01z8> list);

    int insertOrUpdate(E01z8 record);

    int insertOrUpdateSelective(E01z8 record);

    /**
     * 统计实体查阅
     * @return
     */
    List<Map<String,Object>> summaryE01z8();

    /**
     * 功能描述：获取当前登录用户的所有实体档案查阅申请记录
     *
     * @param id
     * @return :
     * @author lutao
     * @date 2019/12/27
     */
    List<E01z8> queryE01z8List(String id);

    /**
     * 功能描述：单条数据插入
     *
     * @param userId
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    String insertE01z8(String uuid, String userId);

    /**
     * 功能描述：获取当前全宗下所有的实体档案查阅数据
     *
     * @param
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    List<E01z8> queryE01z8List();

    /**
     * 功能描述：根据id删除数据
     *
     * @param id
     * @return : int
     * @author lutao
     * @date 2019/12/30
     */
    int deleteE01z8(String id);

    /**
     * 功能描述：修改
     *
     * @param
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    int updateE01z8(E01z8 e01z8);

    /**
     * 功能描述：实体档案操作台账查阅搜索
     *
     * @param
     * @return :
     * @author lutao
     * @date 2019/12/31
     */
    List<Map<String, Object>> queryE01z8AndE01z8Rela(String deptName, String a0101, Integer pageNum, Integer pageSize);

    /**
     * 功能描述：批量查数据
     *
     * @return :
     * @author lutao
     * @date 2020/1/6
     */
    List<E01z8> queryE01z8List(List<String> ids);

    int updateBatchSelective(List<E01z8> list);

    /**
     * 实体档案查阅数据新增（广东海关修改）
     *
     * @param data 包含e01z9对象与详情信息的数据
     */
    CommonResult insertByEntityAndList(Map<String, Object> data, MultipartFile files);

    /**
     * 实体档案查阅记录（广东海关修改）
     *
     * @param e01z8
     * @return
     */
    CommonGridResult getE01z8sByStates(E01z8 e01z8, String isAll);

    /**
     * 到期续查
     * @param condition
     * @return
     */
    CommonResult archiveRenewalOfMaturity(String condition);

    /**
     * 审批
     * @param ids
     * @param isAgree
     * @return
     */
    CommonResult archiveApply(String ids,String isAgree,String e01z817,String flag);

    /**
     * 获取未审批且是系统内用户申请的数据
     * */
    List<E01z8> queryNoAppov();
}









