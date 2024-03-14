package com.xinqi.ctp_puppet.netty.tcp.vo;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/14 02:31
 **/
@Setter
public class RtnTradeRespVO extends CTPRespVOBase {

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
	 * 交易所代码
	 */
	private String exchangeID;
	/**
	 * 交易所交易员代码
	 */
	private String tradeID;
	/**
	 * 买卖方向
	 */
	private Character direction;
	/**
	 * 报单编号
	 */
	private String orderSysID;
	/**
	 * 会员代码
	 */
	private String participantID;
	/**
	 * 客户代码
	 */
	private String clientID;
	/**
	 * 交易角色
	 */
	private Character tradingRole;
	/**
	 * 合约在交易所的代码
	 */
	private String exchangeInstID;
	/**
	 * 开平标志
	 */
	private Character offsetFlag;
	/**
	 * 投机套保标志
	 */
	private Character hedgeFlag;
	/**
	 * 价格
	 */
	private Double price;
	/**
	 * 数量
	 */
	private Integer volume;
	/**
	 * 成交时期
	 */
	private String tradeDate;
	/**
	 * 成交时间
	 */
	private String tradeTime;
	/**
	 * 成交类型
	 */
	private Character tradeType;
	/**
	 * 成交价来源
	 */
	private Character priceSource;
	/**
	 * 交易所交易员代码
	 */
	private String traderID;
	/**
	 * 本地报单编号
	 */
	private String orderLocalID;
	/**
	 * 结算会员编号
	 */
	private String clearingPartID;
	/**
	 * 业务单元
	 */
	private String businessUnit;
	/**
	 * 序号
	 */
	private Integer sequenceNo;
	/**
	 * 交易日
	 */
	private String tradingDay;
	/**
	 * 结算编号
	 */
	private Integer settlementID;
	/**
	 * 经纪公司报单编号
	 */
	private Integer brokerOrderSeq;
	/**
	 * 成交来源
	 */
	private Character tradeSource;
	/**
	 * 投资单元代码
	 */
	private String investUnitID;

	@Override
	public ByteBuf toByteBuf() {
		ByteBuf buf = Unpooled.buffer(336);
		buf.writeBytes(DecodeUtils.getCByteArr(brokerID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(investorID, 13));
		buf.writeBytes(DecodeUtils.getCByteArr(instrumentID, 31));
		buf.writeBytes(DecodeUtils.getCByteArr(orderRef, 13));
		buf.writeBytes(DecodeUtils.getCByteArr(userID, 16));
		buf.writeBytes(DecodeUtils.getCByteArr(exchangeID, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(tradeID, 21));
		buf.writeBytes(DecodeUtils.charToLeByteArr(direction));
		buf.writeBytes(DecodeUtils.getCByteArr(orderSysID, 21));
		buf.writeBytes(DecodeUtils.getCByteArr(participantID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(clientID, 11));
		buf.writeBytes(DecodeUtils.charToLeByteArr(tradingRole));
		buf.writeBytes(DecodeUtils.getCByteArr(exchangeInstID, 31));
		buf.writeBytes(DecodeUtils.charToLeByteArr(offsetFlag));
		buf.writeBytes(DecodeUtils.charToLeByteArr(hedgeFlag));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(price));
		buf.writeBytes(DecodeUtils.intToLeByteArr(volume));
		buf.writeBytes(DecodeUtils.getCByteArr(tradeDate, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(tradeTime, 9));
		buf.writeBytes(DecodeUtils.charToLeByteArr(tradeType));
		buf.writeBytes(DecodeUtils.charToLeByteArr(priceSource));
		buf.writeBytes(DecodeUtils.getCByteArr(traderID, 21));
		buf.writeBytes(DecodeUtils.getCByteArr(orderLocalID, 13));
		buf.writeBytes(DecodeUtils.getCByteArr(clearingPartID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(businessUnit, 21));
		buf.writeBytes(DecodeUtils.intToLeByteArr(sequenceNo));
		buf.writeBytes(DecodeUtils.getCByteArr(tradingDay, 9));
		buf.writeBytes(DecodeUtils.intToLeByteArr(settlementID));
		buf.writeBytes(DecodeUtils.intToLeByteArr(brokerOrderSeq));
		buf.writeBytes(DecodeUtils.charToLeByteArr(tradeSource));
		buf.writeBytes(DecodeUtils.getCByteArr(investUnitID, 17));
		return buf;
	}

}
