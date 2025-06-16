package com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader;


import com.xinqi.puppet.common.UserInfoVO;
import com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader.ctp.CTPTradeSpiImpl;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderActionReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInstrumentCommissionRateReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInvestorPositionDetailReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoConfirmReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradeReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradingAccountReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.SettlementInfoConfirmReqVO;
import ctp.thosttraderapi.CThostFtdcInputOrderActionField;
import ctp.thosttraderapi.CThostFtdcInputOrderField;
import ctp.thosttraderapi.CThostFtdcQryInstrumentCommissionRateField;
import ctp.thosttraderapi.CThostFtdcQryInvestorPositionDetailField;
import ctp.thosttraderapi.CThostFtdcQrySettlementInfoConfirmField;
import ctp.thosttraderapi.CThostFtdcQrySettlementInfoField;
import ctp.thosttraderapi.CThostFtdcQryTradeField;
import ctp.thosttraderapi.CThostFtdcQryTradingAccountField;
import ctp.thosttraderapi.CThostFtdcSettlementInfoConfirmField;
import ctp.thosttraderapi.CThostFtdcTraderApi;
import ctp.thosttraderapi.THOST_TE_RESUME_TYPE;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2025/2/13 09:39
 **/
public class CTPTradeProxy extends TradeProxyBase {

	static {
		System.loadLibrary("thosttraderapi_se");
		System.loadLibrary("thosttraderapi_wrap");
	}

	private CTPTradeSpiImpl traderSpi;

	public CTPTradeProxy(UserInfoVO userInfoVO) {
		super(userInfoVO);
	}

	@Override
	public void init() {
		CThostFtdcTraderApi traderApi = CThostFtdcTraderApi.CreateFtdcTraderApi();
		traderSpi = new CTPTradeSpiImpl(traderApi, userInfo);
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
		CThostFtdcSettlementInfoConfirmField cThostFtdcSettlementInfoConfirmField = new CThostFtdcSettlementInfoConfirmField();
		cThostFtdcSettlementInfoConfirmField.setBrokerID(settlementInfoConfirmReqVO.getBrokerID());
		cThostFtdcSettlementInfoConfirmField.setInvestorID(settlementInfoConfirmReqVO.getInvestorID());
		cThostFtdcSettlementInfoConfirmField.setConfirmDate(settlementInfoConfirmReqVO.getConfirmDate());
		cThostFtdcSettlementInfoConfirmField.setConfirmTime(settlementInfoConfirmReqVO.getConfirmTime());
		cThostFtdcSettlementInfoConfirmField.setSettlementID(settlementInfoConfirmReqVO.getSettlementID());
		cThostFtdcSettlementInfoConfirmField.setAccountID(settlementInfoConfirmReqVO.getAccountID());
		cThostFtdcSettlementInfoConfirmField.setCurrencyID(settlementInfoConfirmReqVO.getCurrencyID());
		return traderSpi.ReqSettlementInfoConfirm(cThostFtdcSettlementInfoConfirmField);
	}

	@Override
	public int qryTradingAccount(QryTradingAccountReqVO qryTradingAccountReqVO) {
		CThostFtdcQryTradingAccountField cThostFtdcQryTradingAccountField = new CThostFtdcQryTradingAccountField();
		cThostFtdcQryTradingAccountField.setBrokerID(qryTradingAccountReqVO.getBrokerID());
		cThostFtdcQryTradingAccountField.setInvestorID(qryTradingAccountReqVO.getInvestorID());
		cThostFtdcQryTradingAccountField.setCurrencyID(qryTradingAccountReqVO.getCurrencyID());
		cThostFtdcQryTradingAccountField.setBizType(qryTradingAccountReqVO.getBizType());
		cThostFtdcQryTradingAccountField.setAccountID(qryTradingAccountReqVO.getAccountID());
		return traderSpi.ReqQryTradingAccount(cThostFtdcQryTradingAccountField);
	}

	@Override
	public int qrySettlementInfoConfirm(QrySettlementInfoConfirmReqVO qrySettlementInfoReqVO) {
		CThostFtdcQrySettlementInfoConfirmField cThostFtdcQrySettlementInfoConfirmField = new CThostFtdcQrySettlementInfoConfirmField();
		cThostFtdcQrySettlementInfoConfirmField.setBrokerID(qrySettlementInfoReqVO.getBrokerID());
		cThostFtdcQrySettlementInfoConfirmField.setInvestorID(qrySettlementInfoReqVO.getInvestorID());
		cThostFtdcQrySettlementInfoConfirmField.setAccountID(qrySettlementInfoReqVO.getAccountID());
		cThostFtdcQrySettlementInfoConfirmField.setCurrencyID(qrySettlementInfoReqVO.getCurrencyID());
		return traderSpi.ReqQrySettlementInfoConfirm(cThostFtdcQrySettlementInfoConfirmField);
	}

	@Override
	public int qrySettlementInfo(QrySettlementInfoReqVO qrySettlementInfoReqVO) {
		CThostFtdcQrySettlementInfoField cThostFtdcQrySettlementInfoField = new CThostFtdcQrySettlementInfoField();
		cThostFtdcQrySettlementInfoField.setBrokerID(qrySettlementInfoReqVO.getBrokerID());
		cThostFtdcQrySettlementInfoField.setInvestorID(qrySettlementInfoReqVO.getInvestorID());
		cThostFtdcQrySettlementInfoField.setTradingDay(qrySettlementInfoReqVO.getTradingDay());
		cThostFtdcQrySettlementInfoField.setAccountID(qrySettlementInfoReqVO.getAccountID());
		cThostFtdcQrySettlementInfoField.setCurrencyID(qrySettlementInfoReqVO.getCurrencyID());
		return traderSpi.ReqQrySettlementInfo(cThostFtdcQrySettlementInfoField);
	}

	@Override
	public int qryInstrumentCommissionRate(QryInstrumentCommissionRateReqVO qryInstrumentCommissionRateReqVO) {
		CThostFtdcQryInstrumentCommissionRateField cThostFtdcQryInstrumentCommissionRateField = new CThostFtdcQryInstrumentCommissionRateField();
		cThostFtdcQryInstrumentCommissionRateField.setBrokerID(qryInstrumentCommissionRateReqVO.getBrokerID());
		cThostFtdcQryInstrumentCommissionRateField.setInvestorID(qryInstrumentCommissionRateReqVO.getInvestorID());
		cThostFtdcQryInstrumentCommissionRateField.setInstrumentID(qryInstrumentCommissionRateReqVO.getInstrumentID());
		cThostFtdcQryInstrumentCommissionRateField.setExchangeID(qryInstrumentCommissionRateReqVO.getExchangeID());
		cThostFtdcQryInstrumentCommissionRateField.setInvestUnitID(qryInstrumentCommissionRateReqVO.getInvestUnitID());
		return traderSpi.ReqQryInstrumentCommissionRate(cThostFtdcQryInstrumentCommissionRateField);
	}

	@Override
	public int qryInvestorPositionDetail(QryInvestorPositionDetailReqVO qryInvestorPositionDetailReqVO) {
		CThostFtdcQryInvestorPositionDetailField cThostFtdcQryInvestorPositionDetailField = new CThostFtdcQryInvestorPositionDetailField();
		cThostFtdcQryInvestorPositionDetailField.setBrokerID(qryInvestorPositionDetailReqVO.getBrokerID());
		cThostFtdcQryInvestorPositionDetailField.setInvestorID(qryInvestorPositionDetailReqVO.getInvestorID());
		cThostFtdcQryInvestorPositionDetailField.setInstrumentID(qryInvestorPositionDetailReqVO.getInstrumentID());
		cThostFtdcQryInvestorPositionDetailField.setExchangeID(qryInvestorPositionDetailReqVO.getExchangeID());
		cThostFtdcQryInvestorPositionDetailField.setInvestUnitID(qryInvestorPositionDetailReqVO.getInvestUnitID());
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
		cThostFtdcQryTradeField.setInvestUnitID(qryTradeReqVO.getInvestUnitID());
		return traderSpi.ReqQryTrade(cThostFtdcQryTradeField);
	}

}
