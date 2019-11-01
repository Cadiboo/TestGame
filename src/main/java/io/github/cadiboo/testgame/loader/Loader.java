package io.github.cadiboo.testgame.loader;

import io.github.cadiboo.testgame.util.TopologicalSort;
import io.github.cadiboo.testgame.util.TopologicalSort.TypedNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cadiboo
 */
public final class Loader {

	private static List<LoadPhase> phases = new ArrayList<>();
	private static int currentIndex = -1;
	static {
		LoadIndex.touch();
	}

	public static int getCurrentIndex() {
		return currentIndex;
	}

	public static boolean canLoad(final LoadPhase loadPhase) {
		return phases.indexOf(loadPhase) <= currentIndex;
	}

	public static void load() {
		System.out.println("Sorting LoadPhases...");
		sortPhases(phases);
		System.out.println("Starting game...");
		currentIndex = 0;
		while (getCurrentIndex() < getSize()) {
			loadNext();
		}
		System.out.println("Finished loading game...");
		currentIndex = -1;
	}

	public static int getSize() {
		return phases.size();
	}

	public static LoadPhase add(LoadPhase loadPhase) {
		phases.add(loadPhase);
		if (isRunning())
			sortPhases(phases);
		return loadPhase;
	}

	private static boolean isRunning() {
		return currentIndex > -1;
	}

	private static void loadNext() {
		final LoadPhase loadEntry = getCurrentEntry();
		try {
			for (final Runnable runnable : loadEntry.runnables)
				runnable.run();
		} catch (Exception e) {
			throw new RuntimeException("Caught exception from " + loadEntry + " (\"" + loadEntry.name + "\") at index " + currentIndex, e);
		}
		++currentIndex;
	}

	public static LoadPhase getCurrentEntry() {
		return phases.get(currentIndex);
	}

	private static void sortPhases(final List<LoadPhase> phases) {

		final Map<String, TopologicalSort.Node> map = new HashMap<>();

		for (final LoadPhase phase : phases)
			if (map.put(phase.name, new TypedNode<>(phase)) != null)
				throw new RuntimeException("Duplicate Phases: " + phase.name);

		for (final LoadPhase phase : phases) {
			final TopologicalSort.Node node = map.get(phase.name);
			for (final String runBefore : phase.runBefore) {
				final TopologicalSort.Node runBeforeNode = map.get(runBefore);
				if (runBeforeNode == null) {
					System.out.println("Phase " + phase.name + " wants to run before non-existent phase " + runBefore);
					continue;
				}
				// first.runsBefore(second) -> first.addEdge(second)
				node.addEdge(runBeforeNode);
			}
			for (final String runAfter : phase.runAfter) {
				final TopologicalSort.Node runAfterNode = map.get(runAfter);
				if (runAfterNode == null) {
					System.out.println("Phase " + phase.name + " wants to run after non-existent phase " + runAfter);
					continue;
				}
				// first.runsBefore(second) -> first.addEdge(second)
				runAfterNode.addEdge(node);
			}
		}
		phases.clear();
		for (final TopologicalSort.Node node : TopologicalSort.sort(map.values()))
			phases.add(((TypedNode<LoadPhase>) node).object);
	}

}
