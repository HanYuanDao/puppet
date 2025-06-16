package com.xinqi.puppet.netty.gateway.tcp.vo;


import com.xinqi.puppet.common.DecodeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Setter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/14 03:16
 **/
@Setter
public class QryInvestorPositionDetailRespVO extends TradeProxyRespVOBase {

	/**
	 * 合约代码
	 */
	private String instrumentID;
	/**
	 * 经纪公司代码
	 */
	private String brokerID;
	/**
	 * 投资者代码
	 */
	private String investorID;
	/**
	 * 投机套保标志
	 */
	private Character hedgeFlag;
	/**
	 * 买卖方向
	 */
	private Character direction;
	/**
	 * 开仓日期
	 */
	private String openDate;
	/**
	 * 交易所交易员代码
	 */
	private String tradeID;
	/**
	 * 数量
	 */
	private Integer volume;
	/**
	 * 价格
	 */
	private Double openPrice;
	/**
	 * 交易日
	 */
	private String tradingDay;
	/**
	 * 结算编号
	 */
	private Integer settlementID;
	/**
	 * 成交类型
	 */
	private Character tradeType;
	/**
	 * 投资单元代码
	 */
	private String combInstrumentID;
	/**
	 * 交易所代码
	 */
	private String exchangeID;
	/**
	 * 逐日盯市平仓盈亏
	 */
	private Double closeProfitByDate;
	/**
	 * 逐笔对冲平仓盈亏
	 */
	private Double closeProfitByTrade;
	/**
	 * 逐日盯市持仓盈亏
	 */
	private Double positionProfitByDate;
	/**
	 * 逐笔对冲持仓盈亏
	 */
	private Double positionProfitByTrade;
	/**
	 * 投资者保证金
	 */
	private Double margin;
	/**
	 * 交易所保证金
	 */
	private Double exchMargin;
	/**
	 * 保证金率
	 */
	private Double marginRateByMoney;
	/**
	 * 保证金率(按手数)
	 */
	private Double marginRateByVolume;
	/**
	 * 昨结算价
	 */
	private Double lastSettlementPrice;
	/**
	 * 结算价
	 */
	private Double settlementPrice;
	/**
	 * 平仓量
	 */
	private Integer closeVolume;
	/**
	 * 平仓金额
	 */
	private Double closeAmount;
	/**
	 * 按照时间顺序平仓的笔数,大商所专用
	 */
	private Integer timeFirstVolume;
	/**
	 * 投资单元代码
	 */
	private String investUnitID;

	@Override
	public ByteBuf toByteBuf() {
		ByteBuf buf = Unpooled.buffer(44);
		buf.writeBytes(DecodeUtils.getCByteArr(instrumentID, 31));
		buf.writeBytes(DecodeUtils.getCByteArr(brokerID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(investorID, 13));
		buf.writeBytes(DecodeUtils.charToLeByteArr(hedgeFlag));
		buf.writeBytes(DecodeUtils.charToLeByteArr(direction));
		buf.writeBytes(DecodeUtils.getCByteArr(openDate, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(tradeID, 21));
		buf.writeBytes(DecodeUtils.intToLeByteArr(volume));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(openPrice));
		buf.writeBytes(DecodeUtils.getCByteArr(tradingDay, 9));
		buf.writeBytes(DecodeUtils.intToLeByteArr(settlementID));
		buf.writeBytes(DecodeUtils.charToLeByteArr(tradeType));
		buf.writeBytes(DecodeUtils.getCByteArr(combInstrumentID, 31));
		buf.writeBytes(DecodeUtils.getCByteArr(exchangeID, 9));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(closeProfitByDate));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(closeProfitByTrade));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(positionProfitByDate));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(positionProfitByTrade));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(margin));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(exchMargin));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(marginRateByMoney));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(marginRateByVolume));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(lastSettlementPrice));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(settlementPrice));
		buf.writeBytes(DecodeUtils.intToLeByteArr(closeVolume));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(closeAmount));
		buf.writeBytes(DecodeUtils.intToLeByteArr(timeFirstVolume));
		buf.writeBytes(DecodeUtils.getCByteArr(investUnitID, 17));
		return buf;
	}

}
