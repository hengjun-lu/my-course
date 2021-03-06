package com.course.business.controller.admin;

import com.course.server.domain.CourseCategory;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseCategoryService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
//返回json数据用RestController
//返回页面用Controller
@RequestMapping( "/admin/courseCategory")
public class CourseCategoryController {

    private  static final Logger LOG = LoggerFactory.getLogger(CourseCategoryController.class);
    public  static final String BUSINESS_NAME ="课程分类关系";

    @Resource
    private CourseCategoryService courseCategoryService;

    /**
     * 页面list方法
     * @param pageDto
     * @return
     */
    @PostMapping( "/list")
    //@RequestMapping( value="/courseCategory", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody PageDto pageDto){//@RequestBody接收json方式
        LOG.info("pageDto:{}",pageDto);
        ResponseDto responseDto = new ResponseDto();
        courseCategoryService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * save保存方法
     * @param courseCategoryDto
     * @return
     */
    @PostMapping( "/save")
    //@RequestMapping( value="/courseCategory", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody CourseCategoryDto courseCategoryDto){//@RequestBody接收json方式
        LOG.info("courseCategoryDto:{}",courseCategoryDto);
           // 保存校验


        ResponseDto responseDto = new ResponseDto();
        courseCategoryService.save(courseCategoryDto);
        responseDto.setContent(courseCategoryDto);
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
        courseCategoryService.delete(id);
        return responseDto;
    }

}
