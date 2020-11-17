package com.course.system.controller.admin;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.User;
import com.course.server.dto.*;
import com.course.server.service.UserService;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
//返回json数据用RestController
//返回页面用Controller
@RequestMapping( "/admin/user")
public class UserController {

    private  static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    public  static final String BUSINESS_NAME ="用户";

    @Resource
    private UserService userService;

    @Resource
    public RedisTemplate redisTemplate;

    /**
     * 页面list方法
     * @param pageDto
     * @return
     */
    @PostMapping( "/list")
    //@RequestMapping( value="/user", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody PageDto pageDto){//@RequestBody接收json方式
        LOG.info("pageDto:{}",pageDto);
        ResponseDto responseDto = new ResponseDto();
        userService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * save保存方法
     * @param userDto
     * @return
     */
    @PostMapping( "/save")
    //@RequestMapping( value="/user", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto save(@RequestBody UserDto userDto){//@RequestBody接收json方式
        LOG.info("userDto:{}",userDto);
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
           // 保存校验
        ValidatorUtil.require(userDto.getLoginName(), "登陆名");
        ValidatorUtil.length(userDto.getLoginName(), "登陆名", 1, 50);
        ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
        ValidatorUtil.require(userDto.getPassword(), "密码");


        ResponseDto responseDto = new ResponseDto();
        userService.save(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }


    /**
     * delete删除方法
     * @param id
     * @return
     */
    @DeleteMapping( "/delete/{id}")
    public ResponseDto list(@PathVariable String id){//@PathVariable是用来对指定请求的URL路径里面的变量
        LOG.info("id:{}",id);
        ResponseDto responseDto = new ResponseDto();
        userService.delete(id);
        return responseDto;
    }


    /**
     * save-password 重置密码
     * @param userDto
     * @return
     */
    @PostMapping( "/save-password")
    public ResponseDto savePassword(@RequestBody UserDto userDto){//@RequestBody接收json方式
        LOG.info("userDto:{}",userDto);
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        // 保存校验
        ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
        ValidatorUtil.require(userDto.getPassword(), "密码");

        ResponseDto responseDto = new ResponseDto();
        userService.savePassword(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }


    /**
     * save-password 登录
     * @param userDto
     * @return
     */
    @PostMapping( "/login")
    public ResponseDto login(@RequestBody UserDto userDto, HttpServletRequest request){//@RequestBody接收json方式
        LOG.info("用户登录开始");
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        // 保存校验
        //获取验证码token，从缓存request.getSession()请求中获取属性token值
        //String imageCode = (String) request.getSession().getAttribute(userDto.getImageCodeToken());
        String imageCode = (String) redisTemplate.opsForValue().get(userDto.getImageCodeToken());
        LOG.info("从redis中获取验证码:{}",imageCode);
        if(StringUtils.isEmpty(imageCode)){
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            LOG.info("用户登录失败，验证码已过期");
            return responseDto;
        }
        if(!imageCode.toLowerCase().equals(userDto.getImageCode().toLowerCase())){
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码不正确");
            LOG.info("用户登录失败，验证码不正确");
            return responseDto;
        }else{
            //验证通过后从缓存中移除验证码
            //request.getSession().removeAttribute(userDto.getImageCodeToken());
            redisTemplate.delete(userDto.getImageCodeToken());
        }

        LoginUserDto loginUserDto = userService.login(userDto);
        //生成一个token值UUID
        String token = UuidUtil.get32UUID();
        loginUserDto.setToken(token);
        //获取当前用户的会话缓存
        //request.getSession().setAttribute(Constants.LOGIN_USER,loginUserDto);
        redisTemplate.opsForValue().set(token, JSON.toJSON(loginUserDto), 3600, TimeUnit.SECONDS);
        LOG.info("用户登录成功后存放token到redis中:{}",token);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }


    /**
     * logout 退出登录
     * @param
     * @return
     */
    @GetMapping( "/logout/{token}")
    public ResponseDto logout(@PathVariable String token){//@RequestBody接收json方式
        ResponseDto responseDto = new ResponseDto();
        //退出登录后删除当前用户的会话缓存
        //request.getSession().removeAttribute(Constants.LOGIN_USER);
        redisTemplate.delete(token);
        LOG.info("从redis中删除token:{}",token);
        return responseDto;
    }
}
