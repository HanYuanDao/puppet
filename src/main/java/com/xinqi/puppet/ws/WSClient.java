package com.xinqi.puppet.ws;



import com.google.gson.Gson;
import com.xinqi.puppet.common.UserInfoVO;
import com.xinqi.puppet.netty.gateway.tcp.server.TradeTcpServerFactory;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/8 13:12
 **/
@Slf4j
@ClientEndpoint
public class WSClient {

	private final static String SPLIT_FLAG = "@";
	private final static int LENGTH_MSG = 2;
	private final static String SPLIT_FLAG_4_MSG = ",";

	private final static Gson GSON = new Gson();

	private Session session;

	private volatile boolean ctpWorking;
	private String containerId;
	private UserInfoVO ctpUserInfoVO;

	private WSClient ALIVE_CLIENT;

	@OnOpen
	public void onOpen(final Session session) {
		if (null == ALIVE_CLIENT) {
			ALIVE_CLIENT = this;
		} else {
			log.error("WS客户端被重复打开");
		}
		log.info("CTP Puppet WS客户端 创建成功");

		this.session = session;

		/**
		 * ws连接打开，发送容器信息
		 */
		this.sendMessage(WebSocketMessageEnum.REGISTER_CONTAINER, this.containerId);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		log.info(message);

		// 解析消息结构
		String[] split = message.split(SPLIT_FLAG, 2);
		if (split.length < LENGTH_MSG) {
			return;
		}
		String msgHead = split[0];
		String msgPayload = split[1];

		WebSocketMessageEnum wsmEnum = WebSocketMessageEnum.getByNmEn(msgHead);
		if (null != wsmEnum) {
			wsmEnum.getHandler().handle(this, msgPayload);
		} else {
			log.error("无法识别消息头:{}", msgHead);
		}
	}

	public void sendMessage(WebSocketMessageEnum msgEnum) {
		sendMessage(msgEnum, " ");
	}
	public void sendMessage(WebSocketMessageEnum msgEnum, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append(msgEnum.getNmEn());
		sb.append(SPLIT_FLAG);
		sb.append(message != null ? message : " ");
		message = sb.toString();
		try {
			this.session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			log.error(this.session.getId() + "发送信息失败：" + message, e);
		}
	}

	@OnError
	public void onError(Throwable t, Session session) {
		ALIVE_CLIENT = null;
	}

	@OnClose
	public void onClose() {
		ALIVE_CLIENT = null;
	}

	public void setCTPWorking(boolean ctpWorking) {
		this.ctpWorking = ctpWorking;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void achieveFutureAccountInfo(String message) {
		this.ctpUserInfoVO = UserInfoVO.resolveJson(message);
		TradeTcpServerFactory.start(this.ctpUserInfoVO);
	}

	public void achievePuppetInfo(String encrygyKey, String port) {
		StringBuilder sb = new StringBuilder();
		sb.append(encrygyKey);
		sb.append(SPLIT_FLAG_4_MSG);
		sb.append(port);
		ALIVE_CLIENT.sendMessage(WebSocketMessageEnum.ACHIEVE_PUPPET_INFO, sb.toString());
	}

	public void registered(String message) {
		new Thread(new Runnable() {
			final int FAIL_NUM = 20;

			@Override
			public void run() {
				int i = 0;
				while (true) {
					try {
						if (ctpWorking) {
							ALIVE_CLIENT.sendMessage(WebSocketMessageEnum.HEART, containerId);
						}
						Thread.sleep(5 * 1000);
						i = 0;
					} catch (Exception e) {
						i++;
						log.error("发送CTP傀儡机心跳失败，失败次数{}", i, e);
						if (i > FAIL_NUM) {
							break;
						}
					}
				}
			}
		}).start();
	}
}
