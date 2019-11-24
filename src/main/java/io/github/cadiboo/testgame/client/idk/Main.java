package io.github.cadiboo.testgame.client.idk;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.chunk.Chunk;
import io.github.cadiboo.testgame.client.ClientGame;
import io.github.cadiboo.testgame.util.Utils;
import io.github.cadiboo.testgame.world.World;

import java.util.concurrent.ConcurrentHashMap;

public final class Main {

	public static volatile boolean isClosed;
	private static boolean hasCrashed;

	public static void main(String[] args) {
		logTime(Main::createWindow, "createWindow");
		logTime(Main::setup, "setup");
		while (isRunning()) {
//			logTime(Main::gameLoop, "gameLoop");
			gameLoop();
		}
		logTime(Main::shutDown, "shutDown");
		System.exit(0);
	}

	private static void createWindow() {
		final Window window = new Window(1280, 800, TestGame.DOMAIN);
		window.create();
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
		new ClientGame();
	}

	private static boolean isRunning() {
		return !hasCrashed && !isClosed;
	}

	private static void gameLoop() {

	}

	private static void shutDown() {
	}

	private static void logTime(final Runnable task, final String name) {
		final long start = System.nanoTime();
		try {
			task.run();
		} finally {
			System.out.println("Task \"" + name + "\" took " + Utils.nanosToMillis(System.nanoTime() - start) + "ms");
		}
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

	private static class Interval {

		private long time;

		private Interval() {
			time = System.nanoTime();
		}

	}

}
