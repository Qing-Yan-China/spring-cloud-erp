package com.slq.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.slq.listener.MySessionListener;
import com.slq.realm.UserRealm;

@Configuration
@ConfigurationProperties(prefix = "shiro")
public class ShiroConfig {

	
	//算法
		private String hashAlogorithName ="Md5"; //Md5Hash.class.getClass().getName();//new Md5Hash().getClass().getName();
	    //散列次数
		private int hashIterations = 2;
	    private String targetBeanName = "shiroFilter";
	    private boolean targetFilterLifecycle = true;
	    //未登录路由跳转的url
	    private String loginUrl = "/noLogin";// 未登陆跳转
	    private String unauthorizedUrl = "/unauth";// 未授权跳转
	    
	    private String[] filterChain;
	    /***
	      * 凭证匹配器
	     * @return
	     */
	    @Bean
	    public CredentialsMatcher credentialsMatcher() {
	    	HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
	    	//设置加密算法
	    	hashedCredentialsMatcher.setHashAlgorithmName(hashAlogorithName);
	    	//设置加密次数
	    	hashedCredentialsMatcher.setHashIterations(hashIterations);
	    	
	    	return hashedCredentialsMatcher;
	    }
	    /***
	     * realm  
	     * @param credentialsMatcher
	     * @return
	     */
		@Bean
	    public UserRealm userRealm(CredentialsMatcher credentialsMatcher) {
	    	UserRealm userRealm=new UserRealm();
	    	//凭证匹配器
	    	userRealm.setCredentialsMatcher(credentialsMatcher);
	    	return  userRealm;
	    }
		/***
		 * 缓存管理
		 * @return
		 */
		@Bean
		public CacheManager configCacheManager() {
			CacheManager cacheManager=new MemoryConstrainedCacheManager();
			return cacheManager;
		}
		/***
		 * 安全管理器
		 * @param realm
		 * @param cacheManager
		 * @return
		 */
	    @Bean
	    public SecurityManager configSecurityManagerBean(Realm realm,CacheManager cacheManager,SessionManager sessionManager) {
	    	DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
	    	
	    	securityManager.setRealm(realm);//设置获取数据域
	    	securityManager.setCacheManager(cacheManager);//设置缓存管理
	    	securityManager.setSessionManager(sessionManager);//设置session管理
	    	return securityManager;
	    }
	    /***
	     * 配置session管理器
	     * @return
	     */
	    @Bean
	    public SessionManager sessionManager() {
	    	DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
	    	List<SessionListener> sessionListeners=new ArrayList<SessionListener>();
	    	sessionListeners.add(new MySessionListener());
	    	sessionManager.setSessionListeners(sessionListeners);
	    	return sessionManager;
	    }
	    /***
	     * 配置shiro代理的过滤器
	     */
//	    @Bean
//	    public FilterRegistrationBean<DelegatingFilterProxy> configShiroFilterProxyBean(SecurityManager securityManager){
//	    	
//	    	DelegatingFilterProxy delegatingFilterProxy=new DelegatingFilterProxy();
//	    	delegatingFilterProxy.setBeanName("shiroFilter");
//	    	delegatingFilterProxy.setTargetFilterLifecycle(targetFilterLifecycle);
//	    	
//	    	FilterRegistrationBean<DelegatingFilterProxy> bean=new FilterRegistrationBean<DelegatingFilterProxy>(delegatingFilterProxy);
//	    	 Collection<String> servletNames = new ArrayList<>();
//	         servletNames.add(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
//	         bean.setServletNames(servletNames);
//	    	return bean;
//	    }
	   //设置shiroFilter过滤器链
	    @Bean(value="shiroFilter")
	    public ShiroFilterFactoryBean configShiroFilterFactoryBean(SecurityManager securityManager) {
	    	ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
	    	//登录跳转的路径
	    	shiroFilterFactoryBean.setLoginUrl(loginUrl);
	    	//设置安全管理器
	    	shiroFilterFactoryBean.setSecurityManager(securityManager);
	    	//设置没有授权进入的页面
	    	shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
	    	Map<String, String> filterChainMap=new HashMap<String, String>();
	    	if (filterChain!=null) {
	    		for (String filter : filterChain) {
					String [] strs=filter.split("=");
					System.out.println(Arrays.toString(strs));
					filterChainMap.put(strs[0], strs[1]);
				}
			}else {
				filterChainMap.put("/api/logout","anon");
				filterChainMap.put("/upload/headPortrai","anon");
				filterChainMap.put("/api/activeUser", "anon");
				filterChainMap.put("/noLogin","anon");//未登录页面
				filterChainMap.put("/api/login", "anon");//登录验证
				filterChainMap.put("/unauth", "anon");//未授权
				filterChainMap.put("/verificationImg", "anon");//获取验证码的
				filterChainMap.put("/static/**","anon");//放行所有的静态资源
				filterChainMap.put("/**","authc");//其他的所有都进行拦截
			}
	    	//设置过滤器链的拦截映射
	    	shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
	    	return shiroFilterFactoryBean;
	    }
}
