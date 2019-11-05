package io.github.cadiboo.testgame.util;

import java.util.Objects;

/**
 * @author Cadiboo
 */
public enum Ordering {

	/**
	 * Logically the same as "either"
	 * Means that the mod doesn't care if it loads before or after
	 */
	NONE("none"),
	BEFORE("before"),
	AFTER("after"),
	;

	private final String name;

	Ordering(final String name) {
		this.name = Objects.requireNonNull(name, "name cannot be null!");
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
