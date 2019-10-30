package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.item.AirItem;
import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.item.ItemProperties;
import io.github.cadiboo.testgame.item.TestItem;
import io.github.cadiboo.testgame.registry.supplier.ItemSupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class Items {

	public static final ItemSupplier<AirItem> AIR = ItemSupplier.of(Location.of("air"));

	public static void register(final RegisterEvent<Item> event) {
		event.getRegistry().registerAll(
				new AirItem(new ItemProperties()),
				new TestItem(Location.of("test_normal_item"), new ItemProperties()),
				new TestItem(Location.of("test_test_item"), new ItemProperties()),
				new Item(Location.of("normal_item"), new ItemProperties()),
				new Item(Location.of("test_item"), new ItemProperties())
		);
	}

}
