package io.github.cadiboo.testgame.client;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.client.color.BlockColorHandler;
import io.github.cadiboo.testgame.client.color.FluidColorHandler;
import io.github.cadiboo.testgame.loader.LoadPhase;
import io.github.cadiboo.testgame.loader.Loader;
import io.github.cadiboo.testgame.registry.Registries;

/**
 * @author Cadiboo
 */
public class ClientGame extends TestGame {

	public ClientGame() {
	}

	@Override
	protected void preInitialise() {
		super.preInitialise();
		Loader.add(new LoadPhase.Builder("init_block_colors")
				.onRun(BlockColorHandler::init)
				.runAfter("register_registry_entries")
				.build()
		);
		Loader.add(new LoadPhase.Builder("init_fluid_colors")
				.onRun(FluidColorHandler::init)
				.runAfter("register_registry_entries")
				.build()
		);
		Loader.add(new LoadPhase.Builder("reload_registries_10")
				.onRun(() -> Registries.REGISTRIES.forEach((location, registry) -> registry.load()))
				.runAfter("reload_registries_9")
				.runBefore("init_block_colors")
				.runBefore("init_fluid_colors")
				.build()
		);
	}

}
