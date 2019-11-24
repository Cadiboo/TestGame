package io.github.cadiboo.testgame.recipe;

import io.github.cadiboo.testgame.item.ItemStack;

/**
 * @author Cadiboo
 */
public class SmeltingRecipe {

	public final int time;
	public final int xp;
	public final ItemStack<?> input;
	public final ItemStack<?> result;

	public SmeltingRecipe(final int time, final int xp, final ItemStack<?> input, final ItemStack<?> result) {
		this.time = time;
		this.xp = xp;
		this.input = input;
		this.result = result;
	}

}
