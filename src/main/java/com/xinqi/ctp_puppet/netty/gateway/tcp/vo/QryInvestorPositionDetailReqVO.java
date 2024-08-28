package com.xinqi.ctp_puppet.netty.gateway.tcp.vo;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import lombok.Getter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/2/2 15:01
 **/
@Getter
public class QryInvestorPositionDetailReqVO extends CTPReqVOBase {

	/**
	 * 经纪公司代码
	 */
	private String brokerID;
	/**
	 * 投资者代码
	 */
	private String investorID;
	/**
	 * 合约代码
	 */
	private String instrumentID;
	/**
	 * 交易所代码
	 */
	private String exchangeID;
	/**
	 * 投资单元代码
	 */
	private String investUnitID;

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
		byteArr = new byte[31];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.instrumentID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[9];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.exchangeID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[17];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.investUnitID = DecodeUtils.getCString(byteArr);
	}

}
