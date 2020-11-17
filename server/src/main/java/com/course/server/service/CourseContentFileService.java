package com.course.server.service;

import com.course.server.domain.CourseContentFile;
import com.course.server.domain.CourseContentFileExample;
import com.course.server.dto.CourseContentFileDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseContentFileMapper;
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
public class CourseContentFileService {

    @Resource
    private CourseContentFileMapper  courseContentFileMapper;

    /**
     * 列表查询
     */
    public List<CourseContentFileDto> list(String courseId) {
        CourseContentFileExample example = new CourseContentFileExample();
        CourseContentFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<CourseContentFile> fileList = courseContentFileMapper.selectByExample(example);
        return CopyUtil.copyList(fileList, CourseContentFileDto.class);
    }

    /**
     * 保存/修改方法
     * @param courseContentFileDto
     */
    public void save (CourseContentFileDto courseContentFileDto){
        //new 目标类,对其进行操作
        //CourseContentFile courseContentFileSave = new CourseContentFile();
        //copy DTO数据传输到实体类中
        //courseContentFileDto.setId(UuidUtil.getShortUuid());
        //BeanUtils.copyProperties(courseContentFileDto,courseContentFileSave);

        CourseContentFile courseContentFile = CopyUtil.copy(courseContentFileDto,CourseContentFile.class);
        if(StringUtils.isEmpty(courseContentFileDto.getId())){
            this.insert(courseContentFile);
        }else{
            this.update(courseContentFile);
        }
    }

    /**
     * 插入
     * @param courseContentFile
     */
    private void insert (CourseContentFile courseContentFile){
        courseContentFile.setId(UuidUtil.getShortUuid());
        courseContentFileMapper.insert(courseContentFile);
    }


    /**
     * 修改
     * @param courseContentFile
     */
    private void update (CourseContentFile courseContentFile){
        courseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        courseContentFileMapper.deleteByPrimaryKey(id);
    }
}