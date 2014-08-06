package com.java.alogrithm.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 开放APK签名
 * 
 * @author steven-pan
 * 
 */
public class MD5SignHelper {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("MD5签名编测试：");
		
		final String key = "abcdefghijklmn";
		final String id = "0123456";
		final String originArray[] = new String[] { "id=" + id, "time=" + getReqTime() };

		String digitalSign = getMD5ByArray(originArray, key);
		System.out.println("digitalSign:" + digitalSign);
	}

	/**
	 * 取请求时间 yyyy-MM-dd HH:mm:ss.fff
	 * 
	 * @return
	 */
	public static String getReqTime() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);
		return df.format(date);
	}

	/**
	 * 获取数据加密值
	 * 
	 * @param sortedArray
	 * @param key
	 * @param inputCharset
	 * @return
	 */
	public static String getMD5ByArray(String[] originalArray, String key) {
		String[] sortedArray = bubbleSort(originalArray);
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < sortedArray.length; i++) {
			if (i == sortedArray.length - 1) {
				builder.append(sortedArray[i]);
			} else {
				builder.append(sortedArray[i] + "&");
			}
		}
		builder.append(key);
		return getMD5(builder.toString());
	}

	/**
	 * MD5加密
	 * 
	 * @param input
	 * @return
	 */
	private static String getMD5(String input) {
		String ret = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(input.getBytes("utf-8"));

			byte temp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte bt = temp[i];
				str[k++] = hexDigits[bt >>> 4 & 0xf];
				str[k++] = hexDigits[bt & 0xf];
			}
			ret = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 数组排序（冒泡排序法）
	 * 
	 * @param originalArray
	 * @return
	 */
	public static String[] bubbleSort(String[] originalArray) {
		int i, j;
		String temp;
		Boolean exchange;

		for (i = 0; i < originalArray.length; i++) {
			exchange = false;
			for (j = originalArray.length - 2; j >= i; j--) {
				if (originalArray[j + 1].compareTo(originalArray[j]) < 0) {
					temp = originalArray[j + 1];
					originalArray[j + 1] = originalArray[j];
					originalArray[j] = temp;

					exchange = true;
				}
			}
			if (!exchange) {
				break;
			}
		}
		return originalArray;
	}
}
