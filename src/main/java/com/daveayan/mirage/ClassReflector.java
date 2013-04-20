package com.daveayan.mirage;

public class ClassReflector {
	private Class< ? > class_to_reflect_on;
	
	public Object createObject() {
		return null;
	}

	public boolean canBeCastedTo(Object otherObject) {
		if (otherObject == null) {
			return Boolean.FALSE;
		}
		return canCast(otherObject.getClass(), class_to_reflect_on);
	}

	public boolean canBeCastedTo(Class< ? > otherClazz) {
		return canCast(otherClazz, class_to_reflect_on);
	}

	private boolean canCast(Class< ? > to, Class< ? > from) {
		if (to == null || from == null) {
			return Boolean.FALSE;
		}
		return to.isAssignableFrom(from);
	}

	public static ClassReflector clazz(Class< ? > clazz) {
		return on(clazz);
	}
	
	public static ClassReflector from(Class< ? > clazz) {
		return on(clazz);
	}

	public static ClassReflector on(Class< ? > clazz) {
		ClassReflector c = new ClassReflector();
		c.class_to_reflect_on = clazz;
		return c;
	}
	
	private ClassReflector() {}
}