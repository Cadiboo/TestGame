package io.github.cadiboo.testgame.client.color;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.client.event.color.BlockColorEvent;
import io.github.cadiboo.testgame.init.Blocks;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.RegistryObject;

import java.util.Arrays;

/**
 * @author Cadiboo
 */
public final class BlockColorHandler {

	public static final int MISSING = 0xFF_FF_00_FF; // Magenta

	private static int[] colors = null;

	static {
		TestGame.EVENT_BUS.register(BlockColorHandler::register);
	}

	public static void register(final BlockColorEvent event) {
		addColor(Blocks.AIR, 0x00_00_00_00);
		addColor(Blocks.STONE, 0xFF, 0x40, 0x40, 0x40);
		addColor(Blocks.DIRT, 0xFF, 70, 49, 33);
		addColor(Blocks.TURF, 0xFF, 0, 127, 0);
		addColor(Blocks.GRASS, 0xFF, 0, 0xFF, 0);
		addColor(Blocks.COAL_ORE, 0xFF, 0, 0, 0);
		addColor(Blocks.IRON_ORE, 0xFF, 157, 112, 70);
		addColor(Blocks.GOLD_ORE, 255, 200, 0);
		addColor(Blocks.DIAMOND_ORE, 0, 255, 255);
		addColor(Blocks.EMERALD_ORE, 0, 255, 0);
		addColor(Blocks.REDSTONE_ORE, 255, 0, 0);
	}

	public static void addColor(RegistryObject<? extends Block> supplier, int r, int g, int b) {
		addColor(supplier, 0xFF, r, g, b);
	}

	public static void addColor(RegistryObject<? extends Block> supplier, int a, int r, int g, int b) {
		a = a & 0xFF;
		r = r & 0xFF;
		g = g & 0xFF;
		b = b & 0xFF;
		addColor(supplier,
				a << 24 | r << 16 | g << 8 | b);
	}

	public static void addColor(RegistryObject<? extends Block> supplier, int color) {
		colors[supplier.get().getId()] = color;
	}

	public static void init() {
		colors = new int[Registries.BLOCKS.size()];
		Arrays.fill(colors, -1);
		TestGame.EVENT_BUS.post(new BlockColorEvent());
	}

	public static int getColor(final Block block) {
		final char id = block.getId();
		if (id > colors.length)
			return MISSING;
		final int color = colors[id];
		return color == -1 ? MISSING : color;
	}

}
