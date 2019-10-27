package io.github.cadiboo.testgame.registry;

/**
 * @author Cadiboo
 */
public class RegistryProperties {

	boolean supportsReplacement;
	boolean reloadable;

	public RegistryProperties supportsReplacement() {
		supportsReplacement = true;
		return this;
	}

	public RegistryProperties reloadable() {
		reloadable = true;
		return this;
	}

}
