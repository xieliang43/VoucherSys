package com.voucher.util;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class FieldChecher {
	private static FieldChecher instance = new FieldChecher();
	
	private FieldChecher() {}
	
	public static FieldChecher getInstance() {
		return instance;
	}
	
	public boolean checkField(Class clazz, String field) {
		Set<String> fieldSet = new HashSet<String>();
		Field []fields = clazz.getDeclaredFields();
		if(fields != null && fields.length > 0) {
			for(Field f : fields) {
				fieldSet.add(f.getName());
			}
		}
		if(fieldSet.contains(field)) {
			return true;
		}
		return false;
	}
}
