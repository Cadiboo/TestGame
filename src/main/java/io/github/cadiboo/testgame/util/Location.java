package io.github.cadiboo.testgame.util;

import io.github.cadiboo.testgame.TestGame;

import java.util.Objects;

/**
 * @author Cadiboo
 */
public class Location {

	/**
	 * The character that separates namespace and path.
	 */
	public static final char SEPARATOR_CHAR = ':';
	/**
	 * A valid namespace value that can be used as a placeholder.
	 */
	public static final String DUMMY_NAMESPACE = "dummy";
	/**
	 * A valid path value that can be used as a placeholder.
	 */
	public static final String DUMMY_PATH = "dummy";
	private final String namespace;
	private final String path;

	private Location(final String namespace, final String path) {
		this.namespace = ensureValidNamespace(Utils.requireNotEmpty(Objects.requireNonNull(namespace, "namespace cannot be null!").toLowerCase(), "namespace cannot be empty!")).intern();
		this.path = ensureValidPath(Objects.requireNonNull(path, "path cannot be null!").toLowerCase()).intern();
	}

	public static Location of(final String location) {
		Objects.requireNonNull(location, "location cannot be null!");
		final int separatorIndex = location.indexOf(SEPARATOR_CHAR);
		if (separatorIndex == -1)
			// TODO: temp
			// throw new IllegalStateException();
			return of(TestGame.NAMESPACE, location);
		else
			return of(location.substring(0, separatorIndex), location.substring(separatorIndex));
	}

	public static Location of(final String domain, final String path) {
		return new Location(domain, path);
	}

	public static boolean isValidNamespace(/* Nonnull */ final String test) {
		return test.chars().allMatch(Location::isValidNamespaceChar);
	}

	public static boolean isValidPath(final String test) {
		return test.chars().allMatch(Location::isValidPathChar);
	}

	public static String ensureValidNamespace(final String namespace) {
		if (!isValidNamespace(namespace))
			throw new IllegalStateException(Localisation.format("invalid.namespace", namespace));
		return namespace;
	}

	public static String ensureValidPath(final String path) {
		if (!isValidPath(path))
			throw new IllegalStateException(Localisation.format("invalid.path", path));
		return path;
	}

	public static boolean isValidNamespaceChar(final int c) {
		return isValidNamespaceChar((char) c);
	}

	public static boolean isValidNamespaceChar(final char c) {
		return c >= '0' && c <= '9' // A number
				|| c >= 'a' && c <= 'z' // A lowercase alphabetic character
				|| c == '_' // An underscore
				;
	}

	public static boolean isValidPathChar(final int c) {
		return isValidPathChar((char) c);
	}

	public static boolean isValidPathChar(final char c) {
		return isValidNamespaceChar(c)
				|| c == '/' // A forward slash
				|| c == '.' // A dot
				|| c == '-' // A hyphen
				;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getPath() {
		return path;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = 31 * hash + namespace.hashCode();
		hash = 31 * hash + path.hashCode();
		return hash;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof Location))
			return false;
		final Location location = (Location) o;
		return namespace.equals(location.namespace) && path.equals(location.path);
	}

	@Override
	public String toString() {
		return namespace + SEPARATOR_CHAR + path;
	}

}
