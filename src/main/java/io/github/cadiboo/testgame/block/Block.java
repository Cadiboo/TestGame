package io.github.cadiboo.testgame.block;

import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public class Block implements RegistryEntry<Block> {

	private final Location registryName;
	private final int hardness;
	private final int conductivity;

	public Block(final Location registryName, BlockProperties properties) {
		this.registryName = registryName;
		this.hardness = properties.hardness;
		this.conductivity = properties.conductivity;
	}

	@Override
	public final Location getRegistryName() {
		return registryName;
	}

	public int getHardness() {
		return hardness;
	}

	public int getConductivity() {
		return conductivity;
	}

}
