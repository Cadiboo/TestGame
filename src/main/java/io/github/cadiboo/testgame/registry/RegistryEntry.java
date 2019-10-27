package io.github.cadiboo.testgame.registry;

import io.github.cadiboo.testgame.util.Location;

/**
 * @author Cadiboo
 */
public interface RegistryEntry<T> {

//	@SuppressWarnings("unchecked")
//	default T getThis() {
//		return (T) this;
//	}

	Location getRegistryName();

}
