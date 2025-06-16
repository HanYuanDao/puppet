package com.xinqi.puppet.netty.quot;


import com.xinqi.puppet.common.DecodeUtils;
import com.xinqi.puppet.netty.NettyTcpClient;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 2021/11/3 14:44
 **/
@Slf4j
@ChannelHandler.Sharable
public class ClientChannelHandler extends SimpleChannelInboundHandler {

	private NettyTcpClient client;

	private final static String REG_4_INSTRUMENT = "[^0-9A-Za-z:]";

	public ClientChannelHandler(NettyTcpClient client) {
		this.client = client;
	}

	/**
	 * 从服务器接收到的msg
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		byte[] source = (byte[]) msg;

		byte[] byteSize = new byte[4];
		for (int i = 0; i < byteSize.length; i++) {
			byteSize[i] = source[i];
		}
		byte[] byteCmdID = new byte[4];
		for (int i = 0; i < byteCmdID.length; i++) {
			byteCmdID[i] = source[4 + i];
		}
		switch (DecodeUtils.leBytesToInt(byteCmdID)) {
			case 11:
				RsqTick(source);
				break;
			case 12:
				RsqCipherTick(source);
				break;
			case 201:
				RsqSubIns(source);
				break;
			default:
				log.info("未知的cmdId {}", DecodeUtils.leBytesToInt(byteCmdID));
				break;
		};
	}

	/**
	 * Netty 断线重连
	 *
	 * @param context
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2021/11/29 19:45:54.
	 */
	@Override
	public void channelInactive(ChannelHandlerContext context) throws Exception {
		//log.info("NettyTcpClient is Reconnect by ClientChannelHandler.channelInactive");
		/*final EventLoop eventExecutors = context.channel().eventLoop();
		eventExecutors.schedule(new Runnable() {
			@Override
			public void run() {
				client.connect();
			}
		}, NettyTcpClient.RECONNECT_DELAY_TIME, NettyTcpClient.RECONNECT_DELAY_TIME_UNIT);
		super.channelInactive(context);*/
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
		context.close();
		log.error("netty异常", cause);
		log.error(cause.getMessage(), cause.getCause());
	}

	@Override
	public void channelUnregistered(final ChannelHandlerContext context) {
		//log.info("NettyTcpClient is Reconnect by ClientChannelHandler.channelUnregistered");
		context.channel().eventLoop().schedule(() -> {
			client.connect();
		}, QuotNettyTcpClient.RECONNECT_DELAY_TIME, QuotNettyTcpClient.RECONNECT_DELAY_TIME_UNIT);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
		//log.info("NettyTcpClient is Reconnect by ClientChannelHandler.userEventTriggered");
		if (!(evt instanceof IdleStateEvent)) {
			return;
		}
		IdleStateEvent e = (IdleStateEvent) evt;
		if (e.state() == IdleState.READER_IDLE) {
			//log.info("Idle, close connect");
			ctx.close();
		}
	}

	private void RsqSubIns(byte[] source) throws UnsupportedEncodingException {
		//int i = 0;
		//byte[] byteSize = new byte[4];
		//for (i = 0; i < byteSize.length; i++) {
		//	byteSize[i] = source[i];
		//}
		//byte[] byteCmdID = new byte[4];
		//for (i = 0; i < byteCmdID.length; i++) {
		//	byteCmdID[i] = source[4 + i];
		//}
		//byte[] byteRequest = new byte[4];
		//for (i = 0; i < byteRequest.length; i++) {
		//	byteRequest[i] = source[8 + i];
		//}
		//byte[] byteErrId = new byte[4];
		//for (i = 0; i < byteErrId.length; i++) {
		//	byteErrId[i] = source[12 + i];
		//}
		//byte[] byteErrMsg = new byte[128];
		//for (i = 0; i < byteErrMsg.length; i++) {
		//	byteErrMsg[i] = source[16 + i];
		//}
		//byte[] byteInstrumentID = new byte[32];
		//for (i = 0; i < byteInstrumentID.length; i++) {
		//	byteInstrumentID[i] = source[144 + i];
		//}
		///*System.out.printf("size: [%d]\n", DecodeUtils.bytesToInt(byteSize));
		//System.out.printf("cmdId: [%d]\n", DecodeUtils.bytesToInt(byteCmdID));
		//System.out.printf("requestId: [%d]\n", DecodeUtils.bytesToInt(byteRequest));
		//System.out.printf("errId: [%d]\n", DecodeUtils.bytesToInt(byteErrId));
		//System.out.printf("errMsg: [%s]\n", new String(byteErrMsg, "UTF8"));
		//System.out.printf("instrumentID: [%s]\n", new String(byteInstrumentID, "UTF8"));*/
	}

	private void RsqTick(byte[] source) throws UnsupportedEncodingException {
		//int i;
		//byte[] byteZero = new byte[4];
		//byte[] byteSize = new byte[4];
		//for (i = 0; i < byteSize.length; i++) {
		//	byteSize[i] = source[i];
		//}
		//byte[] byteCmdID = new byte[4];
		//for (i = 0; i < byteCmdID.length; i++) {
		//	byteCmdID[i] = source[4 + i];
		//}
		//byte[] byteTradingDay = new byte[9];
		//for (i = 0; i < byteTradingDay.length; i++) {
		//	byteTradingDay[i] = source[8 + i];
		//}
		//byte[] byteInstrumentID = new byte[31];
		//for (i = 0; i < byteInstrumentID.length; i++) {
		//	if (source[17 + i] == '\u0000') {
		//		break;
		//	}
		//	byteInstrumentID[i] = source[17 + i];
		//}
		//byte[] byteExchangeID = new byte[9];
		//for (i = 0; i < byteExchangeID.length; i++) {
		//	if (source[48 + i] == '\u0000') {
		//		break;
		//	}
		//	byteExchangeID[i] = source[48 + i];
		//}
		//byte[] byteExchangeInstID = new byte[31];
		//for (i = 0; i < byteExchangeInstID.length; i++) {
		//	byteExchangeInstID[i] = source[57 + i];
		//}
		//byte[] byteLastPrice = new byte[8];
		//for (i = 0; i < byteLastPrice.length; i++) {
		//	byteLastPrice[i] = source[88 + i];
		//}
		//byte[] bytePreSettlementPrice = new byte[8];
		//for (i = 0; i < bytePreSettlementPrice.length; i++) {
		//	bytePreSettlementPrice[i] = source[96 + i];
		//}
		//byte[] bytePreClosePrice = new byte[8];
		//for (i = 0; i < bytePreClosePrice.length; i++) {
		//	bytePreClosePrice[i] = source[104 + i];
		//}
		//byte[] bytePreOpenInterest = new byte[8];
		//for (i = 0; i < bytePreOpenInterest.length; i++) {
		//	bytePreOpenInterest[i] = source[112 + i];
		//}
		//byte[] byteOpenPrice = new byte[8];
		//for (i = 0; i < byteOpenPrice.length; i++) {
		//	byteOpenPrice[i] = source[120 + i];
		//}
		//byte[] byteHighestPrice = new byte[8];
		//for (i = 0; i < byteHighestPrice.length; i++) {
		//	byteHighestPrice[i] = source[128 + i];
		//}
		//byte[] byteLowestPrice = new byte[8];
		//for (i = 0; i < byteLowestPrice.length; i++) {
		//	byteLowestPrice[i] = source[136 + i];
		//}
		//byte[] byteVolume = new byte[4];
		//for (i = 0; i < byteVolume.length; i++) {
		//	byteVolume[i] = source[144 + i];
		//}
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[148 + i];
		//}
		//byte[] byteTurnover = new byte[8];
		//for (i = 0; i < byteTurnover.length; i++) {
		//	byteTurnover[i] = source[152 + i];
		//}
		//byte[] byteOpenInterest = new byte[8];
		//for (i = 0; i < byteOpenInterest.length; i++) {
		//	byteOpenInterest[i] = source[160 + i];
		//}
		//byte[] byteClosePrice = new byte[8];
		//for (i = 0; i < byteClosePrice.length; i++) {
		//	byteClosePrice[i] = source[168 + i];
		//}
		//byte[] byteSettlementPrice = new byte[8];
		//for (i = 0; i < byteSettlementPrice.length; i++) {
		//	byteSettlementPrice[i] = source[176 + i];
		//}
		//byte[] byteUpperLimitPrice = new byte[8];
		//for (i = 0; i < byteUpperLimitPrice.length; i++) {
		//	byteUpperLimitPrice[i] = source[184 + i];
		//}
		//byte[] byteLowerLimitPrice = new byte[8];
		//for (i = 0; i < byteLowerLimitPrice.length; i++) {
		//	byteLowerLimitPrice[i] = source[192 + i];
		//}
		//byte[] bytePreDelta = new byte[8];
		//for (i = 0; i < bytePreDelta.length; i++) {
		//	bytePreDelta[i] = source[200 + i];
		//}
		//byte[] byteCurrDelta = new byte[8];
		//for (i = 0; i < byteCurrDelta.length; i++) {
		//	byteCurrDelta[i] = source[208 + i];
		//}
		//byte[] byteUpdateTime = new byte[9];
		//for (i = 0; i < byteUpdateTime.length; i++) {
		//	byteUpdateTime[i] = source[216 + i];
		//}
		//byteZero = new byte[3];
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[225 + i];
		//}
		//byte[] byteUpdateMillisec = new byte[4];
		//for (i = 0; i < byteUpdateMillisec.length; i++) {
		//	byteUpdateMillisec[i] = source[228 + i];
		//}
		//byte[] byteBidPrice1 = new byte[8];
		//for (i = 0; i < byteBidPrice1.length; i++) {
		//	byteBidPrice1[i] = source[232 + i];
		//}
		//byte[] byteBidVolume1 = new byte[4];
		//for (i = 0; i < byteBidVolume1.length; i++) {
		//	byteBidVolume1[i] = source[240 + i];
		//}
		//byteZero = new byte[4];
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[244 + i];
		//}
		//byte[] byteAskPrice1 = new byte[8];
		//for (i = 0; i < byteAskPrice1.length; i++) {
		//	byteAskPrice1[i] = source[248 + i];
		//}
		//byte[] byteAskVolume1 = new byte[4];
		//for (i = 0; i < byteAskVolume1.length; i++) {
		//	byteAskVolume1[i] = source[256 + i];
		//}
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[260 + i];
		//}
		//byte[] byteBidPrice2 = new byte[8];
		//for (i = 0; i < byteBidPrice2.length; i++) {
		//	byteBidPrice2[i] = source[264 + i];
		//}
		//byte[] byteBidVolume2 = new byte[4];
		//for (i = 0; i < byteBidVolume2.length; i++) {
		//	byteBidVolume2[i] = source[272 + i];
		//}
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[276 + i];
		//}
		//byte[] byteAskPrice2 = new byte[8];
		//for (i = 0; i < byteAskPrice2.length; i++) {
		//	byteAskPrice2[i] = source[280 + i];
		//}
		//byte[] byteAskVolume2 = new byte[4];
		//for (i = 0; i < byteAskVolume2.length; i++) {
		//	byteAskVolume2[i] = source[288 + i];
		//}
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[292 + i];
		//}
		//byte[] byteBidPrice3 = new byte[8];
		//for (i = 0; i < byteBidPrice3.length; i++) {
		//	byteBidPrice3[i] = source[296 + i];
		//}
		//byte[] byteBidVolume3 = new byte[4];
		//for (i = 0; i < byteBidVolume3.length; i++) {
		//	byteBidVolume3[i] = source[304 + i];
		//}
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[308 + i];
		//}
		//byte[] byteAskPrice3 = new byte[8];
		//for (i = 0; i < byteAskPrice3.length; i++) {
		//	byteAskPrice3[i] = source[312 + i];
		//}
		//byte[] byteAskVolume3 = new byte[4];
		//for (i = 0; i < byteAskVolume3.length; i++) {
		//	byteAskVolume3[i] = source[320 + i];
		//}
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[324 + i];
		//}
		//byte[] byteBidPrice4 = new byte[8];
		//for (i = 0; i < byteBidPrice4.length; i++) {
		//	byteBidPrice4[i] = source[328 + i];
		//}
		//byte[] byteBidVolume4 = new byte[4];
		//for (i = 0; i < byteBidVolume4.length; i++) {
		//	byteBidVolume4[i] = source[336 + i];
		//}
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[340 + i];
		//}
		//byte[] byteAskPrice4 = new byte[8];
		//for (i = 0; i < byteAskPrice4.length; i++) {
		//	byteAskPrice4[i] = source[344 + i];
		//}
		//byte[] byteAskVolume4 = new byte[4];
		//for (i = 0; i < byteAskVolume4.length; i++) {
		//	byteAskVolume4[i] = source[352 + i];
		//}
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[356 + i];
		//}
		//byte[] byteBidPrice5 = new byte[8];
		//for (i = 0; i < byteBidPrice5.length; i++) {
		//	byteBidPrice5[i] = source[360 + i];
		//}
		//byte[] byteBidVolume5 = new byte[4];
		//for (i = 0; i < byteBidVolume5.length; i++) {
		//	byteBidVolume5[i] = source[368 + i];
		//}
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[372 + i];
		//}
		//byte[] byteAskPrice5 = new byte[8];
		//for (i = 0; i < byteAskPrice5.length; i++) {
		//	byteAskPrice5[i] = source[376 + i];
		//}
		//byte[] byteAskVolume5 = new byte[4];
		//for (i = 0; i < byteAskVolume5.length; i++) {
		//	byteAskVolume5[i] = source[384 + i];
		//}
		//for (i = 0; i < byteZero.length; i++) {
		//	byteZero[i] = source[388 + i];
		//}
		//byte[] byteAveragePrice = new byte[8];
		//for (i = 0; i < byteAveragePrice.length; i++) {
		//	byteAveragePrice[i] = source[392 + i];
		//}
		//byte[] byteActionDay = new byte[9];
		//for (i = 0; i < byteActionDay.length; i++) {
		//	byteActionDay[i] = source[400 + i];
		//}
		//
		//DepthMarketDataPO depthMarketDataPO = new DepthMarketDataPO();
		//depthMarketDataPO.setTradingDay(new String(byteTradingDay, "UTF8").trim());
		//depthMarketDataPO.setInstrumentID(new String(byteInstrumentID, "UTF8")
		//		.trim().replaceAll(REG_4_INSTRUMENT, ""));
		//depthMarketDataPO.setExchangeID(new String(byteExchangeID, "UTF8").trim());
		//depthMarketDataPO.setExchangeInstID(new String(byteExchangeInstID, "UTF8").trim());
		//depthMarketDataPO.setLastPrice(DecodeUtils.bytesToDouble(byteLastPrice));
		//depthMarketDataPO.setPreSettlementPrice(DecodeUtils.bytesToDouble(bytePreSettlementPrice));
		//depthMarketDataPO.setPreClosePrice(DecodeUtils.bytesToDouble(bytePreClosePrice));
		//depthMarketDataPO.setPreOpenInterest(DecodeUtils.bytesToDouble(bytePreOpenInterest));
		//depthMarketDataPO.setOpenPrice(DecodeUtils.bytesToDouble(byteOpenPrice));
		//depthMarketDataPO.setHighestPrice(DecodeUtils.bytesToDouble(byteHighestPrice));
		//depthMarketDataPO.setLowestPrice(DecodeUtils.bytesToDouble(byteLowestPrice));
		//depthMarketDataPO.setVolume(DecodeUtils.bytesToInt(byteVolume));
		//depthMarketDataPO.setTurnover(DecodeUtils.bytesToDouble(byteTurnover));
		//depthMarketDataPO.setOpenInterest(Double.valueOf(DecodeUtils.bytesToDouble(byteOpenInterest)).intValue());
		//depthMarketDataPO.setClosePrice(DecodeUtils.bytesToDouble(byteClosePrice));
		//depthMarketDataPO.setSettlementPrice(DecodeUtils.bytesToDouble(byteSettlementPrice));
		//depthMarketDataPO.setUpperLimitPrice(DecodeUtils.bytesToDouble(byteUpperLimitPrice));
		//depthMarketDataPO.setLowerLimitPrice(DecodeUtils.bytesToDouble(byteLowerLimitPrice));
		//depthMarketDataPO.setPreDelta(DecodeUtils.bytesToDouble(bytePreDelta));
		//depthMarketDataPO.setCurrDelta(DecodeUtils.bytesToDouble(byteCurrDelta));
		//depthMarketDataPO.setUpdateTime(new String(byteUpdateTime, "UTF8"));
		//depthMarketDataPO.setUpdateMillisec(DecodeUtils.bytesToInt(byteUpdateMillisec));
		//depthMarketDataPO.setBidPrice1(DecodeUtils.bytesToDouble(byteBidPrice1));
		//depthMarketDataPO.setBidVolume1(DecodeUtils.bytesToInt(byteBidVolume1));
		//depthMarketDataPO.setAskPrice1(DecodeUtils.bytesToDouble(byteAskPrice1));
		//depthMarketDataPO.setAskVolume1(DecodeUtils.bytesToInt(byteAskVolume1));
		//depthMarketDataPO.setBidPrice2(DecodeUtils.bytesToDouble(byteBidPrice2));
		//depthMarketDataPO.setBidVolume2(DecodeUtils.bytesToInt(byteBidVolume2));
		//depthMarketDataPO.setAskPrice2(DecodeUtils.bytesToDouble(byteAskPrice2));
		//depthMarketDataPO.setAskVolume2(DecodeUtils.bytesToInt(byteAskVolume2));
		//depthMarketDataPO.setBidPrice3(DecodeUtils.bytesToDouble(byteBidPrice2));
		//depthMarketDataPO.setBidVolume3(DecodeUtils.bytesToInt(byteBidVolume2));
		//depthMarketDataPO.setAskPrice3(DecodeUtils.bytesToDouble(byteAskPrice3));
		//depthMarketDataPO.setAskVolume3(DecodeUtils.bytesToInt(byteAskVolume3));
		//depthMarketDataPO.setBidPrice4(DecodeUtils.bytesToDouble(byteBidPrice4));
		//depthMarketDataPO.setBidVolume4(DecodeUtils.bytesToInt(byteBidVolume4));
		//depthMarketDataPO.setAskPrice4(DecodeUtils.bytesToDouble(byteAskPrice4));
		//depthMarketDataPO.setAskVolume4(DecodeUtils.bytesToInt(byteAskVolume4));
		//depthMarketDataPO.setBidPrice5(DecodeUtils.bytesToDouble(byteBidPrice5));
		//depthMarketDataPO.setBidVolume5(DecodeUtils.bytesToInt(byteBidVolume5));
		//depthMarketDataPO.setAskPrice5(DecodeUtils.bytesToDouble(byteAskPrice5));
		//depthMarketDataPO.setAskVolume5(DecodeUtils.bytesToInt(byteAskVolume5));
		//depthMarketDataPO.setAveragePrice(DecodeUtils.bytesToDouble(byteAveragePrice));
		//depthMarketDataPO.setActionDay(new String(byteActionDay, "UTF8").trim());
		//
		//QuotDataServiceHandler.saveDepthMarketData(depthMarketDataPO);
	}

	private void RsqCipherTick(byte[] source) throws UnsupportedEncodingException {
		//int i;
		//byte[] byteSize = new byte[4];
		//for (i = 0; i < byteSize.length; i++) {
		//	byteSize[i] = source[i];
		//}
		//byte[] byteCmdID = new byte[4];
		//for (i = 0; i < byteCmdID.length; i++) {
		//	byteCmdID[i] = source[4 + i];
		//}
		//byte byteKey = source[8];
		//byte[] bytePayload = new byte[297];
		//for (i = 0; i < bytePayload.length; i++) {
		//	bytePayload[i] = source[9 + i];
		//}
		//
		//for (int j = 0; j < bytePayload.length; j++) {
		//	bytePayload[j] ^= byteKey;
		//}
		//
		//byte[] byteInstrumentID = new byte[31];
		//for (i = 0; i < byteInstrumentID.length; i++) {
		//	byteInstrumentID[i] = bytePayload[0 + i];
		//}
		//byte[] byteLastPrice = new byte[8];
		//for (i = 0; i < byteLastPrice.length; i++) {
		//	byteLastPrice[i] = bytePayload[31 + i];
		//}
		//byte[] byteVolume = new byte[4];
		//for (i = 0; i < byteVolume.length; i++) {
		//	byteVolume[i] = bytePayload[39 + i];
		//}
		//byte[] byteBidPrice1 = new byte[8];
		//for (i = 0; i < byteBidPrice1.length; i++) {
		//	byteBidPrice1[i] = bytePayload[43 + i];
		//}
		//byte[] byteBidPrice2 = new byte[8];
		//for (i = 0; i < byteBidPrice2.length; i++) {
		//	byteBidPrice2[i] = bytePayload[51 + i];
		//}
		//byte[] byteBidPrice3 = new byte[8];
		//for (i = 0; i < byteBidPrice3.length; i++) {
		//	byteBidPrice3[i] = bytePayload[59 + i];
		//}
		//byte[] byteBidPrice4 = new byte[8];
		//for (i = 0; i < byteBidPrice4.length; i++) {
		//	byteBidPrice4[i] = bytePayload[67 + i];
		//}
		//byte[] byteBidPrice5 = new byte[8];
		//for (i = 0; i < byteBidPrice5.length; i++) {
		//	byteBidPrice5[i] = bytePayload[75 + i];
		//}
		//byte[] byteBidVolume1 = new byte[4];
		//for (i = 0; i < byteBidVolume1.length; i++) {
		//	byteBidVolume1[i] = bytePayload[83 + i];
		//}
		//byte[] byteBidVolume2 = new byte[4];
		//for (i = 0; i < byteBidVolume2.length; i++) {
		//	byteBidVolume2[i] = bytePayload[87 + i];
		//}
		//byte[] byteBidVolume3 = new byte[4];
		//for (i = 0; i < byteBidVolume3.length; i++) {
		//	byteBidVolume3[i] = bytePayload[91 + i];
		//}
		//byte[] byteBidVolume4 = new byte[4];
		//for (i = 0; i < byteBidVolume4.length; i++) {
		//	byteBidVolume4[i] = bytePayload[95 + i];
		//}
		//byte[] byteBidVolume5 = new byte[4];
		//for (i = 0; i < byteBidVolume5.length; i++) {
		//	byteBidVolume5[i] = bytePayload[99 + i];
		//}
		//byte[] byteAskPrice1 = new byte[8];
		//for (i = 0; i < byteAskPrice1.length; i++) {
		//	byteAskPrice1[i] = bytePayload[103 + i];
		//}
		//byte[] byteAskPrice2 = new byte[8];
		//for (i = 0; i < byteAskPrice2.length; i++) {
		//	byteAskPrice2[i] = bytePayload[111 + i];
		//}
		//byte[] byteAskPrice3 = new byte[8];
		//for (i = 0; i < byteAskPrice3.length; i++) {
		//	byteAskPrice3[i] = bytePayload[119 + i];
		//}
		//byte[] byteAskPrice4 = new byte[8];
		//for (i = 0; i < byteAskPrice4.length; i++) {
		//	byteAskPrice4[i] = bytePayload[127 + i];
		//}
		//byte[] byteAskPrice5 = new byte[8];
		//for (i = 0; i < byteAskPrice5.length; i++) {
		//	byteAskPrice5[i] = bytePayload[135 + i];
		//}
		//byte[] byteAskVolume1 = new byte[4];
		//for (i = 0; i < byteAskVolume1.length; i++) {
		//	byteAskVolume1[i] = bytePayload[143 + i];
		//}
		//byte[] byteAskVolume2 = new byte[4];
		//for (i = 0; i < byteAskVolume2.length; i++) {
		//	byteAskVolume2[i] = bytePayload[147 + i];
		//}
		//byte[] byteAskVolume3 = new byte[4];
		//for (i = 0; i < byteAskVolume3.length; i++) {
		//	byteAskVolume3[i] = bytePayload[151 + i];
		//}
		//byte[] byteAskVolume4 = new byte[4];
		//for (i = 0; i < byteAskVolume4.length; i++) {
		//	byteAskVolume4[i] = bytePayload[155 + i];
		//}
		//byte[] byteAskVolume5 = new byte[4];
		//for (i = 0; i < byteAskVolume5.length; i++) {
		//	byteAskVolume5[i] = bytePayload[159 + i];
		//}
		//byte[] byteHighestPrice = new byte[8];
		//for (i = 0; i < byteHighestPrice.length; i++) {
		//	byteHighestPrice[i] = bytePayload[163 + i];
		//}
		//byte[] byteLowestPrice = new byte[8];
		//for (i = 0; i < byteLowestPrice.length; i++) {
		//	byteLowestPrice[i] = bytePayload[171 + i];
		//}
		//byte[] byteUpperLimitPrice = new byte[8];
		//for (i = 0; i < byteUpperLimitPrice.length; i++) {
		//	byteUpperLimitPrice[i] = bytePayload[179 + i];
		//}
		//byte[] byteLowerLimitPrice = new byte[8];
		//for (i = 0; i < byteLowerLimitPrice.length; i++) {
		//	byteLowerLimitPrice[i] = bytePayload[187 + i];
		//}
		//byte[] bytePreSettlementPrice = new byte[8];
		//for (i = 0; i < bytePreSettlementPrice.length; i++) {
		//	bytePreSettlementPrice[i] = bytePayload[195 + i];
		//}
		//byte[] bytePreClosePrice = new byte[8];
		//for (i = 0; i < bytePreClosePrice.length; i++) {
		//	bytePreClosePrice[i] = bytePayload[203 + i];
		//}
		//byte[] bytePreOpenInterest = new byte[8];
		//for (i = 0; i < bytePreOpenInterest.length; i++) {
		//	bytePreOpenInterest[i] = bytePayload[211 + i];
		//}
		//byte[] bytePreDelta = new byte[8];
		//for (i = 0; i < bytePreDelta.length; i++) {
		//	bytePreDelta[i] = bytePayload[219 + i];
		//}
		//byte[] byteCurrDelta = new byte[8];
		//for (i = 0; i < byteCurrDelta.length; i++) {
		//	byteCurrDelta[i] = bytePayload[227 + i];
		//}
		//byte[] byteOpenPrice = new byte[8];
		//for (i = 0; i < byteOpenPrice.length; i++) {
		//	byteOpenPrice[i] = bytePayload[235 + i];
		//}
		//byte[] byteClosePrice = new byte[8];
		//for (i = 0; i < byteClosePrice.length; i++) {
		//	byteClosePrice[i] = bytePayload[243 + i];
		//}
		//byte[] byteSettlementPrice = new byte[8];
		//for (i = 0; i < byteSettlementPrice.length; i++) {
		//	byteSettlementPrice[i] = bytePayload[251 + i];
		//}
		//byte[] byteTurnover = new byte[8];
		//for (i = 0; i < byteTurnover.length; i++) {
		//	byteTurnover[i] = bytePayload[259 + i];
		//}
		//byte[] byteOpenInterest = new byte[8];
		//for (i = 0; i < byteOpenInterest.length; i++) {
		//	byteOpenInterest[i] = bytePayload[267 + i];
		//}
		//byte[] byteTradingDay = new byte[9];
		//for (i = 0; i < byteTradingDay.length; i++) {
		//	byteTradingDay[i] = bytePayload[275 + i];
		//}
		//byte[] byteUpdateTime = new byte[9];
		//for (i = 0; i < byteUpdateTime.length; i++) {
		//	byteUpdateTime[i] = bytePayload[284 + i];
		//}
		//byte[] byteUpdateMillisec = new byte[4];
		//for (i = 0; i < byteUpdateMillisec.length; i++) {
		//	byteUpdateMillisec[i] = bytePayload[293 + i];
		//}
		//
		//DepthMarketDataPO depthMarketDataPO = new DepthMarketDataPO();
		//depthMarketDataPO.setTradingDay(new String(byteTradingDay, "UTF8").trim());
		//depthMarketDataPO.setInstrumentID(new String(byteInstrumentID, "UTF8")
		//		.trim().replaceAll(REG_4_INSTRUMENT, ""));
		//depthMarketDataPO.setExchangeID(null);
		//depthMarketDataPO.setExchangeInstID(null);
		//depthMarketDataPO.setLastPrice(DecodeUtils.bytesToDouble(byteLastPrice));
		//depthMarketDataPO.setPreSettlementPrice(DecodeUtils.bytesToDouble(bytePreSettlementPrice));
		//depthMarketDataPO.setPreClosePrice(DecodeUtils.bytesToDouble(bytePreClosePrice));
		//depthMarketDataPO.setPreOpenInterest(DecodeUtils.bytesToDouble(bytePreOpenInterest));
		//depthMarketDataPO.setOpenPrice(DecodeUtils.bytesToDouble(byteOpenPrice));
		//depthMarketDataPO.setHighestPrice(DecodeUtils.bytesToDouble(byteHighestPrice));
		//depthMarketDataPO.setLowestPrice(DecodeUtils.bytesToDouble(byteLowestPrice));
		//depthMarketDataPO.setVolume(DecodeUtils.bytesToInt(byteVolume));
		//depthMarketDataPO.setTurnover(DecodeUtils.bytesToDouble(byteTurnover));
		//depthMarketDataPO.setOpenInterest(Double.valueOf(DecodeUtils.bytesToDouble(byteOpenInterest)).intValue());
		//depthMarketDataPO.setClosePrice(DecodeUtils.bytesToDouble(byteClosePrice));
		//depthMarketDataPO.setSettlementPrice(DecodeUtils.bytesToDouble(byteSettlementPrice));
		//depthMarketDataPO.setUpperLimitPrice(DecodeUtils.bytesToDouble(byteUpperLimitPrice));
		//depthMarketDataPO.setLowerLimitPrice(DecodeUtils.bytesToDouble(byteLowerLimitPrice));
		//depthMarketDataPO.setPreDelta(DecodeUtils.bytesToDouble(bytePreDelta));
		//depthMarketDataPO.setCurrDelta(DecodeUtils.bytesToDouble(byteCurrDelta));
		//depthMarketDataPO.setUpdateTime(new String(byteUpdateTime, "UTF8").trim());
		//depthMarketDataPO.setUpdateMillisec(DecodeUtils.bytesToInt(byteUpdateMillisec));
		//depthMarketDataPO.setBidPrice1(DecodeUtils.bytesToDouble(byteBidPrice1));
		//depthMarketDataPO.setBidVolume1(DecodeUtils.bytesToInt(byteBidVolume1));
		//depthMarketDataPO.setAskPrice1(DecodeUtils.bytesToDouble(byteAskPrice1));
		//depthMarketDataPO.setAskVolume1(DecodeUtils.bytesToInt(byteAskVolume1));
		//depthMarketDataPO.setBidPrice2(DecodeUtils.bytesToDouble(byteBidPrice2));
		//depthMarketDataPO.setBidVolume2(DecodeUtils.bytesToInt(byteBidVolume2));
		//depthMarketDataPO.setAskPrice2(DecodeUtils.bytesToDouble(byteAskPrice2));
		//depthMarketDataPO.setAskVolume2(DecodeUtils.bytesToInt(byteAskVolume2));
		//depthMarketDataPO.setBidPrice3(DecodeUtils.bytesToDouble(byteBidPrice3));
		//depthMarketDataPO.setBidVolume3(DecodeUtils.bytesToInt(byteBidVolume3));
		//depthMarketDataPO.setAskPrice3(DecodeUtils.bytesToDouble(byteAskPrice3));
		//depthMarketDataPO.setAskVolume3(DecodeUtils.bytesToInt(byteAskVolume3));
		//depthMarketDataPO.setBidPrice4(DecodeUtils.bytesToDouble(byteBidPrice4));
		//depthMarketDataPO.setBidVolume4(DecodeUtils.bytesToInt(byteBidVolume4));
		//depthMarketDataPO.setAskPrice4(DecodeUtils.bytesToDouble(byteAskPrice4));
		//depthMarketDataPO.setAskVolume4(DecodeUtils.bytesToInt(byteAskVolume4));
		//depthMarketDataPO.setBidPrice5(DecodeUtils.bytesToDouble(byteBidPrice5));
		//depthMarketDataPO.setBidVolume5(DecodeUtils.bytesToInt(byteBidVolume5));
		//depthMarketDataPO.setAskPrice5(DecodeUtils.bytesToDouble(byteAskPrice5));
		//depthMarketDataPO.setAskVolume5(DecodeUtils.bytesToInt(byteAskVolume5));
		//depthMarketDataPO.setAveragePrice(0);
		//depthMarketDataPO.setActionDay(null);
		//
		//QuotDataServiceHandler.saveDepthMarketData(depthMarketDataPO);
	}

}