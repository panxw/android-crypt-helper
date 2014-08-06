package com.java.alogrithm.utils;


/**
 * HEX字符串与字节码转换工具
 * 
 * @author steven-pan
 * 
 */
public class Hex {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("HEX编解码测试：");
		
		final String input = "01234567890abcdefghijklmnopqrstuvwxyz";
		System.out.println("编码前:" + input);

		String hex = encode(input.getBytes("UTF-8"));
		System.out.println("HEX format:" + hex);

		byte[] hexbytes = deocde(hex);
		System.out.println("解密后:" + new String(hexbytes, "UTF-8"));
	}

	/**
	 * 将16进制转换为二进制(服务端)
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] deocde(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String encode(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

}
