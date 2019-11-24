package io.github.cadiboo.testgame.registry.supplier;

import io.github.cadiboo.testgame.entity.EntityType;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistrySupplier;
import io.github.cadiboo.testgame.util.Location;

import java.util.function.Supplier;

/**
 * @author Cadiboo
 */
public class EntityTypeSupplier<T extends EntityType> extends RegistrySupplier<T, EntityType> {

	public static final Location REGISTRY = Location.of("entity_type");
	public static final Supplier<Registry<EntityType>> REGISTRY_SUPPLIER = () -> Registries.ENTITY_TYPES;

	private EntityTypeSupplier(final Location registryName, final Location registryRegistryName, final Supplier<Registry<EntityType>> registrySupplier) {
		super(registryName, registryRegistryName, registrySupplier);
	}

	public static <T extends EntityType> EntityTypeSupplier<T> of(Location registryName) {
		return new EntityTypeSupplier<>(registryName, REGISTRY, REGISTRY_SUPPLIER);
	}

}
