package io.github.cadiboo.testgame.entity;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.blockentity.BlockEntity;
import io.github.cadiboo.testgame.util.math.Vec3d;
import io.github.cadiboo.testgame.world.World;

/**
 * @author Cadiboo
 */
public class MovingBlockEntity extends MovingEntity {

	public Block block = null;
	public BlockEntity blockEntity = null;

	public MovingBlockEntity(final World world, final Vec3d pos) {
		super(world, pos);
	}

}
