package com.daveayan.mirage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class ReflectionUtils {

	public static Object setFieldInObject(Object object, String fieldName, Object valueToSet) {
		if(object == null || fieldName == null) return null;
		Object objectUnderWork = object;
		List<Field> fields = getAllFieldsIn(objectUnderWork);
		for(Field field: fields) {
			if(StringUtils.equals(field.getName(), fieldName)) {
				makeAccessible(field);
				try {
					field.set(objectUnderWork, valueToSet);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return objectUnderWork;
	}

	public static Object getDeepFieldInObject(Object object, String deepFieldName) {
		if(object == null || deepFieldName == null) return null;
		Object objectUnderWork = object;
		String[] fieldNames = StringUtils.split(deepFieldName, '.');
		for(String fieldName: fieldNames) {
			objectUnderWork = getFieldInObject(objectUnderWork, fieldName);
		}
		return objectUnderWork;
	}

	public static Object getDeepFieldInObjectSafely(Object object, String deepFieldName) {
		if(object == null || deepFieldName == null) return null;
		Object objectUnderWork = object;
		String[] fieldNames = StringUtils.split(deepFieldName, '.');
		for(String fieldName: fieldNames) {
			objectUnderWork = getFieldInObjectSafely(objectUnderWork, fieldName);
		}
		return objectUnderWork;
	}

	public static Object getFieldInObjectSafely(Object object, String fieldName) {
		try {
			return getFieldInObject(object, fieldName);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public static Object getFieldInObject(Object object, String fieldName) throws IllegalArgumentException {
		if(object == null) return null;
		List<Field> fields = getAllFieldsIn(object);
		for (Field field : fields) {
			if (fieldNameIs(field, fieldName)) {
				return getFieldValueSafely(object, field);
			}
		}
		throw new IllegalArgumentException();
	}

	public static Object getFieldValueSafely(Object object, Field field) {
		try {
			return getFieldValue(object, field);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Object getFieldValue(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
		if(isNotAccessible(field)) { makeAccessible(field); }
		return field.get(object);
	}

	public static boolean fieldNameIs(Field field, String fieldName) {
		if (field == null || fieldName == null)
			return false;
		if (field.getName().trim().equals(fieldName.trim()))
			return true;
		return false;
	}

	public static boolean objectImplements(Object objectUnderTest, Class<?> interfaceExpected) {
		return classImplements(objectUnderTest.getClass(), interfaceExpected);
	}

	public static boolean classImplements(Class<?> classUnderTest, Class<?> interfaceExpected) {
		if (classUnderTest.equals(interfaceExpected)) {
			return true;
		}
		Class<?>[] interfaces = classUnderTest.getInterfaces();
		if (interfaces != null) {
			for (Class<?> clazz : interfaces) {
				if (clazz.equals(interfaceExpected)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean classIsOfEitherType(Class<?> classUnderTest, Class<?>... classes) {
		for (Class<?> clazz : classes) {
			if (classUnderTest.getName().trim().equals(clazz.getName().trim())) {
				return true;
			}
		}
		return false;
	}

	public static boolean classIsOfType(Class<?> actualClass, Class<?> expectedClass) {
		return actualClass.getName().trim().equals(expectedClass.getName().trim());
	}

	public static boolean objectIsOfType(Object object, Class<?> expectedClass) {
		return classIsOfType(object.getClass(), expectedClass) || objectImplements(object, expectedClass);
	}

	public static List<Class<?>> classNamesToClasses(List<String> classNames) {
		return Lists.transform(classNames, new Function<String, Class<?>>() {
			public Class<?> apply(String type) {
				return ReflectionUtils.getClassForClassName(type);
			}
		});
	}

	public static List<Field> getAllFieldsIn(Object object) {
		List<Field> classes = new ArrayList<Field>();
		classes.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
		Class<?> superclass = object.getClass().getSuperclass();
		while (superclass != null) {
			classes.addAll(Arrays.asList(superclass.getDeclaredFields()));
			superclass = superclass.getSuperclass();
		}

		return classes;
	}

	public static List<Field> getAllFieldsSortedAscendingIn(Object object) {
		List<Field> allFields = getAllFieldsIn(object);

		Comparator<Field> comparator = new Comparator<Field>() {
			public int compare(Field f1, Field f2) {
				return f1.getName().trim().compareToIgnoreCase(f2.getName().trim());
			}
		};
		Collections.sort(allFields, comparator);
		return allFields;
	}

	public static boolean isAccessible(Field field) {
		return Modifier.isPublic(field.getModifiers());
	}

	public static boolean isNotAccessible(Field field) {
		return ! Modifier.isPublic(field.getModifiers());
	}

	public static void makeAccessible(Field field) {
		if (!isAccessible(field)) {
			field.setAccessible(true);
		}
	}

	public static Class<?> asClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object objectFor(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			return objectForClassForcibly(clazz);
		} catch (IllegalAccessException e) {
			return objectForClassForcibly(clazz);
		}
	}

	public static Object objectForClassForcibly(Class<?> clazz) {
		Objenesis objenesis = new ObjenesisStd();
		ObjectInstantiator thingyInstantiator = objenesis.getInstantiatorOf(clazz);
		return thingyInstantiator.newInstance();
	}

	public static Object objectForClassForcibly(String className) {
		return objectForClassForcibly(asClass(className));
	}

	/**
	 * Use this method to get the Object for the specified class name
	 * @param className
	 * @return
	 */
	public static Object objectFor(String className) {
		return objectFor(asClass(className));
	}

	public static Object set_value_on_field(Object target, String field_name, Object value) {
		List<Field> fields = getAllFieldsIn(target);
		for (Field field : fields) {
			if (StringUtils.equals(field.getName(), field_name)) {
				makeAccessible(field);
				try {
					field.set(target, value);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return target;
	}

	public static Object get_value_on_field(Object target, String field_name) {
	List<Field> fields = getAllFieldsIn(target);
	for (Field field : fields) {
		if (fieldNameIs(field, field_name)) {
			return getFieldValueSafely(target, field);
		}
	}
	return null;
}

	/**
	 * Use this method to find a specific method in a object
	 * @param targetObject
	 * @param methodName
	 * @param parameters
	 * @return java.lang.reflect.Method if one was found, null otherwise
	 */
	public static Method getMethodFor(Object targetObject, String methodName, Object[] parameters) {
		return getMethodFor(targetObject.getClass(), methodName, parameters);
	}

	/**
	 * Use this method to find a specific method in a class
	 * @param targetClass
	 * @param methodName
	 * @param parameters
	 * @return java.lang.reflect.Method if one was found, null otherwise
	 */
	public static Method getMethodFor(Class<?> targetClass, String methodName, Object[] parameters) {
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().trim().equals(methodName)) {
				if (canCast(method.getParameterTypes(), parameters)) {
					return method;
				}
			}
		}
		return null;
	}

	public static boolean canCast(Class<?>[] to, Object[] from) {
		if (to == null || from == null)
			return false;
		if (to.length != from.length)
			return false;
		if (to.length == from.length) {
			int i = 0;
			for (Class<?> parameterClass : to) {
				if (from[i] != null && !canCast(parameterClass, from[i]))
					return false;
				i++;
			}
		}
		return true;
	}

	public static List<String> get_all_field_names_on(Class< ? > target) {
		List<String> field_names = new ArrayList<String>();
		Object target_object = objectForClassForcibly(target);
		List<Field> fields = getAllFieldsIn(target_object);
		for (Field field : fields) {
			if (field != null) {
				field_names.add(field.getType().toString());
			}
		}
		return field_names;
	}

	public static Object getFullInstanceOfClassForcibly(Class<?> clazz) {
		Object o = getInstanceOfClassForcibly(clazz);
		List<Field> fields = ReflectionUtils.getAllFieldsIn(o);
		Iterator<Field> iter = fields.iterator();
		while (true) {
			if (!iter.hasNext())
				break;
			Field field = iter.next();

		}
		return o;
	}

	/**
	 * Use this method to get an instance of the object of the specific class name.
	 * This method does not throw any exception
	 * @param className
	 * @return Object if one was instantiated, null otherwise
	 */
	public static Object getInstanceOfClassForcibly(String className) {
		return getInstanceOfClassForcibly(getClassForClassName(className));
	}

	/**
	 * Use this method to get an instance of the object of the specific class.
	 * This method does not throw any exception
	 * @param Class<?>
	 * @return Object if one was instantiated, null otherwise
	 */
	public static Object getInstanceOfClassForcibly(Class<?> clazz) {
		Objenesis objenesis = new ObjenesisStd();
		ObjectInstantiator thingyInstantiator = objenesis.getInstantiatorOf(clazz);
		return thingyInstantiator.newInstance();
	}

	/**
	 * Use this method to get an instance of the object of the specific class.
	 * This method throws exception if there were issues creating the object
	 * @param Class<?>
	 * @return Object if one was instantiated, null otherwise
	 */
	public static Object getInstanceOfClass(Class<?> clazz) {
		Objenesis objenesis = new ObjenesisStd();
		ObjectInstantiator thingyInstantiator = objenesis.getInstantiatorOf(clazz);
		return thingyInstantiator.newInstance();
	}

	/**
	 * Use this method to get the class for the name of class passed in. Does Class.forName under.
	 * @param className
	 * @return The class if one was found, null otherwise
	 */
	public static Class<?> getClassForClassName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/** Use this method to determine if the "from" can be casted "to".
	 * This is a safe method, it does not throw exception.
	 *
	 * @param to - Object
	 * @param from - Object
	 * @return 	false if 'to' or 'from' is null
	 * 					false if 'from' cannot be casted 'to'
	 * 					true if 'from' can be casted 'to'
	 */
	public static boolean canCast(Object to, Object from) {
		if (to == null || from == null)
			return false;
		return canCast(to.getClass(), from.getClass());
	}

	/** Use this method to determine if the "from" can be casted "to".
	 * This is a safe method, it does not throw exception.
	 *
	 * @param to - Object
	 * @param from - Class<?>
	 * @return 	false if 'to' or 'from' is null
	 * 					false if 'from' cannot be casted 'to'
	 * 					true if 'from' can be casted 'to'
	 */
	public static boolean canCast(Object to, Class<?> from) {
		if (to == null || from == null)
			return false;
		return canCast(to.getClass(), from);
	}

	/** Use this method to determine if the "from" can be casted "to".
	 * This is a safe method, it does not throw exception.
	 *
	 * @param to - Class<?>
	 * @param from - Object
	 * @return 	false if 'to' or 'from' is null
	 * 					false if 'from' cannot be casted 'to'
	 * 					true if 'from' can be casted 'to'
	 */
	public static boolean canCast(Class<?> to, Object from) {
		if (to == null || from == null)
			return false;
		return canCast(to, from.getClass());
	}

	/** Use this method to determine if the "from" can be casted "to".
	 * This is a safe method, it does not throw exception.
	 *
	 * @param to - Class<?>
	 * @param from - Class<?>
	 * @return 	false if 'to' or 'from' is null
	 * 					false if 'from' cannot be casted 'to'
	 * 					true if 'from' can be casted 'to'
	 */
	public static boolean canCast(Class<?> to, Class<?> from) {
		if (to == null || from == null)
			return false;
		return to.isAssignableFrom(from);
	}

	public static Object call_forcibly(Object targetObject, String methodName, Object... parameters) {
		Method method = ReflectionUtils.getMethodFor(targetObject, methodName, parameters);
		if(! method.isAccessible()) {
			method.setAccessible(true);
		}
		return _call_method(targetObject, method, parameters);
	}

	public static Object call(Object targetObject, String methodName, Object... parameters) {
		Method method = ReflectionUtils.getMethodFor(targetObject, methodName, parameters);
		return _call_method(targetObject, method, parameters);
	}

	private static Object _call_method(Object targetObject, Method method, Object... parameters) {
		Object returnValue = null;
		try {
			returnValue = method.invoke(targetObject, parameters);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	private ReflectionUtils() {
	}
}