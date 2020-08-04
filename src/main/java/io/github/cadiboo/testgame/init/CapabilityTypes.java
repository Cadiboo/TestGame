package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.capability.CapabilityType;
import io.github.cadiboo.testgame.energy.EnergyStorage;
import io.github.cadiboo.testgame.heat.HeatLevel;
import io.github.cadiboo.testgame.inventory.Inventory;
import io.github.cadiboo.testgame.item.ItemStack;
import io.github.cadiboo.testgame.loading.Touch;
import io.github.cadiboo.testgame.registry.RegistrationHelper;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.RegistryObject;

/**
 * @author Cadiboo
 */
@Touch
public final class CapabilityTypes {

	private static final RegistrationHelper<CapabilityType<?>> CAPABILITY_TYPES = RegistrationHelper.of(TestGame.NAMESPACE, Registries.CAPABILITY_TYPES);

	public static final RegistryObject<CapabilityType<Inventory>> INVENTORY = CAPABILITY_TYPES.register("inventory", $ -> new CapabilityType<>($, () -> new Inventory(new ItemStack[0])));
	public static final RegistryObject<CapabilityType<EnergyStorage>> ENERGY_STORAGE = CAPABILITY_TYPES.register("energy_storage", $ -> new CapabilityType<>($, () -> new EnergyStorage(0)));
	public static final RegistryObject<CapabilityType<HeatLevel>> HEAT_LEVEL = CAPABILITY_TYPES.register("heat_level", $ -> new CapabilityType<>($, () -> new HeatLevel(0)));

}
