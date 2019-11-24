package io.github.cadiboo.testgame.recipe;

import io.github.cadiboo.testgame.item.ItemStack;

import java.util.HashMap;

/**
 * @author Cadiboo
 */
public class SmeltingRecipes {

	private static final HashMap<ItemStack<?>, SmeltingRecipe> MAP = new HashMap<>();

	public static SmeltingRecipe getRecipe(final ItemStack<?> itemStack) {
		return MAP.get(itemStack);
	}

	public static boolean hasRecipe(final ItemStack<?> itemStack) {
		return getRecipe(itemStack) != null;
	}

}
