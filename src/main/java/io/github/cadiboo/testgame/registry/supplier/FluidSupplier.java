package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

import java.util.function.Supplier;

/**
 * @author Cadiboo
 */
public class FluidSupplier<T extends Fluid> extends RegistrySupplier<T, Fluid> {

	public static final Location REGISTRY = Location.of("fluid");
	public static final Supplier<Registry<Fluid>> REGISTRY_SUPPLIER = () -> Registries.FLUIDS;

	private FluidSupplier(final Location registryName, final Location registryRegistryName, final Supplier<Registry<Fluid>> registrySupplier) {
		super(registryName, registryRegistryName, registrySupplier);
	}

	public static <T extends Fluid> FluidSupplier<T> of(Location registryName) {
		return new FluidSupplier<>(registryName, REGISTRY, REGISTRY_SUPPLIER);
	}

}
