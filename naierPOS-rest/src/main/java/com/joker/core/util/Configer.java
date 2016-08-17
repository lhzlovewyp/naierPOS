package com.joker.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configer {
	private static final Logger logger=LoggerFactory.getLogger(Configer.class);
	private static Properties properties=new Properties();
	
	
	static{
		loadProperties("/config.properties");
	}
	
	public static Properties getProperties(){
		return properties;
	}
	public static String get(String key){
		return properties.getProperty(key);
	}
	
	private static boolean loadProperties(String path){
		InputStream fileInputStream=null;
		InputStreamReader read=null;
		try{
			String classPath=Configer.class.getResource("/").getPath();
			File file=new File(classPath+path);
			if(file.exists()){
				fileInputStream=new FileInputStream(file);
				read=new InputStreamReader(fileInputStream,"UTF-8");
				properties.load(read);
				return true;
			}
			logger.error("unable to read config.properties");
			return false;
		}catch(Exception e){
			logger.error("发生错误",e);
			return false;
		}finally{
			try{
				if(fileInputStream!=null){
					fileInputStream.close();
				}
			}catch(Exception e){
				logger.error("关闭流失败!"+path);
			}
			try{
				if(read!=null){
					read.close();
				}
			}catch(Exception e){
				logger.error("关闭流失败!"+path);
			}
		}
	}
}
