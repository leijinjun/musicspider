package cn.person.musicspider.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {

	private static PropertyDescriptor getpropertDescriptor(PropertyDescriptor orgDescriptor,
			PropertyDescriptor[] destPropertyDescriptors) {
		for (PropertyDescriptor descriptor : destPropertyDescriptors) {
			if(descriptor.getDisplayName().equals("class")){
				continue;
			}
			if(orgDescriptor.getPropertyType().equals(descriptor.getPropertyType())&&orgDescriptor.getName().equals(descriptor.getName())){
				return descriptor;
			}
		}
		return null;
	}
	
	public static <T> T copyProperties(Object org,Class<T> clazz){
		if(org == null){
			return null;
		}
		Class<? extends Object> orgClazz = org.getClass();
		try {
			T t = clazz.newInstance();
			BeanInfo orgBeanInfo = Introspector.getBeanInfo(orgClazz);
			BeanInfo destBeanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] orgPropertyDescriptors = orgBeanInfo.getPropertyDescriptors();
			PropertyDescriptor[] destPropertyDescriptors = destBeanInfo.getPropertyDescriptors();
			for (PropertyDescriptor orgDescriptor : orgPropertyDescriptors) {
				if(orgDescriptor.getDisplayName().equals("class")){
					continue;
				}
				PropertyDescriptor descriptor= getpropertDescriptor(orgDescriptor,destPropertyDescriptors);
				Method writeMethod = descriptor.getWriteMethod();
				Method readMethod = orgDescriptor.getReadMethod();
				writeMethod.invoke(t, readMethod.invoke(org));
			}
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void copyProperties(Object org,Object dest){
		Class<? extends Object> orgClazz = org.getClass();
		Class<? extends Object> destClazz = dest.getClass();
		try {
			BeanInfo orgBeanInfo = Introspector.getBeanInfo(orgClazz);
			BeanInfo destBeanInfo = Introspector.getBeanInfo(destClazz);
			PropertyDescriptor[] orgDescriptors = orgBeanInfo.getPropertyDescriptors();
			PropertyDescriptor[] destDescriptors = destBeanInfo.getPropertyDescriptors();
			for (PropertyDescriptor orgDescriptor : orgDescriptors) {
				if(orgDescriptor.getDisplayName().equals("class")){
					continue;
				}
				PropertyDescriptor descriptor= getpropertDescriptor(orgDescriptor,destDescriptors);
				Method writeMethod = descriptor.getWriteMethod();
				Method readMethod = orgDescriptor.getReadMethod();
				writeMethod.invoke(dest, readMethod.invoke(org));
			}
		} catch (Exception e) {
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
