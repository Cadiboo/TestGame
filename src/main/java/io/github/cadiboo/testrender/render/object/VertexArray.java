package io.github.cadiboo.testrender.render.object;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * @author Cadiboo
 */
public class VertexArray {

	private final int arrayId;
	private final ArrayList<VertexBuffer> vertexBuffers = new ArrayList<>();

	public VertexArray() {
		// Generate a Vertex Array
		arrayId = glGenVertexArrays();
	}

	public int getId() {
		return arrayId;
	}

	public ArrayList<VertexBuffer> getVertexBuffers() {
		return vertexBuffers;
	}

	public void addVertexBuffer(final VertexBuffer vertexBuffer) {
		// Bind our Vertex Array
		glBindVertexArray(arrayId);

		vertexBuffers.add(vertexBuffer);

		// Point the buffer at location 0, the location we set
		// inside the vertex shader. You can use any location
		// but the locations should match
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		glBindVertexArray(0);
	}

}
