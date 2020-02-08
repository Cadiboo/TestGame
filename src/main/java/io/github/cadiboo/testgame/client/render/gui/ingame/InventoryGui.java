package io.github.cadiboo.testgame.client.render.gui.ingame;

/**
 * @author Cadiboo
 */
public abstract class InventoryGui extends IngameGui {

	public InventoryGui(final int width, final int height) {
		super(width, height);
	}

	@Override
	public boolean pausesGame() {
		return false;
	}

	@Override
	protected void renderBackground() {

	}

	@Override
	protected void renderForeground() {

	}

	@Override
	protected void renderTooltips() {

	}

}
