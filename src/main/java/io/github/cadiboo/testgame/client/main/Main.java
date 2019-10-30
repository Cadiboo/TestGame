package io.github.cadiboo.testgame.client.main;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.chunk.Chunk;
import io.github.cadiboo.testgame.client.color.BlockColorHandler;
import io.github.cadiboo.testgame.client.color.FluidColorHandler;
import io.github.cadiboo.testgame.loader.Loader;
import io.github.cadiboo.testgame.world.World;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Math.abs;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public final class Main {

	private static boolean hasCrashed;

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		createWindow();
		logTime("createProgressViewer", startTime);
		startTime = System.nanoTime();
		setup();
		logTime("setup", startTime);
		startTime = System.nanoTime();
		while (isRunning()) {
			gameLoop();
		}
		logTime("run", startTime);
		startTime = System.nanoTime();
		shutDown();
		logTime("shutdown", startTime);
		System.exit(0);
	}

	private static void createWindow() {
		new Thread(() -> {
			// Create game window...
			JFrame app = new JFrame();
			app.setIgnoreRepaint(true);
			app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// Create canvas for painting...
			Canvas canvas = new Canvas();
			canvas.setIgnoreRepaint(true);
			canvas.setBounds(0, 0, 1024, 1024);
			canvas.setSize(1024, 1024);
			canvas.setPreferredSize(new Dimension(1024, 1024));

			// Add canvas to game window...
			app.add(canvas);
			app.pack();
			// Centre Window
			{
				Rectangle rect = app.getBounds();
				Rectangle parRect;
				Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
				parRect = new Rectangle(0, 0, scrDim.width, scrDim.height);
				int newX = parRect.x + (parRect.width - rect.width) / 2;
				int newY = parRect.y + (parRect.height - rect.height) / 2;
				if (newX < 0) {
					newX = 0;
				}
				if (newY < 0) {
					newY = 0;
				}
				app.setBounds(newX, newY, rect.width, rect.height);
			}
			app.setVisible(true);

			// Create BackBuffer...
			canvas.createBufferStrategy(2);
			BufferStrategy buffer = canvas.getBufferStrategy();

			// Get graphics configuration...
			GraphicsEnvironment ge =
					GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gd.getDefaultConfiguration();

			// Create off-screen drawing surface
			BufferedImage bi = gc.createCompatibleImage(4096, 4096);

			// Objects needed for rendering...
			Graphics graphics = null;
			Graphics2D g2d = null;
			Color background = Color.BLACK;
			Random rand = new Random();

			// Variables for counting frames per seconds
			int fps = 0;
			int frames = 0;
			long totalTime = 0;
			long curTime = System.currentTimeMillis();
			long lastTime = curTime;

			final World world = new World();
			final ConcurrentHashMap<Chunk, Integer> chunks = new ConcurrentHashMap<>();

			final int r = Math.max(1, 0x100 / Chunk.AXIS_SIZE);
			final ArrayList<ChunkMaker> runnables = new ArrayList<>();
			for (int z = -r; z <= r; ++z) {
				for (int y = -r; y <= r; ++y) {
					for (int x = -r; x <= r; ++x) {
						runnables.add(new ChunkMaker(x, y, z, chunks, world));
					}
				}
			}
			runnables.sort(Comparator.comparingInt(o -> abs(o.x) + abs(o.y) + abs(o.z)));
			final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(runnables.size());
			queue.addAll(runnables);
			runnables.clear();
			final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0, MILLISECONDS, queue);
			threadPoolExecutor.execute(() -> {
			});

			int z = 0;
			int oldZ = 0;

			while (true) {
				try {
					// count Frames per second...
					lastTime = curTime;
					curTime = System.currentTimeMillis();
					totalTime += curTime - lastTime;
					if (totalTime > 1000) {
						totalTime -= 1000;
						fps = frames;
						frames = 0;
					}
					++frames;

					// clear back buffer...
					g2d = bi.createGraphics();
					g2d.setColor(background);
					g2d.fillRect(0, 0, 200, 100);

//					z = (int) ((System.currentTimeMillis() / 10000) % Chunk.AXIS_SIZE);
					renderChunks(g2d, canvas.getWidth() / 2, canvas.getHeight() / 2, chunks, z, z != oldZ);
					oldZ = z;

					// display frames per second...
					g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
					g2d.setColor(Color.GREEN);
					g2d.drawString(String.format("FPS: %s", fps), 20, 35);

					// display RAM
					final Runtime rt = Runtime.getRuntime();
					g2d.drawString(String.format("RAM: %s/%s", rt.totalMemory() - rt.freeMemory(), rt.totalMemory()), 20, 20);

					g2d.drawString(String.format("Z: %s", z), 20, 50);

					g2d.drawString(String.format("C: %s", chunks.size()), 20, 65);

					g2d.drawString(String.format("R: %s", chunks.values().stream().filter(i -> i != -1).count()), 20, 80);

					// Blit image and flip...
					graphics = buffer.getDrawGraphics();
					graphics.drawImage(bi, 0, 0, null);
					if (!buffer.contentsLost())
						buffer.show();

					// Let the OS have a little time...
					Thread.yield();
				} finally {
					// release resources
					if (graphics != null)
						graphics.dispose();
					if (g2d != null)
						g2d.dispose();
				}
			}

		}, "Game Window").start();
	}

	private static void renderChunks(final Graphics2D g2d, final int cx, final int cy, final Map<Chunk, Integer> chunks, final int z, final boolean force) {
		long startTime = System.currentTimeMillis();
		for (final Chunk chunk : chunks.keySet()) {
			if (System.currentTimeMillis() - startTime > 100)
				break;
			if (force || chunks.get(chunk) != chunk.getModCount()) {
				chunks.put(chunk, chunk.getModCount());
				int tx = cx + (int) World.toBlockPos(chunk.chunkX);
				int ty = cy + (int) -World.toBlockPos(chunk.chunkY);
//				int ty = cy + (int) World.toBlockPos(chunk.chunkZ);
				renderChunk(g2d, z, chunk, tx, ty);
			}
		}
	}

	private static void renderChunk(final Graphics2D g2d, final int z, final Chunk chunk, final int tx, final int ty) {
//		if (chunk.chunkZ != 0)
//			return;
		for (int y = 0; y < Chunk.AXIS_SIZE; ++y) {
			for (int x = 0; x < Chunk.AXIS_SIZE; ++x) {
				g2d.setColor(BlockColorHandler.getColor(chunk.getBlock(x, y, z)));
				g2d.fillRect(tx + x, ty + Chunk.AXIS_SIZE - y, 1, 1);

				g2d.setColor(FluidColorHandler.getColor(chunk.getFluid(x, y, z)));
				g2d.fillRect(tx + x, ty + Chunk.AXIS_SIZE - y, 1, 1);
			}
		}
//		final Color color = BlockColorHandler.getColor(Blocks.STONE.get());
//		for (int y = 0; y < Chunk.AXIS_SIZE; ++y) {
//			for (int x = 0; x < Chunk.AXIS_SIZE; ++x) {
//				g2d.setColor(color);
//				g2d.fillRect(tx + x, ty + Chunk.AXIS_SIZE - y, 1, 1);
//			}
//		}
//		for (int z = 0; z < Chunk.AXIS_SIZE; ++z) {
//			for (int x = 0; x < Chunk.AXIS_SIZE; ++x) {
//				g2d.setColor(BlockColorHandler.getColor(chunk.getBlock(x, 0, z)));
//				g2d.fillRect(tx + x, ty + z, 1, 1);
//
//				g2d.setColor(FluidColorHandler.getColor(chunk.getFluid(x, 0, z)));
//				g2d.fillRect(tx + x, ty + z, 1, 1);
//			}
//		}
	}

	private static void setup() {
		Loader.add("BlockColorHandler::init", BlockColorHandler::init);
		Loader.add("FluidColorHandler::init", FluidColorHandler::init);
		try {
			Class.forName(TestGame.class.getName(), true, Thread.currentThread().getContextClassLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isRunning() {
		return !hasCrashed;
	}

	private static void gameLoop() {

	}

	private static void shutDown() {
	}

	private static void logTime(final String task, final long startTime) {
		final long time = System.nanoTime() - startTime;
		System.out.println("Task \"" + task + "\" took " + time / 1_000_000 + " millis");
	}

	private static class ChunkMaker implements Runnable {

		private final int x;
		private final int y;
		private final int z;
		private final ConcurrentHashMap<Chunk, Integer> chunks;
		private final World world;

		public ChunkMaker(final int x, final int y, final int z, final ConcurrentHashMap<Chunk, Integer> chunks, final World world) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.chunks = chunks;
			this.world = world;
		}

		@Override
		public void run() {
			chunks.put(world.getOrCreateChunk(x, y, z), -1);
		}

	}

}
