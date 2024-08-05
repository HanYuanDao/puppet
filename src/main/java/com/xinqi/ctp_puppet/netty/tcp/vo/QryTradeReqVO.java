package com.xinqi.ctp_puppet.netty.tcp.vo;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/7/29 10:19
 **/
@Getter
public class QryTradeReqVO extends CTPReqVOBase {
	///经纪公司代码
	private String brokerID;
	///投资者代码
	private String investorID;
	///交易所代码
	private String exchangeID;
	///成交编号
	private String tradeID;
	///开始时间
	private String tradeTimeStart;
	///结束时间
	private String tradeTimeEnd;
	///投资单元代码
	private String investUnitID;
	///合约代码
	private String instrumentID;

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
		this.exchangeID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[21];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.tradeID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[9];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.tradeTimeStart = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[9];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.tradeTimeEnd = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[17];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.investUnitID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[31];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.instrumentID = DecodeUtils.getCString(byteArr);
	}

}
