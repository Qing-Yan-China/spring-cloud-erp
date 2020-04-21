package com.slq.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slq.pojo.User;
import com.slq.service.IUserService;
import com.slq.util.ActiveUser;
import com.slq.util.DefaultResponseData;
import com.slq.util.ResponseData;
import com.slq.util.VerificationCodeCreateUtil;

@RestController
public class SystemController {

	@Autowired
	private IUserService userService;
	@RequestMapping("/noLogin")
	public ResponseData noLogin() {
		System.out.println("没有登录!");
		return DefaultResponseData.NOT_LOGIN;
	}
	@GetMapping("/api/activeUser")
	public ResponseData getActiveUser() {
		Subject subject=SecurityUtils.getSubject();
		ActiveUser activeUser=null;
		if(subject!=null) {
			activeUser = (ActiveUser) subject.getPrincipal();
		}
		return DefaultResponseData.successResponseData(activeUser);
	}
	@GetMapping("/api/logout")
	public ResponseData logout() {
		Subject subject=SecurityUtils.getSubject();
		if(subject!=null) {
			subject.logout();
			return DefaultResponseData.logoutResponseData();
		}else {
			return DefaultResponseData.errorResponseData("退出失败!");
		}
	}
	@PostMapping("/api/login")
	public ResponseData login(User user,String verificationCode) {
		//String server_verificationCord=(String) request.getSession().getAttribute(VerificationCodeCreateUtil.DEFAULT_VERIFICATION_CODE_KEY);
		//System.out.println(server_verificationCord);
		return userService.login(user,verificationCode);
	}
	//unauth未授权的
	@RequestMapping("/unauth")
	public ResponseData unauth() {
		System.out.println("未授权的");
		return DefaultResponseData.UNAUTH;
	}
	
	@GetMapping("/verificationImg")
	public void responseVerificationImg(HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("image/jpg");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		//生成4位数的随机验证码
		String verificationCode=VerificationCodeCreateUtil.generateVerificationCode(200, 50, 4);
		//Session session=SecurityUtils.getSubject().getSession();
		HttpSession session= request.getSession();
		System.out.println("sessionId:"+session.getId());
		session.setAttribute(VerificationCodeCreateUtil.DEFAULT_VERIFICATION_CODE_KEY,verificationCode);
		System.out.println(verificationCode);
		try {
			//最后以流的形式返回给客户端
			VerificationCodeCreateUtil.write(response.getOutputStream(), "PNG");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
