package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.block.AirBlock;
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
	public static final BlockSupplier<Block> BLOCK_TO_BE_REPLACED = BlockSupplier.of(Location.of("block_to_be_replaced"));

	public static void register(final RegisterEvent<Block> event) {
		event.getRegistry().registerAll(
				new AirBlock(Location.of("air"), new BlockProperties()),
				new Block(Location.of("stone"), new BlockProperties()),
				new DirtBlock(Location.of("dirt"), new BlockProperties()),
				new TurfBlock(Location.of("turf"), new BlockProperties()),
				new Block(Location.of("grass"), new BlockProperties()),
				new Block(Location.of("coal_ore"), new BlockProperties()),
				new Block(Location.of("iron_ore"), new BlockProperties()),
				new Block(Location.of("gold_ore"), new BlockProperties()),
				new Block(Location.of("diamond_ore"), new BlockProperties()),
				new Block(Location.of("emerald_ore"), new BlockProperties()),
				new Block(Location.of("redstone_ore"), new BlockProperties()),
				new Block(Location.of("block_to_be_replaced"), new BlockProperties())
		);
	}

}
