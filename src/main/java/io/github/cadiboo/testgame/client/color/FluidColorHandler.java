package io.github.cadiboo.testgame.client.color;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.client.event.color.FluidColorEvent;
import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.init.Fluids;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.RegistryObject;

import java.util.Arrays;

import static io.github.cadiboo.testgame.client.color.BlockColorHandler.MISSING;

/**
 * @author Cadiboo
 */
public final class FluidColorHandler {

	private static int[] colors = null;

	static {
		TestGame.EVENT_BUS.register(FluidColorHandler::register);
	}

	public static void register(final FluidColorEvent event) {
		addColor(Fluids.AIR, 0, 0, 0, 0);
		addColor(Fluids.WATER, 0, 0, 0xFF, 127);
		addColor(Fluids.LAVA, 0xFF, 101, 0, 127);
		addColor(Fluids.SULPHUR, 255, 0xFF, 0, 127);
	}

	public static void addColor(RegistryObject<? extends Fluid> supplier, int r, int g, int b) {
		addColor(supplier, 0xFF, r, g, b);
	}

	public static void addColor(RegistryObject<? extends Fluid> supplier, int a, int r, int g, int b) {
		a = a & 0xFF;
		r = r & 0xFF;
		g = g & 0xFF;
		b = b & 0xFF;
		addColor(supplier,
				a << 24 | r << 16 | g << 8 | b);
	}

	public static void addColor(RegistryObject<? extends Fluid> supplier, int color) {
		colors[supplier.get().getId()] = color;
	}

	public static void init() {
		colors = new int[Registries.BLOCKS.size()];
		Arrays.fill(colors, -1);
		TestGame.EVENT_BUS.post(new FluidColorEvent());
	}

	public static int getColor(final Fluid fluid) {
		final char id = fluid.getId();
		if (id > colors.length)
			return MISSING;
		final int color = colors[id];
		return color == -1 ? MISSING : color;
	}

}
