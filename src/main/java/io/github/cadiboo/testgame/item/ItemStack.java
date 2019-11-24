package io.github.cadiboo.testgame.item;

import io.github.cadiboo.testgame.capability.DefaultCapabilityHandler;
import io.github.cadiboo.testgame.init.Items;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.save.SaveData;
import io.github.cadiboo.testgame.save.Saveable;

/**
 * @author Cadiboo
 */
public class ItemStack<T extends Item> extends DefaultCapabilityHandler implements Saveable<ItemStack<?>>, Cloneable {

	public static transient final ItemStack<AirItem> EMPTY = new ItemStack<AirItem>(null, 0, 0) {
		@Override
		public AirItem getItem() {
			return Items.AIR.get();
		}
	};

	private final T item;
	private final int maxSize;
	private int size;

	public ItemStack(final T item) {
		this(item, 1);
	}

	public ItemStack(final T item, final int size) {
		this(item, size, 64);
	}

	protected ItemStack(final T item, final int size, final int maxSize) {
		this.item = item;
		this.maxSize = maxSize;
		this.size = size;
	}

	public ItemStack<?> read(final SaveData saveData) {
		if (saveData.readBoolean())
			return ItemStack.EMPTY;
		char id = saveData.readVarChar();
		int size = saveData.readVarInt();
		int maxSize = saveData.readVarInt();
		final ItemStack<Item> itemStack = new ItemStack<>(Registries.ITEMS.get(id), size, maxSize);
		itemStack.readCapabilities(saveData);
		return itemStack;
	}

	public void write(final SaveData saveData) {
		final boolean empty = this.isEmpty();
		saveData.writeBoolean(empty);
		if (empty)
			return;
		saveData.writeVarChar(this.getItem().getId());
		saveData.writeVarInt(this.getSize());
		saveData.writeVarInt(this.getMaxSize());
		this.writeCapabilities(saveData);
	}

	public T getItem() {
		return item;
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return this == EMPTY || this.size < 1 || this.item == Items.AIR.get();
	}

	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @return How much the size grew by
	 */
	public int grow() {
		return grow(1);
	}

	/**
	 * @param grow How much to grow the size
	 * @return How much the size grew by
	 */
	public int grow(int grow) {
		return grow(grow, false);
	}

	/**
	 * @param grow     How much to grow the size
	 * @param simulate If the stack should NOT be grown
	 * @return How much the size grew by
	 */
	public int grow(int grow, boolean simulate) {
		int grown = Math.min(maxSize - size, grow);
		if (!simulate)
			size += grown;
		return grown;
	}

	/**
	 * @return How much the size shrunk by
	 */
	public int shrink() {
		return shrink(1);
	}

	/**
	 * @param shrink How much to shrink the size
	 * @return How much the size shrunk by
	 */
	public int shrink(int shrink) {
		return shrink(shrink, false);
	}

	/**
	 * @param shrink   How much to shrink the size
	 * @param simulate If the stack should NOT be shrunk
	 * @return How much the shrunk grew by
	 */
	public int shrink(int shrink, boolean simulate) {
		int shrunk = Math.min(size, shrink);
		size += shrunk;
		return shrunk;
	}

	/**
	 * @return The stack that couldn't be added (or empty if everything was added)
	 */
	public ItemStack<?> add(final ItemStack<?> other) {
		if (other.isEmpty())
			return EMPTY;
		if (this.getItem() != other.getItem())
			return other;
		final int added = this.grow(other.getSize());
		other.shrink(added);
		return other.isEmpty() ? EMPTY : other;
	}

	public boolean canAddFully(final ItemStack<?> other) {
		if (other.isEmpty())
			return true;
		if (this.getItem() != other.getItem())
			return false;
		final int otherSize = other.getSize();
		return otherSize == this.grow(otherSize, true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ItemStack<T> clone() {
		try {
			return (ItemStack<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}
