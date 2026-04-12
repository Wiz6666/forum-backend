package com.weisizhang.forumbackend.exception;

import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.common.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//全局统一异常处理
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /*
    * 处理自定义已知异常
    * 以body形式返回
    * */
    @ResponseBody
    @ExceptionHandler(ApplicationException.class)
    public AppResult handleApplicationException(ApplicationException e){
        //打印异常
        e.printStackTrace();
        //记录日志
        log.error(e.getMessage());
        //返回错误
        if (e.getErrorResult() != null) {
            return e.getErrorResult();
        }
        // 如果只传了字符串，也就是 errorResult 是空的，默认返回 1000 (FAILED) 和手写的异常描述
        return AppResult.failed(e.getMessage());
    }

    //处理全局未普获的异常
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public AppResult handleException(Exception e){
        //打印异常
        e.printStackTrace();
        //记录日志
        log.error(e.getMessage());
        //
        if (e.getCause() == null) {
            return AppResult.failed(ResultCode.ERROR_SERVICES);
        }
        //默认返回的异常信息
        return AppResult.failed(e.getMessage());
    }

}
