package com.flayway.fl.demo;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.sun.xml.internal.fastinfoset.sax.Properties;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Work {
	public static void main(String argv[]) throws Exception {
		new Properties();
		def();
		enf();
	}
	
	public static void def() throws Exception {
		Cipher deCipher = Cipher.getInstance("DES");
		BASE64Decoder d = new BASE64Decoder();
		byte b[] = d.decodeBuffer("7EV/Zzutjzg=");
		
		DESKeySpec dks = new DESKeySpec(b);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		
		deCipher.init(Cipher.DECRYPT_MODE, key);
		byte[] result = d.decodeBuffer("02tFOToIZhAP3K/LzFhAWg==");  //base64 decode
		byte strByte[] = deCipher.doFinal(result);                   //cipher 解密
		String s = new String(strByte);
		
		System.out.println(s);
	}
	
	public static void enf() throws Exception {
		Cipher enCipher = Cipher.getInstance("DES");
		BASE64Encoder e = new BASE64Encoder();
		BASE64Decoder d = new BASE64Decoder();
		byte b[] = d.decodeBuffer("7EV/Zzutjzg=");
		
		DESKeySpec dks = new DESKeySpec(b);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		
		enCipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] result = enCipher.doFinal("zhcpb_srmtest".getBytes("UTF-8"));  //cipher加密
		String reResult = e.encode(result);  //base64 encode
		
		System.out.println(reResult);
	}
}
/* srm开发库url : jdbc:oracle:thin:@10.71.32.37:1521:devdb      jdbc:oracle:thin:@10.71.31.12:1521:orcl
 * srm开发库用户名：  zhcpb_srmtest                                 kyxt_zsdx                 
 * srm开发库密码    ：  zhcpb_srmtest                                 kyxt_zsdx
 */

