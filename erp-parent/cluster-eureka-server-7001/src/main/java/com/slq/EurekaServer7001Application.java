package com.slq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer  //服务注册中心注解
public class EurekaServer7001Application {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServer7001Application.class, args);
	}
}
