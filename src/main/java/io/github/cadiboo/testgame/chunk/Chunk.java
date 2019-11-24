package io.github.cadiboo.testgame.chunk;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.blockentity.BlockEntity;
import io.github.cadiboo.testgame.entity.Entity;
import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.registry.RegistryEntry;
import io.github.cadiboo.testgame.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A chunk holds data about a cube of Blocks and Fluid
 *
 * @author Cadiboo
 */
public class Chunk {

	public static final int AXIS_MAX = 0xF; // 15
	public static final int AXIS_SIZE = AXIS_MAX + 1; // 16
	public static final int SHIFT = Utils.countBits(AXIS_MAX); // 8
	public static final int SHIFT2 = SHIFT * 2; // 16
	public static final int SIZE = AXIS_SIZE * AXIS_SIZE * AXIS_SIZE; // 4096

	public final long chunkX;
	public final long chunkY;
	public final long chunkZ;

	private final char[] blocks;
	private final char[] fluids;
	private final List<Entity> entities;
	private final List<BlockEntity> blockEntities;

	private transient int modCount = 0;
	private int blocksCount = 0;
	private int fluidsCount = 0;
	private int entitiesCount = 0;
	private int blockEntitiesCount = 0;

	private Chunk(long chunkX, long chunkY, long chunkZ, final char[] blocks, final char[] fluids, final boolean count, final List<Entity> entities, final List<BlockEntity> blockEntities) {
		if (blocks.length != SIZE)
			throw new IllegalArgumentException("Blocks array is not the right size");
		if (fluids.length != SIZE)
			throw new IllegalArgumentException("Fluids array is not the right size");
		this.blocks = blocks;
		this.fluids = fluids;
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.chunkZ = chunkZ;
		this.entities = entities;
		this.blockEntities = blockEntities;
		if (count) {
			for (final char id : blocks) {
				if (id != 0)
					++blocksCount;
			}
			for (final char id : fluids) {
				if (id != 0)
					++fluidsCount;
			}
			entitiesCount = entities.size();
			blockEntitiesCount = blockEntities.size();
		}
	}

	/**
	 * Makes a new chunk from existing data
	 */
	public Chunk(long chunkX, long chunkY, long chunkZ, final char[] blocks, final char[] fluids, final List<Entity> entities, final List<BlockEntity> blockEntities) {
		this(chunkX, chunkY, chunkZ, blocks, fluids, true, entities, blockEntities);
	}

	/**
	 * Makes a new empty chunk
	 */
	public Chunk(long chunkX, long chunkY, long chunkZ) {
		this(chunkX, chunkY, chunkZ, new char[SIZE], new char[SIZE], false, new ArrayList<>(), new ArrayList<>());
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<BlockEntity> getBlockEntities() {
		return blockEntities;
	}

	public void setBlock(Block block, int x, int y, int z) {
		blocksCount += set(blocks, block.getId(), x, y, z);
	}

	public void setFluid(Fluid fluid, int x, int y, int z) {
		fluidsCount += set(fluids, fluid.getId(), x, y, z);
	}

	public void setBlocks(Block block, int startX, int startY, int startZ, int endX, int endY, int endZ) {
		blocksCount += set(blocks, block.getId(), startX, startY, startZ, endX, endY, endZ);
	}

	public void setFluids(Fluid fluid, int startX, int startY, int startZ, int endX, int endY, int endZ) {
		fluidsCount += set(fluids, fluid.getId(), startX, startY, startZ, endX, endY, endZ);
	}

	private int set(final char[] array, final char id, final int startX, final int startY, final int startZ, final int endX, final int endY, final int endZ) {
		int changed = 0;
		for (int x = startX; x < endX; ++x) {
			for (int y = startY; y < endY; ++y) {
				for (int z = startZ; z < endZ; ++z) {
					changed += set(array, id, x, y, z);
				}
			}
		}
		return changed;
	}

	private int set(final char[] array, final char id, final int x, final int y, final int z) {
		return set(array, id, getIndex(x, y, z));
	}

	private int set(final char[] array, final char id, final int index) {
		final char old = array[index];
		if (old != id) {
			++modCount;
			array[index] = id;
			return getIncrement(id);
		}
		return 0;
	}

	/**
	 * If the id is 0 (the entry is air) then 1 should be removed from the amount of blocks/fluids in the chunk
	 * otherwise, 1 should be added
	 */
	private int getIncrement(final RegistryEntry e) {
		return getIncrement(e.getId());
	}

	/**
	 * If the id is 0 (the entry is air) then 1 should be removed from the amount of blocks/fluids in the chunk
	 * otherwise, 1 should be added
	 */
	private int getIncrement(final char id) {
		return id == 0 ? -1 : 1;
	}

	public Block getBlock(int x, int y, int z) {
		return Registries.BLOCKS.get(get(x, y, z, blocks));
	}

	public Fluid getFluid(int x, int y, int z) {
		return Registries.FLUIDS.get(get(x, y, z, fluids));
	}

	private char get(final int x, final int y, final int z, final char[] array) {
		return array[getIndex(x, y, z)];
	}

	private int getIndex(int x, int y, int z) {
		// index is packed ZYX
		final int index = (z & AXIS_MAX) << SHIFT2 | (y & AXIS_MAX) << SHIFT | x & AXIS_MAX;
		if (index >= SIZE)
			throw new ArrayIndexOutOfBoundsException("index=" + index + ", x=" + x + ", y=" + y + ", z=" + z);
		return index;
	}

	public int getModCount() {
		return modCount;
	}

	public boolean isEmpty() {
		return blocksCount == 0 && fluidsCount == 0 && entitiesCount == 0 && blockEntitiesCount == 0;
	}

	public void addEntity(final Entity entity) {
		if (entities.add(entity)) {
			++entitiesCount;
			++modCount;
		}
	}

	public void removeEntity(final Entity entity) {
		if (entities.remove(entity)) {
			--entitiesCount;
			++modCount;
		}
	}

	public void addBlockEntity(final BlockEntity blockEntity) {
		if (blockEntities.add(blockEntity)) {
			++blockEntitiesCount;
			++modCount;
		}
	}

	public void removeBlockEntity(final BlockEntity blockEntity) {
		if (blockEntities.remove(blockEntity)) {
			--blockEntitiesCount;
			++modCount;
		}
	}

}
