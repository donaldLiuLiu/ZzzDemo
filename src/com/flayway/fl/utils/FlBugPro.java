package com.flayway.fl.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlBugPro {
	private static String disk = "E";
	private static String path = "ZzzDemo";
	private static String file = "bug.txt";
	private static String defaultFile = "conf/bug.txt";
	
	private static File bugFile = null;
	static {
		bugFile = checkFilePath();
	}
	
	public static File checkFilePath() {
		String oriPath = disk + ":" + File.separator + path;
		File path = new File(oriPath);
		if(!path.exists())
			path.mkdirs();
		String filePath = oriPath + File.separator + file;
		File filePathFile = new File(filePath);
		try{
			//默认写入一句话,检测文件是否成功创建
			OutputStream fos = new FileOutputStream(filePathFile, true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String str = sdf.format(new Date()) + " new 日志\n";
			fos.write(str.getBytes());
			fos.flush();
			fos.close();
		} catch(IOException e) {
			//文件无法写入，则认为文件无法创建成功
			//则在项目的路径里面创建日志文件，同时把错误信息写进去
			URL rs = FlBugPro.class.getClassLoader().getResource(defaultFile);
			try {
				OutputStream fos = new FileOutputStream(rs.getFile(), true);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String str = sdf.format(new Date()) + "创建日志文件失败，将使用默认日志文件\n";
				fos.write(str.getBytes());
				fos.flush();
				fos.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return filePathFile;
	}
	
	
	public static void main(String argv[]) throws Exception {
		FlBugPro fl = new FlBugPro();
		FlBugPro.writeBug(FlBugPro.class, fl.getClass().getMethod("checkFilePath", null), new RuntimeException("233"));
	}
	
	public static void writeBug(Class<?> clz, Method mtd, Throwable thw) {
		File file = bugFile;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file ,true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer sb = new StringBuffer();
			sb.append(clz.getSimpleName()).append(" ")
			  .append(mtd.getName()).append(" ")
			  .append(sdf.format(new Date())).append(" ")
			  .append(thw.getMessage()).append("\n");
			fos.write(sb.toString().getBytes(StringUtils.transforToString(FlReadProp.getPropByKey("conf.bm"))));
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
