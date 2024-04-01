package com.xinqi.ctp_puppet.netty.tcp.vo;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Setter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/14 01:48
 **/
@Setter
public class RtnOrderRespVO extends CTPRespVOBase {

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
	private double stopPrice;
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
	 * 本地报单编号
	 */
	private String orderLocalID;
	/**
	 * 交易所代码
	 */
	private String exchangeID;
	/**
	 * 会员代码
	 */
	private String participantID;
	/**
	 * 客户代码
	 */
	private String clientID;
	/**
	 * 合约在交易所的代码
	 */
	private String exchangeInstID;
	/**
	 * 交易所交易员代码
	 */
	private String traderID;
	/**
	 * 安装编号
	 */
	private Integer installID;
	/**
	 * 报单提交状态
	 */
	private Character orderSubmitStatus;
	/**
	 * 报单提示序号
	 */
	private Integer notifySequence;
	/**
	 * 交易日
	 */
	private String tradingDay;
	/**
	 * 结算编号
	 */
	private Integer settlementID;
	/**
	 * 报单编号
	 */
	private String orderSysID;
	/**
	 * 报单来源
	 */
	private Character orderSource;
	/**
	 * 报单状态
	 */
	private Character orderStatus;
	/**
	 * 报单类型
	 */
	private Character orderType;
	/**
	 * 今成交数量
	 */
	private Integer volumeTraded;
	/**
	 * 剩余数量
	 */
	private Integer volumeTotal;
	/**
	 * 报单日期
	 */
	private String insertDate;
	/**
	 * 委托时间
	 */
	private String insertTime;
	/**
	 * 激活时间
	 */
	private String activeTime;
	/**
	 * 挂起时间
	 */
	private String suspendTime;
	/**
	 * 最后修改时间
	 */
	private String updateTime;
	/**
	 * 撤销时间
	 */
	private String cancelTime;
	/**
	 * 最后修改交易所交易员代码
	 */
	private String activeTraderID;
	/**
	 * 结算会员编号
	 */
	private String clearingPartID;
	/**
	 * 序号
	 */
	private Integer sequenceNo;
	/**
	 * 前置编号
	 */
	private Integer frontID;
	/**
	 * 会话编号
	 */
	private Integer sessionID;
	/**
	 * 用户端产品信息
	 */
	private String userProductInfo;
	/**
	 * 状态信息
	 */
	private String statusMsg;
	/**
	 * 用户强评标志
	 */
	private Integer userForceClose;
	/**
	 * 操作用户代码
	 */
	private String activeUserID;
	/**
	 * 经纪公司报单编号
	 */
	private Integer brokerOrderSeq;
	/**
	 * 相关报单
	 */
	private String relativeOrderSysID;
	/**
	 * 郑商所成交数量
	 */
	private Integer zceTotalTradedVolume;
	/**
	 * 互换单标志
	 */
	private Integer isSwapOrder;
	/**
	 * 营业部编号
	 */
	private String branchID;
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
	 * IP地址
	 */
	private String ipAddress;
	/**
	 * Mac地址
	 */
	private String macAddress;

	@Override
	public ByteBuf toByteBuf() {
		ByteBuf buf = Unpooled.buffer(726);
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
		buf.writeBytes(DecodeUtils.getCByteArr(orderLocalID, 13));
		buf.writeBytes(DecodeUtils.getCByteArr(exchangeID, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(participantID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(clientID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(exchangeInstID, 31));
		buf.writeBytes(DecodeUtils.getCByteArr(traderID, 21));
		buf.writeBytes(DecodeUtils.intToLeByteArr(installID));
		buf.writeBytes(DecodeUtils.charToLeByteArr(orderSubmitStatus));
		buf.writeBytes(DecodeUtils.intToLeByteArr(notifySequence));
		buf.writeBytes(DecodeUtils.getCByteArr(tradingDay, 9));
		buf.writeBytes(DecodeUtils.intToLeByteArr(settlementID));
		buf.writeBytes(DecodeUtils.getCByteArr(orderSysID, 21));
		buf.writeBytes(DecodeUtils.charToLeByteArr(orderSource));
		buf.writeBytes(DecodeUtils.charToLeByteArr(orderStatus));
		buf.writeBytes(DecodeUtils.charToLeByteArr(orderType));
		buf.writeBytes(DecodeUtils.intToLeByteArr(volumeTraded));
		buf.writeBytes(DecodeUtils.intToLeByteArr(volumeTotal));
		buf.writeBytes(DecodeUtils.getCByteArr(insertDate, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(insertTime, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(activeTime, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(suspendTime, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(updateTime, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(cancelTime, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(activeTraderID, 21));
		buf.writeBytes(DecodeUtils.getCByteArr(clearingPartID, 11));
		buf.writeBytes(DecodeUtils.intToLeByteArr(sequenceNo));
		buf.writeBytes(DecodeUtils.intToLeByteArr(frontID));
		buf.writeBytes(DecodeUtils.intToLeByteArr(sessionID));
		buf.writeBytes(DecodeUtils.getCByteArr(userProductInfo, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(statusMsg, 162));
		buf.writeBytes(DecodeUtils.intToLeByteArr(userForceClose));
		buf.writeBytes(DecodeUtils.getCByteArr(activeUserID, 16));
		buf.writeBytes(DecodeUtils.intToLeByteArr(brokerOrderSeq));
		buf.writeBytes(DecodeUtils.getCByteArr(relativeOrderSysID, 21));
		buf.writeBytes(DecodeUtils.intToLeByteArr(zceTotalTradedVolume));
		buf.writeBytes(DecodeUtils.intToLeByteArr(isSwapOrder));
		buf.writeBytes(DecodeUtils.getCByteArr(branchID, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(investUnitID, 17));
		buf.writeBytes(DecodeUtils.getCByteArr(accountID, 13));
		buf.writeBytes(DecodeUtils.getCByteArr(currencyID, 4));
		buf.writeBytes(DecodeUtils.getCByteArr(ipAddress, 16));
		buf.writeBytes(DecodeUtils.getCByteArr(macAddress, 21));
		return buf;
	}

}
