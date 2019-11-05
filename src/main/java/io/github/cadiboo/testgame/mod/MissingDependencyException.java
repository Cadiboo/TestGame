package io.github.cadiboo.testgame.mod;

/**
 * @author Cadiboo
 */
public class MissingDependencyException {

	private final ModInfo candidate;
	private final Dependency dependency;

	public MissingDependencyException(final ModInfo candidate, final Dependency dependency) {
		this.candidate = candidate;
		this.dependency = dependency;
	}

}
