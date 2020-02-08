package io.github.cadiboo.testgame;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.capability.CapabilityType;
import io.github.cadiboo.testgame.chunk.generator.ChunkGenerator;
import io.github.cadiboo.testgame.entity.EntityType;
import io.github.cadiboo.testgame.event.EventBus;
import io.github.cadiboo.testgame.event.EventBusImpl;
import io.github.cadiboo.testgame.event.UnsafeEventBusImpl;
import io.github.cadiboo.testgame.event.registry.RegistryPropertiesEvent;
import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.init.BlockEntityTypes;
import io.github.cadiboo.testgame.init.Blocks;
import io.github.cadiboo.testgame.init.CapabilityTypes;
import io.github.cadiboo.testgame.init.EntityTypes;
import io.github.cadiboo.testgame.init.Fluids;
import io.github.cadiboo.testgame.init.Items;
import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.loader.LoadIndex;
import io.github.cadiboo.testgame.loader.Loader;
import io.github.cadiboo.testgame.util.TracingPrintStream;
import io.github.cadiboo.testgame.util.version.Version;

/**
 * @author Cadiboo
 */
public class TestGame {

	public static final EventBus EVENT_BUS = EventBus.make();
	public static final String TITLE = "Test Game";
	public static final String DOMAIN = "testgame";
	public static final Version VERSION = Version.of("0.1.0");
	private static TestGame instance;

	protected TestGame() {
		instance = this;
		if (Boolean.parseBoolean(System.getProperty(DOMAIN + ".debug.logs")))
			traceLogs();
		this.preInitialise();
		this.trackMemory();

		Loader.load();
	}

	protected static void traceLogs() {
		System.out.println("Wrapping System.out and System.err with tracing");
		System.setOut(new TracingPrintStream(System.out));
		System.setErr(new TracingPrintStream(System.err));
	}

	public static void registerListeners() {
		EVENT_BUS.register((RegistryPropertiesEvent e) -> e.getProperties()
				.reloadable()
				.supportsReplacement()
		);

		EVENT_BUS.registerGeneric(Block.class, Blocks::register);
		EVENT_BUS.registerGeneric(Item.class, Items::register);
		EVENT_BUS.registerGeneric(Fluid.class, Fluids::register);
		EVENT_BUS.registerGeneric(BlockEntityType.class, BlockEntityTypes::register);
		EVENT_BUS.registerGeneric(EntityType.class, EntityTypes::register);
		EVENT_BUS.registerGeneric(CapabilityType.class, CapabilityTypes::register);

		EVENT_BUS.register(ChunkGenerator::generateChunk);
	}

	protected void trackMemory() {
		final Thread memoryTracker = new Thread(() -> {
			final Runtime rt = Runtime.getRuntime();
			while (true) {
				if (rt.totalMemory() - rt.freeMemory() > 1024 * 1024 * 1024) {
					new OutOfMemoryError().printStackTrace();
					System.exit(1);
				}
			}
		}, "Memory Tracker");
		memoryTracker.setDaemon(true);
		memoryTracker.start();
	}

	/**
	 * WARNING: Called from the constructor
	 * Most fields will not be initialised
	 * Do not interact with anything except the loader at this point
	 */
	protected void preInitialise() {
		LoadIndex.touch();
	}

	public TestGame getInstance() {
		return instance;
	}

	public void tick() {

	}

}
