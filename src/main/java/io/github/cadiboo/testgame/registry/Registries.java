package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.entity.EntityType;
import io.github.cadiboo.testgame.event.registry.RegistryPropertiesEvent;
import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.loader.LoadIndex;
import io.github.cadiboo.testgame.loader.Loader;
import io.github.cadiboo.testgame.util.Location;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;

/**
 * @author Cadiboo
 */
public class Registries {

	public static final Registry<Block> BLOCKS;
	public static final Registry<Item> ITEMS;
	public static final Registry<Fluid> FLUIDS;
	public static final Registry<BlockEntityType> BLOCK_ENTITY_TYPES;
	public static final Registry<EntityType> ENTITY_TYPES;

//	public static final Registry<Biome> BIOMES;
//	public static final Registry<Dimension> DIMENSIONS;

	private static final LinkedHashMap<Location, Registry<?>> REGISTRY_LIST;

	static {
		if (!Loader.canLoad(LoadIndex.CREATE_REGISTRIES))
			throw new IllegalStateException();
		REGISTRY_LIST = new LinkedHashMap<>();
		final RegistryProperties properties = getRegistryProperties();
		BLOCKS = createRegistry(Location.of("block"), properties, Block.class);
		ITEMS = createRegistry(Location.of("item"), properties, Item.class);
		FLUIDS = createRegistry(Location.of("fluid"), properties, Fluid.class);
		BLOCK_ENTITY_TYPES = createRegistry(Location.of("block_entity_type"), properties, BlockEntityType.class);
		ENTITY_TYPES = createRegistry(Location.of("entity_type"), properties, EntityType.class);
	}

	private static RegistryProperties getRegistryProperties() {
		final RegistryProperties properties = new RegistryProperties();
		TestGame.EVENT_BUS.post(new RegistryPropertiesEvent(properties));
		return properties;
	}

	private static <T extends RegistryEntry> Registry<T> createRegistry(final Location location, final RegistryProperties properties, final Class<T> type) {
		final Registry<T> registry = new Registry<>(location, properties.supportsReplacement, properties.reloadable, type);
		REGISTRY_LIST.put(location, registry);
		return registry;
	}

	public static Registry<?> get(Location name) {
		return REGISTRY_LIST.get(name);
	}

	public static void forEach(BiConsumer<Location, Registry<?>> action) {
		REGISTRY_LIST.forEach(action);
	}

	public static Collection<Registry<?>> values() {
		return REGISTRY_LIST.values();
	}

}
