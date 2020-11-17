package com.course.server.service;

import com.course.server.domain.CourseCategory;
import com.course.server.domain.CourseCategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CourseCategoryMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseCategoryService {

    @Resource
    private CourseCategoryMapper  courseCategoryMapper;

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        //根据sort排序
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);
        //将结果传递到pageInfo中
        PageInfo<CourseCategory> pageInfo = new PageInfo<CourseCategory>(courseCategoryList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseCategoryDto> courseCategoryDtoList = CopyUtil.copyList(courseCategoryList, CourseCategoryDto.class);
        //设定list
        pageDto.setList(courseCategoryDtoList);
    }

    /**
     * 保存/修改方法
     * @param courseCategoryDto
     */
    public void save (CourseCategoryDto courseCategoryDto){

        CourseCategory courseCategory = CopyUtil.copy(courseCategoryDto,CourseCategory.class);
        if(StringUtils.isEmpty(courseCategoryDto.getId())){
            this.insert(courseCategory);
        }else{
            this.update(courseCategory);
        }
    }

    /**
     * 插入
     * @param courseCategory
     */
    private void insert (CourseCategory courseCategory){
        courseCategory.setId(UuidUtil.getShortUuid());
        courseCategoryMapper.insert(courseCategory);
    }


    /**
     * 修改
     * @param courseCategory
     */
    private void update (CourseCategory courseCategory){
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据某一课程，先清空课程分类，再保存课程分类
     * @param dtoList
     */
    @Transactional
    public void saveBatch(String courseId, List<CategoryDto> dtoList){
        //清空后保存
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(example);

        for (int i = 0; i <dtoList.size() ; i++) {
            CategoryDto categoryDto = dtoList.get(i);
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UuidUtil.getShortUuid());
            if(courseCategory.getId() !="" || courseCategory.getId()!=null){
                courseCategory.setCourseId(courseId);
                courseCategory.setCategoryId(categoryDto.getId());
                insert(courseCategory);
            }


        }
    }

    /**
     * 根据课程ID查找当前课程的分类
     * @param courseId
     * @return
     */
    public List<CourseCategoryDto> listByCourse (String courseId){
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        courseCategoryExample.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);
        return CopyUtil.copyList(courseCategoryList,CourseCategoryDto.class);
    }

}