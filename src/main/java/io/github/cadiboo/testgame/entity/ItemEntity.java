package io.github.cadiboo.testgame.entity;

import io.github.cadiboo.testgame.item.ItemStack;
import io.github.cadiboo.testgame.util.math.Vec3d;
import io.github.cadiboo.testgame.world.World;

/**
 * @author Cadiboo
 */
public class ItemEntity extends MovableEntity {

	public ItemStack<?> stack = null;

	public ItemEntity(final World world, final Vec3d pos) {
		super(world, pos);
	}

}
