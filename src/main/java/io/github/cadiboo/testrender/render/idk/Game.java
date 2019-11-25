package io.github.cadiboo.testrender.render.idk;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author Sri Harsha Chilakapati
 */
public class Game {

	private long windowID;

	public Game() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) {
			System.err.println("Error initializing GLFW");
			System.exit(1);
		}

		// Window Hints for OpenGL context
		glfwWindowHint(GLFW_SAMPLES, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

		windowID = glfwCreateWindow(640, 480, "My GLFW Window", NULL, NULL);

		if (windowID == NULL) {
			System.err.println("Error creating a window");
			System.exit(1);
		}

		glfwShowWindow(windowID);

		glfwMakeContextCurrent(windowID);
		GL.createCapabilities();

		glfwSwapInterval(1);
	}

	public static void main(String[] args) {
		new Game().start();
	}

	public long getWindowID() {
		return windowID;
	}

	public void close() {
		glfwSetWindowShouldClose(windowID, true);
	}

	public void onWindowMoved(int x, int y) {
		System.out.println(String.format("Window moved: [%d, %d]", x, y));
	}

	public void onWindowResized(int width, int height) {
		System.out.println(String.format("Window resized: [%d, %d]", width, height));
	}

	public void onWindowClosing() {
		System.out.println("Window closing");
	}

	public void onWindowFocusChanged(boolean focused) {
		System.out.println(String.format("Window focus changed: FOCUS = %B", focused));
	}

	public void onWindowIconfyChanged(boolean iconified) {
		System.out.println(String.format("Window iconified/restored: ICONIFIED = %B", iconified));
	}

	public void onFramebufferResized(int width, int height) {
		System.out.println(String.format("Framebuffer resized: [%d, %d]", width, height));
	}

	public void init() {
	}

	public void update(float delta) {
	}

	public void render(float delta) {
	}

	public void dispose() {
	}

	public void start() {
		float now, delta, last = 0;

		// Set the callbacks
		glfwSetWindowPosCallback(windowID, (window, x, y) -> onWindowMoved(x, y));
		glfwSetWindowSizeCallback(windowID, (window, width, height) -> onWindowResized(width, height));
		glfwSetWindowCloseCallback(windowID, window -> onWindowClosing());
		glfwSetWindowFocusCallback(windowID, (window, focused) -> onWindowFocusChanged(focused));
		glfwSetWindowIconifyCallback(windowID, (window, iconified) -> onWindowIconfyChanged(iconified));
		glfwSetFramebufferSizeCallback(windowID, (window, width, height) -> onFramebufferResized(width, height));

		// Initialize the game
		init();

		// Loop continuously and render and update
		while (!glfwWindowShouldClose(windowID)) {
			now = (float) glfwGetTime();
			delta = now - last;
			last = now;

			// Update and Render
			update(delta);
			render(delta);

			// Poll the events and swap the buffers
			glfwPollEvents();
			glfwSwapBuffers(windowID);
		}

		// Dispose the game
		dispose();

		// Free the callbacks
		glfwSetWindowPosCallback(windowID, null).free();
		glfwSetWindowSizeCallback(windowID, null).free();
		glfwSetWindowCloseCallback(windowID, null).free();
		glfwSetWindowFocusCallback(windowID, null).free();
		glfwSetWindowIconifyCallback(windowID, null).free();
		glfwSetFramebufferSizeCallback(windowID, null).free();

		// Destroy the window
		glfwDestroyWindow(windowID);
		glfwTerminate();

		System.exit(0);
	}

}
