package com.xinqi.ctp_puppet.common;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/5 11:21
 **/
@Slf4j
public class DecodeUtilsTest {

	@Test
	void bytesToLong() {
		//DecodeUtils.bytesToLong(bytes);;
	}

	@Test
	public void stringToBytes() {
		Gson GSON = new Gson();
		String str1 = "我爱你中国";
		byte[] cByteArr1 = DecodeUtils.getCByteArr(str1, 18);
		System.out.println(GSON.toJson(cByteArr1));

		str1 = "10:41:23";
		System.out.println(GSON.toJson(DecodeUtils.getCByteArr(str1, 9)));

		str1 = "%%%%%%%%%%%%%%%%";
		System.out.println(GSON.toJson(DecodeUtils.getCByteArr(str1, 26)));

		char a = '中';
		System.out.println(GSON.toJson(DecodeUtils.charToLeByteArr(a)));

		//String source = "%%%%%%%%%%%%%%%%";
		//int length = 13;
		//byte[] output = new byte[length];
		//if (StringUtils.isNotEmpty(source)) {
		//	byte[] cByteArr = DecodeUtils.getCByteArr(source);
		//	System.arraycopy(cByteArr, 0, output, 0, cByteArr.length);
		//}
		//System.out.println(output);
	}

}