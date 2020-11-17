package com.course.system.controller.admin;

import com.course.server.domain.Resource;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.RoleDto;
import com.course.server.service.ResourceService;
import com.course.server.service.RoleService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

//import javax.annotation.Resource;
import javax.management.relation.RoleStatus;
import java.util.List;

@RestController
//返回json数据用RestController
//返回页面用Controller
@RequestMapping( "/admin/resource")
public class ResourceController {

    private  static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);
    public  static final String BUSINESS_NAME ="资源";

    @javax.annotation.Resource
    private ResourceService resourceService;

    /**
     * 页面list方法
     * @param pageDto
     * @return
     */
    @PostMapping( "/list")
    //@RequestMapping( value="/resource", headers="Accept=application/json",produces="application/json;charset=UTF-8")
    public ResponseDto list(@RequestBody PageDto pageDto){//@RequestBody接收json方式
        LOG.info("pageDto:{}",pageDto);
        ResponseDto responseDto = new ResponseDto();
        resourceService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * save保存方法
     * @param jsonStr
     * @return
     */
    @PostMapping( "/save")
    public ResponseDto save(@RequestBody String jsonStr){//@RequestBody接收json方式
        LOG.info("jsonStr:{}",jsonStr);
           // 保存校验
        ValidatorUtil.require(jsonStr,"资源");
        ResponseDto responseDto = new ResponseDto();
        resourceService.saveJson(jsonStr);
        responseDto.setContent(jsonStr);
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
        resourceService.delete(id);
        return responseDto;
    }

    /**
     * 加载资源树
     * @return
     */
    @GetMapping("/load-tree")
    public ResponseDto loadTree(){
        ResponseDto responseDto = new ResponseDto();
        List<ResourceDto>resourceDtoList  = resourceService.loadTree();
        responseDto.setContent(resourceDtoList);
        return responseDto;
    }


}
