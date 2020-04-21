package com.slq;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import com.slq.pojo.Role;
import com.slq.pojo.User;
/***
 * 主要是通过类 AutoConfigurationImportSelector来实现的自动配置
 * @author 孙陆泉
 *
 */
@SpringBootApplication //权限模块
//@EnableCaching //是cache开启缓存  开启缓存再com.slq.config下的多配置缓存配置类中加入了
//enableCacheing
//@EnableWebSocket
public class PermissionModuleApplication {

	public static void main(String[] args) {
		SpringApplication application=new SpringApplication(PermissionModuleApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}
