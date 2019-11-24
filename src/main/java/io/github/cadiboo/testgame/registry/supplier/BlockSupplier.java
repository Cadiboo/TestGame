package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

import java.util.function.Supplier;

/**
 * @author Cadiboo
 */
public class BlockSupplier<T extends Block> extends RegistrySupplier<T, Block> {

	public static final Location REGISTRY = Location.of("block");
	public static final Supplier<Registry<Block>> REGISTRY_SUPPLIER = () -> Registries.BLOCKS;

	private BlockSupplier(final Location registryName, final Location registryRegistryName, final Supplier<Registry<Block>> registrySupplier) {
		super(registryName, registryRegistryName, registrySupplier);
	}

	public static <T extends Block> BlockSupplier<T> of(Location registryName) {
		return new BlockSupplier<>(registryName, REGISTRY, REGISTRY_SUPPLIER);
	}

}
