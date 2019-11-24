package io.github.cadiboo.testgame.blockentity;

import io.github.cadiboo.testgame.capability.DefaultCapabilityHandler;
import io.github.cadiboo.testgame.util.math.Pos;
import io.github.cadiboo.testgame.world.World;

/**
 * @author Cadiboo
 */
public class BlockEntity extends DefaultCapabilityHandler {

	public final transient World world;
	public final Pos pos;

	public BlockEntity(final World world, final Pos pos) {
		this.world = world;
		this.pos = pos;
	}

	public void markDirty() {
	}

	public void onRemoved() {
	}

}
