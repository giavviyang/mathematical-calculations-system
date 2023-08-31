package com.fudian.framework.auth.interceptor;

import com.fudian.api.system.service.TokenApi;
import com.fudian.common.enumns.ExceptionEnum;
import com.fudian.common.exception.FuDianException;
import com.fudian.common.utils.CpuUtil;
import com.fudian.framework.auth.entity.OrgInfo;
import com.fudian.framework.auth.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * @Description 浮点全宗认证拦截
 * @ClassName ChooseDataSourceInterceptor
 * @Author mr.hou
 * @Date 2019/12/24 6:37 下午
 * @Version 1.0.0
 **/
public class FDAuthInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(FDAuthInterceptor.class);
    // 公钥位置
    @Value("${license.pubKeyPath}")
    private String pubKeyPath;


    @Autowired
    private TokenApi tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (true) {return true;}
        //TODO 从数据库中获取
        //1.从数据库获取license
        String license = "eyJhbGciOiJSUzI1NiJ9.eyJvcmdJZCI6IjEyMyIsIm9yZ05hbWUiOiLmta7ngrnljJfkuqwiLCJsaWNlbnNlS2V5IjoiUVhCd2JHVWpRekF5V2pZMldFeE1Wa05ISTBsdWRHVnNOalFnUm1GdGFXeDVJRFlnVFc5a1pXd2dNVFU0SUZOMFpYQndhVzVuSURFekl6RTIiLCJzdGFydFRpbWUiOjE1NzgzODI5NjYzMjIsImVudFRpbWUiOjE1NzgzOTAxNjYzMjJ9.NM4VOo710Z5AxqYqDDN-sysztXpNTEVT4EwtNaBAe5wRgFqmWaJtNjPFXOnEnAeQVVjuMB9lAtI2TR8nplNAuEX_SHeEpFPFTbZ7JJrGqrX58kNXMPiUXuKyIESMrJ72Nm6mMHSwFuMk3sAx316CHiX12pqqBA-Fy6C6cqcmob8";

        //2.从license中解析全宗信息
        // 解析license
        OrgInfo orgInfo = null;
        ClassPathResource classPathResource = new ClassPathResource(pubKeyPath);
        byte[] pubKeyByte = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
        try {
            orgInfo = JwtUtils.getInfoFromToken(license, pubKeyByte);
        } catch (Exception e) {
            log.info("获取授权信息错误,请联系开发商.");
            throw new FuDianException(ExceptionEnum.ERROR_SYSTEM_LICENSE);
        }

        String licenseKey = CpuUtil.generateLicenseKey();
        licenseKey = Base64.getEncoder().encodeToString(licenseKey.getBytes("utf-8"));

        if (!licenseKey.equals(orgInfo.getLicenseKey())){
            log.info("服务器信息不匹配.");
            throw new FuDianException(ExceptionEnum.INVALID_SYSTEM_LICENSE_KEY);
        }

        //验证是否过期
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis>orgInfo.getEndTime()){
            log.info("当前系统已过期,请联系供应商续期.");
            throw new FuDianException(ExceptionEnum.INVALID_SYSTEM_LICENSE);
        }

        return true;
    }

}
