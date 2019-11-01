package io.github.cadiboo.testgame.capability;

import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public class CapabilityType<T extends Capability<?>> extends RegistryEntry<CapabilityType> {

	public CapabilityType(final Location registryName) {
		super(registryName);
	}

}
