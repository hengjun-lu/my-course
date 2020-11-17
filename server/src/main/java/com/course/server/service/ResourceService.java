package com.course.server.service;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.Resource;
import com.course.server.domain.ResourceExample;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ResourceMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {
    private  static final Logger LOG = LoggerFactory.getLogger(ResourceService.class);

    @javax.annotation.Resource
    private ResourceMapper  resourceMapper;

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ResourceExample resourceExample = new ResourceExample();
        //根据sort排序
        List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);
        //将结果传递到pageInfo中
        PageInfo<Resource> pageInfo = new PageInfo<Resource>(resourceList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        //设定list
        pageDto.setList(resourceDtoList);
    }

    /**
     * 保存/修改方法
     * @param resourceDto
     */
    public void save (ResourceDto resourceDto){
        Resource resource = CopyUtil.copy(resourceDto,Resource.class);
        if(StringUtils.isEmpty(resourceDto.getId())){
            this.insert(resource);
        }else{
            this.update(resource);
        }
    }

    /**
     * 插入（ID是自定义的，不是自动生成的）
     * @param resource
     */
    private void insert (Resource resource){

        //resource.setId(UuidUtil.getShortUuid());
        resourceMapper.insert(resource);
    }


    /**
     * 修改
     * @param resource
     */
    private void update (Resource resource){
        resourceMapper.updateByPrimaryKey(resource);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        resourceMapper.deleteByPrimaryKey(id);
    }


    /**
     * 保存资源树
     * @param json
     */
    @Transactional
    public void saveJson(String json){
        //将json转化成list
        List<ResourceDto> jsonList = JSON.parseArray(json, ResourceDto.class);
        //树形结构的json转化成列表结构的list
        ArrayList<ResourceDto> list = new ArrayList<>();
        if(!CollectionUtils.isEmpty(jsonList)){
            for (ResourceDto d:jsonList) {
                d.setParent("");
                add(list,d);
            }
        }
        LOG.info("共{}条",list.size());
        LOG.info("共{}条",list);
        //清空数据表
        resourceMapper.deleteByExample(null);
        for (int i = 0; i <list.size() ; i++) {
            this.insert(CopyUtil.copy(list.get(i),Resource.class));
        }
    }

    /**
     * 递归，将树形结构的节点全部取出来，放到list中
     * @param list
     * @param dto
     */
    public void add(List<ResourceDto> list,ResourceDto dto){
        list.add(dto);
        if(!CollectionUtils.isEmpty(dto.getChildren())){
            for(ResourceDto d : dto.getChildren()){
                d.setParent(dto.getId());
                add(list,d);
            }
        }
        LOG.info("共{}条",list);
    }

    /**
     * 按约定将列表转成树
     * 要求：ID要正序排列
     * @return
     */
    public List<ResourceDto> loadTree() {
        ResourceExample example = new ResourceExample();
        example.setOrderByClause("id asc");
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        for (int i = resourceDtoList.size() - 1; i >= 0; i--) {
            // 当前要移动的记录
            ResourceDto child = resourceDtoList.get(i);
            // 如果当前节点没有父节点，则不用往下了
            if (StringUtils.isEmpty(child.getParent())) {
                continue;
            }
            // 查找父节点
            for (int j = i - 1; j >= 0; j--) {
                ResourceDto parent = resourceDtoList.get(j);
                if (child.getParent().equals(parent.getId())) {
                    if (CollectionUtils.isEmpty(parent.getChildren())) {
                        parent.setChildren(new ArrayList<>());
                    }
                    // 添加到最前面，否则会变成倒序，因为循环是从后往前循环的
                    parent.getChildren().add(0, child);

                    // 子节点找到父节点后，删除列表中的子节点
                    resourceDtoList.remove(child);
                }
            }
        }
        return resourceDtoList;
    }

}