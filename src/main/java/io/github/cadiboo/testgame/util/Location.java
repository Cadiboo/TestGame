package io.github.cadiboo.testgame.util;

import io.github.cadiboo.testgame.TestGame;

import java.util.Objects;

/**
 * @author Cadiboo
 */
public class Location {

	private final String domain;
	private final String path;

	private Location(final String domain, final String path) {
		this.domain = Objects.requireNonNull(domain, "domain cannot be null!").toLowerCase();
		this.path = Objects.requireNonNull(path, "path cannot be null!").toLowerCase();
	}

	public static Location of(final String location) {
		return of(TestGame.DOMAIN, location);
	}

	public static Location of(final String domain, final String path) {
		return new Location(domain, path);
	}

	public String getDomain() {
		return domain;
	}

	public String getPath() {
		return path;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = 31 * hash + domain.hashCode();
		hash = 31 * hash + path.hashCode();
		return hash;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof Location)) {
			return false;
		}
		final Location location = (Location) o;
		return domain.equals(location.domain) &&
				path.equals(location.path);
	}

	@Override
	public String toString() {
		return domain + ":" + path;
	}

}
