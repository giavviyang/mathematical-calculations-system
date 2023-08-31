package com.fudian.framework.auth.utils;

import com.fudian.framework.auth.entity.OrgInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * jwt 工具类
 */
public class JwtUtils {
    /**
     * 私钥加密token
     *
     * @param orgInfo      载荷中的数据
     * @param privateKey    私钥
     * @param day 有效期，单位天
     * @return
     * @throws Exception
     */
    public static String generateToken(OrgInfo orgInfo, PrivateKey privateKey, int day) throws Exception {
        long startTime = System.currentTimeMillis();
        return Jwts.builder()
                .claim(JwtConstans.JWT_KEY_ORG_ID, orgInfo.getOrgId())
                .claim(JwtConstans.JWT_KEY_ORG_NAME, orgInfo.getOrgName())
                .claim(JwtConstans.JWT_KEY_ORG_LICENSE_KEY, orgInfo.getLicenseKey())
                .claim(JwtConstans.JWT_KEY_ORG_START_TIME,startTime)
                .claim(JwtConstans.JWT_KEY_ORG_END_TIME,startTime+day*24*60*1000L)
                //.setExpiration(DateTime.now().plusDays(day).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 私钥加密token
     *
     * @param orgInfo      载荷中的数据
     * @param privateKey    私钥字节数组
     * @param day 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(OrgInfo orgInfo, byte[] privateKey, int day) throws Exception {
        long startTime = System.currentTimeMillis();
        return Jwts.builder()
                .claim(JwtConstans.JWT_KEY_ORG_ID, orgInfo.getOrgId())
                .claim(JwtConstans.JWT_KEY_ORG_NAME, orgInfo.getOrgName())
                .claim(JwtConstans.JWT_KEY_ORG_START_TIME,startTime)
                .claim(JwtConstans.JWT_KEY_ORG_END_TIME,startTime+day*24*60*1000L)
                //.setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, RsaUtils.getPrivateKey(privateKey))
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥字节数组
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, byte[] publicKey) throws Exception {
        return Jwts.parser().setSigningKey(RsaUtils.getPublicKey(publicKey))
                .parseClaimsJws(token);
    }

    /**
     * 获取token中的全宗信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static OrgInfo getInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new OrgInfo(
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_ORG_ID)),
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_ORG_NAME)),
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_ORG_LICENSE_KEY)),
                ObjectUtils.toLong(body.get(JwtConstans.JWT_KEY_ORG_START_TIME)),
                ObjectUtils.toLong(body.get(JwtConstans.JWT_KEY_ORG_END_TIME))

        );
    }

    /**
     * 获取token中的全宗信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static OrgInfo getInfoFromToken(String token, byte[] publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new OrgInfo(
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_ORG_ID)),
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_ORG_NAME)),
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_ORG_LICENSE_KEY)),
                ObjectUtils.toLong(body.get(JwtConstans.JWT_KEY_ORG_START_TIME)),
                ObjectUtils.toLong(body.get(JwtConstans.JWT_KEY_ORG_END_TIME))
        );
    }
}
