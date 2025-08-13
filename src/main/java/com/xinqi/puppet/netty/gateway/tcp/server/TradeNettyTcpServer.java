package com.xinqi.puppet.netty.gateway.tcp.server;


import com.xinqi.puppet.common.UserInfoVO;
import com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader.CTPTradeProxy;
import com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader.TradeProxyBase;
import com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader.TradeProxyEnum;
import com.xinqi.puppet.netty.quot.VTPQuotServerNettyDescoder;
import com.xinqi.puppet.netty.gateway.tcp.ServerChannelHandler;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderActionReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInstrumentCommissionRateReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInvestorPositionDetailReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoConfirmReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradeReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradingAccountReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.SettlementInfoConfirmReqVO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.nio.ByteOrder;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/2 14:58
 **/
@Slf4j
public class TradeNettyTcpServer {

	private final static Integer PORT_PUPPET = 8099;

	/*static {
		*//*System.loadLibrary("thosttraderapi_se");
		System.loadLibrary("thosttraderapi_wrap");*//*
		*//*System.loadLibrary("ctpminithosttraderapi");
		System.loadLibrary("ctpminithosttraderapi_wrap");
		System.loadLibrary("qdptraderapi");
		System.loadLibrary("qdptraderapi_wrap");*//*
	}*/

	private TradeProxyBase tradeProxy;

	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workerGroup = new NioEventLoopGroup();

	/**
	 * 报单引用
	 */
	private static int ORDER_REF;

	private int port;

	/**
	 * 与服务端建立连接后得到的通道对象
	 */
	private Channel channel;

	private long encryptKey;

	protected TradeNettyTcpServer() {
		port = PORT_PUPPET;
	}

	//private int buildPort() {
	//	Double v = Math.random() * 60000;
	//	return v.intValue();
	//}
	protected int getPort() {
		return port;
	}

	protected void start(UserInfoVO faVO) {
		encryptKey = initEncryptKey();
		log.info("加密字符为{}", encryptKey);

		TradeTcpServerFactory.notifyTcpServerInfo(
				String.valueOf(this.encryptKey), String.valueOf(this.port));

		startTradeProxy(faVO);

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				//.childHandler(new CTPPuppetChannelInitializer())
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						//ch.pipeline().addLast(new ShowChannelHandler());
						ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(
								ByteOrder.LITTLE_ENDIAN, Integer.MAX_VALUE,
								0, 4,
								-4, 4, true));
						ch.pipeline().addLast("decoder", new VTPQuotServerNettyDescoder());
						ch.pipeline().addLast(new ServerChannelHandler());
					}
				})
				// 用于设置TCP连接的最大排队数
				.option(ChannelOption.SO_BACKLOG, 128)
				// 启用心跳保活机制
				.childOption(ChannelOption.SO_KEEPALIVE, true);

		try {
			ChannelFuture channelFuture = bootstrap.bind(port).sync();
			log.info("CTP中转服务启动成功，端口号为" + port);
			if ((null != channelFuture) && (channelFuture.isSuccess())) {
				channel = channelFuture.channel();
			}
		} catch (Exception e) {
			log.error("CTP中转服务启动失败，端口号为" + port, e);
		}
	}
	private void startTradeProxy(UserInfoVO ctpUserInfo) {
		log.info("调用交易代理");

		initOrderRef();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					/*TradeProxyEnum tradeProxyEnum = TradeProxyEnum.getByCode(1);
					if (null == tradeProxyEnum) {
						log.error("无效的交易代理编码 {}", ctpUserInfo.getTradeProxyCode());
					} else {
						log.info("交易代理商为{}", tradeProxyEnum.getNmCn());
					}
					Constructor<? extends TradeProxyBase> ctor
							= tradeProxyEnum.getImpl().getDeclaredConstructor(UserInfoVO.class);
					ctor.setAccessible(true);
					tradeProxy = ctor.newInstance(ctpUserInfo);
					tradeProxy.init();*/
					System.loadLibrary("thosttraderapi_se");
					log.info("加载完thosttraderapi_se");
					System.loadLibrary("thosttraderapi_wrap");
					log.info("加载完thosttraderapi_wrap");
					tradeProxy = new CTPTradeProxy(ctpUserInfo);
					tradeProxy.init();
					log.info("交易代理初始化成功");
				} catch (Exception e) {
					log.error("创建CTP链接失败", e);
				}
			}
		}).start();
	}

	/**
	 * 每次重启CTP连接的时候都需要重制报单引用
	 *
	 * @param
	 *
	 * @return void
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/08/22 10:01:55.
	 */
	private static void initOrderRef() {
		ORDER_REF = 1;
	}
	public static int getOrderRef() {
		return ORDER_REF++;
	}

	/**
	 * 获取加密Key
	 *
	 * @param
	 *
	 * @return int
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/05 11:04:33.
	 */
	public long initEncryptKey() {
		Double d = Math.pow(2, 8 * 7) * Math.random();
		return d.longValue();
	}
	public boolean checkoutEncryptKey(long key) {
		return encryptKey == key;
	}

	/**
	 * 报单录入请求，录入错误时对应响应OnRspOrderInsert、OnErrRtnOrderInsert，正确时对应回报OnRtnOrder、OnRtnTrade。
	 * 可以录入限价单、市价单、条件单等交易所支持的指令，撤单时使用ReqOrderAction。
	 * 不支持预埋单录入，预埋单请使用ReqParkedOrderInsert。
	 *
	 * @param inputOrderReqVO
	 *
	 * @return int 0，代表成功；-1，表示网络连接失败；-2，表示未处理请求超过许可数；-3，表示每秒发送请求数超过许可数。
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/04 10:58:01.
	 */
	public int insertOrder(InputOrderReqVO inputOrderReqVO) {
		return tradeProxy.reqInsertOrder(inputOrderReqVO);
	}

	/**
	 * 报单操作请求
	 * 错误响应: OnRspOrderAction，OnErrRtnOrderAction
	 * 正确响应：OnRtnOrder
	 *
	 * @param inputOrderActionReqVO
	 *
	 * @return int 0，代表成功；-1，表示网络连接失败；-2，表示未处理请求超过许可数；-3，表示每秒发送请求数超过许可数。
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/04 11:07:38.
	 */
	public int actionOrder(InputOrderActionReqVO inputOrderActionReqVO) {
		return tradeProxy.actionOrder(inputOrderActionReqVO);
	}

	/**
	 * 投资者结算结果确认，在开始每日交易前都需要先确认上一日结算单，只需要确认一次。对应响应OnRspSettlementInfoConfirm。
	 *
	 * @param settlementInfoConfirmReqVO
	 *
	 * @return int 0，代表成功；-1，表示网络连接失败；-2，表示未处理请求超过许可数；-3，表示每秒发送请求数超过许可数。
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/04 11:10:11.
	 */
	public int confirmSettlementInfo(SettlementInfoConfirmReqVO settlementInfoConfirmReqVO) {
		return tradeProxy.confirmSettlementInfo(settlementInfoConfirmReqVO);
	}

	/**
	 * 请求查询资金账户
	 *
	 * @param qryTradingAccountReqVO
	 *
	 * @return int 0，代表成功；-1，表示网络连接失败；-2，表示未处理请求超过许可数；-3，表示每秒发送请求数超过许可数。
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/04 11:13:36.
	 */
	public int qryTradingAccount(QryTradingAccountReqVO qryTradingAccountReqVO) {
		return tradeProxy.qryTradingAccount(qryTradingAccountReqVO);
	}

	public int qrySettlementInfoConfirm(QrySettlementInfoConfirmReqVO qrySettlementInfoReqVO) {
		return tradeProxy.qrySettlementInfoConfirm(qrySettlementInfoReqVO);
	}

	public int qrySettlementInfo(QrySettlementInfoReqVO qrySettlementInfoReqVO) {
		return tradeProxy.qrySettlementInfo(qrySettlementInfoReqVO);
	}

	/**
	 * 请求查询合约手续费率，对应响应OnRspQryInstrumentCommissionRate。如果InstrumentID填空，则返回持仓对应的合约手续费率。
	 *
	 * @param qryInstrumentCommissionRateReqVO
	 *
	 * @return int 0，代表成功；-1，表示网络连接失败；-2，表示未处理请求超过许可数；-3，表示每秒发送请求数超过许可数。
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/04 11:15:15.
	 */
	public int qryInstrumentCommissionRate(QryInstrumentCommissionRateReqVO qryInstrumentCommissionRateReqVO) {
		return tradeProxy.qryInstrumentCommissionRate(qryInstrumentCommissionRateReqVO);
	}

	public int qryInvestorPositionDetail(QryInvestorPositionDetailReqVO qryInvestorPositionDetailReqVO) {
		return tradeProxy.qryInvestorPositionDetail(qryInvestorPositionDetailReqVO);
	}

	public int qryTrade(QryTradeReqVO qryTradeReqVO) {
		return tradeProxy.qryTrade(qryTradeReqVO);
	}

	protected void shutdown() {
		try {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		} catch (Exception e) {
			log.error("", e);
		}
	}
}
