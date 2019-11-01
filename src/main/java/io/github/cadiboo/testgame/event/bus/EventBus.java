package io.github.cadiboo.testgame.event.bus;

import io.github.cadiboo.testgame.event.Event;
import io.github.cadiboo.testgame.event.GenericEvent;
import io.github.cadiboo.testgame.event.pooled.PooledEvent;

/**
 * @author Cadiboo
 */
public interface EventBus {

	/**
	 * Posts an event to all listeners for that event type on the event bus
	 *
	 * @param event The event to post
	 * @param <T>   The type of the event
	 */
	<T extends Event> void post(T event);

	/**
	 * Resets and posts a pooled event to all listeners for that event type on the event bus
	 *
	 * @param pooledEvent The event to post
	 * @param <T>         The type of the event
	 */
	default <T extends PooledEvent> void post(T pooledEvent) {
		pooledEvent.reset();
		post((Event) pooledEvent);
	}

	/**
	 * Registers an event listener
	 * The listener will be invoked later when an event of the same type is {@link #post posted} on this event bus
	 *
	 * @param listener The callback that will be invoked for events of the type
	 * @param <T>      The type of the event
	 */
	<T extends Event> void register(EventListener<T> listener);

	/**
	 * Registers a generic event listener
	 * The listener will be invoked later when an event of the same type is {@link #post posted} on this event bus
	 * Additionally, the listener will only be invoked if the generic class matches the generic type of the posted event
	 *
	 * @param genericClass The generic class filter
	 * @param listener     The callback that will be invoked for events of the type
	 * @param <G>          The generic type of the event
	 * @param <T>          The type of the event
	 */
	<G, T extends GenericEvent<G>> void registerGeneric(Class<G> genericClass, EventListener<T> listener);

}
