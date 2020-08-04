package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.util.Location;
import io.github.cadiboo.testgame.util.Utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/**
 * @author Cadiboo
 */
public class RegistrationHelper<T extends RegistryEntry<T>> {

	private final String namespace;
	private final Registry<T> registry;

	private final Map<Location, Function<Location, ? extends T>> entries = new LinkedHashMap<>();

	public RegistrationHelper(final String namespace, final Registry<T> registry) {
		this.namespace = Location.ensureValidNamespace(namespace);
		this.registry = Objects.requireNonNull(registry);
	}

	public static <T extends RegistryEntry<T>> RegistrationHelper<T> of(final String namespace, final Registry<T> registry) {
		final RegistrationHelper<T> registrationHelper = new RegistrationHelper<>(namespace, registry);
		registry.registerInitialisationListener(registrationHelper::register);
		return registrationHelper;
	}

	private void register(final Registry<T> registry) {
		entries.forEach((registryName, function) -> {
			final T entry = function.apply(registryName);
			// Will be null if shouldRegister is false
			if (entry != null)
				registry.register(registryName, entry);
		});
	}

	public <U extends T> RegistryObject<U> register(final String registryPath, final Function<Location, U> function) {
		return registerIf(Utils.ALWAYS_TRUE, registryPath, function);
	}

	public <U extends T> RegistryObject<U> register(final Location registryName, final Function<Location, U> function) {
		return registerIf(Utils.ALWAYS_TRUE, registryName, function);
	}

	public <U extends T> RegistryObject<U> registerIf(final BooleanSupplier shouldRegister, final String registryPath, final Function<Location, U> function) {
		return registerIf(shouldRegister, Location.of(namespace, registryPath), function);
	}

	public <U extends T> RegistryObject<U> registerIf(final BooleanSupplier shouldRegister, final Location registryName, final Function<Location, U> function) {
		entries.put(registryName, $ -> shouldRegister.getAsBoolean() ? function.apply($) : null);
		return RegistryObject.of(registryName, registry);
	}

}
