package cn.person.musicspider.config;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class FileLoader {
	
	private static final String SPLIT_SEP=":";
	
	public static File getFile(String path){
		if(path==null){
			return null;
		}
		int pos = path.indexOf(ContextPathLoader.PROP_PRE);
		if(pos==-1){
			return new File(path);
		}
		String[] sp = path.split(SPLIT_SEP);
		if(sp.length>1){
			String rel = sp[1];
			URL resource = FileLoader.class.getClassLoader().getResource(rel);
			try {
			//	ResourceBundle bundle = ResourceBundle.getBundle("env/local");
				if(resource==null){
					return null;
				}
				return new File(resource.toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
