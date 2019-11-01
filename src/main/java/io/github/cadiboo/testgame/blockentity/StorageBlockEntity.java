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
public class StorageBlockEntity extends BlockEntity {

	private final Inventory inventory = new Inventory(9 * 9) {
		@Override
		protected void slotChanged(final int slot) {
			markDirty();
		}
	};

	public StorageBlockEntity(final World world, final Pos pos) {
		super(world, pos);
	}

	@Override
	public <T extends Capability<?>> T getCapability(final CapabilityType<T> type) {
		if (type == CapabilityTypes.INVENTORY.get())
			return inventory.cast();
		return super.getCapability(type);
	}

}
