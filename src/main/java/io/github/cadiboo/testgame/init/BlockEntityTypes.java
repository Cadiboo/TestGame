package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.blockentity.BlockEntity;
import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.blockentity.SmelterBlockEntity;
import io.github.cadiboo.testgame.blockentity.StorageBlockEntity;
import io.github.cadiboo.testgame.loading.Touch;
import io.github.cadiboo.testgame.registry.RegistrationHelper;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.RegistryObject;

/**
 * @author Cadiboo
 */
@Touch
public final class BlockEntityTypes {

	private static final RegistrationHelper<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationHelper.of(TestGame.NAMESPACE, Registries.BLOCK_ENTITY_TYPES);

	public static final RegistryObject<BlockEntityType<BlockEntity>> TEST_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("test_block_entity", $ -> new BlockEntityType<>($, BlockEntity::new));
	public static final RegistryObject<BlockEntityType<StorageBlockEntity>> STORAGE = BLOCK_ENTITY_TYPES.register("storage", $ -> new BlockEntityType<>($, StorageBlockEntity::new));
	public static final RegistryObject<BlockEntityType<SmelterBlockEntity>> SMELTER = BLOCK_ENTITY_TYPES.register("smelter", $ -> new BlockEntityType<>($, SmelterBlockEntity::new));

}
