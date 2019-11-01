package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.capability.CapabilityType;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public class CapabilitySupplier<T extends CapabilityType<?>> extends RegistrySupplier<T> {

	public static final Location REGISTRY = Location.of("capability_type");

	private CapabilitySupplier(final Location registryName, final Location registryRegistryName) {
		super(registryName, registryRegistryName);
	}

	public static <T extends CapabilityType<?>> CapabilitySupplier<T> of(Location registryName) {
		return new CapabilitySupplier<>(registryName, REGISTRY);
	}

	@Override
	protected Registry getRegistry() {
		return Registries.ENTITY_TYPES;
	}

}
