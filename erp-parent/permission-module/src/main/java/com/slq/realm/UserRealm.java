package com.slq.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.slq.constant.Constant;
import com.slq.pojo.User;
import com.slq.service.IPermissionService;
import com.slq.service.IRoleService;
import com.slq.service.IUserService;
import com.slq.util.ActiveUser;

public class UserRealm extends AuthorizingRealm{

	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IPermissionService permissionService;
	/***
	 * 获取权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo= new SimpleAuthorizationInfo();
		authorizationInfo.addRoles(activeUser.getRoles());
		authorizationInfo.addStringPermissions(activeUser.getPermissions());
		return authorizationInfo;
	}

	/***
	 * 获取登录数据
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String uId=token.getPrincipal().toString();
		User user = userService.getUserByuId(uId);
		if(user==null)return null;
		if(user.getUserStatus()==Constant.USER_STATE_FREEZE) {
			throw new LockedAccountException("user freeze exception");
		}
		List<String> roles=new ArrayList<String>();
		List<String> permissions=new ArrayList<String>();
		if(user.getIsadmin()==Constant.USER_TYPE_SUPER) {
			roles.add("admin");
			permissions.add("*:*");
		}else {
			//查询权限
			//查询角色
			roles = roleService.getUserOwnRole(user.getId());
			permissions = permissionService.getUserOwnPermission(user.getId());
		}
		System.out.println(user);
		ActiveUser activeUser=new ActiveUser(user, roles, permissions);
		AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo
				(activeUser, user.getUpwd(), ByteSource.Util.bytes(user.getUid()), this.getName());
		return authenticationInfo;
	}

}
