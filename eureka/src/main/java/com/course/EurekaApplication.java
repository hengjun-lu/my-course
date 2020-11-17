package com.course;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaServer
//注解：@EnableEurekaServer 作用：将项目作为SpringCloud中的注册中心
public class EurekaApplication {

	//打印日志
	private static final Logger Log = LoggerFactory.getLogger
			(EurekaApplication.class);
//	public static void main(String[] args) {
//		SpringApplication.run(EurekaApplication.class, args);
//	}
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EurekaApplication.class);
		//获取运行环境
		Environment env = app.run(args).getEnvironment();
		Log.info("启动成功");
		Log.info("Eureka地址：\thttp:127.0.0.1:{}",env.getProperty("server.port"));
	}

}
