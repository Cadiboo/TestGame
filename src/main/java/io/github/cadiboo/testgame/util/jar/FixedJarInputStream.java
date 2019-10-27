package io.github.cadiboo.testgame.util.jar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

/**
 * Cadiboo: Stolen from BON2
 * <p>
 * Fixes bug with JRE that expects the Manifest to always be the first entry in META-INF, when in some cases, it isn't.<br/>
 * Credit to clienthax for finding this: <a href=http://bugs.java.com/view_bug.do?bug_id=4338238>http://bugs.java.com/view_bug.do?bug_id=4338238</a>
 */
public class FixedJarInputStream extends JarInputStream {

	private Manifest manifest;

	/**
	 * Creates a new <code>JarInputStream</code> and reads the optional
	 * manifest. If a manifest is present and verify is true, also attempts
	 * to verify the signatures if the JarInputStream is signed.
	 *
	 * @param file   the actual jar file
	 * @param verify whether or not to verify the JarInputStream if it is signed.
	 * @throws IOException if an I/O error has occurred
	 */
	public FixedJarInputStream(File file, boolean verify) throws IOException {
		super(new FileInputStream(file), verify);
		try (JarFile jar = new JarFile(file)) {
			JarEntry manifestEntry = jar.getJarEntry(JarFile.MANIFEST_NAME);
			if (manifestEntry != null) {
				this.manifest = new Manifest(jar.getInputStream(manifestEntry));
			}
		}
	}

	@Override
	public Manifest getManifest() {
		return manifest;
	}

}
