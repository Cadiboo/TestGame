package io.github.cadiboo.testgame.entity;

import io.github.cadiboo.testgame.util.math.Vec3d;
import io.github.cadiboo.testgame.world.World;

/**
 * @author Cadiboo
 */
public class Entity {

	public final Vec3d pos;
	public final Vec3d direction = new Vec3d(1, 0, 0);
	public World world;

	public Entity(final World world, final Vec3d pos) {
		validateCaller();
		this.world = world;
		this.pos = pos;
	}

	private void validateCaller() {
		final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		// 0 -> Thread
		// 1 -> this
		// 2 -> method that called this (caller method)
		// 3 -> method that called the caller method
		try {
			for (int i = 3; i < stackTrace.length; i++) {
				final StackTraceElement element = stackTrace[i];
				if (element.getMethodName().startsWith("<"))
					continue;
				Class<?> c = Class.forName(element.getClassName());
				if (c.isAssignableFrom(Entity.class))
					continue;
				if (c == EntityType.class)
					return;
				throw new IllegalStateException("Do not directly instantiate Entities!");
			}
			throw new NullPointerException();
		} catch (NullPointerException | ArrayIndexOutOfBoundsException | ClassNotFoundException e) {
			// Not possible
			throw new RuntimeException("Couldn't find a class from the stacktrace... WTF");
		}
	}

	protected void setDirection(final Vec3d newDirection) {
		direction.x = newDirection.x;
		direction.y = newDirection.y;
		direction.z = newDirection.z;
	}

}
