package io.github.cadiboo.testrender.main;

import io.github.cadiboo.testrender.render.GameRenderer;

/**
 * @author Cadiboo
 */
public final class Main {

	private static byte[] memoryBuffer = new byte[0x100000];

	public static void main(String... args) {
		try {
			launchGameThread();
			GameRenderer.doRendering();
		} catch (Throwable t) {
			handleException(t);
		}
	}

	private static void launchGameThread() {
		new Thread(Main::runGame, "Game Thread").start();
	}

	private static void runGame() {

	}

	public static void handleException(final String errorMessage) {
		final Exception error = new Exception("" + errorMessage);

		// Remove the call to this method from the stacktrace
		final StackTraceElement[] stackTrace = error.getStackTrace();
		final int newLength = stackTrace.length - 1;
		final StackTraceElement[] newStackTrace = new StackTraceElement[newLength];
		System.arraycopy(stackTrace, 1, newStackTrace, 0, newLength);
		error.setStackTrace(newStackTrace);

		handleException(error);
	}

	public static void handleException(Throwable error) {
		if (error == null)
			error = new NullPointerException("Trying to handle null error!");
		else if (error instanceof OutOfMemoryError) {
			memoryBuffer = null;
			System.gc();
		}
		boolean windowOpen = false;
		try {
			windowOpen = GameRenderer.isWindowOpen();
		} finally {
			if (!windowOpen) {
				error.printStackTrace(System.err);
				error.printStackTrace(System.out);
				return;
			} else {
				return;
			}
		}
	}

}
