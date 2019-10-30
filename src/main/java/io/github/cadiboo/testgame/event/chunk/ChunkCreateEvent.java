package io.github.cadiboo.testgame.event.chunk;

import io.github.cadiboo.testgame.chunk.Chunk;
import io.github.cadiboo.testgame.event.pooled.PooledEvent;

import java.util.Random;
import java.util.Stack;

/**
 * @author Cadiboo
 */
public class ChunkCreateEvent extends PooledEvent implements AutoCloseable {

	private static final Stack<ChunkCreateEvent> POOL = new Stack<>();
	private Chunk chunk;
	private Random random;

	public ChunkCreateEvent(final Chunk chunk, final Random random) {
		this.chunk = chunk;
		this.random = random;
	}

	public static ChunkCreateEvent retain(Chunk chunk, final Random random) {
		if (POOL.isEmpty())
			return new ChunkCreateEvent(chunk, random);
		else {
			final ChunkCreateEvent pooled = POOL.pop();
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
