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
public class ChestBlockEntity extends BlockEntity {

	protected final Inventory inventory = makeInventory();

	public ChestBlockEntity(final World world, final Pos pos) {
		super(world, pos);
	}

	protected Inventory makeInventory() {
		return new Inventory(getInventoryColumns() * getInventoryRows()) {
			@Override
			protected void slotChanged(final int slot) {
				markDirty();
			}
		};
	}

	protected int getInventoryRows() {
		return 6;
	}

	protected int getInventoryColumns() {
		return 9;
	}

	@Override
	public <T extends Capability<?>> T getCapability(final CapabilityType<T> type) {
		if (type == CapabilityTypes.INVENTORY.get())
			return inventory.cast();
		return super.getCapability(type);
	}

}
