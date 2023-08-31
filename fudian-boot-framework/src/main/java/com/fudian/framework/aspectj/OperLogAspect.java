package com.fudian.framework.aspectj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.SysOperLog;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.SysDeptApi;
import com.fudian.api.system.service.SysOperLogApi;
import com.fudian.api.system.service.TokenApi;
import com.fudian.common.constant.HttpStatus;
import com.fudian.common.utils.StringUtils;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/26 17:56
 * @Desc: 登录日志切面处理
 */
@Component
@Aspect
public class OperLogAspect {

    @Autowired
    TokenApi tokenService;

    @Autowired
    SysOperLogApi sysOperLogService;

    @Autowired
    private SysDeptApi sysDeptService;

    /**
     * slf4j日志
     */
    private final static Logger logger = LoggerFactory.getLogger(OperLogAspect.class);

    /**
     * Pointcut 切入点
     * 匹配登录方法
     */
    @Pointcut("execution(public * com.fudian.project.*.controller.*.*(..))")
    public void operLog(){}

    /**
     * 方法执行结束，增强处理
     *
     */
    @AfterReturning(returning = "ret", pointcut = "operLog()")
    public void doAfterReturning(JoinPoint joinPoint,Object ret) throws NoSuchMethodException {
        // 处理完请求，返回内容
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        //获取请求头
        UserAgent userAgent =  UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        try {
            LoginUser loginUser = tokenService.getLoginUser(request);
            if(loginUser == null){
                return;
            }
            SysUser sysUser = loginUser.getUser();

            Class clazz = joinPoint.getTarget().getClass();
            OperTitle operTitle = (OperTitle) clazz.getDeclaredAnnotation(OperTitle.class);
            String title = operTitle == null?"":operTitle.value();
            String targetName = clazz.getSimpleName();
            Class[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
            String methodName = joinPoint.getSignature().getName();
            Method method = clazz.getMethod(methodName,parameterTypes);


            OperInfo operInfo = method.getDeclaredAnnotation(OperInfo.class);
            if (operInfo != null) {
                String[] ignores = operInfo.ignore();
                //添加操作信息到日志表中
                String info = operInfo.info();
                String keys[] = operInfo.params();

                //如果有参数则进行格式化info
                if(keys.length > 0){
                    //格式化info字符串
                    //根据key值去request中获取value数组
                    String mappingUrl = getUrlByMapping(method);
                    String url = request.getRequestURI();
                    String []values = getValuesByRequest(url,mappingUrl,request,keys);
                    info = StringUtils.format(info,values);
                }
                //构建日志信息
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(ret);
                String params = paramsToJson(joinPoint);
                SysOperLog sysOperLog = new SysOperLog();
                sysOperLog.setOperId(StringUtils.uuid());
                sysOperLog.setOperTime(new Date());
                sysOperLog.setTitle(title);
                sysOperLog.setOperParam(params);
                sysOperLog.setMethod(methodName);
                sysOperLog.setRequestMethod(getMappingType(method).toString());
                sysOperLog.setOperName(sysUser.getUserName());
//                sysOperLog.setOperatorType(userAgent);
                SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());
                if(sysDept!=null){
                    sysOperLog.setDeptName(sysDept.getDeptName());
                }else{
                    sysOperLog.setDeptName("");
                }

                sysOperLog.setOperUrl(request.getRequestURL().toString());
                String ip = LoginLogAspect.getIpAddress(request);
                sysOperLog.setBusinessType(getMappingType(method));
                sysOperLog.setOperIp(ip);
                for(String ignore:ignores){
                    jsonObject.remove(ignore);
                }
                Integer code = null;
                if (jsonObject != null) {
                    code = jsonObject.getInteger("code");
                    //去掉列表返回值  因为列表返回值太多  所有去掉rows
                    jsonObject.remove("rows");
                }
                sysOperLog.setJsonResult(jsonObject == null?"":jsonObject.toJSONString());

                if(code == null && jsonObject != null){


                    JSONObject jsonObject1 = jsonObject.getJSONObject("body");
                    //同上
                    jsonObject1.remove("rows");
                    String jsonResult = jsonObject1.toJSONString();
                    if (jsonResult.length()>1000){
                        jsonResult = jsonResult.substring(1000);
                    }
                    // sysOperLog.setJsonResult(jsonResult);
                    code = jsonObject1.getInteger("code");

                    if(code == null){
                        code = 1;
                    }
                }
                if (code == null) {
                    code = 200;
                }
                int status = (code== HttpStatus.SUCCESS) ? 0:1;
                sysOperLog.setStatus(status);
                sysOperLogService.save(sysOperLog);
            }else{
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    @AfterThrowing(throwing="ex",pointcut = "operLog()")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable ex) throws NoSuchMethodException {
        // 处理完请求，返回内容
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        //获取请求头
        UserAgent userAgent =  UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        try {
            LoginUser loginUser = tokenService.getLoginUser(request);
            if(loginUser == null){
                return;
            }
            SysUser sysUser = loginUser.getUser();

            Class clazz = joinPoint.getTarget().getClass();
            OperTitle operTitle = (OperTitle) clazz.getDeclaredAnnotation(OperTitle.class);
            String title = operTitle == null?"":operTitle.value();
            String targetName = clazz.getSimpleName();
            Class[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
            String methodName = joinPoint.getSignature().getName();
            Method method = clazz.getMethod(methodName,parameterTypes);

            GetMapping getMapping = method.getDeclaredAnnotation(GetMapping.class);
            GetMapping putMapping = method.getDeclaredAnnotation(GetMapping.class);

            OperInfo operInfo = method.getDeclaredAnnotation(OperInfo.class);
            if (operInfo != null) {
                //添加操作信息到日志表中
                String info = operInfo.info();
                String keys[] = operInfo.params();

                //如果有参数则进行格式化info
                if(keys.length > 0){
                    //格式化info字符串
                    //根据key值去request中获取value数组
                    String mappingUrl = getUrlByMapping(method);
                    String url = request.getRequestURI();
                    String []values = getValuesByRequest(url,mappingUrl,request,keys);
                    info = StringUtils.format(info,values);
                }
                //构建日志信息
                //构建日志信息
                String params = paramsToJson(joinPoint);
                SysOperLog sysOperLog = new SysOperLog();
                sysOperLog.setOperId(StringUtils.uuid());
                sysOperLog.setOperTime(new Date());
                sysOperLog.setTitle(title);
                sysOperLog.setOperParam(params);
                sysOperLog.setMethod(methodName);
                sysOperLog.setRequestMethod(getMappingType(method).toString());
                sysOperLog.setOperName(sysUser.getUserName());
//                sysOperLog.setOperatorType(userAgent);
                sysOperLog.setOperUrl(request.getRequestURL().toString());
                String ip = LoginLogAspect.getIpAddress(request);
                sysOperLog.setBusinessType(getMappingType(method));
                sysOperLog.setOperIp(ip);
                sysOperLog.setErrorMsg(ex.getMessage());
                sysOperLog.setStatus(1);
                sysOperLogService.save(sysOperLog);
            }else{
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    /**
     * 根据request 取出key对应的值
     * @param request
     * @param keys
     * @param url 注解上的路径
     * @return
     */
    public String[] getValuesByRequest(String url,String mappingUrl,HttpServletRequest request, String keys[]){
        Map<String,String> paramsMap = getParamsMapByUrl(mappingUrl,url);
        String values[] = new String[keys.length];
        for (int i =0;i<keys.length;i++){
            String key = keys[i];
            //去request中取值
            String value = request.getParameter(key);
            if(StringUtils.isEmpty(value)){
                //去url上取值
                value = paramsMap.get(key);
            }
            values[i] = value;
        }
        return values;
    }



    /**
     *
     * @param joinPoint
     * @return 获取请求参数列表
     */
    public String paramsToJson(JoinPoint joinPoint){
        Object objects[] = joinPoint.getArgs();
        List<Object> args = new ArrayList<>();
        for(Object object:objects){
            // request/response无法使用toJSON
            if (object instanceof HttpServletRequest) {
            } else if (object instanceof HttpServletResponse) {
            } else {
                args.add(JSON.toJSON(object));
            }

        }
        return args.toString();
    }

    /**
     * 取出映射url
     * @param method
     * @return
     */
    public String getUrlByMapping(Method method){
        GetMapping getMapping = method.getDeclaredAnnotation(GetMapping.class);
        if(getMapping!=null){
            return getMapping.value()[0];
        }
        PutMapping putMapping = method.getDeclaredAnnotation(PutMapping.class);
        if(putMapping!=null){
            return putMapping.value()[0];
        }
        PostMapping postMapping = method.getDeclaredAnnotation(PostMapping.class);
        if(putMapping!=null){
            return postMapping.value()[0];
        }
        DeleteMapping deleteMapping = method.getDeclaredAnnotation(DeleteMapping.class);
        if(deleteMapping!=null){
            return deleteMapping.value()[0];
        }
        return "";

    }


    /**
     * 取出映射url
     * @param method
     * @return
     */
    public Integer getMappingType(Method method){
        GetMapping getMapping = method.getDeclaredAnnotation(GetMapping.class);
        if(getMapping!=null){
            return 0;
        }
        PutMapping putMapping = method.getDeclaredAnnotation(PutMapping.class);
        if(putMapping!=null){
            return 2;
        }
        PostMapping postMapping = method.getDeclaredAnnotation(PostMapping.class);
        if(postMapping!=null){
            return 1;
        }
        DeleteMapping deleteMapping = method.getDeclaredAnnotation(DeleteMapping.class);
        if(deleteMapping!=null){
            return 3;
        }
        return 0;

    }

    /**
     * 根据映射url跟实际url取出restful风格的参数
     * @param mappingUrl
     * @param url
     * @return
     */
    public Map<String,String> getParamsMapByUrl(String mappingUrl,String url){
        String mappingUrlTmp = mappingUrl;
        List<String> keys = new ArrayList<>();
        while(true){

            int start = mappingUrlTmp.indexOf("/{");
            int end = mappingUrlTmp.indexOf("}");
            if(start < 0 || end <0){
                break;
            }
            keys.add(mappingUrlTmp.substring(start+2,end));
            mappingUrlTmp = mappingUrlTmp.replace(mappingUrlTmp.substring(start,end+1),"");
        }

        int tmp = url.lastIndexOf(mappingUrlTmp);
        String urlTmp = url.substring(tmp,url.length());
        urlTmp = urlTmp.replace(mappingUrlTmp,"");
        String params[] = urlTmp.split("/");
        Map<String,String> map = new HashMap<>();
        for(int i = 0;i<keys.size();i++){
            map.put(keys.get(i),params[i + 1]);
        }
        return map;
    }

//    delete/{id}
//    /position/delete/b4959f9a79bb4c5192920e28fef6d95f
public static void main(String[] args) {
    String url = "/delete";
    String mappingUrl = "delete";
    String mappingUrlTmp = mappingUrl;
    List<String> keys = new ArrayList<>();
    while(true){

        int start = mappingUrlTmp.indexOf("/{");
        int end = mappingUrlTmp.indexOf("}");
        if(start < 0 || end <0){
            break;
        }
        keys.add(mappingUrlTmp.substring(start+2,end));
        mappingUrlTmp = mappingUrlTmp.replace(mappingUrlTmp.substring(start,end+1),"");
    }

    int tmp = url.lastIndexOf(mappingUrlTmp);
    String urlTmp = url.substring(tmp,url.length());
    urlTmp = urlTmp.replace(mappingUrlTmp,"");
    String params[] = urlTmp.split("/");
    Map<String,String> map = new HashMap<>();
    for(int i = 0;i<keys.size();i++){
        map.put(keys.get(i),params[i + 1]);
    }
    System.out.println(map);


}

}
