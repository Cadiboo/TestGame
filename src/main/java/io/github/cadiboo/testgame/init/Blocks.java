package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.block.BlockProperties;
import io.github.cadiboo.testgame.block.DirtBlock;
import io.github.cadiboo.testgame.block.TurfBlock;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.registry.supplier.BlockSupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class Blocks {

	public static final BlockSupplier<Block> AIR = BlockSupplier.of(Location.of("air"));
	public static final BlockSupplier<Block> STONE = BlockSupplier.of(Location.of("stone"));
	public static final BlockSupplier<Block> DIRT = BlockSupplier.of(Location.of("dirt"));
	public static final BlockSupplier<Block> TURF = BlockSupplier.of(Location.of("turf"));
	public static final BlockSupplier<Block> GRASS = BlockSupplier.of(Location.of("grass"));
	public static final BlockSupplier<Block> COAL_ORE = BlockSupplier.of(Location.of("coal_ore"));
	public static final BlockSupplier<Block> IRON_ORE = BlockSupplier.of(Location.of("iron_ore"));
	public static final BlockSupplier<Block> GOLD_ORE = BlockSupplier.of(Location.of("gold_ore"));
	public static final BlockSupplier<Block> DIAMOND_ORE = BlockSupplier.of(Location.of("diamond_ore"));
	public static final BlockSupplier<Block> EMERALD_ORE = BlockSupplier.of(Location.of("emerald_ore"));
	public static final BlockSupplier<Block> REDSTONE_ORE = BlockSupplier.of(Location.of("redstone_ore"));

	public static void register(final RegisterEvent<Block> event) {
		event.getRegistry().registerAll(
				new Block(AIR.registryName, new BlockProperties()),
				new Block(STONE.registryName, new BlockProperties()),
				new DirtBlock(DIRT.registryName, new BlockProperties()),
				new TurfBlock(TURF.registryName, new BlockProperties()),
				new Block(GRASS.registryName, new BlockProperties()),
				new Block(COAL_ORE.registryName, new BlockProperties()),
				new Block(IRON_ORE.registryName, new BlockProperties()),
				new Block(GOLD_ORE.registryName, new BlockProperties()),
				new Block(DIAMOND_ORE.registryName, new BlockProperties()),
				new Block(EMERALD_ORE.registryName, new BlockProperties()),
				new Block(REDSTONE_ORE.registryName, new BlockProperties())
		);
	}

}
