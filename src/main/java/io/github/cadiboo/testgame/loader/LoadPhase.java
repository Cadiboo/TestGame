package io.github.cadiboo.testgame.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Cadiboo
 */
public class LoadPhase {

	public final String name;
	final Runnable[] runnables;
	final String[] runBefore;
	final String[] runAfter;

	private LoadPhase(final String name, final Runnable[] runnables, final String[] runBefore, final String[] runAfter) {
		this.name = name;
		this.runnables = runnables;
		this.runBefore = runBefore;
		this.runAfter = runAfter;
	}

	public static class Builder {

		private final String name;
		private List<Runnable> runnables;
		private String[] runBefore;
		private String[] runAfter;

		public Builder(final String name) {
			this.name = name;
		}

		public Builder onRun(final Runnable... runnables) {
			if (this.runnables == null)
				this.runnables = Arrays.asList(runnables);
			else
				this.runnables.addAll(Arrays.asList(runnables));
			return this;
		}

		public Builder runBefore(final String... runBefore) {
			this.runBefore = runBefore;
			return this;
		}

		public Builder runAfter(final String... runAfter) {
			this.runAfter = runAfter;
			return this;
		}

		public LoadPhase build() {
			if (runnables == null)
				runnables = new ArrayList<>();
			if (runBefore == null)
				runBefore = new String[0];
			if (runAfter == null)
				runAfter = new String[0];
			return new LoadPhase(name, runnables.toArray(new Runnable[0]), runBefore, runAfter);
		}

	}

}
