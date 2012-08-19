package daveayan.mirage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class ReflectionUtils {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Object objectForClassForcibly(Class<?> clazz) {
		try {
			Constructor<?> c = clazz.getDeclaredConstructor();
			c.setAccessible(true);
			return c.newInstance();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Object objectFor(String className) {
		return objectForClassForcibly(asClass(className));
	}

	public static Method getMethodFor(Object targetObject, String methodName, Object[] parameters) {
		return getMethodFor(targetObject.getClass(), methodName, parameters);
	}

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

	public static Object getInstanceOfClassForcibly(String className) {
		return getInstanceOfClassForcibly(getClassForClassName(className));
	}

	public static Object getInstanceOfClassForcibly(Class<?> clazz) {
		try {
			Constructor<?> c = clazz.getDeclaredConstructor();
			c.setAccessible(true);
			return c.newInstance();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Object getInstanceOfClass(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Class<?> getClassForClassName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean canCast(Object to, Object from) {
		if (to == null || from == null)
			return false;
		return canCast(to.getClass(), from.getClass());
	}

	public static boolean canCast(Object to, Class<?> from) {
		if (to == null || from == null)
			return false;
		return canCast(to.getClass(), from);
	}

	public static boolean canCast(Class<?> to, Object from) {
		if (to == null || from == null)
			return false;
		return canCast(to, from.getClass());
	}

	public static boolean canCast(Class<?> to, Class<?> from) {
		if (to == null || from == null)
			return false;
		return to.isAssignableFrom(from);
	}

	private ReflectionUtils() {
	}
}