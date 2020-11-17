package com.course.business.web;


import com.course.server.domain.Member;
import com.course.server.dto.MemberCourseDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.MemberCourseService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("webMemberCourseController")
@RequestMapping("/web/member-course")
public class MemberCourseController {
    
    private  static final Logger LOG = LoggerFactory.getLogger(com.course.business.controller.admin.MemberCourseController.class);
    public  static final String BUSINESS_NAME ="会员报名";

    @Resource
    private MemberCourseService memberCourseService;


    /**
     * 保存，id有值时更新，无值时新增
     * @param memberCourseDto
     * @return
     */
    @PostMapping("/enroll")
    public ResponseDto enroll(@RequestBody MemberCourseDto memberCourseDto){
        LOG.info("memberCourseDto:{}",memberCourseDto);
        // 保存校验
        ValidatorUtil.require(memberCourseDto.getMemberId(), "会员ID");
        ValidatorUtil.require(memberCourseDto.getCourseId(), "课程ID");

        ResponseDto responseDto = new ResponseDto();
        memberCourseDto = memberCourseService.enroll(memberCourseDto);
        responseDto.setContent(memberCourseDto);
        return responseDto;

    }

    /**
     * 获取报名信息
     * @param memberCourseDto
     * @return
     */
    @PostMapping("/get-enroll")
    public ResponseDto getEnroll(@RequestBody MemberCourseDto memberCourseDto){
        ResponseDto responseDto = new ResponseDto();
        memberCourseDto = memberCourseService.getEnroll(memberCourseDto);
        responseDto.setContent(memberCourseDto);
        return  responseDto;

    }

}
