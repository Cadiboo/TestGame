package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.fluid.FluidProperties;
import io.github.cadiboo.testgame.loading.Touch;
import io.github.cadiboo.testgame.registry.RegistrationHelper;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.RegistryObject;

/**
 * @author Cadiboo
 */
@Touch
public final class Fluids {

	private static final RegistrationHelper<Fluid> FLUIDS = RegistrationHelper.of(TestGame.NAMESPACE, Registries.FLUIDS);

	public static final RegistryObject<Fluid> AIR = FLUIDS.register("air", $ -> new Fluid($, new FluidProperties().setMass(0).air()));
	public static final RegistryObject<Fluid> WATER = FLUIDS.register("water", $ -> new Fluid($, new FluidProperties().setMass(1000)));
	public static final RegistryObject<Fluid> LAVA = FLUIDS.register("lava", $ -> new Fluid($, new FluidProperties().setMass(100_000_000)));
	public static final RegistryObject<Fluid> SULPHUR = FLUIDS.register("sulphur", $ -> new Fluid($, new FluidProperties().setMass(32)));

}
