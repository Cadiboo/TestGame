package io.github.cadiboo.testgame.mods;

import io.github.cadiboo.testgame.mods.api.Mod;

/**
 * @author Cadiboo
 */
public final class ModObject {

	public final Mod instance;
	public final String modId;

	public ModObject(final Mod instance, final String modId) {
		this.instance = instance;
		this.modId = modId;
	}

}
