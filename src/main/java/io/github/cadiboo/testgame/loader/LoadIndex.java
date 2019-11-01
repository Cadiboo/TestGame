package io.github.cadiboo.testgame.loader;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.mods.ModLoader;
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
			.runBefore("create_registries")
			.build()
	);

	LoadPhase CREATE_REGISTRIES = add(new LoadPhase.Builder("create_registries")
			.onRun(() -> Registries.get(null))
			.runBefore("register_registry_entries")
			.build()
	);

	LoadPhase REGISTER_REGISTRY_ENTRIES = add(new LoadPhase.Builder("register_registry_entries")
			.onRun(() -> Registries.forEach((location, registry) -> registry.load()))
			.runBefore("reload_registries")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES = add(new LoadPhase.Builder("reload_registries")
			.onRun(() -> Registries.forEach((location, registry) -> registry.reload()))
			.runBefore("reload_registries_again")
			.build()
	);

	LoadPhase RELOAD_REGISTRIES_AGAIN = add(new LoadPhase.Builder("reload_registries_again")
			.onRun(() -> Registries.forEach((location, registry) -> registry.reload()))
			.build()
	);

	static void touch() {
		// Yay, static initialisers
	}

}
