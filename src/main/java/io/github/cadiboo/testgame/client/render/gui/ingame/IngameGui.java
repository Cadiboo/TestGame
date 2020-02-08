package io.github.cadiboo.testgame.client.render.gui.ingame;

import io.github.cadiboo.testgame.client.render.gui.Gui;

/**
 * @author Cadiboo
 */
public abstract class IngameGui extends Gui {

	public IngameGui(final int width, final int height) {
		super(width, height);
	}

	public abstract boolean pausesGame();

}
