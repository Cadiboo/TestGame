package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public class ItemSupplier<T extends Item> extends RegistrySupplier<T> {

	public ItemSupplier(final Location registryName, final Location registryRegistryName) {
		super(registryName, registryRegistryName);
	}

	public static <T extends Item> ItemSupplier<T> of(Location registryName) {
		return new ItemSupplier<>(registryName, Location.of("items"));
	}

	@Override
	protected Registry getRegistry() {
		return Registries.ITEMS;
	}

}
