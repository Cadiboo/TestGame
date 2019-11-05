package io.github.cadiboo.testgame.mod;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.api.Mod;
import io.github.cadiboo.testgame.util.Distribution;
import io.github.cadiboo.testgame.util.TopologicalSort;
import io.github.cadiboo.testgame.util.TopologicalSort.Node;
import io.github.cadiboo.testgame.util.TopologicalSort.TypedNode;
import io.github.cadiboo.testgame.util.Utils;
import io.github.cadiboo.testgame.util.version.Version;
import io.github.cadiboo.testgame.util.version.VersionRange;
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
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Cadiboo
 */
public final class ModLoader {

	private static final String MOD_ANNOTATION_DESC = Type.getDescriptor(Mod.class);
	private static final List<ModCandidate> CANDIDATES = new ArrayList<>();
	private static final HashMap<String, ModObject> MODS = new HashMap<>();

	public static void findAndLoadMods() {
		findAllModCandidates();
		System.out.println("Found " + CANDIDATES.size() + " ModCandidates");
		checkDistributions();
		validateCompatibility();
		CANDIDATES.forEach(modCandidate -> modCandidate.modInfo.computeBeforeAndAfterModIds());
		sortMods(CANDIDATES);
		loadMods(CANDIDATES);
	}

	private static void loadMods(final List<ModCandidate> candidates) {
		candidates.forEach(modCandidate -> {
			System.out.println("Loading ModCandidate " + modCandidate);
			try {
				final String modId = modCandidate.modInfo.modId;
				final String modClassName = modCandidate.modClass.modClassName;
				final Class<?> modClass = Thread.currentThread().getContextClassLoader().loadClass(modClassName);
				MODS.put(modId, new ModObject(modClass.newInstance(), modId, modCandidate.modInfo));
				System.out.println("Loaded mod \"" + modId + "\"");
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
				e.printStackTrace();
			}
		});
	}

	private static void checkDistributions() {
		CANDIDATES.forEach(modCandidate -> {
			final ModInfo modInfo = modCandidate.modInfo;
			if (modInfo.distribution == null)
				modInfo.distribution = getCurrentDistribution();
		});
		CANDIDATES.removeIf(modCandidate -> modCandidate.modInfo.distribution != getCurrentDistribution());
	}

	private static Distribution getCurrentDistribution() {
		// TODO:
		return Distribution.CLIENT;
	}

	/**
	 * Finds all mods from
	 * - the classpath
	 * - the mods folder
	 * that have a mod.toml file and a single class annotated with @Mod
	 */
	private static void findAllModCandidates() {
		findModJarsOnClasspath();
	}

	private static void findModJarsOnClasspath() {
		final List<ModInfo> modInfos = new ArrayList<>();
		final List<ModClass> modClasses = new ArrayList<>();
		try {
			Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("");
			forEachEnumeration(resources, url -> {
				forEachFile(new File(url.getPath()), file -> {
					final String name = file.getName();
					if (name.equals("mod.toml")) {
						try (InputStream is = Files.newInputStream(file.toPath())) {
							modInfos.add(parseModToml(is));
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (name.endsWith(".class")) {
						try (InputStream is = Files.newInputStream(file.toPath())) {
							ModLoader.readModClass(is, modClasses);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		INFOS:
		for (final Iterator<ModInfo> mis = modInfos.iterator(); mis.hasNext(); ) {
			ModInfo modInfo = mis.next();
			for (final Iterator<ModClass> mcs = modClasses.iterator(); mcs.hasNext(); ) {
				ModClass modClass = mcs.next();
				if (modInfo.modId.equals(modClass.modId)) {
					mis.remove();
					mcs.remove();
					CANDIDATES.add(new ModCandidate(modInfo, modClass));
					continue INFOS;
				}
			}
		}
		for (final ModInfo modInfo : modInfos)
			System.err.println("ModInfo \"" + modInfo + "\" did not have an associated @Mod class. This is an error!");
		for (final ModClass modClass : modClasses)
			System.err.println("ModClass \"" + modClass + "\" did not have an associated mod.toml file. This is an error!");
	}

	// TODO:
	private static ModInfo parseModToml(final InputStream is) {
		final ModInfo modInfo = new ModInfo();
		final String[] toml = Utils.splitNewline(Utils.convertStreamToString(is));
		for (final String s : toml) {
			if (s.startsWith("modId")) {
				String temp = s.substring(s.indexOf('=') + 1);
				temp = temp.substring(1, temp.lastIndexOf('"'));
				modInfo.modId = temp;

				modInfo.version = new Version("0.1.0");
				modInfo.gameVersionRange = new VersionRange(TestGame.VERSION.toString());
				modInfo.dependencies = new Dependency[0];

				break;
			}
		}
		return modInfo;
	}

	private static <T> void forEachEnumeration(final Enumeration<T> enumeration, final Consumer<T> consumer) {
		while (enumeration.hasMoreElements()) {
			consumer.accept(enumeration.nextElement());
		}
	}

	// TODO: clean up
	private static void validateCompatibility() {
		List<WrongGameVersionException> wrongGameVersionExceptions = new ArrayList<>();
		List<MissingDependencyException> missingDependencyExceptions = new ArrayList<>();
		List<WrongDependencyVersionException> wrongDependencyVersionExceptions = new ArrayList<>();
		final Version gameVersion = TestGame.VERSION;
		for (final ModCandidate candidate : CANDIDATES) {
			if (!candidate.modInfo.gameVersionRange.matches(gameVersion)) {
				final WrongGameVersionException e = new WrongGameVersionException(candidate.modInfo, gameVersion);
				wrongGameVersionExceptions.add(e);
				continue;
			}
			for (final Dependency dependency : candidate.modInfo.dependencies) {
				if (!dependency.required)
					continue;
				boolean dependencyFound = false;
				boolean dependencyVersionCompatible = false;
				for (final ModCandidate modCandidate : CANDIDATES)
					if (dependency.modId.equals(modCandidate.modInfo.modId)) {
						dependencyFound = true;
						dependencyVersionCompatible = dependency.versionRange.matches(modCandidate.modInfo.version);
						break;
					}

				if (!dependencyFound) {
					final MissingDependencyException e = new MissingDependencyException(candidate.modInfo, dependency);
					missingDependencyExceptions.add(e);
				} else if (!dependencyVersionCompatible) {
					final WrongDependencyVersionException e = new WrongDependencyVersionException(candidate.modInfo, dependency);
					wrongDependencyVersionExceptions.add(e);
				}
			}
		}
		if (!wrongGameVersionExceptions.isEmpty() || !missingDependencyExceptions.isEmpty() || !wrongDependencyVersionExceptions.isEmpty()) {
			for (final WrongGameVersionException e : wrongGameVersionExceptions)
				System.out.println(e.toString());
			for (final MissingDependencyException e : missingDependencyExceptions)
				System.out.println(e.toString());
			for (final WrongDependencyVersionException e : wrongDependencyVersionExceptions)
				System.out.println(e.toString());
			throw new RuntimeException();
		}
	}

	private static void forEachFile(final File root, Consumer<File> consumer) {
		for (File file : root.listFiles()) {
			if (file.isDirectory()) {
				forEachFile(file, consumer);
				continue;
			}
			consumer.accept(file);
		}
	}

	private static void readModClass(final InputStream is, final List<ModClass> modClasses) {
		final ClassNode classNode = new ClassNode();
		try {
			new ClassReader(is).accept(classNode, 0);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		if (classNode.visibleAnnotations == null || classNode.visibleAnnotations.isEmpty())
			return;

		for (AnnotationNode annotationNode : classNode.visibleAnnotations) {
			if (!annotationNode.desc.equals(MOD_ANNOTATION_DESC))
				continue;
			parseModClass(classNode, annotationNode, modClasses);
			break;
		}
	}

	private static void parseModClass(final ClassNode classNode, final AnnotationNode annotationNode, final List<ModClass> modClasses) {
		String modId = null;
//		String[] loadBefore = null;
//		String[] loadAfter = null;
		final List<Object> values = annotationNode.values;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i).equals("value"))
				modId = (String) values.get(i + 1);
//			if (values.get(i).equals("loadBefore"))
//				loadBefore = (String[]) ((ArrayList) values.get(i + 1)).toArray(new String[0]);
//			if (values.get(i).equals("loadAfter"))
//				loadAfter = (String[]) ((ArrayList) values.get(i + 1)).toArray(new String[0]);
		}

//		if (loadBefore == null)
//			loadBefore = new String[0];
//		if (loadAfter == null)
//			loadAfter = new String[0];

		if (modId == null) {
			System.err.println("Error: mod candidate \"" + classNode.name + "\" has no modId!");
			return;
		}
		if (modId.length() < 8 || modId.length() > 64) {
			System.err.println("Error: mod candidate \"" + classNode.name + "\" has an invalid modId!");
			return;
		}

//		if (loadBefore.length > 0 || loadAfter.length > 0) {
//			System.out.println("Mod candidate \"" + modId + "\" wants to load");
//			for (final String s : loadBefore)
//				System.out.println("  Before: " + s);
//			for (final String s : loadAfter)
//				System.out.println("  After: " + s);
//		}

		final String modClassName = classNode.name.replace('/', '.');
		modClasses.add(new ModClass(modId, modClassName));
	}

	//TODO: clean up
	public static void sortMods(final Collection<ModCandidate> modCandidates) {

		final Map<String, Node> map = new HashMap<>();

		for (final ModCandidate modCandidate : modCandidates)
			if (map.put(modCandidate.modInfo.modId, new TypedNode<>(modCandidate)) != null)
				throw new RuntimeException("Duplicate ModCandidates: " + modCandidate.modInfo.modId);

		for (final ModCandidate modCandidate : modCandidates) {
			final Node node = map.get(modCandidate.modInfo.modId);
			for (final String runBefore : modCandidate.modInfo.loadBeforeModIds) {
				final Node runBeforeNode = map.get(runBefore);
				if (runBeforeNode == null) {
					System.out.println("ModCandidate " + modCandidate.modInfo.modId + " wants to run before non-existent modCandidate " + runBefore);
					continue;
				}
				// first.runsBefore(second) -> first.addEdge(second)
				node.addEdge(runBeforeNode);
			}
			for (final String runAfter : modCandidate.modInfo.loadAfterModIds) {
				final Node runAfterNode = map.get(runAfter);
				if (runAfterNode == null) {
					System.out.println("ModCandidate " + modCandidate.modInfo.modId + " wants to run after non-existent modCandidate " + runAfter);
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

}
