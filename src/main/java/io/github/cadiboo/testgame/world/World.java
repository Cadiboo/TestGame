package io.github.cadiboo.testgame.world;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.blockentity.BlockEntity;
import io.github.cadiboo.testgame.blockentity.BlockEntityType;
import io.github.cadiboo.testgame.chunk.Chunk;
import io.github.cadiboo.testgame.chunk.generator.ChunkGenerator;
import io.github.cadiboo.testgame.entity.Entity;
import io.github.cadiboo.testgame.entity.EntityType;
import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.util.LogicalSide;
import io.github.cadiboo.testgame.util.Updateable;
import io.github.cadiboo.testgame.util.math.Pos;
import io.github.cadiboo.testgame.util.math.Vec3d;

import java.util.ArrayList;

/**
 * A world holds entities, blocks, fluids and blockentities and processes the interactions between them.
 *
 * @author Cadiboo
 */
public abstract class World implements Updateable {

	private final ArrayList<Chunk> chunks = new ArrayList<>();

	public static long toChunkPos(final long blockPos) {
		return blockPos >> Chunk.SHIFT;
	}

	public static long toBlockPos(final long chunkPos) {
		return chunkPos << Chunk.SHIFT;
	}

	public void addEntity(final EntityType<?> entityType, final Vec3d pos) {
		final Entity entity = entityType.create(this, pos);
		final Chunk chunk = getOrCreateChunk(((long) pos.x), ((long) pos.y), ((long) pos.z));
		chunk.addEntity(entity);
	}

	public void addBlockEntity(final BlockEntityType<?> blockEntityType, final Pos pos) {
		final BlockEntity blockEntity = blockEntityType.create(this, pos);
		final Chunk chunk = getOrCreateChunk(pos.x, pos.y, pos.z);
		chunk.addBlockEntity(blockEntity);
	}

	public BlockEntity getBlockEntity(final Pos pos) {
		final Chunk chunk = getChunk(pos);
		if (chunk == null)
			return null;
		for (final BlockEntity blockEntity : chunk.getBlockEntities()) {
			if (blockEntity.pos.equals(pos))
				return blockEntity;
		}
		return null;
	}

	private Chunk getChunk(final Pos pos) {
		return getChunk(toChunkPos(pos.x), toChunkPos(pos.y), toChunkPos(pos.z));
	}

	public Chunk getOrCreateChunk(final long chunkX, final long chunkY, final long chunkZ) {
		final Chunk chunk = getChunk(chunkX, chunkY, chunkZ);
		if (chunk != null)
			return chunk;
		return generateChunk(chunkX, chunkY, chunkZ);
	}

	private Chunk getChunk(final long chunkX, final long chunkY, final long chunkZ) {
		for (final Chunk c : chunks) {
			if (c.chunkX == chunkX && c.chunkY == chunkY && c.chunkZ == chunkZ)
				return c;
		}
		return null;
	}

	private Chunk generateChunk(final long chunkX, final long chunkY, final long chunkZ) {
		Chunk c = ChunkGenerator.makeChunk(chunkX, chunkY, chunkZ);
		chunks.add(c);
		return c;
	}

	public void setBlock(Block block, long x, long y, long z) {
		getOrCreateChunk(toChunkPos(x), toChunkPos(y), toChunkPos(z))
				.setBlock(block, toChunkOffset(x), toChunkOffset(y), toChunkOffset(z));
	}

	private int toChunkOffset(final long blockPos) {
		return (byte) (blockPos & Chunk.AXIS_MAX);
	}

	public void setFluid(Fluid fluid, int x, int y, int z) {
		getOrCreateChunk(toChunkPos(x), toChunkPos(y), toChunkPos(z))
				.setFluid(fluid, toChunkOffset(x), toChunkOffset(y), toChunkOffset(z));
	}

	public Block getBlock(int x, int y, int z) {
		return getOrCreateChunk(toChunkPos(x), toChunkPos(y), toChunkPos(z))
				.getBlock(toChunkOffset(x), toChunkOffset(y), toChunkOffset(z));
	}

	public Fluid setBlock(int x, int y, int z) {
		return getOrCreateChunk(toChunkPos(x), toChunkPos(y), toChunkPos(z))
				.getFluid(toChunkOffset(x), toChunkOffset(y), toChunkOffset(z));
	}

	public abstract LogicalSide getLogicalSide();

	@Override
	public void update() {

	}

}
