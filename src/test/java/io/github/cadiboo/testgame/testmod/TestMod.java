package io.github.cadiboo.testgame.testmod;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.api.Mod;
import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.block.BlockProperties;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.event.registry.RegistryLoadedEvent;
import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.testmod.init.ModBlocks;
import io.github.cadiboo.testgame.testmod.init.ModItems;
import io.github.cadiboo.testgame.util.Location;

import java.util.stream.IntStream;

/**
 * @author Cadiboo
 */
@Mod(TestMod.MOD_ID)
public final class TestMod {

	public static final String MOD_ID = "test_mod";

	public TestMod() {
		TestGame.EVENT_BUS.registerGeneric(Block.class, ModBlocks::register);
//		TestGame.EVENT_BUS.registerGeneric(Block.class, this::maxRegistryIds);
		TestGame.EVENT_BUS.registerGeneric(Item.class, ModItems::register);
//		TestGame.EVENT_BUS.register(this::debugRegistry);
	}

	private void doNothing(final RegisterEvent<Block> event) {
	}

	private void maxRegistryIds(final RegisterEvent<Block> event) {
		final Registry<Block> registry = event.getRegistry();
		IntStream.range(0, Registry.MAX_ID - registry.size()).forEach(i -> registry.register(
				new Block(Location.of("block_" + i), new BlockProperties())
		));
		try {
			registry.register(new Block(Location.of("max_id"), new BlockProperties()));
		} catch (Exception e) {
//			debug(e);
		}
	}

	private void debugRegistry(final RegistryLoadedEvent<RegistryEntry<?>> event) {
		final Registry<?> registry = event.getRegistry();
		debug("Registry \"" + registry.registryName + "\" loaded");
		registry.forEach((entryLocation, entry) ->
				debug("  " + entryLocation + " = " + entry));
		debug("");
	}

	private void debug(Object msg) {
		System.out.println("[" + MOD_ID + "]: " + msg);
	}

}
