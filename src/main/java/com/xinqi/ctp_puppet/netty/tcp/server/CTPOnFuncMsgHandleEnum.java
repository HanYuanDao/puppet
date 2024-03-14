package com.xinqi.ctp_puppet.netty.tcp.server;


import com.google.gson.Gson;
import com.xinqi.ctp_puppet.common.DecodeUtils;
import com.xinqi.ctp_puppet.netty.tcp.vo.CTPRespVOBase;
import com.xinqi.ctp_puppet.netty.tcp.vo.ErrorRespVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.InputOrderActionRespVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.InputOrderRespVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QryInstrumentCommissionRateRespVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QryInvestorPositionDetailRespVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QrySettlementInfoConfirmRespVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QrySettlementInfoRespVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QryTradingAccountReqVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.QryTradingAccountRespVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.RespInfoVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.RtnOrderRespVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.RtnTradeRespVO;
import com.xinqi.ctp_puppet.netty.tcp.vo.SettlementInfoConfirmRespVO;
import ctp.thosttraderapi.CThostFtdcOrderField;
import ctp.thosttraderapi.CThostFtdcRspInfoField;
import ctp.thosttraderapi.CThostFtdcTradeField;
import ctp.thosttraderapi.CThostFtdcTradingAccountField;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/14 03:53
 **/
@Slf4j
@Getter
public enum CTPOnFuncMsgHandleEnum {

	SHOOK_HAND(1) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf buf = Unpooled.buffer(25);
			buf.writeBytes(DecodeUtils.getCByteArr(FLAG_SHOOK_HAND, 25));
			return buf;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	HEAD_ERR(2) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf buf = Unpooled.buffer(28);
			buf.writeBytes(DecodeUtils.getCByteArr(FLAG_HEAD_ERR, 28));
			return buf;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	ERR(9) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf output = Unpooled.buffer(44);
			ErrorRespVO errorRespVO = (ErrorRespVO) voBase;
			output.writeBytes(errorRespVO.toByteBuf());
			output.writeBytes(DecodeUtils.intToLeByteArr(nRequestID));
			output.writeBytes(DecodeUtils.booleanToByteArr(bIsLast));
			return output;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	ERR_RTN_ORDER_INSERT(33) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf output = Unpooled.buffer(44);
			InputOrderRespVO inputOrderRespVO = (InputOrderRespVO) voBase;
			output.writeBytes(inputOrderRespVO.toByteBuf());
			output.writeBytes(respInfoVO.toByteBuf());
			return output;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	ORDER_INSERT(34) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf output = Unpooled.buffer(44);
			InputOrderRespVO inputOrderRespVO = (InputOrderRespVO) voBase;
			output.writeBytes(inputOrderRespVO.toByteBuf());
			output.writeBytes(respInfoVO.toByteBuf());
			output.writeBytes(DecodeUtils.intToLeByteArr(nRequestID));
			output.writeBytes(DecodeUtils.booleanToByteArr(bIsLast));
			return output;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	ERR_RTN_ORDER_ACTION(35) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf output = Unpooled.buffer(44);
			InputOrderActionRespVO inputOrderActionRespVO = (InputOrderActionRespVO) voBase;
			output.writeBytes(inputOrderActionRespVO.toByteBuf());
			output.writeBytes(respInfoVO.toByteBuf());
			return output;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	ORDER_ACTION(36) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf output = Unpooled.buffer(44);
			InputOrderActionRespVO inputOrderActionRespVO = (InputOrderActionRespVO) voBase;
			output.writeBytes(inputOrderActionRespVO.toByteBuf());
			output.writeBytes(respInfoVO.toByteBuf());
			output.writeBytes(DecodeUtils.intToLeByteArr(nRequestID));
			output.writeBytes(DecodeUtils.booleanToByteArr(bIsLast));
			return output;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	RTN_ORDER(38) {
		@Override
		public ByteBuf handle(CTPRespVOBase obj, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			RtnOrderRespVO rtnOrderRespVO = (RtnOrderRespVO) obj;
			Gson gson = new Gson();
			return rtnOrderRespVO.toByteBuf();
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {
			TradeTcpServerFactory.addRtnHistory(voBase);
		}
	},
	RTN_TRADE(39) {
		@Override
		public ByteBuf handle(CTPRespVOBase obj, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			RtnTradeRespVO rtnTradeRespVO = (RtnTradeRespVO) obj;
			Gson gson = new Gson();
			return rtnTradeRespVO.toByteBuf();
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {
			TradeTcpServerFactory.addRtnHistory(voBase);
		}
	},
	QRY_SETTLEMENT_INFO_CONFIRM(41) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf output = Unpooled.buffer(44);
			QrySettlementInfoConfirmRespVO qrySettlementInfoConfirmRespVO = (QrySettlementInfoConfirmRespVO) voBase;
			output.writeBytes(qrySettlementInfoConfirmRespVO.toByteBuf());
			output.writeBytes(respInfoVO.toByteBuf());
			output.writeBytes(DecodeUtils.intToLeByteArr(nRequestID));
			output.writeBytes(DecodeUtils.booleanToByteArr(bIsLast));
			return output;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	SETTLEMENT_INFO_CONFIRM(42) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf output = Unpooled.buffer(44);
			SettlementInfoConfirmRespVO settlementInfoConfirmRespVO = (SettlementInfoConfirmRespVO) voBase;
			output.writeBytes(settlementInfoConfirmRespVO.toByteBuf());
			output.writeBytes(respInfoVO.toByteBuf());
			output.writeBytes(DecodeUtils.intToLeByteArr(nRequestID));
			output.writeBytes(DecodeUtils.booleanToByteArr(bIsLast));
			return output;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	QRY_SETTLEMENT_INFO(43) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf output = Unpooled.buffer(44);
			QrySettlementInfoRespVO qrySettlementInfoRespVO = (QrySettlementInfoRespVO) voBase;
			output.writeBytes(qrySettlementInfoRespVO.toByteBuf());
			output.writeBytes(respInfoVO.toByteBuf());
			output.writeBytes(DecodeUtils.intToLeByteArr(nRequestID));
			output.writeBytes(DecodeUtils.booleanToByteArr(bIsLast));
			return output;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	QRY_INSTRUMENT_COMMISSION_RATE(49) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf output = Unpooled.buffer(44);
			QryInstrumentCommissionRateRespVO qryInstrumentCommissionRateRespVO = (QryInstrumentCommissionRateRespVO) voBase;
			output.writeBytes(qryInstrumentCommissionRateRespVO.toByteBuf());
			output.writeBytes(respInfoVO.toByteBuf());
			output.writeBytes(DecodeUtils.intToLeByteArr(nRequestID));
			output.writeBytes(DecodeUtils.booleanToByteArr(bIsLast));
			return output;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	QRY_TRADING_ACCOUNT(50) {
		@Override
		public ByteBuf handle(CTPRespVOBase obj, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf output = Unpooled.buffer(44);
			QryTradingAccountRespVO qryTradingAccountRespVO = (QryTradingAccountRespVO) obj;
			output.writeBytes(qryTradingAccountRespVO.toByteBuf());
			output.writeBytes(respInfoVO.toByteBuf());
			output.writeBytes(DecodeUtils.intToLeByteArr(nRequestID));
			output.writeBytes(DecodeUtils.booleanToByteArr(bIsLast));
			return output;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	QRY_INVESTOR_POSITION_DETAIL(57) {
		@Override
		protected ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
			ByteBuf output = Unpooled.buffer(44);
			QryInvestorPositionDetailRespVO qryInvestorPositionDetailRespVO = (QryInvestorPositionDetailRespVO) voBase;
			output.writeBytes(qryInvestorPositionDetailRespVO.toByteBuf());
			output.writeBytes(respInfoVO.toByteBuf());
			output.writeBytes(DecodeUtils.intToLeByteArr(nRequestID));
			output.writeBytes(DecodeUtils.booleanToByteArr(bIsLast));
			return output;
		}

		@Override
		public void postHandle(CTPRespVOBase voBase) {

		}
	},
	;

	private final static String FLAG_SHOOK_HAND = "CTPPuppetShookHandSuccess";
	private final static String FLAG_HEAD_ERR = "CTPPuppetMsgHeadCheckFailure";

	private Integer no;
	CTPOnFuncMsgHandleEnum(Integer no) {
		this.no = no;
	}

	public ByteBuf getByteBuf(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast) {
		ByteBuf output = Unpooled.buffer(44);
		output.writeBytes(DecodeUtils.intToLeByteArr(this.getNo()));
		output.writeBytes(this.handle(voBase, respInfoVO, nRequestID, bIsLast));
		return output;
	}

	public static CTPOnFuncMsgHandleEnum getEnum(CTPRespVOBase voBase) {
		if (voBase instanceof RtnOrderRespVO) {
			return RTN_ORDER;
		}
		if (voBase instanceof RtnTradeRespVO) {
			return RTN_TRADE;
		}
		return null;
	}

	protected abstract ByteBuf handle(CTPRespVOBase voBase, RespInfoVO respInfoVO, Integer nRequestID, Boolean bIsLast);

	public abstract void postHandle(CTPRespVOBase voBase);
}
