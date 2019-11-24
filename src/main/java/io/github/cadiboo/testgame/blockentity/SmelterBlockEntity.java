package io.github.cadiboo.testgame.blockentity;

import io.github.cadiboo.testgame.heat.HeatLevel;
import io.github.cadiboo.testgame.init.CapabilityTypes;
import io.github.cadiboo.testgame.inventory.Inventory;
import io.github.cadiboo.testgame.item.ItemStack;
import io.github.cadiboo.testgame.recipe.SmeltingRecipe;
import io.github.cadiboo.testgame.recipe.SmeltingRecipes;
import io.github.cadiboo.testgame.util.LogicalSide;
import io.github.cadiboo.testgame.util.Updateable;
import io.github.cadiboo.testgame.util.math.Pos;
import io.github.cadiboo.testgame.world.World;

/**
 * @author Cadiboo
 */
public class SmelterBlockEntity extends BlockEntity implements Updateable {

	public static final int INPUT_SLOT = 0;
	public static final int FUEL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	public static final String FUEL_VALUE_KEY = "fuelValue";

	protected final Inventory inventory = makeInventory();
	protected final HeatLevel heatLevel = new HeatLevel(0);
	protected int fuelTimeLeft;
	protected int smeltTimeLeft;

	public SmelterBlockEntity(final World world, final Pos pos) {
		super(world, pos);
		this.addCapability(CapabilityTypes.INVENTORY.get(), inventory);
		this.addCapability(CapabilityTypes.HEAT_LEVEL.get(), heatLevel);
	}

	protected Inventory makeInventory() {
		return new Inventory(3) {
			@Override
			protected void slotChanged(final int slot) {
				markDirty();
			}
		};
	}

	@Override
	public void update() {
		if (world.getLogicalSide() != LogicalSide.LOGICAL_SERVER)
			return;
		boolean somethingChanged = false;

		final ItemStack<?> input = inventory.get(INPUT_SLOT);
		final ItemStack<?> stackInOutput = inventory.get(OUTPUT_SLOT);
		final SmeltingRecipe recipe = SmeltingRecipes.getRecipe(input);
		final ItemStack<?> smeltResult = recipe == null ? ItemStack.EMPTY : recipe.result;
		final boolean canProduceOutput = !smeltResult.isEmpty() && stackInOutput.canAddFully(smeltResult);

		if (hasFuel()) {
			// reduce amount of fuel time (cooling)
			heatLevel.heat = --fuelTimeLeft;
			somethingChanged = true;
		} else if (canProduceOutput) {
			// burn fuel if we are going to use it
			heatLevel.heat = fuelTimeLeft = tryBurnFuel();
			if (hasFuel())
				somethingChanged = true;
		} else {
			// reduce amount smelted (cooling)
			++smeltTimeLeft;
			somethingChanged = true;
		}
		if (hasFuel() && canProduceOutput) {
			if (smeltTimeLeft > 0) {
				--smeltTimeLeft; // increase amount smelted (using fuel & heating)
				somethingChanged = true;
			}
			if (smeltTimeLeft == 0) {
				stackInOutput.add(smeltResult.clone());
				inventory.put(OUTPUT_SLOT, stackInOutput);
				somethingChanged = true;
			}
		}

		if (somethingChanged) {
			markDirty();
			// sync to clients?
		}
	}

	protected boolean hasFuel() {
		return fuelTimeLeft > 0;
	}

	protected int tryBurnFuel() {
		final ItemStack<?> itemStack = inventory.get(FUEL_SLOT);
		if (itemStack.isEmpty())
			return 0;
		final int fuelTime = itemStack.getItem().getData(FUEL_VALUE_KEY);
		if (fuelTime > 0)
			itemStack.shrink();
		return fuelTime;
	}

}
