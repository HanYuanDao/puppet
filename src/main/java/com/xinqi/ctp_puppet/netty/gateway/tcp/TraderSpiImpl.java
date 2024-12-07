package com.xinqi.ctp_puppet.netty.gateway.tcp;


import com.google.gson.Gson;
import com.xinqi.ctp_puppet.common.UserInfoVO;
import com.xinqi.ctp_puppet.common.DecodeUtils;
import com.xinqi.ctp_puppet.netty.gateway.tcp.server.MsgHandlerTask;
import com.xinqi.ctp_puppet.netty.gateway.tcp.server.CTPMsgHandlerThreadPool;
import com.xinqi.ctp_puppet.netty.gateway.tcp.server.CTPOnFuncMsgHandleEnum;
import com.xinqi.ctp_puppet.netty.gateway.tcp.server.TradeTcpServerFactory;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.ErrorRespVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.InputOrderActionRespVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.InputOrderRespVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QryInstrumentCommissionRateRespVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QryInvestorPositionDetailRespVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QrySettlementInfoConfirmRespVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QrySettlementInfoRespVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.QryTradingAccountRespVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.RespInfoVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.RtnOrderRespVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.RtnTradeRespVO;
import com.xinqi.ctp_puppet.netty.gateway.tcp.vo.SettlementInfoConfirmRespVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextListener;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 7/26/21 9:20 PM
 **/
@Slf4j
public class TraderSpiImpl implements ServletContextListener {

	/*private UserInfoVO ctpUserInfo;
	private CThostFtdcTraderApi traderApi;

	private Integer requestId = 0;

	private Gson GSON = new Gson();

	*//*@Autowired
	private CTPMsgHandlerThreadPool ctpMsgHandlerThreadPool;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
		ctpMsgHandlerThreadPool = webApplicationContext.getBean(CTPMsgHandlerThreadPool.class);
	}*//*

	public TraderSpiImpl(CThostFtdcTraderApi traderapi, UserInfoVO ctpUserInfo) {
		this.traderApi =  traderapi;
		this.ctpUserInfo = ctpUserInfo;
	}

	@Override
	public void OnFrontConnected() {
		log.info("On Front Connected");
		CThostFtdcReqAuthenticateField field = new CThostFtdcReqAuthenticateField();
		field.setBrokerID(ctpUserInfo.getBrokerId());
		field.setUserID(ctpUserInfo.getUserId());
		field.setAppID(ctpUserInfo.getAppId());
		field.setAuthCode(ctpUserInfo.getAuthCode());
		traderApi.ReqAuthenticate(field, getRequestId());
		log.info("Send ReqAuthenticate ok");
	}

	@Override
	public void OnFrontDisconnected(int nReason) {
		log.info("OnFrontDisconnected nReason[{}]", nReason);
	};

	@Override
	public void OnRspAuthenticate(CThostFtdcRspAuthenticateField pRspAuthenticateField, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		if (pRspInfo != null && pRspInfo.getErrorID() != 0) {
			log.info("Login Error ID[{}] ErrMsg[{}]\n", pRspInfo.getErrorID(), pRspInfo.getErrorMsg());
			return;
		}
		log.info("OnRspAuthenticate success!!!");
		CThostFtdcReqUserLoginField field = new CThostFtdcReqUserLoginField();
		field.setBrokerID(ctpUserInfo.getBrokerId());
		field.setUserID(ctpUserInfo.getUserId());
		field.setPassword(ctpUserInfo.getPassword());
		traderApi.ReqUserLogin(field, getRequestId());
		log.info("Send login ok");
	}

	@Override
	public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		if (pRspInfo != null && pRspInfo.getErrorID() != 0) {
			log.info("Login ErrorID[{}] ErrMsg[{}]\n", pRspInfo.getErrorID(), pRspInfo.getErrorMsg());
			return;
		}
		log.info("Login success!!!");
		TradeTcpServerFactory.setCTPWorking(true);
	}

	*//**
	 * 登出请求响应，当执行ReqUserLogout后，该方法被调用。
	 *
	 * @param pUserLogout
	 * @param pRspInfo
	 * @param nRequestID
	 * @param bIsLast
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 11:24:00.
	 *//*
	@Override
	public void OnRspUserLogout(CThostFtdcUserLogoutField pUserLogout, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		log.info("OnRspUserLogout nReason [{}] [{}]\n", pRspInfo.getErrorID(), pRspInfo.getErrorMsg());
		traderApi.Release();
	}

	*//**
	 * 请求查询合约响应，当执行ReqQryInstrument后，该方法被调用。
	 *
	 * @param pInstrument
	 * @param pRspInfo
	 * @param nRequestID
	 * @param bIsLast
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 11:23:26.
	 *//*
	@Override
	public void OnRspQryInstrument(CThostFtdcInstrumentField pInstrument, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		if (pRspInfo != null && pRspInfo.getErrorID() != 0) {
			log.info("OnRspQryInstrument ErrorID[{}] ErrMsg[{}]\n", pRspInfo.getErrorID(), DecodeUtils.decodeStr(pRspInfo.getErrorMsg()));
			return;
		}
	}

	*//**
	 * 请求查询投资者结算结果响应，当执行ReqQrySettlementInfo后，该方法被调用。
	 *
	 * @param var1
	 * @param var2
	 * @param nRequestID
	 * @param bIsLast
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 11:20:36.
	 *//*
	@Override
	public void OnRspQrySettlementInfo(CThostFtdcSettlementInfoField var1, CThostFtdcRspInfoField var2, int nRequestID, boolean bIsLast) {
		MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
		QrySettlementInfoRespVO qrySettlementInfoRespVO = new QrySettlementInfoRespVO();
		if (null != var1) {
			qrySettlementInfoRespVO.setTradingDay(var1.getTradingDay());
			qrySettlementInfoRespVO.setSettlementID(var1.getSettlementID());
			qrySettlementInfoRespVO.setBrokerID(var1.getBrokerID());
			qrySettlementInfoRespVO.setInvestorID(var1.getInvestorID());
			qrySettlementInfoRespVO.setSequenceNo(var1.getSequenceNo());
			qrySettlementInfoRespVO.setContent(var1.getContent());
			qrySettlementInfoRespVO.setAccountID(var1.getAccountID());
			qrySettlementInfoRespVO.setCurrencyID(var1.getCurrencyID());
		}
		RespInfoVO respInfoVO = new RespInfoVO();
		if (null != var2) {
			respInfoVO.setErrorID(var2.getErrorID());
			respInfoVO.setErrorMsg(var2.getErrorMsg());
		}
		Integer requestId = nRequestID;
		Boolean isLast = bIsLast;
		ctpMsgHandlerTask.loading(CTPOnFuncMsgHandleEnum.QRY_SETTLEMENT_INFO, qrySettlementInfoRespVO, respInfoVO, requestId, isLast);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
	}

	*//**
	 * 针对用户请求的出错通知。
	 *
	 * @param var1
	 * @param nRequestID
	 * @param bIsLast
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 11:13:44.
	 *//*
	@Override
	public void OnRspError(CThostFtdcRspInfoField var1, int nRequestID, boolean bIsLast) {
		MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
		ErrorRespVO errorRespVO = new ErrorRespVO();
		if (null != var1) {
			errorRespVO.setErrorID(var1.getErrorID());
			errorRespVO.setErrorMsg(var1.getErrorMsg());
		}
		Integer requestId = nRequestID;
		Boolean isLast = bIsLast;
		ctpMsgHandlerTask.loading(
				CTPOnFuncMsgHandleEnum.ERR, errorRespVO, null, requestId, isLast);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
	}

	*//**
	 * 投资者结算结果确认响应，当执行ReqSettlementInfoConfirm后，该方法被调用
	 *
	 * @param var1
	 * @param var2
	 * @param nRequestID
	 * @param bIsLast
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 10:53:49.
	 *//*
	@Override
	public void OnRspSettlementInfoConfirm(
			CThostFtdcSettlementInfoConfirmField var1, CThostFtdcRspInfoField var2,
			int nRequestID, boolean bIsLast) {
		MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
		SettlementInfoConfirmRespVO settlementInfoConfirmRespVO = new SettlementInfoConfirmRespVO();
		if (null != var1) {
			settlementInfoConfirmRespVO.setBrokerID(var1.getBrokerID());
			settlementInfoConfirmRespVO.setInvestorID(var1.getInvestorID());
			settlementInfoConfirmRespVO.setConfirmDate(var1.getConfirmDate());
			settlementInfoConfirmRespVO.setConfirmTime(var1.getConfirmTime());
			settlementInfoConfirmRespVO.setSettlementID(var1.getSettlementID());
			settlementInfoConfirmRespVO.setAccountID(var1.getAccountID());
			settlementInfoConfirmRespVO.setCurrencyID(var1.getCurrencyID());
		}
		RespInfoVO respInfoVO = new RespInfoVO();
		if (null != var2) {
			respInfoVO.setErrorID(var2.getErrorID());
			respInfoVO.setErrorMsg(var2.getErrorMsg());
		}
		Integer requestId = nRequestID;
		Boolean isLast = bIsLast;
		ctpMsgHandlerTask.loading(
				CTPOnFuncMsgHandleEnum.SETTLEMENT_INFO_CONFIRM, settlementInfoConfirmRespVO, respInfoVO, requestId, isLast);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
	}

	*//**
	 * 报单操作错误回报，当执行ReqOrderAction后有字段填写不对之类的CTP报错则通过此接口返回
	 *
	 * @param var1
	 * @param var2
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 10:55:40.
	 *//*
	@Override
	public void OnErrRtnOrderAction(
			CThostFtdcOrderActionField var1, CThostFtdcRspInfoField var2) {
		MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
		InputOrderActionRespVO inputOrderActionRespVO = new InputOrderActionRespVO();
		if (null != var1) {
			inputOrderActionRespVO.setBrokerID(var1.getBrokerID());
			inputOrderActionRespVO.setInvestorID(var1.getInvestorID());
			inputOrderActionRespVO.setOrderActionRef(var1.getOrderActionRef());
			inputOrderActionRespVO.setOrderRef(var1.getOrderRef());
			inputOrderActionRespVO.setRequestID(var1.getRequestID());
			inputOrderActionRespVO.setFrontID(var1.getFrontID());
			inputOrderActionRespVO.setSessionID(var1.getSessionID());
			inputOrderActionRespVO.setExchangeID(var1.getExchangeID());
			inputOrderActionRespVO.setOrderSysID(var1.getOrderSysID());
			inputOrderActionRespVO.setActionFlag(var1.getActionFlag());
			inputOrderActionRespVO.setLimitPrice(var1.getLimitPrice());
			inputOrderActionRespVO.setVolumeChange(var1.getVolumeChange());
			inputOrderActionRespVO.setUserID(var1.getUserID());
			inputOrderActionRespVO.setInstrumentID(var1.getInstrumentID());
			inputOrderActionRespVO.setInvestUnitID(var1.getInvestUnitID());
			inputOrderActionRespVO.setIpAddress(var1.getIPAddress());
			inputOrderActionRespVO.setMacAddress(var1.getMacAddress());
		}
		RespInfoVO respInfoVO = new RespInfoVO();
		if (null != var2) {
			respInfoVO.setErrorID(var2.getErrorID());
			respInfoVO.setErrorMsg(var2.getErrorMsg());
		}
		ctpMsgHandlerTask.loading(
				CTPOnFuncMsgHandleEnum.ERR_RTN_ORDER_ACTION, inputOrderActionRespVO, respInfoVO, null, null);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
	}

	*//**
	 * 报单操作请求响应，当执行ReqOrderAction后有字段填写不对之类的CTP报错则通过此接口返回
	 *
	 * @param var1
	 * @param var2
	 * @param nRequestID
	 * @param bIsLast
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 10:56:47.
	 *//*
	@Override
	public void OnRspOrderAction(
			CThostFtdcInputOrderActionField var1, CThostFtdcRspInfoField var2,
			int nRequestID, boolean bIsLast) {
		MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
		InputOrderActionRespVO inputOrderActionRespVO = new InputOrderActionRespVO();
		if (null != var1) {
			inputOrderActionRespVO.setBrokerID(var1.getBrokerID());
			inputOrderActionRespVO.setInvestorID(var1.getInvestorID());
			inputOrderActionRespVO.setOrderActionRef(var1.getOrderActionRef());
			inputOrderActionRespVO.setOrderRef(var1.getOrderRef());
			inputOrderActionRespVO.setRequestID(var1.getRequestID());
			inputOrderActionRespVO.setFrontID(var1.getFrontID());
			inputOrderActionRespVO.setSessionID(var1.getSessionID());
			inputOrderActionRespVO.setExchangeID(var1.getExchangeID());
			inputOrderActionRespVO.setOrderSysID(var1.getOrderSysID());
			inputOrderActionRespVO.setActionFlag(var1.getActionFlag());
			inputOrderActionRespVO.setLimitPrice(var1.getLimitPrice());
			inputOrderActionRespVO.setVolumeChange(var1.getVolumeChange());
			inputOrderActionRespVO.setUserID(var1.getUserID());
			inputOrderActionRespVO.setInstrumentID(var1.getInstrumentID());
			inputOrderActionRespVO.setInvestUnitID(var1.getInvestUnitID());
			inputOrderActionRespVO.setIpAddress(var1.getIPAddress());
			inputOrderActionRespVO.setMacAddress(var1.getMacAddress());
		}
		RespInfoVO respInfoVO = new RespInfoVO();
		if (null != var2) {
			respInfoVO.setErrorID(var2.getErrorID());
			respInfoVO.setErrorMsg(var2.getErrorMsg());
		}
		Integer requestId = nRequestID;
		Boolean isLast = bIsLast;
		ctpMsgHandlerTask.loading(
				CTPOnFuncMsgHandleEnum.ORDER_ACTION, inputOrderActionRespVO, respInfoVO, requestId, isLast);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
	}

	*//**
	 * 报单录入错误回报，当执行ReqOrderInsert后有字段填写不对之类的CTP报错则通过此接口返回
	 *
	 * @param var1
	 * @param var2
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 10:57:14.
	 *//*
	@Override
	public void OnErrRtnOrderInsert(
			CThostFtdcInputOrderField var1, CThostFtdcRspInfoField var2) {
		MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
		InputOrderRespVO inputOrderRespVO = new InputOrderRespVO();
		if (null != var1) {
			inputOrderRespVO.setBrokerID(var1.getBrokerID());
			inputOrderRespVO.setInvestorID(var1.getInvestorID());
			inputOrderRespVO.setInstrumentID(var1.getInstrumentID());
			inputOrderRespVO.setOrderRef(var1.getOrderRef());
			inputOrderRespVO.setUserID(var1.getUserID());
			inputOrderRespVO.setOrderPriceType(var1.getOrderPriceType());
			inputOrderRespVO.setDirection(var1.getDirection());
			inputOrderRespVO.setCombOffsetFlag(var1.getCombOffsetFlag());
			inputOrderRespVO.setCombHedgeFlag(var1.getCombHedgeFlag());
			inputOrderRespVO.setLimitPrice(var1.getLimitPrice());
			inputOrderRespVO.setVolumeTotalOriginal(var1.getVolumeTotalOriginal());
			inputOrderRespVO.setTimeCondition(var1.getTimeCondition());
			inputOrderRespVO.setGtdDate(var1.getGTDDate());
			inputOrderRespVO.setVolumeCondition(var1.getVolumeCondition());
			inputOrderRespVO.setMinVolume(var1.getMinVolume());
			inputOrderRespVO.setContingentCondition(var1.getContingentCondition());
			inputOrderRespVO.setStopPrice(var1.getStopPrice());
			inputOrderRespVO.setForceCloseReason(var1.getForceCloseReason());
			inputOrderRespVO.setIsAutoSuspend(var1.getIsAutoSuspend());
			inputOrderRespVO.setBusinessUnit(var1.getBusinessUnit());
			inputOrderRespVO.setRequestID(var1.getRequestID());
			inputOrderRespVO.setUserForceClose(var1.getUserForceClose());
			inputOrderRespVO.setIsSwapOrder(var1.getIsSwapOrder());
			inputOrderRespVO.setExchangeID(var1.getExchangeID());
			inputOrderRespVO.setInvestUnitID(var1.getInvestUnitID());
			inputOrderRespVO.setAccountID(var1.getAccountID());
			inputOrderRespVO.setCurrencyID(var1.getCurrencyID());
			inputOrderRespVO.setClientID(var1.getClientID());
			inputOrderRespVO.setIpAddress(var1.getIPAddress());
			inputOrderRespVO.setMacAddress(var1.getMacAddress());
		}
		RespInfoVO respInfoVO = new RespInfoVO();
		if (null != var2) {
			respInfoVO.setErrorID(var2.getErrorID());
			respInfoVO.setErrorMsg(var2.getErrorMsg());
		}
		ctpMsgHandlerTask.loading(
				CTPOnFuncMsgHandleEnum.ERR_RTN_ORDER_INSERT, inputOrderRespVO, respInfoVO, null, null);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
	}

	*//**
	 * 报单录入请求响应，当执行ReqOrderInsert后有字段填写不对之类的CTP报错则通过此接口返回
	 *
	 * @param var1
	 * @param var2
	 * @param nRequestID
	 * @param bIsLast
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 10:57:54.
	 *//*
	@Override
	public void OnRspOrderInsert(
			CThostFtdcInputOrderField var1, CThostFtdcRspInfoField var2,
			int nRequestID, boolean bIsLast) {
		//InputOrderRespVO inputOrderRespVO = new InputOrderRespVO();
		//inputOrderRespVO.setBrokerID(var1.getBrokerID());
		//inputOrderRespVO.setInvestorID(var1.getInvestorID());
		//inputOrderRespVO.setInstrumentID(var1.getInstrumentID());
		//inputOrderRespVO.setOrderRef(var1.getOrderRef());
		//inputOrderRespVO.setUserID(var1.getUserID());
		//inputOrderRespVO.setOrderPriceType(var1.getOrderPriceType());
		//inputOrderRespVO.setDirection(var1.getDirection());
		//inputOrderRespVO.setCombOffsetFlag(var1.getCombOffsetFlag());
		//inputOrderRespVO.setCombHedgeFlag(var1.getCombHedgeFlag());
		//inputOrderRespVO.setLimitPrice(var1.getLimitPrice());
		//inputOrderRespVO.setVolumeTotalOriginal(var1.getVolumeTotalOriginal());
		//inputOrderRespVO.setTimeCondition(var1.getTimeCondition());
		//inputOrderRespVO.setGtdDate(var1.getGTDDate());
		//inputOrderRespVO.setVolumeCondition(var1.getVolumeCondition());
		//inputOrderRespVO.setMinVolume(var1.getMinVolume());
		//inputOrderRespVO.setContingentCondition(var1.getContingentCondition());
		//inputOrderRespVO.setStopPrice(var1.getStopPrice());
		//inputOrderRespVO.setForceCloseReason(var1.getForceCloseReason());
		//inputOrderRespVO.setIsAutoSuspend(var1.getIsAutoSuspend());
		//inputOrderRespVO.setBusinessUnit(var1.getBusinessUnit());
		//inputOrderRespVO.setRequestID(var1.getRequestID());
		//inputOrderRespVO.setUserForceClose(var1.getUserForceClose());
		//inputOrderRespVO.setIsSwapOrder(var1.getIsSwapOrder());
		//inputOrderRespVO.setExchangeID(var1.getExchangeID());
		//inputOrderRespVO.setInvestUnitID(var1.getInvestUnitID());
		//inputOrderRespVO.setAccountID(var1.getAccountID());
		//inputOrderRespVO.setCurrencyID(var1.getCurrencyID());
		//inputOrderRespVO.setClientID(var1.getClientID());
		//inputOrderRespVO.setIpAddress(var1.getIPAddress());
		//inputOrderRespVO.setMacAddress(var1.getMacAddress());
		//RespInfoVO respInfoVO = new RespInfoVO();
		//if (null != var2) {
		//	respInfoVO.setErrorID(var2.getErrorID());
		//	respInfoVO.setErrorMsg(var2.getErrorMsg());
		//}
		//Integer requestId = nRequestID;
		//Boolean isLast = bIsLast;
		//CTPMsgHandlerThreadPool.execute(
		//		new CTPMsgHandlerTask(
		//				CTPOnFuncMsgHandleEnum.ERR_RTN_ORDER_INSERT,
		//				inputOrderRespVO, respInfoVO, requestId, isLast));
	}

	*//**
	 * 请求查询资金账户响应，当执行ReqQryTradingAccount后，该方法被调用。
	 *
	 * @param var1
	 * @param var2
	 * @param nRequestID
	 * @param bIsLast
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 11:00:46.
	 *//*
	@Override
	public void OnRspQryTradingAccount(
			CThostFtdcTradingAccountField var1, CThostFtdcRspInfoField var2,
			int nRequestID, boolean bIsLast) {
		MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
		QryTradingAccountRespVO qryTradingAccountRespVO = new QryTradingAccountRespVO();
		if (null != var1) {
			qryTradingAccountRespVO.setBrokerID(var1.getBrokerID());
			qryTradingAccountRespVO.setAccountID(var1.getAccountID());
			qryTradingAccountRespVO.setPreMortgage(var1.getPreMortgage());
			qryTradingAccountRespVO.setPreCredit(var1.getPreCredit());
			qryTradingAccountRespVO.setPreDeposit(var1.getPreDeposit());
			qryTradingAccountRespVO.setPreBalance(var1.getPreBalance());
			qryTradingAccountRespVO.setPreMargin(var1.getPreMargin());
			qryTradingAccountRespVO.setInterestBase(var1.getInterestBase());
			qryTradingAccountRespVO.setInterest(var1.getInterest());
			qryTradingAccountRespVO.setDeposit(var1.getDeposit());
			qryTradingAccountRespVO.setWithdraw(var1.getWithdraw());
			qryTradingAccountRespVO.setFrozenMargin(var1.getFrozenMargin());
			qryTradingAccountRespVO.setFrozenCash(var1.getFrozenCash());
			qryTradingAccountRespVO.setFrozenCommission(var1.getFrozenCommission());
			qryTradingAccountRespVO.setCurrMargin(var1.getCurrMargin());
			qryTradingAccountRespVO.setCashIn(var1.getCashIn());
			qryTradingAccountRespVO.setCommission(var1.getCommission());
			qryTradingAccountRespVO.setCloseProfit(var1.getCloseProfit());
			qryTradingAccountRespVO.setPositionProfit(var1.getPositionProfit());
			qryTradingAccountRespVO.setBalance(var1.getBalance());
			qryTradingAccountRespVO.setAvailable(var1.getAvailable());
			qryTradingAccountRespVO.setWithdrawQuota(var1.getWithdrawQuota());
			qryTradingAccountRespVO.setReserve(var1.getReserve());
			qryTradingAccountRespVO.setTradingDay(var1.getTradingDay());
			qryTradingAccountRespVO.setSettlementID(var1.getSettlementID());
			qryTradingAccountRespVO.setCredit(var1.getCredit());
			qryTradingAccountRespVO.setMortgage(var1.getMortgage());
			qryTradingAccountRespVO.setExchangeMargin(var1.getExchangeMargin());
			qryTradingAccountRespVO.setDeliveryMargin(var1.getDeliveryMargin());
			qryTradingAccountRespVO.setExchangeDeliveryMargin(var1.getExchangeDeliveryMargin());
			qryTradingAccountRespVO.setReserveBalance(var1.getReserveBalance());
			qryTradingAccountRespVO.setCurrencyID(var1.getCurrencyID());
			qryTradingAccountRespVO.setPreFundMortgageIn(var1.getPreFundMortgageIn());
			qryTradingAccountRespVO.setPreFundMortgageOut(var1.getPreFundMortgageOut());
			qryTradingAccountRespVO.setFundMortgageIn(var1.getFundMortgageIn());
			qryTradingAccountRespVO.setFundMortgageOut(var1.getFundMortgageOut());
			qryTradingAccountRespVO.setFundMortgageAvailable(var1.getFundMortgageAvailable());
			qryTradingAccountRespVO.setMortgageableFund(var1.getMortgageableFund());
			qryTradingAccountRespVO.setSpecProductMargin(var1.getSpecProductMargin());
			qryTradingAccountRespVO.setSpecProductFrozenMargin(var1.getSpecProductFrozenMargin());
			qryTradingAccountRespVO.setSpecProductCommission(var1.getSpecProductCommission());
			qryTradingAccountRespVO.setSpecProductFrozenCommission(var1.getSpecProductFrozenCommission());
			qryTradingAccountRespVO.setSpecProductPositionProfit(var1.getSpecProductPositionProfit());
			qryTradingAccountRespVO.setSpecProductCloseProfit(var1.getSpecProductCloseProfit());
			qryTradingAccountRespVO.setSpecProductPositionProfitByAlg(var1.getSpecProductPositionProfitByAlg());
			qryTradingAccountRespVO.setSpecProductExchangeMargin(var1.getSpecProductExchangeMargin());
			qryTradingAccountRespVO.setBizType(var1.getBizType());
			qryTradingAccountRespVO.setFrozenSwap(var1.getFrozenSwap());
			qryTradingAccountRespVO.setRemainSwap(var1.getRemainSwap());
		}
		RespInfoVO respInfoVO = new RespInfoVO();
		if (null != var2) {
			respInfoVO.setErrorID(var2.getErrorID());
			respInfoVO.setErrorMsg(var2.getErrorMsg());
		}
		Integer requestId = nRequestID;
		Boolean isLast = bIsLast;
		ctpMsgHandlerTask.loading(
				CTPOnFuncMsgHandleEnum.QRY_TRADING_ACCOUNT, qryTradingAccountRespVO, respInfoVO, requestId, isLast);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
	}

	*//**
	 * 请求查询合约手续费率响应，当执行ReqQryInstrumentCommissionRate后，该方法被调用。
	 *
	 * @param var1
	 * @param var2
	 * @param nRequestID
	 * @param bIsLast
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 11:01:13.
	 *//*
	@Override
	public void OnRspQryInstrumentCommissionRate(
			CThostFtdcInstrumentCommissionRateField var1, CThostFtdcRspInfoField var2,
			int nRequestID, boolean bIsLast) {
		MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
		QryInstrumentCommissionRateRespVO qryInstrumentCommissionRateRespVO = new QryInstrumentCommissionRateRespVO();
		if (null != var1) {
			qryInstrumentCommissionRateRespVO.setInstrumentID(var1.getInstrumentID());
			qryInstrumentCommissionRateRespVO.setInvestorRange(var1.getInvestorRange());
			qryInstrumentCommissionRateRespVO.setBrokerID(var1.getBrokerID());
			qryInstrumentCommissionRateRespVO.setInvestorID(var1.getInvestorID());
			qryInstrumentCommissionRateRespVO.setOpenRatioByMoney(var1.getOpenRatioByMoney());
			qryInstrumentCommissionRateRespVO.setOpenRatioByVolume(var1.getOpenRatioByVolume());
			qryInstrumentCommissionRateRespVO.setCloseRatioByMoney(var1.getCloseRatioByMoney());
			qryInstrumentCommissionRateRespVO.setCloseRatioByVolume(var1.getCloseRatioByVolume());
			qryInstrumentCommissionRateRespVO.setCloseTodayRatioByMoney(var1.getCloseTodayRatioByMoney());
			qryInstrumentCommissionRateRespVO.setCloseTodayRatioByVolume(var1.getCloseTodayRatioByVolume());
			qryInstrumentCommissionRateRespVO.setExchangeID(var1.getExchangeID());
			qryInstrumentCommissionRateRespVO.setBizType(var1.getBizType());
			qryInstrumentCommissionRateRespVO.setInvestUnitID(var1.getInvestUnitID());
		}
		RespInfoVO respInfoVO = new RespInfoVO();
		if (null != var2) {
			respInfoVO.setErrorID(var2.getErrorID());
			respInfoVO.setErrorMsg(var2.getErrorMsg());
		}
		Integer requestId = nRequestID;
		Boolean isLast = bIsLast;
		ctpMsgHandlerTask.loading(
				CTPOnFuncMsgHandleEnum.QRY_INSTRUMENT_COMMISSION_RATE, qryInstrumentCommissionRateRespVO, respInfoVO, requestId, isLast);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
	}

	*//**
	 * 报单通知，当执行ReqOrderInsert后并且报出后，收到返回则调用此接口，私有流回报。
	 *
	 * @param pRtnOrder
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 11:01:44.
	 *//*
	@Override
	public void OnRtnOrder(CThostFtdcOrderField pRtnOrder) {
		MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
		RtnOrderRespVO rtnOrderRespVO = new RtnOrderRespVO();
		if (null != pRtnOrder) {
			rtnOrderRespVO.setBrokerID(pRtnOrder.getBrokerID());
			rtnOrderRespVO.setInvestorID(pRtnOrder.getInvestorID());
			rtnOrderRespVO.setInstrumentID(pRtnOrder.getInstrumentID());
			rtnOrderRespVO.setOrderRef(pRtnOrder.getOrderRef());
			rtnOrderRespVO.setUserID(pRtnOrder.getUserID());
			rtnOrderRespVO.setOrderPriceType(pRtnOrder.getOrderPriceType());
			rtnOrderRespVO.setDirection(pRtnOrder.getDirection());
			rtnOrderRespVO.setCombOffsetFlag(pRtnOrder.getCombOffsetFlag());
			rtnOrderRespVO.setCombHedgeFlag(pRtnOrder.getCombHedgeFlag());
			rtnOrderRespVO.setLimitPrice(pRtnOrder.getLimitPrice());
			rtnOrderRespVO.setVolumeTotalOriginal(pRtnOrder.getVolumeTotalOriginal());
			rtnOrderRespVO.setTimeCondition(pRtnOrder.getTimeCondition());
			rtnOrderRespVO.setGtdDate(pRtnOrder.getGTDDate());
			rtnOrderRespVO.setVolumeCondition(pRtnOrder.getVolumeCondition());
			rtnOrderRespVO.setMinVolume(pRtnOrder.getMinVolume());
			rtnOrderRespVO.setContingentCondition(pRtnOrder.getContingentCondition());
			rtnOrderRespVO.setStopPrice(pRtnOrder.getStopPrice());
			rtnOrderRespVO.setForceCloseReason(pRtnOrder.getForceCloseReason());
			rtnOrderRespVO.setIsAutoSuspend(pRtnOrder.getIsAutoSuspend());
			rtnOrderRespVO.setBusinessUnit(pRtnOrder.getBusinessUnit());
			rtnOrderRespVO.setRequestID(pRtnOrder.getRequestID());
			rtnOrderRespVO.setOrderLocalID(pRtnOrder.getOrderLocalID());
			rtnOrderRespVO.setExchangeID(pRtnOrder.getExchangeID());
			rtnOrderRespVO.setParticipantID(pRtnOrder.getParticipantID());
			rtnOrderRespVO.setClientID(pRtnOrder.getClientID());
			rtnOrderRespVO.setExchangeInstID(pRtnOrder.getExchangeInstID());
			rtnOrderRespVO.setTraderID(pRtnOrder.getTraderID());
			rtnOrderRespVO.setInstallID(pRtnOrder.getInstallID());
			rtnOrderRespVO.setOrderSubmitStatus(pRtnOrder.getOrderSubmitStatus());
			rtnOrderRespVO.setNotifySequence(pRtnOrder.getNotifySequence());
			rtnOrderRespVO.setTradingDay(pRtnOrder.getTradingDay());
			rtnOrderRespVO.setSettlementID(pRtnOrder.getSettlementID());
			rtnOrderRespVO.setOrderSysID(pRtnOrder.getOrderSysID());
			rtnOrderRespVO.setOrderSource(pRtnOrder.getOrderSource());
			rtnOrderRespVO.setOrderStatus(pRtnOrder.getOrderStatus());
			rtnOrderRespVO.setOrderType(pRtnOrder.getOrderType());
			rtnOrderRespVO.setVolumeTraded(pRtnOrder.getVolumeTraded());
			rtnOrderRespVO.setVolumeTotal(pRtnOrder.getVolumeTotal());
			rtnOrderRespVO.setInsertDate(pRtnOrder.getInsertDate());
			rtnOrderRespVO.setInsertTime(pRtnOrder.getInsertTime());
			rtnOrderRespVO.setActiveTime(pRtnOrder.getActiveTime());
			rtnOrderRespVO.setSuspendTime(pRtnOrder.getSuspendTime());
			rtnOrderRespVO.setUpdateTime(pRtnOrder.getUpdateTime());
			rtnOrderRespVO.setCancelTime(pRtnOrder.getCancelTime());
			rtnOrderRespVO.setActiveTraderID(pRtnOrder.getActiveTraderID());
			rtnOrderRespVO.setClearingPartID(pRtnOrder.getClearingPartID());
			rtnOrderRespVO.setSequenceNo(pRtnOrder.getSequenceNo());
			rtnOrderRespVO.setFrontID(pRtnOrder.getFrontID());
			rtnOrderRespVO.setSessionID(pRtnOrder.getSessionID());
			rtnOrderRespVO.setUserProductInfo(pRtnOrder.getUserProductInfo());
			rtnOrderRespVO.setStatusMsg(pRtnOrder.getStatusMsg());
			rtnOrderRespVO.setUserForceClose(pRtnOrder.getUserForceClose());
			rtnOrderRespVO.setActiveUserID(pRtnOrder.getActiveUserID());
			rtnOrderRespVO.setBrokerOrderSeq(pRtnOrder.getBrokerOrderSeq());
			rtnOrderRespVO.setRelativeOrderSysID(pRtnOrder.getRelativeOrderSysID());
			rtnOrderRespVO.setZceTotalTradedVolume(pRtnOrder.getZCETotalTradedVolume());
			rtnOrderRespVO.setIsSwapOrder(pRtnOrder.getIsSwapOrder());
			rtnOrderRespVO.setBranchID(pRtnOrder.getBranchID());
			rtnOrderRespVO.setInvestUnitID(pRtnOrder.getInvestUnitID());
			rtnOrderRespVO.setAccountID(pRtnOrder.getAccountID());
			rtnOrderRespVO.setCurrencyID(pRtnOrder.getCurrencyID());
			rtnOrderRespVO.setIpAddress(pRtnOrder.getIPAddress());
			rtnOrderRespVO.setMacAddress(pRtnOrder.getMacAddress());
		}
		ctpMsgHandlerTask.loading(
				CTPOnFuncMsgHandleEnum.RTN_ORDER, rtnOrderRespVO, null, null, null);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);

		CTPOnFuncMsgHandleEnum.RTN_ORDER.postHandle(rtnOrderRespVO);
	}

	*//**
	 * 成交通知，报单发出后有成交则通过此接口返回。私有流
	 *
	 * @param pRtnTrade
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 11:03:27.
	 *//*
	@Override
	public void OnRtnTrade(CThostFtdcTradeField pRtnTrade) {
		MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
		RtnTradeRespVO rtnTradeRespVO = new RtnTradeRespVO();
		if (null != pRtnTrade) {
			rtnTradeRespVO.setBrokerID(pRtnTrade.getBrokerID());
			rtnTradeRespVO.setInvestorID(pRtnTrade.getInvestorID());
			rtnTradeRespVO.setInstrumentID(pRtnTrade.getInstrumentID());
			rtnTradeRespVO.setOrderRef(pRtnTrade.getOrderRef());
			rtnTradeRespVO.setUserID(pRtnTrade.getUserID());
			rtnTradeRespVO.setExchangeID(pRtnTrade.getExchangeID());
			rtnTradeRespVO.setTradeID(pRtnTrade.getTradeID());
			rtnTradeRespVO.setDirection(pRtnTrade.getDirection());
			rtnTradeRespVO.setOrderSysID(pRtnTrade.getOrderSysID());
			rtnTradeRespVO.setParticipantID(pRtnTrade.getParticipantID());
			rtnTradeRespVO.setClientID(pRtnTrade.getClientID());
			rtnTradeRespVO.setTradingRole(pRtnTrade.getTradingRole());
			rtnTradeRespVO.setExchangeInstID(pRtnTrade.getExchangeInstID());
			rtnTradeRespVO.setOffsetFlag(pRtnTrade.getOffsetFlag());
			rtnTradeRespVO.setHedgeFlag(pRtnTrade.getHedgeFlag());
			rtnTradeRespVO.setPrice(pRtnTrade.getPrice());
			rtnTradeRespVO.setVolume(pRtnTrade.getVolume());
			rtnTradeRespVO.setTradeDate(pRtnTrade.getTradeDate());
			rtnTradeRespVO.setTradeTime(pRtnTrade.getTradeTime());
			rtnTradeRespVO.setTradeType(pRtnTrade.getTradeType());
			rtnTradeRespVO.setPriceSource(pRtnTrade.getPriceSource());
			rtnTradeRespVO.setTraderID(pRtnTrade.getTraderID());
			rtnTradeRespVO.setOrderLocalID(pRtnTrade.getOrderLocalID());
			rtnTradeRespVO.setClearingPartID(pRtnTrade.getClearingPartID());
			rtnTradeRespVO.setBusinessUnit(pRtnTrade.getBusinessUnit());
			rtnTradeRespVO.setSequenceNo(pRtnTrade.getSequenceNo());
			rtnTradeRespVO.setTradingDay(pRtnTrade.getTradingDay());
			rtnTradeRespVO.setSettlementID(pRtnTrade.getSettlementID());
			rtnTradeRespVO.setBrokerOrderSeq(pRtnTrade.getBrokerOrderSeq());
			rtnTradeRespVO.setTradeSource(pRtnTrade.getTradeSource());
			rtnTradeRespVO.setInvestUnitID(pRtnTrade.getInvestUnitID());
		}
		ctpMsgHandlerTask.loading(
				CTPOnFuncMsgHandleEnum.RTN_TRADE, rtnTradeRespVO, null, null, null);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);

		CTPOnFuncMsgHandleEnum.RTN_ORDER.postHandle(rtnTradeRespVO);
	}

	*//**
	 * 请求查询投资者持仓明细响应，当执行ReqQryInvestorPositionDetail后，该方法被调用。
	 *
	 * @param var1
	 * @param var2
	 * @param nRequestID
	 * @param bIsLast
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2023/12/29 11:04:00.
	 *//*
	@Override
	public void OnRspQryInvestorPositionDetail(
			CThostFtdcInvestorPositionDetailField var1, CThostFtdcRspInfoField var2,
			int nRequestID, boolean bIsLast) {
		MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
		QryInvestorPositionDetailRespVO qryInvestorPositionDetailRespVO = new QryInvestorPositionDetailRespVO();
		if (null != var1) {
			qryInvestorPositionDetailRespVO.setInstrumentID(var1.getInstrumentID());
			qryInvestorPositionDetailRespVO.setBrokerID(var1.getBrokerID());
			qryInvestorPositionDetailRespVO.setInvestorID(var1.getInvestorID());
			qryInvestorPositionDetailRespVO.setHedgeFlag(var1.getHedgeFlag());
			qryInvestorPositionDetailRespVO.setDirection(var1.getDirection());
			qryInvestorPositionDetailRespVO.setOpenDate(var1.getOpenDate());
			qryInvestorPositionDetailRespVO.setTradeID(var1.getTradeID());
			qryInvestorPositionDetailRespVO.setVolume(var1.getVolume());
			qryInvestorPositionDetailRespVO.setOpenPrice(var1.getOpenPrice());
			qryInvestorPositionDetailRespVO.setTradingDay(var1.getTradingDay());
			qryInvestorPositionDetailRespVO.setSettlementID(var1.getSettlementID());
			qryInvestorPositionDetailRespVO.setTradeType(var1.getTradeType());
			qryInvestorPositionDetailRespVO.setCombInstrumentID(var1.getCombInstrumentID());
			qryInvestorPositionDetailRespVO.setExchangeID(var1.getExchangeID());
			qryInvestorPositionDetailRespVO.setCloseProfitByDate(var1.getCloseProfitByDate());
			qryInvestorPositionDetailRespVO.setCloseProfitByTrade(var1.getCloseProfitByTrade());
			qryInvestorPositionDetailRespVO.setPositionProfitByDate(var1.getPositionProfitByDate());
			qryInvestorPositionDetailRespVO.setPositionProfitByTrade(var1.getPositionProfitByTrade());
			qryInvestorPositionDetailRespVO.setMargin(var1.getMargin());
			qryInvestorPositionDetailRespVO.setExchMargin(var1.getExchMargin());
			qryInvestorPositionDetailRespVO.setMarginRateByMoney(var1.getMarginRateByMoney());
			qryInvestorPositionDetailRespVO.setMarginRateByVolume(var1.getMarginRateByVolume());
			qryInvestorPositionDetailRespVO.setLastSettlementPrice(var1.getLastSettlementPrice());
			qryInvestorPositionDetailRespVO.setSettlementPrice(var1.getSettlementPrice());
			qryInvestorPositionDetailRespVO.setCloseVolume(var1.getCloseVolume());
			qryInvestorPositionDetailRespVO.setCloseAmount(var1.getCloseAmount());
			qryInvestorPositionDetailRespVO.setTimeFirstVolume(var1.getTimeFirstVolume());
			qryInvestorPositionDetailRespVO.setInvestUnitID(var1.getInvestUnitID());
		}
		RespInfoVO respInfoVO = new RespInfoVO();
		if (null != var2) {
			respInfoVO.setErrorID(var2.getErrorID());
			respInfoVO.setErrorMsg(var2.getErrorMsg());
		}
		Integer requestId = nRequestID;
		Boolean isLast = bIsLast;
		ctpMsgHandlerTask.loading(
				CTPOnFuncMsgHandleEnum.QRY_INVESTOR_POSITION_DETAIL, qryInvestorPositionDetailRespVO, respInfoVO, requestId, isLast);
		CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
	}

	*//**
	 * 请求查询结算信息确认响应，当执行ReqQrySettlementInfoConfirm后，该方法被调用。
	 *
	 * @param var1
	 * @param var2
	 * @param nRequestID
	 * @param bIsLast
	 *
	 * @return void
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/30 10:27:13.
	 *//*
	@Override
	public void OnRspQrySettlementInfoConfirm(
			CThostFtdcSettlementInfoConfirmField var1, CThostFtdcRspInfoField var2,
			int nRequestID, boolean bIsLast) {
		try {
			log.info("OnRspQrySettlementInfoConfirm");
			MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
			QrySettlementInfoConfirmRespVO qrySettlementInfoRespVO = new QrySettlementInfoConfirmRespVO();
			if (null != var1) {
				qrySettlementInfoRespVO.setBrokerID(var1.getBrokerID());
				qrySettlementInfoRespVO.setInvestorID(var1.getInvestorID());
				qrySettlementInfoRespVO.setConfirmDate(var1.getConfirmDate());
				qrySettlementInfoRespVO.setConfirmTime(var1.getConfirmTime());
				qrySettlementInfoRespVO.setSettlementID(var1.getSettlementID());
				qrySettlementInfoRespVO.setAccountID(var1.getAccountID());
				qrySettlementInfoRespVO.setCurrencyID(var1.getCurrencyID());
			}
			log.info(GSON.toJson(qrySettlementInfoRespVO));
			RespInfoVO respInfoVO = new RespInfoVO();
			if (null != var2) {
				respInfoVO.setErrorID(var2.getErrorID());
				respInfoVO.setErrorMsg(var2.getErrorMsg());
			}
			log.info(GSON.toJson(respInfoVO));
			Integer requestId = nRequestID;
			log.info(GSON.toJson(requestId));
			Boolean isLast = bIsLast;
			log.info(GSON.toJson(isLast));
			ctpMsgHandlerTask.loading(
					CTPOnFuncMsgHandleEnum.QRY_SETTLEMENT_INFO_CONFIRM, qrySettlementInfoRespVO, respInfoVO, requestId, isLast);
			CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public void OnRspQryTrade(CThostFtdcTradeField pRtnTrade, CThostFtdcRspInfoField var2, int nRequestID, boolean bIsLast) {
		try {
			log.info("OnRspQryTrade");
			log.info(GSON.toJson(pRtnTrade));
			log.info(GSON.toJson(var2));
			log.info(GSON.toJson(nRequestID));
			log.info(GSON.toJson(bIsLast));
			MsgHandlerTask ctpMsgHandlerTask = new MsgHandlerTask();
			RtnTradeRespVO rtnTradeRespVO = new RtnTradeRespVO();
			if (null != pRtnTrade) {
				rtnTradeRespVO.setBrokerID(pRtnTrade.getBrokerID());
				rtnTradeRespVO.setInvestorID(pRtnTrade.getInvestorID());
				rtnTradeRespVO.setInstrumentID(pRtnTrade.getInstrumentID());
				rtnTradeRespVO.setOrderRef(pRtnTrade.getOrderRef());
				rtnTradeRespVO.setUserID(pRtnTrade.getUserID());
				rtnTradeRespVO.setExchangeID(pRtnTrade.getExchangeID());
				rtnTradeRespVO.setTradeID(pRtnTrade.getTradeID());
				rtnTradeRespVO.setDirection(pRtnTrade.getDirection());
				rtnTradeRespVO.setOrderSysID(pRtnTrade.getOrderSysID());
				rtnTradeRespVO.setParticipantID(pRtnTrade.getParticipantID());
				rtnTradeRespVO.setClientID(pRtnTrade.getClientID());
				rtnTradeRespVO.setTradingRole(pRtnTrade.getTradingRole());
				rtnTradeRespVO.setExchangeInstID(pRtnTrade.getExchangeInstID());
				rtnTradeRespVO.setOffsetFlag(pRtnTrade.getOffsetFlag());
				rtnTradeRespVO.setHedgeFlag(pRtnTrade.getHedgeFlag());
				rtnTradeRespVO.setPrice(pRtnTrade.getPrice());
				rtnTradeRespVO.setVolume(pRtnTrade.getVolume());
				rtnTradeRespVO.setTradeDate(pRtnTrade.getTradeDate());
				rtnTradeRespVO.setTradeTime(pRtnTrade.getTradeTime());
				rtnTradeRespVO.setTradeType(pRtnTrade.getTradeType());
				rtnTradeRespVO.setPriceSource(pRtnTrade.getPriceSource());
				rtnTradeRespVO.setTraderID(pRtnTrade.getTraderID());
				rtnTradeRespVO.setOrderLocalID(pRtnTrade.getOrderLocalID());
				rtnTradeRespVO.setClearingPartID(pRtnTrade.getClearingPartID());
				rtnTradeRespVO.setBusinessUnit(pRtnTrade.getBusinessUnit());
				rtnTradeRespVO.setSequenceNo(pRtnTrade.getSequenceNo());
				rtnTradeRespVO.setTradingDay(pRtnTrade.getTradingDay());
				rtnTradeRespVO.setSettlementID(pRtnTrade.getSettlementID());
				rtnTradeRespVO.setBrokerOrderSeq(pRtnTrade.getBrokerOrderSeq());
				rtnTradeRespVO.setTradeSource(pRtnTrade.getTradeSource());
				rtnTradeRespVO.setInvestUnitID(pRtnTrade.getInvestUnitID());
			}
			RespInfoVO respInfoVO = new RespInfoVO();
			if (null != var2) {
				respInfoVO.setErrorID(var2.getErrorID());
				respInfoVO.setErrorMsg(var2.getErrorMsg());
			}
			ctpMsgHandlerTask.loading(
					CTPOnFuncMsgHandleEnum.QRY_TRADE, rtnTradeRespVO, respInfoVO, nRequestID, bIsLast);
			CTPMsgHandlerThreadPool.execute(ctpMsgHandlerTask);
		} catch (Exception e) {
			log.error("查询成交记录", e);
		}
	}

	*//**
	 * 报单录入请求，录入错误时对应响应OnRspOrderInsert、OnErrRtnOrderInsert，正确时对应回报OnRtnOrder、OnRtnTrade。
	 * 可以录入限价单、市价单、条件单等交易所支持的指令，撤单时使用ReqOrderAction。
	 * 不支持预埋单录入，预埋单请使用ReqParkedOrderInsert。
	 *
	 * @param cThostFtdcInputOrderField
	 *
	 * @return int 0，代表成功；-1，表示网络连接失败；-2，表示未处理请求超过许可数；-3，表示每秒发送请求数超过许可数。
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/04 10:58:01.
	 *//*
	public int ReqInsertOrder(CThostFtdcInputOrderField cThostFtdcInputOrderField) {
		return traderApi.ReqOrderInsert(cThostFtdcInputOrderField, getRequestId());
	}

	*//**
	 * 报单操作请求
	 * 错误响应: OnRspOrderAction，OnErrRtnOrderAction
	 * 正确响应：OnRtnOrder
	 *
	 * @param cThostFtdcInputOrderActionField
	 *
	 * @return int 0，代表成功；-1，表示网络连接失败；-2，表示未处理请求超过许可数；-3，表示每秒发送请求数超过许可数。
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/04 11:07:38.
	 *//*
	public int ReqOrderAction(CThostFtdcInputOrderActionField cThostFtdcInputOrderActionField) {
		return traderApi.ReqOrderAction(cThostFtdcInputOrderActionField, getRequestId());
	}

	*//**
	 * 投资者结算结果确认，在开始每日交易前都需要先确认上一日结算单，只需要确认一次。对应响应OnRspSettlementInfoConfirm。
	 *
	 * @param cThostFtdcSettlementInfoConfirmField
	 *
	 * @return int 0，代表成功；-1，表示网络连接失败；-2，表示未处理请求超过许可数；-3，表示每秒发送请求数超过许可数。
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/04 11:10:11.
	 *//*
	public int ReqSettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField cThostFtdcSettlementInfoConfirmField) {
		return traderApi.ReqSettlementInfoConfirm(cThostFtdcSettlementInfoConfirmField, getRequestId());
	}

	*//**
	 * 请求查询资金账户
	 *
	 * @param cThostFtdcQryTradingAccountField
	 *
	 * @return int 0，代表成功；-1，表示网络连接失败；-2，表示未处理请求超过许可数；-3，表示每秒发送请求数超过许可数。
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/04 11:13:36.
	 *//*
	public int ReqQryTradingAccount(CThostFtdcQryTradingAccountField cThostFtdcQryTradingAccountField) {
		return traderApi.ReqQryTradingAccount(cThostFtdcQryTradingAccountField, getRequestId());
	}

	public int ReqQrySettlementInfoConfirm(CThostFtdcQrySettlementInfoConfirmField cThostFtdcQrySettlementInfoConfirmField){
		log.info("ReqQrySettlementInfoConfirm");
		return traderApi.ReqQrySettlementInfoConfirm(cThostFtdcQrySettlementInfoConfirmField, getRequestId());
	}

	public int ReqQrySettlementInfo(CThostFtdcQrySettlementInfoField cThostFtdcQrySettlementInfoField) {
		return traderApi.ReqQrySettlementInfo(cThostFtdcQrySettlementInfoField, getRequestId());
	}

	*//**
	 * 请求查询合约手续费率，对应响应OnRspQryInstrumentCommissionRate。如果InstrumentID填空，则返回持仓对应的合约手续费率。
	 *
	 * @param cThostFtdcQryInstrumentCommissionRateField
	 *
	 * @return int 0，代表成功；-1，表示网络连接失败；-2，表示未处理请求超过许可数；-3，表示每秒发送请求数超过许可数。
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/01/04 11:15:15.
	 *//*
	public int ReqQryInstrumentCommissionRate(CThostFtdcQryInstrumentCommissionRateField cThostFtdcQryInstrumentCommissionRateField) {
		return traderApi.ReqQryInstrumentCommissionRate(cThostFtdcQryInstrumentCommissionRateField, getRequestId());
	}

	public int ReqQryInvestorPositionDetail(CThostFtdcQryInvestorPositionDetailField cThostFtdcQryInvestorPositionDetailField) {
		return traderApi.ReqQryInvestorPositionDetail(cThostFtdcQryInvestorPositionDetailField, getRequestId());
	}

	public int ReqQryTrade(CThostFtdcQryTradeField cThostFtdcQryTradeField) {
		return traderApi.ReqQryTrade(cThostFtdcQryTradeField, getRequestId());
	}

	private int getRequestId() {
		synchronized (requestId) {
			return requestId++;
		}
	}*/

}