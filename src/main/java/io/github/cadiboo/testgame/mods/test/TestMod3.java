package io.github.cadiboo.testgame.mods.test;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.block.BlockProperties;
import io.github.cadiboo.testgame.block.TestBlock;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.mods.api.Mod;
import io.github.cadiboo.testgame.util.Location;

import java.util.stream.IntStream;

/**
 * @author Cadiboo
 */
@Mod(value = TestMod3.MOD_ID, loadAfter = {TestMod1.MOD_ID, TestMod2.MOD_ID})
public final class TestMod3 {

	public static final String MOD_ID = "register_blocks_test_mod";

	public TestMod3() {
		debug(MOD_ID + ": Mod Initialised");
		TestGame.EVENT_BUS.registerGeneric(Block.class, (RegisterEvent<Block> event) -> {
			IntStream.range(0, 0xFFF).forEach(i ->
					event.getRegistry().register(
							new TestBlock(Location.of("block_" + i), new BlockProperties())
					));
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
