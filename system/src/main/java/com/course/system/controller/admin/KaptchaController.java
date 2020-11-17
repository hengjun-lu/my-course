package com.course.system.controller.admin;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin/kaptcha")
public class KaptchaController {
    public static final String BUSINESS_NAME = "图片验证码";
    //Qualifier的意思是合格者，通过这个标示，表明了哪个实现类才是我们所需要的
    @Qualifier("getDefaultKaptcha")
    @Autowired
    DefaultKaptcha defaultKaptcha;

    @Resource
    public RedisTemplate redisTemplate;

    @GetMapping("/image-code/{imageCodeToken}")
    public void imageCode(@PathVariable(value = "imageCodeToken") String imageCodeToken,
        HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        //new一个输出流
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生成验证码字符串
            String createText = defaultKaptcha.createText();
            // 将生成的验证码放入会话缓存中，后续验证的时候用到
            //request.getSession().setAttribute(imageCodeToken, createText);
            //opsForValue():放入缓存 将生成的验证码放入redis缓存中，后续验证的时候用到
            redisTemplate.opsForValue().set(imageCodeToken, createText, 300, TimeUnit.SECONDS);
            // 使用验证码字符串生成验证码图片
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        //no-store用于防止重要的信息被无意的发布。在请求消息中发送将使得请求和响应消息都不使用缓存。
        httpServletResponse.setHeader("Cache-Control", "no-store");
        //设定消息头指示请求或响应消息不能缓存
        httpServletResponse.setHeader("Pragma", "no-cache");
        //使用Expires实体报头域指定页面过期时间
        httpServletResponse.setDateHeader("Expires", 0);
        //对应的文件类型
        httpServletResponse.setContentType("image/jpeg");
        //new 一个输出流对象
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        //流文件写入
        responseOutputStream.write(captchaChallengeAsJpeg);
        //流文件清空
        responseOutputStream.flush();
        //流文件关闭
        responseOutputStream.close();

    }


}
