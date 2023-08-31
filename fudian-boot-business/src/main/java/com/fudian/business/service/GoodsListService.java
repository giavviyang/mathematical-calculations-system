package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.GoodsList;
import com.fudian.business.vo.Goods;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * @author xuchenghong
 */

public interface GoodsListService extends IService<GoodsList> {

    /**
     * 分页查询商品
     *
     * @param goodsList
     * @return
     */
    CommonGridResult pagingSelectGoodsList(GoodsList goodsList);

    /**
     * 查询商品
     *
     * @param goodsList
     * @return
     */
    List<GoodsList> selectGoodsList(GoodsList goodsList);
    /**
     * 查询商品
     *
     * @return
     */
    List<GoodsList> selectGoodsList();

    /**
     * 根据id查询商品
     *
     * @return
     */
    GoodsList selectGoodsListByGoodsId(String goodsId);
    /**
     * 根据id查询商品
     *
     * @return
     */
    List<GoodsList> selectGoodsListByGoodsId(List<String> goodsIds);

    /**
     * 根据学生id查询学生的商品
     *
     * @return
     */
    List<GoodsList> selectGoodsListByStudentId(String studentId);

    /**
     * 添加商品
     *
     * @param goodsList
     * @return
     */
    int insertGoodsList(GoodsList goodsList);

    /**
     * 修改商品
     *
     * @param goodsList
     * @return
     */
    int updateGoodsListById(GoodsList goodsList);

    /**
     * 批量删除商品
     *
     * @param ids
     * @return
     */
    int deleteGoodsList(List<String> ids);

    CommonResult deleteGoodsList(String ids);

    /*
    * 多条件获取商品列表
    * */
    CommonGridResult getGoodsList(GoodsList goodsList);

    CommonResult updateGoodsListAndFile(Map<String,Object> map, MultipartFile file);

    CommonResult insertGoodsListAndFile(Map<String,Object> map, MultipartFile file);
}
