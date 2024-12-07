package com.xinqi.ctp_puppet.netty.gateway.tcp.server;


import com.google.gson.Gson;
import com.xinqi.ctp_puppet.common.UserInfoVO;
import com.xinqi.ctp_puppet.netty.gateway.qdp.QdpTraderSpiImpl;
import com.xinqi.ctp_puppet.netty.quot.VTPQuotServerNettyDescoder;
import com.xinqi.ctp_puppet.netty.gateway.tcp.ServerChannelHandler;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.InputOrderActionReqVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.InputOrderReqVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QryInstrumentCommissionRateReqVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QryInvestorPositionDetailReqVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QrySettlementInfoConfirmReqVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QrySettlementInfoReqVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QryTradeReqVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QryTradingAccountReqVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.SettlementInfoConfirmReqVO;
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
import qdp.qdptraderapi.CQdpFtdcInputOrderField;
import qdp.qdptraderapi.CQdpFtdcOrderActionField;
import qdp.qdptraderapi.CQdpFtdcQryInvestorAccountField;
import qdp.qdptraderapi.CQdpFtdcQryInvestorFeeField;
import qdp.qdptraderapi.CQdpFtdcQryInvestorPositionField;
import qdp.qdptraderapi.CQdpFtdcQryTradeField;
import qdp.qdptraderapi.CQdpFtdcTraderApi;
import qdp.qdptraderapi.QDP_TE_RESUME_TYPE;

import java.nio.ByteOrder;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/2 14:58
 **/
@Slf4j
public class TradeNettyTcpServer {

	private final static Integer PORT_PUPPET = 8099;

	static {
		//System.loadLibrary("thosttraderapi_se");
		//System.loadLibrary("thosttraderapi_wrap");
		System.loadLibrary("qdptraderapi");
		System.loadLibrary("qdptraderapi_wrap");
	}

	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workerGroup = new NioEventLoopGroup();

	private QdpTraderSpiImpl traderSpi;
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

		startCTP(faVO);

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
	private void startCTP(UserInfoVO ctpUserInfo) {
		log.info("调用CTP");

		initOrderRef();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					/*CThostFtdcTraderApi traderApi = CThostFtdcTraderApi.CreateFtdcTraderApi();
					traderSpi = new TraderSpiImpl(traderApi, ctpUserInfo);

					traderApi.RegisterSpi(traderSpi);
					traderApi.RegisterFront(ctpUserInfo.getTradeAddress());
					traderApi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
					traderApi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
					traderApi.Init();
					traderApi.Join();*/
					log.info("1");
					CQdpFtdcTraderApi traderApi = CQdpFtdcTraderApi.CreateFtdcTraderApi();
					log.info("2");
					traderSpi = new QdpTraderSpiImpl(traderApi, ctpUserInfo);
					log.info("3");
					traderApi.RegisterSpi(traderSpi);
					log.info("4");
					traderApi.RegisterFront(ctpUserInfo.getCtpTradeAddress());
					log.info("5");
					traderApi.SubscribePublicTopic(QDP_TE_RESUME_TYPE.QDP_TERT_RESTART);
					log.info("6");
					traderApi.SubscribePrivateTopic(QDP_TE_RESUME_TYPE.QDP_TERT_RESTART);
					log.info("7");
					traderApi.Init();
					log.info("8");
					traderApi.Join();
					log.info("9");
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
		/*CThostFtdcInputOrderField cThostFtdcInputOrderField = new CThostFtdcInputOrderField();
		cThostFtdcInputOrderField.setBrokerID(inputOrderReqVO.getBrokerID());
		cThostFtdcInputOrderField.setInvestorID(inputOrderReqVO.getInvestorID());
		cThostFtdcInputOrderField.setInstrumentID(inputOrderReqVO.getInstrumentID());
		cThostFtdcInputOrderField.setOrderRef(inputOrderReqVO.getOrderRef());
		cThostFtdcInputOrderField.setUserID(inputOrderReqVO.getUserID());
		cThostFtdcInputOrderField.setOrderPriceType(inputOrderReqVO.getOrderPriceType());
		cThostFtdcInputOrderField.setDirection(inputOrderReqVO.getDirection());
		cThostFtdcInputOrderField.setCombOffsetFlag(inputOrderReqVO.getCombOffsetFlag());
		cThostFtdcInputOrderField.setCombHedgeFlag(inputOrderReqVO.getCombHedgeFlag());
		cThostFtdcInputOrderField.setLimitPrice(inputOrderReqVO.getLimitPrice());
		cThostFtdcInputOrderField.setVolumeTotalOriginal(inputOrderReqVO.getVolumeTotalOriginal());
		cThostFtdcInputOrderField.setTimeCondition(inputOrderReqVO.getTimeCondition());
		cThostFtdcInputOrderField.setGTDDate(inputOrderReqVO.getGtdDate());
		cThostFtdcInputOrderField.setVolumeCondition(inputOrderReqVO.getVolumeCondition());
		cThostFtdcInputOrderField.setMinVolume(inputOrderReqVO.getMinVolume());
		cThostFtdcInputOrderField.setContingentCondition(inputOrderReqVO.getContingentCondition());
		cThostFtdcInputOrderField.setStopPrice(inputOrderReqVO.getStopPrice());
		cThostFtdcInputOrderField.setForceCloseReason(inputOrderReqVO.getForceCloseReason());
		cThostFtdcInputOrderField.setIsAutoSuspend(inputOrderReqVO.getIsAutoSuspend());
		cThostFtdcInputOrderField.setBusinessUnit(inputOrderReqVO.getBusinessUnit());
		cThostFtdcInputOrderField.setRequestID(inputOrderReqVO.getRequestID());
		cThostFtdcInputOrderField.setUserForceClose(inputOrderReqVO.getUserForceClose());
		cThostFtdcInputOrderField.setIsSwapOrder(inputOrderReqVO.getIsSwapOrder());
		cThostFtdcInputOrderField.setExchangeID(inputOrderReqVO.getExchangeID());
		cThostFtdcInputOrderField.setInvestUnitID(inputOrderReqVO.getInvestUnitID());
		cThostFtdcInputOrderField.setAccountID(inputOrderReqVO.getAccountID());
		cThostFtdcInputOrderField.setCurrencyID(inputOrderReqVO.getCurrencyID());
		cThostFtdcInputOrderField.setClientID(inputOrderReqVO.getClientID());
		cThostFtdcInputOrderField.setIPAddress(inputOrderReqVO.getIpAddress());
		cThostFtdcInputOrderField.setMacAddress(inputOrderReqVO.getMacAddress());
		return traderSpi.ReqInsertOrder(cThostFtdcInputOrderField);*/
		CQdpFtdcInputOrderField cThostFtdcInputOrderField = new CQdpFtdcInputOrderField();
		cThostFtdcInputOrderField.setBrokerID(inputOrderReqVO.getBrokerID());
		cThostFtdcInputOrderField.setInvestorID(inputOrderReqVO.getInvestorID());
		cThostFtdcInputOrderField.setInstrumentID(inputOrderReqVO.getInstrumentID());
		cThostFtdcInputOrderField.setUserOrderLocalID(Integer.valueOf(inputOrderReqVO.getOrderRef()));
		cThostFtdcInputOrderField.setUserID(inputOrderReqVO.getUserID());
		cThostFtdcInputOrderField.setOrderPriceType(inputOrderReqVO.getOrderPriceType());
		cThostFtdcInputOrderField.setDirection(inputOrderReqVO.getDirection());
		//cThostFtdcInputOrderField.setCombOffsetFlag(inputOrderReqVO.getCombOffsetFlag());
		//cThostFtdcInputOrderField.setCombHedgeFlag(inputOrderReqVO.getCombHedgeFlag());
		cThostFtdcInputOrderField.setLimitPrice(inputOrderReqVO.getLimitPrice());
		cThostFtdcInputOrderField.setVolume(inputOrderReqVO.getVolumeTotalOriginal());
		cThostFtdcInputOrderField.setTimeCondition(inputOrderReqVO.getTimeCondition());
		cThostFtdcInputOrderField.setGTDDate(inputOrderReqVO.getGtdDate());
		cThostFtdcInputOrderField.setVolumeCondition(inputOrderReqVO.getVolumeCondition());
		cThostFtdcInputOrderField.setMinVolume(inputOrderReqVO.getMinVolume());
		//cThostFtdcInputOrderField.setContingentCondition(inputOrderReqVO.getContingentCondition());
		cThostFtdcInputOrderField.setStopPrice(inputOrderReqVO.getStopPrice());
		cThostFtdcInputOrderField.setForceCloseReason(inputOrderReqVO.getForceCloseReason());
		cThostFtdcInputOrderField.setIsAutoSuspend(inputOrderReqVO.getIsAutoSuspend());
		cThostFtdcInputOrderField.setBusinessUnit(inputOrderReqVO.getBusinessUnit());
		//cThostFtdcInputOrderField.setRequestID(inputOrderReqVO.getRequestID());
		//cThostFtdcInputOrderField.setUserForceClose(inputOrderReqVO.getUserForceClose());
		//cThostFtdcInputOrderField.setIsSwapOrder(inputOrderReqVO.getIsSwapOrder());
		cThostFtdcInputOrderField.setExchangeID(inputOrderReqVO.getExchangeID());
		//cThostFtdcInputOrderField.setInvestUnitID(inputOrderReqVO.getInvestUnitID());
		//cThostFtdcInputOrderField.setAccountID(inputOrderReqVO.getAccountID());
		//cThostFtdcInputOrderField.setCurrencyID(inputOrderReqVO.getCurrencyID());
		//cThostFtdcInputOrderField.setClientID(inputOrderReqVO.getClientID());
		//cThostFtdcInputOrderField.setIPAddress(inputOrderReqVO.getIpAddress());
		//cThostFtdcInputOrderField.setMacAddress(inputOrderReqVO.getMacAddress());
		return traderSpi.ReqInsertOrder(cThostFtdcInputOrderField);
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
		/*CThostFtdcInputOrderActionField cThostFtdcInputOrderActionField = new CThostFtdcInputOrderActionField();
		cThostFtdcInputOrderActionField.setBrokerID(inputOrderActionReqVO.getBrokerID());
		cThostFtdcInputOrderActionField.setInvestorID(inputOrderActionReqVO.getInvestorID());
		cThostFtdcInputOrderActionField.setOrderActionRef(inputOrderActionReqVO.getOrderActionRef());
		cThostFtdcInputOrderActionField.setOrderRef(inputOrderActionReqVO.getOrderRef());
		cThostFtdcInputOrderActionField.setRequestID(inputOrderActionReqVO.getRequestID());
		cThostFtdcInputOrderActionField.setFrontID(inputOrderActionReqVO.getFrontID());
		cThostFtdcInputOrderActionField.setSessionID(inputOrderActionReqVO.getSessionID());
		cThostFtdcInputOrderActionField.setExchangeID(inputOrderActionReqVO.getExchangeID());
		cThostFtdcInputOrderActionField.setOrderSysID(inputOrderActionReqVO.getOrderSysID());
		cThostFtdcInputOrderActionField.setActionFlag(inputOrderActionReqVO.getActionFlag());
		cThostFtdcInputOrderActionField.setLimitPrice(inputOrderActionReqVO.getLimitPrice());
		cThostFtdcInputOrderActionField.setVolumeChange(inputOrderActionReqVO.getVolumeChange());
		cThostFtdcInputOrderActionField.setUserID(inputOrderActionReqVO.getUserID());
		cThostFtdcInputOrderActionField.setInstrumentID(inputOrderActionReqVO.getInstrumentID());
		cThostFtdcInputOrderActionField.setInvestUnitID(inputOrderActionReqVO.getInvestUnitID());
		cThostFtdcInputOrderActionField.setIPAddress(inputOrderActionReqVO.getIpAddress());
		cThostFtdcInputOrderActionField.setMacAddress(inputOrderActionReqVO.getMacAddress());
		return traderSpi.ReqOrderAction(cThostFtdcInputOrderActionField);*/
		CQdpFtdcOrderActionField cThostFtdcInputOrderActionField = new CQdpFtdcOrderActionField();
		cThostFtdcInputOrderActionField.setBrokerID(inputOrderActionReqVO.getBrokerID());
		cThostFtdcInputOrderActionField.setInvestorID(inputOrderActionReqVO.getInvestorID());
		cThostFtdcInputOrderActionField.setUserOrderActionLocalID(inputOrderActionReqVO.getOrderActionRef());
		cThostFtdcInputOrderActionField.setUserOrderLocalID(Integer.valueOf(inputOrderActionReqVO.getOrderRef()));
		//cThostFtdcInputOrderActionField.setRequestID(inputOrderActionReqVO.getRequestID());
		cThostFtdcInputOrderActionField.setFrontID(inputOrderActionReqVO.getFrontID());
		cThostFtdcInputOrderActionField.setSessionID(inputOrderActionReqVO.getSessionID());
		cThostFtdcInputOrderActionField.setExchangeID(inputOrderActionReqVO.getExchangeID());
		cThostFtdcInputOrderActionField.setOrderSysID(inputOrderActionReqVO.getOrderSysID());
		cThostFtdcInputOrderActionField.setActionFlag(inputOrderActionReqVO.getActionFlag());
		cThostFtdcInputOrderActionField.setLimitPrice(inputOrderActionReqVO.getLimitPrice());
		cThostFtdcInputOrderActionField.setVolumeChange(inputOrderActionReqVO.getVolumeChange());
		cThostFtdcInputOrderActionField.setUserID(inputOrderActionReqVO.getUserID());
		cThostFtdcInputOrderActionField.setInstrumentID(inputOrderActionReqVO.getInstrumentID());
		//cThostFtdcInputOrderActionField.setInvestUnitID(inputOrderActionReqVO.getInvestUnitID());
		//cThostFtdcInputOrderActionField.setIPAddress(inputOrderActionReqVO.getIpAddress());
		//cThostFtdcInputOrderActionField.setMacAddress(inputOrderActionReqVO.getMacAddress());
		return traderSpi.ReqOrderAction(cThostFtdcInputOrderActionField);
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
		/*CThostFtdcSettlementInfoConfirmField cThostFtdcSettlementInfoConfirmField = new CThostFtdcSettlementInfoConfirmField();
		cThostFtdcSettlementInfoConfirmField.setBrokerID(settlementInfoConfirmReqVO.getBrokerID());
		cThostFtdcSettlementInfoConfirmField.setInvestorID(settlementInfoConfirmReqVO.getInvestorID());
		cThostFtdcSettlementInfoConfirmField.setConfirmDate(settlementInfoConfirmReqVO.getConfirmDate());
		cThostFtdcSettlementInfoConfirmField.setConfirmTime(settlementInfoConfirmReqVO.getConfirmTime());
		cThostFtdcSettlementInfoConfirmField.setSettlementID(settlementInfoConfirmReqVO.getSettlementID());
		cThostFtdcSettlementInfoConfirmField.setAccountID(settlementInfoConfirmReqVO.getAccountID());
		cThostFtdcSettlementInfoConfirmField.setCurrencyID(settlementInfoConfirmReqVO.getCurrencyID());
		return traderSpi.ReqSettlementInfoConfirm(cThostFtdcSettlementInfoConfirmField);*/
		return 1;
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
		/*CThostFtdcQryTradingAccountField cThostFtdcQryTradingAccountField = new CThostFtdcQryTradingAccountField();
		cThostFtdcQryTradingAccountField.setBrokerID(qryTradingAccountReqVO.getBrokerID());
		cThostFtdcQryTradingAccountField.setInvestorID(qryTradingAccountReqVO.getInvestorID());
		cThostFtdcQryTradingAccountField.setCurrencyID(qryTradingAccountReqVO.getCurrencyID());
		cThostFtdcQryTradingAccountField.setBizType(qryTradingAccountReqVO.getBizType());
		cThostFtdcQryTradingAccountField.setAccountID(qryTradingAccountReqVO.getAccountID());
		return traderSpi.ReqQryTradingAccount(cThostFtdcQryTradingAccountField);*/
		CQdpFtdcQryInvestorAccountField cThostFtdcQryTradingAccountField = new CQdpFtdcQryInvestorAccountField();
		cThostFtdcQryTradingAccountField.setBrokerID(qryTradingAccountReqVO.getBrokerID());
		cThostFtdcQryTradingAccountField.setInvestorID(qryTradingAccountReqVO.getInvestorID());
		//cThostFtdcQryTradingAccountField.setCurrencyID(qryTradingAccountReqVO.getCurrencyID());
		//cThostFtdcQryTradingAccountField.setBizType(qryTradingAccountReqVO.getBizType());
		//cThostFtdcQryTradingAccountField.setAccountID(qryTradingAccountReqVO.getAccountID());
		return traderSpi.ReqQryTradingAccount(cThostFtdcQryTradingAccountField);
	}

	public int qrySettlementInfoConfirm(QrySettlementInfoConfirmReqVO qrySettlementInfoReqVO) {
		/*CThostFtdcQrySettlementInfoConfirmField cThostFtdcQrySettlementInfoConfirmField = new CThostFtdcQrySettlementInfoConfirmField();
		cThostFtdcQrySettlementInfoConfirmField.setBrokerID(qrySettlementInfoReqVO.getBrokerID());
		cThostFtdcQrySettlementInfoConfirmField.setInvestorID(qrySettlementInfoReqVO.getInvestorID());
		cThostFtdcQrySettlementInfoConfirmField.setAccountID(qrySettlementInfoReqVO.getAccountID());
		cThostFtdcQrySettlementInfoConfirmField.setCurrencyID(qrySettlementInfoReqVO.getCurrencyID());
		return traderSpi.ReqQrySettlementInfoConfirm(cThostFtdcQrySettlementInfoConfirmField);*/
		return 1;
	}

	public int qrySettlementInfo(QrySettlementInfoReqVO qrySettlementInfoReqVO) {
		/*CThostFtdcQrySettlementInfoField cThostFtdcQrySettlementInfoField = new CThostFtdcQrySettlementInfoField();
		cThostFtdcQrySettlementInfoField.setBrokerID(qrySettlementInfoReqVO.getBrokerID());
		cThostFtdcQrySettlementInfoField.setInvestorID(qrySettlementInfoReqVO.getInvestorID());
		cThostFtdcQrySettlementInfoField.setTradingDay(qrySettlementInfoReqVO.getTradingDay());
		cThostFtdcQrySettlementInfoField.setAccountID(qrySettlementInfoReqVO.getAccountID());
		cThostFtdcQrySettlementInfoField.setCurrencyID(qrySettlementInfoReqVO.getCurrencyID());
		return traderSpi.ReqQrySettlementInfo(cThostFtdcQrySettlementInfoField);*/
		return 1;
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
		/*CThostFtdcQryInstrumentCommissionRateField cThostFtdcQryInstrumentCommissionRateField = new CThostFtdcQryInstrumentCommissionRateField();
		cThostFtdcQryInstrumentCommissionRateField.setBrokerID(qryInstrumentCommissionRateReqVO.getBrokerID());
		cThostFtdcQryInstrumentCommissionRateField.setInvestorID(qryInstrumentCommissionRateReqVO.getInvestorID());
		cThostFtdcQryInstrumentCommissionRateField.setInstrumentID(qryInstrumentCommissionRateReqVO.getInstrumentID());
		cThostFtdcQryInstrumentCommissionRateField.setExchangeID(qryInstrumentCommissionRateReqVO.getExchangeID());
		cThostFtdcQryInstrumentCommissionRateField.setInvestUnitID(qryInstrumentCommissionRateReqVO.getInvestUnitID());
		return traderSpi.ReqQryInstrumentCommissionRate(cThostFtdcQryInstrumentCommissionRateField);*/
		CQdpFtdcQryInvestorFeeField cThostFtdcQryInstrumentCommissionRateField = new CQdpFtdcQryInvestorFeeField();
		cThostFtdcQryInstrumentCommissionRateField.setBrokerID(qryInstrumentCommissionRateReqVO.getBrokerID());
		cThostFtdcQryInstrumentCommissionRateField.setInvestorID(qryInstrumentCommissionRateReqVO.getInvestorID());
		cThostFtdcQryInstrumentCommissionRateField.setInstrumentID(qryInstrumentCommissionRateReqVO.getInstrumentID());
		cThostFtdcQryInstrumentCommissionRateField.setExchangeID(qryInstrumentCommissionRateReqVO.getExchangeID());
		//cThostFtdcQryInstrumentCommissionRateField.setInvestUnitID(qryInstrumentCommissionRateReqVO.getInvestUnitID());
		return traderSpi.ReqQryInstrumentCommissionRate(cThostFtdcQryInstrumentCommissionRateField);
	}

	public int qryInvestorPositionDetail(QryInvestorPositionDetailReqVO qryInvestorPositionDetailReqVO) {
		/*CThostFtdcQryInvestorPositionDetailField cThostFtdcQryInvestorPositionDetailField = new CThostFtdcQryInvestorPositionDetailField();
		cThostFtdcQryInvestorPositionDetailField.setBrokerID(qryInvestorPositionDetailReqVO.getBrokerID());
		cThostFtdcQryInvestorPositionDetailField.setInvestorID(qryInvestorPositionDetailReqVO.getInvestorID());
		cThostFtdcQryInvestorPositionDetailField.setInstrumentID(qryInvestorPositionDetailReqVO.getInstrumentID());
		cThostFtdcQryInvestorPositionDetailField.setExchangeID(qryInvestorPositionDetailReqVO.getExchangeID());
		cThostFtdcQryInvestorPositionDetailField.setInvestUnitID(qryInvestorPositionDetailReqVO.getInvestUnitID());
		return traderSpi.ReqQryInvestorPositionDetail(cThostFtdcQryInvestorPositionDetailField);*/
		CQdpFtdcQryInvestorPositionField cThostFtdcQryInvestorPositionDetailField = new CQdpFtdcQryInvestorPositionField();
		cThostFtdcQryInvestorPositionDetailField.setBrokerID(qryInvestorPositionDetailReqVO.getBrokerID());
		cThostFtdcQryInvestorPositionDetailField.setInvestorID(qryInvestorPositionDetailReqVO.getInvestorID());
		cThostFtdcQryInvestorPositionDetailField.setInstrumentID(qryInvestorPositionDetailReqVO.getInstrumentID());
		cThostFtdcQryInvestorPositionDetailField.setExchangeID(qryInvestorPositionDetailReqVO.getExchangeID());
		//cThostFtdcQryInvestorPositionDetailField.setInvestUnitID(qryInvestorPositionDetailReqVO.getInvestUnitID());
		return traderSpi.ReqQryInvestorPositionDetail(cThostFtdcQryInvestorPositionDetailField);
	}

	public int qryTrade(QryTradeReqVO qryTradeReqVO) {
		/*CThostFtdcQryTradeField cThostFtdcQryTradeField = new CThostFtdcQryTradeField();
		cThostFtdcQryTradeField.setBrokerID(qryTradeReqVO.getBrokerID());
		cThostFtdcQryTradeField.setInvestorID(qryTradeReqVO.getInvestorID());
		cThostFtdcQryTradeField.setInstrumentID(qryTradeReqVO.getInstrumentID());
		cThostFtdcQryTradeField.setExchangeID(qryTradeReqVO.getExchangeID());
		cThostFtdcQryTradeField.setTradeID(qryTradeReqVO.getTradeID());
		cThostFtdcQryTradeField.setTradeTimeStart(qryTradeReqVO.getTradeTimeStart());
		cThostFtdcQryTradeField.setTradeTimeEnd(qryTradeReqVO.getTradeTimeEnd());
		cThostFtdcQryTradeField.setInvestUnitID(qryTradeReqVO.getInvestUnitID());
		return traderSpi.ReqQryTrade(cThostFtdcQryTradeField);*/
		CQdpFtdcQryTradeField cThostFtdcQryTradeField = new CQdpFtdcQryTradeField();
		cThostFtdcQryTradeField.setBrokerID(qryTradeReqVO.getBrokerID());
		cThostFtdcQryTradeField.setInvestorID(qryTradeReqVO.getInvestorID());
		cThostFtdcQryTradeField.setInstrumentID(qryTradeReqVO.getInstrumentID());
		cThostFtdcQryTradeField.setExchangeID(qryTradeReqVO.getExchangeID());
		cThostFtdcQryTradeField.setTradeID(qryTradeReqVO.getTradeID());
		//cThostFtdcQryTradeField.setTradeTimeStart(qryTradeReqVO.getTradeTimeStart());
		//cThostFtdcQryTradeField.setTradeTimeEnd(qryTradeReqVO.getTradeTimeEnd());
		//cThostFtdcQryTradeField.setInvestUnitID(qryTradeReqVO.getInvestUnitID());
		return traderSpi.ReqQryTrade(cThostFtdcQryTradeField);
	}

	public void onRtnOrder() {

	}

	public void ctpStandBy() {

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
