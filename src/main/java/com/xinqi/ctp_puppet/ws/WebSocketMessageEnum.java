package com.xinqi.ctp_puppet.ws;


import lombok.Getter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/8 13:57
 **/
@Getter
public enum WebSocketMessageEnum {

	SYS_ERROR(1, "sys_error", "系统错误", null),
	/**
	 * 傀儡机消息
	 */
	REGISTER_CONTAINER(11, "register_container", "注册容器", null),
	ACHIEVE_PUPPET_INFO(31, "achieve_puppet_info", "告知CTP傀儡机信息", null),
	HEART(51, "heart", "傀儡机心跳", null),
	/**
	 * 服务器消息
	 */
	ACHIEVE_FUTURE_ACCOUNT_INFO(21, "achieve_future_account_info", "告知期货账号信息", WSMessageHandlerEnum.ACHIEVE_FUTUREACCOUNTINFO),
	REGISTERED(41, "registered", "告知注册完成", WSMessageHandlerEnum.REGISTERED),
	;

	private Integer no;
	private String nmEn;
	private String nmCn;
	private WSMessageHandlerEnum handler;

	WebSocketMessageEnum(Integer no, String nmEn, String nmCn, WSMessageHandlerEnum handler) {
		this.no = no;
		this.nmEn = nmEn;
		this.nmCn = nmCn;
		this.handler = handler;
	}

	public static WebSocketMessageEnum getByNmEn(String nmEn) {
		for (WebSocketMessageEnum value : WebSocketMessageEnum.values()) {
			if (value.getNmEn().equals(nmEn)) {
				return value;
			}
		}
		return null;
	}
}
