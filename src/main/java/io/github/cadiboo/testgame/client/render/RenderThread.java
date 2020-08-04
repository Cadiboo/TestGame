package io.github.cadiboo.testgame.client.render;

/**
 * @author Cadiboo
 */
public class RenderThread extends Thread {

	public RenderThread() {
		if (!"main".equals(Thread.currentThread().getName())) {
			new IllegalStateException("Must be on main thread. Launch with -XstartOnFirstThread.").printStackTrace(System.err);
			Runtime.getRuntime().exit(1);
		}
	}

	@Override
	public void run() {
		super.run();
	}

}
