package com.course.server.service;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.User;
import com.course.server.domain.UserExample;
import com.course.server.dto.LoginUserDto;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.UserDto;
import com.course.server.dto.PageDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.UserMapper;
import com.course.server.mapper.my.MyUserMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.security.auth.login.LoginContext;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private  static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserMapper  userMapper;
    @Resource
    private MyUserMapper myUserMapper;

    /**
     * 页面显示数据list方法
     * @param pageDto
     * @return
     */
    public void list(PageDto pageDto){
        //pageNum:起始条数，pageSize：每页大小
        //插件分页语句的规则，嗲用startPage方法后执行的第一个select语句会进行分页
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        UserExample userExample = new UserExample();
        //根据sort排序
        List<User> userList = userMapper.selectByExample(userExample);
        //将结果传递到pageInfo中
        PageInfo<User> pageInfo = new PageInfo<User>(userList);
        //设定总数
        pageDto.setTotal(pageInfo.getTotal());
        List<UserDto> userDtoList = CopyUtil.copyList(userList, UserDto.class);
        //设定list
        pageDto.setList(userDtoList);
    }

    /**
     * 保存/修改方法
     * @param userDto
     */
    public void save (UserDto userDto){
        User user = CopyUtil.copy(userDto,User.class);
        if(StringUtils.isEmpty(userDto.getId())){
            this.insert(user);
        }else{
            this.update(user);
        }
    }

    /**
     * 插入
     * @param user
     */
    private void insert (User user){
        User userDb = this.selectByLoginName(user.getLoginName());
        //如果结果不为空，就提示信息已存在
        if(userDb != null){
            throw  new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
        user.setId(UuidUtil.getShortUuid());
        userMapper.insert(user);
    }


    /**
     * 修改
     * @param user
     */
    private void update (User user){
        user.setPassword(null);
        //Selective :对字段进心非空判断，在更新，如果没有值就不更新，原理如同mybatis的if
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 删除
     * @param id
     */
    public void delete (String id){
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据登录名字查询信息
     * @param loginName
     * @return
     */
    public User selectByLoginName(String loginName){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        //判断结果是否为空
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }else{
            return userList.get(0);
        }
    }

    /**
     * 重置密码
     * @param userDto
     */
    public void savePassword(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        userMapper.updateByPrimaryKeySelective(user);

    }

    /**
     * 登录校验
     * @param userDto
     */
    public LoginUserDto login(UserDto userDto){
        User user = selectByLoginName(userDto.getLoginName());//根据登录名字查询信息
        if(user == null){
            LOG.info("//用户不存在{}",userDto.getLoginName());
            //返回错误信息
            throw  new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }else{
            if(user.getPassword().equals(userDto.getPassword())){
                //登录成功
                LoginUserDto loginUserDto = CopyUtil.copy(user,LoginUserDto.class);
                //为登录用户获取权限
                setAuth(loginUserDto);
                return loginUserDto;

            }else{
                LOG.info("密码不正确,输入密码{},数据库密码{}",user.getPassword(),userDto.getPassword());
                //返回错误信息
                throw  new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }

    }

    /**
     * 权限校验
     * @param loginUserDto
     */
    private  void setAuth(LoginUserDto loginUserDto){
       List<ResourceDto> resourceDtoList = myUserMapper.findResources(loginUserDto.getId());
       loginUserDto.setResource(resourceDtoList);

       //整理所有权限的请求，用于接口拦截
       //hashset：HashSet 是一个没有重复元素的集合。
       //它是由HashMap实现的，不保证元素的顺序，而且HashSet允许使用 null 元素。
       HashSet<String> requestSet = new HashSet<>();
       for (int i = 0; i <resourceDtoList.size() ; i++) {
           ResourceDto resourceDto = resourceDtoList.get(i);
            String arrayString = resourceDto.getRequest();
            List<String>requestList = JSON.parseArray(arrayString,String.class);
            if(!CollectionUtils.isEmpty(requestList)){
                requestSet.addAll(requestList);
            }
        }
        LOG.info("有权限的请求:{}",requestSet);
        loginUserDto.setRequests(requestSet);
    }

}