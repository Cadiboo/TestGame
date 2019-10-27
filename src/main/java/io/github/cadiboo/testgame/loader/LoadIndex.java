package io.github.cadiboo.testgame.loader;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.mods.ModLoader;
import io.github.cadiboo.testgame.registry.Registries;

import static io.github.cadiboo.testgame.loader.Loader.add;

/**
 * @author Cadiboo
 */
public interface LoadIndex {

	Loader.LoadEntry LOAD_MODS = add(ModLoader::init);

	Loader.LoadEntry REGISTER_LISTENERS = add(TestGame::init);

	Loader.LoadEntry CREATE_REGISTRIES = add(() -> Registries.get(null));

	Loader.LoadEntry REGISTER_OBJECTS = add(() -> Registries.forEach((location, registry) -> registry.load()));

	Loader.LoadEntry RELOAD_REGISTRIES = add(() -> Registries.forEach((location, registry) -> registry.reload()));

	Loader.LoadEntry RELOAD_REGISTRIES_AGAIN = add(() -> Registries.forEach((location, registry) -> registry.reload()));

	static void init() {

	}

}
