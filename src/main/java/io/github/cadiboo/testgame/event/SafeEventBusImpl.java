package io.github.cadiboo.testgame.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Cadiboo
 */
public class SafeEventBusImpl extends EventBusImpl {

	private HashMap<Class<? extends Event>, List<EventListener<?>>> listeners = new HashMap<>();

	@Override
	protected <T extends Event> Iterable<EventListener<?>> getListeners(final Class<T> eventClass) {
		return listeners.getOrDefault(eventClass, Collections.emptyList());
	}

	@Override
	protected Iterable<Class<? extends Event>> getEventClasses() {
		return listeners.keySet();
	}

	@Override
	protected <T extends Event> void addListenerToEventClass(final Class<? extends Event> clazz, final EventListener<T> listener) {
		listeners.computeIfAbsent(clazz, k -> new ArrayList<>()).add(listener);
	}

}
