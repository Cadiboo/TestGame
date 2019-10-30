package io.github.cadiboo.testgame.item;

import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public class Item extends RegistryEntry<Item> {

	private final int maxStackSize;

	public Item(final Location registryName, ItemProperties properties) {
		super(registryName);
		this.maxStackSize = properties.maxStackSize;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}

}
