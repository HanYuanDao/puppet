package com.xinqi.ctp_puppet.netty.gateway.tcp.server;


import com.xinqi.ctp_puppet.netty.quot.VTPQuotServerNettyDescoder;
import com.xinqi.ctp_puppet.netty.gateway.tcp.ServerChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 2021/11/3 14:40
 **/
@Slf4j
public class CTPPuppetChannelInitializer extends ChannelInitializer<SocketChannel> {

	//public NettyTcpClient client;

	//public ClientChannelInitializer(NettyTcpClient client) {
	//	this.client = client;
	//}

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();
		//IdleStateHandler心跳机制,如果超时触发Handle中userEventTrigger()方法
		pipeline.addLast("idleStateHandler",
				new IdleStateHandler(3, 0, 0, TimeUnit.MINUTES));
		// 解决tcp拆包、粘包
		pipeline.addLast(new LengthFieldBasedFrameDecoder(
				ByteOrder.LITTLE_ENDIAN, Integer.MAX_VALUE, 0, 4, 4, 0, true));
		//字符串编解码器
		pipeline.addLast(
				//new StringDecoder(),
				new VTPQuotServerNettyDescoder(),
				new StringEncoder()
		);

		//自定义Handler
		pipeline.addLast("clientChannelHandler", new ServerChannelHandler());
	}
}
