package com.joker.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * 读取配置文件参数
 * Created by Crell on 2016/1/17.
 */
public class PropertiesUtil {

	//private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	public static String getProperty(String fileName, String key) throws IOException {
		Properties prop = new Properties();
		loadProperties(prop, new File(fileName));
		return prop.getProperty(key);
	}
	
	public static void loadProperties(Properties prop,File file){
		Assert.notNull(prop);
		Assert.notNull(file);
		
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			LogUtil.error(e.getMessage(),e);
		}
		try {
			prop.load(is);
		} catch (IOException e) {
			LogUtil.error(e.getMessage(),e);
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				LogUtil.error(e.getMessage(),e);
			}
		}
	}

	public static Properties loadProperties(String filename){
		Assert.notNull(filename);
		URL url = Thread.currentThread().getContextClassLoader().getResource(filename);
		filename = url.getFile();
		File file=new File(filename);
		return loadProperties(file);
	}

	public static Properties loadProperties(File file){
		Assert.notNull(file);
		Properties prop=new Properties();
		loadProperties(prop, file);
		return prop;
	}

}
