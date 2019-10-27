package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.block.BlockProperties;
import io.github.cadiboo.testgame.block.TestBlock;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.registry.supplier.BlockSupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class Blocks {

	public static final BlockSupplier<TestBlock> TEST_NORMAL_BLOCK = BlockSupplier.of(Location.of("test_normal_block"));
	public static final BlockSupplier<TestBlock> TEST_TEST_BLOCK = BlockSupplier.of(Location.of("test_test_block"));
	public static final BlockSupplier<Block> NORMAL_BLOCK = BlockSupplier.of(Location.of("normal_block"));
	public static final BlockSupplier<Block> TEST_BLOCK = BlockSupplier.of(Location.of("test_block"));

	public static void register(final RegisterEvent<Block> event) {
		event.getRegistry().registerAll(
				new TestBlock(Location.of("test_normal_block"), new BlockProperties()),
				new TestBlock(Location.of("test_test_block"), new BlockProperties()),
				new Block(Location.of("normal_block"), new BlockProperties()),
				new Block(Location.of("test_block"), new BlockProperties())
		);
	}

}
