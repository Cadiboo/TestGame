package io.github.cadiboo.testgame.mods.test;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.event.registry.RegistryLoadedEvent;
import io.github.cadiboo.testgame.mods.api.Mod;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistryEntry;

/**
 * @author Cadiboo
 */
@Mod(TestMod2.MOD_ID)
public final class TestMod2 {

	public static final String MOD_ID = "second_test_mod";

	public TestMod2() {
//		debug(MOD_ID + ": Mod Initialised");
//		TestGame.EVENT_BUS.register((RegistryLoadedEvent<RegistryEntry<?>> event) -> {
//			final Registry<?> registry = event.getRegistry();
//			debug("Registry \"" + registry.registryName + "\" loaded");
//			registry.forEach((entryLocation, entry) -> {
//				debug("  " + entryLocation + " = " + entry);
//			});
//			debug("");
//		});
//		debug(MOD_ID + ": Added registry load event");
	}

	private void debug(Object... msgs) {
		for (final Object msg : msgs)
			debug(msg);
	}

	private void debug(Object msg) {
		System.out.println("[" + MOD_ID + "]: " + msg);
	}

}
