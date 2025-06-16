package com.xinqi.puppet.common;


import com.google.gson.Gson;
import lombok.Getter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/5 10:41
 **/
@Getter
public class UserInfoVO {

	private static Gson GSON = new Gson();

	private int tradeProxyCode;

	private String tradeAddress;
	private String brokerId;
	private String userId;
	private String investorId;
	private String password;
	private String accountId;
	private String currencyId;
	private String appId;
	private String authCode;

	public UserInfoVO(
			int tradeProxyCode,
			String tradeAddress, String brokerId,
			String userId, String investorId, String password,
			String appId, String authCode) {
		this.tradeProxyCode = tradeProxyCode;
		this.tradeAddress = tradeAddress;
		this.brokerId = brokerId;
		this.userId = userId;
		this.investorId = investorId;
		this.password = password;
		this.accountId = userId;
		this.currencyId = "CNY";
		this.appId = appId;
		this.authCode = authCode;
	}

	@Override
	public String toString() {
		return GSON.toJson(this);
	}

	public static UserInfoVO resolveJson(String json) {
		return GSON.fromJson(json, UserInfoVO.class);
	}

}
