package com.course.server.service;

import com.course.server.domain.Teacher;
import com.course.server.domain.TeacherExample;
import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.TeacherMapper;
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
public class TeacherService {

    @Resource
    private TeacherMapper  teacherMapper;



    /**
     * 列表查询all
     */
    public  List<TeacherDto> all(){
        TeacherExample teacherExample = new TeacherExample();
        //根据sort排序
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDto.class);
        return teacherDtoList;
    }


    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        TeacherExample teacherExample = new TeacherExample();
        //根据sort排序
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
        //将结果传递到pageInfo中
        PageInfo<Teacher> pageInfo = new PageInfo<Teacher>(teacherList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDto.class);
        //设定list
        pageDto.setList(teacherDtoList);
    }

    /**
     * 保存/修改方法
     * @param teacherDto
     */
    public void save (TeacherDto teacherDto){
        //new 目标类,对其进行操作
        //Teacher teacherSave = new Teacher();
        //copy DTO数据传输到实体类中
        //teacherDto.setId(UuidUtil.getShortUuid());
        //BeanUtils.copyProperties(teacherDto,teacherSave);

        Teacher teacher = CopyUtil.copy(teacherDto,Teacher.class);
        if(StringUtils.isEmpty(teacherDto.getId())){
            this.insert(teacher);
        }else{
            this.update(teacher);
        }
    }

    /**
     * 插入
     * @param teacher
     */
    private void insert (Teacher teacher){
        teacher.setId(UuidUtil.getShortUuid());
        teacherMapper.insert(teacher);
    }


    /**
     * 修改
     * @param teacher
     */
    private void update (Teacher teacher){
        teacherMapper.updateByPrimaryKey(teacher);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        teacherMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据ID查找信息
     * @param id
     */
    public TeacherDto findById(String id){
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        return CopyUtil.copy(teacher,TeacherDto.class);
    }
}