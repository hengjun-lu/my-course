package com.course.system.controller;

import com.course.server.dto.ResponseDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    private  static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResponseDto validatorExceptionHandler(ValidatorException e){

        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        // responseDto.setMessage(e.getMessage());不能给出太明确的信息
        LOG.warn(e.getMessage());//打印错误信息
        responseDto.setMessage("请求参数有误!");
        return responseDto;
    }



    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseDto businessExceptionHandler(BusinessException e){

        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        LOG.error("业务一场:{}",e.getCode().getDesc());//打印异常码的描述
        responseDto.setMessage(e.getCode().getDesc());
        return responseDto;


    }
}
