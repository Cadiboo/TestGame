package io.github.cadiboo.testgame.capability;

import io.github.cadiboo.testgame.save.Saveable;

/**
 * @author Cadiboo
 */
public interface Capability<T> extends Saveable<T> {

	@SuppressWarnings("unchecked")
	default <O extends Capability<?>> O cast() {
		return (O) this;
	}

}
