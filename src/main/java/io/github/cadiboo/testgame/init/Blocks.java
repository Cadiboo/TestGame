package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.block.AirBlock;
import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.block.BlockProperties;
import io.github.cadiboo.testgame.block.DirtBlock;
import io.github.cadiboo.testgame.block.TurfBlock;
import io.github.cadiboo.testgame.loading.Touch;
import io.github.cadiboo.testgame.registry.RegistrationHelper;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.RegistryObject;

/**
 * @author Cadiboo
 */
@Touch
public final class Blocks {

	private static final RegistrationHelper<Block> BLOCKS = RegistrationHelper.of(TestGame.NAMESPACE, Registries.BLOCKS);

	public static final RegistryObject<AirBlock> AIR = BLOCKS.register("air", $ -> new AirBlock($, new BlockProperties()));
	public static final RegistryObject<Block> STONE = BLOCKS.register("stone", $ -> new Block($, new BlockProperties()));
	public static final RegistryObject<DirtBlock> DIRT = BLOCKS.register("dirt", $ -> new DirtBlock($, new BlockProperties()));
	public static final RegistryObject<TurfBlock> TURF = BLOCKS.register("turf", $ -> new TurfBlock($, new BlockProperties()));
	public static final RegistryObject<Block> GRASS = BLOCKS.register("grass", $ -> new Block($, new BlockProperties()));
	public static final RegistryObject<Block> COAL_ORE = BLOCKS.register("coal_ore", $ -> new Block($, new BlockProperties()));
	public static final RegistryObject<Block> IRON_ORE = BLOCKS.register("iron_ore", $ -> new Block($, new BlockProperties()));
	public static final RegistryObject<Block> GOLD_ORE = BLOCKS.register("gold_ore", $ -> new Block($, new BlockProperties()));
	public static final RegistryObject<Block> DIAMOND_ORE = BLOCKS.register("diamond_ore", $ -> new Block($, new BlockProperties()));
	public static final RegistryObject<Block> EMERALD_ORE = BLOCKS.register("emerald_ore", $ -> new Block($, new BlockProperties()));
	public static final RegistryObject<Block> REDSTONE_ORE = BLOCKS.register("redstone_ore", $ -> new Block($, new BlockProperties()));

}
