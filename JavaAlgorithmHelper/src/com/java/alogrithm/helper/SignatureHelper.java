package com.java.alogrithm.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


import com.java.alogrithm.utils.Hex;

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
	 * algorithm: HmacSHA1
	 */
	private static final String HMAC_SHA1 = "HmacSHA1";

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
	 * 取文件MD5值
	 * @param file
	 * @return
	 */
	public static String encryptFileMD5(File file) {
		FileInputStream fis = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(ALGORITHM_MD5);
			fis = new FileInputStream(file);
			byte[] buffer = new byte[102400];
			int length;
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			return Hex.encode(md.digest());
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance(alogrithm);
			md.update(input.getBytes("utf-8"));
			byte temp[] = md.digest();
			return Hex.encode(temp);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * encode HmacSHA1
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] encodeHmacSHA1(final String data, final String key) {
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			SecretKeySpec spec = new SecretKeySpec(key.getBytes(), HMAC_SHA1);
			mac.init(spec);
			byte[] byteHMAC = mac.doFinal(data.getBytes());
			if (byteHMAC != null) {
				return byteHMAC;
			}
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取byte数组加密值
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
	 * 按key升序排序
	 * @param map
	 * @return
	 */
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
		sortMap.putAll(map);
		return sortMap;
	}
	
	public static class MapKeyComparator implements Comparator<String>{
		public int compare(String str1, String str2) {
			return str1.compareTo(str2);
		}
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",
				Locale.CHINA);// 取请求时间
								// yyyy-MM-dd
								// HH:mm:ss.fff
		final String time = df.format(date);
		final String originArray[] = new String[] { "id=" + id, "time=" + time,
				"key=" + key };
		String digitalSign = encryptMD5ByArray(originArray, key);
		System.out.println("digitalSign:" + digitalSign);

		System.out.println(encryptMD5("abcv"));
		System.out.println(encryptSHA1("abcv"));

		System.out.println(Hex.encode(encodeHmacSHA1("abcv", "asdfasdafsf")));
	}
}
