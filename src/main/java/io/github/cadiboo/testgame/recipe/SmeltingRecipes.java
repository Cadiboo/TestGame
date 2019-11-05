package io.github.cadiboo.testgame.recipe;

import io.github.cadiboo.testgame.item.ItemStack;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.supplier.ItemSupplier;
import io.github.cadiboo.testgame.util.Location;

import java.util.HashMap;

/**
 * @author Cadiboo
 */
public class SmeltingRecipes {

	private static final HashMap<Location, Integer> SMELT_TIMES = new HashMap<>();
	private static final HashMap<Location, Integer> SMELT_XPS = new HashMap<>();
	private static final HashMap<Location, Location> SMELT_RESULTS = new HashMap<>();

	public static int getSmeltTime(final ItemStack<?> itemStack) {
		return SMELT_TIMES.get(itemStack.getItem().getRegistryName());
	}

	public static int getSmeltXP(final ItemStack<?> itemStack) {
		return SMELT_XPS.get(itemStack.getItem().getRegistryName());
	}

	public static ItemStack<?> getSmeltResult(final ItemStack<?> itemStack) {
		return new ItemStack<>(Registries.ITEMS.get(SMELT_RESULTS.get(itemStack.getItem().getRegistryName())));
	}

	public static void addRecipe(final ItemSupplier input, final ItemSupplier result, final int time, final int xp) {
		final Location key = input.registryName;
		SMELT_TIMES.put(key, time);
		SMELT_XPS.put(key, xp);
		SMELT_RESULTS.put(key, result.registryName);
	}

	public static boolean hasRecipe(final ItemStack<?> itemStack) {
		return SMELT_TIMES.containsKey(itemStack.getItem().getRegistryName());
	}

}
