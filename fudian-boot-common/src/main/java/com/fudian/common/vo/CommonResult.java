package com.fudian.common.vo;

import cn.hutool.core.util.ObjectUtil;
import com.fudian.common.enumns.ExceptionEnum;
import io.swagger.annotations.ApiModel;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @Description 通用返回实体类
 * @ClassName CommonResult
 * @Author mr.hou
 * @Date 2020/4/6 12:31 下午
 * @Version 1.0.0
 **/
@ApiModel(value="接口返回对象", description="接口返回对象")
public class CommonResult extends HashMap<String, Object> {


    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    public static final String Time_TAG = "timestamp";

    public CommonResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(Time_TAG, System.currentTimeMillis());
        if (ObjectUtil.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }


    public CommonResult(int code, String msg) {
        new CommonResult(code,msg,null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static CommonResult success(String msg, Object data) {
        return new CommonResult(HttpStatus.OK.value(), msg, data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static CommonResult success(String msg) {
        return CommonResult.success(msg, null);
    }


    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static CommonResult success() {
        return CommonResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static CommonResult success(Object data) {
        return CommonResult.success("操作成功", data);
    }


    /**
     * 返回错误消息
     *
     * @return
     */
    public static CommonResult error() {
        return CommonResult.error("操作失败");
    }
    public static CommonResult error(ExceptionEnum em) {
        return  CommonResult.error(em.getCode(),em.getMsg());
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static CommonResult error(String msg) {
        return CommonResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static CommonResult error(String msg, Object data) {
        return new CommonResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static CommonResult error(int code, String msg) {
        return new CommonResult(code, msg, null);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    public static CommonResult toAjax(int rows) {
        return rows > 0 ? CommonResult.success() : CommonResult.error();
    }
    /**
     * 响应返回结果
     *
     * @param b 影响行数
     * @return 操作结果
     */
    public static CommonResult toAjax(boolean b) {
        return b ? CommonResult.success() : CommonResult.error();
    }

}
