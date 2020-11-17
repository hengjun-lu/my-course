package com.course.server.service;

import com.course.server.domain.Test;
import com.course.server.domain.TestExample;
import com.course.server.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper  testMapper;

    public List<Test> list(){
        TestExample testExample = new TestExample();
        //createCriteria 相当于where条件
        testExample.createCriteria().andIdEqualTo("1");
        //setOrderByClause 相当于sql排序
        testExample.setOrderByClause("id desc");
        return testMapper.selectByExample(testExample);
    }
}
