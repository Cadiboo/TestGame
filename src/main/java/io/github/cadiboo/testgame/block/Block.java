package io.github.cadiboo.testgame.block;

import io.github.cadiboo.testgame.blockentity.BlockEntity;
import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public class Block extends RegistryEntry<Block> {

	private final int hardness;
	private final int conductivity;

	public Block(final Location registryName, BlockProperties properties) {
		super(registryName);
		this.hardness = properties.hardness;
		this.conductivity = properties.conductivity;
	}

	public int getHardness() {
		return hardness;
	}

	public int getConductivity() {
		return conductivity;
	}

	public boolean hasBlockEntity() {
		return false;
	}

	public BlockEntity createBlockEntity() {
		return null;
	}

}
