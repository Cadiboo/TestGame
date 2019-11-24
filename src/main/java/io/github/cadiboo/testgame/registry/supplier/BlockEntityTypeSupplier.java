package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

import java.util.function.Supplier;

/**
 * @author Cadiboo
 */
public class BlockEntityTypeSupplier<T extends BlockEntityType> extends RegistrySupplier<T, BlockEntityType> {

	public static final Location REGISTRY = Location.of("block_entity_type");
	public static final Supplier<Registry<BlockEntityType>> REGISTRY_SUPPLIER = () -> Registries.BLOCK_ENTITY_TYPES;

	private BlockEntityTypeSupplier(final Location registryName, final Location registryRegistryName, final Supplier<Registry<BlockEntityType>> registrySupplier) {
		super(registryName, registryRegistryName, registrySupplier);
	}

	public static <T extends BlockEntityType> BlockEntityTypeSupplier<T> of(Location registryName) {
		return new BlockEntityTypeSupplier<>(registryName, REGISTRY, REGISTRY_SUPPLIER);
	}

}
