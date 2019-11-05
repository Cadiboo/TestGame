package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.util.Location;

import java.util.Objects;

/**
 * @author Cadiboo
 */
public class RegistryEntry<T extends RegistryEntry> {

	private final Location registryName;
	private char id;

	public RegistryEntry(final Location registryName) {
		this.registryName = Objects.requireNonNull(registryName, "registryName cannot be null!");
	}

	public final Location getRegistryName() {
		return registryName;
	}

	public final char getId() {
		return id;
	}

	void setId(final int id) {
		if (id > Registry.MAX_ID)
			throw new IllegalArgumentException("Exceeded max ID! Max: " + (int) Registry.MAX_ID + ", requested: " + id);
		this.id = (char) id;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + registryName + "@" + (int) id + "}";
	}

}
