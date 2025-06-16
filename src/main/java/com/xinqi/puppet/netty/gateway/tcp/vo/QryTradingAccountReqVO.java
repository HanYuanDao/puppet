package com.xinqi.puppet.netty.gateway.tcp.vo;


import com.xinqi.puppet.common.DecodeUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/23 09:34
 **/
@Setter
@Getter
public class QryTradingAccountReqVO extends CTPReqVOBase {

	/**
	 * 经纪公司代码
	 */
	private String brokerID;
	/**
	 * 投资者代码
	 */
	private String investorID;
	/**
	 * 币种代码
	 */
	private String currencyID;
	/**
	 * 业务类型
	 */
	private Character bizType;
	/**
	 * 投资者帐号
	 */
	private String accountID;

	@Override
	public void toJavaBean(byte[] source) {
		int offset = 0;
		byte[] byteArr = new byte[11];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.brokerID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[13];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.investorID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[4];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.currencyID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[2];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.bizType = DecodeUtils.leBytesToChar(byteArr);
		offset += byteArr.length;
		byteArr = new byte[13];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.accountID = DecodeUtils.getCString(byteArr);
	}

}
