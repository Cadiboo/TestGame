package io.github.cadiboo.testgame.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author Cadiboo
 */
public final class UnsafeHelper {

	private static final Unsafe unsafe;
	static {
		unsafe = findUnsafe();
	}

	public static Unsafe getUnsafe() {
		return unsafe;
	}

	private static Unsafe findUnsafe() {
		for (final Field field : Unsafe.class.getDeclaredFields()) {
			if (field.getType() != Unsafe.class)
				continue;
			field.setAccessible(true);
			try {
				return (Unsafe) field.get(null);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		throw new RuntimeException("Couldn't find unsafe instance");
	}

	public static <T> T allocateInstance(Class<T> clazz) throws InstantiationException {
		return (T) getUnsafe().allocateInstance(clazz);
	}

}
