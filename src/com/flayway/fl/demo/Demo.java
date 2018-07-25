package com.flayway.fl.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.flayway.fl.utils.FlBugPro;
import com.flayway.fl.utils.FlReadProp;
import com.flayway.fl.utils.FlReadXml;

public class Demo implements Cloneable{
	
	public static void main(String argv[]) throws Exception{
		
		FlReadProp.getPropByKey("conf.sf");
		
	}
}
