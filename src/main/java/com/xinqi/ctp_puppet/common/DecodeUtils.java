package com.xinqi.ctp_puppet.common;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Description: TODO
 * @author : hanzhe.jason@gmail.com
 * @since : 2021/11/10 16:16
 **/
@Slf4j
public class DecodeUtils {

	private static final int BIT_NUM = 8;
	private static final int LENGTH_SHORT = 2;
	private static final int LENGTH_CHAR = 2;
	private static final int LENGTH_INTEGER = 4;
	private static final int LENGTH_LONG = 8;
	private static final int LENGTH_FLOAT = 4;
	private static final int LENGTH_DOUBLE = 8;

	private static final Character TERMINATOR = '\u0000';

	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	private static final Charset ISO88591_CHARSET = Charset.forName("ISO-8859-1");

	public static boolean isDefault(byte[] bytes) {
		if (null == null || bytes.length == 0) {
			return true;
		}
		if (TERMINATOR.equals(bytes[1])) {
			return true;
		}
		return false;
	}

	public static String getCString(byte[] bytes) {
		return new String(bytes, ISO88591_CHARSET)
				.replace("/(\\s*$)/g", "")
				.replace("\u0000", "");
		//return new String(bytes, ISO88591_CHARSET).replace("\\s+$", "");
	}

	public static byte[] getCByteArr(String source) {
		return source.getBytes(UTF8_CHARSET);
	}

	/**
	 * 根据字符串对象生成获得字节数组
	 *
	 * @param source
	 * @param length 字符个数。该数值会在方法中被乘以二
	 *
	 * @return byte[]
	 *
	 * @author: JasonHan (hanzhe.jason@gmail.com).
	 * 2024/03/05 11:39:06.
	 */
	public static byte[] getCByteArr(String source, int length) {
		byte[] output = new byte[length];
		if (StringUtils.isNotEmpty(source)) {
			byte[] cByteArr = DecodeUtils.getCByteArr(source);
			System.arraycopy(cByteArr, 0, output, 0, cByteArr.length);
		}
		return output;
	}

	public static String decodeStr(String source) {
		return new String(source.getBytes(ISO88591_CHARSET), UTF8_CHARSET);
	}

	public static byte[] c2j(byte[] bytes) {
		return c2j(ByteBuffer.wrap(bytes));
	}

	public static byte[] c2j(ByteBuffer inputBuffer) {
		return UTF8_CHARSET.encode(ISO88591_CHARSET.decode(inputBuffer)).array();
	}

	public static byte[] j2c(byte[] bytes) {
		return j2c(ByteBuffer.wrap(bytes));
	}

	public static byte[] j2c(ByteBuffer inputBuffer) {
		return ISO88591_CHARSET.encode(UTF8_CHARSET.decode(inputBuffer)).array();
	}

	public static boolean byteToBool(byte b) {
		return b == 0x01 ? true : false;
	}
	public static byte booleanToByte(Boolean bool) {
		return booleanToByteArr(bool)[1];
	}
	public static byte[] booleanToByteArr(Boolean bool) {
		if (null == bool) {
			return new byte[1];
		}
		return bool ? new byte[] {0x01} : new byte[] {0x00};
	}

	public static char leBytesToChar(byte[] bytes) {
		if (bytes == null || bytes.length != LENGTH_CHAR) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		char value = 0;
		for (int i = 0; i < LENGTH_CHAR; i++) {
			value |= ((bytes[i] & 0xff)) << (i * BIT_NUM);
		}
		return value;
	}
	public static char beBytesToChar(byte[] bytes) {
		if (bytes == null || bytes.length != LENGTH_CHAR) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		char value = 0;
		for (int i = 0; i < LENGTH_CHAR ; i++) {
			value |= ((bytes[LENGTH_CHAR - i - 1] & 0xff) << (i * BIT_NUM));
		}
		return value;
	}
	public static byte[] charToLeByteArr(Character a) {
		byte[] value = new byte[LENGTH_CHAR];
		if (null == a) {
			return value;
		}
		for (int i = 0; i < LENGTH_CHAR; i++) {
			value[i] = (byte) ((a >> (i * BIT_NUM)) & 0xff);
		}
		return value;
	}
	public static byte[] charToBeByteArr(Character a) {
		byte[] value = new byte[LENGTH_CHAR];
		if (null == a) {
			return value;
		}
		for (int i = 0; i < LENGTH_CHAR; i++) {
			value[LENGTH_SHORT - i - 1] = (byte) ((a >> (i * BIT_NUM)) & 0xff);
		}
		return value;
	}

	public static short leBytesToShort(byte[] bytes) {
		if (bytes == null || bytes.length != LENGTH_SHORT) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		short value = 0;
		for (int i = 0; i < LENGTH_SHORT; i++) {
			value |= ((bytes[i] & 0xff)) << (i * BIT_NUM);
		}
		return value;
	}
	public static short beBytesToShort(byte[] bytes) {
		if (bytes == null || bytes.length != LENGTH_SHORT) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		short value = 0;
		for (int i = 0; i < LENGTH_SHORT ; i++) {
			value |= ((bytes[LENGTH_SHORT - i - 1] & 0xff) << (i * BIT_NUM));
		}
		return value;
	}
	public static byte[] shortToLeByteArr(Short a) {
		byte[] value = new byte[LENGTH_SHORT];
		if (null == a) {
			return value;
		}
		for (int i = 0; i < LENGTH_SHORT; i++) {
			value[i] = (byte) ((a >> (i * BIT_NUM)) & 0xff);
		}
		return value;
	}
	public static byte[] shortToBeByteArr(Short a) {
		byte[] value = new byte[LENGTH_SHORT];
		if (null == a) {
			return value;
		}
		for (int i = 0; i < LENGTH_SHORT; i++) {
			value[LENGTH_SHORT - i - 1] = (byte) ((a >> (i * BIT_NUM)) & 0xff);
		}
		return value;
	}

	public static int leBytesToInt(byte[] bytes) {
		if (bytes == null || bytes.length != LENGTH_INTEGER) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		int value = 0;
		for (int i = 0; i < LENGTH_INTEGER; i++) {
			value |= ((int) (bytes[i] & 0xff)) << (i * BIT_NUM);
		}
		return value;
	}
	public static int beBytesToInt(byte[] bytes) {
		if (bytes == null || bytes.length != LENGTH_INTEGER) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		int value = 0;
		for (int i = 0; i < LENGTH_INTEGER ; i++) {
			value |= ((int)(bytes[LENGTH_INTEGER - i - 1] & 0xff) << (i * BIT_NUM));
		}
		return value;
	}
	public static byte[] intToLeByteArr(Integer a) {
		byte[] value = new byte[LENGTH_INTEGER];
		if (null == a) {
			return value;
		}
		for (int i = 0; i < LENGTH_INTEGER; i++) {
			value[i] = (byte) (a >> (i * BIT_NUM));
		}
		return value;
	}
	public static byte[] intToBeByteArr(Integer a) {
		byte[] value = new byte[LENGTH_INTEGER];
		if (null == a) {
			return value;
		}
		for (int i = 0; i < LENGTH_INTEGER; i++) {
			value[LENGTH_INTEGER - i - 1] = (byte) (a >> (i * BIT_NUM));
		}
		return value;
	}

	public static long leBytesToLong(byte[] bytes) {
		if (bytes == null || bytes.length != LENGTH_LONG) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		long value = 0;
		for (int i = 0; i < LENGTH_LONG; i++) {
			value |= ((long)(bytes[i] & 0xff)) << (i * BIT_NUM);
		}
		return value;
	}
	public static long beBytesToLong(byte[] bytes) {
		if (bytes == null || bytes.length != LENGTH_LONG) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		long value = 0;
		for (int i = 0; i < LENGTH_LONG ; i++) {
			value |= ((long)(bytes[i] & 0xff) << ((LENGTH_LONG - i - 1) * BIT_NUM));
		}
		return value;
	}
	public static byte[] longToLeByteArr(Long a) {
		byte[] value = new byte[LENGTH_LONG];
		if (null == a) {
			return value;
		}
		for (int i = 0; i < LENGTH_LONG; i++) {
			value[i] = (byte) ((a >> (i * BIT_NUM)));
		}
		return value;
	}
	public static byte[] longToBeByteArr(Long a) {
		byte[] value = new byte[LENGTH_LONG];
		if (null == a) {
			return value;
		}
		for (int i = 0; i < LENGTH_LONG; i++) {
			value[LENGTH_LONG - i - 1] = (byte) ((a >> (i * BIT_NUM)));
		}
		return value;
	}

	public static float leBytesToFloat(byte[] arr) {
		if (null == arr || arr.length != LENGTH_FLOAT) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		return Float.intBitsToFloat(leBytesToInt(arr));
	}
	public static float beBytesToFloat(byte[] arr) {
		if (null == arr || arr.length != LENGTH_FLOAT) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		return Float.intBitsToFloat(beBytesToInt(arr));
	}
	public static byte[] floatToLeByteArr(Float f) {
		return intToLeByteArr(f != null ? Float.floatToIntBits(f) : null);
	}
	public static byte[] floatToBeByteArr(Float f) {
		return intToBeByteArr(f != null ? Float.floatToIntBits(f) : null);
	}

	public static double leBytesToDouble(byte[] arr) {
		if (null == arr || arr.length != LENGTH_DOUBLE) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		return Double.longBitsToDouble(leBytesToLong(arr));
	}
	public static double beBytesToDouble(byte[] arr) {
		if (null == arr || arr.length != LENGTH_DOUBLE) {
			throw new IllegalArgumentException("Invalid byte array");
		}
		return Double.longBitsToDouble(beBytesToLong(arr));
	}
	public static byte[] doubleToLeByteArr(Double d) {
		return longToLeByteArr(d != null ? Double.doubleToLongBits(d) : null);
	}
	public static byte[] doubleToBeByteArr(Double d) {
		return longToBeByteArr(d != null ? Double.doubleToLongBits(d) : null);
	}

}
