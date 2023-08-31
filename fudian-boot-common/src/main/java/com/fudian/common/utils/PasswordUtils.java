package com.fudian.common.utils;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * AES对称加密和解密
 * @author zyg
 */
@Slf4j
public class PasswordUtils {


    /**
     * licenseKey加密密钥
     */
    public static final String LICENSE_KEY = "fd1234567890abcd";

    /**
     * license 加密密钥
     */
    public static final String LICENSE = "fd1234567890abcd";

    public static final String CPU_KEY = "machineUnicode";

    public static final String DATE_STR = "expiration";

    public static final String IV = "0102030405060708";

    public static final String DATE_UN = "unlimited";


    public static boolean checkLicense(String license){
        license = PasswordUtils.AESDncode(license,LICENSE,IV);
        JSONObject jsonObject = JSON.parseObject(license);
        String date = jsonObject.getString(DATE_STR);

        if(date == null || "".equals(date)){
            return false;
        }


        String machCode = jsonObject.getString(CPU_KEY);
        log.info("license中机器号===>"+machCode);
        //系统到期时间不为永久则验证当前时间是否超过授权的时间
        if(!DATE_UN.equals(date)){
            Date expirDate = DateUtils.parseDate(date);

            if(System.currentTimeMillis()>expirDate.getTime()){
                log.info("license授权超时");
                return false;
            }

        }

        if(!getCpuId().equals(machCode)){
            log.error("license与机器号不匹配");
            return false;
        }
        return true;

    }

    /**
     2      * 获取CPU序列号
     3      * @return
     4      */
      public static String getCpuId()  {
          try {
              return CpuUtil.generateLicenseKey();
          } catch (Exception e) {
              log.error(e.getMessage());
              log.error("获取系统序列号错误");
              return "";
          }
         /* try{
              Process process = Runtime.getRuntime().exec(
                      new String[]{"wmic", "cpu", "get", "ProcessorId"});
              process.getOutputStream().close();
              Scanner sc = new Scanner(process.getInputStream());
              String property = sc.next();
              String serial = sc.next();
              return serial;
          }catch(Exception e){
              log.error(e.getMessage());
              return "";
          }*/
      }

    /**
     *
     * @param data 加密串
     * @param key 密钥
     * @param iv 偏移量
     * @return
     */
    public static String AESEncode(String data,String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes("UTF-8");
            int plaintextLength = dataBytes.length;

            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return Base64.encode(encrypted).trim();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param data 加密后的字符串
     * @param key 密钥
     * @param iv 偏移量
     * @return
     */
    public static String AESDncode(String data, String key,String iv) {
        try
        {
            byte[] encrypted1 = Base64.decode(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 字节数组转16进制
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的Hex字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }


    public static void main(String[] args) {
        String a = "HhRynLrteQJ9QLNxXiRorDIvJ7N2xvK0yWX462eFG6d7sWww/mH0lEEdl8PUXMmb7BOoqBB0k8fdCo/cyuVOVkmhyp13bMOL/2u1L3RAfGGi2LlrTA104AtTjTAhZY4C6qGFe3VAsW9jh31ptwq6/K+8Lp0qahweBFuITqEXVzyrmBngEWpxuZf8F7HbJARH";
        String key = PasswordUtils.AESDncode(a, PasswordUtils.LICENSE_KEY, PasswordUtils.IV);
        JSONObject jsonObject = JSON.parseObject(key);
        jsonObject.put(PasswordUtils.DATE_STR,"2020-09-15");
        System.out.println(PasswordUtils.AESEncode(jsonObject.toJSONString(), PasswordUtils.LICENSE, PasswordUtils.IV));
    }
}
