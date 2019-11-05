package io.github.cadiboo.testgame.util.version;

/**
 * @author Cadiboo
 */
public class Version {

	private final String string;

	public Version(final String string) {
		this.string = string;
	}

	public static Version of(final String s) {
		//TODO
		return new Version(s);
	}

	@Override
	public String toString() {
		return string;
	}

}
