package io.github.cadiboo.testgame.client.render.gui;

/**
 * @author Cadiboo
 */
public abstract class Gui {

	public final int width;
	public final int height;

	public Gui(final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	public final void render() {
		renderBackground();
		renderForeground();
		renderTooltips();
	}

	protected abstract void renderBackground();

	protected abstract void renderForeground();

	protected abstract void renderTooltips();

}
