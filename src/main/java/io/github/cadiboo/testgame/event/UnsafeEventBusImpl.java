package io.github.cadiboo.testgame.event;

import io.github.cadiboo.testgame.util.UnsafeHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cadiboo
 */
public class UnsafeEventBusImpl extends EventBusImpl {

	private static final Map<Class<? extends Event>, Event> defaultInstances = new HashMap<>();

	@Override
	protected <T extends Event> Iterable<EventListener<?>> getListeners(final Class<T> eventClass) {
		final Event defaultInstance = defaultInstances.get(eventClass);
		if (defaultInstance == null)
			return Collections.emptyList();
		return defaultInstance.listeners;
	}

	@Override
	protected Iterable<Class<? extends Event>> getEventClasses() {
		return defaultInstances.keySet();
	}

	@Override
	protected <T extends Event> void addListenerToEventClass(final Class<? extends Event> clazz, final EventListener<T> listener) {
		final Event event = defaultInstances.computeIfAbsent(clazz, k -> makeDefault(clazz));
		if (event.listeners == null)
			event.listeners = new ArrayList<>();
		event.listeners.add(listener);
	}

	private Event makeDefault(final Class<? extends Event> clazz) {
		try {
			return UnsafeHelper.allocateInstance(clazz);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
	}

}


