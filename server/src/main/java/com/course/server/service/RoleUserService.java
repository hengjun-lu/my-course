package com.course.server.service;

import com.course.server.domain.RoleUser;
import com.course.server.domain.RoleUserExample;
import com.course.server.dto.RoleUserDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleUserMapper;
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
public class RoleUserService {

    @Resource
    private RoleUserMapper  roleUserMapper;

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        RoleUserExample roleUserExample = new RoleUserExample();
        //根据sort排序
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);
        //将结果传递到pageInfo中
        PageInfo<RoleUser> pageInfo = new PageInfo<RoleUser>(roleUserList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleUserDto> roleUserDtoList = CopyUtil.copyList(roleUserList, RoleUserDto.class);
        //设定list
        pageDto.setList(roleUserDtoList);
    }

    /**
     * 保存/修改方法
     * @param roleUserDto
     */
    public void save (RoleUserDto roleUserDto){
        //new 目标类,对其进行操作
        //RoleUser roleUserSave = new RoleUser();
        //copy DTO数据传输到实体类中
        //roleUserDto.setId(UuidUtil.getShortUuid());
        //BeanUtils.copyProperties(roleUserDto,roleUserSave);

        RoleUser roleUser = CopyUtil.copy(roleUserDto,RoleUser.class);
        if(StringUtils.isEmpty(roleUserDto.getId())){
            this.insert(roleUser);
        }else{
            this.update(roleUser);
        }
    }

    /**
     * 插入
     * @param roleUser
     */
    private void insert (RoleUser roleUser){
        roleUser.setId(UuidUtil.getShortUuid());
        roleUserMapper.insert(roleUser);
    }


    /**
     * 修改
     * @param roleUser
     */
    private void update (RoleUser roleUser){
        roleUserMapper.updateByPrimaryKey(roleUser);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        roleUserMapper.deleteByPrimaryKey(id);
    }
}