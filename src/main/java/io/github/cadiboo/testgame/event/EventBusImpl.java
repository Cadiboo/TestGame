package io.github.cadiboo.testgame.event;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.util.Utils;

import static io.github.cadiboo.testgame.util.TypeResolver.resolveRawArgument;

/**
 * @author Cadiboo
 */
public abstract class EventBusImpl implements EventBus {

	private static final boolean DEBUG = Boolean.parseBoolean(System.getProperty(TestGame.NAMESPACE + ".debug.events"));

	@SuppressWarnings("unchecked")
	protected static <T extends Event> Class<T> getEventClass(final EventListener<T> listener) {
		final Class<T> eventClass = (Class<T>) resolveRawArgument(EventListener.class, listener.getClass());
		if (!Event.class.isAssignableFrom(eventClass)) // How is this possible
			throw new IllegalArgumentException(eventClass.getName() + " is not a subclass of Event. This should not be possible!");
		return eventClass;
	}

	@Override
	public <T extends Event> void post(final T event) {
		long startTime = 0;
		if (DEBUG)
			startTime = System.nanoTime();
		int i = 0;
		for (final EventListener listener : getListeners(event.getClass())) {
			try {
				listener.accept(event);
			} catch (Exception e) {
				throw new RuntimeException("Exception posting event \"" + event + "\" for listener \"" + listener + "\" at index " + i, e);
			}
			++i;
		}
		// i increments for generic events even if they don't accept the type
		if (DEBUG)
			System.out.println(EventBus.getEventName(event) + " took " + Utils.nanosToMillis(System.nanoTime() - startTime) + "ms and ran for " + i + " listener(s)");
	}

	@Override
	public <T extends Event> void register(final EventListener<T> listener) {
		addListeners(getEventClass(listener), listener);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <G, T extends GenericEvent<G>> void registerGeneric(final Class<G> genericClass, final EventListener<T> listener) {
		addListeners(getEventClass(listener), event -> {
			if (((GenericEvent<G>) event).type == genericClass)
				listener.accept((T) event);
		});
	}

	protected abstract <T extends Event> Iterable<EventListener<?>> getListeners(final Class<T> eventClass);

	private <T extends Event> void addListeners(final Class<? extends Event> clazz, final EventListener<T> listener) {
		addListenerToEventClass(clazz, listener);

		// get listeners for all parent classes and add them to this class
		Class<?> parentClass = clazz.getSuperclass();
		while (Event.class.isAssignableFrom(parentClass)) {
			for (final EventListener<?> otherListener : getListeners((Class<? extends Event>) parentClass))
				addListenerToEventClass(clazz, otherListener);
			parentClass = parentClass.getSuperclass();
		}
		// get all child classes and add this listener to their class
		for (final Class<? extends Event> otherClass : getEventClasses()) {
			if (clazz == otherClass || !clazz.isAssignableFrom(otherClass))
				continue;
			addListenerToEventClass(otherClass, listener);
		}
	}

	protected abstract Iterable<Class<? extends Event>> getEventClasses();

	protected abstract <T extends Event> void addListenerToEventClass(final Class<? extends Event> clazz, final EventListener<T> listener);

}


