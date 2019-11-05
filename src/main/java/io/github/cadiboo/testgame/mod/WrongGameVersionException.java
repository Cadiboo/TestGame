package io.github.cadiboo.testgame.mod;

import io.github.cadiboo.testgame.util.version.Version;

/**
 * @author Cadiboo
 */
public class WrongGameVersionException extends Exception {

	private final ModInfo candidate;
	private final Version gameVersion;

	public WrongGameVersionException(final ModInfo candidate, final Version gameVersion) {
		this.candidate = candidate;
		this.gameVersion = gameVersion;
	}

	@Override
	public String toString() {
		return "WrongGameVersionException {" +
				"candidate=" + candidate +
				", gameVersionRange=" + candidate.gameVersionRange +
				", gameVersion=" + gameVersion +
				'}';
	}

}
