package io.github.cadiboo.testgame.world;

import io.github.cadiboo.testgame.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cadiboo
 */
public class World {

	private final List<Entity> entities = new ArrayList<>();

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

}
