package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.SectionPageDto;
import com.course.server.mapper.SectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;

@Service
public class SectionService {

    @Resource
    private SectionMapper  sectionMapper;

    @Resource
    private CourseService  courseService;

    /**
     * 页面显示数据list方法
     * @param sectionPageDto
     * @return
     */
    public PageDto list(SectionPageDto sectionPageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，调用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(sectionPageDto.getPage(),sectionPageDto.getSize());
        SectionExample sectionExample = new SectionExample();
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if(!StringUtils.isEmpty(sectionPageDto.getCourseId())){
            criteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
        }
        if(!StringUtils.isEmpty(sectionPageDto.getChapterId())){
            criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }
        //根据sort排序
        sectionExample.setOrderByClause("sort asc");
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        //将结果传递到pageInfo中
        PageInfo<Section> pageInfo = new PageInfo<Section>(sectionList);
        //设定总数
        sectionPageDto.setTotal(pageInfo.getTotal());
        //new 一个sectionDtoList
        List<SectionDto> sectionDtoList = new ArrayList<SectionDto>();
        for (int i = 0; i <sectionList.size() ; i++) {
            //取出数据库实体
            Section section = sectionList.get(i);
            //new DTO实体
            SectionDto sectionDto = new SectionDto();
            //copy 数据库实体到DTO实体
            BeanUtils.copyProperties(section,sectionDto);
            //将sectionDto加入到sectionDtoList
            sectionDtoList.add(sectionDto);
        }
        //设定list
        sectionPageDto.setList(pageInfo.getList());
        return sectionPageDto;
    }

    /**
     * 保存/修改方法
     * @param sectionDto
     */
    @Transactional
    public void save (SectionDto sectionDto){

        Section section = CopyUtil.copy(sectionDto,Section.class);
        if(StringUtils.isEmpty(sectionDto.getId())){
            this.insert(section);
        }else{
            this.update(section);
        }
        courseService.updateCourseTime(sectionDto.getCourseId());
    }

    /**
     * 插入
     * @param section
     */
    private void insert (Section section){
        Date now = new Date();
        section.setCreatedAt(now);
        section.setUpdatedAt(now);
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }


    /**
     * 修改
     * @param section
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private void update (Section section){
        Date now = new Date();
        section.setUpdatedAt(now);
        sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        sectionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据ID查找某一视频下的小节信息
     * @return
     */
    public List<SectionDto> listByCourse(String courseId){
        SectionExample example = new SectionExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<Section> sectionList = sectionMapper.selectByExample(example);
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList,SectionDto.class);
        return  sectionDtoList;
    }

}
