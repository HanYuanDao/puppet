package com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader;


import com.xinqi.puppet.common.UserInfoVO;
import com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader.qdp.QdpTradeSpiImpl;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderActionReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInstrumentCommissionRateReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInvestorPositionDetailReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoConfirmReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradeReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradingAccountReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.SettlementInfoConfirmReqVO;
import lombok.extern.slf4j.Slf4j;
import qdp.qdptraderapi.CQdpFtdcInputOrderField;
import qdp.qdptraderapi.CQdpFtdcOrderActionField;
import qdp.qdptraderapi.CQdpFtdcQryInvestorAccountField;
import qdp.qdptraderapi.CQdpFtdcQryInvestorFeeField;
import qdp.qdptraderapi.CQdpFtdcQryInvestorPositionField;
import qdp.qdptraderapi.CQdpFtdcQryTradeField;
import qdp.qdptraderapi.CQdpFtdcTraderApi;
import qdp.qdptraderapi.QDP_TE_RESUME_TYPE;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2025/2/13 09:35
 **/
@Slf4j
public class QDPTradeProxy extends TradeProxyBase {

	static {
		System.loadLibrary("qdptraderapi");
		System.loadLibrary("qdptraderapi_wrap");
	}

	private QdpTradeSpiImpl traderSpi;

	public QDPTradeProxy(UserInfoVO userInfoVO) {
		super(userInfoVO);
	}

	@Override
	public void init() {
		CQdpFtdcTraderApi traderApi = CQdpFtdcTraderApi.CreateFtdcTraderApi();
		traderSpi = new QdpTradeSpiImpl(traderApi, userInfo);
		traderApi.RegisterSpi(traderSpi);
		traderApi.RegisterFront(userInfo.getTradeAddress());
		traderApi.SubscribePublicTopic(QDP_TE_RESUME_TYPE.QDP_TERT_RESTART);
		traderApi.SubscribePrivateTopic(QDP_TE_RESUME_TYPE.QDP_TERT_RESTART);
		traderApi.Init();
		traderApi.Join();
	}

	@Override
	public int reqInsertOrder(InputOrderReqVO inputOrderReqVO) {
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

	@Override
	public int actionOrder(InputOrderActionReqVO inputOrderActionReqVO) {
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

	@Override
	public int confirmSettlementInfo(SettlementInfoConfirmReqVO settlementInfoConfirmReqVO) {
		log.warn("QDP没有确认结算单的接口");
		return 0;
	}

	@Override
	public int qryTradingAccount(QryTradingAccountReqVO qryTradingAccountReqVO) {
		CQdpFtdcQryInvestorAccountField cThostFtdcQryTradingAccountField = new CQdpFtdcQryInvestorAccountField();
		cThostFtdcQryTradingAccountField.setBrokerID(qryTradingAccountReqVO.getBrokerID());
		cThostFtdcQryTradingAccountField.setInvestorID(qryTradingAccountReqVO.getInvestorID());
		//cThostFtdcQryTradingAccountField.setCurrencyID(qryTradingAccountReqVO.getCurrencyID());
		//cThostFtdcQryTradingAccountField.setBizType(qryTradingAccountReqVO.getBizType());
		//cThostFtdcQryTradingAccountField.setAccountID(qryTradingAccountReqVO.getAccountID());
		return traderSpi.ReqQryTradingAccount(cThostFtdcQryTradingAccountField);
	}

	@Override
	public int qrySettlementInfoConfirm(QrySettlementInfoConfirmReqVO qrySettlementInfoReqVO) {
		log.warn("QDP没有查询结算单确认的接口");
		return 0;
	}

	@Override
	public int qrySettlementInfo(QrySettlementInfoReqVO qrySettlementInfoReqVO) {
		log.warn("QDP没有查询结算单的接口");
		return 0;
	}

	@Override
	public int qryInstrumentCommissionRate(QryInstrumentCommissionRateReqVO qryInstrumentCommissionRateReqVO) {
		CQdpFtdcQryInvestorFeeField cThostFtdcQryInstrumentCommissionRateField = new CQdpFtdcQryInvestorFeeField();
		cThostFtdcQryInstrumentCommissionRateField.setBrokerID(qryInstrumentCommissionRateReqVO.getBrokerID());
		cThostFtdcQryInstrumentCommissionRateField.setInvestorID(qryInstrumentCommissionRateReqVO.getInvestorID());
		cThostFtdcQryInstrumentCommissionRateField.setInstrumentID(qryInstrumentCommissionRateReqVO.getInstrumentID());
		cThostFtdcQryInstrumentCommissionRateField.setExchangeID(qryInstrumentCommissionRateReqVO.getExchangeID());
		//cThostFtdcQryInstrumentCommissionRateField.setInvestUnitID(qryInstrumentCommissionRateReqVO.getInvestUnitID());
		return traderSpi.ReqQryInstrumentCommissionRate(cThostFtdcQryInstrumentCommissionRateField);
	}

	@Override
	public int qryInvestorPositionDetail(QryInvestorPositionDetailReqVO qryInvestorPositionDetailReqVO) {
		CQdpFtdcQryInvestorPositionField cThostFtdcQryInvestorPositionDetailField = new CQdpFtdcQryInvestorPositionField();
		cThostFtdcQryInvestorPositionDetailField.setBrokerID(qryInvestorPositionDetailReqVO.getBrokerID());
		cThostFtdcQryInvestorPositionDetailField.setInvestorID(qryInvestorPositionDetailReqVO.getInvestorID());
		cThostFtdcQryInvestorPositionDetailField.setInstrumentID(qryInvestorPositionDetailReqVO.getInstrumentID());
		cThostFtdcQryInvestorPositionDetailField.setExchangeID(qryInvestorPositionDetailReqVO.getExchangeID());
		//cThostFtdcQryInvestorPositionDetailField.setInvestUnitID(qryInvestorPositionDetailReqVO.getInvestUnitID());
		return traderSpi.ReqQryInvestorPositionDetail(cThostFtdcQryInvestorPositionDetailField);
	}

	@Override
	public int qryTrade(QryTradeReqVO qryTradeReqVO) {
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

}
