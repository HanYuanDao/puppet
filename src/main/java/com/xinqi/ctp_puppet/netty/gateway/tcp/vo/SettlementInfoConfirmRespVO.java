package com.xinqi.ctp_puppet.netty.gateway.tcp.vo;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Setter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/30 11:00
 **/
@Setter
public class SettlementInfoConfirmRespVO extends CTPRespVOBase {

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
	public ByteBuf toByteBuf() {
		ByteBuf buf = Unpooled.buffer(336);
		buf.writeBytes(DecodeUtils.getCByteArr(brokerID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(investorID, 13));
		buf.writeBytes(DecodeUtils.getCByteArr(confirmDate, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(confirmTime, 9));
		buf.writeBytes(DecodeUtils.intToLeByteArr(settlementID));
		buf.writeBytes(DecodeUtils.getCByteArr(accountID, 13));
		buf.writeBytes(DecodeUtils.getCByteArr(currencyID, 4));
		return buf;
	}
}
