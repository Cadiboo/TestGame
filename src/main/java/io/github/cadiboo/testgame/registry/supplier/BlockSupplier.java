package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public class BlockSupplier<T extends Block> extends RegistrySupplier<T> {

	private BlockSupplier(final Location registryName, final Location registryRegistryName) {
		super(registryName, registryRegistryName);
	}

	public static <T extends Block> BlockSupplier<T> of(Location registryName) {
		return new BlockSupplier<>(registryName, Location.of("block"));
	}

	@Override
	protected Registry getRegistry() {
		return Registries.BLOCKS;
	}

}
