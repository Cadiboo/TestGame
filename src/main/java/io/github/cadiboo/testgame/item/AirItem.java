package io.github.cadiboo.testgame.item;

import io.github.cadiboo.testgame.block.AirBlock;
import io.github.cadiboo.testgame.init.Blocks;

/**
 * @author Cadiboo
 */
public class AirItem extends BlockItem<AirBlock> {

	public AirItem(final ItemProperties properties) {
		super(Blocks.AIR, properties);
	}

}
