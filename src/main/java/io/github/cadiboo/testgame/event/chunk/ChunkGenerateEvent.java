package io.github.cadiboo.testgame.event.chunk;

import io.github.cadiboo.testgame.chunk.Chunk;
import io.github.cadiboo.testgame.event.pooled.PooledEvent;

import java.util.Random;
import java.util.Stack;

/**
 * @author Cadiboo
 */
public class ChunkGenerateEvent extends PooledEvent implements AutoCloseable {

	private static final Stack<ChunkGenerateEvent> POOL = new Stack<>();
	private Chunk chunk;
	private Random random;

	public ChunkGenerateEvent(final Chunk chunk, final Random random) {
		this.chunk = chunk;
		this.random = random;
	}

	public static ChunkGenerateEvent retain(Chunk chunk, final Random random) {
		if (POOL.isEmpty())
			return new ChunkGenerateEvent(chunk, random);
		else {
			final ChunkGenerateEvent pooled = POOL.pop();
			pooled.chunk = chunk;
			return pooled;
		}
	}

	@Override
	public void close() {
		chunk = null;
		POOL.push(this);
	}

	public Chunk getChunk() {
		return chunk;
	}

	public Random getRandom() {
		return random;
	}

}
