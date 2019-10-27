package io.github.cadiboo.testgame.event;

/**
 * @author Cadiboo
 */
public class GenericEvent<T> extends Event {

	public final Class<T> type;

	public GenericEvent() {
		this.type = null;
	}

	protected GenericEvent(Class<T> type) {
		this.type = type;
	}

}
