package io.github.cadiboo.testrender.render;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static io.github.cadiboo.testrender.main.Main.handleException;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author Cadiboo
 */
public class Window {

	private final int width;
	private final int height;
	private final String title;
	private long windowHandle = NULL;

	public Window(final int width, final int height, final String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	public void init() {
		// Setup an error callback.
		GLFWErrorCallback.create((error, description) ->
				handleException("GLFW Error! Code: " + error + ", Description: " + description))
				.set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!GLFW.glfwInit()) {
			handleException("GLFW wasn't initialised");
			return;
		}

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_SAMPLES, 4); // Multisampling
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

		// Create the window
		windowHandle = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
		if (windowHandle == NULL) {
			handleException("Window couldn't be isWindowOpen");
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
	}

	public void preRender() {
		// Clear the framebuffer (from last draw)
		GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void postRender() {
		// Swap the color buffers
		glfwSwapBuffers(windowHandle);

		// Poll for window events.
		// The key callback will only be invoked during this call.
		glfwPollEvents();
	}

	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(windowHandle);
	}

	public void dispose() {
		glfwDestroyWindow(windowHandle);
	}

}
