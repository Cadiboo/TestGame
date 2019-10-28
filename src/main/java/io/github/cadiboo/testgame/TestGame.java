package io.github.cadiboo.testgame;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.block.BlockProperties;
import io.github.cadiboo.testgame.block.TestBlock;
import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.entity.EntityType;
import io.github.cadiboo.testgame.event.bus.EventBus;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.event.registry.RegistryPropertiesEvent;
import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.item.ItemProperties;
import io.github.cadiboo.testgame.item.TestItem;
import io.github.cadiboo.testgame.loader.Loader;
import io.github.cadiboo.testgame.util.Location;
import io.github.cadiboo.testgame.util.TracingPrintStream;

/**
 * @author Cadiboo
 */
public final class TestGame {

	public static final EventBus EVENT_BUS = new EventBus();

	static {
		if (Boolean.parseBoolean(System.getProperty("testgame.debug.logs")))
			traceLogs();
		Loader.load();
	}
	private static void traceLogs() {
		System.out.println("Wrapping System.out and System.err with tracing");
		System.setOut(new TracingPrintStream(System.out));
		System.setErr(new TracingPrintStream(System.err));
	}

	public static void registerListeners() {
		EVENT_BUS.register((RegistryPropertiesEvent e) -> e.getProperties()
				.reloadable()
				.supportsReplacement()
		);
		for (int i = 0; i < 20; i++) {
			EVENT_BUS.registerGeneric(Block.class, (RegisterEvent<Block> event) -> event.getRegistry().registerAll(
					new TestBlock(Location.of("test_normal_block"), new BlockProperties()),
					new TestBlock(Location.of("test_test_block"), new BlockProperties()),
					new Block(Location.of("normal_block"), new BlockProperties()),
					new Block(Location.of("test_block"), new BlockProperties()),
					new Block(Location.of("block_to_be_replaced"), new BlockProperties())
			));
			EVENT_BUS.registerGeneric(Item.class, (RegisterEvent<Item> event) -> event.getRegistry().registerAll(
					new TestItem(Location.of("test_normal_item"), new ItemProperties()),
					new TestItem(Location.of("test_test_item"), new ItemProperties()),
					new Item(Location.of("normal_item"), new ItemProperties()),
					new Item(Location.of("test_item"), new ItemProperties())
			));
			EVENT_BUS.registerGeneric(BlockEntityType.class, (RegisterEvent<BlockEntityType> event) -> event.getRegistry().registerAll(
					new BlockEntityType<>(Location.of("test_block_entity"), (o, j) -> null)
			));
			EVENT_BUS.registerGeneric(EntityType.class, (RegisterEvent<EntityType> event) -> event.getRegistry().registerAll(
					new EntityType<>(Location.of("test_entity"), (o, j) -> null)
			));
		}
	}

}
