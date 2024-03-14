package com.xinqi.ctp_puppet.netty.vtp;


import com.xinqi.ctp_puppet.netty.NettyTcpClient;
import com.xinqi.ctp_puppet.netty.quot.ClientChannelInitializer;
import com.xinqi.ctp_puppet.netty.quot.ClientConnectionListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 2022/10/17 16:04
 **/
@Slf4j
public class VTPNettyTcpClient extends NettyTcpClient {

	private static int REQUEST_ID = 1;

	public final static int RECONNECT_DELAY_TIME = 1;

	public final static TimeUnit RECONNECT_DELAY_TIME_UNIT = TimeUnit.SECONDS;

	/**
	 * 服务名称
	 */
	private String serviceNm;

	/**
	 * 服务路径
	 */
	private String host;

	/**
	 * 服务端口
	 */
	private int port;

	/**
	 * 与服务端建立连接后得到的通道对象
	 */
	private Channel channel;

	private Bootstrap bootstrap;

	private ClientConnectionListener connectionListener;

	public VTPNettyTcpClient(String serviceNm, String host, int port) {
		this.serviceNm = serviceNm;
		this.host = host;
		this.port = port;
		this.connectionListener = new ClientConnectionListener(this);
	}

	/**
	 * 初始化 `Bootstrap` 客户端引导程序
	 *
	 * @param
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2021/11/03 15:12:47.
	 */
	private final Bootstrap getBootstrap() {
		if (bootstrap == null) {
			bootstrap = new Bootstrap();
			EventLoopGroup group = new NioEventLoopGroup();
			/**
			 * 管道初始化
			 */
			bootstrap.group(group)
					//通道连接者
					.channel(NioSocketChannel.class)
					//通道处理者
					.handler(new ClientChannelInitializer(this))
					//心跳报活
					.option(ChannelOption.SO_KEEPALIVE, true);
		}

		return bootstrap;
	}

	/**
	 * 建立连接，获取连接通道对象
	 *
	 * @return
	 */
	@Override
	public void connect() {
		try {
			ChannelFuture channelFuture = null;
			try {
				channelFuture = getBootstrap().connect(host, port).addListener(connectionListener).sync();
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
			if (channelFuture != null && channelFuture.isSuccess()) {
				channel = channelFuture.channel();
				//log.info("{} connect tcp server host = {}, port = {} success", serviceNm, host, port);
			} else {
				//log.error("{} connect tcp server host = {}, port = {} fail", serviceNm, host, port);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e.getCause());
		}
	}

	/**
	 * 向服务器发送消息
	 *
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public void sendMsg(Object msg) throws Exception {
		if (channel != null) {
			channel.writeAndFlush(msg).sync();
		} else {
			log.warn("消息发送失败,连接尚未建立!");
		}
	}

}
