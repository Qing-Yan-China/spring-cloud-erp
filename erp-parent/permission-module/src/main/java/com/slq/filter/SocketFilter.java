package com.slq.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
/***
 * 失效
 */
//@ServletComponentScan
//@Component
//@WebFilter(urlPatterns = "/imserver/**")
public class SocketFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
//		Session session = SecurityUtils.getSubject().getSession();
//		System.out.println("-------doFilter--------");
//		System.out.println(JSON.toJSONString(session.getAttributeKeys()));
//		System.out.println(session.getId());
//		System.out.println("---------------");
		filterChain.doFilter(request, response);
	}

}
