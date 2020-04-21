package com.slq.config;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/***
 * 多缓存配置类
 * @author 孙陆泉
 *
 */
@Configuration
@EnableCaching  //开启缓存  ehcache需要
public class More_CacheConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate redisTemplate=new RedisTemplate<String, Object>();
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		StringRedisSerializer stringRedisSerializer =new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}
	
	//因为再application.yml中已经配置了缓存管理
	//当然也可以去除application.yml中的配置  使用java代码来配置它
//	@Bean
//	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
//		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
//		cacheManagerFactoryBean.setShared(true);
//		return cacheManagerFactoryBean;
//	}
//	@Bean
//	public EhCacheCacheManager eCacheCacheManager(EhCacheManagerFactoryBean bean) {
//		return new EhCacheCacheManager(bean.getObject());
//	}
	/***
	 * 配置自定义缓存key生成器
	 * 
	 */
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			
			@Override
			public Object generate(Object target, Method method, Object... params) {
				Object obj=method.getName()+Arrays.asList(params);
				System.out.println("----------------------------"+obj);
				return obj;
			}
		};
	}
}
