package com.course.system.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.course.server.domain.Test;
import com.course.server.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
//返回json数据用RestController
//返回页面用Controller
public class TestController {

    @Resource
    private TestService testService;
    @RequestMapping( "/test")
    //@RequestMapping( value="/test", headers="Accept=application/json",produces="application/json;charset=UTF-8")
        public List<Test> test(){
            return testService.list();
    }


}
