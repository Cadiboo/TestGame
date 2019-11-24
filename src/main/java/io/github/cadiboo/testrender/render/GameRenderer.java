package io.github.cadiboo.testrender.render;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static io.github.cadiboo.testrender.main.Main.handleException;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author Cadiboo
 */
public class GameRenderer {

	private static long windowHandle;
	private static boolean created;

	/**
	 * Called from Main.main(String... args)
	 */
	public static void doRendering() {
		try {
			init(800, 400, "Title Here");
			loop();
		} catch (Throwable t) {
			handleException(t);
		}
	}

	private static void init(final int width, final int height, final String title) {

		// Setup an error callback.
		GLFWErrorCallback.create((error, description) -> handleException("GLFW Error! Code: " + error + ", Description: " + description))
				.set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!GLFW.glfwInit()) {
			handleException("GLFW wasn't initialised");
			return;
		}

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		windowHandle = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
		if (windowHandle == NULL) {
			handleException("Window couldn't be created");
			return;
		}

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(windowHandle, (window, key, scanCode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Centre the window
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		if (videoMode == null) {
			handleException("Video Mode is null");
			return;
		}
		GLFW.glfwSetWindowPos(windowHandle, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

		// Make the window visible
		GLFW.glfwShowWindow(windowHandle);

		// Make the OpenGL context current
		glfwMakeContextCurrent(windowHandle);
		// Enable v-sync
		GLFW.glfwSwapInterval(1);

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		//Makes 3D drawing work when something is in front of something else
		glEnable(GL_DEPTH_TEST);

		// Set the clear color
		glClearColor(1.0F, 0.0F, 0.0F, 0.0F);

		created = true;
	}

	private static void loop() {
		while (!GLFW.glfwWindowShouldClose(windowHandle)) {
			update();
		}
	}

	private static void update() {
		// Clear the framebuffer (from last draw)
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		render();

		// Swap the color buffers
		glfwSwapBuffers(windowHandle);

		// Poll for window events.
		// The key callback will only be invoked during this call.
		glfwPollEvents();
	}

	private static void render() {
		glMatrixMode(GL_MODELVIEW); //Switch to the drawing perspective
		glLoadIdentity(); //Reset the drawing perspective

		glBegin(GL_QUADS); //Begin quadrilateral coordinates

		//Trapezoid
		glVertex3f(-0.7f, -1.5f, -5.0f);
		glVertex3f(0.7f, -1.5f, -5.0f);
		glVertex3f(0.4f, -0.5f, -5.0f);
		glVertex3f(-0.4f, -0.5f, -5.0f);

		glEnd(); //End quadrilateral coordinates

		glBegin(GL_TRIANGLES); //Begin triangle coordinates

//		//Pentagon
//		glVertex3f(0.5f, 0.5f, -5.0f);
//		glVertex3f(1.5f, 0.5f, -5.0f);
//		glVertex3f(0.5f, 1.0f, -5.0f);
//
//		glVertex3f(0.5f, 1.0f, -5.0f);
//		glVertex3f(1.5f, 0.5f, -5.0f);
//		glVertex3f(1.5f, 1.0f, -5.0f);
//
//		glVertex3f(0.5f, 1.0f, -5.0f);
//		glVertex3f(1.5f, 1.0f, -5.0f);
//		glVertex3f(1.0f, 1.5f, -5.0f);

		//Triangle
		glVertex3f(-0.5f, 0.5f, -5.0f);
		glVertex3f(-1.0f, 1.5f, -5.0f);
		glVertex3f(-1.5f, 0.5f, -5.0f);

		glEnd(); //End triangle coordinates
	}

	public static boolean isCreated() {
		return created;
	}

}
