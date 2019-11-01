package io.github.cadiboo.testgame.capability;

/**
 * @author Cadiboo
 */
public interface Capability<T> {

	@SuppressWarnings("unchecked")
	default <O extends Capability<?>> O cast() {
		return (O) this;
	}

}
