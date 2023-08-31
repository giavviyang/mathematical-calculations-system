package com.fudian.project.ams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.system.pojo.SysFile;
import com.fudian.api.system.pojo.WatermarkConfig;
import com.fudian.api.system.service.SysFileApi;
import com.fudian.api.system.service.WatermarkConfigApi;
import com.fudian.common.pdf.ParagraphData;
import com.fudian.common.pdf.PdfExport;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.filestorage.controller.FileStorageController;
import com.fudian.filestorage.factory.ImgDESFactory;
import com.fudian.filestorage.service.ImgDESService;
import com.fudian.project.ams.service.E01z1Service;
import com.fudian.project.ams.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/16 10:32
 * @Desc:
 */
@Service
public class ImgServiceImpl implements ImgService {

    public static final int DELETE_ALL = 0;

    @Autowired
    FileStorageController fileStorageController;

    @Autowired
    WatermarkConfigApi watermarkConfigService;
    @Autowired
    SysFileApi sysFileService;

    @Autowired
    E01z1Service e01z1Service;



    /**
     * 存储微服务
     */
    @Autowired
    @Qualifier("fileStorageController")
    private FileStorageController fileStorageClient;

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/16 10:35
    * @Return: 获取材料下所有的图片信息
    * @Param:
    */
    @Override
    public List<SysFile> querySysFileByE01z1Id(String e01z1Id) {
        LambdaQueryWrapper<SysFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysFile::getBindingId,e01z1Id);
        lambdaQueryWrapper.orderByAsc(SysFile::getFileIndex);
        return sysFileService.list(lambdaQueryWrapper);
    }

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/16 11:02
    * @Return: 根据条件删除电子文件信息
    * @Param:
    */
    @Override
    public boolean deleteSysFileByE01z1Id(String e01z1Id, String index, Integer type) {
        LambdaQueryWrapper<SysFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysFile::getBindingId,e01z1Id);

        if(type != ImgServiceImpl.DELETE_ALL){
            String [] indexs = index.split(",");
            List<String> indexList= Arrays.asList(indexs);
            lambdaQueryWrapper.in(SysFile::getFileIndex,indexList);
        }

        //执行删除
        this.sysFileService.remove(lambdaQueryWrapper);

        //重新排序
        LambdaQueryWrapper<SysFile> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(SysFile::getBindingId,e01z1Id);
        lambdaQueryWrapper2.orderByAsc(SysFile::getFileIndex);
        List<SysFile> sysFileList = sysFileService.list(lambdaQueryWrapper2);
        if(sysFileList.size()>0){
            for (int i = 0;i<sysFileList.size();i++){
                sysFileList.get(i).setFileIndex(i + 1);
            }
            sysFileService.updateBatchById(sysFileList);
        }

        return true;
    }

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/16 15:36
    * @Return: 将当前材料下的所有图片文件生成一个pdf
    * @Param:
    */
    @Override
    public byte[] createPdfBytes(String e01z1Id) throws IOException {
        LambdaQueryWrapper<SysFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysFile::getBindingId,e01z1Id);
        lambdaQueryWrapper.orderByAsc(SysFile::getFileIndex);
        List<SysFile> list = sysFileService.list(lambdaQueryWrapper);

        if(list.size() == 0){
            SysFile sysFile = sysFileService.getById(e01z1Id);
            if(sysFile!=null){
                list.add(sysFile);
            }

        }
        byte[][] bytes = new byte[list.size()][];

        if(list.size() == 0){
            return null;
        }

        for(int i = 0;i<list.size();i++){
            try{
                byte[] file = fileStorageClient.downFile(list.get(i).getFileSource());
                if (ImgDESFactory.isDES()) {
                    bytes[i] = ImgDESFactory.getInstance().decode(file);
                }
            }catch(Exception e){
                continue;
            }


        }
        E01z1 e01z1 = e01z1Service.getById(e01z1Id);
        if(e01z1 == null){
            e01z1 = e01z1Service.getById(list.get(0).getBindingId());
        }
        // 一个到处对象对应一个 pdf 文件
        PdfExport pdf = new PdfExport();
        // 设置文档大小，有效
        pdf.setPage("A4", false);
        // 图片水印, 注意修改路径，也可以将水印保存到数据库中
        pdf.setAttribute(e01z1.getE01z111a() == null?"":e01z1.getE01z111a(), "", "", "", "");
        // 一个段落一个对象
        ArrayList<Object> data = new ArrayList<Object>();
        for(byte[] bytes1:bytes){
            ParagraphData paragraphData = new ParagraphData();
            paragraphData.appendImage(bytes1, 2, 1, mmTopx(210), mmTopx(250), 0, "CYAN", 0);
            data.add(paragraphData);
        }

        byte[] fileContent = pdf.writeContent(data);
        return fileContent;
    }




    /**
     * 获取单张图片
     * @param sysFile
     * @param watermarkConfig
     * @return
     * @throws IOException
     */
    public byte[] getImg(SysFile sysFile, WatermarkConfig watermarkConfig) throws IOException {
        byte [] bytes = fileStorageClient.downFile(sysFile.getFileSource());
        //解密
        if (ImgDESFactory.isDES()) {
            bytes = ImgDESFactory.getInstance().decode(bytes);
        }
        //水印配置项不为空则不添加
        if(watermarkConfig != null){
            //添加水印
            if ("0".equals(watermarkConfig.getType())) {
                if (StringUtils.isNotBlank(watermarkConfig.getText())) {
                    bytes = fileStorageController.writeTextWaterMark(bytes, watermarkConfig);
                }
            } else {
                if (watermarkConfig.getImage() != null && watermarkConfig.getImage().length > 0) {
                    bytes = fileStorageController.writeImageWaterMark(bytes, watermarkConfig);
                }
            }
        }
        return bytes;
    }



    public static float mmTopx(float mm){
        mm = (float) (mm *3.33) ;
        return mm ;
    }

    /**
     * @Author: wangyuzhuo
     * @Date: 2020/3/14 17:28
     * @Return: 上传文件
     * @Param:
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean fileUpload(MultipartFile uploadFile, Integer index, String a01Id, String e01z1Id)  {
        String fileId = "123";
        try{
            if (ImgDESFactory.isDES()) {
                ImgDESService imgDESService = ImgDESFactory.getInstance();
                byte[] encrypt = imgDESService.encrypt(uploadFile.getBytes());
                System.out.println("上传图片已被加密");
                fileId = fileStorageClient.fileUpload(encrypt);
            } else {
                fileId = fileStorageClient.fileUpload(uploadFile);
            }
        }catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException("文件上传失败");
        }
//        try {
//            InputStream in = uploadFile.getInputStream();
//            int read = 0;
//            byte [] bytes = new byte[1024];
//            while((read = in.read(bytes))>-1){
//                //什么也不做
//                System.out.println(read);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if("".equals(fileId) || fileId == null){
            return false;
        }else{
            SysFile sysFile = new SysFile();
            sysFile.setId(StringUtils.uuid());
            sysFile.setBindingId(e01z1Id);
            sysFile.setFileIndex(index);
            sysFile.setFileName(uploadFile.getOriginalFilename());
            sysFile.setFileSize(Integer.parseInt(String.valueOf(uploadFile.getSize())));
            sysFile.setFileSource(fileId);
            sysFile.setFileType(uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1));
            sysFile.setFileStorageMode("1");
            return sysFileService.save(sysFile);
        }
    }

    @Override
    public boolean updateFileIndex(String fileId, Integer index) {
        LambdaUpdateWrapper<SysFile> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(SysFile::getId,fileId);
        lambdaUpdateWrapper.set(SysFile::getFileIndex,index);

        return this.sysFileService.update(lambdaUpdateWrapper);
    }

    @Override
    public boolean deleteFileByFileId(String fileId) {

        return sysFileService.removeById(fileId);
    }
}
