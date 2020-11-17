package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.*;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.mapper.CourseContentMapper;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Service
public class CourseService {

    private  static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    @Resource
    private CourseMapper  courseMapper;

    @Resource
    private MyCourseMapper  myCourseMapper;

    @Resource
    private CourseCategoryService courseCategoryService;

    @Resource
    private CourseContentMapper courseContentMapper;

    @Resource
    private TeacherService teacherService;

    @Resource
    private ChapterService chapterService;

    @Resource
    private SectionService sectionService;


    public void list(CoursePageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<CourseDto> courseDtoList = myCourseMapper.list(pageDto);
        PageInfo<CourseDto> pageInfo = new PageInfo<>(courseDtoList);
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setList(courseDtoList);
    }

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public PageDto listold(CoursePageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseExample courseExample = new CourseExample();
        //创建内部类（创建动态sql条件）
        CourseExample.Criteria criteria = courseExample.createCriteria();
        //web短查询视频用到的条件
        if(!StringUtils.isEmpty(pageDto.getStatus())){
          criteria.andStatusEqualTo(pageDto.getStatus());
        }
        //根据sort排序
        courseExample.setOrderByClause("sort asc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        //将结果传递到pageInfo中
        PageInfo<Course> pageInfo = new PageInfo<Course>(courseList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        //new 一个courseDtoList
        List<CourseDto> courseDtoList = new ArrayList<CourseDto>();
        for (int i = 0; i <courseList.size(); i++) {
            //取出数据库实体
            Course course = courseList.get(i);
            //new DTO实体
            CourseDto courseDto = new CourseDto();
            //copy 数据库实体到DTO实体
            BeanUtils.copyProperties(course,courseDto);
            //将courseDto加入到courseDtoList
            courseDtoList.add(courseDto);
        }
        //设定list
        pageDto.setList(pageInfo.getList());
        return pageDto;
    }

    /**
     * 保存/修改方法
     * @param courseDto
     */
    @Transactional
    public void save (CourseDto courseDto){
        Course course = CopyUtil.copy(courseDto,Course.class);
        if(StringUtils.isEmpty(courseDto.getId())){
            this.insert(course);
        }else{
            this.update(course);
        }
        //批量保存分类
        courseCategoryService.saveBatch(course.getId(),courseDto.getCategorys());

    }

    /**
     * 插入
     * @param course
     */
    private void insert (Course course){
        Date now = new Date();
        course.setId(UuidUtil.getShortUuid());
        course.setCreatedAt(now);
        course.setUpdatedAt(now);
        courseMapper.insert(course);
    }


    /**
     * 修改
     * @param course
     */
    private void update (Course course){
        Date now = new Date();
        course.setUpdatedAt(now);
        courseMapper.updateByPrimaryKey(course);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        courseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新课程的时长
     * @param courseId
     */
    public void updateCourseTime(String courseId){
        LOG.info("更新课程的时长:{}",courseId);
        myCourseMapper.updateCourseTime(courseId);
    }

    /**
     * 根据课程ID，查找课程内容
     * @param id
     * @return
     */
    public CourseContentDto findContent(String id){
        CourseContent courseContent = courseContentMapper.selectByPrimaryKey(id);
        if(courseContent == null ){
            return null;
        }
        return CopyUtil.copy(courseContent,CourseContentDto.class);
    }

    /**
     * 保存内容
     * @param contentDto
     * @return
     */
    public int saveContent (CourseContentDto contentDto){
        CourseContent content = CopyUtil.copy(contentDto,CourseContent.class);
        //先看是否更新成功，如果更新的数量为0就插入，否者就直接更新
        int i = courseContentMapper.updateByPrimaryKeyWithBLOBs(content);
        if( i == 0){
            courseContentMapper.insert(content);
        }
        return i;

    }

    /**
     * 排序
     * @param sortDto
     */
    @Transactional
    public void sort(SortDto  sortDto){
        //修改排序
        myCourseMapper.updateSort(sortDto);

        // 如果排序值变大
        if (sortDto.getNewSort() > sortDto.getOldSort()) {
            myCourseMapper.moveSortsForward(sortDto);
        }

        // 如果排序值变小
        if (sortDto.getNewSort() < sortDto.getOldSort()) {
            myCourseMapper.moveSortsBackward(sortDto);
        }

    }

    /**
     * 新视频列表查询，只查询已发布的，按创建日期倒叙
     * @param pageDto
     * @return
     */
    public List<CourseDto> listNew(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseExample example = new CourseExample();
        example.createCriteria().andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
        example.setOrderByClause("created_at desc");
        List<Course>courseList = courseMapper.selectByExample(example);
        return CopyUtil.copyList(courseList,CourseDto.class);
    }

    /**
     * 查找某一个视频，供web模块使用，只能查找已发布的
     * 包含大章小节讲师信息
     * @param id
     * @return
     */
    public CourseDto findCourse(String id){
        //根据ID查询出视频结果
        Course course = courseMapper.selectByPrimaryKey(id);
        //判断视频是否为空或者是否发布，如果未发布就返回null
        if(course == null || !CourseStatusEnum.PUBLISH.getCode().equals(course.getStatus())){
            return null;
        }
        CourseDto courseDto = CopyUtil.copy(course,CourseDto.class);

        //查询内容
        CourseContent content = courseContentMapper.selectByPrimaryKey(id);
        if(content!=null){
            courseDto.setContent(content.getContent());
        }

        //查找讲师信息
        TeacherDto teacherDto =teacherService.findById(courseDto.getTeacherId());
        courseDto.setTeachers(teacherDto);

        //查找大章信息
        List<ChapterDto> chapterDtoList= chapterService.listByCourse(id);
        courseDto.setChapters(chapterDtoList);

        //查找小节信息
        List<SectionDto> sectionDtoList = sectionService.listByCourse(id);
        courseDto.setSections(sectionDtoList);

        return courseDto;
    }
}