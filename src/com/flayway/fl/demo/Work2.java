package com.flayway.fl.demo;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Work2 {
	private static String mycr = "7EV/Zzutjzg=";
	private static byte[] myc = null;
	static {
		Decoder decoder = Base64.getDecoder();
		myc = decoder.decode(mycr);
		System.out.println(myc.length);
	}
	
	public static void main(String argv[])throws Exception {
		
		SecretKey aesSecretKey = generatorSecretKey2("AES", 128);
		SecretKey desSecretKey = generatorSecretKey2("DES", 56);
		
		Cipher encodeCipher = Cipher.getInstance("AES");
		encodeCipher.init(Cipher.ENCRYPT_MODE, aesSecretKey);
		byte[] zgrBytes = encodeCipher.doFinal("中国人".getBytes());
		
		
		Cipher decodeCipher = Cipher.getInstance("AES");
		decodeCipher.init(Cipher.DECRYPT_MODE, aesSecretKey);
		byte[] zgrRet = decodeCipher.doFinal("O8RgROm66P8nlAIdWa9dEw==".getBytes());
		
		System.out.println(new String(zgrRet));
		
	}
	
	/**
	 * SunJCE 中
	 * 无 SecretKeyFactor.ACE ClassPath 
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static SecretKey generatorSecretKey1(String algorithm)throws Exception {
		
		SecretKeyFactory secretKeyGenerator = SecretKeyFactory.getInstance(algorithm);
		SecretKeySpec secretKeySpec = new SecretKeySpec(myc, algorithm);
		
		SecretKey secretKey = secretKeyGenerator.generateSecret(secretKeySpec);
		System.out.println(secretKey.getEncoded().length);
		return secretKey;
		
	}
	
	/**
	 * KeyGenerator
	 * @param algorithm
	 * @param keySize
	 * @return
	 * @throws Exception
	 */
	public static SecretKey generatorSecretKey2(String algorithm, int keySize) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		keyGenerator.init(keySize, new SecureRandom(myc));
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey;
	}
	
}
