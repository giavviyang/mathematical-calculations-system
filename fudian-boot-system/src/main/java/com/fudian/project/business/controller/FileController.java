package com.fudian.project.business.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fudian.business.pojo.AppletFile;
import com.fudian.business.service.AppletFileService;
import com.fudian.business.vo.Classification;
import com.fudian.business.vo.KnowledgePopup;
import com.fudian.business.vo.Subject;
import com.fudian.business.vo.TrainResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.business.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 训练习题
 *
 * @author xuchenghong
 */
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*",origins = "*")
@RestController
@RequestMapping(value = "applet/file")
public class FileController {

    @Autowired
    private AppletFileService appletFileService;

    /**
     * IO流读取图片
     *
     * @return
     */
    @GetMapping(value = "/ioReadImage")
    public String ioReadImage(String fileId, HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        try {
            //获取图片
            AppletFile appletFile = appletFileService.selectAppletFileById(fileId);

            byte[] fileData = appletFile.getFileData();
            response.setContentType("multipart/form-data");
            response.setHeader("Content-disposition","attachment;filename="+appletFile.getId());
            out = response.getOutputStream();
            //读取文件流
            /*int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }*/
            out.write(fileData);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
        return null;
    }
    /**
     * 拼接ip
     *
     * @return
     */
    @GetMapping(value = "/obtainServiceIp")
    public CommonResult<?> obtainServiceIp(String musicId, HttpServletRequest request) throws IOException {
        if (StringUtils.isEmpty(musicId)){
            return CommonResult.error("id不能为空！");
        }
        try {
            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
                    String ip = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + musicId;
            return CommonResult.success("操作成功！",ip);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }
    /**
     *
     *
     * @return
     */
    @PostMapping(value = "/addFile")
    public CommonResult<?> addFile(String id, MultipartFile file) throws IOException {
        try {
            //修改头像
            AppletFile appletFile = new AppletFile();
            appletFile.setId(id);
            byte[] bytes = file.getBytes();
            appletFile.setFileData(bytes);
            appletFileService.save(appletFile);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
        return CommonResult.success();
    }
}
