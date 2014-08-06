package com.java.alogrithm;

import com.java.alogrithm.helper.AESHelper;
import com.java.alogrithm.helper.DESedeHelper;
import com.java.alogrithm.helper.MD5SignHelper;
import com.java.alogrithm.helper.RSAHelper;
import com.java.alogrithm.utils.Hex;

/**
 * HEX,AES,DESede,RSA,MD5等总测试
 * 
 * @author steven-pan
 * 
 */
public class Test {

	/**
	 * @param args
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		MD5SignHelper.main(args);
		System.out.println("------------------------------------\n");

		Hex.main(args);
		System.out.println("------------------------------------\n");

		AESHelper.main(args);
		System.out.println("------------------------------------\n");
		
		DESedeHelper.main(args);
		System.out.println("------------------------------------\n");

		RSAHelper.main(args);
		System.out.println("------------------------------------\n");
	}

}
