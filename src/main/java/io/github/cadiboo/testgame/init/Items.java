package io.github.cadiboo.testgame.init;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.block.DirtBlock;
import io.github.cadiboo.testgame.block.TurfBlock;
import io.github.cadiboo.testgame.blockentity.SmelterBlockEntity;
import io.github.cadiboo.testgame.item.AirItem;
import io.github.cadiboo.testgame.item.BlockItem;
import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.item.ItemProperties;
import io.github.cadiboo.testgame.loading.Touch;
import io.github.cadiboo.testgame.registry.RegistrationHelper;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.RegistryObject;

/**
 * @author Cadiboo
 */
@Touch
public final class Items {

	private static final RegistrationHelper<Item> ITEMS = RegistrationHelper.of(TestGame.NAMESPACE, Registries.ITEMS);

	public static final RegistryObject<AirItem> AIR = ITEMS.register(Blocks.AIR.getRegistryName(), $ -> new AirItem(new ItemProperties()));

	public static final RegistryObject<BlockItem<Block>> STONE = ITEMS.register(Blocks.STONE.getRegistryName(), $ -> new BlockItem<>(Blocks.STONE, new ItemProperties()));
	public static final RegistryObject<BlockItem<DirtBlock>> DIRT = ITEMS.register(Blocks.DIRT.getRegistryName(), $ -> new BlockItem<>(Blocks.DIRT, new ItemProperties()));
	public static final RegistryObject<BlockItem<TurfBlock>> TURF = ITEMS.register(Blocks.TURF.getRegistryName(), $ -> new BlockItem<>(Blocks.TURF, new ItemProperties()));
	public static final RegistryObject<BlockItem<Block>> GRASS = ITEMS.register(Blocks.GRASS.getRegistryName(), $ -> new BlockItem<>(Blocks.GRASS, new ItemProperties()));
	public static final RegistryObject<BlockItem<Block>> COAL_ORE = ITEMS.register(Blocks.COAL_ORE.getRegistryName(), $ -> new BlockItem<>(Blocks.COAL_ORE, new ItemProperties()));
	public static final RegistryObject<BlockItem<Block>> IRON_ORE = ITEMS.register(Blocks.IRON_ORE.getRegistryName(), $ -> new BlockItem<>(Blocks.IRON_ORE, new ItemProperties()));
	public static final RegistryObject<BlockItem<Block>> GOLD_ORE = ITEMS.register(Blocks.GOLD_ORE.getRegistryName(), $ -> new BlockItem<>(Blocks.GOLD_ORE, new ItemProperties()));
	public static final RegistryObject<BlockItem<Block>> DIAMOND_ORE = ITEMS.register(Blocks.DIAMOND_ORE.getRegistryName(), $ -> new BlockItem<>(Blocks.DIAMOND_ORE, new ItemProperties()));
	public static final RegistryObject<BlockItem<Block>> EMERALD_ORE = ITEMS.register(Blocks.EMERALD_ORE.getRegistryName(), $ -> new BlockItem<>(Blocks.EMERALD_ORE, new ItemProperties()));
	public static final RegistryObject<BlockItem<Block>> REDSTONE_ORE = ITEMS.register(Blocks.REDSTONE_ORE.getRegistryName(), $ -> new BlockItem<>(Blocks.REDSTONE_ORE, new ItemProperties()));

	public static final RegistryObject<Item> COAL = ITEMS.register("coal", $ -> new Item($, new ItemProperties().extraData(SmelterBlockEntity.FUEL_VALUE_KEY, 200)));
	public static final RegistryObject<Item> IRON_INGOT = ITEMS.register("iron_ingot", $ -> new Item($, new ItemProperties()));

}
