package com.course.server.service;

import com.course.server.domain.Member;
import com.course.server.domain.MemberCourse;
import com.course.server.domain.MemberCourseExample;
import com.course.server.dto.MemberCourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.MemberCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Service
public class MemberCourseService {

    @Resource
    private MemberCourseMapper  memberCourseMapper;

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        MemberCourseExample memberCourseExample = new MemberCourseExample();
        //根据sort排序
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(memberCourseExample);
        //将结果传递到pageInfo中
        PageInfo<MemberCourse> pageInfo = new PageInfo<MemberCourse>(memberCourseList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<MemberCourseDto> memberCourseDtoList = CopyUtil.copyList(memberCourseList, MemberCourseDto.class);
        //设定list
        pageDto.setList(memberCourseDtoList);
    }

    /**
     * 保存/修改方法
     * @param memberCourseDto
     */
    public void save (MemberCourseDto memberCourseDto){

        MemberCourse memberCourse = CopyUtil.copy(memberCourseDto,MemberCourse.class);
        if(StringUtils.isEmpty(memberCourseDto.getId())){
            this.insert(memberCourse);
        }else{
            this.update(memberCourse);
        }
    }

    /**
     * 插入
     * @param memberCourse
     */
    private void insert (MemberCourse memberCourse){
        Date now = new Date();
        memberCourse.setId(UuidUtil.get32UUID());
        memberCourse.setAt(now);
        memberCourseMapper.insert(memberCourse);
    }


    /**
     * 修改
     * @param memberCourse
     */
    private void update (MemberCourse memberCourse){
        Date now = new Date();
        memberCourseMapper.updateByPrimaryKey(memberCourse);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        memberCourseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 会员报名方法，先判断是否已经报名过
     * @param memberCourseDto
     * @return
     */
    public MemberCourseDto enroll(MemberCourseDto memberCourseDto){
        MemberCourse memberCourseDb=this.select(memberCourseDto.getMemberId(),memberCourseDto.getCourseId());
        if(memberCourseDb == null){
            MemberCourse memberCourse = CopyUtil.copy(memberCourseDto,MemberCourse.class);
            this.insert(memberCourse);
            //将数据库信息全部返回，包括id，at等
            return CopyUtil.copy(memberCourse,MemberCourseDto.class);
        }else {
            //如果已经报名了，则返回已报名的全部信息
            return CopyUtil.copy(memberCourseDb,MemberCourseDto.class);
        }
    }

    /**
     * 根据memberId和courseId查询
     * @param memberId
     * @param courseId
     * @return
     */
    public MemberCourse select(String memberId,String courseId){
        MemberCourseExample example = new MemberCourseExample();
        example.createCriteria().andMemberIdEqualTo(memberId).andCourseIdEqualTo(courseId);
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(memberCourseList)){
            return  null;
        }else{
            return memberCourseList.get(0);
        }
    }

    /**
     * 获取报名信息
     * @param memberCourseDto
     * @return
     */
    public MemberCourseDto getEnroll(MemberCourseDto memberCourseDto){
        MemberCourse memberCourse = this.select(memberCourseDto.getMemberId(),memberCourseDto.getCourseId());
        return  CopyUtil.copy(memberCourse,MemberCourseDto.class);
    }
}