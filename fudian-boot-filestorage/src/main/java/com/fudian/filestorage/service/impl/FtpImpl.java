package com.fudian.filestorage.service.impl;

import com.fudian.api.system.service.SysFileApi;
import com.fudian.filestorage.entity.StorageConfig;
import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.service.FileStorageService;
import com.fudian.filestorage.service.StorageConfigService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Description ftp 存储实现模型
 * @ClassName FtpModel
 * @Author mr.hou
 * @Date 2019/12/28 2:13 下午
 * @Version 1.0.0
 **/
@Component
public class FtpImpl implements FileStorageService {

    @Autowired
    private StorageConfigService storageConfigService;

    @Autowired
    private SysFileApi sysFileService;

    private StorageConfig storageConfig;

    /**
     * 初始化 （只会在项目启动时执行一次）
     * 读取数据库中的配置并创建连接
     */
    @PostConstruct
    public void init() {
        try {
            storageConfig = readConfig();
            StorageConfig.fileStorageType = "FTP";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("无启用的ftp连接");
        }
    }

    /**
     * 维护FTPClient实例
     */
    private final LinkedBlockingQueue<FTPClient> FTP_CLIENT_QUEUE = new LinkedBlockingQueue<>();

    @Override
    public FileStorageType getType() {
        return FileStorageType.FTP;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {

            String fileName = file.getOriginalFilename();
            //中文文件名上传或许存在问题，转为uuid
            String ftpFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileName.split("\\.")[1];

            FTPClient ftpClient = connectClient(storageConfig);
            //根据规则检查文件目录并切换至该文件目录
            String remotePath = "/ams";
            boolean changeResult = ftpClient.changeWorkingDirectory(remotePath);//检查文件夹是否存在并切换目录
            if (!changeResult) {
                Boolean b = ftpClient.makeDirectory(remotePath);
                if (!b) {
                    return null;//文件夹创建失败
                }
                ftpClient.changeWorkingDirectory(remotePath);//切换目录
            }
            InputStream inputStream = file.getInputStream();
            upload(ftpClient,inputStream,ftpFileName);
            return remotePath+"/"+ftpFileName;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        try {

            String fileName = file.getName();
            InputStream inputStream = file.getInputStream();

            //中文文件名上传或许存在问题，转为uuid
            String ftpFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileName.split(".")[1];

            FTPClient ftpClient = connectClient(storageConfig);

            //根据检查文件目录并切换至该文件目录
            String remotePath = createFolderByFolder(ftpClient, folder);
            if (remotePath == null) {
                return null;
            }

            Boolean b= upload(ftpClient, inputStream, ftpFileName);
            if(b){
                return remotePath+"/"+ftpFileName;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String uploadFile(byte[] fileBytes) {
        try {

            //中文文件名上传或许存在问题，转为uuid
            String ftpFileName = UUID.randomUUID().toString().replace("-", "");

            FTPClient ftpClient = connectClient(storageConfig);
            //根据规则检查文件目录并切换至该文件目录
            String remotePath = "/ams";
            boolean changeResult = ftpClient.changeWorkingDirectory(remotePath);//检查文件夹是否存在并切换目录
            if (!changeResult) {
                Boolean b = ftpClient.makeDirectory(remotePath);
                if (!b) {
                    return null;//文件夹创建失败
                }
                ftpClient.changeWorkingDirectory(remotePath);//切换目录
            }
            InputStream inputStream = new ByteArrayInputStream(fileBytes);
            upload(ftpClient,inputStream,ftpFileName);
            return remotePath+"/"+ftpFileName;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String uploadFile(byte[] fileBytes, String folder, String fileName) {
        try {

            InputStream inputStream = new ByteArrayInputStream(fileBytes);

            //中文文件名上传或许存在问题，转为uuid
            String ftpFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileName.split(".")[1];

            FTPClient ftpClient = connectClient(storageConfig);

            //根据规则检查文件目录并切换至该文件目录
            String remotePath = createFolderByFolder(ftpClient, folder);
            if (remotePath == null) {
                return null;
            }

            Boolean b= upload(ftpClient, inputStream, ftpFileName);
            if(b){
                return remotePath+"/"+ftpFileName;
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String uploadFile(byte[] fileBytes, String fileType) {
        try {

            //中文文件名上传或许存在问题，转为uuid
            String ftpFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileType;

            FTPClient ftpClient = connectClient(storageConfig);
            //根据规则检查文件目录并切换至该文件目录
            String remotePath = "/ams";
            boolean changeResult = ftpClient.changeWorkingDirectory(remotePath);//检查文件夹是否存在并切换目录
            if (!changeResult) {
                Boolean b = ftpClient.makeDirectory(remotePath);
                if (!b) {
                    return null;//文件夹创建失败
                }
                ftpClient.changeWorkingDirectory(remotePath);//切换目录
            }
            InputStream inputStream = new ByteArrayInputStream(fileBytes);
            upload(ftpClient,inputStream,ftpFileName);
            return remotePath+"/"+ftpFileName;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteFile(String fileId) {
        try {

//            SysFile sysFile = sysFileService.getBaseMapper().selectById(fileId);
            String filesource = fileId;
            //信息从缓存获取 后期修改
            String remotePath=filesource.substring(0,filesource.lastIndexOf("/"));
            String ftpName=filesource.substring(filesource.lastIndexOf("/")+1,filesource.length());
            FTPClient ftpClient = connectClient(storageConfig);
            boolean changeResult = ftpClient.changeWorkingDirectory(remotePath);
            if (!changeResult) {
                throw new RuntimeException("切换目录失败");
            }
            boolean result = ftpClient.deleteFile(ftpName);
            // 退出FTP
            ftpClient.logout();
            //归还对象
            offer(ftpClient);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public byte[] getFile(String fileId) {
        try {
//            SysFile sysFile = sysFileService.getBaseMapper().selectById(fileId);
            String filesource = fileId;
            String remotePath=filesource.substring(0,filesource.lastIndexOf("/"));
            String ftpName=filesource.substring(filesource.lastIndexOf("/")+1,filesource.length());
            FTPClient ftpClient = connectClient(storageConfig);
            boolean changeResult = ftpClient.changeWorkingDirectory(remotePath);
            if (!changeResult) {
                throw new RuntimeException("切换目录失败");
            }
            ftpClient.setFileType(2);
            if (!"".equals(remotePath)) {
                ftpName = remotePath + "/" + ftpName;
            }
            InputStream inputStream=ftpClient.retrieveFileStream(ftpName);
            byte[] bytes = isBytes(inputStream);
            // 退出FTP
            ftpClient.logout();
            //归还对象
            offer(ftpClient);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean testIsConnect(String ip, int port, String userName, String password) {
        boolean returnFlag = false;
        StorageConfig ftpConfig = new StorageConfig();
        ftpConfig.setIp(ip);
        ftpConfig.setPassword(password);
        ftpConfig.setPort((long) port);
        ftpConfig.setUsername(userName);
        FTPClient ftpClient =null;
        try {
            ftpClient = connectClient(ftpConfig);
            returnFlag=ftpClient!=null;
            ftpClient.logout();
            //归还对象
            offer(ftpClient);
        }catch (IOException e){
            return false;
        }
        return returnFlag;
    }

    /**
     * 刷新存储配置
     * @return
     */
    @Override
    public boolean flushConfig() {
        try {
            storageConfig = readConfig();
            StorageConfig.fileStorageType = "FTP";
            if (storageConfig != null) {
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
        config.setType("0");
        List<StorageConfig> storageConfigList = storageConfigService.select(config);

        //如果没有取出配置抛出异常终止启动
        if (storageConfigList ==null || storageConfigList.size() <= 0) {
            throw new Exception("存储容器配置失败！！！");
        }
        return storageConfigList.get(0);
    }

    private  byte[] isBytes(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
    private  void offer(FTPClient ftpClient) {
        FTP_CLIENT_QUEUE.offer(ftpClient);
    }
    /**
     * 上传文件
     * @param ftpClient   ftp对象
     * @param inputStream 待上传文件输入流
     * @param ftpFileName    ftp文件名
     * @return 是否上传成功
     */
    private boolean upload(FTPClient ftpClient, InputStream inputStream, String ftpFileName) {
        try {
            // 设置被动模式
            ftpClient.enterLocalPassiveMode();
            // 设置流上传方式
            ftpClient.setFileTransferMode(org.apache.commons.net.ftp.FTP.STREAM_TRANSFER_MODE);
            // 设置二进制上传
            ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
            //中文存在问题
            //设置ftp缓冲区大小
            ftpClient.setBufferSize(1024*1024);
            // 上传 fileName为上传后的文件名
            boolean result = ftpClient.storeFile(ftpFileName, inputStream);
            // 关闭本地文件流
            inputStream.close();
            // 退出FTP
            ftpClient.logout();
            //归还对象
            offer(ftpClient);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 登录ftp
     *
     * @param storageConfig 配置
     * @return 是否登录成功
     * @throws IOException
     */
    private  FTPClient connectClient(StorageConfig storageConfig) throws IOException {
        FTPClient ftpClient = getClient();
        // 连接FTP服务器
        ftpClient.connect(storageConfig.getIp(), storageConfig.getPort().intValue());
        // 登录FTP
        ftpClient.login(storageConfig.getUsername(), storageConfig.getPassword());
        // 正常返回230登陆成功
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {//连接失败
            ftpClient.disconnect();
            return null;
        }
        ftpClient.setControlEncoding("GBK");
        return ftpClient;
    }
    /**
     * 获取ftpClient对象
     *
     * @return 获取client对象
     */
    private  FTPClient getClient() {
        FTPClient ftpClient = FTP_CLIENT_QUEUE.poll();
        if (ftpClient != null) {
            return ftpClient;
        }
        return new FTPClient();
    }
    /**
     * 检查并创建文件夹
     * @param ftpClient
     * @param folder  文件夹
     * @return
     */
    private String createFolderByFolder(FTPClient ftpClient,String folder){
        try {
            String[] folders=null;
            if(folder.contains("/")){
                folders=folder.split("/");
            }else if(folder.contains("\\")){
                folders=folder.split("\\\\");
            }
            String files="";
            if(folders!=null){
                for(String file:folders){
                    files=files+"/"+file;
                    boolean changeResult = ftpClient.changeWorkingDirectory(files);//检查文件夹是否存在并切换目录
                    if(!changeResult){
                        Boolean b=ftpClient.makeDirectory(files);
                        if(!b){
                            return null;//文件夹创建失败
                        }
                    }
                }
            }else{
                files="/"+folder;
                boolean changeResult = ftpClient.changeWorkingDirectory(files);//检查文件夹是否存在并切换目录
                if(!changeResult){
                    Boolean b=ftpClient.makeDirectory(files);
                    if(!b){
                        return null;//文件夹创建失败
                    }
                }
            }
            ftpClient.changeWorkingDirectory(files);//检查文件夹是否存在并切换目录
            return files;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
