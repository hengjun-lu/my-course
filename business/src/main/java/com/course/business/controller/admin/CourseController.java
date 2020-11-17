package com.course.business.controller.admin;

import com.course.server.domain.Course;
import com.course.server.domain.CourseCategory;
import com.course.server.domain.CourseContent;
import com.course.server.dto.*;
import com.course.server.mapper.CourseContentMapper;
import com.course.server.service.CourseCategoryService;
import com.course.server.service.CourseService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
//返回json数据用RestController
//返回页面用Controller
@RequestMapping( "/admin/course")
public class CourseController {

    private  static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    public  static final String BUSINESS_NAME ="课程";

    @Resource
    private CourseService courseService;

    @Resource
    private CourseCategoryService courseCategoryService;

    /**
     * 页面list方法
     * @param pageDto
     * @return
     */
    @PostMapping( "/list")
    //@RequestMapping( value="/course", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody CoursePageDto pageDto){//@RequestBody接收json方式
        LOG.info("pageDto:{}",pageDto);
        ResponseDto responseDto = new ResponseDto();
        courseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * save保存方法
     * @param courseDto
     * @return
     */
    @PostMapping( "/save")
    //@RequestMapping( value="/course", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody CourseDto courseDto){//@RequestBody接收json方式
        LOG.info("courseDto:{}",courseDto);
           // 保存校验
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
       // ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);


        ResponseDto responseDto = new ResponseDto();
        courseService.save(courseDto);
        responseDto.setContent(courseDto);
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
        courseService.delete(id);
        return responseDto;
    }

    /**
     * 根据课程的ID查询分类
     * @param courseId
     * @return
     */
    @PostMapping("/list-category/{courseId}")
    public ResponseDto listCatagory(@PathVariable(value = "courseId") String courseId){
        ResponseDto responseDto = new ResponseDto();
        List<CourseCategoryDto> dtoList = courseCategoryService.listByCourse(courseId);
        responseDto.setContent(dtoList);
        return responseDto;
    }

    /**
     * 根据课程ID查询内容
     * @param id
     * @return
     */
    @GetMapping("/find-content/{id}")
    public ResponseDto findContent(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        CourseContentDto contentDto = courseService.findContent(id);
        responseDto.setContent(contentDto);
        return responseDto;

    }

    /**
     * 保存富文本内容
     * @param contentDto
     * @return
     */
    @PostMapping("save-content")
    public ResponseDto saveContent(@RequestBody CourseContentDto contentDto ){
        ResponseDto responseDto = new ResponseDto();
        courseService.saveContent(contentDto);
        return responseDto;
    }

    /**
     * 更新排序
     * @param sortDto
     * @return
     */
    @RequestMapping(value = "/sort")
    public ResponseDto saveContent(@RequestBody SortDto sortDto ){
        LOG.info("更新排序");
        ResponseDto responseDto = new ResponseDto();
        courseService.sort(sortDto);
        return responseDto;
    }

}
