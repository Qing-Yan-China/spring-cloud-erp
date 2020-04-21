package com.slq.config;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.alibaba.fastjson.JSON;
/***
 * 为了让webSocketServer能够获取 当前session
 */
public class SessionConfig extends ServerEndpointConfig.Configurator{

	@Override
	public void modifyHandshake(ServerEndpointConfig endpointConfig, HandshakeRequest request, HandshakeResponse response) {
		Session session=SecurityUtils.getSubject().getSession();
		System.out.println("-------modifyHandshake--------");
		System.out.println(JSON.toJSONString(session.getAttributeKeys()));
		System.out.println(session.getId());
		System.out.println("---------------");
		endpointConfig.getUserProperties().put(Session.class.getName(),session);
	}

	
//	@Override
//	public void modifyHandshake(ServerEndpointConfig endpointConfig, HandshakeRequest request, HandshakeResponse response) {
//		super.modifyHandshake(endpointConfig, request, response);
//		Session session=SecurityUtils.getSubject().getSession();
//		System.out.println("-------modifyHandshake--------");
//		System.out.println(JSON.toJSONString(session.getAttributeKeys()));
//		System.out.println(session.getId());
//		System.out.println("---------------");
//		endpointConfig.getUserProperties().put(Session.class.getName(),session);
//	}

	
}
