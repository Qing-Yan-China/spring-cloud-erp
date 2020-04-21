package com.slq.listener;

import java.io.IOException;

import javax.servlet.annotation.WebListener;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.slq.pojo.User;
import com.slq.util.ActiveUser;
import com.slq.util.DefaultResponseData;
import com.slq.websocket.WebSocketServer;

@WebListener
public class MySessionListener implements SessionListener{

	@Override
	public void onStart(Session session) {
		System.out.println(session.getId());
		System.out.println("session---onStart");
		
	}

	@Override
	public void onStop(Session session) {
		// TODO Auto-generated method stub
		closeCurrentSocketServer(session);
	}

	@Override
	public void onExpiration(Session session) {
		// TODO Auto-generated method stub
		closeCurrentSocketServer(session);
	}

	@Autowired
	private WebSocketServer socketServer;
	
	public void closeCurrentSocketServer(Session session) {
		System.out.println("关闭连接了!!!");
		//判断是否经过身份验证了
		//判断是否需要退出webSocket通信
		Object obj = session.getAttribute(WebSocketServer.AUTHENTICATED_SESSION_KEY);
		System.out.println("session.getAttributeKeys():"+JSON.toJSONString(session.getAttributeKeys()));
		System.out.println(JSON.toJSONString(session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY")));
		System.out.println(JSON.toJSONString(session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY")));
		if(obj!=null&&((boolean)obj)) {//说明是登录到期了  关闭连接
			
			//User user=((ActiveUser)SecurityUtils.getSubject().getPrincipal()).getUser();
			System.out.println("用户已经登录了 但是现在要退出了:");
			try {
				WebSocketServer socketServer=this.socketServer.webSocketMap.get(session.getId());
				socketServer.sendInfo(DefaultResponseData.LOGIN_HAS_EXPIRED);
				socketServer.onClose();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
