package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.util.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Cadiboo
 */
public class Registry<T extends RegistryEntry> {

	public final boolean supportsReplacement;
	public final boolean reloadable;
	private final Location registryName;
	private final LinkedHashMap<Location, T> entries = new LinkedHashMap<>();
	private final HashMap<Location, List<RegistrySupplier<T>>> suppliers = new HashMap<>();
	private final Class<T> type;

	private boolean locked;

	public Registry(final Location registryName, final boolean supportsReplacement, final boolean reloadable, final Class<T> type) {
		this.registryName = registryName;
		this.supportsReplacement = supportsReplacement;
		this.reloadable = reloadable;
		this.type = type;
	}

	public final RegisterEvent<T> createEvent() {
		return new RegisterEvent<>(this, this.type);
	}

	public void registerAll(final T... entries) {
		for (final T entry : entries) {
			register(entry);
		}
	}

	private void register(final T entry) {
		if (locked)
			throw new IllegalStateException("Registry is locked!");
		if (entry == null)
			throw new NullPointerException("entry to register cannot be null!");
		final Location registryName = entry.getRegistryName();
		if (registryName == null)
			throw new NullPointerException("registryName of entry (\"" + entry.getClass().getName() + "\") to register cannot be null!");
		final T oldValue = entries.put(registryName, entry);
		if (oldValue != null && !supportsReplacement) {
			entries.put(registryName, oldValue);
			throw new IllegalStateException();
		}
		final List<RegistrySupplier<T>> registrySuppliers = suppliers.get(registryName);
		if (registrySuppliers != null)
			for (final RegistrySupplier<T> supplier : registrySuppliers) {
				supplier.cached = entry;
			}
	}

	public void reload() {
		if (!this.reloadable)
			throw new IllegalStateException();
		entries.clear();
		load();
	}

	void unlock() {
		locked = false;
	}

	public void lock() {
		locked = true;
	}

	public void forEach(BiConsumer<Location, T> action) {
		entries.forEach(action);
	}

	public Collection<T> values() {
		return entries.values();
	}

	public T get(Location registryName) {
		return entries.get(registryName);
	}

	public boolean isLocked() {
		return locked;
	}

	public void fillSuppliers() {
		final List<RegistrySupplier> suppliersForMe = RegistrySupplier.SUPPLIERS.get(this.registryName);
		if (suppliersForMe == null || suppliersForMe.isEmpty()) {
			return;
		}
		for (RegistrySupplier<T> supplier : suppliersForMe) {
			this.suppliers.computeIfAbsent(supplier.registryName, k -> new ArrayList<>()).add(supplier);
		}
	}

	public void load() {
		fillSuppliers();
		unlock();
		TestGame.EVENT_BUS.post(createEvent());
		lock();
	}

}
