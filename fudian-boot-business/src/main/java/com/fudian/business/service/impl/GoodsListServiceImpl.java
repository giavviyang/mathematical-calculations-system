package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.GoodsListMapper;
import com.fudian.business.pojo.AppletFile;
import com.fudian.business.pojo.GoodsList;
import com.fudian.business.service.AppletFileService;
import com.fudian.business.service.GoodsListService;
import com.fudian.business.vo.Goods;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * @author xuchenghong
 */

@Service
public class GoodsListServiceImpl extends ServiceImpl<GoodsListMapper, GoodsList> implements GoodsListService {


    @Resource
    private GoodsListMapper goodsListMapper;

    @Autowired
    private AppletFileService appletFileService;

    /**
     * 分页查询商品
     *
     * @param goodsList
     * @return
     */
    @Override
    public CommonGridResult pagingSelectGoodsList(GoodsList goodsList) {
        LambdaQueryWrapper<GoodsList> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(goodsList.getPageNum(), goodsList.getPageSize());
        List<GoodsList> goodsLists = goodsListMapper.selectList(queryWrapper);

        Page page = (Page) goodsLists;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(goodsList.getPageNum(), goodsList.getPageSize(), total, goodsLists);
        return commonGridResult;
    }

    /**
     * 查询商品
     *
     * @param goodsList
     * @return
     */
    @Override
    public List<GoodsList> selectGoodsList(GoodsList goodsList) {
        LambdaQueryWrapper<GoodsList> queryWrapper = new LambdaQueryWrapper<>();

        List<GoodsList> goodsLists = goodsListMapper.selectList(queryWrapper);
        return goodsLists;
    }
    /**
     * 查询商品
     *
     * @return
     */
    @Override
    public List<GoodsList> selectGoodsList() {
        QueryWrapper<GoodsList> queryWrapper = new QueryWrapper<>();

        List<GoodsList> goodsLists = goodsListMapper.selectList(queryWrapper);
        return goodsLists;
    }
    /**
     * 根据id查询商品
     *
     * @return
     */
    @Override
    public GoodsList selectGoodsListByGoodsId(String goodsId) {

        GoodsList goodsList = goodsListMapper.selectById(goodsId);

        return goodsList;
    }
    /**
     * 根据id查询商品
     *
     * @return
     */
    @Override
    public List<GoodsList> selectGoodsListByGoodsId(List<String> goodsIds) {

        List<GoodsList> goodsLists = goodsListMapper.selectBatchIds(goodsIds);

        return goodsLists;
    }
    /**
     * 根据学生id查询学生的商品
     *
     * @return
     */
    @Override
    public List<GoodsList> selectGoodsListByStudentId(String studentId) {

        List<GoodsList> goodsLists = goodsListMapper.selectGoodsListByStudentId(studentId);

        return goodsLists;
    }

    /**
     * 添加商品
     *
     * @param goodsList
     * @return
     */
    @Override
    public int insertGoodsList(GoodsList goodsList) {
        String uuid = UUID.randomUUID().toString();
        goodsList.setGoodsId(uuid);
        int insert = goodsListMapper.insert(goodsList);
        return insert;
    }

    /**
     * 修改商品
     *
     * @param goodsList
     * @return
     */
    @Override
    public int updateGoodsListById(GoodsList goodsList) {

        int i = goodsListMapper.updateById(goodsList);
        return i;
    }

    /**
     * 批量删除商品
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteGoodsList(List<String> ids) {
        int insert = goodsListMapper.deleteBatchIds(ids);
        return insert;
    }

    @Transactional
    @Override
    public CommonResult deleteGoodsList(String ids) {
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        LambdaQueryWrapper<GoodsList> goodsListLambdaQueryWrapper = new LambdaQueryWrapper<>();
        goodsListLambdaQueryWrapper.in(GoodsList::getGoodsId,strings);
        List<GoodsList> list = this.list(goodsListLambdaQueryWrapper);
        List<String> fileIds = list.stream().map(e -> e.getGoodsImge()).distinct().collect(Collectors.toList());
        this.removeByIds(strings);
        appletFileService.removeByIds(fileIds);
        return CommonResult.success();
    }

    @Override
    public CommonGridResult getGoodsList(GoodsList goodsList) {
        CommonGridResult commonGridResult = new CommonGridResult();
        Page page = PageHelper.startPage(goodsList.getPageNum(), goodsList.getPageSize());
        baseMapper.getGoodsList(goodsList);
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setRows(page.getResult());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setPageSize(page.getPageSize());
        return commonGridResult;
    }

    @Override
    public CommonResult insertGoodsListAndFile(Map<String, Object> map, MultipartFile file) {
        String goodsId = StringUtils.uuid();
        String goodsName = map.get("goodsName").toString();
        Integer goodsCoins = Integer.valueOf(map.get("goodsCoins").toString());
        Integer goodsNum = Integer.valueOf(map.get("goodsNum").toString());
        String goodsDescribe = map.get("goodsDescribe").toString();

        String fileId = StringUtils.uuid();
        String fileType = file.getContentType();
        byte[] fileData = null;
        try {
            fileData = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoodsList goodsList = new GoodsList();
        goodsList.setGoodsId(goodsId);
        goodsList.setGoodsName(goodsName);
        goodsList.setGoodsCoins(goodsCoins);
        goodsList.setGoodsNum(goodsNum);
        goodsList.setGoodsImge(fileId);
        goodsList.setGoodsDescribe(goodsDescribe);
        AppletFile appletFile = new AppletFile();
        appletFile.setId(fileId);
        appletFile.setFileData(fileData);
        appletFile.setFileFormat(fileType);
        boolean save = this.save(goodsList);
        boolean save1 = appletFileService.save(appletFile);
        if (save&&save1){
            return CommonResult.success();
        }
        return CommonResult.error();
    }

    @Transactional
    @Override
    public CommonResult updateGoodsListAndFile(Map<String, Object> map, MultipartFile file) {
        String goodsId = map.get("goodsId").toString();
        String goodsName = map.get("goodsName").toString();
        Integer goodsCoins = Integer.valueOf(map.get("goodsCoins").toString());
        Integer goodsNum = Integer.valueOf(map.get("goodsNum").toString());
        String goodsDescribe = map.get("goodsDescribe").toString();
        String goodsImge = map.get("goodsImge").toString();
        String fileId = map.get("fileId").toString();
        GoodsList goodsList = new GoodsList();
        goodsList.setGoodsId(goodsId);
        goodsList.setGoodsName(goodsName);
        goodsList.setGoodsCoins(goodsCoins);
        goodsList.setGoodsNum(goodsNum);
        goodsList.setGoodsImge(goodsImge);
        goodsList.setGoodsDescribe(goodsDescribe);
        AppletFile appletFile = new AppletFile();
        appletFile.setId(fileId);
        if (file!=null){
            try {
                appletFile.setFileData(file.getBytes());
                appletFile.setFileFormat(file.getContentType());
                appletFileService.updateById(appletFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boolean b = this.updateById(goodsList);
        return CommonResult.success();
    }
}
