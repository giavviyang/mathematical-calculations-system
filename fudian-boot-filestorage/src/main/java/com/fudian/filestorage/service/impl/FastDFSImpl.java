package com.fudian.filestorage.service.impl;

import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.service.FileStorageService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description FastDFS 存储实现模型
 * @ClassName FastDFSImpl
 * @Author mr.hou
 * @Date 2019/12/28 2:18 下午
 * @Version 1.0.0
 **/
@Component
public class FastDFSImpl implements FileStorageService {
    @Override
    public FileStorageType getType() {
        return FileStorageType.FastDFS;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        return null;
    }

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        return null;
    }

    @Override
    public String uploadFile(byte[] fileBytes) {
        return null;
    }

    @Override
    public String uploadFile(byte[] fileBytes, String folder, String fileName) {
        return null;
    }

    @Override
    public String uploadFile(byte[] fileBytes, String fileType) {
        return null;
    }

    @Override
    public boolean deleteFile(String fileId) {
        return false;
    }

    @Override
    public byte[] getFile(String fileId) {
        return new byte[0];
    }

    @Override
    public boolean testIsConnect(String ip, int port, String userName, String password) {
        return false;
    }

    @Override
    public boolean flushConfig() {
        return false;
    }
}
