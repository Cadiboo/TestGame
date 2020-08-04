package io.github.cadiboo.testrender.render.shader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

/**
 * ShaderProgram Class. Used to load and use Vertex and Fragment shaders easily.
 *
 * @author Sri Harsha Chilakapati
 */
public class ShaderProgram {

	// ProgramID
	private final int programID;

	private int vertexShaderID = -1;
	private int geometryShaderID = -1;
	private int fragmentShaderID = -1;

	/**
	 * Create a new ShaderProgram.
	 */
	public ShaderProgram() {
		programID = glCreateProgram();
	}

	/**
	 * Unbind the shader program.
	 */
	public static void unbind() {
		glUseProgram(0);
	}

	public static String readFromFile(String name) {
		StringBuilder source = new StringBuilder();
		try {
			final InputStream resourceAsStream = ShaderProgram.class.getClassLoader().getResourceAsStream(name);
			if (resourceAsStream == null)
				throw new RuntimeException("Error loading source code: " + name + ". File does not exist!");
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream))) {
				String line;
				while ((line = reader.readLine()) != null) {
					source.append(line).append("\n");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error loading source code: " + name, e);
		}
		return source.toString();
	}

	/**
	 * Attach a Vertex Shader to this program.
	 *
	 * @param name The file name of the vertex shader.
	 */
	public void attachVertexShader(final String name) {
		if (vertexShaderID != -1)
			throw new IllegalStateException("Already have a fragment shader");
		vertexShaderID = attachShader(name + ".vsh", GL_VERTEX_SHADER, "Error creating vertex shader\n");
	}

	/**
	 * Attach a Geometry Shader to this program.
	 *
	 * @param name The file name of the vertex shader.
	 */
	public void attachGeometryShader(final String name) {
		if (geometryShaderID != -1)
			throw new IllegalStateException("Already have a geometry shader");
		geometryShaderID = attachShader(name + ".gsh", GL_GEOMETRY_SHADER, "Error creating geometry shader\n");
	}

	private int attachShader(final String name, final int type, final String errorMessage) {
		// Load the source
		String vertexShaderSource = readFromFile(name);

		// Create the shader and set the source
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, vertexShaderSource);

		// Compile the shader
		glCompileShader(shaderID);

		// Check for errors
		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException(errorMessage + glGetShaderInfoLog(shaderID, glGetShaderi(shaderID, GL_INFO_LOG_LENGTH)));

		// Attach the shader
		glAttachShader(programID, shaderID);
		return shaderID;
	}

	/**
	 * Attach a Fragment Shader to this program.
	 *
	 * @param name The file name of the Fragment Shader.
	 */
	public void attachFragmentShader(final String name) {
		if (fragmentShaderID != -1)
			throw new IllegalStateException("Already have a fragment shader");
		fragmentShaderID = attachShader(name + ".fsh", GL_FRAGMENT_SHADER, "Error creating fragment shader\n");
	}

	/**
	 * Links this program in order to use.
	 */
	public void link() {
		// Link this program
		glLinkProgram(programID);

		// Check for linking errors
		if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE)
			throw new RuntimeException("Unable to link shader program:" + glGetProgramInfoLog(programID));
	}

	/**
	 * Bind this program to use.
	 */
	public void bind() {
		glUseProgram(programID);
	}

	/**
	 * Dispose the program and shaders.
	 */
	public void dispose() {
		// Unbind the program
		unbind();

		// Detach the shaders
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);

		// Delete the shaders
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);

		// Delete the program
		glDeleteProgram(programID);
	}

	/**
	 * @return The ID of this program.
	 */
	public int getID() {
		return programID;
	}

}
