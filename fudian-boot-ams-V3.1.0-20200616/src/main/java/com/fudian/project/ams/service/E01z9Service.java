package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.E01z9;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface E01z9Service extends IService<E01z9> {

    int updateBatch(List<E01z9> list);

    int batchInsert(List<E01z9> list);

    int insertOrUpdate(E01z9 record);

    int insertOrUpdateSelective(E01z9 record);

    /**
     * 统计实体借阅
     * @return
     */
    List<Map<String,Object>> summaryE01z9();

    /**
     * 功能描述：查询当前登录用户所以的档案借阅记录
     *
     * @param id
     * @return :
     * @author lutao
     * @date 2019/12/27
     */
    List<E01z9> queryE01z9List(String id);

    /**
     * 功能描述：单条数据插入
     *
     * @param userId
     * @return : String id  将父表的id返回
     * @author lutao
     * @date 2019/12/30
     */
    String insertE01z9(String uuid, String userId);

    /**
     * 功能描述：获取当前全宗下所有的档案借阅记录
     *
     * @param
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    List<E01z9> queryE01z9List();

    /**
     * 功能描述：根据id删除
     *
     * @param
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    int deleteE01z9ById(String id);

    /**
     * 功能描述：修改
     *
     * @param e01z9
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    int updateE01z9(E01z9 e01z9);

    /**
     * 功能描述：档案借阅台账搜索
     *
     * @param
     * @return :
     * @author lutao
     * @date 2019/12/31
     */
    List<Map<String, Object>> queryE01z9AndE01z9Rela(String deptName, String a0101, Integer pageNum, Integer pageSize);

    /**
     * 功能描述：批量查数据
     *
     * @return :
     * @author lutao
     * @date 2020/1/6
     */
    List<E01z9> queryE01z9List(List<String> ids);

    int updateBatchSelective(List<E01z9> list);



    /**
     * 实体档案借阅记录查询（广东海关修改）
     * isAll 如果存在则查询全部，如果不存在则查询当前用户
     * @param e01z9
     */
    CommonGridResult getE01z9sByStates(E01z9 e01z9, String isAll);

    /**
     * 到期续查
     *
     * @param condition
     * @return
     */
    CommonResult archiveRenewalOfMaturity(String condition);


    /**
     * 获取未审批的数据
     * */
    List<E01z9> queryNoAppro();

    /**
     * 获取借阅已到期但档案未归还的数据
     * */
    List<E01z9> queryNoReturn();
}











