package com.xinqi.ctp_puppet.netty.tcp.vo;


import io.netty.buffer.ByteBuf;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/14 01:53
 **/
public abstract class CTPRespVOBase {

	public abstract ByteBuf toByteBuf();
}
