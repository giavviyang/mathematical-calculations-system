package com.fudian.project.ams.controller;

import com.fudian.common.utils.data.DataStoreWrapper;
import net.sf.jmimemagic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/27 10:13
 * @Desc:
 */
@RestController
@RequestMapping("common")
public class CommonController {

    @Autowired
    DataStoreWrapper redisCache;

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/27 10:20
    * @Return: 通用文件下载
    * @Param:
    */
    @GetMapping("download")
    public ResponseEntity<byte[]> download(HttpServletRequest request,String id,String fileName) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        //设置请求头为二进制流
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        //设置文件名称
        fileName = getFileName(request,fileName);
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;fileName=" +fileName);
        byte [] bytes = redisCache.getCacheObject(id);
        redisCache.deleteObject(id);
        return new ResponseEntity<>(bytes,headers, HttpStatus.OK);
    }


    /**
     * 获取图片流 (可压缩)
     * @param fileId
     * @param compress 是否需要压缩  true为压缩  false为不压缩
     * @return
     */
    @GetMapping("downImage")
    public void downImage(@RequestParam("id")String id,  HttpServletResponse response) throws IOException {

        byte [] bytes = redisCache.getCacheObject(id);
        redisCache.deleteObject(id);

        MagicMatch match = null;
        try {
            match = Magic.getMagicMatch(bytes);
        } catch (MagicParseException ex) {
            ex.printStackTrace();
        } catch (MagicMatchNotFoundException ex) {
            ex.printStackTrace();
        } catch (MagicException ex) {
            ex.printStackTrace();
        }
        String mimeType = match.getMimeType();
        // 设置响应的类型格式为图片格式
        response.setContentType(mimeType);

        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");

        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        bos.write(bytes);

        bos.close();

        //Date returnTime = new Date();
        //System.err.println(dateFormat.format(returnTime) + ":返回图片流");

    }

    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/27 10:20
    * @Return: pdf打印
    * @Param:
    */
    @GetMapping("printpdf")
    public ResponseEntity<byte[]> printPdf(String id) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
//        //设置响应头为查看pdf
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        byte [] bytes = redisCache.getCacheObject(id);
        redisCache.deleteObject(id);
        return new ResponseEntity<>(bytes,headers, HttpStatus.OK);
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String getFileName(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        //ljh  下载文件时空格被转为加号问题处理
        return filename.replaceAll("\\+","%20");
    }
}
