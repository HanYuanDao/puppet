package com.xinqi.ctp_puppet.netty.gateway.tcp.vo;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Setter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/12 15:33
 **/
@Setter
public class QryTradingAccountRespVO extends CTPRespVOBase {

	/**
	 * 经纪公司代码
	 */
	private String brokerID;
	/**
	 * 投资者帐号
	 */
	private String accountID;
	/**
	 * 上次质押金额
	 */
	private Double preMortgage;
	/**
	 * 上次信用额度
	 */
	private Double preCredit;
	/**
	 * 上次存款额
	 */
	private Double preDeposit;
	/**
	 * 上次结算准备金
	 */
	private Double preBalance;
	/**
	 * 上次占用的保证金
	 */
	private Double preMargin;
	/**
	 * 利息基数
	 */
	private Double interestBase;
	/**
	 * 利息收入
	 */
	private Double interest;
	/**
	 * 入金金额
	 */
	private Double deposit;
	/**
	 * 出金金额
	 */
	private Double withdraw;
	/**
	 * 冻结的保证金
	 */
	private Double frozenMargin;
	/**
	 * 冻结的资金
	 */
	private Double frozenCash;
	/**
	 * 冻结的手续费
	 */
	private Double frozenCommission;
	/**
	 * 当前保证金总额
	 */
	private Double currMargin;
	/**
	 * 资金差额
	 */
	private Double cashIn;
	/**
	 * 手续费
	 */
	private Double commission;
	/**
	 * 平仓盈亏
	 */
	private Double closeProfit;
	/**
	 * 持仓盈亏
	 */
	private Double positionProfit;
	/**
	 * 期货结算准备金
	 */
	private Double balance;
	/**
	 * 可用资金
	 */
	private Double available;
	/**
	 * 可取资金
	 */
	private Double withdrawQuota;
	/**
	 * 基本准备金
	 */
	private Double reserve;
	/**
	 * 交易日
	 */
	private String tradingDay;
	/**
	 * 结算编号
	 */
	private Integer settlementID;
	/**
	 * 信用额度
	 */
	private Double credit;
	/**
	 * 质押金额
	 */
	private Double mortgage;
	/**
	 * 交易所保证金
	 */
	private Double exchangeMargin;
	/**
	 * 投资者交割保证金
	 */
	private Double deliveryMargin;
	/**
	 * 交易所交割保证金
	 */
	private Double exchangeDeliveryMargin;
	/**
	 * 保底期货结算准备金
	 */
	private Double reserveBalance;
	/**
	 * 币种代码
	 */
	private String currencyID;
	/**
	 * 上次货币质入金额
	 */
	private Double preFundMortgageIn;
	/**
	 * 上次货币质出金额
	 */
	private Double preFundMortgageOut;
	/**
	 * 货币质入金额
	 */
	private Double fundMortgageIn;
	/**
	 * 货币质出金额
	 */
	private Double fundMortgageOut;
	/**
	 * 货币质押余额
	 */
	private Double fundMortgageAvailable;
	/**
	 * 可质押货币金额
	 */
	private Double mortgageableFund;
	/**
	 * 特殊产品占用保证金
	 */
	private Double specProductMargin;
	/**
	 * 特殊产品冻结保证金
	 */
	private Double specProductFrozenMargin;
	/**
	 * 特殊产品手续费
	 */
	private Double specProductCommission;
	/**
	 * 特殊产品冻结手续费
	 */
	private Double specProductFrozenCommission;
	/**
	 * 特殊产品持仓盈亏
	 */
	private Double specProductPositionProfit;
	/**
	 * 特殊产品平仓盈亏
	 */
	private Double specProductCloseProfit;
	/**
	 * 根据持仓盈亏算法计算的特殊产品持仓盈亏
	 */
	private Double specProductPositionProfitByAlg;
	/**
	 * 特殊产品交易所保证金
	 */
	private Double specProductExchangeMargin;
	/**
	 * 业务类型
	 */
	private Character bizType;
	/**
	 * 延时换汇冻结金额
	 */
	private Double frozenSwap;
	/**
	 * 剩余换汇额度
	 */
	private Double remainSwap;

	@Override
	public ByteBuf toByteBuf() {
		ByteBuf buf = Unpooled.buffer(385);
		buf.writeBytes(DecodeUtils.getCByteArr(brokerID, 11));
		buf.writeBytes(DecodeUtils.getCByteArr(accountID, 11));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(preMortgage));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(preCredit));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(preDeposit));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(preBalance));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(preMargin));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(interestBase));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(interest));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(deposit));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(withdraw));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(frozenMargin));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(frozenCash));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(frozenCommission));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(currMargin));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(cashIn));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(commission));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(closeProfit));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(positionProfit));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(balance));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(available));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(withdrawQuota));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(reserve));
		buf.writeBytes(DecodeUtils.getCByteArr(tradingDay, 9));
		buf.writeBytes(DecodeUtils.intToLeByteArr(settlementID));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(credit));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(mortgage));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(exchangeMargin));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(deliveryMargin));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(exchangeDeliveryMargin));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(reserveBalance));
		buf.writeBytes(DecodeUtils.getCByteArr(currencyID, 4));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(preFundMortgageIn));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(preFundMortgageOut));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(fundMortgageIn));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(fundMortgageOut));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(fundMortgageAvailable));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(mortgageableFund));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(specProductMargin));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(specProductFrozenMargin));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(specProductCommission));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(specProductFrozenCommission));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(specProductPositionProfit));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(specProductCloseProfit));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(specProductPositionProfitByAlg));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(specProductExchangeMargin));
		buf.writeBytes(DecodeUtils.charToLeByteArr(bizType));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(frozenSwap));
		buf.writeBytes(DecodeUtils.doubleToLeByteArr(remainSwap));
		return buf;
	}

}
