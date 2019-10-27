package io.github.cadiboo.testgame.loader;

import java.util.ArrayList;

/**
 * @author Cadiboo
 */
public final class Loader {

	private static final ArrayList<LoadEntry> entries = new ArrayList<>();
	private static int currentIndex = 0;
	static {
		LoadIndex.init();
	}

	public static boolean canLoad(final LoadEntry loadEntry) {
		return loadEntry.index <= currentIndex;
	}

	public static void load() {
		while (currentIndex < entries.size()) {
			loadNext();
		}
	}

	public static LoadEntry add(Runnable onLoad) {
		final LoadEntry loadEntry = new LoadEntry(entries.size(), onLoad);
		entries.add(loadEntry);
		return loadEntry;
	}

	private static void loadNext() {
		final LoadEntry loadEntry = entries.get(currentIndex);
		try {
			loadEntry.onLoad.run();
		} catch (Exception e) {
			throw new RuntimeException("Caught exception from " + loadEntry + " at index " + currentIndex, e);
		}
		++currentIndex;
	}

	/**
	 * @author Cadiboo
	 */
	static class LoadEntry {

		final int index;
		final Runnable onLoad;

		public LoadEntry(final int index, final Runnable onLoad) {
			this.index = index;
			this.onLoad = onLoad;
		}

	}

}
