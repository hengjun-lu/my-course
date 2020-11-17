package com.course.business.controller.admin;

import com.course.server.domain.Sms;
import com.course.server.dto.SmsDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.SmsService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
//返回json数据用RestController
//返回页面用Controller
@RequestMapping( "/admin/sms")
public class SmsController {

    private  static final Logger LOG = LoggerFactory.getLogger(SmsController.class);
    public  static final String BUSINESS_NAME ="验证码";

    @Resource
    private SmsService smsService;

    /**
     * 页面list方法
     * @param pageDto
     * @return
     */
    @PostMapping( "/list")
    //@RequestMapping( value="/sms", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody PageDto pageDto){//@RequestBody接收json方式
        LOG.info("pageDto:{}",pageDto);
        ResponseDto responseDto = new ResponseDto();
        smsService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * save保存方法
     * @param smsDto
     * @return
     */
    @PostMapping( "/save")
    //@RequestMapping( value="/sms", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto save(@RequestBody SmsDto smsDto){//@RequestBody接收json方式
        LOG.info("smsDto:{}",smsDto);
           // 保存校验
        ValidatorUtil.require(smsDto.getMobile(), "手机号");
        ValidatorUtil.length(smsDto.getMobile(), "手机号", 1, 50);
        ValidatorUtil.require(smsDto.getCode(), "验证码");
        ValidatorUtil.require(smsDto.getUse(), "用途");
        ValidatorUtil.require(smsDto.getAt(), "生成时间");
        ValidatorUtil.require(smsDto.getStatus(), "状态");


        ResponseDto responseDto = new ResponseDto();
        smsService.save(smsDto);
        responseDto.setContent(smsDto);
        return responseDto;
    }


    /**
     * delete删除方法
     * @param id
     * @return
     */
    @DeleteMapping( "/delete/{id}")
    public ResponseDto list(@PathVariable String id){//@PathVariable是用来对指定请求的URL路径里面的变量
        LOG.info("id:{}",id);
        ResponseDto responseDto = new ResponseDto();
        smsService.delete(id);
        return responseDto;
    }

}
