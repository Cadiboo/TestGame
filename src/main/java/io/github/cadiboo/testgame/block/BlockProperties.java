package io.github.cadiboo.testgame.block;

/**
 * @author Cadiboo
 */
public class BlockProperties {

	int hardness = -1;
	int conductivity = -1;

	public BlockProperties setHardness(final int newValue) {
		this.hardness = newValue;
		return this;
	}

	public BlockProperties setConductivity(final int newValue) {
		this.conductivity = newValue;
		return this;
	}

}
