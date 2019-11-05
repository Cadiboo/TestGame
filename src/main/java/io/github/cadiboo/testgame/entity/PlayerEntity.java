package io.github.cadiboo.testgame.entity;

import io.github.cadiboo.testgame.init.EntityTypes;
import io.github.cadiboo.testgame.inventory.Inventory;
import io.github.cadiboo.testgame.item.ItemStack;
import io.github.cadiboo.testgame.util.math.Vec3d;
import io.github.cadiboo.testgame.world.World;

/**
 * @author Cadiboo
 */
public class PlayerEntity extends MovingEntity {

	private final Inventory inventory = makeInventory();
	private final Vec3d eyesPos = new Vec3d(pos);

	public PlayerEntity(final World world, final Vec3d pos) {
		super(world, pos);
	}

	protected Inventory makeInventory() {
		return new Inventory(getHotBarSize() + getInventoryColumns() * getInventoryRows());
	}

	protected int getInventoryRows() {
		return 3;
	}

	protected int getInventoryColumns() {
		return 9;
	}

	protected int getHotBarSize() {
		return 9;
	}

	public void dropItem(final int inventorySlot) {
		final ItemStack<?> stack = inventory.get(inventorySlot);
		if (stack.isEmpty())
			return;
		final ItemEntity item = EntityTypes.ITEM.get().create(world, updateAndGetEyesPos());
		item.stack = stack;
		item.direction.set(this.direction);
		item.motion.add(0.25, 0.25, 0.25);
	}

	public Vec3d updateAndGetEyesPos() {
		eyesPos.set(pos);
		eyesPos.y += getEyeHeight();
		return eyesPos;
	}

	private double getEyeHeight() {
		return 1.5;
	}

}
