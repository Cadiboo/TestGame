package io.github.cadiboo.testgame.util.version;

/**
 * @author Cadiboo
 */
public class VersionRange {

	private final String string;

	public VersionRange(final String string) {
		this.string = string;
	}

	public boolean matches(final Version version) {
		//TODO
		return string.equals(version.toString());
	}

	@Override
	public String toString() {
		return string;
	}

}
