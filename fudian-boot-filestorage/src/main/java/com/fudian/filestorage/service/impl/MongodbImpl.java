package com.fudian.filestorage.service.impl;

import com.fudian.common.utils.StringUtils;
import com.fudian.filestorage.entity.StorageConfig;
import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.service.FileStorageService;
import com.fudian.filestorage.service.StorageConfigService;
import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description MongoDB 存储实现模型
 * @ClassName MongodbImpl
 * @Author mr.hou
 * @Date 2019/12/28 2:17 下午
 * @Version 1.0.0
 **/
@Component
public class MongodbImpl implements FileStorageService {

    @Autowired
    private StorageConfigService storageConfigService;

    private StorageConfig storageConfig;

    private MongoDatabase mongoDatabase;

    /**
     * 初始化 （只会在项目启动时执行一次）
     * 读取数据库中的配置并创建连接
     */
    @PostConstruct
    public void init() {
        try {
            //读取配置文件
            storageConfig = readConfig();
            //获取文件操作工具
            mongoDatabase = getMongoDatabase();
            StorageConfig.fileStorageType = "MongoDB";
        } catch (Exception e) {
            System.out.println("无启用的mongodb连接");
        }
    }

    @Override
    public FileStorageType getType() {
        return FileStorageType.MongoDB;
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file) {
        //获取文件名文件累类型
        String originalFilename = file.getOriginalFilename();
        String[] split = originalFilename.split(".");
        String type = null;
        if (split.length >1) {
            type = split[1];
        }
        try {
            //获取mongodb文件操作类
            GridFSBucket bucket = GridFSBuckets.create(mongoDatabase);
            //获取上传的文件名
            String uploadFilename = StringUtils.uuid() + (type == null ? "" : "." + type);
            ObjectId objectId = bucket.uploadFromStream(uploadFilename, file.getInputStream());
            return objectId.toHexString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        return uploadFile(file);
    }

    @Override
    public String uploadFile(byte[] fileBytes) {
        try {
            //获取mongodb文件操作类
            GridFSBucket bucket = GridFSBuckets.create(mongoDatabase);
            //获取上传的文件名
            String uploadFilename = StringUtils.uuid();
            ObjectId objectId = bucket.uploadFromStream(uploadFilename, new ByteArrayInputStream(fileBytes));
            return objectId.toHexString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String uploadFile(byte[] fileBytes, String folder, String fileName) {
        return uploadFile(fileBytes);
    }

    @Override
    public String uploadFile(byte[] fileBytes, String fileType) {
        return uploadFile(fileBytes);
    }

    /**
     * 删除文件
     * @param fileId
     * @return
     */
    @Override
    public boolean deleteFile(String fileId) {
        //获取mongodb文件操作类
        GridFSBucket bucket = GridFSBuckets.create(mongoDatabase);
        bucket.delete(new ObjectId(fileId));
        return false;
    }

    /**
     * 获取文件
     * @param fileId
     * @return
     */
    @Override
    public byte[] getFile(String fileId) {
        //获取mongodb文件操作类
        GridFSBucket bucket = GridFSBuckets.create(mongoDatabase);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bucket.downloadToStream(new ObjectId(fileId),byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public boolean testIsConnect(String ip, int port, String userName, String password) {

        try {
            StorageConfig storageConfig = new StorageConfig();
            storageConfig.setIp(ip);
            storageConfig.setPort((long) port);
            storageConfig.setUsername(userName);
            storageConfig.setPassword(password);

            String databaseName = storageConfig.getBasePath();

            if (databaseName == null || "".equals(databaseName)) {
                databaseName = "ams";
            }

            //配置地址端口
            ServerAddress record = new ServerAddress(storageConfig.getIp(),storageConfig.getPort().intValue());
            List<ServerAddress> serverAddressList = new ArrayList<>();
            serverAddressList.add(record);
            //配置用户名密码
            MongoCredential credential = MongoCredential.createCredential(storageConfig.getUsername()==null?"":storageConfig.getUsername(),databaseName,storageConfig.getPassword()==null?"".toCharArray():storageConfig.getPassword().toCharArray());
            List<MongoCredential> mongoCredentialList = new ArrayList<>();
            mongoCredentialList.add(credential);
            MongoClient mongoClient = new MongoClient(serverAddressList, mongoCredentialList);

            mongoClient.getDatabase(databaseName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 刷新存储配置
     * @return
     */
    @Override
    public boolean flushConfig() {
        try {
            storageConfig = readConfig();
            mongoDatabase = getMongoDatabase();
            StorageConfig.fileStorageType = "MongoDB";
            if (storageConfig != null && mongoDatabase!=null) {
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
        config.setType("1");
        List<StorageConfig> storageConfigList = storageConfigService.select(config);

        //如果没有取出配置抛出异常终止启动
        if (storageConfigList ==null || storageConfigList.size() <= 0) {
            throw new Exception("存储容器配置失败！！！");
        }
        return storageConfigList.get(0);
    }

    /**
     * 获取mongodb文件操作工具
     * @return
     */
    private MongoDatabase getMongoDatabase() {
        try {
            String databaseName = storageConfig.getBasePath();

            if (databaseName == null || "".equals(databaseName)) {
                databaseName = "ams";
            }

            //配置地址端口
            ServerAddress record = new ServerAddress(storageConfig.getIp(),storageConfig.getPort().intValue());
            List<ServerAddress> serverAddressList = new ArrayList<>();
            serverAddressList.add(record);
            //配置用户名密码
            MongoCredential credential = MongoCredential.createCredential(storageConfig.getUsername()==null?"":storageConfig.getUsername(),databaseName,storageConfig.getPassword()==null?"".toCharArray():storageConfig.getPassword().toCharArray());
            List<MongoCredential> mongoCredentialList = new ArrayList<>();
            mongoCredentialList.add(credential);
            MongoClient mongoClient = new MongoClient(serverAddressList, mongoCredentialList);

            return mongoClient.getDatabase(databaseName);
        } catch (Exception e) {
            return null;
        }
    }
}
