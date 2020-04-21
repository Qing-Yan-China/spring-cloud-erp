package com.slq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.slq.common.Params;
import com.slq.overwrite.MyPageInfo;
import com.slq.pojo.User;

@Mapper
public interface UserMapper {

	List<User> getUsers();

	void insertUser(User user);

	User getUserByuId(String uId);

	Integer updateUser(User user);

	//List<User> getUserList(String keyWord);

	List<User> getUserList(Params params);

	List<Integer> getuserOwnRole(Integer id);

	int delete_userRole(Integer id);

	int setUserOwnRole(@Param("id")Integer id,@Param("newRids") List<Integer> newRids);

	void setHeadPortrai(@Param("newName")String newName, @Param("userId")Integer userId);

	int updatePwd(@Param("id")Integer id, @Param("encryptionPwd")String encryptionPwd);

	int updateActiveUser(User user);

	int checkuid(String uid);

}
