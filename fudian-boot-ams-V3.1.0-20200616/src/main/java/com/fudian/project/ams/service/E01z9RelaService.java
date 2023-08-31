package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.E01z9Rela;

import java.util.List;
import java.util.Map;

public interface E01z9RelaService extends IService<E01z9Rela>{

    int updateBatch(List<E01z9Rela> list);

    int batchInsert(List<E01z9Rela> list);

    int insertOrUpdate(E01z9Rela record);

    int insertOrUpdateSelective(E01z9Rela record);

    /**
     * 功能描述：一个批次下所有的数据
     * @author lutao
     * @param  e01z9Id
     * @return :
     * @date    2019/12/27
     */
    List<E01z9Rela> queryE01z9List(String e01z9Id);

    /**
     * 功能描述：档案借阅反馈填写
     * @author lutao
     * @param
     * @return :
     * @date    2019/12/27
     */
    int updateE01z9RelaById(E01z9Rela e01z9Rela);

    /**
     * 功能描述：档案借阅-干部人事档案登记表填写
     * @author lutao
     * @param
     * @return :
     * @date    2019/12/30
     */
    int updateE01z9Rela(E01z9Rela e01z9Rela);

    /**
     * 功能描述：批量插入数据
     * @author lutao
     * @param
     * @return :
     * @date    2019/12/30
     */
    int insertE01z9RelaList(String id,List<E01z9Rela> e01z9Relas);

    /**
     * 功能描述：根据关联id删除数据
     * @author lutao
     * @param  id
     * @return :
     * @date    2019/12/30
     */
    int deleteE01z9Rela(String id);

    /**
     * 功能描述：当前登录用户档案借阅历史搜索
     * @author lutao
     * @param  userId,a0101
     * @return :
     * @date    2019/12/31
     */
    List<Map<String,Object>> queryListByA01AndText(String userId, String a0101,String deptName,Integer pageNum,Integer paegSize);

    /**
     * 功能描述：获取所有未审批的数据
     * @author lutao
     * @param
     * @return :
     * @date    2020/1/6
     */
    List<E01z9Rela> quertNoApproval();

    /**
     * 功能描述：档案借阅审批
     * @author lutao
     * @param id:批次id,ids:子表的uuid
     * @return :
     * @date    2020/1/6
     */
    int ApprovalBorrow(String id,List<String> idList);

    /**
     * 功能描述：数据修改
     * @author lutao
     * @param  e01z9Rela
     * @return :
     * @date    2020/1/10
     */
    int updateE01z9RelaByBean(E01z9Rela e01z9Rela);

    /**
     * 功能描述：获取当前批次下所有未通过审批的数据
     * @author lutao
     * @param
     * @return :
     * @date    2020/1/17
     */
    List<E01z9Rela> queryE01z9ListNotApprov(String e01z9Id);

    /**
     * 根据批次id获取所有详细信息
     * @param id
     * @return
     */
    CommonResult queryE01z9RelasByE01z9Id(String id);
}
