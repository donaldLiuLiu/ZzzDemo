package com.flayway.fl.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.flayway.fl.exception.FlException;

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
		boolean flag = true;
		if(!path.exists())
			flag = path.mkdirs();
		if(false == flag) {
			OutputStream fos = null;
			File filePathFile = null;
			try{
				String error = "无法创建目录[" + oriPath + "]日志将被记录在conf/bug.txt文件中";
				filePathFile = new File(FlBugPro.class.getClassLoader().getResource(defaultFile).getFile());
				fos = new FileOutputStream(filePathFile, true);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
				String time = LocalDateTime.now().format(formatter);
				fos.write((time + " " + error + "\n").getBytes("UTF-8"));
				fos.flush();
				return filePathFile;
			} catch(Exception e) {
				throw new FlException("I001", e.getMessage());
			} finally {
				if(fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			String filePath = oriPath + File.separator + file;
			File filePathFile = new File(filePath);
			try{
				OutputStream fos = new FileOutputStream(filePathFile, true);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
				String time = LocalDateTime.now().format(formatter);
				String str = time + " " + "new 日志\n";
				fos.write(str.getBytes("UTF-8"));
				fos.flush();
				fos.close();
			} catch(IOException e) {
				OutputStream fos = null;
				File eFile = null;
				try{
					String error = "无法创建文件[" + file + "]日志将被记录在conf/bug.txt文件中" + "{" + e.getMessage() + "}";
					eFile = new File(FlBugPro.class.getClassLoader().getResource(defaultFile).getFile());
					fos = new FileOutputStream(eFile, true);
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
					String time = LocalDateTime.now().format(formatter);
					fos.write((time + " " + error + "\n").getBytes("UTF-8"));
					fos.flush();
					return eFile;
				} catch(Exception e2) {
					throw new FlException("I001", e2.getMessage());
				} finally {
					if(fos != null) {
						try {
							fos.close();
						} catch (IOException e3) {
							e3.printStackTrace();
						}
					}
				}
			}
			return filePathFile;
		}
	}
	
	
	public static void main(String argv[]) throws Exception {
		/*FlBugPro fl = new FlBugPro();
		fl.writeBug(FlBugPro.class, fl.getClass().getMethod("checkFilePath", null), new RuntimeException());
		*/
	}
	
	public static void writeBug(Class<?> clz, Method mtd, Throwable thw) {
		File file = bugFile;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file ,true);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
			String time = LocalDateTime.now().format(formatter);
			StringBuffer sb = new StringBuffer();
			sb.append(clz.getSimpleName()).append(" ")
			  .append(mtd.getName()).append(" ")
			  .append(time).append(" ")
			  .append(thw.getMessage()).append("\n");
			fos.write(sb.toString().getBytes("UTF-8"));
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
