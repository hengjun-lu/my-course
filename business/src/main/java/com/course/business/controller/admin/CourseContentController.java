package com.course.business.controller.admin;

import com.course.server.domain.CourseContent;
import com.course.server.dto.CourseContentDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseContentService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
//返回json数据用RestController
//返回页面用Controller
@RequestMapping( "/admin/courseContent")
public class CourseContentController {

    private  static final Logger LOG = LoggerFactory.getLogger(CourseContentController.class);
    public  static final String BUSINESS_NAME ="课程内容表";

    @Resource
    private CourseContentService courseContentService;

    /**
     * 页面list方法
     * @param pageDto
     * @return
     */
    @PostMapping( "/list")
    //@RequestMapping( value="/courseContent", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody PageDto pageDto){//@RequestBody接收json方式
        LOG.info("pageDto:{}",pageDto);
        ResponseDto responseDto = new ResponseDto();
        courseContentService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * save保存方法
     * @param courseContentDto
     * @return
     */
    @PostMapping( "/save")
    //@RequestMapping( value="/courseContent", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody CourseContentDto courseContentDto){//@RequestBody接收json方式
        LOG.info("courseContentDto:{}",courseContentDto);
           // 保存校验
        ValidatorUtil.require(courseContentDto.getContent(), "课程内容");


        ResponseDto responseDto = new ResponseDto();
        courseContentService.save(courseContentDto);
        responseDto.setContent(courseContentDto);
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
        courseContentService.delete(id);
        return responseDto;
    }

}
