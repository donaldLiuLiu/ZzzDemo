package com.flayway.fl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FlReadProp {
	
	private static String disk = "E";
	private static String path = "ZzzDemo";
	private static String file = "conf.properties";
	
	private static String fileName = "conf/conf.properties";
	private static Map<Object, Object> resMap = new HashMap<Object, Object>();
	private FlReadProp() {}
	static {
		init();
	}
	public static void init() {
		checkFilePath();
		
	}
	public static void checkFilePath() {
		String oriPath = disk + ":" + File.separator + path;
		File path = new File(oriPath);
		if(!path.exists())
			path.mkdirs();
		String filePath = oriPath + File.separator + file;
		File filePathFile = new File(filePath);
		Properties prop = new Properties();
		try{
			//当文件不存在的时候创建文件
			filePathFile.createNewFile();
			//尝试性的从文件读取，如果读取失败，则使用系统中ConfCommonDef定义的写到文件里面
			prop.load(new FileInputStream(filePathFile));
			boolean flag = true;
			String sf = StringUtils.transforToString(prop.get("conf.sf"));
			String ysmyc = StringUtils.transforToString(prop.get("conf.ysmyc"));
			String bm = StringUtils.transforToString(prop.get("conf.bm"));
			String size = StringUtils.transforToString(prop.get("conf.size"));
			if(sf.equals("") || ysmyc.equals("") || bm.equals("") ||size.equals(""))
				flag = false;
			if(flag == false) { //读取失败
				throw new RuntimeException("读取失败");
			}
			//读取成功
			loadProperties(prop);
		} catch(Exception e) {
			loadConf();
			//文件读取失败，则使用系统中ConfCommonDef定义的写到文件里面
			try {
				Map<Object, Object> map = ConfCommonDef.transforToMap();
				prop.setProperty("conf.sf", StringUtils.transforToString(map.get("conf.sf")));
				prop.setProperty("conf.ysmyc", StringUtils.transforToString(map.get("conf.ysmyc")));
				prop.setProperty("conf.bm", StringUtils.transforToString(map.get("conf.bm")));
				prop.setProperty("conf.size", StringUtils.transforToString(map.get("conf.size")));
				prop.store(new FileOutputStream(filePathFile), "conf\\.sf=AES;conf\\.size=128;conf\\.ysmyc=kkk;conf\\.bm=UTF-8");
			} catch (Exception e1) {
				throw new RuntimeException("config.properties文件失败，请检查：" + disk + ":" + File.separator + path);
			}
		}
		return ;
	}
	public static void loadConf() {
		resMap = ConfCommonDef.transforToMap();
	}
	public static void loadProperties(Properties prop) {
		for(Object ks : prop.keySet()) {
			resMap.put(ks, prop.get(ks));
		}
	}
	public static void loadFromFile() {
		String oriPath = disk + ":" + File.separator + path;
		String filePath = oriPath + File.separator + file;
		Properties prop = new Properties();
		File filePathFile = new File(filePath);
		try {
			prop.load(new FileInputStream(filePathFile));
			String sf = StringUtils.transforToString(prop.get("conf.sf"));
			String ysmyc = StringUtils.transforToString(prop.get("conf.ysmyc"));
			String bm = StringUtils.transforToString(prop.get("conf.bm"));
			String size = StringUtils.transforToString(prop.get("conf.size"));
			if(sf.equals("") || ysmyc.equals("") || bm.equals("") ||size.equals(""))
				throw new RuntimeException("读取失败");
			loadProperties(prop);
		} catch (IOException e) {
			loadConf();
			throw new RuntimeException("config.properties文件读取失败，请检查：" + disk + ":" + File.separator + path);
		}
	}
	public static Object getPropByKey(Object k) {
		loadFromFile();
		Object obj = resMap.get(k);
		if(obj == null)
			throw new RuntimeException("key无效");
		return obj;
	}
}
