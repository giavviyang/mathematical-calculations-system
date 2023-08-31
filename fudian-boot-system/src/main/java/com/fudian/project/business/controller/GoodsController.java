package com.fudian.project.business.controller;

import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.WatermarkConfig;
import com.fudian.business.pojo.AppletFile;
import com.fudian.business.pojo.GoodsList;
import com.fudian.business.pojo.StudentGoodsRelation;
import com.fudian.business.service.GoodsListService;
import com.fudian.business.service.StudentGoodsRelationService;
import com.fudian.business.service.StudentsService;
import com.fudian.business.vo.Goods;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 小程序商城
 *
 * @author xuchenghong
 */
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*",origins = "*")
@RestController
@RequestMapping(value = "applet/goods")
@OperTitle("商城管理")
public class GoodsController {

    @Autowired
    private GoodsListService goodsListService;
    @Autowired
    private StudentGoodsRelationService studentGoodsRelationService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private StudentsService studentsService;

    /**
     * 查询商品
     *
     * @return
     */
    @PostMapping("queryGoods")
    public CommonResult<?> queryGoods() {
        try {
            HttpServletRequest request = ServletUtils.getRequest();
            //根据token查询学生id
            Students students = tokenService.getLoginUser(request).getStudent();
            //根据学生id查询学生商品id
            List<String> goodsIds = studentGoodsRelationService.selectByStudentId(students.getStudentId());

            List<GoodsList> goodsLists = goodsListService.selectGoodsList();
            ArrayList<Goods> list = new ArrayList<>();
            for (GoodsList goodsList : goodsLists) {
                Goods goods = new Goods();
                goods.setGoodsId(goodsList.getGoodsId());

                String serverName = request.getServerName();
                int serverPort = request.getServerPort();

                if (goodsList.getGoodsImge() != null) {
                    String ip = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + goodsList.getGoodsImge();
                    goods.setGoodsImge(ip);
                }
                goods.setGoodsName(goodsList.getGoodsName());
                goods.setGoodsDescribe(goodsList.getGoodsDescribe());
                for (String goodsId : goodsIds) {
                    if (goods.getGoodsId().equals(goodsId)) {
                        goods.setPurchaseStatus(1);
                    }
                }
                goods.setGoodsCoins(goodsList.getGoodsCoins());
                goods.setGoodsType(goodsList.getGoodsType());

                list.add(goods);
            }
            return CommonResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }

    /**
     * 购买商品
     *
     * @return
     */
    @PostMapping("purchaseGoods")
    public CommonResult<?> purchaseGoods(String goodsId) {
        int studentUpdate = 0;
        int goodsUpdate = 0;
        int CoinsNum = 0;
        Students students = null;
        try {
            //根据token获取学生id  1234567890
            Students student = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();
            students = studentsService.selectStudentsByStudentId(student.getStudentId());
            //查询商品价格
            CoinsNum = students.getCoinsNum();
            GoodsList goodsList = goodsListService.selectGoodsListByGoodsId(goodsId);
            if (students.getCoinsNum() < goodsList.getGoodsCoins()) {
                return CommonResult.success("购买失败！金币不足");
            }
            //减少金币数量
            students.setCoinsNum(students.getCoinsNum() - goodsList.getGoodsCoins());
            studentUpdate = studentsService.updateStudentByStudentId(students);

            StudentGoodsRelation studentGoodsRelation = new StudentGoodsRelation();
            String uuid = UUID.randomUUID().toString();
            studentGoodsRelation.setId(uuid);
            studentGoodsRelation.setStudentId(students.getStudentId());
            studentGoodsRelation.setGoodsId(goodsId);
            goodsUpdate = studentGoodsRelationService.insertStudentGoodsRelation(studentGoodsRelation);

            return CommonResult.success("购买成功！");
        } catch (Exception e) {
            if (studentUpdate == 1 && goodsUpdate == 0) {
                students.setCoinsNum(CoinsNum);
                studentsService.updateStudentByStudentId(students);
            }
            e.printStackTrace();
            return CommonResult.success("购买失败！");
        }
    }

    @GetMapping("getGoodsList")
    @OperInfo(info = "获取商品列表")
    public CommonGridResult getGoodsList(GoodsList goodsList){
        return goodsListService.getGoodsList(goodsList);
    }


    /*
    * 新增商品信息
    * */
    @PostMapping("insertGoodsList")
    @OperInfo(info = "新增商品信息")
    public CommonResult insertGoodsList(@RequestParam Map<String,Object> map, MultipartFile file){
        return goodsListService.insertGoodsListAndFile(map,file);
    }

    /*
    *修改商品信息
    * */
    @PostMapping("updateGoodsList")
    @OperInfo(info = "修改商品信息")
    public CommonResult updateGoodsList(@RequestParam Map<String,Object> map, MultipartFile file){
        return goodsListService.updateGoodsListAndFile(map,file);
    }

    /*
     *修改商品信息
     * */
    @PostMapping("updateGoodsListTwo")
    @OperInfo(info = "修改商品信息")
    public CommonResult updateGoodsListTwo(@RequestBody Map<String,Object> map, MultipartFile file){
        return goodsListService.updateGoodsListAndFile(map,file);
    }

    /*根据商品ids删除商品*/
    @DeleteMapping("deleteGoodsListByIds")
    @OperInfo(info = "根据商品ids删除商品")
    public CommonResult deleteGoodsListByIds(String ids){
        return goodsListService.deleteGoodsList(ids);
    }

}
