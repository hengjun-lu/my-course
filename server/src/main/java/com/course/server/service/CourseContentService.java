package com.course.server.service;

import com.course.server.domain.CourseContent;
import com.course.server.domain.CourseContentExample;
import com.course.server.dto.CourseContentDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseContentMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseContentService {

    @Resource
    private CourseContentMapper  courseContentMapper;

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseContentExample courseContentExample = new CourseContentExample();
        //根据sort排序
        List<CourseContent> courseContentList = courseContentMapper.selectByExample(courseContentExample);
        //将结果传递到pageInfo中
        PageInfo<CourseContent> pageInfo = new PageInfo<CourseContent>(courseContentList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseContentDto> courseContentDtoList = CopyUtil.copyList(courseContentList, CourseContentDto.class);
        //设定list
        pageDto.setList(courseContentDtoList);
    }

    /**
     * 保存/修改方法
     * @param courseContentDto
     */
    public void save (CourseContentDto courseContentDto){

        CourseContent courseContent = CopyUtil.copy(courseContentDto,CourseContent.class);
        if(StringUtils.isEmpty(courseContentDto.getId())){
            this.insert(courseContent);
        }else{
            this.update(courseContent);
        }
    }

    /**
     * 插入
     * @param courseContent
     */
    private void insert (CourseContent courseContent){
        courseContent.setId(UuidUtil.getShortUuid());
        courseContentMapper.insert(courseContent);
    }


    /**
     * 修改
     * @param courseContent
     */
    private void update (CourseContent courseContent){
        courseContentMapper.updateByPrimaryKeyWithBLOBs(courseContent);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        courseContentMapper.deleteByPrimaryKey(id);
    }
}