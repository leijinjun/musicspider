package musicspider;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.ho.yaml.Yaml;
import org.junit.Test;


public class YamlTest {

	
	@SuppressWarnings("unchecked")
	@Test
	public void test1(){
		InputStream in = YamlTest.class.getResourceAsStream("/black-words.yaml");
		List<String> list = (List<String>) Yaml.load(in);
		HashedMap senseWords = new HashedMap(list.size());
		HashedMap endMap = new HashedMap();
		endMap.put("isEnd", true);
		Map nowMap =null;
		Map<String,Object> newWordMap=null;
		/*for (String s : list) {
			 nowMap = senseWords;
			for (int i = 0; i < s.length(); i++) {
				char keyChar = s.charAt(i);       //转换成char型  
                Object wordMap = senseWords.get(keyChar);       //获取  
                  
                if(wordMap != null){        //如果存在该key，直接赋值  
                    nowMap = (Map) wordMap;  
                }  
                else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个  
                	newWordMap = new HashMap<String,String>();
                	newWordMap.put("isEnd", "0");     //不是最后一个  
                    nowMap.put(keyChar, newWordMap);  
                    nowMap = newWordMap;  
                }  
                  
                if(i == s.length() - 1){  
                    nowMap.put("isEnd", "1");    //最后一个  
                }
			}
		}*/
		for (String key : list) {
			nowMap = senseWords;
			for (int i = 0; i < key.length(); i++) {
				char c = key.charAt(i);
				Object o = nowMap.get(c);
				if(o==null){
					newWordMap = new HashedMap();
					newWordMap.put("isEnd", false);
					nowMap.put(c, newWordMap);
					nowMap=newWordMap;
				}else{
					nowMap = (Map) o;
				}
				if(i==key.length()-1){
					nowMap.put("isEnd", true);
				}
			}
		}
		Iterator<Character> it = senseWords.keySet().iterator();
//		while(it.hasNext()){
//			Character next = it.next();
//			System.out.println(next+"-->"+senseWords.get(next));
//		}
		Map m = (Map)((Map) senseWords.get('我')).get('爱');
		System.out.println(m);
			/*String[] singleWords = s.split("");
			HashedMap map = new HashedMap();
			if(senseWords.get(singleWords[0])==null){
				if(singleWords.length>1){
					map.put("isEnd", false);
					senseWords.put(singleWords[0], map);
				}else{
					map.put("isEnd", true);
					senseWords.put(singleWords[0], endMap);
				}
			}else{
				map = (HashedMap) senseWords.get(singleWords[0]);
			}
			for (int i = 1,j = singleWords.length; i < j; i++) {
				if(map.get(singleWords[i])!=null){
					map = (HashedMap) map.get(singleWords[i]);
					if(i==j-1){
						map.put("isEnd", true);
						break;
					}
					continue;
				}
				if(i==j-1){
					map.put(singleWords[i], endMap);
				}else{
					HashedMap map2 = new HashedMap();
					map2.put("isEnd", false);
					map.put(singleWords[i], map2);
					map = map2;
				}
			}
		}*/
		String st = "国民党";
		for (int i = 0; i < st.length(); i++) {
			char c = st.charAt(i);
//			System.out.println(senseWords.get(c));
		}
	}
	
	@Test
	public void test2(){
		InputStream in = YamlTest.class.getResourceAsStream("/local.yaml");
		Object load = Yaml.load(in);
		//System.out.println(load);
	}
	
	@Test
	public void test3(){
		
	}
}
