package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.blockentity.BlockEntity;
import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.blockentity.SmelterBlockEntity;
import io.github.cadiboo.testgame.blockentity.StorageBlockEntity;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.registry.supplier.BlockEntityTypeSupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class BlockEntityTypes {

	public static final BlockEntityTypeSupplier<BlockEntityType<BlockEntity>> TEST_BLOCK_ENTITY = BlockEntityTypeSupplier.of(Location.of("test_block_entity"));
	public static final BlockEntityTypeSupplier<BlockEntityType<StorageBlockEntity>> STORAGE = BlockEntityTypeSupplier.of(Location.of("storage"));
	public static final BlockEntityTypeSupplier<BlockEntityType<SmelterBlockEntity>> SMELTER = BlockEntityTypeSupplier.of(Location.of("smelter"));

	public static void register(RegisterEvent<BlockEntityType> event) {
		event.getRegistry().registerAll(
				new BlockEntityType<>(TEST_BLOCK_ENTITY.registryName, BlockEntity::new),
				new BlockEntityType<>(STORAGE.registryName, StorageBlockEntity::new),
				new BlockEntityType<>(SMELTER.registryName, SmelterBlockEntity::new)
		);
	}

}
