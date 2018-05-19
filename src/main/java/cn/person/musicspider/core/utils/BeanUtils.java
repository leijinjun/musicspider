package cn.person.musicspider.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {

	private static PropertyDescriptor getPropertiesDescriptor(PropertyDescriptor orgDescriptor,
			PropertyDescriptor[] destPropertyDescriptors) {
		for (PropertyDescriptor descriptor : destPropertyDescriptors) {
			if(descriptor.getDisplayName().equals("class")){
				continue;
			}
			if(orgDescriptor.getPropertyType().equals(descriptor.getPropertyType())
					&&orgDescriptor.getName().equals(descriptor.getName())){
				return descriptor;
			}
		}
		return null;
	}
	
	public static <T> T copyProperties(Object org,Class<T> clazz){
		if(org == null){
			return null;
		}
		T t = null;
		try {
			t = clazz.newInstance();
			copy(t,org);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public static void copyProperties(Object org,Object dest){
		copy(dest,org);
	}

	private static void copy(Object dest,Object org){
		Class<? extends Object> orgClazz = org.getClass();
		Class<? extends Object> destClazz = dest.getClass();
		try {
			BeanInfo orgBeanInfo = Introspector.getBeanInfo(orgClazz);
			BeanInfo destBeanInfo = Introspector.getBeanInfo(destClazz);
			PropertyDescriptor[] orgPropertyDescriptors = orgBeanInfo.getPropertyDescriptors();
			PropertyDescriptor[] destPropertyDescriptors = destBeanInfo.getPropertyDescriptors();
			for (PropertyDescriptor orgDescriptor : orgPropertyDescriptors) {
				if(orgDescriptor.getDisplayName().equals("class")){
					continue;
				}
				PropertyDescriptor descriptor= getPropertiesDescriptor(orgDescriptor,destPropertyDescriptors);
				Method writeMethod = descriptor.getWriteMethod();
				Method readMethod = orgDescriptor.getReadMethod();
				writeMethod.invoke(dest, readMethod.invoke(org));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static <T> List<T> copyPropertiesList(List<?> orgList,Class<T> clazz){
		if(orgList==null){
			return null;
		}
		List<T> list = new ArrayList<T>();
		for (Object org : orgList) {
			list.add(copyProperties(org, clazz));
		}
		return list;
	}

}
