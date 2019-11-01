package io.github.cadiboo.testgame.mods;

import io.github.cadiboo.testgame.mods.api.Mod;
import io.github.cadiboo.testgame.util.TopologicalSort;
import io.github.cadiboo.testgame.util.TopologicalSort.Node;
import io.github.cadiboo.testgame.util.TopologicalSort.TypedNode;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cadiboo
 */
public final class ModLoader {

	private static final String MOD_ANNOTATION_DESC = Type.getDescriptor(Mod.class);
	private static final List<ModCandidate> CANDIDATES = new ArrayList<>();
	private static final HashMap<String, ModObject> MODS = new HashMap<>();

	public static void findAndLoadMods() {
		try {
			final Enumeration<URL> resources = ModLoader.class.getClassLoader().getResources("");
			while (resources.hasMoreElements()) {
				recursivelyFindMods(new File(resources.nextElement().getPath()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Found " + CANDIDATES.size() + " ModCandidates");
		sortMods(CANDIDATES);
		CANDIDATES.forEach(modCandidate -> {
			final String modId = modCandidate.modId;
			System.out.println("Loading ModCandidate " + modId);
			final String modClassName = modCandidate.modClassName;
			System.out.println("Loading mod class " + modClassName);
			final Class<?> modClass;
			try {
				modClass = Thread.currentThread().getContextClassLoader().loadClass(modClassName);
				System.out.println("Initialising mod class " + modClassName);
				MODS.put(modId, new ModObject(modClass.newInstance(), modId));
				System.out.println("Loaded mod \"" + modId + "\"");
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
				e.printStackTrace();
			}
		});
	}

	private static void recursivelyFindMods(final File root) throws IOException {
		for (File file : root.listFiles()) {
			if (file.isDirectory()) {
				recursivelyFindMods(file);
				continue;
			}

			if (!file.getName().endsWith(".class"))
				continue;

			try (InputStream is = Files.newInputStream(file.toPath())) {
				final ClassNode classNode = new ClassNode();
				new ClassReader(is).accept(classNode, 0);

				if (classNode.visibleAnnotations == null || classNode.visibleAnnotations.isEmpty())
					continue;

				for (AnnotationNode annotationNode : classNode.visibleAnnotations) {
					if (!annotationNode.desc.equals(MOD_ANNOTATION_DESC))
						continue;

					String modId = null;
					String[] loadBefore = null;
					String[] loadAfter = null;
					final List<Object> values = annotationNode.values;
					for (int i = 0; i < values.size(); i++) {
						if (values.get(i).equals("value"))
							modId = (String) values.get(i + 1);
						if (values.get(i).equals("loadBefore"))
							loadBefore = (String[]) ((ArrayList) values.get(i + 1)).toArray(new String[0]);
						if (values.get(i).equals("loadAfter"))
							loadAfter = (String[]) ((ArrayList) values.get(i + 1)).toArray(new String[0]);
					}

					if (loadBefore == null)
						loadBefore = new String[0];
					if (loadAfter == null)
						loadAfter = new String[0];

					if (modId == null) {
						System.err.println("Error: mod candidate \"" + classNode.name + "\" has no modId!");
						break;
					}
					if (modId.length() < 8 || modId.length() > 64) {
						System.err.println("Error: mod candidate \"" + classNode.name + "\" has an invalid modId!");
						break;
					}

					if (loadBefore.length > 0 || loadAfter.length > 0) {
						System.out.println("Mod candidate \"" + modId + "\" wants to load");
						for (final String s : loadBefore)
							System.out.println("  Before: " + s);
						for (final String s : loadAfter)
							System.out.println("  After: " + s);
					}

					final String modClassName = classNode.name.replace('/', '.');

					CANDIDATES.add(new ModCandidate(modId, modClassName, loadBefore, loadAfter));
					break;
				}
			}
		}
	}

	public static void sortMods(final List<ModCandidate> modCandidates) {

		final Map<String, Node> map = new HashMap<>();

		for (final ModCandidate modCandidate : modCandidates)
			if (map.put(modCandidate.modId, new TypedNode<>(modCandidate)) != null)
				throw new RuntimeException("Duplicate ModCandidates: " + modCandidate.modId);

		for (final ModCandidate modCandidate : modCandidates) {
			final Node node = map.get(modCandidate.modId);
			for (final String runBefore : modCandidate.loadBefore) {
				final Node runBeforeNode = map.get(runBefore);
				if (runBeforeNode == null) {
					System.out.println("ModCandidate " + modCandidate.modId + " wants to run before non-existent modCandidate " + runBefore);
					continue;
				}
				// first.runsBefore(second) -> first.addEdge(second)
				node.addEdge(runBeforeNode);
			}
			for (final String runAfter : modCandidate.loadAfter) {
				final Node runAfterNode = map.get(runAfter);
				if (runAfterNode == null) {
					System.out.println("ModCandidate " + modCandidate.modId + " wants to run after non-existent modCandidate " + runAfter);
					continue;
				}
				// first.runsBefore(second) -> first.addEdge(second)
				runAfterNode.addEdge(node);
			}
		}
		modCandidates.clear();
		for (final Node node : TopologicalSort.sort(map.values()))
			modCandidates.add(((TypedNode<ModCandidate>) node).object);
	}

	public static Object getMod(String modId) {
		final ModObject modObject = MODS.get(modId);
		if (modObject == null)
			return null;
		return modObject.instance;
	}

	private static class ModCandidate {

		final String modId;
		final String modClassName;
		final String[] loadBefore;
		final String[] loadAfter;

		private ModCandidate(final String modId, final String modClassName, final String[] runBefore, final String[] runAfter) {
			this.modId = modId;
			this.modClassName = modClassName;
			this.loadBefore = runBefore;
			this.loadAfter = runAfter;
		}

		@Override
		public String toString() {
			return modId;
		}

	}

}
