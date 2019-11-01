package io.github.cadiboo.testgame.chunk;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.util.Utils;

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

	private int modCount = 0;
	private int blocksCount = 0;
	private int fluidsCount = 0;

	private Chunk(long chunkX, long chunkY, long chunkZ, final char[] blocks, final char[] fluids, final boolean count) {
		if (blocks.length != SIZE)
			throw new IllegalArgumentException("Blocks array is not the right size");
		if (fluids.length != SIZE)
			throw new IllegalArgumentException("Fluids array is not the right size");
		this.blocks = blocks;
		this.fluids = fluids;
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.chunkZ = chunkZ;
		if (count) {
			for (final char id : blocks) {
				if (id != 0)
					++blocksCount;
			}
			for (final char id : fluids) {
				if (id != 0)
					++fluidsCount;
			}
		}
	}

	/**
	 * Makes a new chunk from existing data
	 */
	public Chunk(long chunkX, long chunkY, long chunkZ, final char[] blocks, final char[] fluids) {
		this(chunkX, chunkY, chunkZ, blocks, fluids, true);
	}

	/**
	 * Makes a new empty chunk
	 */
	public Chunk(long chunkX, long chunkY, long chunkZ) {
		this(chunkX, chunkY, chunkZ, new char[SIZE], new char[SIZE], false);
	}

	public void setBlock(Block block, int x, int y, int z) {
		set(blocks, block.getId(), x, y, z);
	}

	public void setFluid(Fluid fluid, int x, int y, int z) {
		set(fluids, fluid.getId(), x, y, z);
	}

	public void setBlocks(Block block, int startX, int startY, int startZ, int endX, int endY, int endZ) {
		set(blocks, block.getId(), startX, startY, startZ, endX, endY, endZ);
	}

	public void setFluids(Fluid fluid, int startX, int startY, int startZ, int endX, int endY, int endZ) {
		set(fluids, fluid.getId(), startX, startY, startZ, endX, endY, endZ);
	}

	public void set(char[] array, char id, int startX, int startY, int startZ, int endX, int endY, int endZ) {
		for (int x = startX; x < endX; ++x) {
			for (int y = startY; y < endY; ++y) {
				for (int z = startZ; z < endZ; ++z) {
					set(array, id, x, y, z);
				}
			}
		}
	}

	private void set(final char[] array, final char id, final int x, final int y, final int z) {
		++modCount;
		array[getIndex(x, y, z)] = id;
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
		return blocksCount == 0 && fluidsCount == 0;
	}

}
