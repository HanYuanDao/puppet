package com.xinqi.puppet.netty.gateway.tcp.vo;


import com.xinqi.puppet.common.DecodeUtils;
import lombok.Getter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/23 09:52
 **/
@Getter
public class SettlementInfoConfirmReqVO extends CTPReqVOBase {

	/**
	 * 经纪公司代码
	 */
	private String brokerID;
	/**
	 * 投资者代码
	 */
	private String investorID;
	/**
	 * 确认日期
	 */
	private String confirmDate;
	/**
	 * 确认时间
	 */
	private String confirmTime;
	/**
	 * 结算编号
	 */
	private Integer settlementID;
	/**
	 * 投资者帐号
	 */
	private String accountID;
	/**
	 * 币种代码
	 */
	private String currencyID;

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
		byteArr = new byte[9];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.confirmDate = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[9];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.confirmTime = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[4];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.settlementID = DecodeUtils.leBytesToInt(byteArr);
		offset += byteArr.length;
		byteArr = new byte[13];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.accountID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[4];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.currencyID = DecodeUtils.getCString(byteArr);

	}

}
