package com.fudian.framework.aspectj;

import com.alibaba.fastjson.JSONObject;
import com.fudian.api.system.enums.LoginStatusEnum;
import com.fudian.api.system.pojo.SysLogininfo;
import com.fudian.api.system.service.SysLogininfoApi;
import com.fudian.common.constant.HttpStatus;
import com.fudian.common.utils.StringUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/26 17:56
 * @Desc: 登录日志切面处理
 */
@Component
@Aspect
public class LoginLogAspect {

    @Autowired
    SysLogininfoApi sysLogininfoApi;

    /**
     * slf4j日志
     */
    private final static Logger logger = LoggerFactory.getLogger(LoginLogAspect.class);

    /**
     * Pointcut 切入点
     * 匹配登录方法
     */
    @Pointcut("execution(public * com.fudian.project.system.controller.LoginController.login(..))")
    public void loginLog(){}

    /**
     * 方法执行结束，增强处理
     *
     */
    @AfterReturning(returning = "ret", pointcut = "loginLog()")
    public void doAfterReturning(Object ret){
        // 处理完请求，返回内容
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        //获取请求头
        UserAgent userAgent =  UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        SysLogininfo sysLogininfo = new SysLogininfo();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(ret);
        sysLogininfo.setMsg(jsonObject.getString("msg"));
        sysLogininfo.setUserName(request.getParameter("username"));
        String ip = getIpAddress(request);
        sysLogininfo.setIpaddr(ip);
        sysLogininfo.setBrowser(userAgent.getBrowser().toString());
        sysLogininfo.setOs(userAgent.getOperatingSystem().toString());
        sysLogininfo.setLoginTime(new Date());
        Integer code = jsonObject.getInteger("code");
        String status = (code== HttpStatus.SUCCESS) ? LoginStatusEnum.SUCCESS.getStatus():LoginStatusEnum.FAILD.getStatus();
        sysLogininfo.setStatus(status);
//        sysLogininfo.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        sysLogininfoApi.save(sysLogininfo);
//        logger.info("登录方法返回值 : " + ret);
    }

    public static String getIpAddress(HttpServletRequest request) {
                 String ip = request.getHeader("x-forwarded-for");
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                         ip = request.getHeader("Proxy-Client-IP");
                     }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                         ip = request.getHeader("WL-Proxy-Client-IP");
                     }
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                         ip = request.getHeader("HTTP_CLIENT_IP");
                     }
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                         ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                     }
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                         ip = request.getRemoteAddr();
                     }
                 return ip;
             }


}
