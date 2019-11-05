package io.github.cadiboo.testgame.mod;

/**
 * @author Cadiboo
 */
public class ModCandidate {

	final ModInfo modInfo;
	final ModClass modClass;

	public ModCandidate(final ModInfo modInfo, final ModClass modClass) {
		this.modInfo = modInfo;
		this.modClass = modClass;
	}

	@Override
	public String toString() {
		return modInfo + " | " + modClass.modClassName;
	}

}
