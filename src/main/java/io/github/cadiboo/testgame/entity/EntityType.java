package io.github.cadiboo.testgame.entity;

import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.util.Location;
import io.github.cadiboo.testgame.util.math.Vec3d;
import io.github.cadiboo.testgame.world.World;

import java.util.function.BiFunction;

/**
 * @author Cadiboo
 */
public class EntityType<T extends Entity> extends RegistryEntry<EntityType<?>> {

	private final BiFunction<World, Vec3d, T> factory;

	public EntityType(final Location registryName, final BiFunction<World, Vec3d, T> factory) {
		super(registryName);
		this.factory = factory;
	}

	public T create(World world, Vec3d vec3d) {
		return factory.apply(world, vec3d);
	}

}
