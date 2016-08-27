package com.joker.core.util;

import java.io.File;

public class FileUtil {

	/**
	 * 创建文件.
	 * @param fileName
	 */
	public static void createFolder(String fileName){
		File file=new File(fileName);
		if(!file.exists() || !file.isDirectory()){
			file.mkdirs();
		}
	}
}
