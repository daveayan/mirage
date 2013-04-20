package com.daveayan.mirage;

public class ObjectReflector {
	private Object object_to_reflect_on;
	
	public boolean canBeCastedTo(Class<?> otherClazz) {
		if(object_to_reflect_on == null || otherClazz == null) {
			return Boolean.FALSE;
		}
		return canCast(otherClazz, object_to_reflect_on.getClass());
	}
	
	public boolean canBeCastedTo(Object otherObject) {
		if(object_to_reflect_on == null || otherObject == null) {
			return Boolean.FALSE;
		}
		return canCast(otherObject.getClass(), object_to_reflect_on.getClass());
	}
	
	private boolean canCast(Class<?> to, Class<?> from) {
		if (to == null || from == null) {
			return Boolean.FALSE;
		}
		return to.isAssignableFrom(from);
	}
	
	public static ObjectReflector object(Object object) {
		return on(object);
	}
	
	public static ObjectReflector on(Object object) {
		ObjectReflector o = new ObjectReflector();
		o.object_to_reflect_on = object;
		return o;
	}
	
	private ObjectReflector() {}
}