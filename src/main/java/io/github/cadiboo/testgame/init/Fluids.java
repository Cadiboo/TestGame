package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.fluid.FluidProperties;
import io.github.cadiboo.testgame.registry.supplier.FluidSupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class Fluids {

	public static final FluidSupplier<Fluid> AIR = FluidSupplier.of(Location.of("air"));
	public static final FluidSupplier<Fluid> WATER = FluidSupplier.of(Location.of("water"));
	public static final FluidSupplier<Fluid> LAVA = FluidSupplier.of(Location.of("lava"));
	public static final FluidSupplier<Fluid> SULPHUR = FluidSupplier.of(Location.of("sulphur"));

	public static void register(final RegisterEvent<Fluid> event) {
		event.getRegistry().registerAll(
				new Fluid(AIR.registryName, new FluidProperties().setMass(0).air()),
				new Fluid(WATER.registryName, new FluidProperties().setMass(1000)),
				new Fluid(LAVA.registryName, new FluidProperties().setMass(100_000_000)),
				new Fluid(SULPHUR.registryName, new FluidProperties().setMass(32))
		);
	}

}
