package com.course.system.controller.admin;

import com.course.server.domain.Role;
import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.RoleService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
//返回json数据用RestController
//返回页面用Controller
@RequestMapping( "/admin/role")
public class RoleController {

    private  static final Logger LOG = LoggerFactory.getLogger(RoleController.class);
    public  static final String BUSINESS_NAME ="角色";

    @Resource
    private RoleService roleService;

    /**
     * 页面list方法
     * @param pageDto
     * @return
     */
    @PostMapping( "/list")
    //@RequestMapping( value="/role", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody PageDto pageDto){//@RequestBody接收json方式
        LOG.info("pageDto:{}",pageDto);
        ResponseDto responseDto = new ResponseDto();
        roleService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * save保存方法
     * @param roleDto
     * @return
     */
    @PostMapping( "/save")
    //@RequestMapping( value="/role", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto save(@RequestBody RoleDto roleDto){//@RequestBody接收json方式
        LOG.info("roleDto:{}",roleDto);
           // 保存校验
        ValidatorUtil.require(roleDto.getName(), "角色");
        ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
        ValidatorUtil.require(roleDto.getDesc(), "描述");
        ValidatorUtil.length(roleDto.getDesc(), "描述", 1, 100);


        ResponseDto responseDto = new ResponseDto();
        roleService.save(roleDto);
        responseDto.setContent(roleDto);
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
        roleService.delete(id);
        return responseDto;
    }

    /**
     * 保存角色资源关联
     * @param roleDto
     * @return
     */
    @PostMapping("/save-resource")
    public ResponseDto saveResource(@RequestBody RoleDto roleDto){
        LOG.info("保存角色资源关联开始");
        ResponseDto<RoleDto>  responseDto = new ResponseDto<>();
        roleService.saveResource(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 根据角色Id查询资源树显示
     * 加载已关联的资源
     * @param roleId
     * @return
     */
    @GetMapping("/list-resource/{roleId}")
    public ResponseDto listResource(@PathVariable String roleId){
        LOG.info("根据角色Id加载资源树开始");
        ResponseDto responseDto = new ResponseDto();
        List<String> resourceIdList =roleService.listResource(roleId);
        responseDto.setContent(resourceIdList);
        return responseDto;
    }

    /**
     * 保存角色用户
     * @param roleDto
     * @return
     */
    @PostMapping("/save-user")
    public ResponseDto saveUser(@RequestBody RoleDto roleDto){
        LOG.info("保存角色用户关联开始");
        ResponseDto<RoleDto> responseDto = new ResponseDto<>();
        roleService.saveUser(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 根据角色ID加载用户开始
     * @param roleId
     * @return
     */
    @GetMapping("/list-user/{roleId}")
    public ResponseDto listUser(@PathVariable String roleId){
        LOG.info("根据角色ID加载用户开始");
        ResponseDto responseDto = new ResponseDto();
        List<String> userIdList = roleService.listUser(roleId);
        responseDto.setContent(userIdList);
        return responseDto;
    }

}
