package io.github.cadiboo.testgame.testmod.init;

import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.init.Items;
import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.item.ItemProperties;
import io.github.cadiboo.testgame.registry.supplier.ItemSupplier;
import io.github.cadiboo.testgame.util.Location;

import static io.github.cadiboo.testgame.testmod.TestMod.MOD_ID;

/**
 * @author Cadiboo
 */
public class ModItems {

	public static final ItemSupplier<Item> NEW_ITEM = ItemSupplier.of(Location.of(MOD_ID, "new_item"));

	public static void register(final RegisterEvent<Item> event) {
		event.getRegistry().registerAll(
				new Item(Items.IRON_INGOT.registryName, new ItemProperties()),
				new Item(NEW_ITEM.registryName, new ItemProperties())
		);
	}

}
