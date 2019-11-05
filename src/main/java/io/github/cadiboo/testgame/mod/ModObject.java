package io.github.cadiboo.testgame.mod;

/**
 * @author Cadiboo
 */
public class ModObject {

	public final Object instance;
	public final String modId;
	public final ModInfo modInfo;

	public ModObject(final Object instance, final String modId, final ModInfo modInfo) {
		this.instance = instance;
		this.modId = modId;
		this.modInfo = modInfo;
	}

}
