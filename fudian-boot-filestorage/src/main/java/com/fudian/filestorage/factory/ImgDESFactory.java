package com.fudian.filestorage.factory;

import com.fudian.filestorage.service.ImgDESService;
import com.fudian.filestorage.service.impl.ImgSaltServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 图片加密工厂
 **/
@Component
public class ImgDESFactory {

    private static ImgDESService imgDESService;

    /**
     * 初始化 （只会在项目启动时执行一次）
     *
     */
    @PostConstruct
    public void init() {
        readConfig();
    }

    public static ImgDESService getInstance() {
        return imgDESService;
    }

    public static void put(ImgDESService imgDESService) {
        ImgDESFactory.imgDESService = imgDESService;
    }

    /**
     * 刷新  用于实时修改工具类
     */
    public static void flush() {
        readConfig();
    }

    /**
     * 是否需要加解密
     * @return
     */
    public static boolean isDES() {
        return true;
    }

    /**
     * 从数据库中读取配置并创建实例
     */
    private static void readConfig() {
        //暂时写死
        imgDESService = new ImgSaltServiceImpl();
    }
}
