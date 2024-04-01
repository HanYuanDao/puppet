package com.xinqi.ctp_puppet.netty.tcp.vo;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import com.xinqi.ctp_puppet.netty.tcp.server.CTPOnFuncMsgHandleEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Setter;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/14 03:39
 **/
@Setter
public class RespInfoVO extends CTPRespVOBase {

	/**
	 * 错误代码
	 */
	private Integer errorID;
	/**
	 * 错误信息
	 */
	private String errorMsg;

	@Override
	public ByteBuf toByteBuf() {
		ByteBuf buf = Unpooled.buffer(85);
		buf.writeBytes(DecodeUtils.intToLeByteArr(errorID));
		buf.writeBytes(DecodeUtils.getCByteArr(errorMsg, 162));
		return buf;
	}

}
