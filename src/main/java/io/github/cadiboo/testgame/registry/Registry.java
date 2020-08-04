package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.event.registry.RegistryLoadedEvent;
import io.github.cadiboo.testgame.util.Location;
import io.github.cadiboo.testgame.util.TypeResolver;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Cadiboo
 */
public class Registry<T extends RegistryEntry<T>> extends RegistryEntry<Registry<?>> {

	private final Map<Location, T> entries = new ConcurrentHashMap<>();
	/**
	 * Maps registry names to listeners that want to be notified when the object for that registry name changes.
	 */
	private final Map<Location, List<Consumer<T>>> entryListeners = new HashMap<>();
	private final List<Consumer<Registry<T>>> initialisationListeners = new LinkedList<>();
	private final Class<T> genericType = (Class<T>) TypeResolver.resolveRawArgument(Registry.class, getClass());

	public Registry(final Location registryName) {
		super(registryName);
	}

	Registry(final String registryName) {
		this(Location.of(TestGame.NAMESPACE, registryName));
	}

	public <U extends T> void registerEntryListener(final RegistryObject<U> registryObject) {
		entryListeners.computeIfAbsent(registryObject.getRegistryName(), k -> new LinkedList<>())
			.add(newObject -> registryObject.value = (U) newObject);
	}

	public void registerInitialisationListener(final Consumer<Registry<T>> listener) {
		initialisationListeners.add(listener);
	}

	public <U extends T> void register(final Location registryName, final U entry) {
		entries.put(registryName, entry);
		entryListeners.getOrDefault(registryName, Collections.emptyList())
			.forEach(consumer -> consumer.accept(entry));
	}

	public void forEach(final BiConsumer<? super Location, ? super T> action) {
		entries.forEach(action);
	}

	public Collection<T> values() {
		return entries.values();
	}

	public void load() {
//		unlock();
		initialisationListeners.parallelStream().forEach(registryConsumer -> registryConsumer.accept(this));
//		lock();
		TestGame.EVENT_BUS.post(new RegistryLoadedEvent<>(this, this.genericType));
	}

	public T get(final char id) {
		return (T) entries.values().toArray()[id];
	}

	public T get(final Location id) {
		return entries.get(id);
	}

	public int size() {
		return entries.size();
	}

	public boolean isEmpty() {
		return entries.isEmpty();
	}

}
