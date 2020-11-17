package com.course.server.service;

import com.course.server.domain.*;
import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.RoleMapper;
import com.course.server.mapper.RoleResourceMapper;
import com.course.server.mapper.RoleUserMapper;
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
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class  RoleService {

    private  static final Logger LOG = LoggerFactory.getLogger(RoleService.class);

    @Resource
    private RoleMapper  roleMapper;

    @Resource
    private RoleResourceMapper roleResourceMapper;

    @Resource
    private RoleUserMapper roleUserMapper;

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        RoleExample roleExample = new RoleExample();
        //根据sort排序
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        //将结果传递到pageInfo中
        PageInfo<Role> pageInfo = new PageInfo<Role>(roleList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleDto> roleDtoList = CopyUtil.copyList(roleList, RoleDto.class);
        //设定list
        pageDto.setList(roleDtoList);
    }

    /**
     * 保存/修改方法
     * @param roleDto
     */
    public void save (RoleDto roleDto){

        Role role = CopyUtil.copy(roleDto,Role.class);
        if(StringUtils.isEmpty(roleDto.getId())){
            this.insert(role);
        }else{
            this.update(role);
        }
    }

    /**
     * 插入
     * @param role
     */
    private void insert (Role role){
        role.setId(UuidUtil.getShortUuid());
        roleMapper.insert(role);
    }


    /**
     * 修改
     * @param role
     */
    private void update (Role role){
        roleMapper.updateByPrimaryKey(role);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        roleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 角色ID和资源树关联
     * @param roleDto
     */
    @Transactional
    public void saveResource(RoleDto roleDto){
        //获取角色ID和资源ID数组
        String roleId = roleDto.getId();
        List<String>resourceIds =  roleDto.getResourceIds();
        //清空库里当前角色下的记录
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        LOG.info("根据角色ID删除关联资源开始");
        roleResourceMapper.deleteByExample(example);
        LOG.info("根据角色ID删除关联资源结束");

        //保存角色资源
        for (int i = 0; i <resourceIds.size() ; i++) {
            RoleResource roleResource = new RoleResource();
            roleResource.setId(UuidUtil.get32UUID());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceIds.get(i));
            roleResourceMapper.insert(roleResource);
        }
    }

    /**
     * 按照角色加载资源树
     * @param roleId
     * @return
     */
    public List<String> listResource(String roleId){
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleResource>roleResourceList =roleResourceMapper.selectByExample(example);
        List<String> resourceIdList = new ArrayList<>();
        for (int i = 0; i <roleResourceList.size() ; i++) {
            resourceIdList.add(roleResourceList.get(i).getResourceId());
        }
        return resourceIdList;
    }

    /**
     * 保存角色用户
     * @param roleDto
     */
    public void saveUser(RoleDto roleDto){
        String roleId = roleDto.getId();//角色ID
        List<String> userIdList = roleDto.getUserIds();//用户ID集合

        //根据角色ID清空当前角色下的数据记录
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleUserMapper.deleteByExample(example);

        //保存角色用户
        for (int i = 0; i <userIdList.size() ; i++) {
            RoleUser roleUser = new RoleUser();
            roleUser.setId(UuidUtil.getShortUuid());
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userIdList.get(i));
            roleUserMapper.insert(roleUser);
        }
    }

    public List<String> listUser(String roleId){
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(example);
        ArrayList<String> UserIdList = new ArrayList<>();
        for (int i = 0; i <roleUserList.size() ; i++) {
            UserIdList.add(roleUserList.get(i).getUserId());
        }
        return UserIdList;
    }

}