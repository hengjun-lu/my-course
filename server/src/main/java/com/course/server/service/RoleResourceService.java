package com.course.server.service;

import com.course.server.domain.RoleResource;
import com.course.server.domain.RoleResourceExample;
import com.course.server.dto.RoleResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleResourceMapper;
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
public class RoleResourceService {

    @Resource
    private RoleResourceMapper  roleResourceMapper;

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        //根据sort排序
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(roleResourceExample);
        //将结果传递到pageInfo中
        PageInfo<RoleResource> pageInfo = new PageInfo<RoleResource>(roleResourceList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleResourceDto> roleResourceDtoList = CopyUtil.copyList(roleResourceList, RoleResourceDto.class);
        //设定list
        pageDto.setList(roleResourceDtoList);
    }

    /**
     * 保存/修改方法
     * @param roleResourceDto
     */
    public void save (RoleResourceDto roleResourceDto){
        //new 目标类,对其进行操作
        //RoleResource roleResourceSave = new RoleResource();
        //copy DTO数据传输到实体类中
        //roleResourceDto.setId(UuidUtil.getShortUuid());
        //BeanUtils.copyProperties(roleResourceDto,roleResourceSave);

        RoleResource roleResource = CopyUtil.copy(roleResourceDto,RoleResource.class);
        if(StringUtils.isEmpty(roleResourceDto.getId())){
            this.insert(roleResource);
        }else{
            this.update(roleResource);
        }
    }

    /**
     * 插入
     * @param roleResource
     */
    private void insert (RoleResource roleResource){
        roleResource.setId(UuidUtil.getShortUuid());
        roleResourceMapper.insert(roleResource);
    }


    /**
     * 修改
     * @param roleResource
     */
    private void update (RoleResource roleResource){
        roleResourceMapper.updateByPrimaryKey(roleResource);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        roleResourceMapper.deleteByPrimaryKey(id);
    }
}