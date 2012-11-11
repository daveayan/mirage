package com.daveayan.mirage;

import java.util.ArrayList;
import java.util.List;

public class ObjectUtils {
	public static List<?> asList(Object... objects) {
		List<Object> list = new ArrayList<Object>();
		if(objects != null) {
			for(Object object: objects) {
				list.add(object);
			}
		}
		return list;
	}
}