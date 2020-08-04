package io.github.cadiboo.testgame.testmod.init;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.block.BlockProperties;
import io.github.cadiboo.testgame.init.Blocks;
import io.github.cadiboo.testgame.loading.Touch;
import io.github.cadiboo.testgame.registry.RegistrationHelper;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.RegistryObject;

import static io.github.cadiboo.testgame.testmod.TestMod.MOD_ID;

/**
 * @author Cadiboo
 */
@Touch
public class ModBlocks {

	private static final RegistrationHelper<Block> BLOCKS = RegistrationHelper.of(MOD_ID, Registries.BLOCKS);

	public static final RegistryObject<Block> NEW_BLOCK = BLOCKS.register("new_block", $ -> new Block($, new BlockProperties()));
	public static final RegistryObject<Block> STONE_OVERRIDE = BLOCKS.register(Blocks.STONE.getRegistryName(), $ -> new Block($, new BlockProperties()));

}
