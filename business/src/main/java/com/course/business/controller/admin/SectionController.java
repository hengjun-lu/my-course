package com.course.business.controller.admin;

import com.course.server.domain.Section;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.service.SectionService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
//返回json数据用RestController
//返回页面用Controller
@RequestMapping( "/admin/section")
public class SectionController {

    private  static final Logger LOG = LoggerFactory.getLogger(SectionController.class);
    public  static final String BUSINESS_NAME ="小节";

    @Resource
    private SectionService sectionService;

    /**
     * 页面list方法
     * @param sectionPageDto
     * @return
     */
    @PostMapping( "/list")
    //@RequestMapping( value="/section", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody SectionPageDto sectionPageDto){//@RequestBody接收json方式
        LOG.info("pageDto:{}",sectionPageDto);
        ResponseDto responseDto = new ResponseDto();
        ValidatorUtil.require(sectionPageDto.getCourseId(),"课程ID");
        ValidatorUtil.require(sectionPageDto.getChapterId(),"大章ID");
        sectionService.list(sectionPageDto);
        responseDto.setContent(sectionPageDto);
        return responseDto;
    }

    /**
     * save保存方法
     * @param sectionDto
     * @return
     */
    @PostMapping( "/save")
    //@RequestMapping( value="/section", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody SectionDto sectionDto){//@RequestBody接收json方式
        LOG.info("sectionDto:{}",sectionDto);
           // 保存校验
        ValidatorUtil.require(sectionDto.getTitle(), "标题");
        ValidatorUtil.length(sectionDto.getTitle(), "标题", 1, 50);
//        ValidatorUtil.require(sectionDto.getVideo(), "视频");
//        ValidatorUtil.length(sectionDto.getVideo(), "视频", 1, 200);


        ResponseDto responseDto = new ResponseDto();
        sectionService.save(sectionDto);
        responseDto.setContent(sectionDto);
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
        sectionService.delete(id);
        return responseDto;
    }

}
