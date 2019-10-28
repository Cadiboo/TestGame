package io.github.cadiboo.testgame.loader;

import java.util.LinkedList;

/**
 * @author Cadiboo
 */
public final class Loader {

	private static final LinkedList<LoadEntry> entries = new LinkedList<>();
	private static int currentIndex = 0;
	static {
		LoadIndex.init();
	}

	public static boolean canLoad(final LoadEntry loadEntry) {
		return entries.indexOf(loadEntry) <= currentIndex;
	}

	public static void load() {
		while (currentIndex < entries.size()) {
			loadNext();
		}
	}

	public static LoadEntry add(final String name, Runnable onLoad) {
		final LoadEntry loadEntry = new LoadEntry(name, onLoad);
		entries.add(loadEntry);
		return loadEntry;
	}

	private static void loadNext() {
		final LoadEntry loadEntry = entries.get(currentIndex);
		try {
			loadEntry.onLoad.run();
		} catch (Exception e) {
			throw new RuntimeException("Caught exception from " + loadEntry + " (\"" + loadEntry.name + "\") at index " + currentIndex, e);
		}
		++currentIndex;
	}

	/**
	 * @author Cadiboo
	 */
	static class LoadEntry {

		final Runnable onLoad;
		private final String name;

		public LoadEntry(final String name, final Runnable onLoad) {
			this.name = name;
			this.onLoad = onLoad;
		}

	}

}
