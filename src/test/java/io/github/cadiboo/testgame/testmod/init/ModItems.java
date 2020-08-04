package io.github.cadiboo.testgame.testmod.init;

import io.github.cadiboo.testgame.init.Items;
import io.github.cadiboo.testgame.item.Item;
import io.github.cadiboo.testgame.item.ItemProperties;
import io.github.cadiboo.testgame.loading.Touch;
import io.github.cadiboo.testgame.registry.RegistrationHelper;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.RegistryObject;

import static io.github.cadiboo.testgame.testmod.TestMod.MOD_ID;

/**
 * @author Cadiboo
 */
@Touch
public class ModItems {

	private static final RegistrationHelper<Item> ITEMS = RegistrationHelper.of(MOD_ID, Registries.ITEMS);

	public static final RegistryObject<Item> NEW_ITEM = ITEMS.register("new_block", $ -> new Item($, new ItemProperties()));
	public static final RegistryObject<Item> IRON_INGOT_OVERRIDE = ITEMS.register(Items.IRON_INGOT.getRegistryName(), $ -> new Item($, new ItemProperties()));

}
