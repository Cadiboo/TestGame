package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.blockentity.BlockEntity;
import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.blockentity.StorageBlockEntity;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.inventory.Inventory;
import io.github.cadiboo.testgame.registry.supplier.BlockEntitySupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class BlockEntityTypes {

	public static final BlockEntitySupplier<BlockEntityType<StorageBlockEntity>> STORAGE = BlockEntitySupplier.of(Location.of("storage"));

	public static void register(RegisterEvent<BlockEntityType> event) {
		event.getRegistry().registerAll(
				new BlockEntityType<>(Location.of("test_block_entity"), BlockEntity::new),
				new BlockEntityType<>(Location.of("storage"), StorageBlockEntity::new)
		);
	}

}
