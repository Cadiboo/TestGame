package io.github.cadiboo.testgame.client.idk;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * @author Sri Harsha Chilakapati
 */
public class Tutorial1 extends Game {

	private ShaderProgram shaderProgram;

	private int vaoID;
	private int vboID;

	public static void main(String[] args) {
		new Tutorial1().start();
	}

	public void init() {
		glfwSetWindowTitle(getWindowID(), "The First Triangle");

		shaderProgram = new ShaderProgram();
		shaderProgram.attachVertexShader("shaders/shader.vs");
		shaderProgram.attachFragmentShader("shaders/shader.fs");
		shaderProgram.link();

		// Generate and bind a Vertex Array
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		// The vertices of our Triangle
		float[] vertices = new float[]{
				+0.0f, +0.8f,    // Top coordinate
				-0.8f, -0.8f,    // Bottom-left coordinate
				+0.8f, -0.8f,     // Bottom-right coordinate

				+0f, +1f,    // Top coordinate
				-1f, -1f,    // Bottom-left coordinate
				+1f, -1f,     // Bottom-right coordinate
		};

		// Create a FloatBuffer of vertices
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
		verticesBuffer.put(vertices).flip();

		// Create a Buffer Object and upload the vertices buffer
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

		// Point the buffer at location 0, the location we set
		// inside the vertex shader. You can use any location
		// but the locations should match
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		glBindVertexArray(0);
	}

	public void render(float delta) {
		// Clear the screen
		glClear(GL_COLOR_BUFFER_BIT);

		// Use our program
		shaderProgram.bind();

		// Bind the vertex array and enable our location
		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);

		// Draw a triangle of 3 vertices
		glDrawArrays(GL_TRIANGLES, 0, 3);

		// Disable our location
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);

		// Un-bind our program
		ShaderProgram.unbind();
	}

	public void dispose() {
		// Dispose the program
		shaderProgram.dispose();

		// Dispose the vertex array
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoID);

		// Dispose the buffer object
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboID);
	}

}
