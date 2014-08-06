package com.java.alogrithm.helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import com.java.alogrithm.utils.Base64;


/**
 * 该算法于1977年由美国麻省理工学院MIT(Massachusetts Institute of Technology)的 Ronal
 * Rivest，Adi Shamir和Len Adleman三位年轻教授提出，并以三人的姓氏Rivest，Shamir和Adlernan命名为RSA算法，
 * 是一个支持变长密钥的公共密钥算法，需要加密的文件块的长度也是可变的!
 * 所谓RSA加密算法，是世界上第一个非对称加密算法，也是数论的第一个实际应用。它的算法如下：
 * 1.找两个非常大的质数p和q（通常p和q都有155十进制位或都有512十进制位）并计算n=pq，k=(p-1)(q-1)。
 * 2.将明文编码成整数M，保证M不小于0但是小于n。 3.任取一个整数e，保证e和k互质，而且e不小于0但是小于k。加密钥匙（称作公钥）是(e, n)。
 * 4.找到一个整数d，使得ed除以k的余数是1（只要e和n满足上面条件，d肯定存在）。解密钥匙（称作密钥）是(d, n)。 加密过程：
 * 加密后的编码C等于M的e次方除以n所得的余数。 解密过程： 解密后的编码N等于C的d次方除以n所得的余数。 只要e、d和n满足上面给定的条件。M等于N。
 * 
 */
public class RSAHelper {
	/** 指定加密算法为RSA */
	private static String ALGORITHM = "RSA";
	/** 指定key的大小 */
	private static int KEYSIZE = 1024;
	/** 指定公钥存放文件 */
	private static String PUBLIC_KEY_FILE = "PublicKey";
	/** 指定私钥存放文件 */
	private static String PRIVATE_KEY_FILE = "PrivateKey";

	public static void main(String[] args) throws Exception {
		System.out.println("RSA加解密测试：");
		
		generateKeyPair();

		final String source = "73C58BAFE578C59366D8C995CD0B9";// 要加密的字符串
		System.out.println("加密前:" + source);

		String cryptograph = encrypt(source);// 生成的密文
		System.out.println("Base64 format:" + cryptograph);

		String target = decrypt(cryptograph);// 解密密文
		System.out.println("解密后:" + target);
	}
	
	/**
	 * 生成密钥对
	 */
	private static void generateKeyPair() throws Exception {
		/** RSA算法要求有一个可信任的随机数源 */
		SecureRandom sr = new SecureRandom();
		/** 为RSA算法创建一个KeyPairGenerator对象 */
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
		/** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
		kpg.initialize(KEYSIZE, sr);
		/** 生成密匙对 */
		KeyPair kp = kpg.generateKeyPair();
		/** 得到公钥 */
		Key publicKey = kp.getPublic();
		/** 得到私钥 */
		Key privateKey = kp.getPrivate();
		/** 用对象流将生成的密钥写入文件 */
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
		oos1.writeObject(publicKey);
		oos2.writeObject(privateKey);
		/** 清空缓存，关闭文件输出流 */
		oos1.close();
		oos2.close();
	}

	/**
	 * 加密方法 source： 源数据
	 */
	public static String encrypt(String source) throws Exception {
		/** 将文件中的公钥对象读出 */
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
		Key key = (Key) ois.readObject();
		ois.close();
		/** 得到Cipher对象来实现对源数据的RSA加密 */
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] b = source.getBytes();
		/** 执行加密操作 */
		byte[] b1 = cipher.doFinal(b);
		return Base64.encode(b1);
	}

	/**
	 * 解密算法 cryptograph:密文
	 */
	public static String decrypt(String cryptograph) throws Exception {
		/** 将文件中的私钥对象读出 */
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
		Key key = (Key) ois.readObject();
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] b1 = Base64.decode(cryptograph);
		/** 执行解密操作 */
		byte[] b = cipher.doFinal(b1);
		return new String(b);
	}

}