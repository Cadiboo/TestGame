package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.blockentity.BlockEntity;
import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class BlockEntityTypes {

	public static void register(RegisterEvent<BlockEntityType> event) {
		event.getRegistry().registerAll(
				new BlockEntityType<>(Location.of("test_block_entity"), BlockEntity::new)
		);
	}

}
