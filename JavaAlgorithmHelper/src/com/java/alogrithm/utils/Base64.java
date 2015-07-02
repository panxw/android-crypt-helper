package com.java.alogrithm.utils;  
  
import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.io.OutputStream;  
import java.io.UnsupportedEncodingException;

/**
 * Base64字符串与字节码转换工具
 * 
 * @author steven-pan
 * 
 */
public class Base64 {  
    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();  
   
    /** 
     * data[]进行编码 
     * @param data 
     * @return 
     */  
        public static String encode(byte[] data) {  
            int start = 0;  
            int len = data.length;  
            StringBuffer buf = new StringBuffer(data.length * 3 / 2);  
  
            int end = len - 3;  
            int i = start;  
            int n = 0;  
  
            while (i <= end) {  
                int d = ((((int) data[i]) & 0x0ff) << 16)  
                        | ((((int) data[i + 1]) & 0x0ff) << 8)  
                        | (((int) data[i + 2]) & 0x0ff);  
                buf.append(legalChars[(d >> 18) & 63]);  
                buf.append(legalChars[(d >> 12) & 63]);  
                buf.append(legalChars[(d >> 6) & 63]);  
                buf.append(legalChars[d & 63]);  
                i += 3;  
                if (n++ >= 14) {  
                    n = 0;  
                    buf.append(" ");  
                }  
            }  
  
            if (i == start + len - 2) {  
                int d = ((((int) data[i]) & 0x0ff) << 16)  
                        | ((((int) data[i + 1]) & 255) << 8);  
                buf.append(legalChars[(d >> 18) & 63]);  
                buf.append(legalChars[(d >> 12) & 63]);  
                buf.append(legalChars[(d >> 6) & 63]);  
                buf.append("=");  
            } else if (i == start + len - 1) {  
                int d = (((int) data[i]) & 0x0ff) << 16;  
                buf.append(legalChars[(d >> 18) & 63]);  
                buf.append(legalChars[(d >> 12) & 63]);  
                buf.append("==");  
            }  
  
            return buf.toString();  
        }  
  
        private static int decode(char c) {  
            if (c >= 'A' && c <= 'Z')  
                return ((int) c) - 65;  
            else if (c >= 'a' && c <= 'z')  
                return ((int) c) - 97 + 26;  
            else if (c >= '0' && c <= '9')  
                return ((int) c) - 48 + 26 + 26;  
            else  
                switch (c) {  
                case '+':  
                    return 62;  
                case '/':  
                    return 63;  
                case '=':  
                    return 0;  
                default:  
                    throw new RuntimeException("unexpected code: " + c);  
                }  
        }  
  
        /** 
         * Decodes the given Base64 encoded String to a new byte array. The byte 
         * array holding the decoded data is returned. 
         */  
  
        public static byte[] decode(String s) {  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            try {  
                decode(s, bos);  
            } catch (IOException e) {  
                throw new RuntimeException();  
            }  
            byte[] decodedBytes = bos.toByteArray();  
            try {  
                bos.close();  
                bos = null;  
            } catch (IOException ex) {  
                System.err.println("Error while decoding BASE64: " + ex.toString());  
            }  
            return decodedBytes;  
        }  
  
        private static void decode(String s, OutputStream os) throws IOException {  
            int i = 0;  
            int len = s.length();  
            while (true) {  
                while (i < len && s.charAt(i) <= ' ')  
                    i++;  
                if (i == len)  
                    break;  
                int tri = (decode(s.charAt(i)) << 18)  
                        + (decode(s.charAt(i + 1)) << 12)  
                        + (decode(s.charAt(i + 2)) << 6)  
                        + (decode(s.charAt(i + 3)));  
                os.write((tri >> 16) & 255);  
                if (s.charAt(i + 2) == '=')  
                    break;  
                os.write((tri >> 8) & 255);  
                if (s.charAt(i + 3) == '=')  
                    break;  
                os.write(tri & 255);  
                i += 4;  
            }  
        }  
        
    	/**
    	 * 去掉BASE64加密字符串换行符
    	 * 
    	 * @param str
    	 * @return
    	 */
    	public static String filter(String str) {
    		String output = "";
    		StringBuffer sb = new StringBuffer();
    		for (int i = 0; i < str.length(); i++) {
    			int asc = str.charAt(i);
    			if (asc != 10 && asc != 13) {
    				sb.append(str.subSequence(i, i + 1));
    			}
    		}
    		output = new String(sb);
    		return output;
    	}
    	
    	
    	public static void main(String[] args) {
            try {
                System.out.println(encode("asdssdfafasdfsw12345?@!#~#@#%#$%&%^*&&(&*)_()+()+sdfadsfsdfsfasd-*/878ssdfasdfasdfsadfsdafsadfasdfasdf".getBytes("UTF-8")));
                System.out.println(new String(decode("YXNkc3NkZmFmYXNkZnN3MTIzNDU/QCEjfiNAIyUjJCUmJV4qJiYoJiopXygp KygpK3NkZmFkc2ZzZGZzZmFzZC0qLzg3OHNzZGZhc2RmYXNkZnNhZGZzZGFm c2FkZmFzZGZhc2Rm"), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    	
//    	private static final char last2byte = (char) Integer
//    			.parseInt("00000011", 2);
//
//    	private static final char last4byte = (char) Integer
//    			.parseInt("00001111", 2);
//
//    	private static final char last6byte = (char) Integer
//    			.parseInt("00111111", 2);
//
//    	private static final char lead6byte = (char) Integer
//    			.parseInt("11111100", 2);
//
//    	private static final char lead4byte = (char) Integer
//    			.parseInt("11110000", 2);
//
//    	private static final char lead2byte = (char) Integer
//    			.parseInt("11000000", 2);
//
//    	private static final char[] encodeTable = new char[] { 'A', 'B', 'C', 'D',
//    			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
//    			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
//    			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
//    			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
//    			'4', '5', '6', '7', '8', '9', '+', '/' };
//    	
//    	
//    	public static String encode(byte[] from) {
//    		StringBuffer to = new StringBuffer((int) (from.length * 1.34) + 3);
//    		int num = 0;
//    		char currentByte = 0;
//    		for (int i = 0; i < from.length; i++) {
//    			num = num % 8;
//    			while (num < 8) {
//    				switch (num) {
//    				case 0:
//    					currentByte = (char) (from[i] & lead6byte);
//    					currentByte = (char) (currentByte >>> 2);
//    					break;
//    				case 2:
//    					currentByte = (char) (from[i] & last6byte);
//    					break;
//    				case 4:
//    					currentByte = (char) (from[i] & last4byte);
//    					currentByte = (char) (currentByte << 2);
//    					if ((i + 1) < from.length) {
//    						currentByte |= (from[i + 1] & lead2byte) >>> 6;
//    					}
//    					break;
//    				case 6:
//    					currentByte = (char) (from[i] & last2byte);
//    					currentByte = (char) (currentByte << 4);
//    					if ((i + 1) < from.length) {
//    						currentByte |= (from[i + 1] & lead4byte) >>> 4;
//    					}
//    					break;
//    				}
//    				to.append(encodeTable[currentByte]);
//    				num += 6;
//    			}
//    		}
//    		if (to.length() % 4 != 0) {
//    			for (int i = 4 - to.length() % 4; i > 0; i--) {
//    				to.append("=");
//    			}
//    		}
//    		return to.toString();
//    	}
          
}  