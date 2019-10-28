package io.github.cadiboo.testgame.mods.test;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.block.BlockProperties;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.mods.api.Mod;
import io.github.cadiboo.testgame.registry.supplier.BlockSupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
@Mod(TestMod1.MOD_ID)
public final class TestMod1 {

	public static final String MOD_ID = "testmod1";

	private static final BlockSupplier<Block> BLOCK_TO_BE_REPLACED = BlockSupplier.of(Location.of("block_to_be_replaced"));
	private static final BlockSupplier<Block> TEST_MOD_BLOCK = BlockSupplier.of(Location.of(MOD_ID, "test_mod_block"));

	public TestMod1() {
		debug(MOD_ID + ": Mod Initialised");
		TestGame.EVENT_BUS.registerGeneric(Block.class, (RegisterEvent<Block> event) -> {
			debug(TEST_MOD_BLOCK.registryName);
			debug(TEST_MOD_BLOCK.get());
			debug(BLOCK_TO_BE_REPLACED.registryName);
			debug(BLOCK_TO_BE_REPLACED.get());
			event.getRegistry().registerAll(
					new Block(TEST_MOD_BLOCK.registryName, new BlockProperties()
							.setConductivity(1)
							.setHardness(1)
					),
					new Block(BLOCK_TO_BE_REPLACED.registryName, new BlockProperties()
							.setConductivity(Integer.MAX_VALUE)
							.setHardness(Integer.MAX_VALUE)
					)
			);
			debug(TEST_MOD_BLOCK.registryName);
			debug(TEST_MOD_BLOCK.get());
			debug(BLOCK_TO_BE_REPLACED.registryName);
			debug(BLOCK_TO_BE_REPLACED.get());
		});
		debug(MOD_ID + ": Added block registry event");
	}

	private void debug(Object... msgs) {
		for (final Object msg : msgs)
			debug(msg);
	}

	private void debug(Object msg) {
		System.out.println("[" + MOD_ID + "]: " + msg);
	}

}
