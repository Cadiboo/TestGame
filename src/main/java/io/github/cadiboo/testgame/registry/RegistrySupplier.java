package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.util.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Cadiboo
 */
public abstract class RegistrySupplier<T extends RegistryEntry> {

	static final HashMap<Location, List<RegistrySupplier>> SUPPLIERS = new HashMap<>();
	private static final Function<Object, List<RegistrySupplier>> MAKE_ARRAY_LIST = k -> new ArrayList<>();

	public final Location registryName;
	T cached = null;

	public RegistrySupplier(Location registryName, Location registryRegistryName) {
		this.registryName = Objects.requireNonNull(registryName, "registryName cannot be null!");
		Objects.requireNonNull(registryRegistryName, "registryName of registry cannot be null!");
		final List<RegistrySupplier> registrySuppliers = SUPPLIERS.computeIfAbsent(registryRegistryName, MAKE_ARRAY_LIST);
		registrySuppliers.add(this);
	}

	public final T get() {
		if (cached != null) {
			return cached;
		}
		final Registry registry = Objects.requireNonNull(getRegistry(), "Cannot get entry before registry exists");
		return cached = (T) registry.get(registryName);
	}

	protected abstract Registry getRegistry();

}
