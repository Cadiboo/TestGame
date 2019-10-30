package io.github.cadiboo.testgame.world;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.chunk.Chunk;
import io.github.cadiboo.testgame.chunk.generator.ChunkGenerator;
import io.github.cadiboo.testgame.entity.Entity;
import io.github.cadiboo.testgame.fluid.Fluid;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cadiboo
 */
public class World {

	private final ArrayList<Chunk> chunks = new ArrayList<>();
	private final List<Entity> entities = new ArrayList<>();

	public static long toChunkPos(final long blockPos) {
		return blockPos >> Chunk.SHIFT;
	}

	public static long toBlockPos(final long chunkPos) {
		return chunkPos << Chunk.SHIFT;
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public Chunk getOrCreateChunk(final long chunkX, final long chunkY, final long chunkZ) {
		for (final Chunk c : chunks) {
			if (c.chunkX == chunkX && c.chunkY == chunkY && c.chunkZ == chunkZ)
				return c;
		}
		return generateChunk(chunkX, chunkY, chunkZ);
	}

	private Chunk generateChunk(final long chunkX, final long chunkY, final long chunkZ) {
		Chunk c = ChunkGenerator.makeChunk(chunkX, chunkY, chunkZ);
		chunks.add(c);
		return c;
	}

	public void setBlock(Block block, long x, long y, long z) {
		getOrCreateChunk(toChunkPos(x), toChunkPos(y), toChunkPos(z))
				.setBlock(block, (byte) (x & 0xFF), (byte) (y & 0xFF), (byte) (z & 0xFF));
	}

	public void setFluid(Fluid fluid, int x, int y, int z) {
		getOrCreateChunk(toChunkPos(x), toChunkPos(y), toChunkPos(z))
				.setFluid(fluid, (byte) (x & 0xFF), (byte) (y & 0xFF), (byte) (z & 0xFF));
	}

	public Block getBlock(int x, int y, int z) {
		return getOrCreateChunk(toChunkPos(x), toChunkPos(y), toChunkPos(z))
				.getBlock((byte) (x & 0xFF), (byte) (y & 0xFF), (byte) (z & 0xFF));
	}

	public Fluid setBlock(int x, int y, int z) {
		return getOrCreateChunk(toChunkPos(x), toChunkPos(y), toChunkPos(z))
				.getFluid((byte) (x & 0xFF), (byte) (y & 0xFF), (byte) (z & 0xFF));
	}

}
