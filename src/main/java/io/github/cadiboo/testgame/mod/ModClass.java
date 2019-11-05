package io.github.cadiboo.testgame.mod;

/**
 * @author Cadiboo
 */
class ModClass {

	final String modId;
	final String modClassName;

	public ModClass(final String modId, final String modClassName) {
		this.modId = modId;
		this.modClassName = modClassName;
	}

	@Override
	public String toString() {
		return modId + " on " + modClassName;
	}

}
