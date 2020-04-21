package com.slq.util;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;

import com.slq.realize.ApplicationContext;

public class MD5Util {
	
	public static String MD5Encryption(Object source,Object salt) {
		HashedCredentialsMatcher credentialsMatcher = ApplicationContext.getBean(HashedCredentialsMatcher.class);
		System.out.println(source.toString());
		System.out.println(salt.toString());
		System.out.println(credentialsMatcher.getHashIterations());
		Md5Hash md5=new Md5Hash(source, salt, credentialsMatcher.getHashIterations());
		return md5.toString();
	}
}
