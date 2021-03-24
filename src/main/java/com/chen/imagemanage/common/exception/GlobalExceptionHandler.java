package com.chen.imagemanage.common.exception;

import com.chen.imagemanage.common.api.ApiResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

public class GlobalExceptionHandler {
    /**
     * 捕获自定义异常
     */
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public ApiResult<Map<String, Object>> handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return ApiResult.failed(e.getErrorCode());
        }
        return ApiResult.failed(e.getMessage());
    }
}
