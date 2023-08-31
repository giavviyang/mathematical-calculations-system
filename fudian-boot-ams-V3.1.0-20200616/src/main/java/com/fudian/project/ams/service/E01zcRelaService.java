package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.ams.pojo.E01zcRela;

import java.util.List;
import java.util.Map;

public interface E01zcRelaService extends IService<E01zcRela> {

    int updateBatch(List<E01zcRela> list);

    int batchInsert(List<E01zcRela> list);

    int insertOrUpdate(E01zcRela record);

    int insertOrUpdateSelective(E01zcRela record);

    /**
     * 功能描述：当前批次下所有的干部信息或材料信息
     *
     * @param id(E01zc.id)
     * @return :
     * @author lutao
     * @date 2019/12/27
     */
    List<E01zcRela> queryE01zcRelaList(String id);

    /**
     * 功能描述：电子档案查阅反馈填写
     *
     * @param
     * @return :
     * @author lutao
     * @date 2019/12/27
     */
    int updateE01zcRelaById(E01zcRela e01zcRela) throws IllegalAccessException;

    /**
     * 功能描述：批量插入数据
     *
     * @param uid 关联主表id
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    int insertE01zcRelaList(String uid, List<E01zcRela> e01zcRelaList);

    /**
     * 功能描述：批量查找
     *
     * @param idList
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    List<E01zcRela> queryE01ZcRelaList(List<String> idList);

    /**
     * 功能描述：当前登录用户电子档案查阅搜索
     *
     * @param userId,a0101
     * @return :
     * @author lutao
     * @date 2019/12/31
     */
    List<Map<String, Object>> queryListByA01AndText(String userId, String a0101, String deptName, Integer pageNum, Integer pageSize);

    /**
     * 功能描述：批量删除
     *
     * @param id
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    int deleteE01zcRela(String id);

    /**
     * 功能描述：获取所有未审批的数据
     *
     * @param
     * @return :
     * @author lutao
     * @date 2020/1/6
     */
    List<E01zcRela> quertNoApproval();

    /**
     * 功能描述：电子档案查阅审批
     *
     * @param id:批次id,ids:子表的uuid
     * @return :
     * @author lutao
     * @date 2020/1/6
     */
    int ApprovalElec(String id, List<String> idList);

    /**
     * 功能描述：判断查阅材料是否有权限查看
     *
     * @param id：材料id
     * @param a01Id：干部id
     * @return :
     * @author lutao
     * @date 2020/1/8
     */
    List<E01zcRela> queryArchiveInTime(String id, String a01Id);

    /**
     * 功能描述：数据修改
     *
     * @param e01zcRela
     * @return :
     * @author lutao
     * @date 2020/1/10
     */
    int updateE01zcRela(E01zcRela e01zcRela);

    /**
     * 功能描述：提供档案浏览页面材料信息
     *
     * @param
     * @return :
     * @author lutao
     * @date 2020/1/16
     */
    List<E01z1> getE01zcRelaByE01z1Id(String a01Id);

    /**
     * 功能描述：判断当前材料是否存在权限
     *
     * @param
     * @return :
     * @author lutao
     * @date 2020/1/16
     */
    boolean judgeAthor(String e01z1Id);

    /**
     * 功能描述：
     *
     * @param
     * @return :
     * @author lutao
     * @date 2020/1/16
     */
    List<String> getA01Author();

    /**
     * 功能描述：获取当前批次下所有未通过审批的数据
     *
     * @param id(E01zcId)
     * @return :
     * @author lutao
     * @date 2020/1/17
     */
    List<E01zcRela> queryE01zcRelaListNotApprov(String id);

    List<Map<String, Object>> queryE01zcRelaTreeByE01zcId(String e01zcId);

    List<Map<String, Object>> queryE01zcRelaTreeByE01zcRelaId(String e01zcRelaId);
}


