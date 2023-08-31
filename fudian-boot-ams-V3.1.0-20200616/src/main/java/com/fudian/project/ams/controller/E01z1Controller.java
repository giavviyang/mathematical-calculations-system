package com.fudian.project.ams.controller;

import com.fudian.api.ams.pojo.A01;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.threadLocal.DataPermissionConfig;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.data.DataStoreWrapper;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.ams.pojo.E01z2;
import com.fudian.project.ams.service.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 档案材料录入控制器层
 * @ClassName E01z1Controller
 * @Author liang
 * @Date 2020/01/02 19:05
 * @Version 1.0.0
 **/

@RestController
@RequestMapping("e01z1")
@OperTitle("材料接收")
public class E01z1Controller {


    @Autowired
    private E01z1Service e01z1Service;

    @Autowired
    private WfTaskE01z1Service wfTaskE01z1Service;

    @Autowired
    private WfTaskService wfTaskService;

    @Autowired
    private E01z2Service e01z2Service;

    @Autowired
    private DataStoreWrapper redisCache;
    /**
     * 档案接收导入档案目录
     * 解析Excel并保存E01z1
     * 20200317 hwy
     *
     * @param part 档案目录Excel
     * @param a01Id 人员信息id
     * @return
     */
    @PostMapping("insertE01z1s")
    @OperInfo(info = "档案接收导入档案目录", params = {"a01Id"})
    public CommonResult insertE01z1s(MultipartFile part, String a01Id){
        try {
            e01z1Service.parseAndInsert(part,a01Id);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(500, "档案目录导入失败");
        }
        CommonResult commonResult = CommonResult.success("档案目录导入成功", null);
        return commonResult;
    }



    /**
     * 档案接收材料录入页面左侧grid
     * @param part excel文件
     * @return
     */
    @PostMapping("queryE01z1Grid")
    @OperInfo(info = "档案接收材料录入页面左侧grid", params = {"a01Id","isClassIfy"})
    public CommonResult queryE01z1Grid(MultipartFile part, String a01Id, String isClassIfy){
        if(isClassIfy!=null&&isClassIfy.equals("1")){
            DataPermissionConfig.isClassIfy(true);
        }
        CommonResult commonResult =e01z1Service.getE01z1Grid(part,a01Id);
        return commonResult;
    }

    /**
     * 取年份
     * @return
     */
    @GetMapping("queryYear")
    @OperInfo(info = "查询年份")
    public ResponseEntity<CommonResult> queryYear(){
        List<E01z2> list = e01z2Service.list();
        List<String> years = new ArrayList<>();
        list.stream().forEach(x->{
            String year = x.getE01z201().substring(0,4);
            if(!years.contains(year)){
                years.add(year);
            }
        });
        List<Map<String,String>> yearList = new ArrayList<>();
        years.stream().forEach(x->{
            Map<String,String> map = new HashMap<>();
            map.put("id",x);
            map.put("label",x + "年度");
            yearList.add(map);
        });
        List<Map<String,String>> sortedList = yearList.stream().sorted(
                (o1,o2)->Integer.parseInt(o2.get("id"))-Integer.parseInt(o1.get("id")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(CommonResult.success(sortedList));
    }

    /**
     * 获取材料接收台账列表
     * @param e01z2
     * @return
     */
    @GetMapping("queryE01z2List")
    @OperInfo(info="查询材料接收台账，id{}",params={"id"})
    public CommonGridResult queryE01z2List(E01z2 e01z2){
        return e01z2Service.queryE01z2s(e01z2);
    }

    @GetMapping("queryE01z1ListByA01AndE01z101")
    @OperInfo(info = "查询材料列表", params = {"a01Id","e01z101"})
    public CommonResult queryE01z1ListByA01AndE01a101(String a01Id, String e01z101){
        List<E01z1> e01z1s = e01z1Service.queryE01z1ListByA01AndE01z101(a01Id,e01z101);
        return CommonResult.success(e01z1s);
    }


    /**
     * 删除材料接收台账信息
     * @param e01z2Ids 多个材料接收记录id
     * @return
     */
    @DeleteMapping("deleteE01z2s")
    @OperInfo(info="删除材料接收台账，ids{}",params={"e01z2Ids"})
    public CommonResult deleteE01z2s(String e01z2Ids){
        int i=e01z2Service.deleteE01z2s(e01z2Ids);
        if(i>0){
            return CommonResult.success("删除成功");
        }else{
            return CommonResult.error("400","删除失败");
        }
    }

    /**
     * 根据用户id和材料类型查询材料
     * @param userIds
     * @param materialIds
     * @return
     */
    @GetMapping("queryE01z1List")
    @OperInfo(info = "根据用户id和材料类型查询材料", params = {"userIds","materialIds"})
    public CommonResult queryE01z1List(String userIds, String materialIds){

        CommonResult commonResult = new CommonResult();
        List<E01z1> list= e01z1Service.queryE01z1List(userIds,materialIds);
        commonResult.setCode(200);
        commonResult.setData(list);
        return commonResult;
    }

    /**
     * 根据材料id查询材料目录
     * @param id
     * @return
     */
    @GetMapping("selectById")
    @OperInfo(info = "根据材料id查询材料目录", params = {"id"})
    public CommonResult selectById(String id){

        CommonResult commonResult = new CommonResult();
        try {
            if (id == null || "".equals(id)) {
                commonResult.setCode(500);
                commonResult.setMsg("id不能为空");
                return commonResult;
            }
            E01z1 e01z1 = e01z1Service.selectById(id);
            commonResult.setCode(200);
            commonResult.setData(e01z1);
            commonResult.setMsg("查询成功");
            return commonResult;
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setCode(500);
            commonResult.setMsg("查询失败");
            return commonResult;
        }
    }

    @GetMapping("queryE01z1ListByFlag")
    @OperInfo(info = "查询材料列表", params = {"e01z1","a0101"})
    public CommonGridResult queryE01z1ListByFlag(E01z1 e01z1, String a0101){
        CommonGridResult result=null;
        result=e01z1Service.queryListByFlag(e01z1,a0101);
        return result;
    }

    @GetMapping("queryE01z1ListByName")
    @OperInfo(info = "查询材料列表", params = {"e01z111a","a0101","pageNum","pageSize"})
    public CommonGridResult queryE01z1ListByName(String e01z111a, String a0101, Integer pageNum, Integer pageSize){
        return e01z1Service.queryE01z1List(e01z111a, a0101, pageNum, pageSize);
    }

    @DeleteMapping("deleteById")
    @OperInfo(info = "删除材料", params = {"ids"})
    public CommonResult deleteById(String[] ids){
        CommonResult result=null;
        List<String> idList=Arrays.asList(ids);
        System.out.println(idList);
        result=e01z1Service.removoByIds(idList);
        return result;
    }

    @PutMapping("returnPlace")
    @OperInfo(info = "修改材料", params = {"ids"})
    public CommonResult returnPlace(String ids){
        CommonResult result=null;
        if (ids == null || "".equals(ids)) {
            return new CommonResult(500, "修改失败");
        }
        String[] split = ids.split(",");
        List<String> idList=Arrays.asList(split);
        result=e01z1Service.updateFlagByIds(idList);
        return result;
    }


    /**
     * 电子档更新
     * @return
     */
    @PutMapping("updateE01z1")
    @OperInfo(info = "电子档更新", params = {"e01z1"})
    public CommonResult updateE01z1(E01z1 e01z1){
        try{
            e01z1Service.updateById(e01z1);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(500,"修改失败");
        }
        return CommonResult.success("修改成功");
    }


    /**
     * 新增一条档案材料并重新排序
     * @param e01z1
     * @return
     */
    @PostMapping("insertOneArchiveData")
    @OperInfo(info = "新增一条档案材料并重新排序", params = {"e01z1"})
    public CommonResult insertOneArchiveData(E01z1 e01z1){
        try{
            return e01z1Service.insertOneArchiveData(e01z1);
        }catch(Exception e) {
            e.printStackTrace();
            return CommonResult.error(500, "保存失败");
        }
    }

    /**
     * 调整分类
     * @param e01z1
     * @return
     */
    @PutMapping("moveClassify")
    @OperInfo(info = "调整分类", params = {"e01z1"})
    public CommonResult moveClassify(E01z1 e01z1){
        return e01z1Service.moveClassify(e01z1);
    }
}
