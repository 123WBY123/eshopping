package com.jd.edu.utils.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//自定义异常类
@Data
@AllArgsConstructor //有参构造方法
@NoArgsConstructor //无参构造方法
public class EsException extends RuntimeException {

    private Integer code;   //状态码
    private String msg; //异常信息
}
