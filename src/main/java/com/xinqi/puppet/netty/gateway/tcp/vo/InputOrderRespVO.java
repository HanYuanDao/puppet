package com.xinqi.puppet.netty.gateway.tcp.vo;


import com.xinqi.puppet.common.DecodeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Setter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/2/2 10:25
 **/
@Setter
public class InputOrderRespVO extends TradeProxyRespVOBase {

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
	public ByteBuf toByteBuf() {
		ByteBuf buf = Unpooled.buffer(267);
		buf.writeBytes(DecodeUtils.getCByteArr(brokerID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(investorID, 13));
		buf.writeBytes(DecodeUtils.getCByteArr(instrumentID, 31));
		buf.writeBytes(DecodeUtils.getCByteArr(orderRef, 13));
		buf.writeBytes(DecodeUtils.getCByteArr(userID, 16));
		buf.writeBytes(DecodeUtils.charToLeByteArr(orderPriceType));
		buf.writeBytes(DecodeUtils.charToLeByteArr(direction));
		buf.writeBytes(DecodeUtils.getCByteArr(combOffsetFlag, 5));
		buf.writeBytes(DecodeUtils.getCByteArr(combHedgeFlag, 5));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(limitPrice));
		buf.writeBytes(DecodeUtils.intToLeByteArr(volumeTotalOriginal));
		buf.writeBytes(DecodeUtils.charToLeByteArr(timeCondition));
		buf.writeBytes(DecodeUtils.getCByteArr(gtdDate, 9));
		buf.writeBytes(DecodeUtils.charToLeByteArr(volumeCondition));
		buf.writeBytes(DecodeUtils.intToLeByteArr(minVolume));
		buf.writeBytes(DecodeUtils.charToLeByteArr(contingentCondition));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(stopPrice));
		buf.writeBytes(DecodeUtils.charToLeByteArr(forceCloseReason));
		buf.writeBytes(DecodeUtils.intToLeByteArr(isAutoSuspend));
		buf.writeBytes(DecodeUtils.getCByteArr(businessUnit, 21));
		buf.writeBytes(DecodeUtils.intToLeByteArr(requestID));
		buf.writeBytes(DecodeUtils.intToLeByteArr(userForceClose));
		buf.writeBytes(DecodeUtils.intToLeByteArr(isSwapOrder));
		buf.writeBytes(DecodeUtils.getCByteArr(exchangeID, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(investUnitID, 17));
		buf.writeBytes(DecodeUtils.getCByteArr(accountID, 13));
		buf.writeBytes(DecodeUtils.getCByteArr(currencyID, 4));
		buf.writeBytes(DecodeUtils.getCByteArr(clientID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(ipAddress, 16));
		buf.writeBytes(DecodeUtils.getCByteArr(macAddress, 21));
		return buf;
	}

}
