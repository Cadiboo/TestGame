package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.entity.Entity;
import io.github.cadiboo.testgame.entity.EntityType;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class EntityTypes {

	public static void register(RegisterEvent<EntityType> event) {
		event.getRegistry().registerAll(
				new EntityType<>(Location.of("test_entity"), Entity::new)
		);
	}

}
