package io.github.cadiboo.testgame.fluid;

import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.util.Location;

/**
 * A Fluid is a substance that continually deforms under an applied shear stress.
 * Fluids are a subset of the phases of matter and include liquids, gases, plasmas, and to some extent, plastic solids.
 * Fluids are substances that have zero shear modulus,
 * or, in simpler terms,
 * a fluid is a substance which cannot resist any shear force applied to it.
 *
 * @author Cadiboo
 */
public class Fluid extends RegistryEntry<Fluid> {

	private final int flowRange;
	private final int flowSpeed;
	private final int mass;

	public Fluid(final Location registryName, FluidProperties properties) {
		super(registryName);
		this.flowRange = properties.flowRange;
		this.flowSpeed = properties.flowSpeed;
		this.mass = properties.mass;
	}

	public int getFlowRange() {
		return flowRange;
	}

	public int getFlowSpeed() {
		return flowSpeed;
	}

	public int getMass() {
		return mass;
	}

}
