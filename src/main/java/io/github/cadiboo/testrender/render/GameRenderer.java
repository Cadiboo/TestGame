package io.github.cadiboo.testrender.render;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testrender.render.object.VertexArray;
import io.github.cadiboo.testrender.render.object.VertexBuffer;
import io.github.cadiboo.testrender.render.shader.ShaderProgram;

import java.util.ArrayList;

import static io.github.cadiboo.testrender.main.Main.handleException;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_Q;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * @author Cadiboo
 */
public class GameRenderer {

	private final Window window;
	private final VertexArray vertexArray;
	private boolean isWindowOpen;
	private ShaderProgram shader;

	private GameRenderer() {
		window = new Window(800, 400, TestGame.TITLE);
		window.init();
		isWindowOpen = true;
		{
			shader = new ShaderProgram();
			shader.attachVertexShader("shader/base");
			shader.attachGeometryShader("shader/base");
			shader.attachFragmentShader("shader/base");
			shader.link();
		}
		{
			vertexArray = new VertexArray();
//			final float[] bigTri = {
//					+0.0F, +1.0F,    // Top coordinate
//					-1.0F, -1.0F,    // Bottom-left coordinate
//					+1.0F, -1.0F,    // Bottom-right coordinate
//			};
//			final float[] smallTri = {
//					+0.0F, +0.8F,    // Top coordinate
//					-0.8F, -0.8F,    // Bottom-left coordinate
//					+0.8F, -0.8F,    // Bottom-right coordinate
//			};
			final float[] smallQuad = {
					-0.5F, -0.5F,    // Bottom-left coordinate
					-0.5F, +0.5F,    // Top-left coordinate
					+0.5F, +0.5F,    // Top-right coordinate
					+0.5F, -0.5F,    // Bottom-right coordinate
			};
//			vertexArray.addVertexBuffer(new VertexBuffer(bigTri, GL_TRIANGLES, 3));
//			vertexArray.addVertexBuffer(new VertexBuffer(smallTri, GL_TRIANGLES, 3));
			vertexArray.addVertexBuffer(new VertexBuffer(smallQuad, GL_TRIANGLES, 4));
		}
	}

	/**
	 * Called from Main.main(String... args)
	 */
	public static void doRendering() {
		getInstance().renderGame();
	}

	public static GameRenderer getInstance() {
		return InstanceHolder.instance;
	}

	public static boolean isWindowOpen() {
		return getInstance() != null && getInstance().isWindowOpen;
	}

	private void render() {
		// Clear the screen
		glClear(GL_COLOR_BUFFER_BIT);

		// Use our program
		shader.bind();

		// Bind the vertex array and enable our location
		glBindVertexArray(vertexArray.getId());
		glEnableVertexAttribArray(0);

		for (final VertexBuffer vertexBuffer : vertexArray.getVertexBuffers()) {
			// Draw the data
			glDrawArrays(vertexBuffer.getMode(), 0, vertexBuffer.getCount());
		}

		// Disable our location
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);

		// Un-bind our program
		ShaderProgram.unbind();
	}

	public void renderGame() {
		try {
			while (!window.shouldClose()) {
				window.preRender();
				render();
				window.postRender();
			}
		} catch (Throwable t) {
			handleException(t);
		}
		try {
			dispose();
		} catch (Throwable t) {
			handleException(t);
		}
	}

	private void dispose() {
		isWindowOpen = false;
		// Dispose the program
		shader.dispose();
		// Destroy the window
		window.dispose();
		glfwTerminate();
	}

	private static class InstanceHolder {

		private static final GameRenderer instance = new GameRenderer();

	}

}
