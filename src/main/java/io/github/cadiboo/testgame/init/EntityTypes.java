package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.entity.EntityType;
import io.github.cadiboo.testgame.entity.ItemEntity;
import io.github.cadiboo.testgame.entity.MovingBlockEntity;
import io.github.cadiboo.testgame.entity.PlayerEntity;
import io.github.cadiboo.testgame.loading.Touch;
import io.github.cadiboo.testgame.registry.RegistrationHelper;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.RegistryObject;

/**
 * @author Cadiboo
 */
@Touch
public final class EntityTypes {

	private static final RegistrationHelper<EntityType<?>> ENTITY_TYPES = RegistrationHelper.of(TestGame.NAMESPACE, Registries.ENTITY_TYPES);

	public static final RegistryObject<EntityType<PlayerEntity>> PLAYER = ENTITY_TYPES.register("player", $ -> new EntityType<PlayerEntity>($, PlayerEntity::new));
	public static final RegistryObject<EntityType<ItemEntity>> ITEM = ENTITY_TYPES.register("item", $ -> new EntityType<ItemEntity>($, ItemEntity::new));
	public static final RegistryObject<EntityType<MovingBlockEntity>> MOVING_BLOCK = ENTITY_TYPES.register("moving_block", $ -> new EntityType<MovingBlockEntity>($, MovingBlockEntity::new));

}
