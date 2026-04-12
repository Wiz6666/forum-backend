package com.weisizhang.forumbackend.exception;

import com.weisizhang.forumbackend.common.AppResult;

public class ApplicationException extends RuntimeException {
    //自定义错误
    protected AppResult errorResult;

    //指定状态码,异常回复
    public ApplicationException(AppResult errorResult){
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }

    //自定义异常描述
    public ApplicationException(String message){
        super(message);
    }
    //自定义异常信息
    public ApplicationException(Throwable cause){
        super(cause);
    }
    //自定义异常描述, 异常信息
    public ApplicationException(String message,Throwable cause){
        super(message,cause);
    }

    public AppResult getErrorResult() {
        return errorResult;
    }
}
