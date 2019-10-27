package io.github.cadiboo.testgame.event.bus;

import io.github.cadiboo.testgame.event.Event;

/**
 * @author Cadiboo
 */
@FunctionalInterface
public interface EventListener<T extends Event> {

	void accept(T event);

}
