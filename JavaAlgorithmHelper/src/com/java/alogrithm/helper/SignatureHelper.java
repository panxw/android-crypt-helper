package com.java.alogrithm.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * MD5, SHA-1等字符串签名工具
 * 
 * @author steven-pan
 * 
 */
public class SignatureHelper {
	/**
	 * algorithm: MD5
	 */
	private static final String ALGORITHM_MD5 = "MD5";

	/**
	 * algorithm: SHA-1
	 */
	private static final String ALGORITHM_SHA1 = "SHA-1";

	/**
	 * 对字符串取MD5值
	 * 
	 * @param strInput
	 *            输入字符串
	 * @return
	 */
	public static String encryptMD5(String strInput) {
		return encyptByAlogrithm(strInput, ALGORITHM_MD5);
	}

	/**
	 * 对字符串取SHA-1值
	 * 
	 * @param strInput
	 *            输入字符串
	 * @return
	 */
	public static String encryptSHA1(String strInput) {
		return encyptByAlogrithm(strInput, ALGORITHM_SHA1);
	}

	/**
	 * 按输入算法名取摘要签名
	 * 
	 * @param input
	 *            输入字符串
	 * @param alogrithm
	 *            摘要算法名
	 * @return
	 */
	private static String encyptByAlogrithm(String input, String alogrithm) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance(alogrithm);
			md.update(input.getBytes("utf-8"));
			byte temp[] = md.digest();

			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < temp.length; i++) {
				String hex = Integer.toHexString(temp[i] & 0xFF);
				if (hex.length() < 2) {
					buf.append("0");
				}
				buf.append(hex);
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取数据加密值
	 * 
	 * @param sortedArray
	 * @param key
	 * @param inputCharset
	 * @return
	 */
	public static String encryptMD5ByArray(String[] originalArray, String key) {
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
		return encryptMD5(builder.toString());
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

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("MD5签名编测试：");

		final String key = "abcdefghijklmn";
		final String id = "0123456";
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);// 取请求时间
																							// yyyy-MM-dd
																							// HH:mm:ss.fff
		final String time = df.format(date);
		final String originArray[] = new String[] { "id=" + id, "time=" + time, "key=" + key };
		String digitalSign = encryptMD5ByArray(originArray, key);
		System.out.println("digitalSign:" + digitalSign);

		System.out.println(encryptMD5("abcv"));
		;
		System.out.println(encryptSHA1("abcv"));
		;
	}
}
