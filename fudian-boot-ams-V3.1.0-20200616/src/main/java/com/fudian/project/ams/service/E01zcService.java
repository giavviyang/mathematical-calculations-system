package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.ams.pojo.E01zc;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface E01zcService extends IService<E01zc> {


    int updateBatch(List<E01zc> list);

    int batchInsert(List<E01zc> list);

    int insertOrUpdate(E01zc record);

    int insertOrUpdateSelective(E01zc record);

    /**
     * 功能描述：插入数据
     *
     * @param uuid
     * @return : String id
     * @author lutao
     * @date 2019/12/30
     */
    String insertE01zc(String uuid, SysUser user);

    /**
     * 功能描述：获取电子档案查阅申请记录
     *
     * @param id-a01Id
     * @return :
     * @author lutao
     * @date 2019/12/27
     */
    List<E01zc> queryE01zcList(E01zc e01zc, String id, Integer status, Integer target);

    List<E01zc> queryE01zcList(String id);
    /**
     * 功能描述：根据输入条件模糊查询电子档案查阅的申请记录
     *
     * @param text
     * @return :
     * @author lutao
     * @date 2019/12/27
     */
    List<E01zc> queryE01z8ListBytext(String text);

    /**
     * 功能描述：判断当前登录用户是否有查阅材料的权限
     *
     * @param id-a01Id
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    List<E01zc> queryE01zcListInTime(String id);

    /**
     * 功能描述：档案浏览获取所有符合条件的材料
     *
     * @param
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    List<E01z1> queryE01z1ListByCondition(String a01Id, String fileType, String text);

    /**
     * 功能描述：查询当前全宗下所有的电子档案查阅
     *
     * @param
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    List<E01zc> queryE01zcList();

    /**
     * 功能描述：根据id删除数据
     *
     * @param id
     * @return :
     * @author lutao
     * @date 2019/12/30insertOrUpdateSelective(record);
     */
    int deleteE01zc(String id);

    /**
     * 功能描述：修改
     *
     * @param
     * @return :
     * @author lutao
     * @date 2019/12/30
     */
    int updateE01zc(E01zc e01zc);

    /**
     * 功能描述：查阅电子档案台账搜索
     *
     * @param text
     * @return :
     * @author lutao
     * @date 2019/12/31
     */
    List<Map<String, Object>> queryE01zcAndE01zcRela(String deptName, String a0101, Integer pageNum, Integer pageSize);

    /**
     * 功能描述：
     *
     * @param
     * @return :
     * @author lutao
     * @date 2020/1/2
     */
    CommonGridResult queryElecByPage(E01zc e01zc);

    /**
     * 功能描述：批量查数据
     *
     * @return :
     * @author lutao
     * @date 2020/1/6
     */
    List<E01zc> queryE01zcList(List<String> ids);

    boolean approVal(String jsonData) throws ParseException;

    String addE01zc(E01zc e01zc,String jsonData);

    List<E01zc> queryNoApproList();

    /**
     * 统计电子查阅
     * @return
     */
    List<Map<String,Object>> summaryE01zc();

}







