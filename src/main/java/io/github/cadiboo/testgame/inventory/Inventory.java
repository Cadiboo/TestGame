package io.github.cadiboo.testgame.inventory;

import io.github.cadiboo.testgame.capability.Capability;
import io.github.cadiboo.testgame.item.ItemStack;
import io.github.cadiboo.testgame.save.SaveData;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Cadiboo
 */
public class Inventory implements Capability<Inventory> {

	private final ItemStack<?>[] stacks;

	public Inventory(final ItemStack<?>[] stacks) {
		Objects.requireNonNull(stacks, "stacks cannot be null");
		for (final ItemStack<?> stack : stacks)
			Objects.requireNonNull(stack, "Stack cannot be null");
		this.stacks = stacks;
	}

	public Inventory(final int size) {
		final ItemStack<?>[] stacks = new ItemStack<?>[size];
		Arrays.fill(stacks, ItemStack.EMPTY);
		this.stacks = stacks;
	}

	/**
	 * Puts a stack into a slot
	 * If you modify the stack put in or returned stack the inventory will NOT know about it!
	 *
	 * @return The stack that was in the slot
	 */
	public ItemStack<?> put(final int slot, final ItemStack<?> stack) {
		Objects.requireNonNull(stack, "stack cannot be null");
		final ItemStack<?> old = stacks[slot];
		if (stack.isEmpty() && old.isEmpty())
			return stack;
		stacks[slot] = stack;
		slotChanged(slot);
		return old;
	}

	/**
	 * Returns the stack in the slot
	 * If you modify the returned stack the inventory will NOT know about it!
	 */
	public ItemStack<?> get(final int slot) {
		return stacks[slot];
	}

	/**
	 * Replaces the stack in the slot with air
	 * If you modify the returned stack the inventory will NOT know about it!
	 *
	 * @return The stack that was in the slot
	 */
	public ItemStack<?> remove(final int slot) {
		final ItemStack<?> stack = stacks[slot];
		if (stack.isEmpty())
			return stack;
		stacks[slot] = ItemStack.EMPTY;
		slotChanged(slot);
		return stack;
	}

	/**
	 * Called when a stack is changed
	 *
	 * @param slot The slot that had something in it changed
	 */
	protected void slotChanged(final int slot) {
	}

	public Inventory read(final SaveData saveData) {
		ItemStack<?>[] stacks = new ItemStack<?>[saveData.readVarInt()];
		for (int i = 0; i < stacks.length; ++i)
			stacks[i] = ItemStack.EMPTY.read(saveData);
		return new Inventory(stacks);
	}

	@Override
	public void write(final SaveData saveData) {
		saveData.writeVarInt(stacks.length);
		for (final ItemStack<?> stack : stacks)
			stack.write(saveData);
	}

	@Override
	public String toString() {
		return "Inventory {" +
				"size=" + stacks.length +
				", stacks=" + Arrays.toString(stacks) +
				'}';
	}

}
