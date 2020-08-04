package io.github.cadiboo.testgame.loader;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.mod.ModLoader;
import io.github.cadiboo.testgame.registry.Registries;

import static io.github.cadiboo.testgame.loader.Loader.add;

/**
 * @author Cadiboo
 */
public interface LoadIndex {

	LoadPhase REGISTER_LISTENERS = add(new LoadPhase.Builder("register_listeners")
			.onRun(TestGame::registerListeners)
			.runBefore("load_mods")
			.build()
	);

	LoadPhase LOAD_MODS = add(new LoadPhase.Builder("load_mods")
			.onRun(ModLoader::findAndLoadMods)
			.runBefore("register_registry_entries")
			.build()
	);

	LoadPhase REGISTER_REGISTRY_ENTRIES = add(new LoadPhase.Builder("register_registry_entries")
			.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
			.runAfter("load_mods")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES_0 = add(new LoadPhase.Builder("reload_registries_0")
		.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
			.runAfter("register_registry_entries")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES_1 = add(new LoadPhase.Builder("reload_registries_1")
			.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
			.runAfter("reload_registries_0")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES_2 = add(new LoadPhase.Builder("reload_registries_2")
			.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
			.runAfter("reload_registries_1")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES_3 = add(new LoadPhase.Builder("reload_registries_3")
			.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
			.runAfter("reload_registries_2")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES_4 = add(new LoadPhase.Builder("reload_registries_4")
			.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
			.runAfter("reload_registries_3")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES_5 = add(new LoadPhase.Builder("reload_registries_5")
			.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
			.runAfter("reload_registries_4")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES_6 = add(new LoadPhase.Builder("reload_registries_6")
			.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
			.runAfter("reload_registries_5")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES_7 = add(new LoadPhase.Builder("reload_registries_7")
			.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
			.runAfter("reload_registries_6")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES_8 = add(new LoadPhase.Builder("reload_registries_8")
			.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
			.runAfter("reload_registries_7")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES_9 = add(new LoadPhase.Builder("reload_registries_9")
			.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
			.runAfter("reload_registries_8")
			.build()
	);

	static void touch() {
		// Yay, static initialisers
	}

}
