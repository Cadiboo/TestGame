package io.github.cadiboo.testgame.block;

import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.init.BlockEntityTypes;
import io.github.cadiboo.testgame.util.Location;
import io.github.cadiboo.testgame.util.math.Pos;
import io.github.cadiboo.testgame.world.World;

/**
 * @author Cadiboo
 */
public class StorageBlock extends Block {

	public StorageBlock(final Location registryName, final BlockProperties properties) {
		super(registryName, properties);
	}

	@Override
	public boolean hasBlockEntity() {
		return true;
	}

	@Override
	public BlockEntityType<?> getBlockEntityType(final World world, final Pos pos) {
		return BlockEntityTypes.STORAGE.get();
	}

}
