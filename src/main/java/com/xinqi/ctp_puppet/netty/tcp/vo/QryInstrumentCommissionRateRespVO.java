package com.xinqi.ctp_puppet.netty.tcp.vo;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Setter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/12 15:34
 **/
@Setter
public class QryInstrumentCommissionRateRespVO extends CTPRespVOBase {

	/**
	 * 合约代码
	 */
	private String instrumentID;
	/**
	 * 投资者范围
	 */
	private Character investorRange;
	/**
	 * 经纪公司代码
	 */
	private String brokerID;
	/**
	 * 投资者代码
	 */
	private String investorID;
	/**
	 * 开仓手续费率
	 */
	private Double openRatioByMoney;
	/**
	 * 开仓手续费
	 */
	private Double openRatioByVolume;
	/**
	 * 平仓手续费率
	 */
	private Double closeRatioByMoney;
	/**
	 * 平仓手续费
	 */
	private Double closeRatioByVolume;
	/**
	 * 平今手续费率
	 */
	private Double closeTodayRatioByMoney;
	/**
	 * 平今手续费
	 */
	private Double closeTodayRatioByVolume;
	/**
	 * 交易所代码
	 */
	private String exchangeID;
	/**
	 * 业务类型
	 */
	private Character bizType;
	/**
	 * 投资单元代码
	 */
	private String investUnitID;

	@Override
	public ByteBuf toByteBuf() {
		ByteBuf buf = Unpooled.buffer(44);
		buf.writeBytes(DecodeUtils.getCByteArr(instrumentID, 31));
		buf.writeBytes(DecodeUtils.charToLeByteArr(investorRange));
		buf.writeBytes(DecodeUtils.getCByteArr(brokerID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(investorID, 13));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(openRatioByMoney));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(openRatioByVolume));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(closeRatioByMoney));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(closeRatioByVolume));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(closeTodayRatioByMoney));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(closeTodayRatioByVolume));
		buf.writeBytes(DecodeUtils.getCByteArr(exchangeID, 9));
		buf.writeBytes(DecodeUtils.charToLeByteArr(bizType));
		buf.writeBytes(DecodeUtils.getCByteArr(investUnitID, 17));
		return buf;
	}
}
