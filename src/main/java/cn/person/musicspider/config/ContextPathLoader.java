package cn.person.musicspider.config;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class ContextPathLoader implements ServletContextListener {

	public static final String PROP_SUffIX=".properties";
	public static final String PROP_PRE="classpath:";

	protected Properties loadConfig(String fileName) throws IOException {
		File file = FileLoader.getFile(PROP_PRE+fileName+PROP_SUffIX);
		Properties properties=null;
		if(file!=null){
			properties = new Properties();
			properties.load(new FileInputStream(file));
		}
		return properties;
	}

	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
}
