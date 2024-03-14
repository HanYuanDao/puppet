package com.xinqi.ctp_puppet.netty.tcp.vo;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Setter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/3/4 16:32
 **/
@Setter
public class ErrorRespVO extends CTPRespVOBase {

	private Integer errorID;

	private String errorMsg;

	@Override
	public ByteBuf toByteBuf() {
		ByteBuf buf = Unpooled.buffer(95);
		buf.writeBytes(DecodeUtils.intToLeByteArr(errorID));
		buf.writeBytes(DecodeUtils.getCByteArr(errorMsg, 91));
		return buf;
	}

}
