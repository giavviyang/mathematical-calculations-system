package com.fudian.filestorage.service;

import com.fudian.filestorage.enums.FileStorageType;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @ClassName FileStorageService
 * @Author mr.hou
 * @Date 2019/12/5 6:53 下午
 * @Version 1.0.0
 **/
public interface FileStorageService {


    /**
     * 查询实现类型
     * @return
     */
    FileStorageType getType();

    /**
     * 文件上传
     * @param file
     * @return 文件存储标识Id
     */
    String uploadFile(MultipartFile file);

    /**
     * 文件上传（ftp需根据干部基本信息创建文件夹）
     * @param file
     * @return 文件存储标识Id
     */
    String uploadFile(MultipartFile file, String folder) ;

    /**
     * 文件上传
     * @param fileBytes 文件二进制
     * @return 文件存储标识Id
     */
    String uploadFile(byte[] fileBytes) ;

    /**
     * 文件上传（ftp需根据干部基本信息创建文件夹）
     * @param fileBytes 文件二进制
     * @param folder   文件存储目录
     * @param fileName  文件名
     * @return 文件存储标识Id
     */
    String uploadFile(byte[] fileBytes, String folder, String fileName);


    /**
     * 文件上传（ftp需根据干部基本信息创建文件夹）
     * @param fileBytes 文件二进制
     * @param fileType 文件类型
     * @return 文件存储标识Id
     */
    String uploadFile(byte[] fileBytes, String fileType);

    /**
     * 文件删除
     * @param fileId
     * @return true 删除成功  false: 失败
     */
    boolean deleteFile(String fileId);


    /**
     * 获取文件流
     * @param fileId
     * @return
     */
    byte[] getFile(String fileId);

    /**
     * 验证是否能够连接
     * @param
     * @return
     */
    boolean testIsConnect(String ip, int port, String userName, String password);

    /**
     * 刷新存储配置
     * @return
     */
    boolean flushConfig();

}
