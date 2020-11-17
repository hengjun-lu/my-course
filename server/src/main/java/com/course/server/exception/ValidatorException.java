package com.course.server.exception;

/**
 * 自定义异常
 */
public class ValidatorException extends RuntimeException{
    //写一个有参数的构造方法，调用被继承的父级的构造方法
    public  ValidatorException(String message){
        super(message);
    }
}
