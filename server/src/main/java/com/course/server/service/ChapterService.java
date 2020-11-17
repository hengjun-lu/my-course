package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.ChapterPageDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
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
public class ChapterService {

    @Resource
    private ChapterMapper  chapterMapper;

    /**
     * 页面显示数据list方法
     * @param chapterPageDto
     * @return
     */
    public PageDto list(ChapterPageDto chapterPageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(chapterPageDto.getPage(),chapterPageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
        //创建规则(只能创建一次)
        ChapterExample.Criteria criteria = chapterExample.createCriteria();
        //createCriteria().andXxxEqualTo(value):创建规则。添加xxx字段等于value条件
        if(!StringUtils.isEmpty(chapterPageDto.getCourseId())){
            criteria.andCourseIdEqualTo(chapterPageDto.getCourseId());
        }
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        //将结果传递到pageInfo中
        PageInfo<Chapter> pageInfo = new PageInfo<Chapter>(chapterList);
        //设定总数
        chapterPageDto.setTotal(pageInfo.getTotal());
        //new 一个chapterDtoList
        List<ChapterDto> chapterDtoList = new ArrayList<ChapterDto>();
        for (int i = 0; i <chapterList.size() ; i++) {
            //取出数据库实体
            Chapter chapter = chapterList.get(i);
            //new DTO实体
            ChapterDto chapterDto = new ChapterDto();
            //copy 数据库实体到DTO实体
            BeanUtils.copyProperties(chapter,chapterDto);
            //将chapterDto加入到chapterDtoList
            chapterDtoList.add(chapterDto);
        }
        //设定list
        chapterPageDto.setList(pageInfo.getList());
        return chapterPageDto;
    }

    /**
     * 保存/修改方法
     * @param chapterDto
     */
    public void save (ChapterDto chapterDto){
        //new 目标类,对其进行操作
       // Chapter chapterSave = new Chapter();
        //copy DTO数据传输到实体类中
        //chapterDto.setId(UuidUtil.getShortUuid());
        //BeanUtils.copyProperties(chapterDto,chapterSave);

        Chapter chapter = CopyUtil.copy(chapterDto,Chapter.class);
        if(StringUtils.isEmpty(chapterDto.getId())){
            this.insert(chapter);
        }else{
            this.update(chapter);
        }
    }

    /**
     * 插入
     * @param chapter
     */
    private void insert (Chapter chapter){
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }


    /**
     * 修改
     * @param chapter
     */
    private void update (Chapter chapter){
        chapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        chapterMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据ID查找某一视频下的所有章节
     * @param courseId
     * @return
     */
    public  List<ChapterDto> listByCourse(String courseId){
        ChapterExample example = new ChapterExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<Chapter> chapterList = chapterMapper.selectByExample(example);
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        return chapterDtoList;
    }

}
