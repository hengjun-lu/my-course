package com.course.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

	//打印日志
	private static final Logger Log = LoggerFactory.getLogger
			(GatewayApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(GatewayApplication.class);
		//获取运行环境
		Environment env = app.run(args).getEnvironment();
		Log.info("启动成功");
		Log.info("Gateway地址：\thttp:127.0.0.1:{}",env.getProperty("server.port"));
	}

	/**
	 * 配置跨域
	 */
	@Bean
	public CorsWebFilter corsWebFilter(){
		//1、 new 一个 CorsConfiguration添加Cors配置信息
		CorsConfiguration config = new CorsConfiguration();
		//2）、是否发送cookie信息，允许cookie跨域
		config.setAllowCredentials(Boolean.TRUE);
		//3）、允许的请求方式
		config.addAllowedMethod("*");
		// 4）允许向该服务器提交请求的URI，*表示全部允许
		config.addAllowedOrigin("*");
		// 5）、允许的头信息
		config.addAllowedHeader("*");
		// 1小时内不需要再预检（发OPTIONS请求）
		config.setMaxAge(3600L);
		//与跨域配置信息映射的容器
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
		source.registerCorsConfiguration("/**", config);

		return new CorsWebFilter(source);
	}


}
