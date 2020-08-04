package io.github.cadiboo.testgame.blockentity;

import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.util.Location;
import io.github.cadiboo.testgame.util.math.Pos;
import io.github.cadiboo.testgame.world.World;

import java.util.function.BiFunction;

/**
 * @author Cadiboo
 */
public class BlockEntityType<T extends BlockEntity> extends RegistryEntry<BlockEntityType<?>> {

	private final BiFunction<World, Pos, T> factory;

	public BlockEntityType(final Location registryName, final BiFunction<World, Pos, T> factory) {
		super(registryName);
		this.factory = factory;
	}

	public T create(World world, Pos pos) {
		return factory.apply(world, pos);
	}

}
