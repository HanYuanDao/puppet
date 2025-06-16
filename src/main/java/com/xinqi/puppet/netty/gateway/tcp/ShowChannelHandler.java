package com.xinqi.puppet.netty.gateway.tcp;


import com.google.gson.Gson;
import com.xinqi.puppet.common.DecodeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/2/29 14:32
 **/
@Slf4j
public class ShowChannelHandler extends ChannelInboundHandlerAdapter {

	private final static Gson GSON = new Gson();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf in = (ByteBuf) msg;
		int readableBytes = in.readableBytes();
		byte[] source = new byte[readableBytes];
		in.readBytes(source);

		int offset = 0;
		byte[] length = new byte[4];
		for (int i = 0; i < length.length; i++) {
			length[i] = source[offset + i];
		}
		log.info("数据长度：{}", DecodeUtils.leBytesToInt(length));

		log.info(GSON.toJson(source));
	}
}
