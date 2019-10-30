package io.github.cadiboo.testgame.loader;

import java.util.LinkedList;

/**
 * @author Cadiboo
 */
public final class Loader {

	private static final LinkedList<LoadEntry> entries = new LinkedList<>();

	public static int getCurrentIndex() {
		return currentIndex;
	}

	private static int currentIndex = 0;
	static {
		LoadIndex.init();
	}

	public static boolean canLoad(final LoadEntry loadEntry) {
		return entries.indexOf(loadEntry) <= currentIndex;
	}

	public static void load() {
		while (getCurrentIndex() < getSize()) {
			loadNext();
		}
	}

	public static int getSize() {
		return entries.size();
	}

	public static LoadEntry add(final String name, Runnable onLoad) {
		final LoadEntry loadEntry = new LoadEntry(name, onLoad);
		entries.add(loadEntry);
		return loadEntry;
	}

	private static void loadNext() {
		final LoadEntry loadEntry = getCurrentEntry();
		try {
			loadEntry.onLoad.run();
		} catch (Exception e) {
			throw new RuntimeException("Caught exception from " + loadEntry + " (\"" + loadEntry.name + "\") at index " + currentIndex, e);
		}
		++currentIndex;
	}

	public static LoadEntry getCurrentEntry() {
		return entries.get(currentIndex);
	}

	/**
	 * @author Cadiboo
	 */
	public static class LoadEntry {

		final Runnable onLoad;
		public final String name;

		public LoadEntry(final String name, final Runnable onLoad) {
			this.name = name;
			this.onLoad = onLoad;
		}

	}

}
