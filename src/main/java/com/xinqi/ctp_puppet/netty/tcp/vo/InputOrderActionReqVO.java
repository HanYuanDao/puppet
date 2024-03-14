package com.xinqi.ctp_puppet.netty.tcp.vo;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import lombok.Getter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/22 11:23
 **/
@Getter
public class InputOrderActionReqVO extends CTPReqVOBase {

	/**
	 * 经纪公司代码
	 */
	private String brokerID;
	/**
	 * 投资者代码
	 */
	private String investorID;
	/**
	 * 报单操作引用
	 */
	private Integer orderActionRef;
	/**
	 * 报单引用
	 */
	private String orderRef;
	/**
	 * 请求编号
	 */
	private Integer requestID;
	/**
	 * 前置编号
	 */
	private Integer frontID;
	/**
	 * 会话编号
	 */
	private Integer sessionID;
	/**
	 * 交易所代码
	 */
	private String exchangeID;
	/**
	 * 报单编号
	 */
	private String orderSysID;
	/**
	 * 操作标志
	 */
	private Character actionFlag;
	/**
	 * 价格
	 */
	private Double limitPrice;
	/**
	 * 数量变化
	 */
	private Integer volumeChange;
	/**
	 * 用户代码
	 */
	private String userID;
	/**
	 * 合约代码
	 */
	private String instrumentID;
	/**
	 * 投资单元代码
	 */
	private String investUnitID;
	/**
	 * IP地址
	 */
	private String ipAddress;
	/**
	 * Mac地址
	 */
	private String macAddress;

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
		this.orderActionRef = DecodeUtils.leBytesToInt(byteArr);
		offset += byteArr.length;
		byteArr = new byte[13];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.orderRef = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[4];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.requestID = DecodeUtils.leBytesToInt(byteArr);
		offset += byteArr.length;
		byteArr = new byte[4];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.frontID = DecodeUtils.leBytesToInt(byteArr);
		offset += byteArr.length;
		byteArr = new byte[4];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.sessionID = DecodeUtils.leBytesToInt(byteArr);
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
		this.orderSysID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[2];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.actionFlag = DecodeUtils.leBytesToChar(byteArr);
		offset += byteArr.length;
		byteArr = new byte[8];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.limitPrice = DecodeUtils.leBytesToDouble(byteArr);
		offset += byteArr.length;
		byteArr = new byte[4];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.volumeChange = DecodeUtils.leBytesToInt(byteArr);
		offset += byteArr.length;
		byteArr = new byte[16];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.userID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[31];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.instrumentID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[17];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.investUnitID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[16];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.ipAddress = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[21];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.macAddress = DecodeUtils.getCString(byteArr);
	}

}
