package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public class BlockEntitySupplier<T extends BlockEntityType> extends RegistrySupplier<T> {

	public static final Location REGISTRY = Location.of("block_entity_type");

	private BlockEntitySupplier(final Location registryName, final Location registryRegistryName) {
		super(registryName, registryRegistryName);
	}

	public static <T extends BlockEntityType> BlockEntitySupplier<T> of(Location registryName) {
		return new BlockEntitySupplier<>(registryName, REGISTRY);
	}

	@Override
	protected Registry getRegistry() {
		return Registries.BLOCK_ENTITY_TYPES;
	}

}
