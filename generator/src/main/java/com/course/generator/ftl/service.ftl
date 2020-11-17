package com.course.server.service;

import com.course.server.domain.${Domain};
import com.course.server.domain.${Domain}Example;
import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.${Domain}Mapper;
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
<#list typeSet as type>
    <#if type='Date'>
import java.util.Date;
    </#if>
</#list>

@Service
public class ${Domain}Service {

    @Resource
    private ${Domain}Mapper  ${domain}Mapper;

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        //根据sort排序
        <#list fieldList as field>
            <#if field.nameHump=='sort'>
        ${domain}Example.setOrderByClause("sort asc");
            </#if>
        </#list>
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);
        //将结果传递到pageInfo中
        PageInfo<${Domain}> pageInfo = new PageInfo<${Domain}>(${domain}List);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<${Domain}Dto> ${domain}DtoList = CopyUtil.copyList(${domain}List, ${Domain}Dto.class);
        //设定list
        pageDto.setList(${domain}DtoList);
    }

    /**
     * 保存/修改方法
     * @param ${domain}Dto
     */
    public void save (${Domain}Dto ${domain}Dto){
        //new 目标类,对其进行操作
        //${Domain} ${domain}Save = new ${Domain}();
        //copy DTO数据传输到实体类中
        //${domain}Dto.setId(UuidUtil.getShortUuid());
        //BeanUtils.copyProperties(${domain}Dto,${domain}Save);

        ${Domain} ${domain} = CopyUtil.copy(${domain}Dto,${Domain}.class);
        if(StringUtils.isEmpty(${domain}Dto.getId())){
            this.insert(${domain});
        }else{
            this.update(${domain});
        }
    }

    /**
     * 插入
     * @param ${domain}
     */
    private void insert (${Domain} ${domain}){
        <#list typeSet as type>
            <#if type='Date'>
        Date now = new Date();
            </#if>
        </#list>
        <#list fieldList as field>
            <#if field.nameHump=='createdAt'>
        ${domain}.setCreatedAt(now);
            </#if>
            <#if field.nameHump=='updatedAt'>
        ${domain}.setUpdatedAt(now);
            </#if>
        </#list>
        ${domain}.setId(UuidUtil.getShortUuid());
        ${domain}Mapper.insert(${domain});
    }


    /**
     * 修改
     * @param ${domain}
     */
    private void update (${Domain} ${domain}){
        <#list typeSet as type>
            <#if type='Date'>
        Date now = new Date();
            </#if>
        </#list>
        <#list fieldList as field>
            <#if field.nameHump=='updatedAt'>
        ${domain}.setUpdatedAt(now);
            </#if>
        </#list>
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}