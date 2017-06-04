package com.fishbaby.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ExcelToObject {
	 @SuppressWarnings("unchecked")
	 public static <T> ArrayList<T> getObjectFromExcel(String[][] readFromFile, Class<T> clazz) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, ParseException{
	    	ArrayList<T> res = new ArrayList<T>();
	    	System.out.println(clazz.getSimpleName());
	    	Field[] fields = clazz.getDeclaredFields();
	    	Map<String,String> annotionOrFieldName = getAnnotionOrFieldName(fields);
	    	Map<String,Integer> orderMapByExcel = orderMapByExcel(annotionOrFieldName, readFromFile[0]);
	    	Map<String, Class> fieldNameMapingType = fieldName2Type(fields);
	    	for (int j = 1; j < readFromFile.length; j++) {
	    		String[] strings = readFromFile[j];
	    		Object tempObj = clazz.newInstance();
	    		for (String fieldName : orderMapByExcel.keySet()) {
	    			Field field = tempObj.getClass().getDeclaredField(fieldName);
	    			field.setAccessible(true);
	    			Object tempObject = getObjectByfieldAndType(fieldName,fieldNameMapingType,strings[orderMapByExcel.get(fieldName)]);
	    			field.set(tempObj, tempObject);
	    		}
	    		res.add((T)tempObj);
			}
	    	return res;
	    }
	    /**
	     * 根据属性名得到对应类型的实例
	     * @param fieldName
	     * @param fieldNameMapingType
	     * @param strings 
	     * @return
	     * @throws ClassNotFoundException 
	     * @throws IllegalAccessException 
	     * @throws InstantiationException 
	     * @throws SecurityException 
	     * @throws NoSuchFieldException 
	     * @throws NoSuchMethodException 
	     * @throws InvocationTargetException 
	     * @throws IllegalArgumentException 
	     * @throws ParseException 
	     */
	    @SuppressWarnings("unchecked")
		private static Object getObjectByfieldAndType(String fieldName,
				Map<String, Class> fieldNameMapingType, String str) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, ParseException {
	    	Class objectClass = fieldNameMapingType.get(fieldName);
	    	String objectType = objectClass.getName();
	    	if(objectClass.isEnum()){
	    		Class temp = Class.forName(objectType);
	    		if(temp.isAnnotationPresent(Convertable.class)){
	    			Method method = temp.getMethod("ofText", String.class);
		    		return method.invoke(temp, str);
	    		}
	    		return null;
	    	}
	    	if(objectType.toLowerCase().endsWith("bigdecimal")){
	    		return new BigDecimal(Double.parseDouble(str));
	    	}
	    	else if(objectType.toLowerCase().endsWith("byte")){
	    		if(str.contains(".")){
	    			str = str.substring(0,str.indexOf("."));
	    		}
	    		return Byte.parseByte(str);
	    	}
	    	else if(objectType.toLowerCase().endsWith("short")){
	    		if(str.contains(".")){
	    			str = str.substring(0,str.indexOf("."));
	    		}
	    		return Short.parseShort(str);
	    	}
	    	else if(objectType.toLowerCase().endsWith("integer")){
	    		if(str.contains(".")){
	    			str = str.substring(0,str.indexOf("."));
	    		}
	    		return Integer.parseInt(str);
	    	}
	    	else if(objectType.toLowerCase().endsWith("long")){
	    		if(str.contains(".")){
	    			str = str.substring(0,str.indexOf("."));
	    		}
	    		return Long.parseLong(str);
	    	}
	    	else if(objectType.toLowerCase().endsWith("double")){
	    		return Double.parseDouble(str);
	    	}
	    	else if(objectType.toLowerCase().endsWith("float")){
	    		return Float.parseFloat(str);
	    	}
	    	else if(objectType.toLowerCase().endsWith("Long")){
	    		if(str.contains(".")){
	    			str = str.substring(0,str.indexOf("."));
	    		}
	    		return Long.parseLong(str);
	    	}
	    	else if(objectType.toLowerCase().endsWith("string")){
	    		return new String(str);
	    	}
	    	else if(objectType.toLowerCase().endsWith("date")){
	    		String m = "yyyy-MM-dd HH:mm:ss";
	    		StringBuilder sb = new StringBuilder();
	    		for (int i = 0; i < str.length(); i++) {
	    			if(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
	    				sb.append(m.charAt(i));
	    			}else {
						sb.append(str.charAt(i));
					}
	    		}
	    		return new SimpleDateFormat(sb.toString()).parse(str);
	    	}
	    	return null;
		}

		/**
	     * 得到属性与其所属类型的对应关系
	     * @param fields
	     * @return
	     */
	    private static Map<String, Class> fieldName2Type(Field[] fields){
	    	/*Map<String, String> res = new HashMap<String, String>();
	    	for (Field field : fields) {
				res.put(field.getName(), field.getType().getName());
			}*/
	    	Map<String, Class> res = new HashMap<String, Class>();
	    	for (Field field : fields) {
				res.put(field.getName(), field.getType());
			}
	    	return res;
	    }
	    /***
	     * 根据Excel表头决定Excel与Object属性的对应顺序
	     * @param annotionOrFieldName
	     * @param strings
	     * @return Map<String,Integer> --->Map<fieldName,ExcelIndex>
	     */
	    private static Map<String,Integer> orderMapByExcel(
				Map<String, String> annotionOrFieldName, String[] strings) {
	    	Map<String, Integer> res = new HashMap<String, Integer>();
	    	Set<String> mapKeySet = annotionOrFieldName.keySet();
	    	System.err.println(mapKeySet.toString());
	    	for (int i = 0; i < strings.length; i++) {
	    		if(!mapKeySet.contains(strings[i])){
					continue;
				}
	    		res.put(annotionOrFieldName.get(strings[i]), i);
			}
	    	return res;
		}

		/***
	     * 得到注解与属性的映射关系，没有注解，使用属性名充当注解
	     * @param fields
	     * @return Map<String,String> -->Map<aliasName, FieldName>
	     */
		private static Map<String,String> getAnnotionOrFieldName(Field[] fields) {
			Map<String,String> res = new HashMap<String, String>();
			for (Field field : fields) {
				System.out.println(field.getName());
				if(!field.isAnnotationPresent(AliasName.class)){
					res.put(field.getName(),field.getName());
					continue;
				}
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if(annotation.annotationType().equals(AliasName.class)){
						AliasName temp = (AliasName)annotation;
						res.put(temp.aliaName(),field.getName());
						break;
					}
				}
			}
			return res;
		}
}
