package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.capability.CapabilityType;
import io.github.cadiboo.testgame.energy.EnergyStorage;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.inventory.Inventory;
import io.github.cadiboo.testgame.registry.supplier.CapabilitySupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class CapabilityTypes {

	public static final CapabilitySupplier<CapabilityType<Inventory>> INVENTORY = CapabilitySupplier.of(Location.of("inventory"));
	public static final CapabilitySupplier<CapabilityType<EnergyStorage>> ENERGY_STORAGE = CapabilitySupplier.of(Location.of("energy_storage"));

	public static void register(RegisterEvent<CapabilityType> event) {
		event.getRegistry().registerAll(
				new CapabilityType<>(INVENTORY.registryName),
				new CapabilityType<>(ENERGY_STORAGE.registryName)
		);
	}

}
