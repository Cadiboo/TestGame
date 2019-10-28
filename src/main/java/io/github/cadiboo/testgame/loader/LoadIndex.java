package io.github.cadiboo.testgame.loader;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.mods.ModLoader;
import io.github.cadiboo.testgame.registry.Registries;

import static io.github.cadiboo.testgame.loader.Loader.add;

/**
 * @author Cadiboo
 */
public interface LoadIndex {

	Loader.LoadEntry START = add("Start", () -> System.out.println("Starting game..."));

	Loader.LoadEntry REGISTER_LISTENERS = add("Register Listeners", TestGame::registerListeners);

	Loader.LoadEntry LOAD_MODS = add("Load Mods", ModLoader::findAndLoadMods);

	Loader.LoadEntry CREATE_REGISTRIES = add("Create Registries", () -> Registries.get(null));

	Loader.LoadEntry REGISTER_REGISTRY_ENTRIES = add("Register Registry Entries", () -> Registries.forEach((location, registry) -> registry.load()));

	Loader.LoadEntry RELOAD_REGISTRIES = add("Reload Registries", () -> Registries.forEach((location, registry) -> registry.reload()));

	Loader.LoadEntry RELOAD_REGISTRIES_AGAIN = add("Reload Registries Again", () -> Registries.forEach((location, registry) -> registry.reload()));

	Loader.LoadEntry END = add("End", () -> System.out.println("Finished loading game..."));

	static void init() {
		// Yay, static initialisers
	}

}
