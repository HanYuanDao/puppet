package com.xinqi.ctp_puppet.netty.gateway.tcp.vo;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Setter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/2/2 13:16
 **/
@Setter
public class InputOrderActionRespVO extends CTPRespVOBase {

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
	public ByteBuf toByteBuf() {
		ByteBuf buf = Unpooled.buffer(267);
		buf.writeBytes(DecodeUtils.getCByteArr(brokerID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(investorID, 13));
		buf.writeBytes(DecodeUtils.intToLeByteArr(orderActionRef));
		buf.writeBytes(DecodeUtils.getCByteArr(orderRef, 13));
		buf.writeBytes(DecodeUtils.intToLeByteArr(requestID));
		buf.writeBytes(DecodeUtils.intToLeByteArr(frontID));
		buf.writeBytes(DecodeUtils.intToLeByteArr(sessionID));
		buf.writeBytes(DecodeUtils.getCByteArr(exchangeID, 9));
		buf.writeBytes(DecodeUtils.getCByteArr(orderSysID, 21));
		buf.writeBytes(DecodeUtils.charToLeByteArr(actionFlag));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(limitPrice));
		buf.writeBytes(DecodeUtils.intToLeByteArr(volumeChange));
		buf.writeBytes(DecodeUtils.getCByteArr(userID, 16));
		buf.writeBytes(DecodeUtils.getCByteArr(instrumentID, 31));
		buf.writeBytes(DecodeUtils.getCByteArr(investUnitID, 17));
		buf.writeBytes(DecodeUtils.getCByteArr(ipAddress, 16));
		buf.writeBytes(DecodeUtils.getCByteArr(macAddress, 21));
		return buf;
	}

}
