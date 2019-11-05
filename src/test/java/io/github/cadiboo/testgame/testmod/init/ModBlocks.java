package io.github.cadiboo.testgame.testmod.init;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.block.BlockProperties;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.init.Blocks;
import io.github.cadiboo.testgame.registry.supplier.BlockSupplier;
import io.github.cadiboo.testgame.util.Location;

import static io.github.cadiboo.testgame.testmod.TestMod.MOD_ID;

/**
 * @author Cadiboo
 */
public class ModBlocks {

	public static final BlockSupplier<Block> NEW_BLOCK = BlockSupplier.of(Location.of(MOD_ID, "new_block"));

	public static void register(final RegisterEvent<Block> event) {
		event.getRegistry().registerAll(
				new Block(Blocks.STONE.registryName, new BlockProperties()),
				new Block(NEW_BLOCK.registryName, new BlockProperties())
		);
	}

}
