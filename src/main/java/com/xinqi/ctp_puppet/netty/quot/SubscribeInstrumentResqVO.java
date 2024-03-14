package com.xinqi.ctp_puppet.netty.quot;


import java.io.Serializable;
import java.util.Arrays;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 2021/11/5 14:31
 **/
public class SubscribeInstrumentResqVO implements Serializable {

	public int size;

	public int cmdId;

	public int requestId;

	public RspInfoVO rspInfoVO;

	public char[] instrumentId = new char[32];

	@Override
	public String toString() {
		return "SubscribeInstrumentResqVO {" +
				"size=" + size +
				", cmdId=" + cmdId +
				", requestId=" + requestId +
				", rspInfoVO=" + rspInfoVO +
				", instrumentId=" + Arrays.toString(instrumentId) +
				'}';
	}

}
