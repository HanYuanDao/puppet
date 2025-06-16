package com.xinqi.puppet.netty.gateway.tcp.vo;


import com.xinqi.puppet.common.DecodeUtils;
import com.xinqi.puppet.netty.gateway.tcp.server.TradeNettyTcpServer;
import lombok.Getter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/22 10:37
 **/
@Getter
public class InputOrderReqVO extends CTPReqVOBase {

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
	 * 报单引用
	 */
	private String orderRef;
	/**
	 * 用户代码
	 */
	private String userID;
	/**
	 * 报单价格条件
	 */
	private Character orderPriceType;
	/**
	 * 买卖方向
	 */
	private Character direction;
	/**
	 * 组合开平标志
	 */
	private String combOffsetFlag;
	/**
	 * 组合投机套保标志
	 */
	private String combHedgeFlag;
	/**
	 * 价格
	 */
	private Double limitPrice;
	/**
	 * 数量
	 */
	private Integer volumeTotalOriginal;
	/**
	 * 有效期类型
	 */
	private Character timeCondition;
	/**
	 * GTD日期
	 */
	private String gtdDate;
	/**
	 * 成交量类型
	 */
	private Character volumeCondition;
	/**
	 * 最小成交量
	 */
	private Integer minVolume;
	/**
	 * 触发条件
	 */
	private Character contingentCondition;
	/**
	 * 止损价
	 */
	private Double stopPrice;
	/**
	 * 强平原因
	 */
	private Character forceCloseReason;
	/**
	 * 自动挂起标志
	 */
	private Integer isAutoSuspend;
	/**
	 * 业务单元
	 */
	private String businessUnit;
	/**
	 * 请求编号
	 */
	private Integer requestID;
	/**
	 * 用户强评标志
	 */
	private Integer userForceClose;
	/**
	 * 互换单标志
	 */
	private Integer isSwapOrder;
	/**
	 * 交易所代码
	 */
	private String exchangeID;
	/**
	 * 投资单元代码
	 */
	private String investUnitID;
	/**
	 * 资金账号
	 */
	private String accountID;
	/**
	 * 币种代码
	 */
	private String currencyID;
	/**
	 * 交易编码
	 */
	private String clientID;
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
		byteArr = new byte[31];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.instrumentID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[13];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		//this.orderRef = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[16];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.userID = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[2];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.orderPriceType = DecodeUtils.leBytesToChar(byteArr);
		offset += byteArr.length;
		byteArr = new byte[2];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.direction = DecodeUtils.leBytesToChar(byteArr);
		offset += byteArr.length;
		byteArr = new byte[5];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.combOffsetFlag = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[5];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.combHedgeFlag = DecodeUtils.getCString(byteArr);
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
		this.volumeTotalOriginal = DecodeUtils.leBytesToInt(byteArr);
		offset += byteArr.length;
		byteArr = new byte[2];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.timeCondition = DecodeUtils.leBytesToChar(byteArr);
		offset += byteArr.length;
		byteArr = new byte[9];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.gtdDate = DecodeUtils.getCString(byteArr);
		offset += byteArr.length;
		byteArr = new byte[2];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.volumeCondition = DecodeUtils.leBytesToChar(byteArr);
		offset += byteArr.length;
		byteArr = new byte[4];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.minVolume = DecodeUtils.leBytesToInt(byteArr);
		offset += byteArr.length;
		byteArr = new byte[2];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.contingentCondition = DecodeUtils.leBytesToChar(byteArr);
		offset += byteArr.length;
		byteArr = new byte[8];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.stopPrice = DecodeUtils.leBytesToDouble(byteArr);
		offset += byteArr.length;
		byteArr = new byte[2];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.forceCloseReason = DecodeUtils.leBytesToChar(byteArr);
		offset += byteArr.length;
		byteArr = new byte[4];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.isAutoSuspend = DecodeUtils.leBytesToInt(byteArr);
		offset += byteArr.length;
		byteArr = new byte[21];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.businessUnit = DecodeUtils.getCString(byteArr);
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
		this.userForceClose = DecodeUtils.leBytesToInt(byteArr);
		offset += byteArr.length;
		byteArr = new byte[4];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.isSwapOrder = DecodeUtils.leBytesToInt(byteArr);
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
		offset += byteArr.length;
		byteArr = new byte[11];
		for (int i = 0; i < byteArr.length; i++) {
			byteArr[i] = source[offset + i];
		}
		this.clientID = DecodeUtils.getCString(byteArr);
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
