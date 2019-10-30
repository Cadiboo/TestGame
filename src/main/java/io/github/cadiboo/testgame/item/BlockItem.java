package io.github.cadiboo.testgame.item;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.registry.supplier.BlockSupplier;

/**
 * @author Cadiboo
 */
public class BlockItem<T extends Block> extends Item {

	private final BlockSupplier<T> block;

	public BlockItem(final BlockSupplier<T> block, final ItemProperties properties) {
		super(block.registryName, properties);
		this.block = block;
	}

	public BlockSupplier<T> getBlock() {
		return block;
	}

}
