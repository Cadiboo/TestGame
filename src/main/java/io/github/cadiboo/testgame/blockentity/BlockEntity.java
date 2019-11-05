package io.github.cadiboo.testgame.blockentity;

import io.github.cadiboo.testgame.capability.Capability;
import io.github.cadiboo.testgame.capability.CapabilityType;
import io.github.cadiboo.testgame.init.CapabilityTypes;
import io.github.cadiboo.testgame.inventory.Inventory;
import io.github.cadiboo.testgame.util.math.Pos;
import io.github.cadiboo.testgame.world.World;

/**
 * @author Cadiboo
 */
public class BlockEntity {

	public final World world;
	public final Pos pos;

	public BlockEntity(final World world, final Pos pos) {
		this.world = world;
		this.pos = pos;
		final Inventory inventory = getCapability(CapabilityTypes.INVENTORY.get());
	}

	public <T extends Capability<?>> T getCapability(CapabilityType<T> type) {
		return null;
	}

	public void markDirty() {
	}

	public void onRemoved() {

	}

}
