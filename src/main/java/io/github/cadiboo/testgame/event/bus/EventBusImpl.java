package io.github.cadiboo.testgame.event.bus;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.event.Event;
import io.github.cadiboo.testgame.event.GenericEvent;
import io.github.cadiboo.testgame.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static io.github.cadiboo.testgame.util.TypeResolver.resolveRawArgument;

/**
 * @author Cadiboo
 */
public class EventBusImpl implements EventBus {

	private static final boolean DEBUG = Boolean.parseBoolean(System.getProperty(TestGame.DOMAIN + ".debug.events"));
	private static final Function<Class<? extends Event>, List<EventListener<?>>> MAKE_LISTENERS_LIST_FUNCTION = k -> new ArrayList<>();

	private HashMap<Class<? extends Event>, List<EventListener<?>>> listeners = new HashMap<>();

	@SuppressWarnings("unchecked")
	protected static <T extends Event> Class<T> getEventClass(final EventListener<T> listener) {
		final Class<T> eventClass = (Class<T>) resolveRawArgument(EventListener.class, listener.getClass());
		if (!Event.class.isAssignableFrom(eventClass)) // How is this possible
			throw new IllegalArgumentException(eventClass.getName() + " is not a subclass of Event. This should not be possible!");
		return eventClass;
	}

	protected static String getEventName(final Event event) {
		String name = event.getClass().getSimpleName();
		if (event instanceof GenericEvent)
			name += "<" + ((GenericEvent) event).type.getSimpleName() + ">";
		return name;
	}

	@Override
	public <T extends Event> void post(final T event) {
		long startTime = 0;
		if (DEBUG)
			startTime = System.nanoTime();
		for (final EventListener listener : getListeners(event)) {
			try {
				listener.accept(event);
			} catch (Exception e) {
				throw new RuntimeException("Exception posting event \"" + event + "\" for listener \"" + listener + "\"", e);
			}
		}
		if (DEBUG)
			System.out.println(getEventName(event) + " took " + Utils.nanosToMillis(System.nanoTime() - startTime) + "ms");
	}

	@Override
	public <T extends Event> void register(final EventListener<T> listener) {
		getOrCreateListenerList(listener).add(listener);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <G, T extends GenericEvent<G>> void registerGeneric(final Class<G> genericClass, final EventListener<T> listener) {
		getOrCreateListenerList(listener).add(event -> {
			if (((GenericEvent<G>) event).type == genericClass)
				listener.accept((T) event);
		});
	}

	protected <T extends Event> Iterable<EventListener<?>> getListeners(final T event) {
		return listeners.getOrDefault(event.getClass(), Collections.emptyList());
	}

	private <T extends Event> List<EventListener<?>> getOrCreateListenerList(final EventListener<T> listener) {
		return listeners.computeIfAbsent(getEventClass(listener), MAKE_LISTENERS_LIST_FUNCTION);
	}

}


