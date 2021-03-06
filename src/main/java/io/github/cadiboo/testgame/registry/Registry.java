package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.event.registry.RegisterEvent;
import io.github.cadiboo.testgame.event.registry.RegistryLoadedEvent;
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

	public static final char MAX_ID = Character.MAX_VALUE;
	public final boolean supportsReplacement;
	public final boolean reloadable;
	public final Location registryName;
	private final LinkedHashMap<Location, T> entries = new LinkedHashMap<>();
	private final HashMap<Location, List<RegistrySupplier<T, ?>>> suppliers = new HashMap<>();
	private final Class<T> type;
	private RegistryEntry[] ids = null;
	private boolean locked;

	public Registry(final Location registryName, final boolean supportsReplacement, final boolean reloadable, final Class<T> type) {
		this.registryName = registryName;
		this.supportsReplacement = supportsReplacement;
		this.reloadable = reloadable;
		this.type = type;
	}

	@SafeVarargs
	public final void registerAll(final T... entries) {
		for (final T entry : entries) {
			register(entry);
		}
	}

	public void register(final T entry) {
		if (locked)
			throw new IllegalStateException("Registry is locked!");

		if (entry == null)
			throw new NullPointerException("entry to register cannot be null!");

		if (!type.isAssignableFrom(entry.getClass()))
			throw new IllegalStateException("I hate generics. Registry type is \"" + type.getName() + "\" but entry being registered is \"" + entry.getClass() + "\"");

		final Location registryName = entry.getRegistryName();
		if (registryName == null)
			throw new NullPointerException("registryName of entry (\"" + entry.getClass().getName() + "\") to register cannot be null!");

		final int insertId = entries.size();
		final T oldValue = entries.put(registryName, entry);
		if (oldValue != null)
			if (!supportsReplacement) {
				entries.put(registryName, oldValue);
				throw new IllegalStateException("Registry does not support replacement");
			} else
				entry.setId(oldValue.getId());
		else {
			entry.setId(insertId);
		}
		final List<RegistrySupplier<T, ?>> registrySuppliers = suppliers.get(registryName);
		if (registrySuppliers != null)
			for (final RegistrySupplier<T, ?> supplier : registrySuppliers) {
				supplier.cached = entry;
			}
	}

	public void reload() {
		if (!this.reloadable)
			throw new IllegalStateException("Reload called on a non-reloadable registry!");
		entries.clear();
		load();
	}

	void unlock() {
		locked = false;
		ids = null;
	}

	public void lock() {
		locked = true;
		ids = new RegistryEntry[entries.size()];
		int i = 0;
		for (final T entry : entries.values()) {
			ids[i++] = entry;
		}
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

	@SuppressWarnings("unchecked")
	public T get(char id) {
		return (T) ids[id];
	}

	public boolean isLocked() {
		return locked;
	}

	@SuppressWarnings("unchecked")
	public void fillSuppliers() {
		final List<RegistrySupplier> suppliersForMe = RegistrySupplier.SUPPLIERS.remove(this.registryName);
		if (suppliersForMe == null || suppliersForMe.isEmpty()) {
			return;
		}
		for (RegistrySupplier<T, ?> supplier : suppliersForMe) {
			this.suppliers.computeIfAbsent(supplier.registryName, k -> new ArrayList<>()).add(supplier);
		}
	}

	public void load() {
		fillSuppliers();
		unlock();
		TestGame.EVENT_BUS.post(new RegisterEvent<>(this, this.type));
		lock();
		TestGame.EVENT_BUS.post(new RegistryLoadedEvent<>(this, this.type));
	}

	public int size() {
		return entries.size();
	}

}
