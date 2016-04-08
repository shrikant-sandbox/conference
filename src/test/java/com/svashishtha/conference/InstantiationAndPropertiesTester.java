package com.svashishtha.conference;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.mockito.internal.util.Primitives;
import org.slf4j.LoggerFactory;

public class InstantiationAndPropertiesTester {

	private static final org.slf4j.Logger log = LoggerFactory
			.getLogger(InstantiationAndPropertiesTester.class);

	public static void shouldHaveValidGettersFor(Class<?>... classes)
			throws Exception {
		for (int i = classes.length - 1; i >= 0; i--) {
			Class<?> clazz = classes[i];
			touchProperties(clazz, construct(clazz));
			callToString(clazz);
		}
	}

	private static void touchProperties(Class<?> clazz, Object object)
			throws Exception {
		List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
		List<String> lowercaseFields = new ArrayList<String>();
		for (Field field : fields) {
			lowercaseFields.add(field.getName().toLowerCase());
		}
		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String propertyName = method.getName()
					.replaceFirst("(is|get|set)", "").toLowerCase();
			if (method.getName().startsWith("set")
					&& method.getParameterTypes().length == 1
					&& lowercaseFields.contains(propertyName)) {
				method.invoke(object,
						imposterize(method.getParameterTypes()[0]));
			} else if ((method.getName().startsWith("get") || method.getName()
					.startsWith("is"))
					&& lowercaseFields.contains(propertyName)
					&& method.getParameterTypes().length == 0) {
				method.invoke(object);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T construct(Class<T> clazz) throws Exception {

		Constructor<?>[] constructors = clazz.getConstructors();
		if (constructors.length == 0
				|| Modifier.isAbstract(clazz.getModifiers())) {
			return null;
		}
		Constructor<?> constructor = selectConstructorWithLeastNumberParameters(constructors);
		Class<?>[] parameterTypes = constructor.getParameterTypes();

		Object[] parameters = new Object[parameterTypes.length];
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = imposterize(parameterTypes[i]);
		}
		return (T) constructor.newInstance(parameters);
	}

	/*
	 * Borrowed partly convertToDocument Mockito (EmptyReturnValues.class)
	 */
	private static Object imposterize(Class<?> type) throws Exception {
		if (type.isPrimitive()) {
			return primitiveOf(type);
		} else if (Primitives.isPrimitiveOrWrapper(type)) {
			return Primitives.defaultValueForPrimitiveOrWrapper(type);
		} else if (type == String.class) {
			return "";
		} else if (type == LocalDate.class) {
			return new LocalDate();
		} else if (type == LocalDateTime.class) {
			return new LocalDateTime();
		} else if (type == LocalTime.class) {
			return new LocalTime();
		} else if (type.isEnum()) {
			Method m = type.getMethod("values", new Class[0]);
			Object[] o = (Object[]) m.invoke(null);
			return o[0];
		} else if (type == Integer[].class) {
			return new Integer[] {};
		} else if (type == Integer[][].class) {
			return new Integer[][] {};
		}
		// Let's not care about collections.
		return mock(type);
	}

	private static Object primitiveOf(Class<?> type) {
		if (type == Boolean.TYPE) {
			return false;
		} else if (type == Character.TYPE) {
			return (char) 0;
		} else {
			return 0;
		}
	}

	private static void callToString(Class<?> clazz) throws Exception {
		try {
			Method method = clazz.getDeclaredMethod("toString");
			Object newInstance = construct(clazz);
			method.invoke(newInstance);
		} catch (NoSuchMethodException e) {
			log.info("Ignored missing toString on " + clazz);
		}
	}

	/*
	 * By selecting the constructor with the least number of arguments, the
	 * default constructor is favorite over others. Thereby circumventing the
	 * selection of constructors with hard to mock parameters.
	 */
	private static Constructor<?> selectConstructorWithLeastNumberParameters(
			Constructor<?>[] constructors) {
		Constructor<?> constructor = null;
		for (int i = 0; i < constructors.length; i++) {
			if (constructor == null) {
				constructor = constructors[i];
			} else {
				if (constructor.getParameterTypes().length > constructors[i]
						.getParameterTypes().length) {
					constructor = constructors[i];
				}
			}
		}
		return constructor;
	}

}
