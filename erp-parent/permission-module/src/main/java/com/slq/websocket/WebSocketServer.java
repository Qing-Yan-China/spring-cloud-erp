package com.slq.websocket;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.alibaba.fastjson.JSON;
import com.slq.config.SessionConfig;
import com.slq.util.DataFormatConversionUtil;
import com.slq.util.DefaultResponseData;
import com.slq.util.ResponseData;

/***
 * 接收websocket注册进来的用户
 * 
 * @author 孙陆泉
 */
@Component													
@ServerEndpoint(value="/imserver/{userId}",configurator = SessionConfig.class)
public class WebSocketServer {

	public WebSocketServer() {
		super();
		System.out.println("初始化了");
	}
	// 与客户端的连接会话 通过它来推送数据
	private Session session;
	// 存储连接的用户信息  唯一的键我们用sessionId
	public static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<String, WebSocketServer>();

	static final String CONNECT_SUCCESS = "连接成功!";

	//用户使用的会话是否登录
	public final static String AUTHENTICATED_SESSION_KEY=WebSocketServer.class.getName()+"_AUTHENTICATED_SESSION_KEY";
	
	private String session_id;
	//private String userId;// 用户的唯一标识
	// 服务连接的时候调用 什么时候可以连接成功!
	// 当登录成功的时候可以连接成功!
	//shiroSession
	private org.apache.shiro.session.Session shiro_session;
	@OnOpen
	public void onOpen(EndpointConfig endpointConfig,Session session, @PathParam("userId") String userId) {
		System.out.println("onOpen");
		org.apache.shiro.session.Session shiro_session = (org.apache.shiro.session.Session) endpointConfig.getUserProperties().get(org.apache.shiro.session.Session.class.getName());
		
		boolean fals = (boolean) shiro_session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);// 是否登录了
		//if (subject != null) {
			//org.apache.shiro.session.Session shiro_session = subject.getSession();
		//}
		try {
			if (!fals) {
				// 如果为false说明没有登录 就需要拒绝连接
				sendInfo(DefaultResponseData.NOT_LOGIN);
				System.out.println("没有登录");
			} else {
				// 如果为true则登录了存储当前会话
				//设置为登录了 因为在session销毁的时候已经获取不到数据了
				shiro_session.setAttribute(WebSocketServer.AUTHENTICATED_SESSION_KEY, true);
				this.shiro_session=shiro_session;
				this.session=session;
				this.session_id=shiro_session.getId().toString();
				webSocketMap.put(session_id, this);
				System.out.println("登录了");
				System.out.println("session_id:"+session_id);
				sendInfo(DefaultResponseData.successResponseData(CONNECT_SUCCESS));
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

	// 接收客户端发过来的消息
	@OnMessage
	public void onMessage(Session session,String message) {
		
	}

	// 通信关闭的方法
	@OnClose
	public void onClose() {
		if(webSocketMap.containsKey(session_id)) {
			System.out.println("和客户端的用户:"+session_id+",通信关闭!!");
			webSocketMap.remove(session_id);
		}
	}

	// 发送单个自定义消息
	public void sendInfo(ResponseData responsedata) throws IOException {
		System.out.println("sendInfo:"+JSON.toJSONString(responsedata));
		if (session != null) {
			System.out.println("发送成功!");
			session.getBasicRemote().sendText(DataFormatConversionUtil.toJSON(responsedata));
		}
	}
	/***
	 * 通知消息给单个人
	 */
	public static void sendMessageSingle(String userId,ResponseData responseData) {
		WebSocketServer socketServer=null;
		if(webSocketMap.containsKey(userId)) {//如果存在
			try {
				socketServer=webSocketMap.get(userId);
				socketServer.sendInfo(responseData);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/***
	 * 通知消息给所有人
	 * @param obj
	 */
	public static void sendMessageAll(Object obj) {
		WebSocketServer socketServer=null;
		Session session=null;
		String message=DataFormatConversionUtil.toJSON(obj);
		for (Entry<String, WebSocketServer> entry : webSocketMap.entrySet()) {
			socketServer=entry.getValue();
			session = socketServer.session;
			if(session!=null) {
				//发送消息到所有在线连接的用户  
				session.getAsyncRemote().sendText(message);
			}
		}
	}
}
