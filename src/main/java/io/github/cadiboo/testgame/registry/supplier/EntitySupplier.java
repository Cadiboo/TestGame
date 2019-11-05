package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.entity.EntityType;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public class EntitySupplier<T extends EntityType> extends RegistrySupplier<T> {

	public static final Location REGISTRY = Location.of("entity_type");

	private EntitySupplier(final Location registryName, final Location registryRegistryName) {
		super(registryName, registryRegistryName);
	}

	public static <T extends EntityType> EntitySupplier<T> of(Location registryName) {
		return new EntitySupplier<>(registryName, REGISTRY);
	}

	@Override
	protected Registry getRegistry() {
		return Registries.ENTITY_TYPES;
	}

}
