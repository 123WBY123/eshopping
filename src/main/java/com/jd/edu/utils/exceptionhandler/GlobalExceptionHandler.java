package com.jd.edu.utils.exceptionhandler;

import com.jd.edu.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//先查找特定异常类 如果没有指定 则运行全局异常处理类
@ControllerAdvice
@Slf4j //该异常类信息打印到ERROR日志中
public class GlobalExceptionHandler {

    //自定义异常处理类
    @ExceptionHandler(EsException.class)
    @ResponseBody
    public R error(EsException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
