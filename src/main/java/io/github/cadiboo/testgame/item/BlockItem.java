package io.github.cadiboo.testgame.item;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.registry.RegistryObject;

/**
 * @author Cadiboo
 */
public class BlockItem<T extends Block> extends Item {

	private final RegistryObject<T> block;

	public BlockItem(final RegistryObject<T> block, final ItemProperties properties) {
		super(block.getRegistryName(), properties);
		this.block = block;
	}

	public RegistryObject<T> getBlock() {
		return block;
	}

}
