package io.github.cadiboo.testgame.item;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.init.Blocks;
import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.registry.supplier.BlockSupplier;
import io.github.cadiboo.testgame.util.Location;

import java.util.HashMap;

/**
 * @author Cadiboo
 */
public class Item extends RegistryEntry<Item> {

	private final int maxStackSize;
	private final HashMap<String, ?> extraData;

	public Item(final Location registryName, ItemProperties properties) {
		super(registryName);
		this.maxStackSize = properties.maxStackSize;
		this.extraData = properties.extraData.isEmpty() ? null : properties.extraData;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}

	@SuppressWarnings("unchecked")
	public <T> T getData(final String key) {
		return extraData == null ? null : (T) extraData.get(key);
	}

	public Block asBlock() {
		if (!(this instanceof BlockItem))
			return Blocks.AIR.get();
		return ((BlockSupplier<?>) ((BlockItem) this).getBlock()).get();
	}

}
