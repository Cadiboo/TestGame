package io.github.cadiboo.testgame.event;

/**
 * @author Cadiboo
 */
@FunctionalInterface
public interface EventListener<T extends Event> {

	void accept(T event);

}
