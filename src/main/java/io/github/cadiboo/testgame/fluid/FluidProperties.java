package io.github.cadiboo.testgame.fluid;

/**
 * @author Cadiboo
 */
public class FluidProperties {

	int flowRange = 0;
	int flowSpeed = 0;
	int mass = 0;

	public FluidProperties setFlowRange(final int newValue) {
		this.flowRange = newValue;
		return this;
	}

	public FluidProperties setFlowSpeed(final int newValue) {
		this.flowSpeed = newValue;
		return this;
	}

	public FluidProperties setMass(final int newValue) {
		this.mass = newValue;
		return this;
	}

}
