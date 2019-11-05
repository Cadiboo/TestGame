package io.github.cadiboo.testgame.block;

import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.util.Location;
import io.github.cadiboo.testgame.util.math.Pos;
import io.github.cadiboo.testgame.world.World;

/**
 * @author Cadiboo
 */
public class Block extends RegistryEntry<Block> {

	private final int hardness;
	private final int conductivity;

	public Block(final Location registryName, final BlockProperties properties) {
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

	public BlockEntityType<?> getBlockEntityType(final World world, final Pos pos) {
		return null;
	}

}
