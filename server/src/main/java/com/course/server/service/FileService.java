package com.course.server.service;

import com.course.server.domain.File;
import com.course.server.domain.FileExample;
import com.course.server.dto.FileDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.FileMapper;
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
import java.util.Collection;
import java.util.List;
import java.util.Date;

@Service
public class FileService {

    @Resource
    private FileMapper  fileMapper;

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        FileExample fileExample = new FileExample();
        //根据sort排序
        List<File> fileList = fileMapper.selectByExample(fileExample);
        //将结果传递到pageInfo中
        PageInfo<File> pageInfo = new PageInfo<File>(fileList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<FileDto> fileDtoList = CopyUtil.copyList(fileList, FileDto.class);
        //设定list
        pageDto.setList(fileDtoList);
    }

    /**
     * 保存/修改方法
     * @param fileDto
     */
    public void save (FileDto fileDto){
        File file = CopyUtil.copy(fileDto,File.class);
        //根据key保存文件记录
        File fileDb = selectByKey(fileDto.getKey());
        //如果根据文件key找不到文件，就插入文件，否则就进行修改分片记录
        if(fileDb == null){
            this.insert(file);
        }else{
            fileDb.setShardIndex(fileDto.getShardIndex());
            this.update(fileDb);
        }
    }

    /**
     * 插入
     * @param file
     */
    private void insert (File file){
        Date now = new Date();
        file.setCreatedAt(now);
        file.setUpdatedAt(now);
        file.setId(UuidUtil.getShortUuid());
        fileMapper.insert(file);
    }


    /**
     * 修改
     * @param file
     */
    private void update (File file){
        Date now = new Date();
        file.setUpdatedAt(now);
        fileMapper.updateByPrimaryKey(file);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        fileMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据文件key 查找记录
     * @param key
     * @return
     */
    public File selectByKey(String key){
        FileExample example = new FileExample();
        example.createCriteria().andKeyEqualTo(key);
        List<File> fileList = fileMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(fileList)){
            return  null;
        }else{
            return fileList.get(0);
        }
    }

    /**
     * 根据文件的标识查询数据库的记录
     * @param key
     * @return
     */
    public FileDto findByKey(String key){
        return CopyUtil.copy(selectByKey(key),FileDto.class);
    }
}