package io.github.cadiboo.testgame.testmod;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.api.Mod;
import io.github.cadiboo.testgame.event.registry.RegistryLoadedEvent;
import io.github.cadiboo.testgame.registry.Registry;

/**
 * @author Cadiboo
 */
@Mod(TestMod.MOD_ID)
public final class TestMod {

	public static final String MOD_ID = "test_mod";

	public TestMod() {
		TestGame.EVENT_BUS.register(this::debugRegistry);
	}

//	private void maxRegistryIds(final RegisterEvent<Block> event) {
//		final Registry<Block> registry = event.getRegistry();
//		IntStream.range(0, Registry.MAX_ID - registry.size()).forEach(i -> registry.register(
//				new Block(Location.of("block_" + i), new BlockProperties())
//		));
//		try {
//			registry.register(new Block(Location.of("max_id"), new BlockProperties()));
//		} catch (Exception e) {
////			debug(e);
//		}
//	}

	private void debugRegistry(final RegistryLoadedEvent<?> event) {
		final Registry<?> registry = event.getRegistry();
		debug("Registry \"" + registry.getRegistryName() + "\" loaded");
		registry.forEach((entryLocation, entry) -> debug("  " + entryLocation + " = " + entry));
		debug("");
	}

	private void debug(Object msg) {
		System.out.println("[" + MOD_ID + "]: " + msg);
	}

}
