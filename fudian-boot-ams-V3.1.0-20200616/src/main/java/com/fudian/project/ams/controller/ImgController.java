package com.fudian.project.ams.controller;

import com.fudian.common.constant.HttpStatus;
import com.fudian.common.enumns.RedisEnum;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.data.DataStoreWrapper;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.ams.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/14 16:51
 * @Desc:
 */
@RequestMapping("img")
@RestController
@OperTitle("图片处理")
public class ImgController {

    private Map<String,String> map = new ConcurrentHashMap<>();

    @Autowired
    ImgService imgService;

    @Autowired
    DataStoreWrapper redisCache;

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/16 10:37
    * @Return: 获取某一材料下的所有图像信息
    * @Param:
    */
    @GetMapping("queryImgInfoByE01z1Id/{e01z1Id}")
    @OperInfo(info = "查询图片", params = {"e01z1Id"})
    public ResponseEntity<CommonResult> queryImgInfoByE01z1Id(@PathVariable("e01z1Id") String e01z1Id){
        return ResponseEntity.ok(CommonResult.success(imgService.querySysFileByE01z1Id(e01z1Id)));
    }


    /**
     * @Author: wangyuzhuo
     * @Date: 2020/3/16 10:37
     * @Return: 删除某一材料下图像数据
     * @Param:
     */
    @DeleteMapping("deleteImgInfoByE01z1Id")
    @OperInfo(info = "删除图像", params = {"e01z1Id","index","type"})
    public ResponseEntity<CommonResult> deleteImgInfoByE01z1Id(String e01z1Id, String index, Integer type){

        return ResponseEntity.ok(CommonResult.success(imgService.deleteSysFileByE01z1Id(e01z1Id,index,type)));
    }

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/16 18:43
    * @Return: 返回pdf流
    * @Param:
    */
    @GetMapping("createImgPdfBy/{e01z1Id}")
    @OperInfo(info = "下载pdf", params = {"e01z1Id"})
    public ResponseEntity<CommonResult> printPdf(@PathVariable("e01z1Id") String e01z1Id) throws IOException {
//        HttpHeaders headers = new HttpHeaders();
//        //设置响应头为查看pdf
//        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        byte[] bytes = null;
        try{
            String id = StringUtils.uuid();
            bytes = imgService.createPdfBytes(e01z1Id);
            if(bytes == null){
                return ResponseEntity.ok(CommonResult.error("电子文件不存在"));
            }
            redisCache.setCacheObject(id,bytes, RedisEnum.SHORT);
            return ResponseEntity.ok(CommonResult.success("",id));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(CommonResult.error("服务器错误"));
        }
    }




    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/19 16:25
    * @Return: 更新电子文件序号
    * @Param:
    */
    @PutMapping("updateFileIndex")
    @OperInfo(info = "更新电子文件序号", params = {"index","fileId"})
    public ResponseEntity<CommonResult> updateFileIndex(@RequestParam("index")Integer index, @RequestParam("fileId")String fileId) throws IOException {



        boolean b = imgService.updateFileIndex(fileId,index);
        if(b){
            return ResponseEntity.ok(CommonResult.success());
        }else{
            return ResponseEntity.ok(CommonResult.error("操作失败"));
        }
    }

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/20 17:08
    * @Return: 上传电子文件
    * @Param:
    */
    @PostMapping("fileUpload")
    @OperInfo(info = "上传电子文件", params = {"index","a01Id","e01z1Id"})
    public ResponseEntity<CommonResult> fileUpload(@RequestParam("uploadFile") MultipartFile uploadFile, @RequestParam("index")Integer index, @RequestParam("a01Id")String a01Id, @RequestParam("e01z1Id")String e01z1Id) throws IOException {

        if(!checkParams(uploadFile, index, a01Id, e01z1Id)){
            return ResponseEntity.ok(CommonResult.error("请保证传输的参数正确"));
        }

        boolean b = imgService.fileUpload(uploadFile,index,a01Id,e01z1Id);
        if(b){
            return ResponseEntity.ok(CommonResult.success());
        }else{
            return ResponseEntity.ok(CommonResult.error("操作失败"));
        }
    }


    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/20 17:07
    * @Return: 删除服务器文件
    * @Param:
    */
    @DeleteMapping("deleteFileByFileId")
    @OperInfo(info = "删除服务器文件", params = {"fileId"})
    public ResponseEntity<CommonResult> deleteFileByFileId(@RequestParam("fileId")String fileId) throws IOException {



        boolean b = imgService.deleteFileByFileId(fileId);
        if(b){
            return ResponseEntity.ok(CommonResult.success());
        }else{
            return ResponseEntity.ok(CommonResult.error("操作失败"));
        }
    }

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/20 17:07
    * @Return: 存放上传参数
    * @Param:
    */
    @PostMapping("putParamsByredis")
    @OperInfo(info = "存放上传参数", params = {"data"})
    public ResponseEntity<CommonResult> putParamsByredis(@RequestBody String data){
        String id = StringUtils.uuid();
        map.put(id,data);
        return ResponseEntity.ok(CommonResult.success("",id));
    }

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/20 17:07
    * @Return: 获取上传参数
    * @Param:
    */
    @GetMapping("getParamsByredis")
    @OperInfo(info = "获取上传参数", params = {"id"})
    public ResponseEntity<CommonResult> getParamsByredis(@RequestParam("id")String id){
        String params = map.get(id);
        map.remove(id);
        return ResponseEntity.ok(CommonResult.success("",params));
    }

    private boolean checkParams(MultipartFile uploadFile,Integer index,String a01Id,String e01z1Id){
//        boolean b = StringUtils.isNotEmpty(e01z1Id);
//        b = StringUtils.isNotEmpty(a01Id);
//        b = StringUtils.isNotNull(uploadFile);
        return StringUtils.isNotEmpty(e01z1Id)&& StringUtils.isNotEmpty(a01Id)&& StringUtils.isNotNull(uploadFile)&& StringUtils.isNotNull(index);
    }
}
