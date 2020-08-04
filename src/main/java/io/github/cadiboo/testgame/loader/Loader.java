package io.github.cadiboo.testgame.loader;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.util.TopologicalSort;
import io.github.cadiboo.testgame.util.TopologicalSort.TypedNode;
import io.github.cadiboo.testgame.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Cadiboo
 */
public final class Loader {

	private static final boolean DEBUG = Boolean.parseBoolean(System.getProperty(TestGame.NAMESPACE + ".debug.loader"));
	private static List<LoadPhase> phases = new ArrayList<>();
	/**
	 * Contains all the already loaded phases + the currently loading one
	 */
	private static Set<LoadPhase> loaded = new HashSet<>();
	private static boolean loading = false;

	public static boolean canLoad(final LoadPhase phase) {
		return loaded.contains(phase);
	}

	public static void load() {
		System.out.println("Sorting LoadPhases...");
		sortPhases(phases);
		System.out.println("Starting game...");
		loading = true;
		while (phases.size() > 0) {
			loadNext();
		}
		loading = false;
		System.out.println("Finished loading game...");
	}

	public static LoadPhase add(LoadPhase phase) {
		phases.add(phase);
		if (loading) {
			System.out.println("LoadPhase added while already loading! Re-sorting LoadPhases...");
			sortPhases(phases);
		}
		return phase;
	}

	private static void loadNext() {
		// Remove it now so that if more phases are added it isn't re-evaluated and/or re-run by a re-sort
		final LoadPhase phase = phases.remove(0);
		loaded.add(phase);
		long startTime = 0;
		if (DEBUG) {
			System.out.println("Loading phase \"" + phase.name + "\"");
			startTime = System.nanoTime();
		}
		try {
			for (final Runnable runnable : phase.runnables)
				runnable.run();
		} catch (Exception e) {
			throw new RuntimeException("Caught exception from " + phase + " (\"" + phase.name + "\") at index " + loaded.size(), e);
		}
		if (DEBUG)
			System.out.println("Phase \"" + phase.name + "\" took " + Utils.nanosToMillis(System.nanoTime() - startTime) + "ms");
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
					System.err.println("Phase \"" + phase.name + "\" wants to run before non-existent phase \"" + runBefore + "\"");
					continue;
				}
				// first.runsBefore(second) -> first.addEdge(second)
				node.addEdge(runBeforeNode);
			}
			for (final String runAfter : phase.runAfter) {
				final TopologicalSort.Node runAfterNode = map.get(runAfter);
				if (runAfterNode == null) {
					System.err.println("Phase \"" + phase.name + "\" wants to run after non-existent phase \"" + runAfter + "\"");
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
