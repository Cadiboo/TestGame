package io.github.cadiboo.testgame.mods.test;

import io.github.cadiboo.testgame.mods.api.Mod;

/**
 * @author Cadiboo
 */
@Mod(value = TestMod2.MOD_ID, loadBefore = {"this_mod_doesnt_actually_exist", TestMod3.MOD_ID}, loadAfter = TestMod1.MOD_ID)
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
