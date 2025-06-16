package com.xinqi.puppet.ws;


import lombok.extern.slf4j.Slf4j;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 2021/12/31 14:37
 **/
@Slf4j
public enum WSMessageHandlerEnum {

	ACHIEVE_FUTUREACCOUNTINFO {
		@Override
		public void abstractHandle(WSClient wss, String message) {
			//wss.achieveFutureAccountInfo(message);
		}
	},
	REGISTERED {
		@Override
		public void abstractHandle(WSClient wss, String message) {
			wss.registered(message);
		}
	}
	;

	public void handle(WSClient wss, String message) {
		try {
			abstractHandle(wss, message);
		} catch (Exception e) {
			log.error("WS消息处理异常", e);
		}
	}

	public abstract void abstractHandle(WSClient wss, String message);
}
