package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

import java.util.function.Supplier;

/**
 * @author Cadiboo
 */
public class ItemSupplier<T extends Item> extends RegistrySupplier<T, Item> {

	public static final Location REGISTRY = Location.of("item");
	public static final Supplier<Registry<Item>> REGISTRY_SUPPLIER = () -> Registries.ITEMS;

	public ItemSupplier(final Location registryName, final Location registryRegistryName, final Supplier<Registry<Item>> registrySupplier) {
		super(registryName, registryRegistryName, registrySupplier);
	}

	public static <T extends Item> ItemSupplier<T> of(Location registryName) {
		return new ItemSupplier<>(registryName, REGISTRY, REGISTRY_SUPPLIER);
	}

}
