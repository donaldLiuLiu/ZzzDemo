package com.flayway.fl.utils;

import java.util.HashMap;
import java.util.Map;

public class ConfCommonDef {
	private static String sf = "AES"; //算法名称
	private static String ysmyc = "7EV/Zzutjzg="; //原始秘钥串
	private static String bm = "UTF-8"; //编码
	private static int size = 128;  //size
	public static Map<Object, Object> transforToMap() {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("conf.sf", sf);
		map.put("conf.ysmyc", ysmyc);
		map.put("conf.bm", bm);
		map.put("conf.size", size);
		return map;
	}
}
