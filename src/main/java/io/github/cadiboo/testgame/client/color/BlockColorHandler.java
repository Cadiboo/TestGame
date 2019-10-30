package io.github.cadiboo.testgame.client.color;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.client.event.color.BlockColorEvent;
import io.github.cadiboo.testgame.init.Blocks;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.supplier.BlockSupplier;

import java.awt.Color;

/**
 * @author Cadiboo
 */
public final class BlockColorHandler {

	private static Color[] colors = null;

	static {
		TestGame.EVENT_BUS.register(BlockColorHandler::register);
	}

	public static void register(final BlockColorEvent event) {
		addColor(Blocks.AIR, new Color(0, 0, 0, 0));
//		addColor(Blocks.AIR, Color.WHITE);
		addColor(Blocks.STONE, Color.DARK_GRAY);
		addColor(Blocks.DIRT, new Color(70, 49, 33));
		addColor(Blocks.TURF, new Color(0, 127, 0));
		addColor(Blocks.GRASS, Color.GREEN);
		addColor(Blocks.COAL_ORE, Color.BLACK);
		addColor(Blocks.IRON_ORE, new Color(157, 112, 70));
		addColor(Blocks.GOLD_ORE, Color.ORANGE);
		addColor(Blocks.DIAMOND_ORE, Color.CYAN);
		addColor(Blocks.EMERALD_ORE, Color.GREEN);
		addColor(Blocks.REDSTONE_ORE, Color.RED);
	}

	public static void addColor(BlockSupplier<?> blockSupplier, Color color) {
		colors[blockSupplier.get().getId()] = color;
	}

	public static void init() {
		colors = new Color[Registries.BLOCKS.size()];
		TestGame.EVENT_BUS.post(new BlockColorEvent());
	}

	public static Color getColor(final Block block) {
		final char id = block.getId();
		if (id > colors.length)
			return Color.MAGENTA;
		final Color color = colors[id];
		return color == null ? Color.MAGENTA : color;
	}

}
