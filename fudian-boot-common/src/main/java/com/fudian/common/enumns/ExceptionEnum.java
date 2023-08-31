package com.fudian.common.enumns;


import com.fudian.common.constant.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 〈异常枚举〉
 *
 * @author Mr.Hou
 * @create 2019/6/26
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    /**
     * id不能为空
     */

    INVALID_SYSTEM_LICENSE(402,"当前全宗授权过期,请联系供应商续费."),
    ERROR_SYSTEM_LICENSE(402,"获取授权信息错误,请联系开发商.."),
    INVALID_SYSTEM_LICENSE_KEY(402,"授权信息错误,请联系供应商"),
    INVALID_TOKEN(402,"token错误!"),
    ID_CANNOT_BE_NULL(402,"id不能为空!"),
    USERNAME_CANNOT_BE_NULL(400,"用户不能为空!"),
    INVALID_USERNAME_PASSWORD(402,"用户名密码错误!"),
    FILESTORAGETYPE_CANNOT_BE_NULL(500,"文件存储配置服务配置错误!"),
    USER_ACCOUNT_EXCEPTION(HttpStatus.UNAUTHORIZED,"获取用户账户异常!"),
    USER_INFO_EXCEPTION(HttpStatus.UNAUTHORIZED,"获取用户信息异常!"),
    CURRENT_USER_NOT_EXIST(HttpStatus.UNAUTHORIZED,"当前用户不存在!"),
    CURRENT_USER_HAS_DELETE(HttpStatus.UNAUTHORIZED,"当前用户已被删除!"),
//    CURRENT_USER_DISABLE(HttpStatus.UNAUTHORIZED,"当前用户已经被停用!")
    CURRENT_USER_DISABLE(402,"当前用户已经被停用!"),
    LOGIN_TIMEOUT(HttpStatus.LOGIN_TIMEOUT,"登录超时!"),
    PASSWORD_TIMEOUT(402,"该用户已过期，请重新登录");
    private int code;
    private String msg;

}
