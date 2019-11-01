package io.github.cadiboo.testgame.item;

import io.github.cadiboo.testgame.init.Items;

/**
 * @author Cadiboo
 */
public class ItemStack<T extends Item> {

	public static final ItemStack<AirItem> EMPTY = new ItemStack<AirItem>(null, 0, 0) {
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

	public T getItem() {
		return item;
	}

	public int getSize() {
		return size;
	}

	public void setSize(final int size) {
		this.size = size;
	}

	public boolean isEmpty() {
		return this == EMPTY || this.size < 1 || this.item == Items.AIR.get();
	}

	/**
	 * @param grow How much to grow the size
	 * @return How much the size grew by
	 */
	public int grow(int grow) {
		int grown = Math.min(maxSize - size, grow);
		size += grown;
		return grown;
	}

	/**
	 * @return How much the size grew by
	 */
	public int grow() {
		return grow(1);
	}

	/**
	 * @param shrink How much to shrink the size
	 * @return How much the size shrunk by
	 */
	public int shrink(int shrink) {
		int shrunk = Math.min(size, shrink);
		size += shrunk;
		return shrunk;
	}

	/**
	 * @return How much the size shrunk by
	 */
	public int shrink() {
		return shrink(1);
	}

}
