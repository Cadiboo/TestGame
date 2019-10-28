package io.github.cadiboo.testgame.event.bus;

import io.github.cadiboo.testgame.event.Event;
import io.github.cadiboo.testgame.event.GenericEvent;
import io.github.cadiboo.testgame.event.pooled.PooledEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static io.github.cadiboo.testgame.util.TypeResolver.resolveRawArgument;

/**
 * @author Cadiboo
 */
public class EventBus {

	private static final Function<Class<? extends Event>, List<EventListener>> MAKE_LISTENERS_LIST_FUNCTION = k -> new ArrayList<>();
	private static final HashMap<EventListener, Class<? extends Event>> EVENT_LOOKUP = new HashMap<>();

	private HashMap<Class<? extends Event>, List<EventListener>> listeners = new HashMap<>();

	protected static <T extends Event> Class<T> getEventClass(final EventListener<T> listener) {
		@SuppressWarnings("unchecked") final Class<T> eventClass = (Class<T>) resolveRawArgument(EventListener.class, listener.getClass());
		if (Object.class.equals(eventClass)) // How is this possible
			throw new IllegalStateException(eventClass.getName());
		EVENT_LOOKUP.put(listener, eventClass);
		return eventClass;
	}

	public void post(final Event event) {
		for (final EventListener listener : getListeners(event)) {
			try {
				listener.accept(event);
			} catch (Exception e) {
				throw new RuntimeException("Exception posting event \"" + event + "\" for listener \"" + listener + "\"");
			}
		}
	}

	public void post(final PooledEvent event) {
		event.reset();
		post((Event) event);
	}

	private List<EventListener> getListeners(final Event event) {
		return listeners.getOrDefault(event.getClass(), Collections.emptyList());
	}

	public <T extends Event> void registerGeneric(final Class<?> genericClass, final EventListener<T> listener) {
		getOrCreateListenerList(listener).add(event -> {
			if (((GenericEvent) event).type == genericClass)
				listener.accept((T) event);
		});
	}

	public <T extends Event> void register(final EventListener<T> listener) {
		getOrCreateListenerList(listener).add(listener);
	}

	private <T extends Event> List<EventListener> getOrCreateListenerList(final EventListener<T> listener) {
		return listeners.computeIfAbsent(getEventClass(listener), MAKE_LISTENERS_LIST_FUNCTION);
	}

}

