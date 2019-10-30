package io.github.cadiboo.testgame.chunk;

import io.github.cadiboo.testgame.block.Block;
import io.github.cadiboo.testgame.fluid.Fluid;
import io.github.cadiboo.testgame.registry.Registries;
import io.github.cadiboo.testgame.util.Utils;

/**
 * @author Cadiboo
 */
public class Chunk {

	// from 0-0xFF
	public static final int AXIS_MAX = 0xF;
	public static final int AXIS_SIZE = AXIS_MAX + 1;
	public static final int SHIFT = Utils.countBits(AXIS_MAX);
	public static final int SHIFT2 = SHIFT * 2;
	public static final int SIZE = AXIS_SIZE * AXIS_SIZE * AXIS_SIZE;

	public final long chunkX;
	public final long chunkY;
	public final long chunkZ;

	private final char[] blocks;
	private final char[] fluids;

	private int modCount = 0;

	public Chunk(long chunkX, long chunkY, long chunkZ, final char[] blocks, final char[] fluids) {
		if (blocks.length != SIZE)
			throw new IllegalArgumentException("Blocks array is not the right size");
		if (fluids.length != SIZE)
			throw new IllegalArgumentException("Fluids array is not the right size");
		this.blocks = blocks;
		this.fluids = fluids;
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.chunkZ = chunkZ;
	}

	public Chunk(long chunkX, long chunkY, long chunkZ) {
		this(chunkX, chunkY, chunkZ, new char[SIZE], new char[SIZE]);
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
		// index is 0xZYX
		final int z1 = z & AXIS_MAX;
		final int y1 = y & AXIS_MAX;
		final int x1 = x & AXIS_MAX;
//		if (x != x1 || y != y1 || z != z1) {
//			int fz = 0;
//		}
		final int index = z1 << SHIFT2 | y1 << SHIFT | x1;
		if (index >= SIZE)
			throw new ArrayIndexOutOfBoundsException("index=" + index + ", x=" + x + ", y=" + y + ", z=" + z);
		return index;
	}

	public int getModCount() {
		return modCount;
	}

}
