package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.blockentity.SmelterBlockEntity;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.item.AirItem;
import io.github.cadiboo.testgame.item.BlockItem;
import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.item.ItemProperties;
import io.github.cadiboo.testgame.registry.supplier.ItemSupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class Items {

	public static final ItemSupplier<AirItem> AIR = ItemSupplier.of(Blocks.AIR.registryName);
	public static final ItemSupplier<Item> COAL = ItemSupplier.of(Location.of("coal"));
	public static final ItemSupplier<Item> IRON_INGOT = ItemSupplier.of(Location.of("iron_ingot"));

	public static void register(final RegisterEvent<Item> event) {
		// Items
		event.getRegistry().registerAll(
				new AirItem(new ItemProperties()),
				new Item(COAL.registryName, new ItemProperties().extraData(SmelterBlockEntity.FUEL_VALUE_KEY, 200)),
				new Item(IRON_INGOT.registryName, new ItemProperties())
		);
		// BlockItems
		event.getRegistry().registerAll(
				new BlockItem<>(Blocks.STONE, new ItemProperties()),
				new BlockItem<>(Blocks.DIRT, new ItemProperties()),
				new BlockItem<>(Blocks.TURF, new ItemProperties()),
				new BlockItem<>(Blocks.GRASS, new ItemProperties()),
				new BlockItem<>(Blocks.COAL_ORE, new ItemProperties()),
				new BlockItem<>(Blocks.IRON_ORE, new ItemProperties()),
				new BlockItem<>(Blocks.GOLD_ORE, new ItemProperties()),
				new BlockItem<>(Blocks.DIAMOND_ORE, new ItemProperties()),
				new BlockItem<>(Blocks.EMERALD_ORE, new ItemProperties()),
				new BlockItem<>(Blocks.REDSTONE_ORE, new ItemProperties())
		);
	}

}
