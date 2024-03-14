package com.xinqi.ctp_puppet.netty.quot;


import com.xinqi.ctp_puppet.common.DecodeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 2021/11/3 15:28
 **/
@Setter
@Getter
public class SubscribeInstrumentReqVO implements Serializable {

	private int size;

	private int cmdId;

	private int requestId;

	private String instrumentId;

	public ByteBuf toByteBuf() {
		ByteBuf buf = Unpooled.buffer(44);
		buf.writeBytes(DecodeUtils.intToLeByteArr(size));
		buf.writeBytes(DecodeUtils.intToLeByteArr(cmdId));
		buf.writeBytes(DecodeUtils.intToLeByteArr(requestId));
		byte[] insCharArr = new byte[32];
		int insCharArrSize = insCharArr.length;
		//for (int i = 0; i < insCharArrSize; i++) {
		//	insCharArr[i] = ' ';
		//}
		byte[] a = instrumentId.getBytes();
		int aSize = a.length;
		//for (int i = 1; i <= aSize; i++) {
		//	insCharArr[insCharArrSize - i] = a[aSize - i];
		//}
		for (int i = 0; i < aSize; i++) {
			insCharArr[i] = a[i];
		}
		byte[] bytes_final = DecodeUtils.j2c(insCharArr);
		buf.writeBytes(bytes_final);
		return buf;
	}
}
