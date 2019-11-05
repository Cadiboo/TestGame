package io.github.cadiboo.testgame.loader;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Cadiboo
 */
public class LoadPhase {

	final String name;
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
		private Runnable[] runnables;
		private String[] runBefore;
		private String[] runAfter;

		public Builder(final String name) {
			this.name = Objects.requireNonNull(name, "name cannot be null!");
		}

		private static <T> T[] joinArrays(final T[] array0, final T[] array1) {
			int l0 = array0.length;
			int l1 = array1.length;
			T[] joined = Arrays.copyOf(array0, l0 + l1);
			System.arraycopy(array1, 0, joined, l0, l1);
			return joined;
		}

		public Builder onRun(final Runnable... newRunnables) {
			Objects.requireNonNull(newRunnables, "newRunnables cannot be null!");
			for (final Runnable runnable : newRunnables)
				if (runnable == null)
					throw new NullPointerException("Runnable cannot be null!\n" + Arrays.toString(newRunnables));
			if (this.runnables == null)
				this.runnables = newRunnables;
			else {
				this.runnables = joinArrays(this.runnables, newRunnables);
			}
			return this;
		}

		public Builder runBefore(final String... newRunBefore) {
			Objects.requireNonNull(newRunBefore, "newRunBefore cannot be null!");
			for (final String str : newRunBefore)
				if (str == null)
					throw new NullPointerException("runBefore cannot be null!\n" + Arrays.toString(newRunBefore));
			if (this.runBefore == null)
				this.runBefore = newRunBefore;
			else {
				this.runBefore = joinArrays(this.runBefore, newRunBefore);
			}
			return this;
		}

		public Builder runAfter(final String... newRunAfter) {
			Objects.requireNonNull(newRunAfter, "newRunAfter cannot be null!");
			for (final String str : newRunAfter)
				if (str == null)
					throw new NullPointerException("runAfter cannot be null!\n" + Arrays.toString(newRunAfter));
			if (this.runAfter == null)
				this.runAfter = newRunAfter;
			else {
				this.runAfter = joinArrays(this.runAfter, newRunAfter);
			}
			return this;
		}

		public LoadPhase build() {
			if (runnables == null)
				runnables = new Runnable[0];
			if (runBefore == null)
				runBefore = new String[0];
			if (runAfter == null)
				runAfter = new String[0];
			return new LoadPhase(name, runnables, runBefore, runAfter);
		}

	}

}
