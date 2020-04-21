package com.slq.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.slq.common.Params;
import com.slq.overwrite.MyPageInfo;
import com.slq.pojo.User;
import com.slq.util.ResponseData;

public interface IUserService {

	List<User> getUsers();

	void setUserPage(MyPageInfo<User> userPage);

	void insertUser(User user);

	User getUserByuId(String uId);

	Integer updateUser(User user);

	PageInfo<User> getUserPageInfo(Params params);

	List<Integer> getUserOwnRole(Integer id);

	List<Integer> setUserOwnRole(Integer id, List<Integer> newRids, List<Integer> oldRids);

	ResponseData login(User user, String verificationCord);

	String setHeadPortrai(MultipartFile file, Integer userId)throws Exception;

	void equalsPwd(String upwd);

	void updatePwd(String newUpwd);

	void updateActiveUser(User user);

	boolean checkuid(String uid);

}
