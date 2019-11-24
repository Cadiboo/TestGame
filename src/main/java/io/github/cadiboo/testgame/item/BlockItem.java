package io.github.cadiboo.testgame.item;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.registry.supplier.BlockSupplier;

/**
 * @author Cadiboo
 */
public class BlockItem<T extends Block> extends Item {

	private final BlockSupplier block;

	public BlockItem(final BlockSupplier block, final ItemProperties properties) {
		super(block.registryName, properties);
		this.block = block;
	}

	public BlockSupplier getBlock() {
		return block;
	}

}
