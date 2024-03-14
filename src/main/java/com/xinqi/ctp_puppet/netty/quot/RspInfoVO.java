package com.xinqi.ctp_puppet.netty.quot;


import java.io.Serializable;
import java.util.Arrays;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 2021/11/5 14:38
 **/
public class RspInfoVO implements Serializable {

	/**
	 * 0表示成功，其它表示失败时的错误代码
	 */
	public int ErrCode;

	/**
	 * 失败时的错误信息
	 */
	public char[] ErrMsg = new char[128];

	@Override
	public String toString() {
		return "RspInfoVO{" +
				"ErrCode=" + ErrCode +
				", ErrMsg=" + Arrays.toString(ErrMsg) +
				'}';
	}

}
