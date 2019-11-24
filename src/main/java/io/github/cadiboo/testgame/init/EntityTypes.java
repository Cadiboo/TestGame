package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.entity.EntityType;
import io.github.cadiboo.testgame.entity.ItemEntity;
import io.github.cadiboo.testgame.entity.MovingBlockEntity;
import io.github.cadiboo.testgame.entity.PlayerEntity;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.registry.supplier.EntityTypeSupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class EntityTypes {

	public static final EntityTypeSupplier<EntityType<PlayerEntity>> PLAYER = EntityTypeSupplier.of(Location.of("player"));
	public static final EntityTypeSupplier<EntityType<ItemEntity>> ITEM = EntityTypeSupplier.of(Location.of("item"));
	public static final EntityTypeSupplier<EntityType<MovingBlockEntity>> MOVING_BLOCK = EntityTypeSupplier.of(Location.of("moving_block"));

	public static void register(RegisterEvent<EntityType> event) {
		event.getRegistry().registerAll(
				new EntityType<>(PLAYER.registryName, PlayerEntity::new),
				new EntityType<>(ITEM.registryName, ItemEntity::new),
				new EntityType<>(MOVING_BLOCK.registryName, MovingBlockEntity::new)
		);
	}

}
