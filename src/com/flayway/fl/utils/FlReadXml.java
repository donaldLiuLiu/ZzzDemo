package com.flayway.fl.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class FlReadXml {
	
	private static String fileName = "conf/db.xml";
	private static Map<Object, Map<Object, Object>> dbMap = new HashMap<Object, Map<Object, Object>>();
	static {
		init();
	}
	public static void init() {
		SAXReader reader = new SAXReader();
		try{
			Document document = reader.read(FlReadXml.class.getClassLoader().getResourceAsStream(fileName));
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
				//系统异常退出
				System.exit(-1);
			}
			dbMap = new HashMap<Object, Map<Object, Object>>();
		}
	}
	public static Map<Object, Map<Object, Object>> getDbMap() {
		return dbMap;
	}
}
