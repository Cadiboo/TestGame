package io.github.cadiboo.testgame.client.color;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.client.event.color.FluidColorEvent;
import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.init.Fluids;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.supplier.FluidSupplier;

import java.awt.Color;

/**
 * @author Cadiboo
 */
public final class FluidColorHandler {

	private static Color[] colors = null;

	static {
		TestGame.EVENT_BUS.register(FluidColorHandler::register);
	}

	public static void register(final FluidColorEvent event) {
		addColor(Fluids.AIR, new Color(0, 0, 0, 0));
		addColor(Fluids.WATER, new Color(0, 0, 0xFF, 127));
		addColor(Fluids.LAVA, new Color(0xFF, 101, 0, 127));
		addColor(Fluids.SULPHUR, new Color(255, 0xFF, 0, 127));
	}

	public static void addColor(FluidSupplier<?> fluidSupplier, Color color) {
		colors[fluidSupplier.get().getId()] = color;
	}

	public static void init() {
		colors = new Color[Registries.FLUIDS.size()];
		TestGame.EVENT_BUS.post(new FluidColorEvent());
	}

	public static Color getColor(final Fluid fluid) {
		final char id = fluid.getId();
		if (id > colors.length)
			return Color.MAGENTA;
		final Color color = colors[id];
		return color == null ? Color.MAGENTA : color;
	}

}
