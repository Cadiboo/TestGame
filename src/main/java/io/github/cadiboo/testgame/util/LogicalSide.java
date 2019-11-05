package io.github.cadiboo.testgame.util;

import java.util.Objects;

/**
 * @author Cadiboo
 */
public enum LogicalSide {

	LOGICAL_CLIENT("logical_client"),
	LOGICAL_SERVER("logical_server"),
	;

	private final String name;

	LogicalSide(final String name) {
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
