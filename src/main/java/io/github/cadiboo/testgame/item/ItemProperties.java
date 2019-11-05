package io.github.cadiboo.testgame.item;

import java.util.HashMap;

/**
 * @author Cadiboo
 */
public class ItemProperties {

	final HashMap<String, Object> extraData = new HashMap<>();
	int maxStackSize = 64;

	public ItemProperties setMaxStackSize(final int newValue) {
		this.maxStackSize = newValue;
		return this;
	}

	public ItemProperties extraData(final String key, Object value) {
		extraData.put(key, value);
		return this;
	}

}
