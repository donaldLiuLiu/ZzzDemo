package com.flayway.fl.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.flayway.fl.exception.FlException;

public class FlReadXml {
	
	private static String disk = "E";
	private static String path = "ZzzDemo";
	private static String file = "db.xml";
	
	private static String fileName = "conf/db.xml";
	private static Map<Object, Map<Object, Object>> dbMap = new HashMap<Object, Map<Object, Object>>();
	static {
		init();
	}
	public static void init() {
		String oriPath = disk + ":" + File.separator + path;
		File path = new File(oriPath);
		boolean flag = true;
		if(!path.exists())
			flag = path.mkdirs();
		if(false == flag) {
			File filePathFile = null;
			SAXReader reader = new SAXReader();
			String error = "无法创建目录[" + oriPath + "]-使用conf/db.xml文件";
			filePathFile = new File(FlReadXml.class.getClassLoader().getResource(fileName).getFile());
			try{
				FlBugPro.writeBug(FlReadXml.class, 
						FlReadXml.class.getMethod("init", new Class<?>[]{}), new RuntimeException(error));
				Document document = reader.read(filePathFile);
				Element root = document.getRootElement();
				@SuppressWarnings("rawtypes")
				List dbElements = root.elements("db");
				for(Object db : dbElements) {
					Element elem = (Element) db;
					Map<Object, Object> hmp = new HashMap<Object, Object>();
					hmp.put("url", elem.element("url").getText());
					hmp.put("user", elem.element("user").getText());
					hmp.put("password", elem.element("password").getText());
					dbMap.put(elem.attribute("name").getText(), hmp);
				}
			} catch(Exception e) {
				try {
					FlBugPro.writeBug(FlReadXml.class, 
							FlReadXml.class.getMethod("init", new Class<?>[]{}), e);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				dbMap = new HashMap<Object, Map<Object, Object>>();
			}
		} else {
			String filePath = oriPath + File.separator + file;
			File filePathFile = new File(filePath);
			SAXReader reader = new SAXReader();
			try{
				Document document = reader.read(filePathFile);
				Element root = document.getRootElement();
				@SuppressWarnings("rawtypes")
				List dbElements = root.elements("db");
				for(Object db : dbElements) {
					Element elem = (Element) db;
					Map<Object, Object> hmp = new HashMap<Object, Object>();
					hmp.put("url", elem.element("url").getText());
					hmp.put("user", elem.element("user").getText());
					hmp.put("password", elem.element("password").getText());
					dbMap.put(elem.attribute("name").getText(), hmp);
				}
			} catch(Exception e) {
				try {
					FlBugPro.writeBug(FlReadXml.class, 
							FlReadXml.class.getMethod("init", new Class<?>[]{}), e);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				dbMap = new HashMap<Object, Map<Object, Object>>();
			}
		}
	}
	public static Map<Object, Map<Object, Object>> getDbMap() {
		return dbMap;
	}
}
