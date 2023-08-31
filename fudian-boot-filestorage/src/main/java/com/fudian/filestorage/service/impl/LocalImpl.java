package com.fudian.filestorage.service.impl;

import com.fudian.common.utils.StringUtils;
import com.fudian.filestorage.entity.StorageConfig;
import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.service.FileStorageService;
import com.fudian.filestorage.service.StorageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

/**
 * @Description 本地存储实现类
 * @ClassName LocalImpl
 * @Author mr.hou
 * @Date 2019/12/28 2:35 下午
 * @Version 1.0.0
 **/
@Component
public class LocalImpl implements FileStorageService {

    @Autowired
    private StorageConfigService storageConfigService;

    private StorageConfig storageConfig;

    private String basePath;

    /**
     * 初始化 （只会在项目启动时执行一次）
     * 读取数据库中的配置并创建连接
     */
    @PostConstruct
    public void init() {
        try {
            storageConfig = readConfig();
            StorageConfig.fileStorageType = "LOCAL";
        } catch (Exception e) {
            System.out.println("无启用的本地连接");
        }
    }

    @Override
    public FileStorageType getType() {
        return FileStorageType.LOCAL;
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file) {
        FileOutputStream outputStream = null;
        try {
            //获取文件名文件累类型
            String originalFilename = file.getOriginalFilename();
            String[] split = originalFilename.split(".");
            String type = null;
            if (split.length >1) {
                type = split[1];
            }
            //生成文件名
            String filename = (type==null?StringUtils.uuid():StringUtils.uuid()+"."+type);
            File localFile = new File(basePath + File.separator + filename);

            outputStream = new FileOutputStream(localFile);
            outputStream.write(file.getBytes());
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        FileOutputStream outputStream = null;
        try {
            //获取文件名文件累类型
            String originalFilename = file.getOriginalFilename();
            String[] split = originalFilename.split(".");
            String type = null;
            if (split.length >1) {
                type = split[1];
            }

            //生成文件名
            String filename = (type==null?StringUtils.uuid():StringUtils.uuid()+"."+type);

            File folderFile = new File(basePath + File.separator + folder);
            if (!folderFile.isDirectory() || !folderFile.exists()) {
                folderFile.mkdirs();
            }
            File localFile = new File(folderFile + File.separator + filename);

            outputStream = new FileOutputStream(localFile);
            outputStream.write(file.getBytes());
            return folder + File.separator + filename;
        } catch (IOException e) {
            e.printStackTrace();
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public String uploadFile(byte[] fileBytes) {
        FileOutputStream outputStream = null;
        try {
            //生成文件名
            String filename = StringUtils.uuid();
            File localFile = new File(basePath + File.separator + filename);

            outputStream = new FileOutputStream(localFile);
            outputStream.write(fileBytes);
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 上传文件
     * 避免重复  不使用传来的文件名
     * @param fileBytes 文件二进制
     * @param folder   文件存储目录
     * @param filename
     * @return
     */
    @Override
    public String uploadFile(byte[] fileBytes, String folder, String filename) {
        FileOutputStream outputStream = null;
        try {
            //生成文件名
            filename = StringUtils.uuid();
            File folderFile = new File(basePath + File.separator + folder);
            if (!folderFile.isDirectory() || !folderFile.exists()) {
                folderFile.mkdirs();
            }
            File localFile = new File(folderFile + File.separator + filename);

            outputStream = new FileOutputStream(localFile);
            outputStream.write(fileBytes);
            return folder + File.separator + filename;
        } catch (IOException e) {
            e.printStackTrace();
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public String uploadFile(byte[] fileBytes, String fileType) {
        FileOutputStream outputStream = null;
        try {
            //生成文件名
            String filename = StringUtils.uuid() + "." + fileType;
            File localFile = new File(basePath + File.separator + filename);
            outputStream = new FileOutputStream(localFile);
            outputStream.write(fileBytes);
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 删除文件
     * @param fileId
     * @return
     */
    @Override
    public boolean deleteFile(String fileId) {
        File file = new File(basePath + File.separator + fileId);
        return file.delete();
    }

    /**
     * 获取文件
     * @param fileId
     * @return
     */
    @Override
    public byte[] getFile(String fileId) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(basePath + File.separator + fileId));
            //读取文件流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] temp = new byte[1024*8];
            int i;
            while ((i=inputStream.read(temp)) != -1) {
                outputStream.write(temp,0,i);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean testIsConnect(String ip, int port, String userName, String password) {
        return true;
    }

    @Override
    public boolean flushConfig() {
        try {
            readConfig();
            StorageConfig.fileStorageType = "LOCAL";
            if (StringUtils.isNotBlank(basePath)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取存储配置
     * @return
     */
    private StorageConfig readConfig() throws Exception {
        //从数据库中取出存储配置
        StorageConfig config = new StorageConfig();
        config.setActive("1");
        config.setType("3");
        List<StorageConfig> storageConfigList = storageConfigService.select(config);

        //如果没有取出配置抛出异常终止启动
        if (storageConfigList ==null || storageConfigList.size() <= 0) {
            throw new Exception("存储容器配置失败！！！");
        }
        StorageConfig storageConfig = storageConfigList.get(0);
        //获取存储根路径
        basePath = storageConfig.getBasePath();
        return storageConfig;
    }
}
