package com.slq.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.PingTarget;
import com.slq.common.Params;
import com.slq.constant.Constant;
import com.slq.mapper.UserMapper;
import com.slq.overwrite.MyPageInfo;
import com.slq.pojo.User;
import com.slq.realize.ApplicationContext;
import com.slq.service.IUserService;
import com.slq.util.ActiveUser;
import com.slq.util.DefaultResponseData;
import com.slq.util.MD5Util;
import com.slq.util.PinYingUtil;
import com.slq.util.ResponseData;
import com.slq.util.VerificationCodeCreateUtil;

@Service
@CacheConfig(cacheNames = "myCache") // 使用默认的缓存配置
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public ResponseData login(User user, String verificationCord) {
		String message = "登录成功!";
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
//		String server_verificationCord = session.getAttribute(VerificationCodeCreateUtil.DEFAULT_VERIFICATION_CODE_KEY).toString();
//		if(server_verificationCord!=null&&!server_verificationCord.equalsIgnoreCase(verificationCord)) {
//			message="验证码不正确!";
//			return DefaultResponseData.errorResponseData(message);
//		}
		AuthenticationToken authenticationToken = new UsernamePasswordToken(user.getUid(), user.getUpwd());
		System.out.println("是否登录了:" + subject.isAuthenticated());
//		if(subject.isAuthenticated()) {
//			ActiveUser activeUser=(ActiveUser) subject.getPrincipal();
//			return DefaultResponseData.successResponseData(message, activeUser);
//		}
		try {
			subject.login(authenticationToken);
			session = subject.getSession();
			ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
			System.out.println("session.getAttributeKeys():"+JSON.toJSONString(session.getAttributeKeys()));
			System.out.println(JSON.toJSONString(session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY")));
			System.out.println(JSON.toJSONString(session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY")));
			return DefaultResponseData.successResponseData(message, activeUser);
		} catch (AuthenticationException e) {
			if (e instanceof LockedAccountException) {
				message = e.getMessage();
			} else if (e instanceof UnknownAccountException || e instanceof IncorrectCredentialsException) {
				message = "用户名密码不正确!";
			} else {
				message = "登录失败!请联系管理员";
			}
			// e.printStackTrace();
		}

		return DefaultResponseData.errorResponseData(message);
	}

	@Cacheable
	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userMapper.getUsers();
	}

	@CachePut
	@Override
	public void setUserPage(MyPageInfo<User> userPage) {
		PageHelper.startPage(userPage.getPageNum(), userPage.getPageSize());
		// userPage.setList(userMapper.getUserList(userPage.getKeyWord()));
		System.out.println("userPage：" + JSON.toJSONString(userPage));
	}

	@Transactional
	@CacheEvict(allEntries = true)
	@Override
	public void insertUser(User user) {
		user.setUpwd(Constant.DEFULAT_USER_PASSWORD);
		user.setImgPath(Constant.DEFAULT_USER_HEADPORTRAIT);
		user.setUpwd(MD5Util.MD5Encryption(user.getUpwd(), user.getUid()));
		user.setInitial(PinYingUtil.getShortPinyin(user.getUname()));
		userMapper.insertUser(user);
		if (user.getId() == null || user.getId() <= 0) {
			throw new RuntimeException();
		}
	}

	@Cacheable
	@Override
	public User getUserByuId(String uId) {

		return userMapper.getUserByuId(uId);
	}

	@CacheEvict(allEntries = true)
	@Override
	public Integer updateUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUser(user);
	}

	@Cacheable
	@Override
	public PageInfo<User> getUserPageInfo(Params params) {
		
		User user=((ActiveUser)SecurityUtils.getSubject().getPrincipal()).getUser();
		params.setReplenishParams("id", user.getId().toString());
		return PageHelper.startPage(params.getPageNum(), params.getPageSize())
				.doSelectPageInfo(() -> userMapper.getUserList(params));
	}

	@Cacheable(keyGenerator = "keyGenerator")
	@Override
	public List<Integer> getUserOwnRole(Integer id) {
		return userMapper.getuserOwnRole(id);
	}

	@Transactional
	@CachePut(key = "'getUserOwnRole['+#id+']'")
	@Override
	public List<Integer> setUserOwnRole(Integer id, List<Integer> newRids, List<Integer> oldRids) {
		// List<Integer> oldRids=getUserOwnRole(id);
		int i;
		if (oldRids != null && !oldRids.isEmpty()) {
			i = userMapper.delete_userRole(id);
			if (i < 0)
				throw new RuntimeException();
		}
		i = userMapper.setUserOwnRole(id, newRids);
		if (i > 0) {
			return newRids;
		}
		throw new RuntimeException();
	}

	private String storageImagePath="C:\\javaEclipse作业合集\\spring-boot作业合集\\Project2\\src\\main\\resources\\static\\images";
	@Transactional
	@Override
	public String setHeadPortrai(MultipartFile file, Integer userId) 
		throws Exception{
		// 获取旧的图片名称
		String oldName = file.getOriginalFilename();
		// 获取文件后缀名
		String suffix = oldName.substring(oldName.lastIndexOf("."));
		String newName = UUID.randomUUID().toString() + suffix;
		userMapper.setHeadPortrai(newName,userId);
		File storageFile = new File(storageImagePath);
		if (!storageFile.exists()) {
			storageFile.mkdir();
		}
		File newFile = new File(storageFile, newName);
		// 写入到指定文件夹
		file.transferTo(newFile);
		return newName;
	}

	@Override
	public void equalsPwd(String upwd) {
		Subject subject = SecurityUtils.getSubject();
		User user=((ActiveUser)subject.getPrincipal()).getUser();
		String encryptionPwd = MD5Util.MD5Encryption(upwd,user.getUid());
		System.out.println("encryptionPwd:"+encryptionPwd);
		System.out.println("user.getUpwd():"+user.getUpwd());
		if(!user.getUpwd().equals(encryptionPwd)) {
			throw new RuntimeException("密码错误!");
		}
	}

	@Override
	public void updatePwd(String newUpwd) {
		Subject subject = SecurityUtils.getSubject();
		User user=((ActiveUser)subject.getPrincipal()).getUser();
		String encryptionPwd = MD5Util.MD5Encryption(newUpwd,user.getUid());
		int i =userMapper.updatePwd(user.getId(),encryptionPwd);
		if(i<=0)throw new RuntimeException();
	}
	
	@CacheEvict(allEntries = true)
	@Override
	public void updateActiveUser(User user) {
		user.setInitial(PinYingUtil.getShortPinyin(user.getUname()));
		int i = userMapper.updateActiveUser(user);
		if(i<0) {
			throw new RuntimeException("保存失败!");
		}
	}

	@Cacheable(keyGenerator = "keyGenerator")
	@Override
	public boolean checkuid(String uid) {
		return userMapper.checkuid(uid)>0?false:true;
	}

}
