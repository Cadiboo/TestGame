package io.github.cadiboo.testgame.entity;

import io.github.cadiboo.testgame.util.Updateable;
import io.github.cadiboo.testgame.util.math.Vec3d;
import io.github.cadiboo.testgame.world.World;

/**
 * @author Cadiboo
 */
public class MovableEntity extends Entity implements Updateable {

	public final Vec3d motion = new Vec3d(0, 0, 0);

	public MovableEntity(final World world, final Vec3d pos) {
		super(world, pos);
	}

	@Override
	public void update() {
		pos.add(motion);
		motion.multiply(getMotionMultiplier());
	}

	private double getMotionMultiplier() {
		return 0.9;
//		return 1 - world.airDensity();
	}

}
