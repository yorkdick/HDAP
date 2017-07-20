package com.myself.hdap.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtil {
	
	public static File moveFile2Path(File file,String path) throws Exception {
		String classpPath = ClassLoader.getSystemResource("").getPath();
		
		File cfile = new File(classpPath);
		
		String cpath = cfile.getParent()+File.separator+path+File.separator+file.getName();
		
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
}
