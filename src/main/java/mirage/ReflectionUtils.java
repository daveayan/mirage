package mirage;

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

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class ReflectionUtils {
	public static boolean objectIsOfType(Class<?> actualClass, Class<?> expectedClass) {
		return actualClass.getName().trim().equals(expectedClass.getName().trim());
	}
	
	public static boolean objectIsOfType(Object object, Class<?> expectedClass) {
		return object.getClass().getName().trim().equals(expectedClass.getName().trim());
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