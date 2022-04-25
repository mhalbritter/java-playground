package nativeplayground;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Moritz Halbritter
 */
class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("Class.forName(...)");
		Class<?> aClass = Class.forName(getClassName());
		System.out.println("  " + aClass);

		System.out.println("getMethods()");
		Method[] methods = aClass.getMethods();
		for (Method method : methods) {
			System.out.println("  " + method);
		}

		System.out.println("getDeclaredMethods()");
		Method[] declaredMethods = aClass.getDeclaredMethods();
		for (Method declaredMethod : declaredMethods) {
			System.out.println("  " + declaredMethod);
		}

		System.out.println("getFields()");
		Field[] fields = aClass.getFields();
		for (Field field : fields) {
			System.out.println("  " + field);
		}

		System.out.println("getDeclaredFields()");
		Field[] declaredFields = aClass.getDeclaredFields();
		for (Field declaredField : declaredFields) {
			System.out.println("  " + declaredField);
		}

		System.out.println("getClasses()");
		Class<?>[] classes = aClass.getClasses();
		for (Class<?> clazz : classes) {
			System.out.println("  " + clazz);
		}

		System.out.println("getDeclaredClasses()");
		Class<?>[] declaredClasses = aClass.getDeclaredClasses();
		for (Class<?> declaredClass : declaredClasses) {
			System.out.println("  " + declaredClass);
		}

		System.out.println("getAnnotations()");
		Annotation[] annotations = aClass.getAnnotations();
		for (Annotation annotation : annotations) {
			System.out.println("  " + annotation);
		}

		System.out.println("getDeclaredAnnotations()");
		Annotation[] declaredAnnotations = aClass.getDeclaredAnnotations();
		for (Annotation declaredAnnotation : declaredAnnotations) {
			System.out.println("  " + declaredAnnotation);
		}

		System.out.println("getConstructors()");
		Constructor<?>[] constructors = aClass.getConstructors();
		for (Constructor<?> constructor : constructors) {
			System.out.println("  " + constructor);
		}

		System.out.println("getDeclaredConstructors()");
		Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
		for (Constructor<?> declaredConstructor : declaredConstructors) {
			System.out.println("  " + declaredConstructor);
		}
	}

	private static String getClassName() {
		StringBuilder sb = new StringBuilder();
		sb.append("native").append("play").append("ground").append(".").append("A");
		return sb.toString();
	}
}
