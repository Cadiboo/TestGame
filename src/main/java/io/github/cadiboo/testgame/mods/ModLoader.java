package io.github.cadiboo.testgame.mods;

import io.github.cadiboo.testgame.mods.api.Mod;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

/**
 * @author Cadiboo
 */
public final class ModLoader {

	private static final String MOD_ANNOTATION_DESC = Type.getDescriptor(Mod.class);
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
	}

	private static void recursivelyFindMods(final File root) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
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
					final List<Object> values = annotationNode.values;
					for (int i = 0; i < values.size(); i++) {
						if (values.get(i).equals("value")) {
							modId = (String) ((List) values.get(i + 1)).get(0);
						}
					}

					if (modId == null) {
						System.err.println("Error: mod \"" + classNode.name + "\" has no modId!");
						break;
					}
					if (modId.length() < 8 || modId.length() > 64) {
						System.err.println("Error: mod \"" + classNode.name + "\" has an invalid modId!");
						break;
					}

					System.out.println("Loading mod \"" + modId + "\"");
					final String modClassName = classNode.name.replace('/', '.');

					System.out.println("Loading mod class " + modClassName);
					final Class<?> modClass = Thread.currentThread().getContextClassLoader().loadClass(modClassName);

					System.out.println("Initialising mod class " + modClassName);
					MODS.put(modId, new ModObject(modClass.newInstance(), modId));

					System.out.println("Loaded mod \"" + modId + "\"");
					break;
				}
			}
		}
	}

}
