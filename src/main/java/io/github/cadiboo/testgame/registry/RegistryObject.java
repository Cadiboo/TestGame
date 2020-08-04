package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.util.Location;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Cadiboo
 */
public class RegistryObject<T extends RegistryEntry<? super T>> implements Supplier<T> {

	private final Location registryName;
	private final Registry<? super T> registry;
//	@Nullable
	T value;

	protected RegistryObject(final Location registryName, final Registry<? super T> registry) {
		this.registryName = Objects.requireNonNull(registryName);
		this.registry = Objects.requireNonNull(registry);
	}

	public static <T extends RegistryEntry<T>, U extends T> RegistryObject<U> of(final Location registryName, final Registry<T> registry) {
		final RegistryObject<U> registryObject = new RegistryObject<>(registryName, registry);
		registry.registerEntryListener(registryObject);
		if (!registry.isEmpty())
			registryObject.value = (U) registry.get(registryName);
		return registryObject;
	}

	@Override
	public T get() {
		return value;
	}

	public Location getRegistryName() {
		return registryName;
	}

}
