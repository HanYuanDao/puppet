package com.xinqi.ctp_puppet.netty;


/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 2022/10/18 09:10
 **/
public abstract class NettyTcpClient {

	public abstract void connect();

	public abstract void sendMsg(Object msg) throws Exception;
}
