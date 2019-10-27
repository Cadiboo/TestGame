package io.github.cadiboo.testgame.item;

import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public class Item implements RegistryEntry<Item> {

	private final Location registryName;
	private final int maxStackSize;

	public Item(final Location registryName, ItemProperties properties) {
		this.registryName = registryName;
		this.maxStackSize = properties.maxStackSize;
	}

	@Override
	public final Location getRegistryName() {
		return registryName;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}

}
