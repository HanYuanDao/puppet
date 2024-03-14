package com.xinqi.ctp_puppet.netty.tcp.vo;


import io.netty.buffer.ByteBuf;
import lombok.Getter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/22 10:38
 **/
public abstract class CTPReqVOBase {

	public abstract void toJavaBean(byte[] source);
}
