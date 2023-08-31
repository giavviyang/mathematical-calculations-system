package com.fudian.common.advice;

import com.fudian.common.exception.FuDianException;
import com.fudian.common.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 异常处理类
 * @ClassName HouBootExceptionHandler
 * @Author mr.hou
 * @Date 2020/4/6 12:16 下午
 * @Version 1.0.0
 **/
//@RestControllerAdvice
@ControllerAdvice
@Slf4j
public class FuDianExceptionHandler {


    //自定义异常拦截
    @ExceptionHandler(FuDianException.class)
    public ResponseEntity<CommonResult> handlerException(FuDianException e){
        log.error(e.getMessage(),e);
        if (e.getExceptionEnum()!=null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(CommonResult.error(e.getExceptionEnum()));
        }else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(CommonResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage()));
        }

    }

    // Bean 参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResult> validationErrorHandler(MethodArgumentNotValidException ex) {
        // 同样是获取BindingResult对象，然后获取其中的错误信息
        // 如果前面开启了fail_fast，事实上这里只会有一个信息
        //如果没有，则可能又多个
        List<String> errorInformation = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new CommonResult(HttpStatus.BAD_REQUEST.value(),errorInformation.toString()));
    }

    // 方法参数校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResult> validationErrorHandler(ConstraintViolationException ex) {
        List<String> errorInformation = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new CommonResult(HttpStatus.BAD_REQUEST.value(),errorInformation.toString()));
    }

    /**
     * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public CommonResult handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        return CommonResult.error("文件大小超出限制, 请压缩或降低文件质量! ");
    }

/*    @ExceptionHandler(DataIntegrityViolationException.class)
    public CommonResult handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        //System.out.println("===getCause==="+e.getCause());
        return CommonResult.error("数据库操作异常");
    }*/

    @ExceptionHandler(PoolException.class)
    public CommonResult handlePoolException(PoolException e) {
        log.error(e.getMessage(), e);
        return CommonResult.error("Redis 连接异常!");
    }

}
