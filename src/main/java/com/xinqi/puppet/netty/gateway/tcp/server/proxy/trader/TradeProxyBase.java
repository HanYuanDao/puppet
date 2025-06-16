package com.xinqi.puppet.netty.gateway.tcp.server.proxy.trader;


import com.xinqi.puppet.common.UserInfoVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderActionReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.InputOrderReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInstrumentCommissionRateReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryInvestorPositionDetailReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoConfirmReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QrySettlementInfoReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradeReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.QryTradingAccountReqVO;
import com.xinqi.puppet.netty.gateway.tcp.vo.SettlementInfoConfirmReqVO;


/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2025/2/13 09:37
 **/
public abstract class TradeProxyBase {

	protected UserInfoVO userInfo;
	public TradeProxyBase(UserInfoVO userInfoVO) {this.userInfo = userInfoVO;}

	/**
	 * 进行交易接口的初始化
	 *
	 * @return null
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2025/06/13 10:53:56.
	 */
	abstract public void init();

	abstract public int reqInsertOrder(InputOrderReqVO inputOrderReqVO);

	abstract public int actionOrder(InputOrderActionReqVO inputOrderActionReqVO);

	abstract public int confirmSettlementInfo(SettlementInfoConfirmReqVO settlementInfoConfirmReqVO);

	abstract public int qryTradingAccount(QryTradingAccountReqVO qryTradingAccountReqVO);

	abstract public int qrySettlementInfoConfirm(QrySettlementInfoConfirmReqVO qrySettlementInfoReqVO);

	abstract public int qrySettlementInfo(QrySettlementInfoReqVO qrySettlementInfoReqVO);

	abstract public int qryInstrumentCommissionRate(QryInstrumentCommissionRateReqVO qryInstrumentCommissionRateReqVO);

	abstract public int qryInvestorPositionDetail(QryInvestorPositionDetailReqVO qryInvestorPositionDetailReqVO);

	abstract public int qryTrade(QryTradeReqVO qryTradeReqVO);
}
