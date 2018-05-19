package cn.person.musicspider.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

	public static Properties load(String path){
		InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(path);
		Properties prop = new Properties();
	    try {
			prop.load(in);
			return prop;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
