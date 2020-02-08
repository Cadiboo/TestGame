package io.github.cadiboo.testgame.event;

/**
 * @author Cadiboo
 */
public class GenericEvent<T> extends Event {

	public final Class<T> type;

	public GenericEvent(Class<T> type) {
		this.type = type;
	}

}
