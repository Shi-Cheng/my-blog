package com.blog.myblog.config;

import com.blog.myblog.exception.BusinessException;
import com.blog.myblog.response.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理、数据预处理等
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 校验异常统一处理 参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResponse validExceptionHandler(BindException e) {
        CommonResponse commonResp = new CommonResponse();
        LOG.warn("参数校验失败：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }

    /**
     * 校验异常统一处理  业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResponse validExceptionHandler(BusinessException e) {
        CommonResponse commonResp = new CommonResponse();
        LOG.warn("业务异常：{}", e.getCode().getDesc());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getCode().getDesc());
        return commonResp;
    }

    /**
     * 校验异常统一处理 系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResponse validExceptionHandler(Exception e) {
        CommonResponse commonResp = new CommonResponse();
        LOG.error("系统异常：", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统出现异常，请联系管理员");
        return commonResp;
    }
}
