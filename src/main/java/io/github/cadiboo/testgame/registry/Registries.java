package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.capability.CapabilityType;
import io.github.cadiboo.testgame.entity.EntityType;
import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.item.Item;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Cadiboo
 */
public final class Registries {

	// Java 13
//	public static final Registry<Registry<?>> REGISTRIES = register(new Registry<>("registries") {});
//	public static final Registry<Block> BLOCKS = register(new Registry<>("blocks") {});
//	public static final Registry<Item> ITEMS = register(new Registry<>("items") {});
//	public static final Registry<Fluid> FLUIDS = register(new Registry<>("fluids") {});
//	public static final Registry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = register(new Registry<>("block_entity_types") {});
//	public static final Registry<EntityType<?>> ENTITY_TYPES = register(new Registry<>("entity_types") {});
//	public static final Registry<CapabilityType<?>> CAPABILITY_TYPES = register(new Registry<>("capability_types") {});

	public static final Registry<Registry<?>> REGISTRIES = new Registry<Registry<?>>("registries") {};
	public static final Registry<Block> BLOCKS = register(new Registry<Block>("blocks") {});
	public static final Registry<Item> ITEMS = register(new Registry<Item>("items") {});
	public static final Registry<Fluid> FLUIDS = register(new Registry<Fluid>("fluids") {});
	public static final Registry<BlockEntityType<?>> BLOCK_ENTITY_TYPES = register(new Registry<BlockEntityType<?>>("block_entity_types") {});
	public static final Registry<EntityType<?>> ENTITY_TYPES = register(new Registry<EntityType<?>>("entity_types") {});
	public static final Registry<CapabilityType<?>> CAPABILITY_TYPES = register(new Registry<CapabilityType<?>>("capability_types") {});

	private static <T extends Registry<?>> T register(T registry) {
		if (registry == REGISTRIES)
			throw new IllegalStateException();
		REGISTRIES.register(registry.getRegistryName(), registry);
		return registry;
	}

}
