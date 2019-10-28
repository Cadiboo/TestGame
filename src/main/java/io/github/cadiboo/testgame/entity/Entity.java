package io.github.cadiboo.testgame.entity;

import io.github.cadiboo.testgame.util.math.Vec3d;
import io.github.cadiboo.testgame.world.World;

/**
 * @author Cadiboo
 */
public class Entity {

	public final Vec3d pos;
	public World world;

	public Entity(final World world, final Vec3d pos) {
		this.world = world;
		this.pos = pos;
	}

}
