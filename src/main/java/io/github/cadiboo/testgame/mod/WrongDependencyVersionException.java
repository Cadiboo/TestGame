package io.github.cadiboo.testgame.mod;

/**
 * @author Cadiboo
 */
public class WrongDependencyVersionException {

	private final ModInfo candidate;
	private final Dependency dependency;

	public WrongDependencyVersionException(final ModInfo candidate, final Dependency dependency) {
		this.candidate = candidate;
		this.dependency = dependency;
	}

}
