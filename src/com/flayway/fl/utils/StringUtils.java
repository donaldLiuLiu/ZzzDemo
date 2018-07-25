package com.flayway.fl.utils;

public class StringUtils {
	public static boolean isEmpty(String value) {
		if(value == null) return true;
		if(value.trim().length() == 0) return true;
		return false;
	}
	public static String trim(String value) {
		if(value == null) return null;
		return value.trim();
	}
	public static String transforToString(Object object) {
		if(object == null) return "";
		return String.valueOf(object);
	}
	public static String getHelpMenuSplit() {
		return "#";
	}
}
