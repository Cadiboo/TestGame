package io.github.cadiboo.testgame.item;

/**
 * @author Cadiboo
 */
public class ItemProperties {

	int maxStackSize = 64;

	public ItemProperties setMaxStackSize(final int newValue) {
		this.maxStackSize = newValue;
		return this;
	}

}
