package io.github.cadiboo.testgame.event.registry;

import io.github.cadiboo.testgame.event.GenericEvent;
import io.github.cadiboo.testgame.registry.Registry;
import io.github.cadiboo.testgame.registry.RegistryEntry;

/**
 * @author Cadiboo
 */
public class RegistryLoadedEvent<T extends RegistryEntry> extends GenericEvent<T> {

	private final Registry<T> registry;

	public RegistryLoadedEvent(final Registry<T> registry, Class<T> type) {
		super(type);
		this.registry = registry;
	}

	public Registry<T> getRegistry() {
		return registry;
	}

}
