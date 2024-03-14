package com.xinqi.ctp_puppet.netty.tcp;


import com.google.gson.Gson;
import com.xinqi.ctp_puppet.common.DecodeUtils;
import com.xinqi.ctp_puppet.netty.tcp.server.CTPMsgHandlerTask;
import com.xinqi.ctp_puppet.netty.tcp.server.CTPMsgHandlerThreadPool;
import com.xinqi.ctp_puppet.netty.tcp.server.CTPOnFuncMsgHandleEnum;
import com.xinqi.ctp_puppet.netty.tcp.server.TradeTcpServerFactory;
import com.xinqi.ctp_puppet.netty.tcp.vo.CTPReqVOBase;
import com.xinqi.ctp_puppet.netty.tcp.vo.InputOrderActionReqVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.InputOrderReqVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QryInstrumentCommissionRateReqVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QryInvestorPositionDetailReqVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QryInvestorPositionDetailRespVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QrySettlementInfoConfirmReqVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QrySettlementInfoReqVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QryTradingAccountReqVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.SettlementInfoConfirmReqVO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/3 10:19
 **/
@Slf4j
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {

	private static Gson GSON = new Gson();

	/**
	 * 当channle被激活的回调（第次连接只发生一次）
	 *
	 * @param ctx
	 *
	 * @return void
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/08 10:28:58.
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		log.info("有客户端连接 channel ID is : {}", channel.id());
		this.notifyShookHand(channel);
		TradeTcpServerFactory.notifyRtnHistory(channel);
		TradeTcpServerFactory.addClientChannel(channel);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		Channel channel = ctx.channel();
		log.info("有客户端断开 channel ID is : {}", channel.id());
		TradeTcpServerFactory.removeClientChannel(ctx.channel());
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) {
		Channel channel = ctx.channel();
		log.info("有客户端注销 channel ID is : {}", channel.id());
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		//if (evt instanceof IdleStateEvent) {
		//	IdleStateEvent idleEvent = (IdleStateEvent) evt;
		//	if (idleEvent.state() == IdleState.READER_IDLE) {//读
		//		ctx.channel().close();
		//	} else if (idleEvent.state() == IdleState.WRITER_IDLE) {//写
		//
		//	} else if (idleEvent.state() == IdleState.ALL_IDLE) {//全部
		//
		//	}
		//}
		//super.userEventTriggered(ctx, evt);
		log.info("触发事件{}", evt.getClass());
		log.info("事件描述{}", GSON.toJson(evt));
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if(hex.length() < 2){
				sb.append(0);
			}
			sb.append(hex);
			sb.append(" ");
		}
		return sb.toString();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		//ByteBuf in = (ByteBuf) msg;
		//int readableBytes = in.readableBytes();
		//byte[] source = new byte[readableBytes];
		//in.readBytes(source);
		byte[] source = (byte[]) msg;

		try {
			log.info(bytesToHex(source));
		} catch (Exception e) {

		}

		byte[] tmp = new byte[source.length];
		for (int i = 0; i < source.length; i++) {
			tmp[i] = (byte) Integer.valueOf(source[i]).intValue();
		}
		log.info(Arrays.toString(tmp));

		/**
		 * 校验加密头
		 */
		final int byteHeadLength = 8;
		byte[] byteHead = new byte[byteHeadLength];
		for (int i = 0; i < byteHeadLength; i++) {
			byteHead[i] = source[i];
		}
		if (!checkHead(DecodeUtils.leBytesToLong(byteHead))) {
			log.error("消息头校验失败");

			CTPMsgHandlerTask ctpMsgHandlerTask = new CTPMsgHandlerTask();
			ctpMsgHandlerTask.loading(CTPOnFuncMsgHandleEnum.HEAD_ERR, null, null, null, null);
			ctpMsgHandlerTask.setChannel(ctx.channel());
			CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);

			return;
		}

		final int byteMsgTypeLength = 4;
		final int byteMsgTypeOffset = byteHeadLength;
		byte[] byteMsgType = new byte[byteMsgTypeLength];
		for (int i = 0; i < byteMsgTypeLength; i++) {
			byteMsgType[i] = source[i + byteMsgTypeOffset];
		}
		final int payloadOffset = byteHeadLength + byteMsgTypeLength;
		byte[] payload = new byte[source.length - payloadOffset];
		for (int i = 0; i < payload.length; i++) {
			payload[i] = source[i + payloadOffset];
		}

		msgRoute(DecodeUtils.leBytesToInt(byteMsgType), payload);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.error("连接出错", cause);
		ctx.close();
	}

	/**
	 * 校验tcp头是否正确
	 *
	 * @param head
	 *
	 * @return boolean
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/08 10:32:26.
	 */
	public boolean checkHead(long head) {
		log.info("收到的加密字符为" + head);
		return TradeTcpServerFactory.getTradeNettyTcpServer().checkoutEncryptKey(head);
	}

	public void msgRoute(int msgType, byte[] msgPayload) {
		switch (msgType) {
			case 33:
				InputOrderReqVO inputOrderReqVO = new InputOrderReqVO();
				inputOrderReqVO.toJavaBean(msgPayload);
				log.info(GSON.toJson(inputOrderReqVO));
				TradeTcpServerFactory.handler(inputOrderReqVO);
				break;
			case 34:
				InputOrderActionReqVO inputOrderActionReqVO = new InputOrderActionReqVO();
				inputOrderActionReqVO.toJavaBean(msgPayload);
				log.info(GSON.toJson(inputOrderActionReqVO));
				TradeTcpServerFactory.handler(inputOrderActionReqVO);
				break;
			case 41:
				QrySettlementInfoConfirmReqVO qrySettlementInfoConfirmReqVO = new QrySettlementInfoConfirmReqVO();
				qrySettlementInfoConfirmReqVO.toJavaBean(msgPayload);
				log.info(GSON.toJson(qrySettlementInfoConfirmReqVO));
				TradeTcpServerFactory.handler(qrySettlementInfoConfirmReqVO);
				break;
			case 42:
				SettlementInfoConfirmReqVO settlementInfoConfirmReqVO = new SettlementInfoConfirmReqVO();
				settlementInfoConfirmReqVO.toJavaBean(msgPayload);
				log.info(GSON.toJson(settlementInfoConfirmReqVO));
				TradeTcpServerFactory.handler(settlementInfoConfirmReqVO);
				break;
			case 43:
				QrySettlementInfoReqVO qrySettlementInfoReqVO = new QrySettlementInfoReqVO();
				qrySettlementInfoReqVO.toJavaBean(msgPayload);
				log.info(GSON.toJson(qrySettlementInfoReqVO));
				TradeTcpServerFactory.handler(qrySettlementInfoReqVO);
				break;
			case 49:
				QryInstrumentCommissionRateReqVO qryInstrumentCommissionRateReqVO = new QryInstrumentCommissionRateReqVO();
				qryInstrumentCommissionRateReqVO.toJavaBean(msgPayload);
				log.info(GSON.toJson(qryInstrumentCommissionRateReqVO));
				TradeTcpServerFactory.handler(qryInstrumentCommissionRateReqVO);
				break;
			case 50:
				QryTradingAccountReqVO qryTradingAccountReqVO = new QryTradingAccountReqVO();
				qryTradingAccountReqVO.toJavaBean(msgPayload);
				log.info(GSON.toJson(qryTradingAccountReqVO));
				TradeTcpServerFactory.handler(qryTradingAccountReqVO);
				break;
			case 57:
				QryInvestorPositionDetailReqVO qryInvestorPositionDetailReqVO = new QryInvestorPositionDetailReqVO();
				qryInvestorPositionDetailReqVO.toJavaBean(msgPayload);
				log.info(GSON.toJson(qryInvestorPositionDetailReqVO));
				TradeTcpServerFactory.handler(qryInvestorPositionDetailReqVO);
				break;
			default:
				log.error("异常的消息头{}", msgType);
				break;
		}
	}

	public void notifyShookHand(Channel channel) {
		CTPMsgHandlerTask ctpMsgHandlerTask = new CTPMsgHandlerTask();
		ctpMsgHandlerTask.loading(
				CTPOnFuncMsgHandleEnum.SHOOK_HAND, null, null, null, null);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
	}
}
