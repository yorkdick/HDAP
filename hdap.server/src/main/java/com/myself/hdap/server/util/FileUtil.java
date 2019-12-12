package com.myself.hdap.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
	
	public static File moveJar2Path(File file,String path) throws Exception {
		String classPath = ClassLoader.getSystemResource("").getPath();
		
		File cfile = new File(classPath);
		
		String fileName = file.getName().substring(0,file.getName().lastIndexOf("."));
		String sub = file.getName().substring(file.getName().lastIndexOf(".")+1);
		
		String cpath = cfile.getParent()+File.separator+path+File.separator+fileName+getRealName(fileName)+"."+sub;
		
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(cpath);
		
		byte[] b = new byte[2048];
		
		int read = 0;
		while((read = fis.read(b))>0) {
			fos.write(b,0,read);
		}
		
		fis.close();
		fos.close();
		
		return new File(cpath);
	}

	private static String getRealName(String fileName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return fileName + sdf.format(new Date());
	}
}
