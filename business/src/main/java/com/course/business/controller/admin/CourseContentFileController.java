package com.course.business.controller.admin;

import com.course.server.domain.CourseContentFile;
import com.course.server.dto.CourseContentFileDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseContentFileService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
//返回json数据用RestController
//返回页面用Controller
@RequestMapping( "/admin/course-content-file")
public class CourseContentFileController {

    private  static final Logger LOG = LoggerFactory.getLogger(CourseContentFileController.class);
    public  static final String BUSINESS_NAME ="课程文件内容";

    @Resource
    private CourseContentFileService courseContentFileService;

    /**
     * 列表查询
     */
    @GetMapping("/list/{courseId}")
    public ResponseDto list(@PathVariable String courseId) {
        ResponseDto responseDto = new ResponseDto();
        List<CourseContentFileDto> fileDtoList =  courseContentFileService.list(courseId);
        responseDto.setContent(fileDtoList);
        return responseDto;
    }

    /**
     * save保存方法
     * @param courseContentFileDto
     * @return
     */
    @PostMapping( "/save")
    //@RequestMapping( value="/courseContentFile", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody CourseContentFileDto courseContentFileDto){//@RequestBody接收json方式
        LOG.info("courseContentFileDto:{}",courseContentFileDto);
           // 保存校验
        ValidatorUtil.require(courseContentFileDto.getCourseId(), "课程ID");
        ValidatorUtil.length(courseContentFileDto.getUrl(), "地址", 1, 100);
        ValidatorUtil.length(courseContentFileDto.getName(), "文件名", 1, 10);


        ResponseDto responseDto = new ResponseDto();
        courseContentFileService.save(courseContentFileDto);
        responseDto.setContent(courseContentFileDto);
        return responseDto;
    }


    /**
     * delete删除方法
     * @param id
     * @return
     */
    @DeleteMapping( "/delete/{id}")
    public ResponseDto delete(@PathVariable String id){//@PathVariable是用来对指定请求的URL路径里面的变量
        LOG.info("id:{}",id);
        ResponseDto responseDto = new ResponseDto();
        courseContentFileService.delete(id);
        return responseDto;
    }

}
