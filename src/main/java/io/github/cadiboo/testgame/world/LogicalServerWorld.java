package io.github.cadiboo.testgame.world;

import io.github.cadiboo.testgame.util.LogicalSide;

/**
 * @author Cadiboo
 */
public class LogicalServerWorld extends World {

	@Override
	public LogicalSide getLogicalSide() {
		return LogicalSide.LOGICAL_SERVER;
	}

	@Override
	public void update() {
		super.update();
	}

}
