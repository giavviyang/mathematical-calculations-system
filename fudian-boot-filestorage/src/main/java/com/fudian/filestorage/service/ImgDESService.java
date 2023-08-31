package com.fudian.filestorage.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 图片加解密
 */
public interface ImgDESService {

    /**
     * 加密
     * @param data 图片流
     * @return
     */
    byte[] encrypt(byte[] data);

    /**
     * 加密
     * @param data 图片流
     * @param key 加密字符串
     * @return
     */
    byte[] encrypt(byte[] data, String key);

    /**
     * 加密
     * @param file 图片文件
     * @return
     */
    byte[] encrypt(MultipartFile file);

    /**
     * 解密
     * @param data 图片流
     * @return
     */
    byte[] decode(byte[] data);

    /**
     * 解密
     * @param data 图片流
     * @param key 解密字符串
     * @return
     */
    byte[] decode(byte[] data, String key);

    /**
     * 解密
     * @param file 图片文件
     * @return
     */
    byte[] decode(MultipartFile file);

}
