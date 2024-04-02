package com.xinqi.ctp_puppet.netty.tcp.server;


import com.google.gson.Gson;
import com.xinqi.ctp_puppet.common.DecodeUtils;
import com.xinqi.ctp_puppet.netty.tcp.vo.CTPRespVOBase;
import com.xinqi.ctp_puppet.netty.tcp.vo.RespInfoVO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/23 10:54
 **/
@Slf4j
@Getter
public class CTPMsgHandlerTask implements Runnable {

	private static final Integer NUM_MESSAGE_TRY = 3;

	private long handleTime;
	private CTPOnFuncMsgHandleEnum ctpOnFuncMsgHandleEnum;
	private CTPRespVOBase obj;
	private RespInfoVO pRspInfo;
	private Integer nRequestID;
	private Boolean bIsLast;

	private Channel channel;

	public CTPMsgHandlerTask() {
		this.handleTime = System.nanoTime();
	}
	public CTPMsgHandlerTask(long handleTime) {
		this.handleTime = handleTime;
	}

	public void loading(CTPOnFuncMsgHandleEnum ctpOnFuncMsgHandleEnum,
				  CTPRespVOBase obj, RespInfoVO respInfoVO,
				  Integer nRequestID, Boolean bIsLast) {
		this.ctpOnFuncMsgHandleEnum = ctpOnFuncMsgHandleEnum;
		this.obj = obj;
		this.pRspInfo = respInfoVO;
		this.nRequestID = nRequestID;
		this.bIsLast = bIsLast;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void run() {
		try {
			ByteBuf payload = ctpOnFuncMsgHandleEnum.getByteBuf(obj, pRspInfo, nRequestID, bIsLast);
			int lengthOutput = payload.readableBytes() + 4;
			ByteBuf output = Unpooled.buffer(lengthOutput);
			output.writeBytes(DecodeUtils.intToLeByteArr(lengthOutput));
			output.writeBytes(payload);
			sendMessage(output);
		} catch (Exception e) {
			log.error("执行CTP消息转发任务失败", e);
		}
	}

	private void sendMessage(ByteBuf byteBuf) {
		/**
		 * 当傀儡机向外发送消息时，有可能出现io异常，此时添加一个重试次数为{@link NUM_MESSAGE_TRY}的重试机制
		 */
		int messageTryNum = NUM_MESSAGE_TRY;
		while (messageTryNum > 0) {
			try {
				if (null != channel) {
					channel.writeAndFlush(byteBuf);
				} else {
					TradeTcpServerFactory.sendMessage(byteBuf);
				}
				messageTryNum = 0;
			} catch (Exception e) {
				messageTryNum--;
				if (null != channel) {
					log.error("服务端往单一客户端发送消息失败。", e);
				} else {
					log.error("服务端往多个客户端发送消息失败。", e);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					log.error("", e);
				}
			}
		}

		Gson gson = new Gson();
		log.info(gson.toJson(ctpOnFuncMsgHandleEnum));
		log.info(gson.toJson(obj));
		log.info(gson.toJson(pRspInfo));
		log.info(gson.toJson(nRequestID));
		log.info(gson.toJson(bIsLast));
		log.info("消息长度：" + byteBuf.readableBytes());
	}

}
