package com.fudian.filestorage;

import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.factory.FileStorageFactory;
import com.fudian.filestorage.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 测试类
 * @ClassName Test
 * @Author mr.hou
 * @Date 2019/12/28 2:33 下午
 * @Version 1.0.0
 **/
@Controller
public class FileStorageTest {

    @Value("${fileStorage.type}")
    private String fileStorageType;


    @RequestMapping("/test")
    public void test(byte[] fileByte){
        FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));

        String s = fileStorageService.uploadFile(fileByte);
    }

}
