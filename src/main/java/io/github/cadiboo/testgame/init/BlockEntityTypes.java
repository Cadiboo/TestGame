package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.blockentity.BlockEntity;
import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.blockentity.SmelterBlockEntity;
import io.github.cadiboo.testgame.blockentity.StorageBlockEntity;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.registry.supplier.BlockEntitySupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class BlockEntityTypes {

	public static final BlockEntitySupplier<BlockEntityType<BlockEntity>> TEST_BLOCK_ENTITY = BlockEntitySupplier.of(Location.of("test_block_entity"));
	public static final BlockEntitySupplier<BlockEntityType<StorageBlockEntity>> STORAGE = BlockEntitySupplier.of(Location.of("storage"));
	public static final BlockEntitySupplier<BlockEntityType<SmelterBlockEntity>> SMELTER = BlockEntitySupplier.of(Location.of("smelter"));

	public static void register(RegisterEvent<BlockEntityType> event) {
		event.getRegistry().registerAll(
				new BlockEntityType<>(TEST_BLOCK_ENTITY.registryName, BlockEntity::new),
				new BlockEntityType<>(STORAGE.registryName, StorageBlockEntity::new),
				new BlockEntityType<>(SMELTER.registryName, SmelterBlockEntity::new)
		);
	}

}
