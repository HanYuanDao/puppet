package com.xinqi.ctp_puppet.netty.quot;


import com.xinqi.ctp_puppet.netty.NettyTcpClient;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 2021/11/25 09:23
 **/
@Slf4j
public class ClientConnectionListener implements ChannelFutureListener {

	private NettyTcpClient client;

	public ClientConnectionListener(NettyTcpClient client) {
		this.client = client;
	}

	/**
	 * Netty 断线重连
	 *
	 * @param future
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2021/11/29 19:46:23.
	 */
	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		//log.info("This is ClientConnectionListener.operationComplete. {}", future.isSuccess());
		/*if (!future.isSuccess()) {
			log.info("NettyTcpClient is Reconnect by ClientConnectionListener.operationComplete");
			final EventLoop loop = future.channel().eventLoop();
			loop.schedule(new Runnable() {
				@Override
				public void run() {
					client.connect();
				}
			}, NettyTcpClient.RECONNECT_DELAY_TIME, NettyTcpClient.RECONNECT_DELAY_TIME_UNIT);
		}*/
	}

}
