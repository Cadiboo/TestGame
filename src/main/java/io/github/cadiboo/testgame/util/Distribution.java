package io.github.cadiboo.testgame.util;

import java.util.Objects;

/**
 * @author Cadiboo
 */
public enum Distribution {

	CLIENT("client"),
	SERVER("server"),
	;

	private final String name;

	Distribution(final String name) {
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
