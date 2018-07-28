package com.flayway.fl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("unused")
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
		boolean flag = true;
		if(!path.exists())
			flag = path.mkdirs();
		if(flag == false) {
			loadConf();
		} else {
			String filePath = oriPath + File.separator + file;
			File filePathFile = new File(filePath);
			Properties prop = new Properties();
			try{
				filePathFile.createNewFile();
				prop.load(new FileInputStream(filePathFile));
				boolean flage = true;
				String sf = StringUtils.transforToString(prop.get("conf.sf"));
				String ysmyc = StringUtils.transforToString(prop.get("conf.ysmyc"));
				String bm = StringUtils.transforToString(prop.get("conf.bm"));
				String size = StringUtils.transforToString(prop.get("conf.size"));
				if(sf.equals("") || ysmyc.equals("") || bm.equals("") ||size.equals(""))
					flage = false;
				if(flage == false) {
					throw new RuntimeException("conf.properties文件配置错误，将使用默认配置写入文件\n");
				}
				loadProperties(prop);
			} catch(Exception e) {
				try{
					FlBugPro.writeBug(FlReadProp.class, FlReadProp.class.getMethod("checkFilePath", new Class[]{}), e);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				loadConf();
				try {
					Map<Object, Object> map = ConfCommonDef.transforToMap();
					prop.setProperty("conf.sf", StringUtils.transforToString(map.get("conf.sf")));
					prop.setProperty("conf.ysmyc", StringUtils.transforToString(map.get("conf.ysmyc")));
					prop.setProperty("conf.bm", StringUtils.transforToString(map.get("conf.bm")));
					prop.setProperty("conf.size", StringUtils.transforToString(map.get("conf.size")));
					prop.store(new FileOutputStream(filePathFile), "conf\\.sf=AES;conf\\.size=128;conf\\.ysmyc=kkk;conf\\.bm=UTF-8");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			return ;
		}
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
				throw new RuntimeException("conf.properties文件配置错误，将使用默认配置写入文件\n");
			loadProperties(prop);
		} catch(FileNotFoundException e) {
			loadConf();
			try{
				FlBugPro.writeBug(FlReadProp.class, FlReadProp.class.getMethod("loadFromFile", new Class[]{}), e);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
			loadConf();
			try{
				FlBugPro.writeBug(FlReadProp.class, FlReadProp.class.getMethod("loadFromFile", new Class[]{}), e);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			try {
				Map<Object, Object> map = ConfCommonDef.transforToMap();
				prop.setProperty("conf.sf", StringUtils.transforToString(map.get("conf.sf")));
				prop.setProperty("conf.ysmyc", StringUtils.transforToString(map.get("conf.ysmyc")));
				prop.setProperty("conf.bm", StringUtils.transforToString(map.get("conf.bm")));
				prop.setProperty("conf.size", StringUtils.transforToString(map.get("conf.size")));
				prop.store(new FileOutputStream(filePathFile), "conf\\.sf=AES;conf\\.size=128;conf\\.ysmyc=kkk;conf\\.bm=UTF-8");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public static Object getPropByKey(Object k) {
		loadFromFile();
		Object obj = resMap.get(k);
		if(obj == null)
			try{
				FlBugPro.writeBug(FlReadProp.class, FlReadProp.class.getMethod("loadFromFile", new Class[]{}), new RuntimeException("key无效"));
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		return obj;
	}
}
