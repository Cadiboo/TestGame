package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.entity.EntityType;
import io.github.cadiboo.testgame.entity.ItemEntity;
import io.github.cadiboo.testgame.entity.PlayerEntity;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.registry.supplier.EntitySupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class EntityTypes {

	public static final EntitySupplier<EntityType<PlayerEntity>> PLAYER = EntitySupplier.of(Location.of("player"));
	public static final EntitySupplier<EntityType<ItemEntity>> ITEM = EntitySupplier.of(Location.of("item"));

	public static void register(RegisterEvent<EntityType> event) {
		event.getRegistry().registerAll(
				new EntityType<>(Location.of("player"), PlayerEntity::new),
				new EntityType<>(Location.of("item"), ItemEntity::new)
		);
	}

}
