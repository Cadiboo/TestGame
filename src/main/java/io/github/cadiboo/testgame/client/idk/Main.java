package io.github.cadiboo.testgame.client.idk;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.chunk.Chunk;
import io.github.cadiboo.testgame.client.color.BlockColorHandler;
import io.github.cadiboo.testgame.client.color.FluidColorHandler;
import io.github.cadiboo.testgame.loader.LoadPhase;
import io.github.cadiboo.testgame.loader.Loader;
import io.github.cadiboo.testgame.world.World;

import java.util.concurrent.ConcurrentHashMap;

public final class Main {

	public static volatile boolean isClosed;
	private static boolean hasCrashed;

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		createWindow();
		logTime("createWindow", startTime);
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
		// Create the Window
//		try {
//			Display.setDisplayMode(new DisplayMode(width, height));
//			Display.setFullscreen(false);
//			Display.setVSyncEnabled(true);
//			Display.create();
//			Display.setTitle(title);
//		} catch (LWJGLException e) {
//			System.out.println("LWJGLException @ Renderer start");
//			System.exit(0);
//		}
	}

//	private static void renderChunks(final Graphics2D g2d, final int cx, final int cy, final Map<Chunk, Integer> chunks, final int z, final boolean force) {
//		long startTime = System.currentTimeMillis();
//		for (final Chunk chunk : chunks.keySet()) {
//			if (System.currentTimeMillis() - startTime > 100)
//				break;
//			if (force || chunks.get(chunk) != chunk.getModCount()) {
//				chunks.put(chunk, chunk.getModCount());
//				int tx = cx + (int) World.toBlockPos(chunk.chunkX);
//				int ty = cy + (int) -World.toBlockPos(chunk.chunkY);
////				int ty = cy + (int) World.toBlockPos(chunk.chunkZ);
//				renderChunk(g2d, z, chunk, tx, ty);
//			}
//		}
//	}
//
//	private static void renderChunk(final Graphics2D g2d, final int z, final Chunk chunk, final int tx, final int ty) {
////		if (chunk.chunkZ != 0)
////			return;
//		for (int y = 0; y < Chunk.AXIS_SIZE; ++y) {
//			for (int x = 0; x < Chunk.AXIS_SIZE; ++x) {
//				g2d.setColor(BlockColorHandler.getColor(chunk.getBlock(x, y, z)));
//				g2d.fillRect(tx + x, ty + Chunk.AXIS_SIZE - y, 1, 1);
//
//				g2d.setColor(FluidColorHandler.getColor(chunk.getFluid(x, y, z)));
//				g2d.fillRect(tx + x, ty + Chunk.AXIS_SIZE - y, 1, 1);
//			}
//		}
////		final Color color = BlockColorHandler.getColor(Blocks.STONE.get());
////		for (int y = 0; y < Chunk.AXIS_SIZE; ++y) {
////			for (int x = 0; x < Chunk.AXIS_SIZE; ++x) {
////				g2d.setColor(color);
////				g2d.fillRect(tx + x, ty + Chunk.AXIS_SIZE - y, 1, 1);
////			}
////		}
////		for (int z = 0; z < Chunk.AXIS_SIZE; ++z) {
////			for (int x = 0; x < Chunk.AXIS_SIZE; ++x) {
////				g2d.setColor(BlockColorHandler.getColor(chunk.getBlock(x, 0, z)));
////				g2d.fillRect(tx + x, ty + z, 1, 1);
////
////				g2d.setColor(FluidColorHandler.getColor(chunk.getFluid(x, 0, z)));
////				g2d.fillRect(tx + x, ty + z, 1, 1);
////			}
////		}
//	}

	public static void setup() {
		Loader.add(new LoadPhase.Builder("init_block_colors")
				.onRun(BlockColorHandler::init)
				.runAfter("register_registry_entries")
				.build()
		);
		Loader.add(new LoadPhase.Builder("init_fluid_colors")
				.onRun(FluidColorHandler::init)
				.runAfter("register_registry_entries")
				.build()
		);
		try {
			Class.forName(TestGame.class.getName(), true, Thread.currentThread().getContextClassLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isRunning() {
		return !hasCrashed && !isClosed;
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
