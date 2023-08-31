package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.E01z1Example;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface E01z1Service extends IService<E01z1>{


    boolean addE01z1AndImgByApi(E01z1 e01z1, MultipartFile[] files) throws IOException;

    long countByExample(E01z1Example example);

    int deleteByExample(E01z1Example example);

    List<E01z1> selectByExample(E01z1Example example);

    int updateByExampleSelective(E01z1 record, E01z1Example example);

    int updateByExample(E01z1 record, E01z1Example example);

    int updateBatch(List<E01z1> list);

    int batchInsert(List<E01z1> list);

    int insertOrUpdate(E01z1 record);

    int insertOrUpdateSelective(E01z1 record);

    /**
     * 功能描述：档案浏览-档案材料搜索
     * @author lutao
     * @param
     * @return :
     * @date    2019/12/31
     */
//    CommonGridResult queryE01z1byText(String a01Id, String a0101, String e01z111a, Integer pageNum, Integer pageSize);

    /**
     * 功能描述：获取十大类结构树
     * @author lutao
     * @param  a01Id
     * @param  b 材料目录名称是否携带序号
     * @return :
     * @date    2019/12/31
     */
    List<Map<String,Object>> queryTree(String a01Id,boolean b);

    /**
     * 解析档案目录并保存E01z1
     * @param file
     * @param a01Id
     * @return 保存结果
     */
    boolean parseAndInsert(MultipartFile file,String a01Id);



    List<E01z1> parsingExcel(MultipartFile port);

    CommonResult getE01z1Grid(MultipartFile file,String a01Id);

    /**
     * 查询最大序号
     * @param a01Id  干部基本信息id
     * @param type  材料类型
     * @return 数字化任务ID
     */
    int queryMaxIndex(String a01Id,String type);

    List<E01z1> queryE01z1List(String userIds, String materialIds);

    List<E01z1> queryE01z1ListByA01AndE01z101(String a01Id, String e01z101);


    /**
     * 获取用户已删除的材料(e01z1Flag==1)
     * @param e01z1
     * @return
     */
    CommonGridResult queryListByFlag(E01z1 e01z1, String a0101);

    CommonGridResult queryE01z1List(String e01z111a,String a0101,Integer pageNum,Integer pageSize);

    /**
     * 通过id批量删除
     * @param ids
     * @return
     */
    CommonResult removoByIds(List<String> ids);

    /**
     * 通过ids批量修改材料状态
     * @param ids
     * @return
     */
    CommonResult updateFlagByIds(List<String> ids);


    HSSFWorkbook makeE01z1sExcel(String a01Id);

    CommonResult insertOneArchiveData(E01z1 e01z1);

    CommonResult moveClassify(E01z1 e01z1);

    E01z1 selectById(String id);
}
