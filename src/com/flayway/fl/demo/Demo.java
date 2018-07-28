package com.flayway.fl.demo;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.flayway.fl.utils.FlReadProp;


public class Demo implements Cloneable{
	
	public static void main(String argv[]) throws Exception{
		
		FlReadProp.getPropByKey("123");
		
	}
}
