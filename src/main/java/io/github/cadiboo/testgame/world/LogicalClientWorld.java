package io.github.cadiboo.testgame.world;

import io.github.cadiboo.testgame.util.LogicalSide;

/**
 * @author Cadiboo
 */
public class LogicalClientWorld extends World {

	@Override
	public LogicalSide getLogicalSide() {
		return LogicalSide.LOGICAL_CLIENT;
	}

	@Override
	public void update() {
		super.update();
	}

}
