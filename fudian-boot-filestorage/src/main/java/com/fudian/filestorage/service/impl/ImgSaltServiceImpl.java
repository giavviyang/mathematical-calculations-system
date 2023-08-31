package com.fudian.filestorage.service.impl;

import com.fudian.filestorage.service.ImgDESService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

/**
 * 传入盐进行加密
 */
public class ImgSaltServiceImpl implements ImgDESService {

    //如未传入盐则默认使用该字符串
    private static final String defaultSalt = "fuDian.+";

    @Override
    public byte[] encrypt(byte[] data) {
        return encrypt(data,defaultSalt);
    }

    /**
     * 主要加密方法
     * @param data 图片流
     * @param key 加密字符串
     * @return
     */
    @Override
    public byte[] encrypt(byte[] data, String key) {
        if (key == null || "".equals(key)) {
            key = defaultSalt;
        }
        //将字节流通过Base64转换为字符串
        //String imgStr = Base64.getEncoder().encode(data);
        //数据字符组
        byte[] imgBytes = Base64.getEncoder().encode(data);
        //加密字符组
        char[] saltChar = key.toCharArray();
        int x = 0;
        for (int i = 0; i < imgBytes.length; i++) {
            imgBytes[i] = (byte)(imgBytes[i] ^ saltChar[x]);
            if (++x == saltChar.length) {
                x = 0;
            }
        }
        return imgBytes;
    }

    @Override
    public byte[] encrypt(MultipartFile file) {
        try {
            return encrypt(file.getBytes(),defaultSalt);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public byte[] decode(byte[] data) {
        return decode(data,defaultSalt);
    }

    /**
     * 主要解密方法
     * @param data 图片流
     * @param key 解密字符串
     * @return
     */
    @Override
    public byte[] decode(byte[] data, String key) {
        if (key == null || "".equals(key)) {
            key = defaultSalt;
        }
        //加密字符组
        char[] saltChar = key.toCharArray();
        int x = 0;
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte)(data[i] ^ saltChar[x]);
            if (++x == saltChar.length) {
                x = 0;
            }
        }
        return Base64.getDecoder().decode(data);
    }

    @Override
    public byte[] decode(MultipartFile file) {
        try {
            return decode(file.getBytes(),defaultSalt);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
