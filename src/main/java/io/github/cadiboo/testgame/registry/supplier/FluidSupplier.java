package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public class FluidSupplier<T extends Fluid> extends RegistrySupplier<T> {

	private FluidSupplier(final Location registryName, final Location registryRegistryName) {
		super(registryName, registryRegistryName);
	}

	public static <T extends Fluid> FluidSupplier<T> of(Location registryName) {
		return new FluidSupplier<>(registryName, Location.of("fluid"));
	}

	@Override
	protected Registry getRegistry() {
		return Registries.FLUIDS;
	}

}
