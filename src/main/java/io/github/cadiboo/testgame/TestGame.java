package io.github.cadiboo.testgame;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.block.BlockProperties;
import io.github.cadiboo.testgame.block.TestBlock;
import io.github.cadiboo.testgame.event.bus.EventBus;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.event.registry.RegistryPropertiesEvent;
import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.item.ItemProperties;
import io.github.cadiboo.testgame.item.TestItem;
import io.github.cadiboo.testgame.loader.Loader;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public final class TestGame {

	public static final EventBus EVENT_BUS = new EventBus();

	static {
		Loader.load();
	}

	public static void init() {
		EVENT_BUS.register((RegistryPropertiesEvent e) -> e.getProperties().reloadable().supportsReplacement());
		for (int i = 0; i < 20; i++) {
			EVENT_BUS.registerGeneric(Block.class, (RegisterEvent<Block> event) -> event.getRegistry().registerAll(
					new TestBlock(Location.of("test_normal_block"), new BlockProperties()),
					new TestBlock(Location.of("test_test_block"), new BlockProperties()),
					new Block(Location.of("normal_block"), new BlockProperties()),
					new Block(Location.of("test_block"), new BlockProperties())
			));
			EVENT_BUS.registerGeneric(Item.class, (RegisterEvent<Item> event) -> event.getRegistry().registerAll(
					new TestItem(Location.of("test_normal_item"), new ItemProperties()),
					new TestItem(Location.of("test_test_item"), new ItemProperties()),
					new Item(Location.of("normal_item"), new ItemProperties()),
					new Item(Location.of("test_item"), new ItemProperties())
			));
		}
	}

}
