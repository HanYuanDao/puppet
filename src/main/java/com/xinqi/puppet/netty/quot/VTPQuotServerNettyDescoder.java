package com.xinqi.puppet.netty.quot;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 2021/11/12 09:16
 **/
@Slf4j
public class VTPQuotServerNettyDescoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int len = in.readableBytes();
		in.markReaderIndex();
		if (len > 0) {
			byte[] src = new byte[len];
			in.readBytes(src);
			in.resetReaderIndex();
			out.add(src);
		}
		in.skipBytes(in.readableBytes());
	}

}
