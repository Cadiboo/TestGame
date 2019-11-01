package io.github.cadiboo.testgame.inventory;

import io.github.cadiboo.testgame.capability.Capability;
import io.github.cadiboo.testgame.item.ItemStack;

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
		for (int i = 0; i < size; ++i)
			stacks[i] = ItemStack.EMPTY;
		this.stacks = stacks;
	}

	public int getMaxStackSize(final int slot) {
		return 64;
	}

	public ItemStack<?> put(final int slot, final ItemStack<?> stack) {
		Objects.requireNonNull(stack, "stack cannot be null");
		final ItemStack<?> old = stacks[slot];
		if (stack.isEmpty() && old.isEmpty())
			return stack;
		stacks[slot] = stack;
		slotChanged(slot);
		return old;
	}

	public ItemStack<?> get(final int slot) {
		return stacks[slot];
	}

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

}
