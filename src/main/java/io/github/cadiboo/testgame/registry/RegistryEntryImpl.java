package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.util.Location;

import java.util.Objects;

/**
 * @author Cadiboo
 */
public class RegistryEntryImpl<T> implements RegistryEntry<T> {

	private final Location registryName;

	public RegistryEntryImpl(final Location registryName) {
		this.registryName = Objects.requireNonNull(registryName, "registryName cannot be null!");
	}

	@Override
	public final Location getRegistryName() {
		return registryName;
	}

}
