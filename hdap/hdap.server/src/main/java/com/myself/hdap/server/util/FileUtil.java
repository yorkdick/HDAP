package com.myself.hdap.server.util;

import com.myself.hdap.common.CommonInstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
	
	public static File moveJar2Path(File file) throws Exception {
		File cfile = CommonInstant.getJarFile(file.getName());
		cfile.getParentFile().mkdirs();
		Files.copy(file.toPath(),cfile.toPath());
		return cfile;
	}
}
