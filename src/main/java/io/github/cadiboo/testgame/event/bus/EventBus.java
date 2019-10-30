package io.github.cadiboo.testgame.event.bus;

import io.github.cadiboo.testgame.event.Event;
import io.github.cadiboo.testgame.event.GenericEvent;
import io.github.cadiboo.testgame.event.pooled.PooledEvent;

/**
 * @author Cadiboo
 */
public interface EventBus {

	<T extends Event> void post(T event);

	<T extends PooledEvent> void post(T event);

	<G, T extends GenericEvent<G>> void registerGeneric(Class<G> genericClass, EventListener<T> listener);

	<T extends Event> void register(EventListener<T> listener);

}
