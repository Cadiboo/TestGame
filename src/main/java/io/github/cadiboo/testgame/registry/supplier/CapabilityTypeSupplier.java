package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.capability.CapabilityType;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

import java.util.function.Supplier;

/**
 * @author Cadiboo
 */
public class CapabilityTypeSupplier<T extends CapabilityType<?>> extends RegistrySupplier<T, CapabilityType> {

	public static final Location REGISTRY = Location.of("capability_type");
	public static final Supplier<Registry<CapabilityType>> REGISTRY_SUPPLIER = () -> Registries.CAPABILITY_TYPES;

	private CapabilityTypeSupplier(final Location registryName, final Location registryRegistryName, final Supplier<Registry<CapabilityType>> registrySupplier) {
		super(registryName, registryRegistryName, registrySupplier);
	}

	public static <T extends CapabilityType<?>> CapabilityTypeSupplier<T> of(Location registryName) {
		return new CapabilityTypeSupplier<>(registryName, REGISTRY, REGISTRY_SUPPLIER);
	}

}
