package com.course.server.util;

import com.course.server.exception.ValidatorException;
import org.springframework.util.StringUtils;

public class ValidatorUtil {

    /**
     * 判断校验空(null or "")
     * @param str
     * @param fieldName
     */
    public static void require(Object str,String fieldName){
        if(StringUtils.isEmpty(str)){
            throw new ValidatorException(fieldName + "不能为空");
        }
    }

    /**
     * 判断名字的长度校验
     * @param str
     * @param fieldName
     * @param min
     * @param max
     */
    public static void length(String str,String fieldName,int min, int max){
        if(!StringUtils.isEmpty(str)){
            return;
        }
        int length = 0;
        if(!StringUtils.isEmpty(str)){
            length = str.length();
        }
        if(length<min || length >max){
            throw new ValidatorException(fieldName +"的长度是" +min + "~" + max + "位" );
        }
    }



}
