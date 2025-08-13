package com.xinqi.puppet.netty.gateway.tcp.server;


import com.google.gson.Gson;
import com.xinqi.puppet.common.UserInfoVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.CTPReqVOBase;
import com.xinqi.puppet.netty.gateway.tcp.vo.TradeProxyRespVOBase;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderActionReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInstrumentCommissionRateReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInvestorPositionDetailReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoConfirmReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradeReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradingAccountReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.SettlementInfoConfirmReqVO;
import com.xinqi.puppet.ws.WSClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/4 10:34
 **/
@PropertySource(value = { "classpath:application.properties" })
@Component
@Slf4j
public class TradeTcpServerFactory {

	private static Gson GSON = new Gson();

	private static String URL;
	@Value("${vtp.manager.ws.uri}")
	public void setKey(String key) {
		URL = key;
	}

	private static TradeNettyTcpServer tradeNettyTcpServer;
	private static WSClient wsClient;

	//TODO JasonHan 没有在客户端断开连接时删除channel，需要添加对应的事件处理
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	private static List<TradeProxyRespVOBase> RTN_HISTORY = new CopyOnWriteArrayList<>();

	public synchronized static void initTradeTcpServer() {
		if (null == tradeNettyTcpServer) {
			tradeNettyTcpServer = new TradeNettyTcpServer();
		}
	}

	public synchronized static void initWSClient(String containerId) throws DeploymentException, IOException {
		TradeTcpServerFactory.wsClient = new WSClient();
		TradeTcpServerFactory.wsClient.setContainerId(containerId);
		ContainerProvider.getWebSocketContainer().connectToServer(
				TradeTcpServerFactory.wsClient, URI.create(URL + containerId));
	}

	public static TradeNettyTcpServer getTradeNettyTcpServer() {
		if (null == tradeNettyTcpServer) {
			initTradeTcpServer();
		}
		return tradeNettyTcpServer;
	}

	/**
	 * 添加新的管道
	 *
	 * @param channel
	 *
	 * @return void
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/04/02 11:31:29.
	 */
	public static void addClientChannel(Channel channel) {
		/**
		 * 清空消息队列
		 */
		TradeProxyMsgHandlerThreadPool.clean();

		/**
		 * 新连接上来之后关闭之前的管道
		 */
		//for (Channel c : channelGroup) {
		//	c.close();
		//}
		channelGroup.close();

		channelGroup.add(channel);
	}
	public static void removeClientChannel(Channel channel) {
		channelGroup.remove(channel);
	}
	public static void sendMessage(ByteBuf byteBuf) {
		//for (Channel channel : channelGroup) {
		//	try {
		//		channel.writeAndFlush(byteBuf);
		//		log.info("发送消息给{}, 消息长度为{}", channel.id(), byteBuf.readableBytes());
		//	} catch (Exception e) {
		//		log.error("服务端往客户端发送消息失败。", e);
		//	}
		//}

		try {
			channelGroup.writeAndFlush(byteBuf);
		} catch (Exception e) {
			log.error("服务端往客户端发送消息失败。", e);
		}
	}

	public static void addRtnHistory(TradeProxyRespVOBase byteBuf) {
		RTN_HISTORY.add(byteBuf);
	}
	public static void notifyRtnHistory(Channel channel) {
		for (TradeProxyRespVOBase vo : RTN_HISTORY) {
			TradeProxyOnFuncMsgHandleEnum handleEnum = TradeProxyOnFuncMsgHandleEnum.getEnum(vo);
			if (null != handleEnum) {
				MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
				ctpMsgHandlerTask.loading(
						handleEnum, vo, null, null, null);
				TradeProxyMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
			}
		}
	}
	
	public static void handler(CTPReqVOBase ctpReqVOBase) {
		log.error("错误的实现");
	}
	public static void handler(InputOrderReqVO inputOrderReqVO) {
		tradeNettyTcpServer.insertOrder(inputOrderReqVO);
	}
	public static void handler(InputOrderActionReqVO inputOrderActionReqVO) {
		tradeNettyTcpServer.actionOrder(inputOrderActionReqVO);
	}
	public static void handler(SettlementInfoConfirmReqVO settlementInfoConfirmReqVO) {
		tradeNettyTcpServer.confirmSettlementInfo(settlementInfoConfirmReqVO);
	}
	public static void handler(QryTradingAccountReqVO qryTradingAccountReqVO) {
		//log.info("查询期货账号信息 {}", tradeNettyTcpServer.qryTradingAccount(qryTradingAccountReqVO));
		tradeNettyTcpServer.qryTradingAccount(qryTradingAccountReqVO);
	}
	public static void handler(QrySettlementInfoConfirmReqVO qrySettlementInfoReqVO) {
		tradeNettyTcpServer.qrySettlementInfoConfirm(qrySettlementInfoReqVO);
	}
	public static void handler(QrySettlementInfoReqVO qrySettlementInfoReqVO) {
		tradeNettyTcpServer.qrySettlementInfo(qrySettlementInfoReqVO);
	}
	public static void handler(QryInstrumentCommissionRateReqVO qryInstrumentCommissionRateReqVO) {
		tradeNettyTcpServer.qryInstrumentCommissionRate(qryInstrumentCommissionRateReqVO);
	}
	public static void handler(QryInvestorPositionDetailReqVO qryInvestorPositionDetailReqVO) {
		tradeNettyTcpServer.qryInvestorPositionDetail(qryInvestorPositionDetailReqVO);
	}
	public static void handler(QryTradeReqVO qryTradeReqVO) {
		tradeNettyTcpServer.qryTrade(qryTradeReqVO);
	}

	public static void start(UserInfoVO faVO) {
		tradeNettyTcpServer.start(faVO);
	}

	public static void notifyTcpServerInfo(String encrygyKey, String port) {
		wsClient.achievePuppetInfo(encrygyKey, port);
	}

	public static void setCTPWorking(boolean working) {
		wsClient.setCTPWorking(working);
	}

	public static void shutdownTradeTcpServer() {
		tradeNettyTcpServer.shutdown();
	}
}
