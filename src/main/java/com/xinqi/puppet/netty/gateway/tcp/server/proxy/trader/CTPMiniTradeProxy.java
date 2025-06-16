package com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader;


import com.xinqi.puppet.common.UserInfoVO;
import com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader.ctp.CTPTradeSpiImpl;
import com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader.ctpmini.CTPMINITradeSpiImpl;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderActionReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInstrumentCommissionRateReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInvestorPositionDetailReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoConfirmReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradeReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradingAccountReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.SettlementInfoConfirmReqVO;
import ctpmini.thosttraderapi.CThostFtdcInputOrderActionField;
import ctpmini.thosttraderapi.CThostFtdcInputOrderField;
import ctpmini.thosttraderapi.CThostFtdcQryInstrumentCommissionRateField;
import ctpmini.thosttraderapi.CThostFtdcQryInvestorPositionDetailField;
import ctpmini.thosttraderapi.CThostFtdcQryTradeField;
import ctpmini.thosttraderapi.CThostFtdcQryTradingAccountField;
import ctpmini.thosttraderapi.CThostFtdcTraderApi;
import ctpmini.thosttraderapi.THOST_TE_RESUME_TYPE;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2025/6/13 09:46
 **/
public class CTPMiniTradeProxy extends TradeProxyBase {

	static {
		System.loadLibrary("ctpminithosttraderapi_se");
		System.loadLibrary("ctpminithosttraderapi_wrap");
	}

	private CTPMINITradeSpiImpl traderSpi;

	public CTPMiniTradeProxy(UserInfoVO userInfoVO) {
		super(userInfoVO);
	}

	@Override
	public void init() {
		CThostFtdcTraderApi traderApi = CThostFtdcTraderApi.CreateFtdcTraderApi();
		traderSpi = new CTPMINITradeSpiImpl(traderApi, userInfo);
		traderApi.RegisterSpi(traderSpi);
		traderApi.RegisterFront(userInfo.getTradeAddress());
		traderApi.SubscribePublicTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
		traderApi.SubscribePrivateTopic(THOST_TE_RESUME_TYPE.THOST_TERT_RESTART);
		traderApi.Init();
		traderApi.Join();
	}

	@Override
	public int reqInsertOrder(InputOrderReqVO inputOrderReqVO) {
		CThostFtdcInputOrderField cThostFtdcInputOrderField = new CThostFtdcInputOrderField();
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
		return traderSpi.ReqInsertOrder(cThostFtdcInputOrderField);
	}

	@Override
	public int actionOrder(InputOrderActionReqVO inputOrderActionReqVO) {
		CThostFtdcInputOrderActionField cThostFtdcInputOrderActionField = new CThostFtdcInputOrderActionField();
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
		return traderSpi.ReqOrderAction(cThostFtdcInputOrderActionField);
	}

	@Override
	public int confirmSettlementInfo(SettlementInfoConfirmReqVO settlementInfoConfirmReqVO) {
		return 0;
	}

	@Override
	public int qryTradingAccount(QryTradingAccountReqVO qryTradingAccountReqVO) {
		CThostFtdcQryTradingAccountField cThostFtdcQryTradingAccountField = new CThostFtdcQryTradingAccountField();
		cThostFtdcQryTradingAccountField.setBrokerID(qryTradingAccountReqVO.getBrokerID());
		cThostFtdcQryTradingAccountField.setInvestorID(qryTradingAccountReqVO.getInvestorID());
		cThostFtdcQryTradingAccountField.setCurrencyID(qryTradingAccountReqVO.getCurrencyID());
		//cThostFtdcQryTradingAccountField.setBizType(qryTradingAccountReqVO.getBizType());
		//cThostFtdcQryTradingAccountField.setAccountID(qryTradingAccountReqVO.getAccountID());
		return traderSpi.ReqQryTradingAccount(cThostFtdcQryTradingAccountField);
	}

	@Override
	public int qrySettlementInfoConfirm(QrySettlementInfoConfirmReqVO qrySettlementInfoReqVO) {
		return 0;
	}

	@Override
	public int qrySettlementInfo(QrySettlementInfoReqVO qrySettlementInfoReqVO) {
		return 0;
	}

	@Override
	public int qryInstrumentCommissionRate(QryInstrumentCommissionRateReqVO qryInstrumentCommissionRateReqVO) {
		CThostFtdcQryInstrumentCommissionRateField cThostFtdcQryInstrumentCommissionRateField = new CThostFtdcQryInstrumentCommissionRateField();
		cThostFtdcQryInstrumentCommissionRateField.setBrokerID(qryInstrumentCommissionRateReqVO.getBrokerID());
		cThostFtdcQryInstrumentCommissionRateField.setInvestorID(qryInstrumentCommissionRateReqVO.getInvestorID());
		cThostFtdcQryInstrumentCommissionRateField.setInstrumentID(qryInstrumentCommissionRateReqVO.getInstrumentID());
		//cThostFtdcQryInstrumentCommissionRateField.setExchangeID(qryInstrumentCommissionRateReqVO.getExchangeID());
		//cThostFtdcQryInstrumentCommissionRateField.setInvestUnitID(qryInstrumentCommissionRateReqVO.getInvestUnitID());
		return traderSpi.ReqQryInstrumentCommissionRate(cThostFtdcQryInstrumentCommissionRateField);
	}

	@Override
	public int qryInvestorPositionDetail(QryInvestorPositionDetailReqVO qryInvestorPositionDetailReqVO) {
		CThostFtdcQryInvestorPositionDetailField cThostFtdcQryInvestorPositionDetailField = new CThostFtdcQryInvestorPositionDetailField();
		cThostFtdcQryInvestorPositionDetailField.setBrokerID(qryInvestorPositionDetailReqVO.getBrokerID());
		cThostFtdcQryInvestorPositionDetailField.setInvestorID(qryInvestorPositionDetailReqVO.getInvestorID());
		cThostFtdcQryInvestorPositionDetailField.setInstrumentID(qryInvestorPositionDetailReqVO.getInstrumentID());
		//cThostFtdcQryInvestorPositionDetailField.setExchangeID(qryInvestorPositionDetailReqVO.getExchangeID());
		//cThostFtdcQryInvestorPositionDetailField.setInvestUnitID(qryInvestorPositionDetailReqVO.getInvestUnitID());
		return traderSpi.ReqQryInvestorPositionDetail(cThostFtdcQryInvestorPositionDetailField);
	}

	@Override
	public int qryTrade(QryTradeReqVO qryTradeReqVO) {
		CThostFtdcQryTradeField cThostFtdcQryTradeField = new CThostFtdcQryTradeField();
		cThostFtdcQryTradeField.setBrokerID(qryTradeReqVO.getBrokerID());
		cThostFtdcQryTradeField.setInvestorID(qryTradeReqVO.getInvestorID());
		cThostFtdcQryTradeField.setInstrumentID(qryTradeReqVO.getInstrumentID());
		cThostFtdcQryTradeField.setExchangeID(qryTradeReqVO.getExchangeID());
		cThostFtdcQryTradeField.setTradeID(qryTradeReqVO.getTradeID());
		cThostFtdcQryTradeField.setTradeTimeStart(qryTradeReqVO.getTradeTimeStart());
		cThostFtdcQryTradeField.setTradeTimeEnd(qryTradeReqVO.getTradeTimeEnd());
		//cThostFtdcQryTradeField.setInvestUnitID(qryTradeReqVO.getInvestUnitID());
		return traderSpi.ReqQryTrade(cThostFtdcQryTradeField);
	}

}
