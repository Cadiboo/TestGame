package io.github.cadiboo.testgame.chunk.generator;

import io.github.cadiboo.testgame.TestGame;
import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.chunk.Chunk;
import io.github.cadiboo.testgame.event.chunk.ChunkCreateEvent;
import io.github.cadiboo.testgame.event.chunk.ChunkGenerateEvent;
import io.github.cadiboo.testgame.init.Blocks;
import io.github.cadiboo.testgame.registry.RegistryObject;
import io.github.cadiboo.testgame.world.World;

import java.util.Random;

/**
 * @author Cadiboo
 */
public final class ChunkGenerator {

	private static final Layer[] LAYERS = {
			new Layer(175, 179, Blocks.TURF),
			new Layer(150, 175, Blocks.DIRT),
			new Layer(-5000, 150, Blocks.STONE),
	};
	private static final Decorator[] DECORATORS = {
			new Decorator(5, 179, 180, Blocks.GRASS),
			new Decorator(50, -300, 150, Blocks.COAL_ORE),
			new Decorator(100, -400, 100, Blocks.IRON_ORE),
			new Decorator(200, -400, 0, Blocks.GOLD_ORE),
			new Decorator(300, -500, -200, Blocks.DIAMOND_ORE),
			new Decorator(2000, -1000, -100, Blocks.EMERALD_ORE),
			new Decorator(1000, -350, 50, Blocks.REDSTONE_ORE),
	};

	public static void generateChunk(ChunkGenerateEvent event) {
		final Chunk chunk = event.getChunk();
		final Random random = event.getRandom();

		final long posX = World.toBlockPos(chunk.chunkX);
		final long posY = World.toBlockPos(chunk.chunkY);
		final long posZ = World.toBlockPos(chunk.chunkZ);

		for (final Layer layer : LAYERS) {
			final Block block = layer.block.get();
			for (int x = 0; x < Chunk.AXIS_SIZE; x++) {
				for (int y = 0; y < Chunk.AXIS_SIZE; y++) {
					for (int z = 0; z < Chunk.AXIS_SIZE; z++) {
						long oY = posY + y;
						if (layer.minY < oY && oY <= layer.maxY)
							chunk.setBlock(block, x, y, z);
					}
				}
			}
		}
		for (final Decorator dec : DECORATORS) {
			final Block block = dec.block.get();
			for (int x = 0; x < Chunk.AXIS_SIZE; x++) {
				for (int y = 0; y < Chunk.AXIS_SIZE; y++) {
					for (int z = 0; z < Chunk.AXIS_SIZE; z++) {
						long oY = posY + y;
						if (dec.minY < oY && oY <= dec.maxY)
							if (random.nextInt(dec.chance) == 0)
								chunk.setBlock(block, x, y, z);
					}
				}
			}
		}
//		// Caves
//		final Block AIR = Blocks.AIR.get();
//		for (int x = 0; x < Chunk.AXIS_SIZE; x++) {
//			for (int y = 0; y < Chunk.AXIS_SIZE; y++) {
//				for (int z = 0; z < Chunk.AXIS_SIZE; z++) {
//					long oX = posX + x;
//					long oY = posY + y;
//					long oZ = posZ + z;
//					if (sin(oX) > 0) {
//						chunk.setBlock(AIR, x, y, z);
//					}
//				}
//			}
//		}

	}

	public static Chunk makeChunk(final long chunkX, final long chunkY, final long chunkZ) {
		Chunk c = new Chunk(chunkX, chunkY, chunkZ);
		Random random = new Random(chunkX * chunkY * chunkZ); // TODO: seed
		try (ChunkCreateEvent event = ChunkCreateEvent.retain(c, random)) {
			TestGame.EVENT_BUS.post(event);
		}
		try (ChunkGenerateEvent event = ChunkGenerateEvent.retain(c, random)) {
			TestGame.EVENT_BUS.post(event);
		}
		return c;
	}

	private static class Layer {

		private final long minY;
		private final long maxY;
		private final RegistryObject<? extends Block> block;

		public Layer(final long minY, final long maxY, final RegistryObject<? extends Block> block) {
			this.minY = minY;
			this.maxY = maxY;
			this.block = block;
		}

	}

	private static class Decorator {

		private final int chance;
		private final long minY;
		private final long maxY;
		private final RegistryObject<? extends Block> block;

		public Decorator(final int chance, final long minY, final long maxY, final RegistryObject<? extends Block> block) {
			this.chance = chance;
			this.minY = minY;
			this.maxY = maxY;
			this.block = block;
		}

	}

}
