package com.vkls.wisdom.exception;

import com.vkls.wisdom.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    //全局异常处理，@ExceptionHandler中指定要处理的异常类，因为是处理全局异常，所以在这里写的是所有异常的父类Exception.class
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail().message("执行了全局异常处理");
    }

    //特定异常处理器 如果发生了属于ArithmeticException的异常，执行该方法，其他的异常执行全局异常处理器定义的方法
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result ArithmeticError(Exception e){
        e.printStackTrace();
        return Result.fail().message("发生了Arithmetic异常");
    }

    //自定义异常处理器
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result MyError(MyException e){
        e.printStackTrace();
        return Result.fail(null).code(e.getCode()).message(e.getMsg());
    }
}