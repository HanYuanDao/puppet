package com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader;


import lombok.Getter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2025/2/13 09:55
 **/
@Getter
public enum TradeProxyEnum {

	CTP(1, "CTP", "CTP", CTPTradeProxy.class),
	CTP_MINI(2, "CTP_MINI", "CTP", CTPMiniTradeProxy.class),
	QDP(6, "国富", "QDP", QDPTradeProxy.class),
	;

	private int code;
	private String nmCn;
	private String nmEn;
	private Class<? extends TradeProxyBase> impl;

	TradeProxyEnum(int code, String nmCn, String nmEn, Class<? extends TradeProxyBase> impl) {
		this.code = code;
		this.nmCn = nmCn;
		this.nmEn = nmEn;
		this.impl = impl;
	}

	public static TradeProxyEnum getByCode(int code) {
		for (TradeProxyEnum e : TradeProxyEnum.values()) {
			if (e.code == code) {
				return e;
			}
		}
		return null;
	}
}
