package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.capability.CapabilityType;
import io.github.cadiboo.testgame.energy.EnergyStorage;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.heat.HeatLevel;
import io.github.cadiboo.testgame.inventory.Inventory;
import io.github.cadiboo.testgame.item.ItemStack;
import io.github.cadiboo.testgame.registry.supplier.CapabilityTypeSupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class CapabilityTypes {

	public static final CapabilityTypeSupplier<CapabilityType<Inventory>> INVENTORY = CapabilityTypeSupplier.of(Location.of("inventory"));
	public static final CapabilityTypeSupplier<CapabilityType<EnergyStorage>> ENERGY_STORAGE = CapabilityTypeSupplier.of(Location.of("energy_storage"));
	public static final CapabilityTypeSupplier<CapabilityType<HeatLevel>> HEAT_LEVEL = CapabilityTypeSupplier.of(Location.of("heat_level"));

	public static void register(RegisterEvent<CapabilityType> event) {
		event.getRegistry().registerAll(
				new CapabilityType<>(INVENTORY.registryName, () -> new Inventory(new ItemStack[0])),
				new CapabilityType<>(ENERGY_STORAGE.registryName, () -> new EnergyStorage(0)),
				new CapabilityType<>(HEAT_LEVEL.registryName, () -> new HeatLevel(0))
		);
	}

}
