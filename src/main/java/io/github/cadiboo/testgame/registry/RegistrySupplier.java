package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.util.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Cadiboo
 */
public abstract class RegistrySupplier<T extends RegistryEntry, R extends RegistryEntry> {

	static final HashMap<Location, List<RegistrySupplier>> SUPPLIERS = new HashMap<>();
	private static final Function<Object, List<RegistrySupplier>> MAKE_ARRAY_LIST = k -> new ArrayList<>();

	public final Location registryName;
	private final Supplier<Registry<R>> registrySupplier;
	T cached = null;

	public RegistrySupplier(final Location registryName, final Location registryRegistryName, final Supplier<Registry<R>> registrySupplier) {
		this.registryName = Objects.requireNonNull(registryName, "registryName cannot be null!");
		Objects.requireNonNull(registryRegistryName, "registryName of registry cannot be null!");
		final List<RegistrySupplier> registrySuppliers = SUPPLIERS.computeIfAbsent(registryRegistryName, MAKE_ARRAY_LIST);
		this.registrySupplier = Objects.requireNonNull(registrySupplier, "registrySupplier cannot be null!");
		registrySuppliers.add(this);
	}

	public final T get() {
		if (cached != null) {
			return cached;
		}
		final Registry<R> registry = Objects.requireNonNull(registrySupplier.get(), "Cannot get entry before registry exists");
		return cached = (T) registry.get(registryName);
	}

	@Override
	public int hashCode() {
		return registryName.hashCode();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final RegistrySupplier<?, ?> that = (RegistrySupplier<?, ?>) o;
		return registryName.equals(that.registryName);
	}

}
